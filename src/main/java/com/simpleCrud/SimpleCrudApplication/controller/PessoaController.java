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
        boolean valid = isValid(data.getCpf());
        if (valid) {
            repository.save(data);
            return ResponseEntity.ok().build();
        } else {
            throw new EntityNotFoundException();
        }
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
            boolean valid = isValid(data.getCpf());
            if (valid) {
                product.setCpf(data.getCpf());
            }
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


    public boolean isValid(String value) {
        return value == null || value.isEmpty() || isCpf(value);
    }

    private boolean isCpf(String cpf) {
        cpf = cpf.replace(".", "");
        cpf = cpf.replace("-", "");

        try {
            Long.parseLong(cpf);
        } catch (NumberFormatException e) {
            return false;
        }

        int d1, d2;
        int digito1, digito2, resto;
        int digitoCPF;
        String nDigResult;

        d1 = d2 = 0;
        digito1 = digito2 = resto = 0;

        for (int nCount = 1; nCount < cpf.length() - 1; nCount++) {
            digitoCPF = Integer.valueOf(cpf.substring(nCount - 1, nCount)).intValue();
            d1 = d1 + (11 - nCount) * digitoCPF;
            d2 = d2 + (12 - nCount) * digitoCPF;
        }

        resto = (d1 % 11);
        if (resto < 2)
            digito1 = 0;
        else
            digito1 = 11 - resto;

        d2 += 2 * digito1;
        resto = (d2 % 11);
        if (resto < 2)
            digito2 = 0;
        else
            digito2 = 11 - resto;
        String nDigVerific = cpf.substring(cpf.length() - 2, cpf.length());
        nDigResult = String.valueOf(digito1) + String.valueOf(digito2);

        return nDigVerific.equals(nDigResult);
    }

}
