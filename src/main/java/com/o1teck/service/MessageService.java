package com.o1teck.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.o1teck.model.dto.SimpleMessage;
import com.o1teck.model.entity.Message;
import com.o1teck.model.entity.SiteUser;
import com.o1teck.model.repository.MessageDao;

@Service
public class MessageService {
	
	@Autowired
	private MessageDao messageDao;
	
	public List<SimpleMessage> fetchConvo(Long thisUserID, Long toUserID, int pages) {
		
		//let's eventually put this INT into a properties file
		PageRequest request = PageRequest.of(pages, 15);
		
		//List<SimpleMessage> messages = new ArrayList<>();
		Slice<Message> convo = messageDao.fetchConvo(thisUserID, toUserID, request);
		
		return convo.map(m -> new SimpleMessage(m, m.getFromUser().getId().compareTo(toUserID) == 0)).getContent();
	}
	
	@Async
	public void save(SiteUser fromUser, SiteUser toUser, String text) {
		
		messageDao.save(new Message(fromUser, toUser, text));
	}

	public Page<SimpleMessage> fetchMessageListPage(Long toUserID, int pageNumber) {
		PageRequest request = PageRequest.of(pageNumber-1, 5);
		Page<Message> results = messageDao.findByToUserIdAndIsReadFalseOrderBySentDesc(toUserID, request);
		return results.map(m -> new SimpleMessage(m,true));
	}

	public Optional<Message> get(Long messageID) {
		
		return messageDao.findById(messageID);
	}

	public void save(Message message) {
		messageDao.save(message);
	}
}
