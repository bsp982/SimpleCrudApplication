package com.simpleCrud.SimpleCrudApplication.service;

import com.simpleCrud.SimpleCrudApplication.dto.PessoaDTO;
import com.simpleCrud.SimpleCrudApplication.dto.PessoaPageDTO;
import com.simpleCrud.SimpleCrudApplication.dto.PessoaRequestDTO;
import com.simpleCrud.SimpleCrudApplication.dto.mapper.PessoaMapper;
import com.simpleCrud.SimpleCrudApplication.entity.Contato;
import com.simpleCrud.SimpleCrudApplication.entity.Pessoa;
import com.simpleCrud.SimpleCrudApplication.infra.BusinessException;
import com.simpleCrud.SimpleCrudApplication.infra.RecordNotFoundException;
import com.simpleCrud.SimpleCrudApplication.repository.PessoaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Service
@Validated
public class PessoaService {

    private final PessoaRepository pessoaRepository;
    private final PessoaMapper pessoaMapper;

    public PessoaService(PessoaRepository pessoaRepository, PessoaMapper pessoaMapper) {
        this.pessoaRepository = pessoaRepository;
        this.pessoaMapper = pessoaMapper;
    }

    public PessoaPageDTO findAll(@PositiveOrZero int page, @Positive @Max(1000) int pageSize) {
        Page<Pessoa> pessoaPage = pessoaRepository.findAll(PageRequest.of(page, pageSize));
        List<PessoaDTO> list = pessoaPage.getContent().stream()
                .map(pessoaMapper::toDTO)
                .toList();
        return new PessoaPageDTO(list, pessoaPage.getTotalElements(), pessoaPage.getTotalPages());
    }

    public List<PessoaDTO> findByName(@NotNull String nome) {
        return pessoaRepository.findByNome(nome).stream().map(pessoaMapper::toDTO).toList();
    }

    public PessoaDTO findById(@Positive @NotNull Long id) {
        return pessoaRepository.findById(id).map(pessoaMapper::toDTO)
                .orElseThrow(() -> new RecordNotFoundException(id));
    }

    public PessoaDTO create(@Valid PessoaRequestDTO pessoaRequestDTO) {
        pessoaRepository.findByNome(pessoaRequestDTO.nome()).stream()
                .findAny().ifPresent(c -> {
                    throw new BusinessException("A pessoa com o nome " + pessoaRequestDTO.nome() + " já esta cadastrada.");
                });
        Pessoa pessoa = pessoaMapper.toModel(pessoaRequestDTO);
        return pessoaMapper.toDTO(pessoaRepository.save(pessoa));
    }

    public PessoaDTO update(@Positive @NotNull Long id, @Valid PessoaRequestDTO pessoaRequestDTO) {
        return pessoaRepository.findById(id).map(actual -> {
                    actual.setNome(pessoaRequestDTO.nome());
                    mergeContatosForUpdate(actual, pessoaRequestDTO);
                    return pessoaMapper.toDTO(pessoaRepository.save(actual));
                })
                .orElseThrow(() -> new RecordNotFoundException(id));
    }

    private void mergeContatosForUpdate(Pessoa pessoaEdit, PessoaRequestDTO pessoaRequestDTO) {
        List<Contato> contatosToRemove = pessoaEdit.getContatos().stream()
                .filter(contato -> pessoaRequestDTO.contatos().stream()
                        .anyMatch(contatoDTO -> contatoDTO._id() != 0 && contatoDTO._id() == contato.getId()))
                .toList();
        contatosToRemove.forEach(pessoaEdit::removeContato);

        pessoaRequestDTO.contatos().forEach(contatoDTO -> {
            if (contatoDTO._id() == 0) {
                pessoaEdit.addContato(pessoaMapper.convertContatoDTOToContato(contatoDTO));
            } else {
                pessoaEdit.getContatos().stream()
                        .filter(contato -> contato.getId() == contatoDTO._id())
                        .findAny()
                        .ifPresent(contato -> {
                            contato.setNome(contatoDTO.nome());
                            contato.setTelefone(contatoDTO.telefone());
                        });
            }
        });
    }

    public void delete(@Positive @NotNull Long id) {
        pessoaRepository.delete(pessoaRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(id)));
    }
}
