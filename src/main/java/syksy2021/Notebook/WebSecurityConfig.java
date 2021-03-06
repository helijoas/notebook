package syksy2021.Notebook;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import syksy2021.Notebook.web.UserDetailServiceImpl;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserDetailServiceImpl userDetailsService;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.cors().configurationSource(request -> {
			      var cors = new CorsConfiguration();
			      cors.setAllowedOrigins(List.of("*"));
			      cors.setAllowedMethods(List.of("GET","POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
			      cors.setAllowedHeaders(List.of("*"));
			      return cors;
			    })
			.and()
			.authorizeRequests().antMatchers("/css/**", "/login").permitAll() // , "/api/**" huom! api k??yt??ss?? ilman auktorisointia 
			.and()
			.authorizeRequests().antMatchers("/admin/**").hasAuthority("ADMIN")
			.and()
			.authorizeRequests().anyRequest().authenticated()
			.and()
			.formLogin()
				.loginPage("/login")
				.defaultSuccessUrl("/notelist", true)
				.permitAll()
				.and()
			.logout()
				.permitAll();
	}
	
	@Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
        
        // lis??t????n user-tasoinen k??ytt??j?? (k??yt?? n??it?? tunnuksia Postmanissa)
     	auth
     	.inMemoryAuthentication()  
     	.withUser("user").password(passCoder().encode("password"))
     	.authorities("USER").roles("USER");
     		
     	// lis??t????n admin-tasoinen k??ytt??j?? (k??yt?? n??it?? tunnuksia Postmanissa)
     	auth
        .inMemoryAuthentication() 
        .withUser("admin").password(passCoder().encode("password"))
        .authorities("ADMIN").roles("ADMIN");
    }
	
	@Bean
	public PasswordEncoder passCoder() {
		return new BCryptPasswordEncoder(); 
	}
	
	/*
	@Bean
	CorsConfigurationSource corsConfigurationSource() 
    {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST", "PUT", "PATCH", "DELETE", "OPTIONS"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
    */
	
}
