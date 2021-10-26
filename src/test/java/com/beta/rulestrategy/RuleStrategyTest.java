package com.beta.rulestrategy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class RuleStrategyTest {

    @Autowired
    private RuleFactory ruleFactory;

    @BeforeEach
    void setUp() {
    }

    @Test
    @DisplayName("Test reverse rule")
    void testRuleOne() {
        Strategy strategy =  ruleFactory.getRuleInstance(1);
        assertEquals(strategy.applyRule("manish"), "hsinam");
    }

    @Test
    @DisplayName("Test hash rule")
    void testRuleTwo() {
        Strategy strategy =  ruleFactory.getRuleInstance(2);
        assertEquals(strategy.applyRule("manish"), "59c95189ac895fcc1c6e1c38d067e244");
    }

    @Test
    @DisplayName("Test hash rule")
    void testRuleTwoOne() {
        Strategy strategy =  ruleFactory.getRuleInstance(1);
        String firstConversion = strategy.applyRule("manish");
        String secondConversion = ruleFactory.getRuleInstance(2).applyRule(firstConversion);
        assertEquals(secondConversion, "91fab82f2ae4decb76fc4d4c809d0421");
    }
}
