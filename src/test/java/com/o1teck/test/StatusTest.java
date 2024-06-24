package com.o1teck.test;

/*
import static org.junit.Assert.assertNotNull;

import java.util.Calendar;

import static org.junit.Assert.assertEquals;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.o1teck.model.entity.StatusUpdate;
import com.o1teck.model.repository.StatusUpdateDao;


@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration(App.class);
//@WebAppConfiguration would have told the test to load same config as webapp would use
@SpringBootTest
@Transactional
public class StatusTest {
	
	@Autowired
	private StatusUpdateDao statusUpdateDao;
	
	@Test
	public void testSave() {
		
		StatusUpdate status = new StatusUpdate("This is a test Status Update");
		
		statusUpdateDao.save(status);
		
		assertNotNull("Non null ID", status.getId());
		assertNotNull("Non null Date", status.getAdded());
		
		//NOW, we want to get out out of the DB to do a proper test on it
		//StatusUpdate retrieved = Optional<statusUpdateDao>.findOne(status.getId());
		//Note, due to seaming deprecation, I had to get the below code from Discus comments on the tutorial
		StatusUpdate retrieved = statusUpdateDao.findById(status.getId()).get();
		
		//Second parameter is what we're expecting, and we will be able to compare thanks to our equals method 
		assertEquals("Matching Status Update", status, retrieved);
	}
	
	
	@Test
	public void testFindLatest() {
		
		Calendar calendar = Calendar.getInstance();
		
		StatusUpdate lastStatusUpdate = null;
		
		for(int i=0; i<99; i++) {
			calendar.add(Calendar.DAY_OF_YEAR, -1);
			
			StatusUpdate status = new StatusUpdate("Status update " + i, calendar.getTime());
			
			statusUpdateDao.save(status);
			
			lastStatusUpdate = status;
		}
		
		StatusUpdate retrieved = statusUpdateDao.findFirstByOrderByAddedDesc();
		
		assertEquals("Latest status update", lastStatusUpdate, retrieved);
	}
}
*/