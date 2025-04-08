package io.github.spring_boot_studies.arquiteturaSpring;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

@SpringBootApplication
@EnableConfigurationProperties // Habilita a configuração personalizada criada no application.yml
public class Application {

	public static void main(String[] args) {
		//SpringApplication.run(Application.class, args);
		SpringApplicationBuilder builder = new SpringApplicationBuilder(Application.class);
		builder.bannerMode(Banner.Mode.OFF); // Banner Spring boot no terminal
		builder.profiles("producao", "homologacao"); // Ativa os perfis de produção e homologação
//	builder.lazyInitialization(true); // Ativa a inicialização preguiçosa dos beans
		builder.run(args);

		// Contexto da aplicação já iniciada
		ConfigurableApplicationContext applicationContext = builder.context();
		//var produtoRepository = applicationContext.getBean("produtoRepository");

		ConfigurableEnvironment environment = applicationContext.getEnvironment();
		String applicationName = environment.getProperty("spring.application.name");
		System.out.println("Nome da aplicação: " + applicationName);

		ExampleValue value = applicationContext.getBean("exampleValue", ExampleValue.class);
		value.printVariable();

		AppProperties properties = applicationContext.getBean(AppProperties.class); // Classe de configuração personalizada criada no application.yml
		System.out.println("Valor de value1: " + properties.getValue1());
	}
}
