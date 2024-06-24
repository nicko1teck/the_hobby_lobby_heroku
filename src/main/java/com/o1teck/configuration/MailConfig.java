package com.o1teck.configuration;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailConfig {
	
	@Value("${spring.mail.host}")
	private String host;
	
	@Value("${spring.mail.port}")
	private int port;
	
	@Value("${spring.mail.username}")
	private String user;
	
	@Value("${spring.mail.password}")
	private String password;
	
	@Bean
	public JavaMailSender mailSender(){
		
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		
		//https://stackoverflow.com/questions/10509699/must-issue-a-starttls-command-first
		
		// Trying to get Gmail SMTP to work
		Properties props = new Properties();
		props = new Properties();  
	    props.setProperty("mail.transport.protocol", "smtp");     
	    props.setProperty("mail.host", "smtp.gmail.com");  
	    props.put("mail.smtp.auth", "true");  
	    props.put("mail.smtp.port", "465");  
	    props.put("mail.debug", "true");  
	    props.put("mail.smtp.socketFactory.port", "465");  
	    props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");  
	    props.put("mail.smtp.socketFactory.fallback", "false");  
		
		mailSender.setHost(host);
		mailSender.setPort(port);
		mailSender.setUsername(user);
		mailSender.setPassword(password);
		
		mailSender.setJavaMailProperties(props);
		
		return mailSender;
	}
}