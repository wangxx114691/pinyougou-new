package com.itheima.core;

import java.util.Map;

public interface PayService {
    Map<String,String> creatNative(String name);

    Map<String,String> queryPayStatus(String out_trade_no);
}
