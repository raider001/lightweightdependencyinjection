package com.kalynx.lwdi;

/**
 * An exception that is fired when no or more than one constructor has been given the @DI annotation.
 */
public class DIAnnotationException extends DependencyInjectionException {

    /**
     * An exception that fires when a DI Annotation is expected, but not provided.
     * @param offendingClass The class that caused the exception.
     */
    public DIAnnotationException(Class<?> offendingClass) {
        super(offendingClass.getName() + " must have one @DI annotation.");
    }
}
