package com.pkrete.xrd4j.common.security;

/**
 * This interface defines operations for encrypting strings.
 *
 * @author Petteri Kivimäki
 */
public interface Encrypter {

    /**
     * Encrypts the given string.
     *
     * @param plainText string to be encrypted
     * @return encrypted string
     */
    public String encrypt(String plainText);
}
