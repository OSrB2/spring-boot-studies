package io.github.spring_boot_studies.libraryapi.config;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSetSource;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import io.github.spring_boot_studies.libraryapi.security.CustomAuthentication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.security.web.SecurityFilterChain;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Duration;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

// Essa classe é responsável por configurar o servidor de autorização
// Ela define as configurações de token, como o formato do token e o tempo de expiração

@Configuration
@EnableWebSecurity
public class AuthorizationServerConfiguration {

  @Bean
  @Order(1) // Define a ordem de execução do filtro de segurança
  public SecurityFilterChain authServerSecurityFilterChain(HttpSecurity httpSecurity) throws Exception { // Método para configurar o filtro de segurança do servidor de autorização
    OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(httpSecurity); // Aplica a configuração padrão de segurança do servidor de autorização

    httpSecurity.getConfigurer(OAuth2AuthorizationServerConfigurer.class)
        .oidc(Customizer.withDefaults()); // Habilita o suporte a OpenID Connect (OIDC)

    httpSecurity.oauth2ResourceServer(oauth2ResourceServer ->
        oauth2ResourceServer.jwt(Customizer.withDefaults())); // Configura o servidor de recursos OAuth2 para usar JWT como formato de token

    httpSecurity.formLogin(configurer -> configurer.loginPage("/login")); // Configura a página de login personalizada

    return httpSecurity.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(10); // Define um bean do tipo PasswordEncoder que utiliza o algoritmo BCrypt para codificar senhas.
  }

  // Essa configuração define o formato do token como JWT e o tempo de expiração do token de acesso como 60 minutos
  @Bean
  public TokenSettings tokenSettings() {
    return TokenSettings.builder()
        .accessTokenFormat(OAuth2TokenFormat.SELF_CONTAINED)
        // access_token: token utilizado nas requisições
        .accessTokenTimeToLive(Duration.ofMinutes(60))
        // refresh_token: token utilizado para renovar o access_token
        .refreshTokenTimeToLive(Duration.ofMinutes(90))
        .build();
  }

  // Essa configuração define as configurações do cliente, como a necessidade de consentimento de autorização
  @Bean
  public ClientSettings clientSettings() {
    return ClientSettings.builder()
        .requireAuthorizationConsent(false) // Se o cliente precisa de consentimento de autorização
        .build();
  }

  // JWK -> JSON Web Key: é um padrão aberto (RFC 7517) que define um formato de chave pública para uso em JSON Web Tokens (JWTs).
  // Ele permite que as chaves públicas sejam representadas em um formato JSON, facilitando a troca de chaves entre diferentes partes.
  @Bean
  public JWKSource<SecurityContext> jwkSource() throws Exception {
    RSAKey rsaKey = generateRsaKey(); // Gera uma nova chave RSA
    JWKSet jwkSet = new JWKSet(rsaKey); // Cria um novo conjunto de chaves JWK
    return new ImmutableJWKSet<>(jwkSet);
  }

  // Gerar par de chaves RSA
  private RSAKey generateRsaKey() throws Exception {
    KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA"); // Cria um gerador de chaves RSA

    keyPairGenerator.initialize(2048); // Inicializa o gerador de chaves com um tamanho de chave de 2048 bits
    KeyPair keyPair = keyPairGenerator.generateKeyPair(); // Gera uma par de chaves pública e privada.

    RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic(); // Obtém a chave pública RSA
    RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate(); // Obtém a chave privada RSA

    return new RSAKey
        .Builder(publicKey)
        .privateKey(privateKey)
        .keyID(UUID.randomUUID().toString())
        .build(); // Cria uma nova chave RSA com a chave pública e privada geradas
  }

  // Esse bean é responsável por decodificar o JWT usando a chave pública gerada anteriormente
  @Bean
  public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
    return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
  }

  // esse AuthorizationServerSettings é utilizado para definir as configurações do servidor de autorização
  @Bean
  public AuthorizationServerSettings authorizationServerSettings() {
    return AuthorizationServerSettings.builder()
        // obter token
        .tokenEndpoint("/oauth2/token")
        // Utilizado para consultar status do token
        .tokenIntrospectionEndpoint("/oauth2/introspect")
        // utilizado para revogar o token
        .tokenRevocationEndpoint("/oauth2/revoke")
        // Authorization endpoint
        .authorizationEndpoint("/oauth2/authorize")
        // Obter informações do usuário OPEN ID CONNECT
        .oidcUserInfoEndpoint("/oidc/userinfo")
        // Obter a chave publica para verificar assinatura do token
        .jwkSetEndpoint("/oahtu2/jwks")
        // Endpoint para fazer o logout
        .oidcLogoutEndpoint("/oidc/logout")
        .build();
  }


  // Esse bean é responsável por personalizar o token JWT gerado pelo servidor de autorização
  @Bean
  public OAuth2TokenCustomizer<JwtEncodingContext> tokenCustomizer() {
    return context -> {
      var principal = context.getPrincipal(); // Obtém o principal (usuário autenticado) do contexto

      if (principal instanceof CustomAuthentication authentication) {
        OAuth2TokenType tokenType = context.getTokenType();

        if (OAuth2TokenType.ACCESS_TOKEN.equals(tokenType)) {
          Collection<GrantedAuthority> authorities = authentication.getAuthorities();
          List<String> authoritiesList = authorities.stream().map(GrantedAuthority::getAuthority).toList();
          context
              .getClaims()
              .claim("authorities", authoritiesList)
              .claim("email", authentication.getUser().getEmail());
        }
      }
    };
  }
}
