package com.kalynx.lwdi;

/**
 * An exception that is thrown when a required dependency hasn't yet been injected.
 */
public class DependenciesDontExistException extends DependencyInjectionException {

    /**
     *
     * @param offendingClass The class that throw the exception
     * @param requiredDependency The dependency needing to be injected before the offending class.
     */
    public DependenciesDontExistException(Class<?> offendingClass, Class<?> requiredDependency) {
        super(offendingClass.getName() + " requires " + requiredDependency.getName() + " to be injected first.");
    }
}
