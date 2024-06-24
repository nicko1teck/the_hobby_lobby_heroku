package com.o1teck.controllers;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.o1teck.model.dto.ChatRequest;
import com.o1teck.model.dto.SimpleMessage;
import com.o1teck.model.entity.Message;
import com.o1teck.model.entity.SiteUser;
import com.o1teck.service.MessageService;
import com.o1teck.service.UserService;

@Controller
public class ChatController {

	@Autowired
	private SimpMessagingTemplate simpleMessagingTemplate;
	
	@Autowired
	private Util util;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private MessageService messageService;
	
	@RequestMapping("/markread")
	String markRead(@RequestParam("f") Long fromUserID ,@RequestParam("m") Long messageID) {
		
		Optional<Message> messageOpt = messageService.get(messageID);
		
		if(messageOpt.isPresent())  {
			
			Message message = messageOpt.get();
			message.setIsRead(true);
			messageService.save(message);
		}
		
		System.out.println();
		System.out.println();
		System.out.println(messageID);
		System.out.println();
		System.out.println();
		
		return "redirect:/chatview/" + fromUserID;
	}
	
	@RequestMapping("/messages")
	ModelAndView checkMessages(ModelAndView modelAndView, @RequestParam("p") int pageNumber){
		
		SiteUser user = util.getUser();
		
		Page<SimpleMessage> messages = messageService.fetchMessageListPage(user.getId(),pageNumber);
		
		modelAndView.getModel().put("messageList", messages);
		modelAndView.getModel().put("pageNumber", pageNumber);
		modelAndView.getModel().put("userId", user.getId());
	
		modelAndView.setViewName("app.checkmessages");
		return modelAndView;
	}
	
	// method to retrieve a list of messages between users
	@ResponseBody
	@RequestMapping(value="/convo/{otherUserID}", method=RequestMethod.POST, produces="application/json")
	List<SimpleMessage> fetchConvo(@PathVariable("otherUserID") Long otherUserID, @RequestBody ChatRequest request){
		//List<SimpleMessage> list = new ArrayList<SimpleMessage>();
		//list.add(new SimpleMessage("hello"));
		//debug
		//System.out.println("The FETCH CONVO method was successfully called!");
		
		SiteUser thisUser = util.getUser();
		
		List<SimpleMessage> simpleMessageList = messageService.fetchConvo(thisUser.getId(), otherUserID, request.getPage());
		
		return simpleMessageList;
	}
	
	
	@RequestMapping("/chatview/{chatWithUserID}")
	ModelAndView chatView(ModelAndView modelAndView, @PathVariable Long chatWithUserID) {
		
		SiteUser thisUser = util.getUser();
		String chatWithUserName = userService.getUserName(chatWithUserID);
		
		System.out.println(chatWithUserID);
		System.out.println(chatWithUserName);
		System.out.println("The model has these values!");
		
		modelAndView.setViewName("chat.view");
		modelAndView.getModel().put("thisUserName",thisUser.getFirstname());
		modelAndView.getModel().put("thisUserID", thisUser.getId());
		modelAndView.getModel().put("chatWithUserID", chatWithUserID);
		modelAndView.getModel().put("chatWithUserName", chatWithUserName);
		
		return modelAndView;
	}

	
	@MessageMapping("/message/send/{toUserID}")
		public void send(Principal principal, SimpleMessage message, @DestinationVariable Long toUserID){
	
		//System.out.println(message);
			
		//get details for sending user (current user)
		String fromUsername = principal.getName();
		SiteUser fromUser = userService.get(fromUsername);
		Long fromUserID = fromUser.getId();
		
		//get details for receiving user
		Optional<SiteUser> toUserOpt = userService.get(toUserID);
		SiteUser toUser = toUserOpt.get();
		String toUserName = toUser.getEmail();
		
		String returnReceiptQueue = String.format("/queue/%d", toUserID);
		String toUserQueue = String.format("/queue/%d", fromUserID);
		
		messageService.save(fromUser, toUser, message.getText());
		
		message.setIsReply(false);
		simpleMessagingTemplate.convertAndSendToUser(fromUsername, returnReceiptQueue, message);
		
		message.setFrom(fromUser.getFirstname() + " " + fromUser.getSurname());
		
		message.setIsReply(true);
		simpleMessagingTemplate.convertAndSendToUser(toUserName, toUserQueue, message);
		
		//send a new message notification
		simpleMessagingTemplate.convertAndSendToUser(toUserName, "/queue/newmessages", message);
		}
}
