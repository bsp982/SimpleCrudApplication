package com.simpleCrud.SimpleCrudApplication.dto.mapper;

import com.simpleCrud.SimpleCrudApplication.dto.ContatoDTO;
import com.simpleCrud.SimpleCrudApplication.dto.PessoaDTO;
import com.simpleCrud.SimpleCrudApplication.dto.PessoaRequestDTO;
import com.simpleCrud.SimpleCrudApplication.entity.Contato;
import com.simpleCrud.SimpleCrudApplication.entity.Pessoa;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class PessoaMapper {

    public Pessoa toModel(PessoaRequestDTO pessoaRequestDTO) {

        Pessoa pessoa = new Pessoa();
        pessoa.setNome(pessoaRequestDTO.nome());
        pessoa.setCpf(pessoaRequestDTO.cpf());
        pessoa.setNascimento(pessoaRequestDTO.nascimento());
        Set<Contato> contatos = pessoaRequestDTO.contatos().stream()
                .map(contatoDTO -> {
                    Contato lesson = new Contato();
                    if (lesson.getId() > 0) {
                        lesson.setId(contatoDTO._id());
                    }
                    lesson.setNome(contatoDTO.nome());
                    lesson.setTelefone(contatoDTO.telefone());
                    lesson.setPessoa(pessoa);
                    return lesson;
                }).collect(Collectors.toSet());
        pessoa.setContatos(contatos);

        return pessoa;
    }

    public PessoaDTO toDTO(Pessoa pessoa) {
        if (pessoa == null) {
            return null;
        }
        List<ContatoDTO> lessonDTOList = pessoa.getContatos()
                .stream()
                .map(lesson -> new ContatoDTO(lesson.getId(), lesson.getNome(), lesson.getTelefone()))
                .toList();
        return new PessoaDTO(pessoa.getId(), pessoa.getNome(), pessoa.getCpf(), pessoa.getNascimento(),
                lessonDTOList);
    }

    public Contato convertContatoDTOToContato(ContatoDTO contatoDTO) {
        Contato lesson = new Contato();
        lesson.setId(contatoDTO._id());
        lesson.setNome(contatoDTO.nome());
        lesson.setTelefone(contatoDTO.telefone());
        return lesson;
    }
}
