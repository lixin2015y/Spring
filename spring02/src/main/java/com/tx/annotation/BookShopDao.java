package com.tx.annotation;


public interface BookShopDao {


    /**
     * 根据书号查询价格
     */
    public Integer findPriceByIsbn(String isbn);

    /**
     * 跟新库存
     */
    public void updateStock(String isbn);

    /**
     * 跟新余额
     */
    void updateUserAccount(String username, Integer price);


}
