package com.app.app;

import com.app.app.model.Message;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;


@SpringBootTest(webEnvironment = RANDOM_PORT)
public class EndpointTests {

    private static final String CONTENT_TYPE = "application/json";

    @LocalServerPort
    private int port;


    @BeforeEach
    public void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    @Test
    void messageCreationTest() {
        int magicNumber = 107;
        String email = "example@gmail.com";

        Message message = new Message(
                magicNumber,
                email,
                "No title",
                "No content"
        );

        Response response = getMessageCreationResponse(message);
        Message createdMessage = response.getBody().as(Message.class);

        assertEquals(200, response.getStatusCode());
        assertEquals(magicNumber, createdMessage.getMagicNumber());
        assertEquals(email, createdMessage.getEmail());
        assertEquals(message.getTitle(), createdMessage.getTitle());
        assertEquals(message.getContent(), createdMessage.getContent());

        clearCreatedMessages(magicNumber);
    }

    @Test
    void messageCreationAndGetByEmailTest() {
        int magicNumber = 107;
        String email = "example@gmail.com";

        Message message = new Message(
                magicNumber,
                email,
                "No title",
                "No content"
        );

        getMessageCreationResponse(message);
        Response response = getMessageByEmailResponse(email);
        Message[] messages = response.as(Message[].class);

        assertEquals(200, response.getStatusCode());
        assertEquals(message.getEmail(), messages[0].getEmail());
        assertEquals(message.getContent(), messages[0].getContent());
        assertEquals(message.getMagicNumber(), messages[0].getMagicNumber());
        assertEquals(message.getTitle(), messages[0].getTitle());

        clearCreatedMessages(magicNumber);
    }

    @Test
    void messageGetByEmailTest() {
        int magicNumber = 107;
        String email = "example@gmail.com";

        Message message = new Message(
                magicNumber,
                email,
                "No title",
                "No content"
        );

        getMessageCreationResponse(message);
        Response response = getMessageByEmailResponse(email);
        Message[] messages = response.body().as(Message[].class);

        assertEquals( 200, response.getStatusCode());
        assertEquals(email, messages[0].getEmail());
        assertEquals(message.getContent(), messages[0].getContent());
        assertEquals(magicNumber, messages[0].getMagicNumber());
        assertEquals(message.getTitle(), messages[0].getTitle());

        clearCreatedMessages(magicNumber);
    }

    @Test
    void messageSendingTest() {
        List<Message> messages = new ArrayList<>();
        int magicNumber = 125;

        for (int i=0; i<5; i++) {
            Message message = new Message(
                    magicNumber,
                    "example@gmail.com",
                    "No title",
                    "No content"
            );

            messages.add(message);
        }

        messages.forEach(this::getMessageCreationResponse);

        Map<String, Integer> requestBody = new HashMap<>();
        requestBody.put("magic_number", magicNumber);

        Response response = getMessagesSendingResponse(requestBody);
        Message[] sentMessages = response.as(Message[].class);

        assertEquals(response.getStatusCode(), 200);
        assertEquals(messages.size(), sentMessages.length);

        for (Message sentMessage : sentMessages) {
            assertEquals(sentMessage.getMagicNumber(), magicNumber);
        }
    }

    @Test
    void messageSendingWrongNumberTest() {
        List<Message> messages = new ArrayList<>();
        int magicNumber = 125;

        for (int i=0; i<5; i++) {
            Message message = new Message(
                    magicNumber,
                    "example@gmail.com",
                    "No title" ,
                    "No content"
            );

            messages.add(message);
        }

        messages.forEach(this::getMessageCreationResponse);

        Map<String, Integer> requestBody = new HashMap<>();
        requestBody.put("magic_number", 130);

        Response response = getMessagesSendingResponse(requestBody);
        Message[] sentMessages = response.as(Message[].class);

        assertEquals( 200, response.getStatusCode());
        assertEquals( 0, sentMessages.length);

        clearCreatedMessages(magicNumber);
    }

    private Response getMessageCreationResponse(Message message) {
        return given()
                .contentType(CONTENT_TYPE)
                .accept(CONTENT_TYPE)
                .body(message)
                .when()
                .post("/api/message")
                .then()
                .contentType(CONTENT_TYPE)
                .extract()
                .response();
    }

    private Response getMessageByEmailResponse(String email) {
        return given()
                .contentType(CONTENT_TYPE)
                .accept(CONTENT_TYPE)
                .when()
                .get("/api/messages/" + email)
                .then()
                .contentType(CONTENT_TYPE)
                .extract()
                .response();
    }

    private Response getMessagesSendingResponse(Map<String, Integer> requestBody) {
        return given()
                .contentType(CONTENT_TYPE)
                .accept(CONTENT_TYPE)
                .body(requestBody)
                .when()
                .post("/api/send")
                .then()
                .contentType(CONTENT_TYPE)
                .extract()
                .response();
    }

    private void clearCreatedMessages(int magicNumber) {
        Map<String, Integer> requestBody = new HashMap<>();
        requestBody.put("magic_number", magicNumber);
        getMessagesSendingResponse(requestBody);
    }
}