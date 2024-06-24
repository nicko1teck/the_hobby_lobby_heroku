package com.o1teck.model.repository;

import java.util.List;

//import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.o1teck.model.dto.SearchResults;
import com.o1teck.model.entity.Profile;
import com.o1teck.model.entity.SiteUser;

//Gotta admit, these Dao classes are confusing to me in their simplicity.
//I need to look more into the Documentation for CrudRepository interface.

public interface ProfileDao extends CrudRepository<Profile, Long>{
	
	Profile findByUser(SiteUser user);
	
	List<Profile> findByInterestsNameContainingIgnoreCase(String text);
		
	Page<Profile> findByInterestsNameContainingIgnoreCase(String text, Pageable request);

}