package com.ai.chatbot.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ChatService {

	@Value("${google.ai.api.key}")
	private String apiKey;

	@Value("${google.ai.model:gemini-2.0-flash}")
	private String model;

	@SuppressWarnings("unchecked")
	public String getChatResponse(String message) {
		RestTemplate restTemplate = new RestTemplate();
		String url = "https://generativelanguage.googleapis.com/v1beta/models/" + model + ":generateContent?key=" + apiKey;

		Map<String, Object> requestBody = Map.of(
			"contents", List.of(Map.of(
				"parts", List.of(Map.of("text", message))
			))
		);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);
		Map<String, Object> response = restTemplate.postForObject(url, entity, Map.class);

		List<Map<String, Object>> candidates = (List<Map<String, Object>>) response.get("candidates");
		Map<String, Object> content = (Map<String, Object>) candidates.get(0).get("content");
		List<Map<String, Object>> parts = (List<Map<String, Object>>) content.get("parts");
		return (String) parts.get(0).get("text");
	}
}
