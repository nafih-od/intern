package com.example.educare;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.ai.client.generativeai.GenerativeModel;
import com.google.ai.client.generativeai.java.GenerativeModelFutures;
import com.google.ai.client.generativeai.type.Content;
import com.google.ai.client.generativeai.type.GenerateContentResponse;
import com.google.ai.client.generativeai.type.Part;
import com.google.ai.client.generativeai.type.TextPart;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.MoreExecutors;

import java.util.ArrayList;
import java.util.List;

public class Chat_bot extends AppCompatActivity {
    private TextView chatHistory;
    private EditText messageInput;
    private Button sendButton;
    private GenerativeModelFutures model;
    private List<String> conversation = new ArrayList<>(); // To store chat history

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_bot);

        chatHistory = findViewById(R.id.chatHistory);
        messageInput = findViewById(R.id.messageInput);
        sendButton = findViewById(R.id.sendButton);

        // Initialize Gemini API
        String apiKey = "AIzaSyBYMXKtvKw2gFgw5c1dlC1fyEuenZrQu2U"; // Replace with your actual API key
        GenerativeModel gm = new GenerativeModel("gemini-1.5-flash", apiKey);
        model = GenerativeModelFutures.from(gm);

        // Get the predicted snake name from Intent
        String snakeName = getIntent().getStringExtra("snake_name");

        // Initial bot message with snake details query
        String botMessage = "Bot: Hello!";
        conversation.add(botMessage);
        updateChatHistory();

//        // Automatically send query to Gemini AI
//        if (snakeName != null && !snakeName.isEmpty()) {
//            getResponseFromGemini("Tell me about the snake named " + snakeName);
//        }

        sendButton.setOnClickListener(v -> {
            String userMessage = messageInput.getText().toString().trim();
            if (!userMessage.isEmpty()) {
                conversation.add("You: " + userMessage);
                updateChatHistory();
                messageInput.setText(""); // Clear input
                getResponseFromGemini(userMessage);
            }
        });
    }


    private void getResponseFromGemini(String query) {
        List<Part> parts = new ArrayList<>();
        parts.add(new TextPart(query));
        Content content = new Content(parts);

        ListenableFuture<GenerateContentResponse> response = model.generateContent(content);

        Futures.addCallback(response, new FutureCallback<GenerateContentResponse>() {
            @Override
            public void onSuccess(GenerateContentResponse result) {
                String generatedText = result.getText().replace("*", ""); // Remove asterisks
                conversation.add("Bot: " + generatedText);
                runOnUiThread(() -> updateChatHistory());
            }

            @Override
            public void onFailure(Throwable t) {
                conversation.add("Bot: Sorry, I encountered an error: " + t.getMessage());
                runOnUiThread(() -> updateChatHistory());
            }
        }, MoreExecutors.directExecutor());
    }

    private void updateChatHistory() {
        StringBuilder history = new StringBuilder();
        for (String message : conversation) {
            history.append(message).append("\n\n");
        }
        chatHistory.setText(history.toString());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), Home.class);
        startActivity(intent);
    }
}