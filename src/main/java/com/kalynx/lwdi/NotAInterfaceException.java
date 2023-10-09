package com.kalynx.lwdi;

/**
 * An exception that is thrown when a required dependency hasn't yet been injected.
 */
public class NotAInterfaceException extends DependencyInjectionException {

    /**
     * An exception that occurs where a interface is expected.
     * @param offendingClass The class that throw the exception
      */
    public NotAInterfaceException(Class<?> offendingClass) {
        super(offendingClass.getName() + " is not an interface.");
    }
}
