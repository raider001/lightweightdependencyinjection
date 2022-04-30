package com.lazyproductions.lwdi.testclasses;

import com.lazyproductions.lwdi.DI;

public class ComplexClass {

    @DI
    public ComplexClass(SimpleClassWithAnnotation clz1, SimpleClassWithoutAnnotation clz2) {

    }
}
