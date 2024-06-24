package com.o1teck.test;


/*
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.lang.reflect.Method;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.o1teck.service.FileService;

@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration(App.class);
//@WebAppConfiguration would have told the test to load same config as webapp would use
@SpringBootTest
@Transactional
public class FileServiceTest {
	
	@Autowired
	FileService fileService;
	
	@Value("${photo.upload.directory}")
	private String photoUploadDirectory;
	
	@Test
	//Okay look, we're going to use Reflection to get a reference to the 
	//method that we can't test traditionally because it's Private
	public void testGetExtension() throws Exception{
		//We use the Method class to create a method object, and
		//set it equal to the method we want to test in the real fileService class
		
		//first arg: name of the method we want the reference to
		//2nd arg:  the type of arguments that method has
		Method method = FileService.class.getDeclaredMethod("getFileExtension", String.class);
		
		//set the method to accessible, so we can get at it despite being private
		method.setAccessible(true);
		
		//now we can call the private method for testing file extensions
		//we pass in the file service object we want to test, and
		//the argument we want to supply to the method
		assertEquals("should be png", "png",(String)method.invoke(fileService, "test.png"));
		
		assertEquals("should be png", "png",(String)method.invoke(fileService, "test.png"));
		assertEquals("should be doc", "doc",(String)method.invoke(fileService, "s.doc"));
		assertEquals("should be pdf", "pdf",(String)method.invoke(fileService, "40oz.pdf"));
		assertEquals("should be jpg", "jpg",(String)method.invoke(fileService, "Jack_avatar.jpg"));
		assertNull("should be null",(String)method.invoke(fileService, "syx"));
		
		//and the return value is gonna be the return value of getFileExtension, 
		//but cast to an object, so we have to cast it back to String
	}
	
	@Test
	public void testIsImageExtension() throws Exception{
		
		Method method = FileService.class.getDeclaredMethod("isImageExtension", String.class);
		
		method.setAccessible(true);
		
		//AssertTrue has two arguments, the second argument is a boolean
		assertTrue("png should be valid",(Boolean)method.invoke(fileService, "png"));
		assertTrue("PNG should be valid",(Boolean)method.invoke(fileService, "PNG"));
		assertTrue("jpg should be valid",(Boolean)method.invoke(fileService, "jpg"));
		assertTrue("JPG should be valid",(Boolean)method.invoke(fileService, "JPG"));
		assertTrue("jpeg should be valid",(Boolean)method.invoke(fileService, "jpeg"));
		assertTrue("JPEG should be valid",(Boolean)method.invoke(fileService, "JPEG"));
		
		assertFalse("bob should be valid",(Boolean)method.invoke(fileService, "bob"));
		assertFalse("pdf should be valid",(Boolean)method.invoke(fileService, "pdf"));
		assertFalse("doc should be valid",(Boolean)method.invoke(fileService, "doc"));
	}

	
	@Test
	public void testCreateDirectory() throws Exception{
		
		Method method = FileService.class.getDeclaredMethod("makeSubdirectory", String.class, String.class);
		
		method.setAccessible(true);
		
		for (int i=0;i<10000; i++) {
			
			File created = (File)method.invoke(fileService, photoUploadDirectory, "photo");
			
			assertTrue("Directory should exist" + created.getAbsolutePath(), created.exists());
			
			//I added this code cause I don't see these dirs being created.  Are they supposed to be?  I thought so.
			if (created.exists())
			System.out.println(created.getAbsolutePath());
		}
	}
	
}

*/
