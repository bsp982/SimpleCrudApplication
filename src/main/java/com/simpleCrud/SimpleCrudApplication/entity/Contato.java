package com.simpleCrud.SimpleCrudApplication.entity;

import jakarta.persistence.*;

import javax.validation.constraints.NotEmpty;
import java.sql.Date;

@Entity(name = "contato")
@Table(name = "contato")
public class Contato {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @NotEmpty
    private String nome;
    @NotEmpty
    private String telefone;
    @NotEmpty
    private Date email;

    @ManyToOne()
    @JoinColumn(name = "pessoa")
    @NotEmpty
    private Pessoa pessoa;


    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

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

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Date getEmail() {
        return email;
    }

    public void setEmail(Date email) {
        this.email = email;
    }
}
