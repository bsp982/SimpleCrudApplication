package com.simpleCrud.demo.entity;

import jakarta.persistence.*;

import java.sql.Date;

@Entity(name = "contato")
@Table(name = "contato")
public class Contato {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;

    private String telefone;

    private Date email;

    @ManyToOne()
    @JoinColumn(name = "pessoa")
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
