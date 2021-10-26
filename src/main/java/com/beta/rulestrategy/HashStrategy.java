package com.beta.rulestrategy;

import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
public class HashStrategy implements Strategy{
    @Override
    public String applyRule(String message) {
        return getMd5(message);
    }

    /**
     * @param input
     * @return
     */
    public static String getMd5(String input)
    {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");

            byte[] messageDigestBytes = messageDigest.digest(input.getBytes());

            BigInteger no = new BigInteger(1, messageDigestBytes);
            String hashtext = no.toString(16);

            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Rule getRule() {
        return Rule.HASH;
    }
}
