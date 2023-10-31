package com.simpleCrud.SimpleCrudApplication.controller;

import com.simpleCrud.SimpleCrudApplication.entity.Pessoa;
import com.simpleCrud.SimpleCrudApplication.repository.PessoaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/pessoa")
public class PessoaController {
    @Autowired
    private PessoaRepository repository;

    @GetMapping
    public ResponseEntity getAllPessoas() {
        var allPessoas = repository.findAll();
        return ResponseEntity.ok(allPessoas);
    }

    @PostMapping
    public ResponseEntity registerPessoa(@RequestBody Pessoa data) {
        repository.save(data);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity getPessoaById(@PathVariable Long id) {
        Optional<Pessoa> pessoaOptional = repository.findById(id);
        if (pessoaOptional.isPresent()) {
            return ResponseEntity.ok(pessoaOptional.get());
        } else {
            throw new EntityNotFoundException();
        }
    }

    @GetMapping("/{nome}")
    public ResponseEntity getPessoasByNome(@PathVariable String nome) {
        List<Pessoa> collect = repository.findAll().stream()
                .filter(item -> Objects.equals(item.getName(), nome))
                .collect(Collectors.toList());
        return ResponseEntity.ok(collect);
    }

    @PutMapping
    @Transactional
    public ResponseEntity updatePessoa(@RequestBody Pessoa data) {
        Optional<Pessoa> optionalPessoa = repository.findById(data.getId());
        if (optionalPessoa.isPresent()) {
            Pessoa product = optionalPessoa.get();
            product.setName(data.getName());
            product.setCpf(data.getCpf());
            product.setNascimento(data.getNascimento());

            return ResponseEntity.ok(product);
        } else {
            throw new EntityNotFoundException();
        }
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deletePessoa(@PathVariable Long id) {
        Optional<Pessoa> optionalPessoa = repository.findById(id);
        if (optionalPessoa.isPresent()) {
            repository.delete(optionalPessoa.get());
            return ResponseEntity.noContent().build();
        } else {
            throw new EntityNotFoundException();
        }
    }

}
