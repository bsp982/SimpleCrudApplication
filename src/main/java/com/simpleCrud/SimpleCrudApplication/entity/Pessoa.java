package com.simpleCrud.SimpleCrudApplication.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.Cascade;

import javax.validation.constraints.NotEmpty;
import java.sql.Date;
import java.util.List;

@Entity(name = "pessoa")
@Table(name = "pessoa")
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotEmpty
    private String name;
    @NotEmpty
    private String cpf;
    @NotEmpty
    private Date nascimento;

    //Lista de Contatos
    @OneToMany(fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL, mappedBy = "pessoa")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<Contato> contatos;

    public List<Contato> getContatos() {
        return contatos;
    }

    public void setContatos(List<Contato> contatos) {
        this.contatos = contatos;
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

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Date getNascimento() {
        return nascimento;
    }

    public void setNascimento(Date nascimento) {
        this.nascimento = nascimento;
    }
}
