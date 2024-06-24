 

package com.o1teck.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@Value("${message.error.exception}") 
	private String exceptionMessage;
	
	@Value("${message.error.duplicate.user}")
	private String duplicateUserMessage;
	
	
	@ExceptionHandler(value=Exception.class)
	public ModelAndView defaultErrorHandler(HttpServletRequest req, Exception e){
		
		ModelAndView modelAndView = new ModelAndView();
		
		//Remember what we're doing here is adding these to the page/model
		//so they're available to call.  For instance, the exception item
		//below allows us to call the exception.message variable from exceptoin.jsp
		modelAndView.getModel().put("message", "An exception has occurred");
		modelAndView.getModel().put("url", req.getRequestURI());
		modelAndView.getModel().put("exception", e);
		
		modelAndView.setViewName("app.exception");
		
		return modelAndView;
	}

	@ExceptionHandler(value=DataIntegrityViolationException.class)
	public ModelAndView duplicateUserHandler(HttpServletRequest req, Exception e) {
		ModelAndView modelAndView = new ModelAndView();
		
		modelAndView.getModel().put("message", duplicateUserMessage);
		modelAndView.getModel().put("url", req.getRequestURL());
		modelAndView.getModel().put("exception", e);
		
		modelAndView.setViewName("app.exception");
		
		return modelAndView;
	}
	
	@ExceptionHandler(MultipartException.class)
	@ResponseBody
	public String fileUploadExceptionHandler(Exception e){
		e.printStackTrace();
		
		return "An error occurred while you were uploading that shit.";
	}
}
