package com.lee.jvm;

import com.sun.crypto.provider.DESKeyGenerator;
import sun.misc.Launcher;

import java.net.URL;
import java.util.Arrays;

public class TestJdkClassLoader {
    public static void main(String[] args) {
        // String 类由引导类加载器加载，事JVM生成，C++对象，所以这里加载为NULL
        System.out.println("String.class.getClassLoader() = " + String.class.getClassLoader());
        System.out.println("com.sun.crypto.provider.DESKeyGenerator.class.getClassLoader() = " + DESKeyGenerator.class.getClassLoader());
        System.out.println("TestJdkClassLoader.class.getClassLoader() = " + TestJdkClassLoader.class.getClassLoader());
        System.out.println();

        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
        ClassLoader extClassLoader = systemClassLoader.getParent();
        ClassLoader bootstrapClassLoader = extClassLoader.getParent();
        System.out.println("bootstrapClassLoader = " + bootstrapClassLoader);
        System.out.println("extClassLoader = " + extClassLoader);
        System.out.println("systemClassLoader = " + systemClassLoader);

        System.out.println();
        System.out.println("启动类加载器加载以下路径");
        URL[] urLs = Launcher.getBootstrapClassPath().getURLs();
        Arrays.stream(urLs).forEach(System.out::println);
        System.out.println("扩展类加载器加载以下路径");
        System.out.println(System.getProperty("java.ext.dirs"));
        System.out.println("应用类加载器加载以下路径");
        System.out.println(System.getProperty("java.class.path"));

    }
}
