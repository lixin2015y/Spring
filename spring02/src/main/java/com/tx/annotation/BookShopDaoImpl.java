package com.tx.annotation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class BookShopDaoImpl implements BookShopDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public Integer findPriceByIsbn(String isbn) {
        String sql = "select price from book where isbn = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, isbn);
    }

    @Override
    public void updateStock(String isbn) {
        String sql = "select stock from book_stock where isbn = ?";
        final Integer stock = jdbcTemplate.queryForObject(sql, Integer.class, isbn);
        if (stock <= 0) {
            throw new BookStockException("库存不足");
        }
        // 测试单行注释
        sql = "update book_stock set stock = stock -1 where isbn = ?";
        jdbcTemplate.update(sql, isbn);
    }

    @Override
    public void updateUserAccount(String username, Integer price) {

        String sql = "select balance from account where username = ? ";
        final Integer balance = jdbcTemplate.queryForObject(sql, Integer.class, username);
        if (balance < price) {
            throw new UserAccountException("余额不足");
        }
        System.out.println("balance = " + balance);
        sql = "update account set balance = balance - ? where username = ?";
        jdbcTemplate.update(sql, price, username);

    }
}
