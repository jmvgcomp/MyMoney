package dev.jmvg.api.repository.lancamento;

import dev.jmvg.api.model.Lancamento;
import dev.jmvg.api.model.Lancamento_;
import dev.jmvg.api.repository.filtro.LancamentoFiltro;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
    public Page<Lancamento> filtrar(LancamentoFiltro lancamentoFiltro, Pageable pageable) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Lancamento> criteria = builder.createQuery(Lancamento.class);
        Root<Lancamento> root = criteria.from(Lancamento.class);

        Predicate[] predicates = criarRestricoes(lancamentoFiltro, builder, root);
        criteria.where(predicates);

        TypedQuery<Lancamento> query = manager.createQuery(criteria);
        adicionarRestricoesPaginacao(query, pageable);
        return new PageImpl<>(query.getResultList(), pageable, total(lancamentoFiltro));
    }


    private Predicate[] criarRestricoes(LancamentoFiltro lancamentoFiltro, CriteriaBuilder builder, Root<Lancamento> root) {
        List<Predicate> predicates = new ArrayList<>();
        if(!StringUtils.isEmpty(lancamentoFiltro.getDescricao())){
            predicates.add(builder.like(
                    builder.lower(root.get(Lancamento_.descricao)), "%" + lancamentoFiltro.getDescricao().toLowerCase() +"%")
            );
        }
        if (lancamentoFiltro.getDataVencimentoDe() != null) {
            predicates.add(
              builder.greaterThanOrEqualTo(root.get(Lancamento_.dataVencimento), lancamentoFiltro.getDataVencimentoDe())
            );
        }
        if(lancamentoFiltro.getDataVencimentoAte()!=null){
            predicates.add(
              builder.lessThanOrEqualTo(root.get(Lancamento_.dataVencimento), lancamentoFiltro.getDataVencimentoAte())
            );
        }


        return predicates.toArray(new Predicate[predicates.size()]);
    }

    private void adicionarRestricoesPaginacao(TypedQuery<Lancamento> query, Pageable pageable) {
        int paginaAtual = pageable.getPageNumber();
        int totalRegistroPaginas = pageable.getPageSize();
        int primeiroRegistroPagina = paginaAtual * totalRegistroPaginas;

        query.setFirstResult(primeiroRegistroPagina);
        query.setMaxResults(totalRegistroPaginas);
    }

    private Long total(LancamentoFiltro lancamentoFiltro) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
        Root<Lancamento> root = criteria.from(Lancamento.class);

        Predicate[] predicates = criarRestricoes(lancamentoFiltro, builder, root);

        criteria.where(predicates);
        criteria.select(builder.count(root));
        return manager.createQuery(criteria).getSingleResult();
    }
}
