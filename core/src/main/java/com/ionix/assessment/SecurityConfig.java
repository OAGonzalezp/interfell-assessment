package com.ionix.assessment;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${appUser}")
    private String user;
    @Value("${appPassword}")
    private String password;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Use BCryptPasswordEncoder for better security
    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser(user)
                .password(passwordEncoder().encode(password))
                .roles("USER");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
/*
        http.authorizeRequests()
                .antMatchers("/api/**").authenticated()
                .and()
                .httpBasic();
*/
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/v0/secure/user/**").authenticated() // Secure POST and DELETE endpoints
                .anyRequest().permitAll() // Allow all other requests without authentication
                .and()
                .httpBasic();
    }
}