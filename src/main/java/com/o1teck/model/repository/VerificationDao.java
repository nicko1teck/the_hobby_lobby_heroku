package com.o1teck.model.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.o1teck.model.entity.VerificationToken;


@Repository
public interface VerificationDao extends CrudRepository<VerificationToken, Long>{
	
	public VerificationToken findByToken(String token);
	
}
