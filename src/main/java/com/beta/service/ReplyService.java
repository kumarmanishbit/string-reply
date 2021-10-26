package com.beta.service;

import com.beta.dto.ReplyMessage;
import com.beta.rulestrategy.RuleFactory;
import com.beta.rulestrategy.Strategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ReplyService {

    private static Logger logger = LoggerFactory.getLogger(ReplyService.class);

    @Value("${app.service.max.rule: 2}")
    private int MAX_NUMBER_OF_SUPPORTED_RULES;

    @Value("${app.service.message.delimiter: -}")
    private String DEFAULT_DELIMITER;

    @Autowired
    private RuleFactory ruleFactory;

    /**
     * @param message
     * @return
     */
    public ResponseEntity getReplyMessages(String message) {

        logger.info("Received request for {}", message);

        if(!validateMessage(message)) {
            logger.warn("The input message {}, is not valid", message);
            return new ResponseEntity(new ReplyMessage("Invalid input"), HttpStatus.BAD_REQUEST);
        }

        return processMessage(message);
    }

    /**
     * @param message
     * @return
     */
    private ResponseEntity processMessage(String message) {

        try {
            // delimiter can also be changed. Today if we are supporting "-" as
            // delimiter then it can be changed later without making any code change (through properties file)
            String[] messageRule = message.split(DEFAULT_DELIMITER);

            String[] rule = messageRule[0].split("");

            String processMessage = messageRule[1];

            Strategy strategy = null;

            // Requirement says rules always contain two numbers, however we are supporting
            // this for future CR as well. Consider the use case where we got the support for
            // 5 rules at a time?
            for(int i=0; i < rule.length; i++) {
                strategy = ruleFactory.getRuleInstance(Integer.parseInt(rule[i]));
                if (strategy == null) {
                    logger.warn("The input message {}, is not valid", message);
                    return new ResponseEntity(new ReplyMessage("Invalid input"), HttpStatus.NOT_FOUND);
                }
                processMessage = strategy.applyRule(processMessage);
            }
            return new ResponseEntity(new ReplyMessage(processMessage), HttpStatus.ACCEPTED);
        } catch (Exception ex) {
            logger.error("Error occurred while processing {}", message);
            // wrapping code which may bring unwanted issue in try catch. This
            // will conclude as server error.
            return new ResponseEntity(new ReplyMessage("Unexpected Exception"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * @param message
     * @return
     */
    private boolean validateMessage(String message) {
        if(message == null || message =="") {
            return false;
        }

        String[] messageRule = message.split(DEFAULT_DELIMITER);

        // This will handle use case where user invoke the api
        // with invalid delimiter. eg:- "12--text". This is not a valid one.
        if(messageRule.length !=2) {
           return false;
        }

        /* This enables us to have configurable number of rule support.
            Let say tomorrow we want to extend our support for 5 rules then this
            can be change through environment variable.
            Benefit:- No need to change any code.
         */
        if(messageRule[0].length() > MAX_NUMBER_OF_SUPPORTED_RULES) {
            return false;
        }

        return true;
    }
}
