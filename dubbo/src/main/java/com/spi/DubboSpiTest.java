package com.spi;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.extension.ExtensionLoader;
import com.alibaba.dubbo.rpc.Filter;
import com.spi.service.Animal;
import org.junit.Test;

import java.util.List;
import java.util.ServiceLoader;

public class DubboSpiTest {
    @Test
    public void test(){
        URL url = new URL("http", "localhost", 8080);
        url.addParameter("color", "red");

        ServiceLoader<Animal> serviceLoader = ServiceLoader.load(Animal.class);
        serviceLoader.iterator().forEachRemaining(
                animal -> animal.sayHello()
        );
    }

    @Test
    public void test2(){
        ExtensionLoader<Animal> animalExtensionLoader = ExtensionLoader.getExtensionLoader(Animal.class);
        Animal dog = animalExtensionLoader.getExtension("dog");
        dog.sayHello();
    }

    @Test
    public void test3() {
        ExtensionLoader<Filter> extensionLoader = ExtensionLoader.getExtensionLoader(Filter.class);
        URL url = new URL("http://", "localhost", 8080);
        url = url.addParameter("cache", "test");

        List<Filter> activateExtensions = extensionLoader.getActivateExtension(url, new String[]{"validation"},
                "consumer");
        for (Filter activateExtension : activateExtensions) {
            System.out.println(activateExtension);
        }
    }
}
