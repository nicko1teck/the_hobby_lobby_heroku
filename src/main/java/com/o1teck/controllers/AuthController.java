 package com.o1teck.controllers;

import java.io.FileNotFoundException;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.o1teck.model.entity.SiteUser;
import com.o1teck.model.entity.VerificationToken;
import com.o1teck.model.repository.VerificationDao;
import com.o1teck.service.EmailService;
import com.o1teck.service.UserService;

@Controller
public class AuthController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private VerificationDao verificationDao;
	
	@Value("${message.registration.confirmed}")
	private String registrationConfirmedMessage;
	
	@Value("${message.invalid.user}")
	private String invalidUserMessage;
	
	@Value("${message.expired.token}")
	private String expiredTokenMessage;
	
	
@RequestMapping("/login")
	String admin(){
		return "app.login";
	}

@RequestMapping("/pleaseverify")
String pleaseVerify(){
	return "app.pleaseverify";
}

@RequestMapping("/confirmregister")
ModelAndView registrationConfirmed(ModelAndView modelAndView, @RequestParam("t") String tokenString){
	
	VerificationToken token = userService.getVerificationToken(tokenString);
	
	//check to see if there's a token
	if (token == null){
		modelAndView.setViewName("redirect:/invaliduser");
		userService.deleteToken(token);
		return modelAndView;
	}
	
	//We also need to check if the token is expired
	Date expirationDate = token.getExpiration();
	
	if (expirationDate.before(new Date())){
		modelAndView.setViewName("redirect:/expiredtoken");
		userService.deleteToken(token);
		return modelAndView;
	}
	
	//Next we can try to get the user FROM  the token
	SiteUser user = token.getUser();
	//so we can make sure that we didn't end up with a token that doens't correspond to an actual user
	
	if (user == null){
		modelAndView.setViewName("redirect:/invaliduser");
		userService.deleteToken(token);
		return modelAndView;	
	}
	
	userService.deleteToken(token);
	user.setEnabled(true);
	userService.save(user);
	
	//modelAndView.getModel().put("url", "http://localhost:8080");
	modelAndView.getModel().put("message", registrationConfirmedMessage);
	modelAndView.setViewName("app.message");
	return modelAndView;
}


@RequestMapping("/invaliduser")
ModelAndView invalidUser(ModelAndView modelAndView){
	
	modelAndView.getModel().put("message",  invalidUserMessage);
	//modelAndView.setViewName("redirect:/invaliduser");
	modelAndView.setViewName("app.message");
	return modelAndView;
}


@RequestMapping("/expiredtoken")
ModelAndView expiredToken(ModelAndView modelAndView){
	
	modelAndView.getModel().put("message",  expiredTokenMessage);
	modelAndView.setViewName("app.message");
	return modelAndView;
}


@RequestMapping(value="/register", method=RequestMethod.GET)
ModelAndView register(ModelAndView modelAndView) throws FileNotFoundException{
	
	SiteUser user = new SiteUser();
	
	modelAndView.getModel().put("user", user);
	modelAndView.setViewName("app.register");
	return modelAndView;
}


@RequestMapping(value="/register", method=RequestMethod.POST)
ModelAndView register(ModelAndView modelAndView, /* @ModelAttribute(value="user")*/ @Valid  SiteUser user, BindingResult result){
	
	modelAndView.setViewName("app.register");
	
	if (!result.hasErrors()){
		
		userService.register(user);
		
		String token = userService.createEmailVerificationToken(user);
		
		emailService.sendVerificationEmail(user.getEmail(), token);
		
		//modelAndView.setViewName("redirect:/pleaseverify");
		modelAndView.setViewName("app.pleaseverify");
	}
	
	return modelAndView;
	}
}
