package com.leocaliban.finance.api.repository.lancamento;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.util.StringUtils;

import com.leocaliban.finance.api.model.Lancamento;
import com.leocaliban.finance.api.model.Lancamento_;
import com.leocaliban.finance.api.repository.filter.LancamentoFilter;

/**
 * Classe {@link LancamentoRepositoryImpl} responsável por implementar as restrições de filtragem da pesquisa de Lançamentos.
 * @author Leocaliban
 *
 * 3 de mar de 2018
 */
public class LancamentoRepositoryImpl implements LancamentoRepositoryQuery{
	
	@PersistenceContext
	private EntityManager manager;

	@Override
	public List<Lancamento> filtrar(LancamentoFilter filter) {
		CriteriaBuilder builder = manager.getCriteriaBuilder(); 
		CriteriaQuery<Lancamento> criteria = builder.createQuery(Lancamento.class);
		
		Root<Lancamento> root = criteria.from(Lancamento.class);
		
		Predicate[] predicates = criarRestricoes(filter, builder, root);
		criteria.where(predicates);
		
		TypedQuery<Lancamento> query = manager.createQuery(criteria);
		
		return query.getResultList(); 
	}

	/**
	 * Cria os atributos da filtragem 
	 * @param filter LancamentoFilter que contém os atributos que serão filtrados
	 * @param builder construtor da query
	 * @param root que referencia a entidade filtrada na query
	 * @return lista atributos atribuídos na filtragem
	 */
	private Predicate[] criarRestricoes(LancamentoFilter filter, CriteriaBuilder builder, Root<Lancamento> root) {

		List<Predicate> predicates = new ArrayList<>();
		if(!StringUtils.isEmpty(filter.getDescricao())) {
			predicates.add(builder.like(builder.lower(root.get(Lancamento_.descricao)), "%" + filter.getDescricao().toLowerCase() + "%"));
		}
		
		if(filter.getDataVencimentoDe() != null) {
			predicates.add(builder.greaterThanOrEqualTo(root.get(Lancamento_.dataVencimento), filter.getDataVencimentoDe()));
		}
		
		if(filter.getDataVencimentoAte() != null) {
			predicates.add(builder.lessThanOrEqualTo(root.get(Lancamento_.dataVencimento), filter.getDataVencimentoAte()));
		}
		return predicates.toArray(new Predicate[predicates.size()]);
	}

}
