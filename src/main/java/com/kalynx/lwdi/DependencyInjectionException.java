package com.kalynx.lwdi;

/**
 * A generic exception this is the base of all dependency injections.
 */
public abstract class DependencyInjectionException extends Exception {
    public DependencyInjectionException(String message) {
        super(message);
    }
}
