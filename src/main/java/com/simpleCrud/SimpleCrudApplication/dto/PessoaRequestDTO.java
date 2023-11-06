package com.simpleCrud.SimpleCrudApplication.dto;

import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.util.List;

public record PessoaRequestDTO(
        @NotBlank @NotNull @Length(min = 5, max = 200) String nome,
        @NotBlank @NotNull @Length(min = 5, max = 200) String cpf,
        @NotBlank @NotNull @Length(min = 5, max = 200) Date nascimento,
        @NotNull @NotEmpty @Valid List<ContatoDTO> contatos) {
}
