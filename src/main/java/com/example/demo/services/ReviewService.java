
package com.example.demo.services;
import java.io.IOException;
import org.apache.http.HttpException;
import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {

    private final Client client;

    public ReviewService(@Value("${GOOGLE_API_KEY}") String apiKey) {
        this.client = Client.builder().apiKey(apiKey).build();
    }

    public String generateReview(String movieTitle) throws IOException, HttpException {

        GenerateContentResponse response =
                client.models.generateContent(
                        "gemini-3.6-flash",
                        "Write a short review for the movie: "
                                + movieTitle,
                        null
                );

        return response.text();
    }
}
