package com.ai.chatbot.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ai.chatbot.dto.AIResponse;

@Service
public class ChatService {

	@Autowired
	private RestTemplate restTemplate;

	@Value("${google.ai.api.key}")
	private String apiKey;

	@Value("${google.ai.model:gemini-2.0-flash}")
	private String model;

	public String getChatResponse(String message) {
		if (message == null || message.isBlank()) return "Please provide a message.";
		try {
			String url = "https://generativelanguage.googleapis.com/v1beta/models/" + model + ":generateContent?key=" + apiKey;

			Map<String, Object> requestBody = Map.of(
				"contents", List.of(Map.of(
					"parts", List.of(Map.of("text", message))
				))
			);

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);

			HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

			ResponseEntity<String> rawResponse = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
			System.out.println("RAW RESPONSE: " + rawResponse.getBody());

			AIResponse response = restTemplate.postForObject(url, entity, AIResponse.class);
			if (response == null || response.candidates == null || response.candidates.isEmpty())
				return "No response from AI";
			return response.candidates.get(0).content.parts.get(0).text;
		} catch (Exception e) {
			e.printStackTrace();
			return "Error: " + e.getMessage();
		}
	}
}
