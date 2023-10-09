package com.kalynx.lwdi;

/**
 * An exception that gets thrown a dependency has already been added.
 */
public class AlreadyAddedException extends DependencyInjectionException {

    /**
     * A dependency injection error that occurs
     * @param offendingClass The class that throws the exception.
     */
    public AlreadyAddedException(Class<?> offendingClass) {
        super(offendingClass.getName() + " has already been added to the framework.");
    }
}
