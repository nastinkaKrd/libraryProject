package com.library.project.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.library.project.models.UserRoles.ROLE_ADMIN;
import static com.library.project.models.UserRoles.ROLE_LIBRARIAN;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class Security {
    private final JwtRequestFilter jwtRequestFilter;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .formLogin()
                //.loginPage("/login.html")
                //.loginProcessingUrl("/api/v1/auth/authenticate2")
                //.successForwardUrl(u)
                .defaultSuccessUrl("/hello", true)
                .and()
                .authorizeHttpRequests()
                .requestMatchers("/api/v1/auth/**", "hello")
                .permitAll()
                //.requestMatchers(HttpMethod.POST, "/elements").hasAnyRole(ROLE_LIBRARIAN.name(), ROLE_ADMIN.name())
                //.requestMatchers(HttpMethod.DELETE, "/elements/**").hasAnyRole(ROLE_LIBRARIAN.name(), ROLE_ADMIN.name())
               // .requestMatchers(HttpMethod.PUT, "/elements/change_status").hasAnyRole(ROLE_LIBRARIAN.name(), ROLE_ADMIN.name())
               // .requestMatchers(HttpMethod.POST, "/readers").hasAnyRole(ROLE_LIBRARIAN.name(), ROLE_ADMIN.name())
               // .requestMatchers(HttpMethod.DELETE, "/readers").hasAnyRole(ROLE_LIBRARIAN.name(), ROLE_ADMIN.name())
                .anyRequest()
                .authenticated()
                .and()
                .logout()
                .deleteCookies("JSESSIONID")
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
