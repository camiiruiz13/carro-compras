package com.camilo.ucc.edu.co.carrocomprasthymeleaf.app.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ResourceServerConfig  {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests((authz) -> authz
                                .requestMatchers("/**").permitAll()
                        //.requestMatchers("/ver/**").hasAnyRole("USER")
                        //.requestMatchers("/uploads/**").hasAnyRole("USER")
                        //.requestMatchers("/form/**").hasAnyRole("ADMIN")
                        //.requestMatchers("/eliminar/**").hasAnyRole("ADMIN")
                        //.requestMatchers("/factura/**").hasAnyRole("ADMIN")
                        //.anyRequest().authenticated()
                )/*.formLogin(form -> form.successHandler(successHandler)
                        .loginPage("/login")
                        .permitAll())
                .logout((logout) -> logout.permitAll()).
                exceptionHandling(acces -> acces.accessDeniedPage("/error_403"))*/;

        return http.build();

    }

}
