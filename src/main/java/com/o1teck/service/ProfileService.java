package com.o1teck.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.o1teck.model.entity.Interest;
import com.o1teck.model.entity.Profile;
import com.o1teck.model.entity.SiteUser;
import com.o1teck.model.repository.ProfileDao;

@Service
public class ProfileService {
	
	@Autowired
	ProfileDao profileDao;
	
	//@PreAuthorize("isAuthenticated()")
	public void save(Profile profile){
		profileDao.save(profile);
	}

	//@PreAuthorize("isAuthenticated()")
	public Profile getUserProfile(SiteUser user){
		return profileDao.findByUser(user);
	}
}
