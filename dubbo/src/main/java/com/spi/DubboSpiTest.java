package com.spi;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.extension.ExtensionLoader;
import com.alibaba.dubbo.rpc.Filter;
import org.junit.Test;

import java.util.List;
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
        Animal cat = animalExtensionLoader.getExtension("cat");
        cat.sayHello();
        Animal dog = animalExtensionLoader.getExtension("dog");
        dog.sayHello();

        ExtensionLoader<Color> colorLoader = ExtensionLoader.getExtensionLoader(Color.class);
        Color red = colorLoader.getExtension("red");
        red.sayMyColor();
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
