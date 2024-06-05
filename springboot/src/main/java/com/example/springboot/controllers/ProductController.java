package com.example.springboot.controllers;

import com.example.springboot.dtos.ProductRecordDto;
import com.example.springboot.models.ProductModel;
import com.example.springboot.repositories.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController // @RestController define que a classe é um controlador REST (recebe requisições e responde)
public class ProductController {

    @Autowired // @Autowired faz a injeção de dependência (instancia um objeto de uma classe em outra classe)
    ProductRepository productRepository;

    @PostMapping("/products") // @PostMapping define que o método responde a requisições do tipo POST na URL /products
    public ResponseEntity<ProductModel> saveProduct(@RequestBody @Valid ProductRecordDto productRecordDto) {
        // ResponseEntity é uma classe que representa toda a resposta HTTP
        // @RequestBody define que o corpo da requisição será convertido para o objeto productRecordDto
        // @Valid define que o objeto productRecordDto será validado
        var productModel = new ProductModel();
        BeanUtils.copyProperties(productRecordDto, productModel);
        // BeanUtils.copyProperties copia as propriedades de um objeto para outro objeto
        return ResponseEntity.status(HttpStatus.CREATED).body(productRepository.save(productModel));
        // ResponseEntity.status define o status da resposta HTTP
        // HttpStatus.CREATED é um status HTTP que indica que a requisição foi bem sucedida e um novo recurso foi criado
    }

    @GetMapping("/products") // @GetMapping define que o método responde a requisições do tipo GET na URL /products
    public ResponseEntity<List<ProductModel>> getAllProducts() {
        List<ProductModel> productsList = productRepository.findAll();
        if(!productsList.isEmpty()) {
            for (ProductModel product : productsList) {
                UUID id = product.getIdProduct();
                // Adiciona um link para cada produto usando HATEOAS
                product.add(linkTo(methodOn(ProductController.class).getOneProduct(id)).withSelfRel());
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(productsList);
    }

    @GetMapping("/products/{id}") // @GetMapping define que o método responde a requisições do tipo GET na URL /products
    public ResponseEntity<Object> getOneProduct(@PathVariable(value = "id") UUID id) {
        // @PathVariable define que o valor da variável id será passado na URL
        Optional<ProductModel> product = productRepository.findById(id);
        if (product.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }
        // Adiciona um link para a lista de todos os produtos usando HATEOAS
        product.get().add(linkTo(methodOn(ProductController.class).getAllProducts()).withRel("List of all products"));
        return ResponseEntity.status(HttpStatus.OK).body(product.get());
    }

    @PutMapping("/products/{id}") // @PutMapping define que o método responde a requisições do tipo PUT na URL /products
    public ResponseEntity<Object> updateProduct(@PathVariable(value = "id") UUID id, @RequestBody @Valid ProductRecordDto productRecordDto) {
        Optional<ProductModel> product = productRepository.findById(id);
        if (product.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }
        ProductModel productModel = product.get();
        BeanUtils.copyProperties(productRecordDto, productModel);
        return ResponseEntity.status(HttpStatus.OK).body(productRepository.save(productModel));
    }

    @DeleteMapping("/products/{id}") // @DeleteMapping define que o método responde a requisições do tipo DELETE na URL /products
    public ResponseEntity<Object> deleteProduct(@PathVariable(value = "id") UUID id) {
        Optional<ProductModel> product = productRepository.findById(id);
        if (product.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }
        productRepository.delete(product.get());
        return ResponseEntity.status(HttpStatus.OK).body("Product deleted successfully");
    }
}
