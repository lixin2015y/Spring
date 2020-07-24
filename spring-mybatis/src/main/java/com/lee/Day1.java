package com.lee;

import com.lee.entity.User;
import org.apache.ibatis.annotations.Select;
import org.junit.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

interface UserDao {
    @Select("select * from user where id = #{id} and name = #{name}")
    List<User> selectUser(Integer id, String name);
}


public class Day1 {

    @Test
    public void test() {
        //使用JKD动态代理生成代理类
        UserDao userDao = (UserDao) Proxy.newProxyInstance(UserDao.class.getClassLoader(), new Class[]{UserDao.class}, new InvocationHandler() {
            public Object invoke(Object proxy, Method method, Object[] args) throws InvocationTargetException, IllegalAccessException {
                Select annotation = method.getAnnotation(Select.class);
                String originalSqlStr = annotation.value()[0];

                StringBuilder parseSql = new StringBuilder();
                for (int i = 0; i < originalSqlStr.length(); i++) {
                    if (originalSqlStr.charAt(i) == '#') {
                        //解析
                        parseSqlArgs(originalSqlStr, i);
                    } else {
                        parseSql.append(originalSqlStr.charAt(i));
                    }
                }

                final Object result = method.invoke(new UserDao() {
                    public List<User> selectUser(Integer id, String name) {
                        
                        return null;
                    }
                }, args);
                return result;
            }
        });
        userDao.selectUser(1, "lixin");
    }

    String parseSqlArgs(String originSql, Integer argIndex) {
        if (originSql.charAt(argIndex + 1) != '{') {
            throw new RuntimeException("缺少｛");
        }

        String resultSql = "";

        return resultSql;
    }

}
