package com.marcelo.spring.data.mongodb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
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
	        .antMatchers("/usuarios").permitAll()
	        .antMatchers(HttpMethod.GET,"/usuarios").permitAll()
	        .antMatchers(HttpMethod.GET,"/usuarios/{id}").permitAll()
	        .antMatchers(HttpMethod.POST, "/usuarios").permitAll()
	        .antMatchers(HttpMethod.PUT,"/usuarios/{id}").permitAll()
	        .antMatchers(HttpMethod.DELETE,"/usuarios/{id}").permitAll()
	        .antMatchers(HttpMethod.DELETE,"/usuarios").permitAll()
	        .antMatchers(HttpMethod.GET,"/cuentas").permitAll()
	        .antMatchers(HttpMethod.GET,"/cuentas/{id}").permitAll()
	        .antMatchers(HttpMethod.POST,"/cuentas").permitAll()
	        .antMatchers(HttpMethod.PUT,"/cuentas/{id}").permitAll()
	        .antMatchers(HttpMethod.DELETE,"/cuentas/{id}").permitAll()
	        .antMatchers(HttpMethod.DELETE,"/cuentas").permitAll()
	        .anyRequest().authenticated();
	   }
	   
	   @Override
	   public void configure (WebSecurity web) throws Exception {
		   	web.ignoring().antMatchers("/v2/api-docs/**");
		    web.ignoring().antMatchers("/swagger.json");
		    web.ignoring().antMatchers("/swagger-ui.html");
		    web.ignoring().antMatchers("/swagger-resources/**");
		    web.ignoring().antMatchers("/webjars/**");
	   }
}
