package com.itheima.core;

import com.itheima.core.pojo.seller.Seller;
import entity.PageResult;

public interface SellerService {
    void add(Seller seller);

    PageResult search(Integer pageNum, Integer pageSize, Seller seller);

    Seller findOne(String sellerId);

    void updateStatus(String sellerId,String status);

    void delete(String[] ids);
}
