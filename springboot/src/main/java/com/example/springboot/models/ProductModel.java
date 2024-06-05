package com.example.springboot.models;

import jakarta.persistence.*; // * é um coringa que importa todas as classes do pacote
import org.springframework.hateoas.RepresentationModel;
// Hateoas é um acrônimo para Hypermedia as the Engine of Application State
// Ele serve para permitir que o servidor envie links para o cliente, permitindo que o cliente navegue pela aplicação

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Entity // @Entitu define que a classe é uma entidade
@Table(name = "TB_PRODUCTS") // @Table define o nome da tabela no banco de dados
public class ProductModel extends RepresentationModel<ProductModel> implements Serializable {
    // RepresentationModel é uma classe que representa um modelo de representação que serve como base para a representação de recursos como JSON ou XML
    // Serializable é uma interface que permite que os objetos de uma classe sejam convertidos em uma sequência de bytes
    // para que os objetos possam ser salvos em arquivos, enviados pela rede ou armazenados em banco de dados.
    private static final long serialVersionUID = 1L; // serialVersionUID é um número de versão universal para uma classe Serializable

    @Id // @Id define que o atributo é uma chave primária
    @GeneratedValue(strategy = GenerationType.AUTO) // @GeneratedValue define a estratégia de geração de valores para a chave primária (AUTO = escolhe a melhor estratégia)
    private UUID idProduct; // UUID é um identificador único universal
    private String name;
    private BigDecimal value; // bigDecimal é uma classe que fornece suporte para aritmética de precisão arbitrária

    public UUID getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(UUID idProduct) {
        this.idProduct = idProduct;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }
}
