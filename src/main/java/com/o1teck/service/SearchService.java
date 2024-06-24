package com.o1teck.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.o1teck.model.dto.SearchResults;
import com.o1teck.model.entity.Profile;
import com.o1teck.model.repository.ProfileDao;

@Service
public class SearchService {
	
	@Value("${results.pagesize}")
	private int pageSize;
	
	@Autowired
	private ProfileDao profileDao;
	
	
	public Page<SearchResults> search(String text, int pageNumber){
		
		PageRequest request = PageRequest.of(pageNumber-1, pageSize); 
		
		Page<Profile> results = profileDao.findByInterestsNameContainingIgnoreCase(text, request);
		
		return results.map(SearchResults::new);
	}	 
}