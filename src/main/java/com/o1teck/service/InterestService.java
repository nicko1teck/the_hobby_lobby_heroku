package com.o1teck.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.o1teck.model.entity.Interest;
import com.o1teck.model.repository.InterestDao;

@Service
public class InterestService {
	
	@Autowired
	private InterestDao interestDao;

	public Interest get(String interestName){
		return interestDao.findOneByName(interestName);	
	}
	
	public void save(Interest interestName){
		interestDao.save(interestName);
	}
	
	public long count(){
		return interestDao.count();
	}
	
	@Transactional
	public Interest createIfNotExists(String interestText){
		
		Interest interest = interestDao.findOneByName(interestText);
		
		if(interest == null){
			
			interest = new Interest(interestText);
			interestDao.save(interest);
		}
		
		return interest;
	}
}
