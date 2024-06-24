package com.o1teck.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.o1teck.model.dto.SearchResults;
import com.o1teck.service.SearchService;

import java.util.List;

@Controller
public class SearchController {
	
	@Autowired
	SearchService searchService;
	 
	@RequestMapping(value="/search", method={RequestMethod.POST, RequestMethod.GET})
	public ModelAndView search(ModelAndView modelAndView, @RequestParam("s") String text, @RequestParam(name= "p", defaultValue="1") int pageNumber){
		
		System.out.println("Search text:  " + text);
		
		Page<SearchResults> results = searchService.search(text, pageNumber);
		
		modelAndView.getModel().put("page", results);
		modelAndView.getModel().put("s", text);
		
		modelAndView.setViewName("app.search");
		return modelAndView;
	}
}
