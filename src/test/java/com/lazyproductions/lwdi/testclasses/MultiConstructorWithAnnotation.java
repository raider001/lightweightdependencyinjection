package com.lazyproductions.lwdi.testclasses;

import com.lazyproductions.lwdi.DI;

public class MultiConstructorWithAnnotation {

    @DI
    public MultiConstructorWithAnnotation(SimpleClassWithAnnotation annotation) {

    }

    public MultiConstructorWithAnnotation(SimpleClassWithoutAnnotation annotation) {

    }
}
