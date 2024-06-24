package com.o1teck.test;


/*
import java.util.HashSet;
import static org.junit.Assert.*;
import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.o1teck.model.entity.Interest;
import com.o1teck.model.entity.Profile;
import com.o1teck.model.entity.SiteUser;
import com.o1teck.service.InterestService;
import com.o1teck.service.ProfileService;
import com.o1teck.service.UserService;


@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration(App.class);
//@WebAppConfiguration would have told the test to load same config as webapp would use
@SpringBootTest
@Transactional
public class ProfileTest {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ProfileService profileService;
	
	@Autowired
	private InterestService interestService;


	private SiteUser[] users = {
			new SiteUser("bobbob", "aaa", "bob@bob.com", "aaaa"),
			new SiteUser("jimjim", "bbb", "jim@jim.com", "bbbb"),
			new SiteUser("joejoe", "ccc",  "joe@joe.com", "cccc")
		};
	
	
	private String[][] interests = {
			{"music", "guitar_XXXXXXX", "plants"},
			{"music", "music", "philosophy_lkjlkjlk"},
			{"philosophy_lkjlkjlk", "football"}
	};
	
	
	@Test
	public void testInterests(){
		
		//TEST PROCEDURE
		//create some users and register them
		//give them profiles
		//add some interests to them
		//save the profiles
		//then retrieve them from DB and see if they have the right interest list assocaited with them
		
		for (int i=0; i< users.length; i++){
			SiteUser user = users[i];
			String[] interestArray = interests[i];
			
			userService.register(user);
			
			HashSet<Interest> interestSet = new HashSet<Interest>();
			for(String interestText: interestArray) {
				Interest interest = interestService.createIfNotExists(interestText);
				interestSet.add(interest);
				
				//Given what we're trying to test, we could probably stop here to make sure we don't 
				//get infected with bad data, (like nulls and such).  So we can check that here.
				
				assertNotNull("Interest should not be bull", interest);
				assertNotNull("Interest should have ID", interest.getId());
				assertEquals("Text should match", interestText, interest.getName());
			}
			
			Profile profile = new Profile(user);
			profile.setInterests(interestSet);
			profileService.save(profile);
			
			Profile retrievedProfile = profileService.getUserProfile(user);
			
			assertEquals("Interests sets shoudl match", interestSet, retrievedProfile.getInterests());
		}

	}
}

*/