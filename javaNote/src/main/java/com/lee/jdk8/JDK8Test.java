package com.lee.jdk8;

import com.lee.eo.User;
import org.junit.Test;
import sun.nio.ch.FileKey;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class JDK8Test {

    @FunctionalInterface
    public interface Converter<T, F> {
        T convert(F v);
    }

    @Test
    public void test() {
        Converter<Integer, Integer> converter = Integer::valueOf;
        Integer converted = converter.convert(1);
        System.out.println(converted);   // 123
    }

    @Test
    public void test2() {
        Predicate<String> p1 = (s) -> s.startsWith("lixin");
        Predicate<String> p2 = (s) -> s.endsWith("lixin");
        System.out.println(p1.and(p2).test("lixinasdfasdfaslixin"));


        Function<String, Float> stringFloatFunction = Float::parseFloat;
        Function<String, String> floatIntegerFunction = stringFloatFunction.andThen(String::valueOf);
        Function<String, String> findFirstIndexStr = (s) -> String.valueOf(s.charAt(0));
        System.out.println(floatIntegerFunction.andThen(findFirstIndexStr).apply("123.0"));

        Supplier supplier = User::new;
        System.out.println(supplier.get());

        Consumer consumer = p -> System.out.println(p);
        consumer.accept(new User());

        System.out.println("Optional.of(\"lixin\") = " + Optional.of("lixin"));
        System.out.println("Optional.of(null).orElse(\"lixin\") = " + Optional.ofNullable(null).orElse("lixin"));

    }

    @Test
    public void test3() {
        final TreeSet<Integer> set = new TreeSet<>();
        set.add(2);
        set.add(1);
        set.add(6);
        set.add(6);
//        set.add(null);
        final Iterator<Integer> iterator = set.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }

    @Test
    public void test4() {
        final HashSet set = new HashSet();
        set.add(1);
        set.add(3);
        set.add(2);
        set.add(2);
        set.add(null);
        final Iterator iterator = set.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }

    @Test
    public void test5() {
        final LinkedHashSet<Integer> set = new LinkedHashSet<>();
        set.add(1);
        set.add(3);
        set.add(2);
        set.add(2);
        set.add(null);
        final Iterator iterator = set.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }


}
