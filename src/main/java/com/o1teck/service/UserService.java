 package com.o1teck.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.o1teck.model.dto.SpringUser;
import com.o1teck.model.entity.SiteUser;
import com.o1teck.model.entity.TokenType;
import com.o1teck.model.entity.VerificationToken;
import com.o1teck.model.repository.UserDao;
import com.o1teck.model.repository.VerificationDao;

@Service
public class UserService implements UserDetailsService{
	
	@Autowired
	private UserDao	userDao;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private VerificationDao verificationDao;
	
	
	public void register(SiteUser user){
		user.setRole("ROLE_USER");
		user.setPassword(passwordEncoder().encode(user.getPlainPassword()));
		userDao.save(user);
	}

	
	@Bean
	public PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}
	
	
	public void save(SiteUser user) {
		userDao.save(user);
	}
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		passwordEncoder = passwordEncoder();
		
		SiteUser user = userDao.findByEmail(email);
		
		if (user == null){
			return null;
		}
		List<GrantedAuthority> auth = AuthorityUtils.commaSeparatedStringToAuthorityList(user.getRole());
		
		String password = user.getPassword();
		Boolean enabled = user.getEnabled();
		//Let's get first name so we can display it in the nav bar
		String firstname = user.getFirstname();
		
		// Before we made the username available in the nav bar
		// return new org.springframework.security.core.userdetails.User(email, password, enabled, true, true, true, auth);
		
		// To display username in Navbar, we're now returning a SpringUser object with the necessary details (firstname)
		return new SpringUser(firstname, email, password, enabled, true, true, true, auth);
	}
	
	
		public String createEmailVerificationToken(SiteUser user){
			
			VerificationToken token = new VerificationToken(UUID.randomUUID().toString(), user, TokenType.REGISTRATION);
			
			verificationDao.save(token);
			
			return token.getToken();
		}
		
		
		public VerificationToken getVerificationToken(String token){
			
			return verificationDao.findByToken(token);
		}

		
		public void deleteToken(VerificationToken token) {
			
		}

		
		public SiteUser get(String email) {
			return userDao.findByEmail(email);
		}

		
		public Optional<SiteUser> get(Long id){
			
			return userDao.findById(id);
		}
		

		public String getUserName(Long chatWithUserID) {
			Optional<SiteUser> userOptional = userDao.findById(chatWithUserID);
			SiteUser user = userOptional.get();
			return user.getFirstname() + " " + user.getSurname();
		}
}	