package com.kalynx.lwdi;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Dependency Injector allows for simple and easy to use dependency injection for projects.
 * By injecting classes and objects into this class
 */
public class DependencyInjector {
    private final Map<Class<?>, Object> registeredClasses = new HashMap<>();

    /**
     * Adds a pre-built dependency into the dependency injection framework.
     *
     * This can be particularly useful if you did not want other certain construction objects to be available throughout the system.
     * @param obj The object to be used in the dependency injection framework.
     * @param <T> The added object.
     * @return The added object.
     * @throws AlreadyAddedException when the dependency has already been added.
     */
    public <T> T add(T obj) throws AlreadyAddedException {
        if(registeredClasses.containsKey(obj.getClass())) throw new AlreadyAddedException(obj.getClass());
        registeredClasses.putIfAbsent(obj.getClass(), obj);
        return obj;
    }

    /**
     * The abstraction or common interface for dependency injection.
     * @param clzInterface The singleton class to retrieve from a central locaiton. This is used to retrieve the registered instance.
     * @param object The instance of the class.
     * @param <T> The type of class being created
     * @param <V> The actual object.
     * @return The given instance for future use if required after registration.
     * @throws DependencyInjectionException Throws an appropriate dependency injection exception based on the issue found.
     */
    public <T, V> T add(Class<T> clzInterface, T object) throws DependencyInjectionException {

        if(registeredClasses.get(clzInterface) != null)  throw new AlreadyAddedException(clzInterface);

        if(!clzInterface.isInterface()) {
            throw new NotAInterfaceException(clzInterface);
        }

        registeredClasses.put(clzInterface, object);

        return object;
    }

    /**
     *
     * Injects an interface based dependency injection to allow swappable concrete objects.
     * @param clzInterface The interface being injected for reference
     * @param actual The concrete implementation intended to be used
     * @param <T> The Interface class type.
     * @param <V> The concrete class that implements the interface
     * @return The concrete class created.
     * @throws DependencyInjectionException Throws an exception based on why the injection failed.
     */
    public <T, V extends T> T inject(Class<T> clzInterface, Class<V> actual) throws DependencyInjectionException {

        if(registeredClasses.get(clzInterface) != null)  throw new AlreadyAddedException(clzInterface);

        if(!clzInterface.isInterface()) {
            throw new NotAInterfaceException(clzInterface);
        }

        T newObject =  objectBuilder(actual);
        registeredClasses.put(clzInterface,newObject);

        return newObject;
    }

    /**
     * Injects a class into the dependency injector. An exception will be thrown if one of the following rules are broken:
     * <ul>
     *     <li>The class must have exactly one DI annotation, or a single constructor.</li>
     *     <li>The dependencies this class has must have already been injected.</li>
     * </ul>
     * @param <T> The instantiated object of the injected class.
     * @param clz The class to instantiate and allow for future injections.
     * @return the instantiated class.
     * @throws DependencyInjectionException is thrown when one of the rules above has been broken.
     */
    public <T> T inject(Class<T> clz) throws DependencyInjectionException {
        if(registeredClasses.get(clz) != null)  throw new AlreadyAddedException(clz);
        T newObject =  objectBuilder(clz);
        registeredClasses.put(clz,newObject);
        return newObject;

    }

    private <T> T objectBuilder(Class<T> clz) throws DependencyInjectionException {
        // We know this is the constructor type because it was derived directly from clz, which must return an instance of T when created.
        List<Constructor<T>> constructors  = Arrays.stream((Constructor<T>[])clz.getConstructors()).filter(ctr -> ctr.getAnnotation(DI.class) != null).collect(Collectors.toList());


        if(constructors.isEmpty()) {
            constructors = Arrays.stream((Constructor<T>[]) clz.getConstructors()).toList();
            if(constructors.size() > 1) throw new TooManyConstructorsException(clz);
        }

        if(constructors.size() != 1) throw new DIAnnotationException(clz);

        Constructor<T> selectedCtor = constructors.get(0);
        Object[] params = new Object[selectedCtor.getParameterTypes().length];

        for(int i = 0; i < selectedCtor.getParameterTypes().length; i++) {
            Class<?> currentParam = selectedCtor.getParameterTypes()[i];
            params[i] = registeredClasses.get(currentParam);

            if(params[i] == null) throw new DependenciesDontExistException(clz, selectedCtor.getParameterTypes()[i]);

        }
        try {
            return selectedCtor.newInstance(params);

        } catch (InstantiationException|IllegalAccessException|InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Provides a way to retrieve a dependency that has been injected. If the dependency does not exist, it will return null.
     * @param dependency The instantiated dependency to retrieve
     * @param <T> The type of dependency to retrieve
     * @return The instantiated dependency
     */
    public <T> T getDependency(Class<T> dependency) {
        return (T) registeredClasses.get(dependency);
    }

}
