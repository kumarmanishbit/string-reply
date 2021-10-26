package com.beta.rulestrategy;

public interface Strategy {
    /**
     * @param message
     * @return
     */
    String applyRule(String message);

    Rule getRule();
}
