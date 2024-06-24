package com.o1teck.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.o1teck.service.UserService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	UserService userService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		//http.cors().and().csrf().disable().authorizeRequests()
		//.antMatchers("/ws/info","/ws/**","/webjars/**").permitAll();
		//THe above code had been patched in as a fix to an earlier issue and was causing an error, when removed I Was able to 
		//see the console log of the CSRF info
		//https://stackoverflow.com/questions/43114750/header-in-the-response-must-not-be-the-wildcard-when-the-requests-credentia
		
		http
		.authorizeRequests()
			.antMatchers(
					"/",
					"/about",
					"/register",
					"/registrationconfirmed",
					"/invaliduser",
					"/expiredtoken",
					"/profilephoto/*",
					"/verifyemail",
					"/confirmregister",
					"/search",
					"/photos/*",
					"/upload/*",
					"/upload",
					"http://res.cloudinary.com/**"
					)
			.permitAll()
			.antMatchers(
				"/js/*",
				"/css/*",
				"/img/*")
			.permitAll()
			.antMatchers("/addstatus",
					"/editstatus",
					"/deletestatus",
					"/viewstatus")
			.hasRole("ADMIN")
			.antMatchers(
					"/webjars/**",
					"/profile",
					"/profile/*",
					"/edit-profile-about",
					"/upload-profile-photo",
					"/save-interest",
					"/delete-interest",
					"/chat/**",
					"/chatview/**",
					"/chatviewscript",
					"/messagecount",
					"/convo/**",
					"/messages",
					"/markread",
					"/profilephoto"
					)
			.authenticated()
			.anyRequest()
			.denyAll()
			.and()
		.formLogin()
			.loginPage("/login")
			.defaultSuccessUrl("/",true)
			.permitAll()
			.and()
		.logout()
			.permitAll();
		//@formatter.on
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authProvider());
		auth.userDetailsService(userService);
	}
	
	@Bean
	public DaoAuthenticationProvider authProvider() {
	    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
	    authProvider.setUserDetailsService(userService);
	    authProvider.setPasswordEncoder(passwordEncoder());
	    //authProvider.setPasswordEncoder(passwordEncoder);
	    return authProvider;
	}
}

/*
@Autowired
public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
	
	PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
	String encodedPassword = encoder.encode("hello");
	
	// @formatter:off
	auth
		.inMemoryAuthentication()
		.withUser("john")
		.password(encodedPassword)
		.roles("USER");
	
	// @formatter:on

}
**/
