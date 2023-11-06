package com.simpleCrud.SimpleCrudApplication.dto;

import java.util.List;

public record PessoaPageDTO(List<PessoaDTO> pessoas, long totalElements, int totalPages) {

}
