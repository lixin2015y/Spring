package com.spi;

import java.util.Calendar;

public class AnimalWrapper implements Animal {

    Animal animal;

    public AnimalWrapper(Animal animal) {
        this.animal = animal;
    }

    @Override
    public void sayHello() {
        animal.sayHello();
    }
}
