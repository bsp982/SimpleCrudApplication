package com.simpleCrud.SimpleCrudApplication.entity;

import jakarta.persistence.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotBlank
    @Length(min = 5, max = 200)
    @Column(length = 200, nullable = false)
    private String nome;

    @NotBlank
    @Length(min = 5, max = 200)
    @Column(length = 200, nullable = false)
    private String cpf;
    @NotEmpty
    private Date nascimento;

    //Lista de Contatos
    @OneToMany(mappedBy = "pessoa", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("id")
    @NotEmpty
    private Set<Contato> contatos = new HashSet<>();

    public Set<Contato> getContatos() {
        return contatos;
    }

    public void addContato(Contato contato) {
        if (contato == null) {
            throw new IllegalArgumentException("Contato não pode ser nulo.");
        }
        contato.setPessoa(this);
        this.contatos.add(contato);
    }

    public void removeContato(Contato contato) {
        if (contato == null) {
            throw new IllegalArgumentException("Contato não pode ser nulo.");
        }
        contato.setPessoa(null);
        this.contatos.remove(contato);
    }

    public void setContatos(Set<Contato> contatos) {
        if (contatos == null) {
            throw new IllegalArgumentException("Contato não pode ser nulo.");
        }
        this.contatos.clear();
        this.contatos.addAll(contatos);
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
