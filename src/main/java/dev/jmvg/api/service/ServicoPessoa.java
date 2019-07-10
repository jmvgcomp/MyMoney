package dev.jmvg.api.service;

import dev.jmvg.api.model.Pessoa;
import dev.jmvg.api.repository.PessoaRepositorio;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class ServicoPessoa {
    private final PessoaRepositorio pessoaRepositorio;

    public ServicoPessoa(PessoaRepositorio pessoaRepositorio) {
        this.pessoaRepositorio = pessoaRepositorio;
    }

    public Pessoa pessoaAtualizar(Long codigo, Pessoa pessoa){
        Pessoa pessoaSalva = buscarPessoaPeloCodigo(codigo);
        BeanUtils.copyProperties(pessoa, pessoaSalva, "codigo");
        return pessoaRepositorio.save(pessoaSalva);
    }

    public void atualizarPropriedadeAtivo(Long codigo, Boolean ativo) {

        Pessoa pessoaSalva = buscarPessoaPeloCodigo(codigo);
        pessoaSalva.setAtivo(ativo);
        pessoaRepositorio.save(pessoaSalva);
    }

    private Pessoa buscarPessoaPeloCodigo(Long codigo){
        Pessoa pessoaSalva = pessoaRepositorio.findOne(codigo);
        if(pessoaSalva == null){
            throw new EmptyResultDataAccessException(1);
        }
        return pessoaSalva;
    }
}
