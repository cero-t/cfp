package jjug.security;

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableOAuth2Sso
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.requestMatchers().antMatchers("/**").and().authorizeRequests()
				.mvcMatchers("/submissions/*", "/conferences/*",
						"/conferences/*/submissions", "/conferences/*/votes")
				.permitAll().mvcMatchers("/v1/votes").authenticated()
				.mvcMatchers("/admin", "/admin/**", "/v1/**").hasRole("ADMIN")
				.antMatchers("/login**").permitAll().anyRequest().authenticated().and()
				.csrf()
				.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
	}
}