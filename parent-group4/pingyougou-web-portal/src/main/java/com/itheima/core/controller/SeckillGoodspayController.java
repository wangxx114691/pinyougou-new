package com.zero.chn.core.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.zero.chn.Utils.Result;
import com.zero.chn.core.service.SeckillGoodspayService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("SeckillGoodspay")
public class SeckillGoodspayController {

    @Reference
    private SeckillGoodspayService seckillGoodspayService;


    @RequestMapping("createNative")
    public Map createNative(){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        Map<String,String> map =  seckillGoodspayService.createNative(name);
        return map;
    }

    @SuppressWarnings("Duplicates")
    @RequestMapping("queryPayStatus")
    public Result queryPayStatus(Long out_trade_no){
        int index = 0;
        while (true){
            try {
                Map<String,String> map =  seckillGoodspayService.queryPayStatus(out_trade_no);
                if("SUCCESS".equals(map.get("trade_state"))){
                    System.out.println(map.get("trade_state"));
                    return new Result(true,"成功");
                }else{
                    if("REFUND".equals(map.get("trade_state"))||"NOTPAY".equals(map.get("trade_state"))||"CLOSED".equals(map.get("trade_state"))
                            ||"USERPAYING".equals(map.get("trade_state"))||
                            "PAYERROR".equals(map.get("trade_state"))){
                        Thread.sleep(3000);
                        index++;
                        if(index > 20){
                            //关闭微信客服api
                            return new Result(false,"二维码超时");
                        }

                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
                return new Result(false,"失败");
            }
        }

    }

}
