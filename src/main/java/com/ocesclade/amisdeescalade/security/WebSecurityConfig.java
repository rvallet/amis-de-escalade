package com.ocesclade.amisdeescalade.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	private DataSource dataSource;

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		auth.jdbcAuthentication().dataSource(dataSource)
			.usersByUsernameQuery("SELECT username as principal, password as credentials," + " enabled FROM user WHERE username=?")
			.authoritiesByUsernameQuery("SELECT username as principal," + " role as role FROM user WHERE username=?")
			.rolePrefix("ROLE_");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.formLogin().loginPage("/identification");
		http.authorizeRequests().antMatchers("/").permitAll();
		http.authorizeRequests().antMatchers("/accueil").permitAll();
		http.authorizeRequests().antMatchers("/autre").hasAnyRole("USER","ADMIN");
	}
	
}
