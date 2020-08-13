package com.lee;

import com.lee.entity.User;
import org.apache.ibatis.annotations.Select;
import org.junit.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

interface UserDao {
    @Select("select * from user where id = #{id} and name = #{name}")
    List<User> selectUser(Integer id, String name);
}


public class Day1 {

    @Test
    public void test() {
        //使用JKD动态代理生成代理类
        UserDao userDao = (UserDao) Proxy.newProxyInstance(UserDao.class.getClassLoader(), new Class[]{UserDao.class}, new InvocationHandler() {
            public Object invoke(Object proxy, Method method, Object[] args) {
                Select annotation = method.getAnnotation(Select.class);
                Map<String, Object> param = getParam(method, args);
                if (annotation != null) {
                    String[] value = annotation.value();
                    String sql = value[0];
                    System.out.println(parseSqlArgs(sql, param));
                }
                return null;
            }
        });
        userDao.selectUser(1, "lixin");
    }

    String parseSqlArgs(String sql, Map<String, Object> param) {
        StringBuilder sb = new StringBuilder();
        int length = sql.length();
        for (int i = 0; i < length; i++) {
            char c = sql.charAt(i);
            if (c == '#') {
                int nextIndex = i + 1;
                char nextChar = sql.charAt(nextIndex);
                if (nextChar != '{') {
                    throw new RuntimeException("缺少｛");
                }
                StringBuilder arg = new StringBuilder();
                i = getIndex(arg, sql, nextIndex);
                sb.append(param.get(arg.toString()));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    Map<String, Object> getParam(Method method, Object[] arg) {
        Map<String, Object> param = new HashMap<>();
        Parameter[] parameters = method.getParameters();
        int a[] = {0};
        Arrays.stream(parameters).forEach(parameter -> {
            String name = parameter.getName();
            param.put(name, arg[a[0]]);
            a[0]++;
        });
        return param;
    }

    int getIndex(StringBuilder sb, String sql, int index) {
        index++;
        for (; index < sql.length(); index++) {
            char c = sql.charAt(index);
            if (c != '}') {
                sb.append(c);
                continue;
            } else {
                return index;
            }
        }
        throw new RuntimeException("未找到}");
    }

}
