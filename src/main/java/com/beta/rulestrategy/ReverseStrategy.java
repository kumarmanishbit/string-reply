package com.beta.rulestrategy;

import org.springframework.stereotype.Component;

@Component
public class ReverseStrategy implements Strategy{
    @Override
    public String applyRule(String message) {
        StringBuilder stringBuilder = new StringBuilder(message);
        return  stringBuilder.reverse().toString();
    }

    @Override
    public Rule getRule() {
        return Rule.REVERSE;
    }
}
