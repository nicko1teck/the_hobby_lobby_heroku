package com.o1teck.batch;

/*
package com.o1teck.batch;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import junit.framework.Assert;
import com.o1teck.exceptions.ImageTooSmallException;
import com.o1teck.exceptions.InvalidFileException;
import com.o1teck.model.FileInfo;
import com.o1teck.model.Interest;
import com.o1teck.model.Profile;
import com.o1teck.model.SiteUser;
import com.o1teck.service.InterestService;
import com.o1teck.service.ProfileService;
import com.o1teck.service.UserService;
import com.o1teck.status.PhotoUploadStatus;

import org.springframework.boot.SpringApplication;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
//@Transactional
public class UpdatePhoto {
	
	
	
public static void main(String[] args) {
		
	}
	
	

	@Autowired
	private UserService userService;
	
	@Autowired
	private ProfileService profileService;
	
	@Autowired
	private InterestService interestService;
	
	
	@RequestMapping(value = "/upload-profile-photo", method = RequestMethod.POST)
	@ResponseBody // returns data in JASON format
	public ResponseEntity<PhotoUploadStatus> handlePhotoUploads(@RequestParam("file") MultipartFile file) {
		// modelAndView.setViewName("redirect:/profile");
		// In order to use our new variables to save photo info...

		// First, get the user -- with our utility method
		SiteUser user = getUser();

		// Then get the user's profile and pass in the info
		Profile profile = profileService.getUserProfile(user);

		Path oldPhotoPath = profile.getPhoto(photoUploadDirectory);

		PhotoUploadStatus status = new PhotoUploadStatus(photoStatusOK);

		// ??? - But it's not yet clear to me how merely the above gets that new
		// info to the profile
		// I think this is why John now goes to the profile class and adds a
		// convenience method for getting
		// that FileInfo object

		try {
			FileInfo photoInfo = fileService.saveImageFile(file, photoUploadDirectory, "photos", "p" + user.getId(),
					100, 100);

			// Having saved the details to fields in the profile class, we can
			// now...
			profile.setPhotoDetails(photoInfo);
			profileService.save(profile);

			System.out.println();
			System.out.println(photoInfo.toString());
			System.out.println();

			if (oldPhotoPath != null) {
				Files.delete(oldPhotoPath);
			}

		} catch (InvalidFileException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (ImageTooSmallException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

		// modelAndView.setViewName("/profile");
		// return modelAndView;
		// return modelAndView;
		return new ResponseEntity(status, HttpStatus.OK);
	}

	@RequestMapping(value = "/profilephoto/{id}", method = RequestMethod.GET)
	// we also need to say that what's returned is not some Tiles mapping, but
	// rather a load of data that should be displyed directly
	@ResponseBody
	// which will then be wrapped in this ResponseEntity, in other words...
	// What we're going to send in the body if this response is an
	// InputStreamResource
	// which will contain the data of our photo
	ResponseEntity<InputStreamResource> servePhoto(@PathVariable Long id)throws IOException {

		SiteUser user = userService.get(id);

		Profile profile = profileService.getUserProfile(user);

		//Path photoPath2 = Paths.get(photoUploadDirectory, "default", "Jack_avatar.jpg");
		Path photoPath = Paths.get(photoUploadDirectory, "default");
		
		if (profile != null && profile.getPhoto(photoUploadDirectory) != null) {
			photoPath = profile.getPhoto(photoUploadDirectory);
		}

		return ResponseEntity.ok()
				.contentLength(Files.size(photoPath))
				.contentType(MediaType.parseMediaType(URLConnection.guessContentTypeFromName(photoPath.toString())))
				.body(new InputStreamResource(Files.newInputStream(photoPath, StandardOpenOption.READ)));
	}
	
	
	
	
	
}


**/