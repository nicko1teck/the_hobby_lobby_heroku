package com.o1teck.controllers;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.Map;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.cloudinary.json.JSONObject;
import org.owasp.html.PolicyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
//import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
//import com.o1teck.MockMultipartFile;
import com.o1teck.model.entity.Interest;
import com.o1teck.model.entity.Profile;
import com.o1teck.model.entity.SiteUser;
import com.o1teck.model.repository.UserDao;
//import com.o1teck.service.CloudinaryService;
import com.o1teck.service.FileService;
import com.o1teck.service.InterestService;
import com.o1teck.service.ProfileService;
import com.o1teck.service.UserService;


@Controller
public class ProfileController {
	
	@Autowired
    private Cloudinary cloudinary;

	@Autowired
	private FileService fileService;

	@Autowired
	private ProfileService profileService;

	@Autowired
	private UserService userService;

	@Autowired
	private InterestService interestService;

	@Autowired
	private PolicyFactory htmlPolicy;

	@Autowired
	private UserDao userDao;
	
	//private String profilePhotoName;
	
	
	@Value("${photo.upload.directory}")
	private String photoUploadDirectory;

	@Value("${photo.upload.ok}")
	private String photoStatusOK;

	@Value("${photo.upload.invalid}")
	private String photoStatusInvalid;

	@Value("${photo.upload.ioexception}")
	private String photoStatusIOException;

	@Value("${photo.upload.toosmall}")
	private String photoStatusTooSmall;
	
	
	
	

	private SiteUser getUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		return userService.get(email);
	}

	private ModelAndView showProfile(SiteUser user) {
		ModelAndView modelAndView = new ModelAndView();

		if (user == null) {
			modelAndView.setViewName("redirect:/");
			return modelAndView;
		}

		Profile profile = profileService.getUserProfile(user);

		if (profile == null) {
			profile = new Profile();
			profile.setUser(user);
			profileService.save(profile);
		}

		Profile webProfile = new Profile();
		webProfile.safeCopyFrom(profile);
		
			//modelAndView = showProfile(user);

		modelAndView.getModel().put("userId", user.getId());
		modelAndView.getModel().put("profile", webProfile);
		modelAndView.getModel().put("firstname", user.getFirstname());
			modelAndView.getModel().put("userPhotoUrl", user.getProfilePhotoUrl());

		modelAndView.setViewName("app.profile");

		return modelAndView;
	}

	// To show Profile of the CURRENT USER
	// Calls: showProfile(user)
	@RequestMapping(value = "/profile")
	public ModelAndView showProfile() {

		SiteUser user = getUser();
			//Profile profile = profileService.getUserProfile(user);

		String firstname = user.getFirstname();
		String surname = user.getSurname();
			String profilePhotoUrl = user.getProfilePhotoUrl();
			String profilePhotoName = user.getProfilePhotoName();
			
		ModelAndView modelAndView = showProfile(user); // This is our new refactored method above
		
		modelAndView.getModel().put("ownProfile", true);
		modelAndView.getModel().put("firstname", firstname);
		modelAndView.getModel().put("surname", surname);
			modelAndView.getModel().put("userPhotoUrl", profilePhotoUrl);
			modelAndView.getModel().put("userPhotoName", profilePhotoName);

		return modelAndView;
	}

	
	
	@RequestMapping(value = "/profile/{id}")
	public ModelAndView showProfile(@PathVariable("id") Long id) {
		
		// use the PathVariable(id) to get the corresponding user...
		Optional<SiteUser> userOpt = userService.get(id);
		SiteUser user = userOpt.get();

		String firstname = user.getFirstname();
		String surname = user.getSurname();

		// And maybe create our modelAndView for returning
		ModelAndView modelAndView = new ModelAndView();

		// now to get the user...
		if (user != null) {

			// set modelAndView equal to show profile? (must be an M&V return method)
			modelAndView = showProfile(user);

			modelAndView.getModel().put("ownProfile", false);
			modelAndView.getModel().put("firstname", firstname);
			modelAndView.getModel().put("surname", surname);

			return modelAndView;

		} else {
			modelAndView.setViewName("redirect:/");
			return modelAndView;
		}
	}

	
	
	@RequestMapping(value = "/edit-profile-about", method = RequestMethod.GET)
	public ModelAndView editProfile(ModelAndView modelAndView) {

		// We need to know what user's profile to edit!
		SiteUser user = getUser();

		Profile profile = profileService.getUserProfile(user);

		Profile webProfile = new Profile();

		webProfile.safeCopyFrom(profile);

		modelAndView.getModel().put("profile", webProfile);

		modelAndView.setViewName("app.editProfileAbout");

		return modelAndView;
	}

	
	
	
	@RequestMapping(value = "/edit-profile-about", method = RequestMethod.POST)
	ModelAndView editProfile(ModelAndView modelAndView, @Valid Profile webProfile, BindingResult result) {

		modelAndView.setViewName("app.editProfileAbout");

		SiteUser user = getUser();

		Profile profile = profileService.getUserProfile(user);

		profile.safeMergeFrom(webProfile, htmlPolicy);

		if (!result.hasErrors()) {
			profileService.save(profile);
			modelAndView.setViewName("redirect:/profile");
		}
		return modelAndView;
	}
	
	
	
	
	
	@PostMapping("/upload")
    @ResponseBody
    public ModelAndView uploadFile(ModelAndView modelAndView, @RequestParam("file") MultipartFile file) throws IOException, URISyntaxException, ServletException {
    	
		// create byte array to hold file bytes	
		byte[] fileBytes = null;
			
		// Get the file's bytes
		fileBytes = file.getBytes();
		
		// create a Map to hold the Response from cloud photo platform (Cloudinary)
		Map responseMap = null;
		
		
		// Upload photo to Cloudinary
		try {
    	responseMap = cloudinary.uploader().unsignedUpload(fileBytes, "apzbavjn", ObjectUtils.asMap());    //("cloud_name", "nicko1teck"));  
    	
    	
		}catch (Exception e) {
			e.printStackTrace();
			}
    	
			// FOR TESTING
    		System.out.println();
    		System.out.println();
    		System.out.println();
    		System.out.println(responseMap.toString());
    		System.out.println();
    		System.out.println();
    		System.out.println();
    		
    	// Turn response into JSON data
    	JSONObject json=new JSONObject(responseMap);
    	
    	// extract the photo's new Cloudinary URL
    	String url=json.getString("url");
    	
    	
    	// ... and it's Cloudinary ID/name
    	String imageName=json.getString("public_id");
    
    	// resize the photo
    	String resizedImageString = cloudinary.url().transformation(new Transformation().height(100).width(100).crop("scale")).imageTag(imageName);
    	
    	// FOR TESTING
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println(resizedImageString);
		System.out.println();
		System.out.println();
		System.out.println();
		
    	// get the current user
    	SiteUser user = getUser();
    	
    	// get that user's profile
		Profile profile = profileService.getUserProfile(user);
		
		modelAndView = showProfile(user);
		
		//GET JUST THE URL OF THE REZIZED PHOTO
		String resizedProfilePhotoUrl = StringUtils.substringBetween(resizedImageString, "'", "'");
		
		//Add the data we need
		modelAndView.getModel().put("firstname", getUser().getFirstname());
    	modelAndView.getModel().put("interests", profile.getInterests());
    	modelAndView.getModel().put("ownProfile", true);
    	modelAndView.getModel().put("userPhotoUrl", resizedProfilePhotoUrl);
    	modelAndView.getModel().put("userPhotoName", imageName);
		
		// set our new values to the User object
		//user.setProfilePhotoUrl(url);
		user.setProfilePhotoUrl(resizedProfilePhotoUrl);
		user.setProfilePhotoName(imageName);
		
		// save the profile and user
		profileService.save(profile);
		userService.save(user);
	
    	modelAndView.setViewName("app.profile");
		//modelAndView.setViewName("redirect:/profile");
    	return modelAndView;
		//return responseMap;
		//return resizeString;
    }
	
	
	
	
	
	
	@RequestMapping(value = "/profilephoto/{id}", method = RequestMethod.GET)
	@ResponseBody
	ResponseEntity<InputStreamResource> servePhoto(@PathVariable Long id) throws IOException, URISyntaxException {

		// SiteUser user = userService.get(id);
		Optional<SiteUser> userOpt = userService.get(id);
		SiteUser user = userOpt.get();

		Profile profile = profileService.getUserProfile(user);

		Path photoPath = Paths.get(new URI(user.getProfilePhotoUrl()));

		//Path photoPath = Paths.get(photoUploadDirectory, "default","productive-hobbies100x100.jpg");

		/*
		 * Excluding this code to see if I can get a Cloudinary image to serve here,
		 * found at the above URL
		 * 
		 * if (profile != null && profile.getPhoto(photoUploadDirectory) != null) {
		 * photoPath = profile.getPhoto(photoUploadDirectory); }
		 * 
		 */

		return ResponseEntity.ok().contentLength(Files.size(photoPath))
				.contentType(MediaType.parseMediaType(URLConnection.guessContentTypeFromName(photoPath.toString())))
				.body(new InputStreamResource(Files.newInputStream(photoPath, StandardOpenOption.READ)));
	}

	
	
	@RequestMapping(value = "/save-interest", method = RequestMethod.POST)
	@ResponseBody // this method returns whatever it does... no view name for Tiles or anything
					// like that.
	public ResponseEntity<?> saveInterest(@RequestParam("name") String interestName) {

		SiteUser user = getUser();
		Profile profile = profileService.getUserProfile(user);

		String cleanedInterestName = htmlPolicy.sanitize(interestName);

		Interest interest = interestService.createIfNotExists(cleanedInterestName);

		profile.addInterest(interest);
		profileService.save(profile);

		return new ResponseEntity<>(null, HttpStatus.OK);
	}
	
	

	@RequestMapping(value = "delete-interest", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> deleteInterest(@RequestParam("name") String interestName) {

		SiteUser user = getUser();
		Profile profile = profileService.getUserProfile(user);

		profile.removeInterest(interestName);

		profileService.save(profile);
		
		System.out.println();
		System.out.println();
		System.out.println("IS ANYTHING FUCKING WORKING?");
		System.out.println();
		System.out.println();

		return new ResponseEntity<>(null, HttpStatus.OK);
	}

	
	
	
}