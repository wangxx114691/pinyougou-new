package com.itheima.core.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.wxpay.sdk.WXPayUtil;
import com.itheima.core.SeckillGoodspayService;

import com.itheima.core.dao.seckill.SeckillOrderDao;
import com.itheima.core.pojo.seckill.SeckillOrder;
import com.itheima.core.pojo.seckill.SeckillOrderQuery;
import com.itheima.core.utils.HttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SeckillGoodspayImpl implements SeckillGoodspayService {

    @Value("${appid}")
    private String appid;

    @Value("${partner}")
    private String partner;

    @Value("${partnerkey}")
    private String partnerkey;
    @Autowired
    private SeckillOrderDao seckillOrderDao;


    @SuppressWarnings("Duplicates")
    @Override
    public Map<String, String> createNative(String name) {
        SeckillOrderQuery seckillOrderQuery = new SeckillOrderQuery();
        seckillOrderQuery.createCriteria().andUserIdEqualTo(name);
        seckillOrderQuery.createCriteria().andStatusEqualTo("0");
        List<SeckillOrder> seckillOrders = seckillOrderDao.selectByExample(seckillOrderQuery);
        SeckillOrder seckillOrder = null;
        if(null != seckillOrders && seckillOrders.size() > 0){
            for (SeckillOrder order : seckillOrders) {
                if("0".equals(order.getStatus())){
                    seckillOrder = order;
                    break;
                }
            }
        }

        HashMap<String, String> parmeMap = new HashMap<>();
        parmeMap.put("appid",appid);
        parmeMap.put("mch_id",partner);
        parmeMap.put("nonce_str", WXPayUtil.generateNonceStr());
        parmeMap.put("body","品优购结账");
        parmeMap.put("out_trade_no",seckillOrder.getTransactionId());
        parmeMap.put("total_fee",String.valueOf(seckillOrder.getMoney()));
        parmeMap.put("spbill_create_ip","127.0.0.1");
        parmeMap.put("notify_url","127.0.0.1");
        parmeMap.put("total_fee","1");
        parmeMap.put("notify_url","www.itcast.cn");
        parmeMap.put("trade_type","NATIVE");


        try {
            String xml = WXPayUtil.generateSignedXml(parmeMap, partnerkey);

            //apach 提供了HTTPS的转换工具
            String url = "https://api.mch.weixin.qq.com/pay/unifiedorder";
            HttpClient httpClient = new HttpClient(url);
            httpClient.setHttps(true);
            httpClient.setXmlParam(xml);
            httpClient.post();
            String result = httpClient.getContent();
            Map<String, String> map = WXPayUtil.xmlToMap(result);
            map.put("out_trade_no",seckillOrder.getTransactionId());
            map.put("total_fee",String.valueOf(seckillOrder.getMoney()));

            seckillOrder.setStatus("1");
            seckillOrderDao.updateByPrimaryKey(seckillOrder);

            return map;//里面包括了二维码连接。

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

    @SuppressWarnings("Duplicates")
    @Override
    public Map<String, String> queryPayStatus(Long out_trade_no) {
        Map<String,String> paremap = new HashMap<>();
        paremap.put("appid",appid);
        paremap.put("mch_id",partner);
        paremap.put("nonce_str", WXPayUtil.generateNonceStr());
        paremap.put("out_trade_no",String.valueOf(out_trade_no));
        try {
            String xml = WXPayUtil.generateSignedXml(paremap, partnerkey);
            String url = "https://api.mch.weixin.qq.com/pay/orderquery";
            HttpClient client = new HttpClient(url);
            client.setHttps(true);
            client.setXmlParam(xml);
            client.post();

            String result = client.getContent();
            Map<String, String> map = WXPayUtil.xmlToMap(result);



            return map;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
