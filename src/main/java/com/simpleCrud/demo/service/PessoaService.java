package com.simpleCrud.demo.service;

import com.simpleCrud.demo.entity.Pessoa;
import com.simpleCrud.demo.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository pessoaRepository;


    public Pessoa buscaPorId(Long id) {
        Optional<Pessoa> byId = pessoaRepository.findById(id);
        return byId.orElse(null);
    }
    public Pessoa salvar(Pessoa pessoa) {
        return pessoaRepository.save(pessoa);
    }

    public List<Pessoa> listaPessoa() {
        return pessoaRepository.findAll();
    }


    public void deleta(Long id) {
        Optional<Pessoa> byId = pessoaRepository.findById(id);
        byId.ifPresent(pessoa -> pessoaRepository.delete(pessoa));
    }


}
