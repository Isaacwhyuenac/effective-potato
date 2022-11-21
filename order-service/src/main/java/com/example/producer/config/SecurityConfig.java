package com.example.producer.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.oauth2.jwt.JwtValidators;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
@Configuration
public class SecurityConfig {

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

    // @formatter:off
   http
     .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
     .and()
     .csrf().disable()
        .authorizeRequests()
        .antMatchers("/transaction/**").authenticated()
//     .mvcMatchers("/transaction").permitAll()

     .mvcMatchers("/v3/api-docs").permitAll()
     .and()
        .oauth2ResourceServer()
        .jwt();

   // @formatter:on

    return http.build();
  }

  @Value("${auth0.audience}")
  private String audience;

  @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
  private String issuer;

  @Bean
  public JwtDecoder jwtDecoder() {
    NimbusJwtDecoder jwtDecoder = JwtDecoders.fromOidcIssuerLocation(issuer);

    OAuth2TokenValidator<Jwt> audienceValidator = new AudienceValidator(audience);
    OAuth2TokenValidator<Jwt> withIssuer = JwtValidators.createDefaultWithIssuer(issuer);
    OAuth2TokenValidator<Jwt> withAudience = new DelegatingOAuth2TokenValidator<>(withIssuer, audienceValidator);

    jwtDecoder.setJwtValidator(withAudience);

    return jwtDecoder;
  }

}

// @EnableWebSecurity
// public class SecurityConfig extends WebSecurityConfigurerAdapter {

//   @Value("${auth0.audience}")
//   private String audience;

//   @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
//   private String issuer;

//   @Override
//   protected void configure(HttpSecurity http) throws Exception {
//     http.csrf().disable()
//       .authorizeRequests()
//       .anyRequest().authenticated()
//       .and()
//       .oauth2ResourceServer().jwt();
//   }

//   @Bean
//   public JwtDecoder jwtDecoder() {
//     NimbusJwtDecoder jwtDecoder = JwtDecoders.fromOidcIssuerLocation(issuer);

//     OAuth2TokenValidator<Jwt> audienceValidator = new AudienceValidator(audience);
//     OAuth2TokenValidator<Jwt> withIssuer = JwtValidators.createDefaultWithIssuer(issuer);
//     OAuth2TokenValidator<Jwt> withAudience = new DelegatingOAuth2TokenValidator<>(withIssuer, audienceValidator);

//     jwtDecoder.setJwtValidator(withAudience);

//     return jwtDecoder;
//   }
// }


