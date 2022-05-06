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
    public Map<Class<?>, Object> registeredClasses = new HashMap<>();

    /**
     * Adds a pre-built dependency into the dependency injection framework.
     *
     * This can be particularily useful if you did not want other certain construction objects to be available throughout the system.
     * @param obj The object to be used in the dependency injection framework
     * @return The added object.
     */
    public <T> T add(T obj) throws AlreadyAddedException {
        if(registeredClasses.containsKey(obj.getClass())) throw new AlreadyAddedException(obj.getClass());
        registeredClasses.putIfAbsent(obj.getClass(), obj);
        return obj;
    }

    /**
     * Injects a class into the dependency injector. An exception will be thrown if one of the following rules are broken:
     * <ul>
     *     <li>The class must have exactly one DI annotation, or a single constructor</li>
     *     <li>The dependencies this class has must have already been injected.</li>
     * </ul>
     * @param clz The class to instantiate and allow for future injections
     * @return the instantiated class.
     */
    public <T> T inject(Class<T> clz) throws DependencyInjectionException {

        if(registeredClasses.get(clz) != null)  throw new AlreadyAddedException(clz);

        // We know this is the constructor type because is was derived directly from clz, which must return an instance of T when created.
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
            T newInstance = selectedCtor.newInstance(params);
            registeredClasses.put(clz, newInstance);
            return newInstance;
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
