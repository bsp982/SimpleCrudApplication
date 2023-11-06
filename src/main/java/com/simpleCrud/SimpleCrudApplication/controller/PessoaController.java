package com.simpleCrud.SimpleCrudApplication.controller;

import com.simpleCrud.SimpleCrudApplication.dto.PessoaDTO;
import com.simpleCrud.SimpleCrudApplication.dto.PessoaPageDTO;
import com.simpleCrud.SimpleCrudApplication.dto.PessoaRequestDTO;
import com.simpleCrud.SimpleCrudApplication.entity.Pessoa;
import com.simpleCrud.SimpleCrudApplication.repository.PessoaRepository;
import com.simpleCrud.SimpleCrudApplication.service.PessoaService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Validated
@RestController
@RequestMapping("/pessoa")
public class PessoaController {
    private final PessoaService pessoaService;

    public PessoaController(PessoaService pessoaService) {
        this.pessoaService = pessoaService;
    }

    @GetMapping
    public PessoaPageDTO findAll(@RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "10") int pageSize) {
        return pessoaService.findAll(page, pageSize);
    }

    @GetMapping("/searchByName")
    public List<PessoaDTO> findByName(@RequestParam @NotNull @NotBlank String nome) {
        return pessoaService.findByName(nome);
    }

    @GetMapping("/{id}")
    public PessoaDTO findById(@PathVariable @Positive @NotNull Long id) {
        return pessoaService.findById(id);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public PessoaDTO create(@RequestBody @Valid PessoaRequestDTO pessoa) {
        return pessoaService.create(pessoa);
    }

    @PutMapping(value = "/{id}")
    public PessoaDTO update(@PathVariable @Positive @NotNull Long id,
                            @RequestBody @Valid PessoaRequestDTO pessoa) {
        return pessoaService.update(id, pessoa);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable @Positive @NotNull Long id) {
        pessoaService.delete(id);
    }

}
