package com.leaf.eexamen.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.leaf.eexamen.serviceImpl.CustomUserDetailService;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private CustomUserDetailService userDetailsService;
	@Autowired 
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService)
		.passwordEncoder(bCryptPasswordEncoder);		
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
        http
			.authorizeRequests()
			.antMatchers("/").permitAll()
			.antMatchers("/forgotPassword").permitAll()
			.antMatchers("/resetPassword").permitAll()
			.antMatchers("/profile").permitAll()
			.antMatchers("/profileReference").permitAll()
			.antMatchers("/saveProfile").permitAll()
			.antMatchers("/login").permitAll()
			.anyRequest()
			.authenticated().and().csrf().disable().formLogin()
			.loginPage("/login").failureUrl("/login?error=true")
			.defaultSuccessUrl("/home")
			.usernameParameter("username")
			.passwordParameter("password")
			.and().logout()
			.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
			.logoutSuccessUrl("/").and().exceptionHandling()
			.accessDeniedPage("/access-denied");
		
	}
	
	@Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers( 
                		"/css/**", 
                		"/js/**", 
                		"/images/**",
                		"/fonts/**");
	}
	
	
	
}
