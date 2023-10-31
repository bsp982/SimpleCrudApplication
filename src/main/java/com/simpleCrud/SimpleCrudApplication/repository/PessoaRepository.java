package com.simpleCrud.SimpleCrudApplication.repository;

import com.simpleCrud.SimpleCrudApplication.entity.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
}
