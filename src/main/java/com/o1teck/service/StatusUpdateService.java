package com.o1teck.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.o1teck.model.entity.StatusUpdate;
import com.o1teck.model.repository.StatusUpdateDao;

@Service
public class StatusUpdateService {
	
	//private final static int PAGESIZE = 10;
	
	@Value("${status.pagesize}")
	private int pageSize;
	
	@Autowired
	private StatusUpdateDao statusUpdateDao;
	
	
	public void save(StatusUpdate statusUpdate){
		statusUpdateDao.save(statusUpdate);
		//StatusUpdate latestStatusUpdate = statusUpdate;
	}
	
	
	//Here is where we wrap that method (with a simpler name) from the Interface we/Spring created
	//which we can do because we have a statusUpdateDao in this class
	public StatusUpdate getLatest(){
		return statusUpdateDao.findFirstByOrderByAddedDesc();
	}

	public Page<StatusUpdate> getPage(int pageNumber){
		PageRequest request = PageRequest.of(pageNumber - 1, pageSize, Sort.Direction.DESC, "added");
		return statusUpdateDao.findAll(request);
	}

	public void delete(Long id) {
		statusUpdateDao.deleteById(id);	
	}

	public StatusUpdate get(Long id) {
		return statusUpdateDao.findById(id).orElse(null);
	}
}
