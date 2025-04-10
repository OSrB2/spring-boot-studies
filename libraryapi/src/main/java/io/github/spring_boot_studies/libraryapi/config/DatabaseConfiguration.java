package io.github.spring_boot_studies.libraryapi.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfiguration {

  // As propriedades do banco de dados são definidas no arquivo application.yml ou application.properties
  @Value("${spring.datasource.url}")
  String url;
  @Value("${spring.datasource.username}")
  String username;
  @Value("${spring.datasource.password}")
  String password;
  @Value("${spring.datasource.driver-class-name}")
  String driver;

  // O DriverManagerDataSource é o mais simples e fácil de usar, mas não é recomendado para produção
//  @Bean
//  public DataSource dataSource() {
//    DriverManagerDataSource ds = new DriverManagerDataSource();
//    ds.setUrl(url);
//    ds.setUsername(username);
//    ds.setPassword(password);
//    ds.setDriverClassName(driver);
//    return ds;
//  }

  // O Hikari é o mais recomendado para produção e para a criação de um pool de conexões
  // DOCS -> https://github.com/brettwooldridge/HikariCP
  @Bean
  public DataSource hikariDataSource() {
    HikariConfig config = new HikariConfig();
    config.setUsername(username);
    config.setPassword(password);
    config.setDriverClassName(driver);
    config.setJdbcUrl(url);

    // Essas propriedades são importantes para evitar que o banco de dados fique sobrecarregado
    config.setMaximumPoolSize(10); // Tamanho maximo do pool de conexões liberadas
    config.setMinimumIdle(1); // Tamanho iniicial do pool
    config.setPoolName("library-db-pool"); // Nome do pool de conexões
    config.setMaxLifetime(600000); // Tempo maximo de vida de uma conexão, tempo em milissegundos, nesse caso 10 minutos
    config.setConnectionTimeout(100000); // Tempo maximo de espera para uma conexão ser liberada, tempo em milissegundos
    config.setConnectionTestQuery("select 1"); // Query para testar a conexão, nesse caso é uma query simples que retorna 1

    return new HikariDataSource(config);
  }
}
