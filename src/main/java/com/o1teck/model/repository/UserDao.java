package com.o1teck.model.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.o1teck.model.entity.SiteUser;

@Repository						
public interface UserDao extends CrudRepository<SiteUser, Long>{
	
	SiteUser findByEmail(String email);
    
	Optional<SiteUser> findById(Long id);
	}