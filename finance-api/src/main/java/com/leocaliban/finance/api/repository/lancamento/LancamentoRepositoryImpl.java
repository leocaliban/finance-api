package com.leocaliban.finance.api.repository.lancamento;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import com.leocaliban.finance.api.dto.LancamentoEstatisticaCategoriaDTO;
import com.leocaliban.finance.api.dto.LancamentoEstatisticaDiariaDTO;
import com.leocaliban.finance.api.dto.LancamentoEstatisticaPessoaDTO;
import com.leocaliban.finance.api.model.Categoria_;
import com.leocaliban.finance.api.model.Lancamento;
import com.leocaliban.finance.api.model.Lancamento_;
import com.leocaliban.finance.api.model.Pessoa_;
import com.leocaliban.finance.api.repository.filter.LancamentoFilter;
import com.leocaliban.finance.api.repository.projection.ResumoLancamento;

/**
 * Classe {@link LancamentoRepositoryImpl} responsável por implementar as
 * restrições de filtragem da pesquisa de Lançamentos.
 * 
 * @author Leocaliban
 *
 *         3 de mar de 2018
 */
public class LancamentoRepositoryImpl implements LancamentoRepositoryQuery {

	@PersistenceContext
	private EntityManager manager;

	@Override
	public Page<Lancamento> filtrar(LancamentoFilter filter, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Lancamento> criteria = builder.createQuery(Lancamento.class);

		Root<Lancamento> root = criteria.from(Lancamento.class); // onde está sendo feito a pesquisa

		Predicate[] predicates = criarRestricoes(filter, builder, root);
		criteria.where(predicates);

		TypedQuery<Lancamento> query = manager.createQuery(criteria);

		adicionarRestricoesDePaginacao(query, pageable);

		return new PageImpl<>(query.getResultList(), pageable, total(filter));
	}

	@Override
	public Page<ResumoLancamento> resumir(LancamentoFilter filter, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<ResumoLancamento> criteria = builder.createQuery(ResumoLancamento.class);

		Root<Lancamento> root = criteria.from(Lancamento.class); // onde está sendo feito a pesquisa

		criteria.select(builder.construct(ResumoLancamento.class, root.get(Lancamento_.codigo),
				root.get(Lancamento_.descricao), root.get(Lancamento_.dataVencimento),
				root.get(Lancamento_.dataPagamento), root.get(Lancamento_.valor), root.get(Lancamento_.tipo),
				root.get(Lancamento_.categoria).get(Categoria_.nome), root.get(Lancamento_.pessoa).get(Pessoa_.nome)));

		Predicate[] predicates = criarRestricoes(filter, builder, root);
		criteria.where(predicates);

		TypedQuery<ResumoLancamento> query = manager.createQuery(criteria);

		adicionarRestricoesDePaginacao(query, pageable);

		return new PageImpl<>(query.getResultList(), pageable, total(filter));
	}

	/**
	 * Cria os atributos da filtragem
	 * 
	 * @param filter
	 *            LancamentoFilter que contém os atributos que serão filtrados
	 * @param builder
	 *            construtor da query
	 * @param root
	 *            que referencia a entidade filtrada na query
	 * @return lista atributos atribuídos na filtragem
	 */
	private Predicate[] criarRestricoes(LancamentoFilter filter, CriteriaBuilder builder, Root<Lancamento> root) {

		List<Predicate> predicates = new ArrayList<>();
		if (!StringUtils.isEmpty(filter.getDescricao())) {
			predicates.add(builder.like(builder.lower(root.get(Lancamento_.descricao)),
					"%" + filter.getDescricao().toLowerCase() + "%"));
		}

		if (filter.getDataVencimentoDe() != null) {
			predicates.add(
					builder.greaterThanOrEqualTo(root.get(Lancamento_.dataVencimento), filter.getDataVencimentoDe()));
		}

		if (filter.getDataVencimentoAte() != null) {
			predicates.add(
					builder.lessThanOrEqualTo(root.get(Lancamento_.dataVencimento), filter.getDataVencimentoAte()));
		}
		return predicates.toArray(new Predicate[predicates.size()]);
	}

	/**
	 * Adiciona restrições de paginação na pesquisa filtrada
	 * 
	 * @param query
	 *            consulta
	 * @param pageable
	 *            paginação
	 */
	private void adicionarRestricoesDePaginacao(TypedQuery<?> query, Pageable pageable) {
		int paginaAtual = pageable.getPageNumber();
		int totalRegistroPorPagina = pageable.getPageSize();
		int primeiroRegistroDaPagina = paginaAtual * totalRegistroPorPagina;

		query.setFirstResult(primeiroRegistroDaPagina);
		query.setMaxResults(totalRegistroPorPagina);
	}

	/**
	 * Define a quantidade de elementos para o filtro.
	 * 
	 * @param filter
	 *            contém os atributos que serão filtrados.
	 * @return Long número de elementos.
	 */
	private Long total(LancamentoFilter filter) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);

		Root<Lancamento> root = criteria.from(Lancamento.class); // onde está sendo feito a pesquisa

		Predicate[] predicates = criarRestricoes(filter, builder, root);
		criteria.where(predicates);

		// count - select * from lancamento
		criteria.select(builder.count(root));
		return manager.createQuery(criteria).getSingleResult();
	}

	@Override
	public List<LancamentoEstatisticaCategoriaDTO> porCategoria(LocalDate mesReferencia) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<LancamentoEstatisticaCategoriaDTO> criteria = builder
				.createQuery(LancamentoEstatisticaCategoriaDTO.class);

		Root<Lancamento> root = criteria.from(Lancamento.class);

		criteria.select(builder.construct(LancamentoEstatisticaCategoriaDTO.class, root.get(Lancamento_.categoria),
				builder.sum(root.get(Lancamento_.valor))));

		LocalDate primeiroDia = mesReferencia.withDayOfMonth(1);
		LocalDate ultimoDia = mesReferencia.withDayOfMonth(mesReferencia.lengthOfMonth());

		criteria.where(builder.greaterThanOrEqualTo(root.get(Lancamento_.dataVencimento), primeiroDia),
				builder.lessThanOrEqualTo(root.get(Lancamento_.dataVencimento), ultimoDia));

		criteria.groupBy(root.get(Lancamento_.categoria));

		TypedQuery<LancamentoEstatisticaCategoriaDTO> typedQuery = manager.createQuery(criteria);
		return typedQuery.getResultList();
	}

	@Override
	public List<LancamentoEstatisticaDiariaDTO> porDia(LocalDate mesReferencia) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<LancamentoEstatisticaDiariaDTO> criteria = builder
				.createQuery(LancamentoEstatisticaDiariaDTO.class);

		Root<Lancamento> root = criteria.from(Lancamento.class);

		criteria.select(builder.construct(LancamentoEstatisticaDiariaDTO.class, 
				root.get(Lancamento_.tipo),
				root.get(Lancamento_.dataVencimento),
				builder.sum(root.get(Lancamento_.valor))));

		LocalDate primeiroDia = mesReferencia.withDayOfMonth(1);
		LocalDate ultimoDia = mesReferencia.withDayOfMonth(mesReferencia.lengthOfMonth());

		criteria.where(builder.greaterThanOrEqualTo(root.get(Lancamento_.dataVencimento), primeiroDia),
				builder.lessThanOrEqualTo(root.get(Lancamento_.dataVencimento), ultimoDia));

		criteria.groupBy(root.get(Lancamento_.tipo),root.get(Lancamento_.dataVencimento));

		TypedQuery<LancamentoEstatisticaDiariaDTO> typedQuery = manager.createQuery(criteria);
		return typedQuery.getResultList();
	}
	
	@Override
	public List<LancamentoEstatisticaPessoaDTO> porPessoa(LocalDate inicio, LocalDate fim) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<LancamentoEstatisticaPessoaDTO> criteria = builder
				.createQuery(LancamentoEstatisticaPessoaDTO.class);

		Root<Lancamento> root = criteria.from(Lancamento.class);

		criteria.select(builder.construct(LancamentoEstatisticaPessoaDTO.class, 
				root.get(Lancamento_.tipo),
				root.get(Lancamento_.pessoa),
				builder.sum(root.get(Lancamento_.valor))));

		criteria.where(builder.greaterThanOrEqualTo(root.get(Lancamento_.dataVencimento), inicio),
				builder.lessThanOrEqualTo(root.get(Lancamento_.dataVencimento), fim));

		criteria.groupBy(root.get(Lancamento_.tipo),root.get(Lancamento_.pessoa));

		TypedQuery<LancamentoEstatisticaPessoaDTO> typedQuery = manager.createQuery(criteria);
		return typedQuery.getResultList();
	}

}
