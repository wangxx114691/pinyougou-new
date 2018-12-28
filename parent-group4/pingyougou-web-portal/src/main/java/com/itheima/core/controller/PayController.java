package com.itheima.core.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.core.PayService;
import entity.Result;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("pay")
public class PayController {

    @Reference
    private PayService payService;

    @RequestMapping("createNative")
    public Map<String, String> createNative() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        return payService.creatNative(name);
    }

    @RequestMapping("queryPayStatus")
    public Result queryPayStatus(String out_trade_no) {
        try {
            int count = 0;
            while (true) {
                Map<String, String> map = payService.queryPayStatus(out_trade_no);
                if (null != map) {
                    String tradeState = map.get("trade_state");
                    if ("SUCCESS".equals(tradeState)) {// 有五种交易状态
                        System.out.println(tradeState);
                        return new Result(true, "支付成功");
                    }
                    if ("NOTPAY".equals(tradeState) || "PAYERROR".equals(tradeState)
                            || "REVOKED".equals(tradeState) || "USERPAYING".equals(tradeState)) {
                        Thread.sleep(5000);
                        count++;
                        if (count > 100) {
                            return new Result(false, "二维码超时");
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "支付失败");
        }
    }
}
