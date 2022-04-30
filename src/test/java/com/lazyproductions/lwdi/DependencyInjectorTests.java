package com.lazyproductions.lwdi;

import com.lazyproductions.lwdi.testclasses.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DependencyInjectorTests {

    @Test
    public void add_whenClassAdded_classIsAccessible() {
        DependencyInjector dependencyInjector = new DependencyInjector();
        SimpleClassWithAnnotation simpleClass = dependencyInjector.add(new SimpleClassWithAnnotation());
        Assertions.assertNotNull(simpleClass);
        Assertions.assertEquals(simpleClass, dependencyInjector.getDependency(SimpleClassWithAnnotation.class));
    }

    @Test
    public void inject_whenClassAddedWithNoDIAnnotation_classRegistered() throws DependencyInjectionException {
        DependencyInjector dependencyInjector = new DependencyInjector();
        SimpleClassWithoutAnnotation obj = dependencyInjector.inject(SimpleClassWithoutAnnotation.class);
        Assertions.assertNotNull(dependencyInjector.getDependency(SimpleClassWithoutAnnotation.class));
        Assertions.assertEquals(obj, dependencyInjector.getDependency(SimpleClassWithoutAnnotation.class));
    }

    @Test
    public void inject_whenClassAddedWithDIAnnotation_classIsAccessible() throws DependencyInjectionException {
        DependencyInjector dependencyInjector = new DependencyInjector();
        SimpleClassWithAnnotation simpleClass = dependencyInjector.inject(SimpleClassWithAnnotation.class);
        Assertions.assertNotNull(simpleClass);
        Assertions.assertEquals(simpleClass, dependencyInjector.getDependency(SimpleClassWithAnnotation.class));
    }

    @Test
    public void inject_whenClassAddedWithOtherDIDependencies_classIsAccessible() throws DependencyInjectionException {
        DependencyInjector dependencyInjector = new DependencyInjector();
        dependencyInjector.inject(SimpleClassWithAnnotation.class);
        dependencyInjector.add(new SimpleClassWithoutAnnotation());
        ComplexClass complexClass = dependencyInjector.inject(ComplexClass.class);
        Assertions.assertNotNull(complexClass);
        Assertions.assertEquals(complexClass, dependencyInjector.getDependency(ComplexClass.class));
    }

    @Test
    public void inject_whenClassAddedWithButDependenciesNotAvailable_dependencyInjectionExceptionThrown() throws DependencyInjectionException {
        DependencyInjector dependencyInjector = new DependencyInjector();
        dependencyInjector.inject(SimpleClassWithAnnotation.class);
        Assertions.assertThrows(DependenciesDontExistException.class, () -> dependencyInjector.inject(ComplexClass.class));
    }

    @Test
    public void inject_moreThanOneConstructorWithDIAnnotation_dependencyInjectionExceptionThrown() throws DependencyInjectionException {
        DependencyInjector dependencyInjector = new DependencyInjector();
        Assertions.assertThrows(DIAnnotationException.class, () -> dependencyInjector.inject(SimpleClassWithMultipleDiAnnotation.class));
    }

    @Test
    public void inject_sameObjectAddedTwice_dependencyInjectionExceptionThrown() throws DependencyInjectionException {
        DependencyInjector dependencyInjector = new DependencyInjector();
        dependencyInjector.inject(SimpleClassWithAnnotation.class);
        Assertions.assertThrows(DIAnnotationException.class, () -> dependencyInjector.inject(SimpleClassWithMultipleDiAnnotation.class));
    }

    @Test
    public void inject_classHadMultipleAnnotationsWithoutDIAnnotation_dependencyInjectionThrown() throws DependencyInjectionException {
        DependencyInjector dependencyInjector = new DependencyInjector();
        dependencyInjector.inject(SimpleClassWithoutAnnotation.class);
        dependencyInjector.inject(SimpleClassWithAnnotation.class);

        Assertions.assertThrows(TooManyConstructorsException.class, () -> dependencyInjector.inject(MultiConstructorWithNoAnnotation.class));

    }

    @Test
    public void inject_classHadMultipleAnnotationsWithDIAnnotation_dependencyInjectionThrown() throws DependencyInjectionException {
        DependencyInjector dependencyInjector = new DependencyInjector();
        dependencyInjector.inject(SimpleClassWithoutAnnotation.class);
        dependencyInjector.inject(SimpleClassWithAnnotation.class);
        MultiConstructorWithAnnotation sut = dependencyInjector.inject(MultiConstructorWithAnnotation.class);
        Assertions.assertNotNull(sut);
        Assertions.assertEquals(sut, dependencyInjector.getDependency(MultiConstructorWithAnnotation.class));

    }
}
