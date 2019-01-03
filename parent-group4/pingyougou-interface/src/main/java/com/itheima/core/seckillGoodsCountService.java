package com.itheima.core;


import java.util.Map;

public interface seckillGoodsCountService {
    Map<String,Integer> countSeckillGoodsOrder(String name);

    void print();
}
