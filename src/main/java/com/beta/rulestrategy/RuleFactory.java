package com.beta.rulestrategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
public class RuleFactory {

    private Map<Rule, Strategy> strategies;

    @Autowired
    public RuleFactory(Set<Strategy> strategySet) {
        populateRuleStrategy(strategySet);
    }

    public Strategy getRuleInstance(int ruleNumber) {
        Rule[] rules = Rule.values();
        if(rules.length <= ruleNumber || ruleNumber == 0) {
            return null;
        }
        // This is efficient, and O(1) time operation.
        return strategies.get(Rule.values()[ruleNumber]);
    }
    private void populateRuleStrategy(Set<Strategy> strategySet) {
        strategies = new HashMap<>();
        strategySet.forEach(
                strategy ->strategies.put(strategy.getRule(), strategy));
    }
}
