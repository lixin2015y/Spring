package com.tx.annotation;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test1 {


    BookShopDao bookShopDao;

    BookShopService bookShopService;

    @Before
    public void init() {
        final ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("JdbcTemplate.xml");
        bookShopDao = context.getBean("bookShopDaoImpl", BookShopDao.class);
        bookShopService = context.getBean("bookShopServiceImpl", BookShopService.class);
    }


    @Test
    public void test1() {
        bookShopService.byBook("Tom", "ISBN-001");
    }
}
