package io.submanager;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@EnableScheduling
@EnableRetry
public class SubManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SubManagerApplication.class, args);
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public StringEncryptor credentialEncryptor() {
		PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();

		// TODO extract key to properties
		SimpleStringPBEConfig config = new SimpleStringPBEConfig();
		config.setPassword("abc");
		config.setAlgorithm("PBEWithMD5AndDES");
		config.setKeyObtentionIterations("1000");
		config.setPoolSize("1");
		config.setProviderName("SunJCE");
		config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
		config.setStringOutputType("base64");

		encryptor.setConfig(config);

		return encryptor;
	}
}
