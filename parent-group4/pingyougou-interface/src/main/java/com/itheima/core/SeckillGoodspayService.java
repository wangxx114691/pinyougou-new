package com.zero.chn.core.service;

import java.util.Map;

public interface SeckillGoodspayService {
    Map<String,String> createNative(String name);

    Map<String,String> queryPayStatus(Long out_trade_no);
}
