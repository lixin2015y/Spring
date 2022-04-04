package com.lee.spi;

import java.util.ServiceLoader;

public class SpiDemo {
    public static void main(String[] args) {
        ServiceLoader<Animal> load = ServiceLoader.load(Animal.class);
        for (Animal animal : load) {
            animal.sayHello();
        }
    }
}
