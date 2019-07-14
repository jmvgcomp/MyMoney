package dev.jmvg.api.service;

import dev.jmvg.api.model.Lancamento;
import dev.jmvg.api.model.Pessoa;
import dev.jmvg.api.repository.LancamentoRepositorio;
import dev.jmvg.api.repository.PessoaRepositorio;
import dev.jmvg.api.service.exception.PessoaInexistenteOuInativaException;
import org.springframework.stereotype.Service;

@Service
public class ServicoLancamento {
    private final PessoaRepositorio pessoaRepositorio;
    private LancamentoRepositorio lancamentoRepositorio;
    public ServicoLancamento(PessoaRepositorio pessoaRepositorio, LancamentoRepositorio lancamentoRepositorio) {
        this.pessoaRepositorio = pessoaRepositorio;
        this.lancamentoRepositorio = lancamentoRepositorio;
    }

    public Lancamento salvar(Lancamento lancamento) {
        Pessoa pessoa = pessoaRepositorio.findOne(lancamento.getPessoa().getCodigo());
        if (pessoa == null || pessoa.isInativo()) {
            throw new PessoaInexistenteOuInativaException();
        }
        return lancamentoRepositorio.save(lancamento);
    }
}
