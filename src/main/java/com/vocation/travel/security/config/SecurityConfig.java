package com.vocation.travel.security.config;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import com.vocation.travel.security.AuthenticationFailureHandler;
import com.vocation.travel.security.FilterService;
import com.vocation.travel.security.JpaUserDetailsService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@Log4j2
public class SecurityConfig {
    @Autowired
    private RsaKeyConfigProperties rsaKeyConfigProperties;

    @Autowired
    private JpaUserDetailsService userDetailsService;

    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    /**
     * Set value provider manager security.
     *
     * @return AuthenticationManager
     * */
    @Bean
    public AuthenticationManager authManager() {
        var authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(authProvider);
    }

    /**
     * Filter security.
     *
     * @param http HttpSecurity
     * @param introspector HandlerMappingIntrospector
     * @return SecurityFilterChain
     * */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception {
        return http
                .addFilterBefore(new FilterService(), UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/auth/*").permitAll();
                    auth.anyRequest().authenticated();
                })
                .csrf(csrf -> {csrf.disable();})
                .cors(cors -> cors.disable())
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt.decoder(jwtDecoder())))
                .userDetailsService(userDetailsService)
                .httpBasic(Customizer.withDefaults())
                .exceptionHandling(e -> e.authenticationEntryPoint(authenticationFailureHandler))
                .build();
    }

    /**
     * Decoder jwt.
     *
     * @return JwtDecoder
     * */
    @Bean
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withPublicKey(rsaKeyConfigProperties.publicKey()).build();
    }

    /**
     * Encoder jwt.
     *
     * @return JwtEncoder
     * */
    @Bean
    JwtEncoder jwtEncoder() {
        JWK jwk = new RSAKey.Builder(rsaKeyConfigProperties.publicKey()).privateKey(rsaKeyConfigProperties.privateKey()).build();

        JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwks);
    }

    /**
     * Password encoder.
     *
     * @return PasswordEncoder
     * */
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
