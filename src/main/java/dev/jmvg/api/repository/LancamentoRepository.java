package dev.jmvg.api.repository;

import dev.jmvg.api.model.Lancamento;
import dev.jmvg.api.repository.lancamento.LancamentoRepositoryQuery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long>, LancamentoRepositoryQuery {
}
