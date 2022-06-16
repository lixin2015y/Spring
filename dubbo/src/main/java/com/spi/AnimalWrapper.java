package com.spi;

import com.spi.service.Animal;

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
