package com.lazyproductions.lwdi.testclasses;

import com.lazyproductions.lwdi.DI;

public class SimpleClassWithMultipleDiAnnotation {

    @DI
    public SimpleClassWithMultipleDiAnnotation() {

    }

    @DI
    public SimpleClassWithMultipleDiAnnotation(SimpleClassWithoutAnnotation a) {

    }
}
