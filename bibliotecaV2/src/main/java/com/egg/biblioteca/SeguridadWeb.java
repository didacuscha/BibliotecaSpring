package com.egg.biblioteca;


import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;

import com.egg.biblioteca.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
@EnableWebSecurity
//@EnableMethodSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SeguridadWeb {

    @Autowired
    private UsuarioServicio usuarioServicio;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(usuarioServicio)
                .passwordEncoder(new BCryptPasswordEncoder());
    }

    @Bean
    SecurityFilterChain web(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/admin/*").hasRole("ADMIN")
                        .requestMatchers("/css/*", "/js/*", "/img/*", "/**")
                        //.requestMatchers("/css/*", "/js/*", "/img/*", "/inicio", "/", "/error", "/logout", "/perfil", "libro/lista","editorial/lista", "autor/lista").hasRole("USER")
                        //.requestMatchers("/css/*", "/js/*", "/img/*", "/registro", "/registrar", "/error", "/", "/inicio")
                        //.requestMatchers(new AntPathRequestMatcher("/**")).hasRole("ADMIN")
                        //.requestMatchers(new AntPathRequestMatcher("/**")).hasRole("USER")
                        //.requestMatchers("/css/*", "/js/*", "/img/*", "/inicio", "/", "/error", "/logout", "/perfil", "libro/lista","editorial/lista", "autor/lista").hasRole("USER")
                        //.requestMatchers("/css/*", "/js/*", "/img/*", "/registro", "/login", "/registrar", "/error")
                        //.requestMatchers("/css/*", "/js/*", "/img/*", "/registro", "/login", "/registrar")
                        //.requestMatchers("/css/*", "/js/*", "/img/*", "/registro", "/registrar", "/error")
                        .permitAll()
                )
                /*
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/css/*", "/js/*", "/img/*", "/inicio", "/", "/error", "/logout", "/perfil", "libro/lista","editorial/lista", "autor/lista", "/logincheck").hasRole("USER")
                        .requestMatchers("/user/*")
                        .permitAll()
                )
                 */
                .formLogin(login -> login
                        .loginPage("/login")
                        .loginProcessingUrl("/logincheck")
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/inicio")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login")
                        .permitAll()
                ).csrf(AbstractHttpConfigurer::disable);


        return http.build();
    }



}

