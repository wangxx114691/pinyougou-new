package com.itheima.core.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.core.UserService;
import com.itheima.core.dao.user.UserDao;
import com.itheima.core.pojo.user.User;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.transaction.annotation.Transactional;

import javax.jms.*;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private JmsTemplate jmsTemplate;
    @Autowired
    private Destination smsDestination;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private UserDao userDao;

    /**
     * 这个是用户获取验证码的方法
     * @param phone
     */
    @Override
    public void sendCode(String phone) {
        // 1.生成6位随机数
        String code = RandomStringUtils.randomNumeric(6);//204925
        // 2.设置验证码存活时间
        redisTemplate.boundValueOps(phone).set(code);
        redisTemplate.boundValueOps(phone).expire(5, TimeUnit.DAYS);
        // 3.发消息
        jmsTemplate.send(smsDestination, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                MapMessage mapMessage = session.createMapMessage();
                mapMessage.setString("phone",phone);
                mapMessage.setString("templateCode","SMS_126462276");//SMS_126462276
                mapMessage.setString("signName","品优购商城");
                mapMessage.setString("templateParam","{\"number\":\""+code+"\"}");
                return mapMessage;
            }
        });
    }

    /**
     * 这个是用户注册用的方法
     * @param user
     * @param smscode
     */
    @Override
    public void add(User user, String smscode) {
        String code = (String) redisTemplate.boundValueOps(user.getPhone()).get();
        if (code != null){
            // 判断验证码是否一致
            if (code.equals(smscode)){
                user.setCreated(new Date());// 封装这些必要的信息
                user.setUpdated(new Date());
                userDao.insertSelective(user);
            }else {
                throw new RuntimeException("验证码错误");
            }
        }else {
            // 密码为空
            throw new RuntimeException("验证码已失效");
        }
    }
    // 发送验证码
}
