package io.github.matskira.config;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
public class InternacionalizacaoConfig {

	@Bean
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("classpath:messages");
		messageSource.setDefaultEncoding("ISO-8859-1");
		messageSource.setDefaultLocale(Locale.getDefault());
		
		return messageSource;
	}
	
	@Bean
	public LocalValidatorFactoryBean validadorFactory() {
		LocalValidatorFactoryBean facBean = new LocalValidatorFactoryBean();
		facBean.setValidationMessageSource(messageSource());
		return facBean;
	}
}
