package com.example.demo.services;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import org.apache.http.HttpException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class GeminiService {
    private final Client client;

    public GeminiService(@Value("${GOOGLE_API_KEY}") String apikey) {
        this.client = Client.builder().apiKey(apikey).build();
    }

    public String generateDescription(String movieTitle) throws IOException, HttpException {
        GenerateContentResponse response = client.models.generateContent("gemini-3.6-flash", "Write a short description for the movie: " + movieTitle, null);
        return response.text();
    }
}
