package com.ai.chatbot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ai.chatbot.service.ChatService;

	@RestController
	@RequestMapping("/chat")
public class ChatController {

	@Autowired
	private ChatService chatService;
	
	@PostMapping
	public String chat(@RequestBody String message) {
		return chatService.getChatResponse(message);
	}
}