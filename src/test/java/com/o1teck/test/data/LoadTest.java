package com.o1teck.test.data;

/*
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.o1teck.model.entity.Interest;
import com.o1teck.model.entity.Profile;
import com.o1teck.model.entity.SiteUser;
import com.o1teck.service.InterestService;
import com.o1teck.service.ProfileService;
import com.o1teck.service.UserService;



//@SpringBootConfiguration
//@RunWith(SpringJUnit4ClassRunner.class)
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
//@Transactional
public class LoadTest {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ProfileService profileService;
	
	@Autowired
	private InterestService interestService;
	
	//Declare the file paths for the two files
	private static final String namesFile ="/com/o1teck/test/data/names.txt";
	private static final String interestsFile="/com/o1teck/test/data/hobbies.txt";
	
	private List<String> loadFile(String filename, int maxLength) throws IOException{
		
		//We need an absolute location for this file we want to load
		Path filePath = new ClassPathResource(filename).getFile().toPath();
		
		System.out.println(filePath);
		
		Stream<String> stream = Files.lines(filePath);
		
		// @formatter:off
		
		List<String> items = stream
			.filter(line -> !line.isEmpty())
			.map(line -> line.trim())
			.filter(line -> line.length() <= maxLength)
			.map(line -> line.substring(0, 1).toUpperCase() + line.substring(1).toLowerCase())
			//.map(line -> line + "lfhglkshgksh")
			.collect(Collectors.toList());
		
		// @formatter:on
		
		stream.close();
		
		return items;
	}
	
	
	//@Ignore
	@SuppressWarnings("deprecation")
	@Test
	public void createTestData() throws IOException{
		
		Random random = new Random();
		
		//Create Interests list  
			//NOTE -- The LoadFile method should trim and delete blanks!!
		List<String> interests = loadFile(interestsFile, 25);
		
		//Create Names list
			//NOTE -- The LoadFile method should trim and delete blanks!!
		List<String> names = loadFile(namesFile, 20);
		
		
		//Check the interests at console
		for(String s: interests) {
			System.out.println(s);
		}
		
		//check the names at console
		for(String n: names) {
			System.out.println(n);
		}
		

		for(int numUsers=0; numUsers < 4000; numUsers++){
			
			//Get a random firstname for user
			String firstname = names.get(random.nextInt(names.size()));
			
			//Get a random surname for user
			String surname = names.get(random.nextInt(names.size()));
			
			//Create email address for user
			String email = firstname.toLowerCase() + surname.toLowerCase() + "@example.com";
			
				//TEST
			if(numUsers<25) {
				System.out.println();
				System.out.println();
				System.out.println(email);
				System.out.println();
				System.out.println();
			}
			if(userService.get(email) != null) {
				continue;
			}
			
			//Create password for user
			String password = "pass" + firstname.toLowerCase();
			
			//Trim password to requisite length if needed
			password = password.substring(0, Math.min(15, password.length()));
			
			//TEST... that password is requisite length
			assertTrue(password.length() <= 15);
			
			//Create User object for this user
			SiteUser user = new SiteUser(firstname, surname, email, password);
			
			//Enable every fifth (??) user... or something
			user.setEnabled(random.nextInt(5) != 0);
		
			if(numUsers<25) {
			//Output to Console the user's toString 
			System.out.println();
			System.out.println();
			System.out.println("User info:  " + user);
			System.out.println();
			System.out.println("Iteration Loop (of 200) :" + numUsers);
			System.out.println();
			System.out.println();
			}
			//register that user
			userService.register(user);
			
			//Create a profile for that user
			Profile profile = new Profile(user);
			
			//Pick a random # of interests between 1 and 7
			int numberInterests = random.nextInt(7);
			
			//Create a SET to hold that user's interests
			Set<Interest> userInterests = new HashSet<Interest>();
			
			//Loop thru that user's interests
			for(int i=0; i<numberInterests; i++){
				
				//Create a string to hold this (the i'th) interest
				String interestText = interests.get(random.nextInt(interests.size()));
				
				//Create that interest if it does not already exist
				Interest interest = interestService.createIfNotExists(interestText);
				
				//Add that interest to the user's interest list created above
				userInterests.add(interest);
			}
			//Attach interest list to user's profile
			profile.setInterests(userInterests);
			
			//save the user's profile
			profileService.save(profile);
		}
		
		assertTrue(true);
	}
}  
*/