package dev.jmvg.api.repository.lancamento;

import dev.jmvg.api.model.Lancamento;
import dev.jmvg.api.repository.filtro.LancamentoFiltro;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class LancamentoRepositoryImpl implements LancamentoRepositoryQuery {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<Lancamento> filtrar(LancamentoFiltro lancamentoFiltro) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Lancamento> criteria = builder.createQuery(Lancamento.class);
        Root<Lancamento> root = criteria.from(Lancamento.class);

        Predicate[] predicates = criarRestricoes(lancamentoFiltro, builder, root);
        criteria.where(predicates);

        TypedQuery<Lancamento> query = manager.createQuery(criteria);
        return query.getResultList();
    }

    private Predicate[] criarRestricoes(LancamentoFiltro lancamentoFiltro, CriteriaBuilder builder, Root<Lancamento> root) {
        List<Predicate> predicates = new ArrayList<>();
        if(!StringUtils.isEmpty(lancamentoFiltro.getDescricao())){
            predicates.add(builder.like(
                    builder.lower(root.get("descricao")), "%" + lancamentoFiltro.getDescricao().toLowerCase() +"%")
            );
        }

        return predicates.toArray(new Predicate[predicates.size()]);
    }
}
