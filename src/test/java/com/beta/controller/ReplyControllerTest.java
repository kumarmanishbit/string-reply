package com.beta.controller;

import com.beta.dto.ReplyMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ReplyControllerTest {

    @Autowired
    private ReplyController replyController;

    @BeforeEach
    void setUp() {
    }

    @Test
    @DisplayName("Test happy path")
    void testReply() {
        ResponseEntity<ReplyMessage> replyMessageResponseEntity =  replyController.replyingRule("11-manish");
        assertEquals(replyMessageResponseEntity.getBody().getMessage(), "manish");
    }

    @Test
    @DisplayName("Test not found rule")
    void testReply404() {
        ResponseEntity<ReplyMessage> replyMessageResponseEntity =  replyController.replyingRule("14-manish");
        assertEquals(replyMessageResponseEntity.getStatusCodeValue(), 404);
    }

    @Test
    @DisplayName("Test bad request")
    void testReply400() {
        ResponseEntity<ReplyMessage> replyMessageResponseEntity =  replyController.replyingRule("111-manish");
        assertEquals(replyMessageResponseEntity.getStatusCodeValue(), 400);
    }

    @Test
    @DisplayName("Test request when input contains 0 rule")
    void testReply404WhenRuleContainZero() {
        ResponseEntity<ReplyMessage> replyMessageResponseEntity =  replyController.replyingRule("01-manish");
        assertEquals(replyMessageResponseEntity.getStatusCodeValue(), 404);
    }

    @Test
    @DisplayName("Test internal server error")
    void testReply500() {
        ResponseEntity<ReplyMessage> replyMessageResponseEntity =  replyController.replyingRule("ab-manish");
        assertEquals(replyMessageResponseEntity.getStatusCodeValue(), 500);
    }

}
