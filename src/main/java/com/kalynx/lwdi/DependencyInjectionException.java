package com.kalynx.lwdi;

/**
 * A generic exception this is the base of all dependency injections.
 */
public abstract class DependencyInjectionException extends Exception {
    /**
     *
     * @param message The message displayed in the exception.
     */
    public DependencyInjectionException(String message) {
        super(message);
    }
}
