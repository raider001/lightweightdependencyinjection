package com.lazyproductions.lwdi;

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
    public <T> T add(T obj) {
        registeredClasses.putIfAbsent(obj.getClass(), obj);
        return obj;
    }

    /**
     * Injects a class into the dependency injector. An exception will be thrown if one of the following rules are broken:
     * <ul>
     *     <li>The class must have exactly one DI annotation.</li>
     *     <li>The dependencies this class has must have already been injected.</li>
     * </ul>
     * @param clz The class to instantiate and allow for future injections
     * @return the instantiated class.
     * @throws DependencyInjectionException
     */
    public <T> T inject(Class<T> clz) throws DependencyInjectionException {

        if(registeredClasses.get(clz) != null)  throw new AlreadyAddedException(clz);

        // We know this is the constructor type because is was derived directly from clz, which must return an instance of T when created.
        //noinspection unchecked
        List<Constructor<T>> constructors  = Arrays.stream((Constructor<T>[])clz.getConstructors()).filter(ctr -> ctr.getAnnotation(DI.class) != null).collect(Collectors.toList());


        if(constructors.isEmpty()) {
            //noinspection unchecked
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

    @SuppressWarnings("unchecked")
    public <T> T getDependency(Class<T> dependency) {
        // Warning suppressed. Cast exception no possible due to how information is stored in a map.
        return (T) registeredClasses.get(dependency);
    }

}
