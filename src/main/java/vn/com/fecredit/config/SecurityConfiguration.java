package vn.com.fecredit.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	private static final String[] WHITE_LIST = {"/h2-console/**","/api-docs/**","/swagger-ui.html","/swagger-ui/**"
	,"/awss3/**"
	};
	
	//Configure routing policy
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.cors().and().csrf().disable(); //Make you send request freely
		http.authorizeRequests().antMatchers(WHITE_LIST).permitAll()
//				.antMatchers("/**").hasRole("USER").and().httpBasic()
				.and().formLogin().disable();
		//For test h2 database
		http.headers().frameOptions().disable();

		http.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		String defaultUser = StaticContextAccessor.getBean(ConfigurationComponent.class).getDefaultUser();
		String defaultPassword = StaticContextAccessor.getBean(ConfigurationComponent.class).getDefaultPassword();
		String encodedPassword = passwordEncoder().encode(defaultPassword);
		auth.inMemoryAuthentication().withUser(defaultUser).password(encodedPassword).roles("USER");
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
