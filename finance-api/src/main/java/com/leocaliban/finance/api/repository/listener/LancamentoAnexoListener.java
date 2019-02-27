package com.leocaliban.finance.api.repository.listener;

import javax.persistence.PostLoad;

import org.springframework.util.StringUtils;

import com.leocaliban.finance.api.FinanceApiApplication;
import com.leocaliban.finance.api.model.Lancamento;
import com.leocaliban.finance.api.storage.S3;

public class LancamentoAnexoListener {

	@PostLoad
	public void postLoad(Lancamento lancamento) {
		if(StringUtils.hasText(lancamento.getAnexo())) {
			S3 s3 = FinanceApiApplication.getBean(S3.class);
			lancamento.setUrlAnexo(s3.configurarUrl(lancamento.getAnexo()));
		}
	}
}
