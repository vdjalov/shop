package app.web.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import app.service.UserService;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserService userService;
	
	private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable()
			.authorizeRequests()
			.antMatchers("/css/*" ,"/js/*").permitAll()
			.antMatchers("/users/login", "/users/register", "/index").permitAll()
			.anyRequest().authenticated()
			.and()
			.formLogin().loginPage("/users/login").permitAll()
			.usernameParameter("username")
			.passwordParameter("password")
			.defaultSuccessUrl("/home", true)
			.failureUrl("/users/login/failure").permitAll()
			.and()
			.logout().logoutSuccessUrl("/index").permitAll()
			.and()
			.exceptionHandling().accessDeniedPage("/unauthorized");
	}
	
	
	@Override
	@Bean
	public UserDetailsService userDetailsService() {return userService;}
	
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return passwordEncoder;
	}
	
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
	}
}







