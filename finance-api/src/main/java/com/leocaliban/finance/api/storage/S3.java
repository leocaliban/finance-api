package com.leocaliban.finance.api.storage;

import java.io.IOException;
import java.util.Arrays;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AccessControlList;
import com.amazonaws.services.s3.model.GroupGrantee;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.ObjectTagging;
import com.amazonaws.services.s3.model.Permission;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.Tag;
import com.leocaliban.finance.api.config.property.FinanceApiProperty;

@Component
public class S3 {

	private static final Logger logger = LoggerFactory.getLogger(S3.class);

	@Autowired
	private FinanceApiProperty property;

	@Autowired
	private AmazonS3 amazons3;

	public String salvarTemporariamente(MultipartFile arquivo) {
		AccessControlList acl = new AccessControlList();

		acl.grantPermission(GroupGrantee.AllUsers, Permission.Read);

		ObjectMetadata objectMetadata = new ObjectMetadata();
		objectMetadata.setContentType(arquivo.getContentType());
		objectMetadata.setContentLength(arquivo.getSize());

		String nomeUnico = gerarNomeUnico(arquivo.getOriginalFilename());

		try {
			PutObjectRequest putObjectRequest = new PutObjectRequest(property.getS3().getBucket(), nomeUnico,
					arquivo.getInputStream(), objectMetadata).withAccessControlList(acl);

			putObjectRequest.setTagging(new ObjectTagging(Arrays.asList(new Tag("expirar", "true"))));
			amazons3.putObject(putObjectRequest);

			if (logger.isDebugEnabled()) {
				logger.debug("Arquivo {} enviado com sucesso para o S3.", arquivo.getOriginalFilename());
			}

			return nomeUnico;
		} catch (IOException e) {
			throw new RuntimeException("Problema ao tentar enviar o arquivo para o S3.");
		}
	}

	private String gerarNomeUnico(String originalFilename) {
		return UUID.randomUUID().toString() + "_" + originalFilename;
	}

}
