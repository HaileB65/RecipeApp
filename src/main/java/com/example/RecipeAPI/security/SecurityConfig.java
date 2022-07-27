package com.example.RecipeAPI.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //disable CSRF for Postman usage
                .csrf().disable()
                //all requests require authorization
                .authorizeRequests()
                //allow all requests to read recipes and reviews
                .antMatchers(HttpMethod.GET, "/recipes/**", "/review/**").permitAll()
                //allow all requests to create a user account or post review
                .antMatchers(HttpMethod.POST, "/user").permitAll()
                //allow creation of new recipes only by authenticated users
                .antMatchers(HttpMethod.POST, "/recipes", "/review/**").authenticated()
                //all other requests should be authenticated
                .anyRequest().authenticated()
                .and()
                //users should log in using HTTP Basic authentication
                .httpBasic();
    }

    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //use a customUserDetailService and a BCryptPasswordEncoder to set up an AuthenticationManager.
        auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
    }
}
