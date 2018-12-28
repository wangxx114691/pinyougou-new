package com.itheima.core.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.wxpay.sdk.WXPayUtil;
import com.itheima.core.PayService;
import com.itheima.core.pojo.log.PayLog;

import com.itheima.core.utils.HttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
public class PayServiceImpl implements PayService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Value("${appid}")
    private String appid;
    @Value("${partner}")
    private String partner;
    @Value("${partnerkey}")
    private String partnerkey;

//    appid=wx8397f8696b538317
//    partner=1473426802
//    partnerkey=T6m9iK73b0kn9g5v426MKfHQH7X8rKwb
    @Override
    public Map<String, String> creatNative(String name) {
        try {
            // 从缓存中获取订单信息, 加快读取速度
            PayLog payLog = (PayLog) redisTemplate.boundHashOps("payLog").get(name);
            // 获取微信支付系统的许可并获取交易code
            // url = https://api.mch.weixin.qq.com/pay/unifiedorder
            String url = "https://api.mch.weixin.qq.com/pay/unifiedorder";
                // 为什么使用的封装的httpClient的一个很大的原因就是,微信支付需要https协议所以需要封装对象
            HttpClient httpClient = new HttpClient(url);
            // 用工具类HttpClient发送请求需要两个参数, 一个请求地址url,另一个是携带参数map
            Map<String,String> map = new HashMap<>();  // 开始封装map请求参数的过程
            map.put("appid",appid); // 公众号id
            map.put("mch_id",partner);  // 商家id
            map.put("nonce_str",WXPayUtil.generateNonceStr());    // 随机字符串就是
            map.put("out_trade_no",payLog.getOutTradeNo());// 交易码
            map.put("total_fee","1");   //订单总金额
            map.put("spbill_create_ip","127.0.0.1");    //终端IP 因为是局域网所以找不到, 填了和没填没有区别
            map.put("body","传智播客求客官行行好, 随手就打赏点吧");// 支付页提示信息
            map.put("notify_url","www.itcast.cn");
            map.put("trade_type","NATIVE");
            // map中比较复杂的就是签名了,通过签名算法计算得出的签名值，详见签名生成算法.  签名默认使用md5加密方式
            String signXml = WXPayUtil.generateSignedXml(map, "T6m9iK73b0kn9g5v426MKfHQH7X8rKwb");
                // 签名需要传两个参数, 第一个就是之前封装的map,第二就是秘钥, 所以签名需要放在其他参数设置好的才进行的原因
            httpClient.setXmlParam(signXml);    // 因为微信支付中心和商家的交互模式都是xml, 所以参数都得转成xml
            httpClient.setHttps(true);
            // 发送请求
            httpClient.post();  // post方法提交参数, 底层调用的是httpPost(url)
            // 接收响应, 但是只能调用getContent方法, 因为没有response
            String content = httpClient.getContent();   // 注意响应回来的参数都是xml形式的
            Map<String, String> toMap = WXPayUtil.xmlToMap(content);// 将响应内容的xml重新解析为map
            // 需要在返回的map中重新 添加交易订单号和需要支付的钱数
            toMap.put("out_trade_no",payLog.getOutTradeNo());
            toMap.put("total_fee",String.valueOf(payLog.getTotalFee()));
            return toMap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Map<String, String> queryPayStatus(String out_trade_no) {
        Map<String,String> map = new HashMap<>();
        map.put("appid",appid);
        map.put("mch_id",partner);
        map.put("out_trade_no",out_trade_no);
        map.put("nonce_str",WXPayUtil.generateNonceStr());
        // 剩下的就是签名了
        try {
            String signXml = WXPayUtil.generateSignedXml(map, partnerkey);
            String url="https://api.mch.weixin.qq.com/pay/orderquery";
            HttpClient httpClient = new HttpClient(url);
            httpClient.setHttps(true);
            httpClient.setXmlParam(signXml);
            httpClient.post();
            String content = httpClient.getContent();
            if (null!=content){
                Map<String, String> toMap = WXPayUtil.xmlToMap(content);
                return toMap;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return null;
    }


}
