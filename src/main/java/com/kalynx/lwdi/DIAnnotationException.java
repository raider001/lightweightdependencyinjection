package com.kalynx.lwdi;

/**
 * An exception that is fired when no or more than one constructor has been given the @DI annotation.
 */
public class DIAnnotationException extends DependencyInjectionException {

    public DIAnnotationException(Class<?> offendingClass) {
        super(offendingClass.getName() + " must have one @DI annotation.");
    }
}
