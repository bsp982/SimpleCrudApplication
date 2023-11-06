package com.simpleCrud.SimpleCrudApplication.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Date;
import java.util.List;

public record PessoaDTO(
        @JsonProperty("_id") Long id,
        String nome,
        String cpf,

        Date nascimento,
        List<ContatoDTO> contatos) {
}
