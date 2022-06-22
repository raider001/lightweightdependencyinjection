package com.kalynx.lwdi;

/**
 * An exception that is thrown when a required dependency hasn't yet been injected.
 */
public class NotAInterfaceException extends DependencyInjectionException {

    /**
     *
     * @param offendingClass The class that throw the exception
     * @param requiredDependency The dependency needing to be injected before the offending class.
     */
    public NotAInterfaceException(Class<?> offendingClass) {
        super(offendingClass.getName() + " is not an interface.");
    }
}
