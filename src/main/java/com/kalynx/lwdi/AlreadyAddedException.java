package com.kalynx.lwdi;

/**
 * An exception that gets thrown an a dependency has already been added.
 */
public class AlreadyAddedException extends DependencyInjectionException {

    /**
     *
     * @param offendingClass The class that throws the exception.
     */
    public AlreadyAddedException(Class<?> offendingClass) {
        super(offendingClass.getName() + " has already been added to the framework.");
    }
}
