package com.example.api_nunesports.domain;


import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "TB01_PRODUTO")
@SequenceGenerator(name = "TB01_PRODUTO_TB01_COD_PRODUTO_SEQ", sequenceName = "TB01_PRODUTO_TB01_COD_PRODUTO_SEQ", allocationSize = 1)
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TB01_PRODUTO_TB01_COD_PRODUTO_SEQ")
    @Column(name = "TB01_COD_PRODUTO")
    private Long id;

    @Column(name = "TB01_NOME")
    private String nome;

    @Column(name = "TB01_DESCRICAO")
    private String descricao;

    @Column(name = "TB01_PRECO")
    private BigDecimal preco;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }
}
