package com.ai.chatbot.dto;

import java.util.List;

public class AIResponse {
	public List<Candidate> candidates;

	public static class Candidate {
		public Content content;
	}

	public static class Content {
		public List<Part> parts;
	}

	public static class Part {
		public String text;
	}
}
