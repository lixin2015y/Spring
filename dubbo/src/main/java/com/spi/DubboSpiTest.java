package com.spi;

import com.alibaba.dubbo.common.extension.ExtensionLoader;
import org.junit.Test;

import java.util.ServiceLoader;

public class DubboSpiTest {
    @Test
    public void test(){
        ServiceLoader<Animal> serviceLoader = ServiceLoader.load(Animal.class);
        serviceLoader.iterator().forEachRemaining(
                Animal::sayHello
        );
    }

    @Test
    public void test2(){
        ExtensionLoader<Animal> animalExtensionLoader = ExtensionLoader.getExtensionLoader(Animal.class);
        Animal dog = animalExtensionLoader.getExtension("dog");
        dog.sayHello();

    }
}
