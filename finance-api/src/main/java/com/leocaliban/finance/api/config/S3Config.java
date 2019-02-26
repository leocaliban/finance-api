package com.leocaliban.finance.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.leocaliban.finance.api.config.property.FinanceApiProperty;

@Configuration
public class S3Config {

	@Autowired
	private FinanceApiProperty property;

	@Bean
	private AmazonS3 amazonS3() {
		AWSCredentials credenciais = new BasicAWSCredentials(property.getS3().getAccessKeyId(),
				property.getS3().getSecretAccessKey());

		AmazonS3 amazons3 = AmazonS3ClientBuilder.standard()
				.withCredentials(new AWSStaticCredentialsProvider(credenciais)).build();
		return amazons3;
	}
}
