package dev.jmvg.api.repository.lancamento;

import dev.jmvg.api.model.Lancamento;
import dev.jmvg.api.repository.filtro.LancamentoFiltro;

import java.util.List;

public interface LancamentoRepositoryQuery {
    List<Lancamento> filtrar(LancamentoFiltro lancamentoFiltro);
}
