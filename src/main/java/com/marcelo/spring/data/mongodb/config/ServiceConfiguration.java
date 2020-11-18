package com.marcelo.spring.data.mongodb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class ServiceConfiguration extends WebSecurityConfigurerAdapter {
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder () {
		return new BCryptPasswordEncoder();
	}
	
	   @Override
	    public void configure(HttpSecurity http) throws Exception {
	       http.csrf().disable().authorizeRequests()
	        .antMatchers("/api/usuarios").permitAll()
	        .antMatchers(HttpMethod.POST,"/api/usuarios").permitAll()
	        .antMatchers(HttpMethod.POST, "/api/usuarios").permitAll()
	        .antMatchers(HttpMethod.POST,"/api/usuarios").permitAll()
	        .antMatchers(HttpMethod.GET,"/api/usuarios").permitAll()
	        .antMatchers(HttpMethod.GET,"/api/usuarios").permitAll()
	        .antMatchers(HttpMethod.GET,"/api/cuentas").permitAll()
	        .antMatchers(HttpMethod.POST,"/api/cuentas").permitAll()
	        .antMatchers(HttpMethod.PUT,"/api/cuentas").permitAll()
	        .antMatchers(HttpMethod.DELETE,"/api/cuentas").permitAll()
	        .anyRequest().authenticated();
	    }
	
}
