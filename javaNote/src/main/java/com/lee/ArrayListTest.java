package com.lee;

import com.lee.eo.User;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class ArrayListTest {

    @Test
    public void test() throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        ArrayList<Object> list = new ArrayList<>();
        Class<?> arrayListClass = Class.forName("java.util.ArrayList");
        Field elementDataField = arrayListClass.getDeclaredField("elementData");
        elementDataField.setAccessible(true);
        for (int i = 0; i < 11; i++) {
            list.add(i);
            System.out.println("elementDataField.get(list) = " + ((Object[]) elementDataField.get(list)).length);
        }


        User[] users = new User[]{
                new User(1, "admin", "admin@qq.com"),
                new User(2, "maco", "maco@qq.com"),
                new User(3, "kitty", "kitty@163.com")
        };
        Object[] target = users;
        System.out.println(target.getClass());  // class [Ltest.User;
        target[0] = new Object();   // java.lang.ArrayStoreException: java.lang.Object
        System.out.println(list.toArray().getClass());

        list.listIterator().forEachRemaining(o -> System.out.println(o));

    }


    @Test
    public void test2() {
        Object[] a = {};
        Object[] b = {};
        System.out.println(a == b);
        int c = 6;
        System.out.println(c >> 1);

    }

}
