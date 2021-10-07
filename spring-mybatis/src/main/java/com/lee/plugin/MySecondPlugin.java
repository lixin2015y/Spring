package com.lee.plugin;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.*;

import java.sql.Statement;
import java.util.Properties;

@Intercepts(
        {
                // 拦截设置参数方法
                @Signature(type = StatementHandler.class, method = "parameterize", args = Statement.class)
        }
)
public class MySecondPlugin implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        System.out.println("调用=========MySecondPlugin.intercept方法，拦截：" + invocation.getMethod().getName());
        Object proceed = invocation.proceed();
        return proceed;
    }

    @Override
    public Object plugin(Object target) {
        System.out.println("调用=========MySecondPlugin.plugin方法，");
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
        System.out.println("调用=========MySecondPlugin.setProperties方法：" + properties.toString());

    }
}
