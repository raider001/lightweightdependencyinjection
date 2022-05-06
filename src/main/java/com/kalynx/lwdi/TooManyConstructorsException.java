package com.kalynx.lwdi;

/**
 * An exception which is fired when there is more than one constructor in the class
 * which notified the developer they require the @DI annotation.
 */
public class TooManyConstructorsException extends DependencyInjectionException {

    /**
     *
     * @param offendingClass The offending class.
     */
    public TooManyConstructorsException(Class<?> offendingClass) {
        super(offendingClass.getName() + " has too many constructors. Use @DI annotation to determine which constructor to use.");
    }
}
