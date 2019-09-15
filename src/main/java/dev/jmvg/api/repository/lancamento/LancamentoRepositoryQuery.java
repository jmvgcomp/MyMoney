package dev.jmvg.api.repository.lancamento;

import dev.jmvg.api.model.Lancamento;
import dev.jmvg.api.repository.filtro.LancamentoFiltro;
import dev.jmvg.api.repository.projection.ResumoLancamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LancamentoRepositoryQuery {
    Page<Lancamento> filtrar(LancamentoFiltro lancamentoFiltro, Pageable pageable);
    Page<ResumoLancamento> resumir(LancamentoFiltro lancamentoFiltro, Pageable pageable);
}
