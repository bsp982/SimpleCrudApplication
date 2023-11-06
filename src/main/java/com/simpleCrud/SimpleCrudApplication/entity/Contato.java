package com.simpleCrud.SimpleCrudApplication.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.sql.Date;

@Entity()
public class Contato {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @NotBlank
    @Length(min = 5, max = 100)
    @Column(length = 100, nullable = false)
    private String nome;

    @NotEmpty
    @NotBlank
    @Length(min = 5, max = 100)
    @Column(length = 100, nullable = false)
    private String telefone;

    @JoinColumn(name = "pessoa")
    @NotEmpty
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OrderBy("id ASC")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Pessoa pessoa;


    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

}
