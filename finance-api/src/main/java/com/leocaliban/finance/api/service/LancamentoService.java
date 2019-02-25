package com.leocaliban.finance.api.service;

import java.io.InputStream;
import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.leocaliban.finance.api.dto.LancamentoEstatisticaCategoriaDTO;
import com.leocaliban.finance.api.dto.LancamentoEstatisticaDiariaDTO;
import com.leocaliban.finance.api.dto.LancamentoEstatisticaPessoaDTO;
import com.leocaliban.finance.api.mail.Mailer;
import com.leocaliban.finance.api.model.Lancamento;
import com.leocaliban.finance.api.model.Pessoa;
import com.leocaliban.finance.api.model.Usuario;
import com.leocaliban.finance.api.repository.LancamentoRepository;
import com.leocaliban.finance.api.repository.PessoaRepository;
import com.leocaliban.finance.api.repository.UsuarioRepository;
import com.leocaliban.finance.api.repository.filter.LancamentoFilter;
import com.leocaliban.finance.api.repository.projection.ResumoLancamento;
import com.leocaliban.finance.api.service.exceptions.PessoaInexistenteOuInativaException;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 * Classe {@link LancamentoService} é responsável pelas regras de negócio que envolvem Lancamento
 * @author Leocaliban
 *
 * 1 de mar de 2018
 */
@Service //Indica ao spring que essa classe pode ser injetada
public class LancamentoService {
	
	private static final String DESTINATARIOS = "ROLE_PESQUISAR_LANCAMENTO";
	
	private static final Logger logger = LoggerFactory.getLogger(LancamentoService.class);
	
	@Autowired 
	private PessoaRepository pessoaRepository;
	
	@Autowired 
	private LancamentoRepository lancamentoRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private Mailer mailer;

	/**
	 * Método que recupera por filtragem todos os Lançamentos do banco de dados através do repository.
	 * @param filter filtro da listagem de lançamentos.
	 * @return páginas de lançamentos.
	 */
	public Page<Lancamento> listar(LancamentoFilter filter, Pageable pageable){
		return lancamentoRepository.filtrar(filter, pageable);
	}
	
	/**
	 * Busca os Lançamentos do banco de dados de forma resumida.
	 * @param filter LancamentoFilter que contém os atributos que serão filtrados
	 * @param pageable paginação da listagem de lançamentos
	 * @return Páginas de lançamentos resumidos.
	 */
	public Page<ResumoLancamento> resumirListagem(LancamentoFilter filter, Pageable pageable) {
		return lancamentoRepository.resumir(filter, pageable);
	}
	
	/**
	 * Método que busca um lançamento no banco de dados.
	 * @param codigo código identificador do lançamento.
	 * @return Lancamento 
	 */
	public Lancamento buscarPorCodigo(Long codigo) {
		Lancamento lancamento = lancamentoRepository.findOne(codigo);
		if (lancamento == null) {
			throw new IllegalArgumentException();
		}
		return lancamento;
	}
	
	public List<LancamentoEstatisticaCategoriaDTO> buscarPorCategoria(){
		return this.lancamentoRepository.porCategoria(LocalDate.now());
	}
	
	public List<LancamentoEstatisticaDiariaDTO> buscarPorDia() {
		return this.lancamentoRepository.porDia(LocalDate.now());
	}
	
	public List<LancamentoEstatisticaPessoaDTO> buscarPorPessoa(LocalDate inicio, LocalDate fim) {
		return this.lancamentoRepository.porPessoa(inicio, fim);
	}
	
	public byte[] relatorioPorPessoa(LocalDate inicio, LocalDate fim) throws Exception {
		List<LancamentoEstatisticaPessoaDTO> dados = lancamentoRepository.porPessoa(inicio, fim);
		Map<String, Object> parametros = new HashMap<>();
		parametros.put("DT_INICIO", Date.valueOf(inicio));
		parametros.put("DT_FIM", Date.valueOf(fim));
		parametros.put("REPORT_LOCALE", new Locale("pt", "BR"));
		
		InputStream inputStream = this.getClass().getResourceAsStream("/relatorios/lancamentos-por-pessoa.jasper");
		JasperPrint jasperPrint = JasperFillManager.fillReport(inputStream, parametros, new JRBeanCollectionDataSource(dados));
		return JasperExportManager.exportReportToPdf(jasperPrint);
	}
	
	/**
	 * Salva um lançamento no banco de dados.
	 * @param lancamento Lançamento que será salvo.
	 * @return lancamento salvo.
	 */
	public Lancamento salvar(Lancamento lancamento) {
		Pessoa pessoa = pessoaRepository.findOne(lancamento.getPessoa().getCodigo());
		if(pessoa == null || pessoa.isInativo()) {
			throw new PessoaInexistenteOuInativaException();
		}
		Lancamento lancamentoSalvo = lancamentoRepository.save(lancamento);
		return lancamentoSalvo;
	}
	
	/**
	 * Método que edita um lançamento
	 * @param codigo código do lançamento
	 * @param lancamento lançamento editado
	 * @return lançamento salvo
	 */
	public Lancamento editar(Long codigo, Lancamento lancamento) {
		Lancamento lancamentoSalvo = buscarPorCodigo(codigo);
		if (!lancamento.getPessoa().equals(lancamentoSalvo.getPessoa())) {
			validarPessoa(lancamento);
		}

		BeanUtils.copyProperties(lancamento, lancamentoSalvo, "codigo");

		return lancamentoRepository.save(lancamentoSalvo);
	}
	
	/**
	 * Remove um lançamento no banco de dados através do repository.
	 * @param codigo Código do lançamento que será excluído.
	 */
	public void remover(Long codigo) {
		lancamentoRepository.delete(codigo);
	}
	
	/**
	 * Método verifica se a pessoa que será salva é null ou inativa
	 * @param lancamento lançamento da pessoa
	 */
	private void validarPessoa(Lancamento lancamento) {
		Pessoa pessoa = null;
		if (lancamento.getPessoa().getCodigo() != null) {
			pessoa = pessoaRepository.findOne(lancamento.getPessoa().getCodigo());
		}

		if (pessoa == null || pessoa.isInativo()) {
			throw new PessoaInexistenteOuInativaException();
		}
	}
	
	//@Scheduled(fixedDelay = 1000 * 60 * 30) 
	//@Scheduled(cron = "0 0 6 * * *")
	public void notificarLancamentosVencidos() {
		if(logger.isDebugEnabled()) {
			logger.debug("Preparando envio e-mails de Lançamentos vencidos.");
		}
		List<Lancamento>vencidos = lancamentoRepository.findByDataVencimentoLessThanEqualAndDataPagamentoIsNull(LocalDate.now());
		
		if(vencidos.isEmpty()) {
			logger.info("Sem lançamentos vencidos");
			return;
		}
		
		logger.info("Existem {} lançamentos vencidos.", vencidos.size());
		List<Usuario>destinatarios = usuarioRepository.findByPermissoesDescricao(DESTINATARIOS);
		
		if(destinatarios.isEmpty()) {
			logger.warn("Existem lançamentos vencidos, mas o sistema não encontrou destinatários.");
			return;
		}
		mailer.notificarLancamentosVencidos(vencidos, destinatarios);
		logger.info("O e-mail de Lançamentos vencidos foi enviado com sucesso!");
	}

	
}
