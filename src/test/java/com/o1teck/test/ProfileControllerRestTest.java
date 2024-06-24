package com.o1teck.test;

/*
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

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
public class ProfileControllerRestTest {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ProfileService profileService;
	
	@Autowired
	private InterestService interestService;
	
	private MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext webApplicationContext;
	
	@Before
	public void setup(){
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	@WithMockUser(username="nicktest5@email.com")
	public void testSaveAndDeleteInterest()throws Exception{
		
		//first we need the name of an interest that doesn't already exist in DB
		String interestText = "some interest here";
		
		mockMvc.perform(post("/save-interest").param("name", interestText))
		.andExpect(status().isOk());
		
		//Check if the interest being tested is actually saving to DB
		Interest interest = interestService.get(interestText);
		assertNotNull("should not be null", interest);
		
		//we can also test whether the retrieved interest text matches the text we saved for that interest
		assertEquals("Retrieved interest text should match", interestText, interest.getName());
		
		//Now retrieve the profile and check if the profile retrieved as the correct interest on it
		//and we borrow code body from our getUser() method in ProfileController
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		SiteUser user = userService.get(email);
		Profile profile = profileService.getUserProfile(user);
		//now we can compare list of interests on the profile with what we just saved
		assertTrue("profile should contain added interest", profile.getInterests().contains(new Interest(interestText)));
		
		//Now test whether we can DELETE Them
		mockMvc.perform(post("/delete-interest").param("name", interestText)).andExpect(status().isOk());
		
		//after we've done that, we can get the profile out again...
		profile = profileService.getUserProfile(user);
		//And it SHOULD NOT contain the interet, so...
		assertFalse("The profile should not contain the interest", profile.getInterests().contains(new Interest(interestText)));
		
		
		//So with these tests passed we can go work on the front end of profiles/interests/etc, and be confident
		//in the underlying model/service/logic, etc.
		
	}
}

*/
