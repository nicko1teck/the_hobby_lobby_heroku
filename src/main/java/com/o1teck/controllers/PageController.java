package com.o1teck.controllers;

import org.owasp.html.PolicyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.o1teck.model.entity.StatusUpdate;
import com.o1teck.service.StatusUpdateService;

@Controller
public class PageController {
	
	@Autowired
	private StatusUpdateService statusUpdateService;
	
	@Value("${message.error.forbidden}")
	private String accessDeniedMessage;
	
	@RequestMapping("/")
	ModelAndView home(ModelAndView modelAndView) {
		
		StatusUpdate statusUpdate = statusUpdateService.getLatest();
		
		modelAndView.getModel().put("statusUpdate", statusUpdate);
		
		modelAndView.setViewName("app.homepage");
		return modelAndView;
	}
	 
	@RequestMapping("/403")
	ModelAndView accessDenied(ModelAndView modelAndView){
		
		modelAndView.getModel().put("message", accessDeniedMessage);
		modelAndView.setViewName("app.message");
		return modelAndView;
	}
	
	@RequestMapping("/about")
	String about() {
		return "app.about";
	}
}