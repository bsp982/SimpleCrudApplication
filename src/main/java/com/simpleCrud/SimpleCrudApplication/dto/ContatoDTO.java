package com.simpleCrud.SimpleCrudApplication.dto;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record ContatoDTO(
        int _id,
        @NotBlank @NotNull @Length(min = 5, max = 100) String nome,
        @NotBlank @NotNull @Length(min = 10, max = 11) String telefone) {
}
