package com.o1teck.service;

import java.util.Date;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

@Service
public class EmailService {
	
	private TemplateEngine templateEngine;
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Value("${mail.enable}")
	private boolean enable;
	
	@Value("${site.url}")
	private String url;
	
	private void send(MimeMessagePreparator preparator){
		if (enable){
			mailSender.send(preparator);
		}
	}
	
	//we're autowiring the constructor, rather than the variable itself, because we need to configure the variable's object created
	@Autowired
	public void EmailService(TemplateEngine templateEngine){
		
		ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver(); 
		
		//We need to tell the TemplateResolver where to find the template, and it's normally gonna look in the resources folder
		templateResolver.setPrefix("mail/");
		templateResolver.setSuffix(".html");
		
		//we also want to make sure emails are not cached, and thus created from scratch
		templateResolver.setCacheable(false);
		
		//and we need to tell it what kind of thing it is supposed to be resolving
		templateResolver.setTemplateMode("HTML5");
		
		//now we can pass it in
		templateEngine.setTemplateResolver(templateResolver);
		
		//here we're setting the value of this class's templateEngine variable (to whatever is passed in)
		this.templateEngine = templateEngine;
	}  
	
	@Async
	public void sendVerificationEmail(String emailAddress, String token){
		
		
		StringBuilder sb = new StringBuilder();
		sb.append("<HTML>");
		sb.append("<p> Hellow there this is <em>HTML</em> </p>");
		sb.append("</HTML>");
		
		
		Context context = new Context();
		
		context.setVariable("token", token);
		context.setVariable("url", url);
		
		//Here, below, we comment out the next line so we can see what parameters it expects
		//String emailContents = templateEngine.process(template, context)
		String emailContents = templateEngine.process("pleaseverify", context);
		
		//and then down below we will past this emailContents string where we had been using SB 
		
		MimeMessagePreparator preparator = new MimeMessagePreparator(){

		@Override
		public void prepare(MimeMessage mimeMessage) throws Exception {
				
			MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
					
			message.setTo(emailAddress);
			message.setFrom(new InternetAddress("bluecollarcoding@gmail.com"));
			message.setSubject("Verify your email address");
			message.setSentDate(new Date());
			message.setText("Please confirm your email by clicking this non-existent link");	
			message.setText(sb.toString(), true);
			message.setText(emailContents, true);
			}	
		};
		send(preparator);
	}
}
