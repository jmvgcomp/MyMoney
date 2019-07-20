package dev.jmvg.api.repository.lancamento;

import dev.jmvg.api.model.Lancamento;
import dev.jmvg.api.repository.filtro.LancamentoFiltro;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LancamentoRepositoryQuery {
    Page<Lancamento> filtrar(LancamentoFiltro lancamentoFiltr, Pageable pageable);
}
