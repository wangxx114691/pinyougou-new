package com.itheima.core.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.core.UserService;
import com.itheima.core.dao.user.UserDao;
import com.itheima.core.pojo.good.Goods;
import com.itheima.core.pojo.user.User;
import com.itheima.core.pojo.user.UserQuery;
import entity.PageResult;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.transaction.annotation.Transactional;

import javax.jms.*;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
     *
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
                mapMessage.setString("phone", phone);
                mapMessage.setString("templateCode", "SMS_126462276");//SMS_126462276
                mapMessage.setString("signName", "品优购商城");
                mapMessage.setString("templateParam", "{\"number\":\"" + code + "\"}");
                return mapMessage;
            }
        });
    }

    /**
     * 这个是用户注册用的方法
     *
     * @param user
     * @param smscode
     */
    @Override
    public void add(User user, String smscode) {
        String code = (String) redisTemplate.boundValueOps(user.getPhone()).get();
        if (code != null) {
            // 判断验证码是否一致
            if (code.equals(smscode)) {
                user.setCreated(new Date());// 封装这些必要的信息
                user.setUpdated(new Date());
                userDao.insertSelective(user);
            } else {
                throw new RuntimeException("验证码错误");
            }
        } else {
            // 密码为空
            throw new RuntimeException("验证码已失效");
        }
    }
    // 发送验证码

    @Override
    public Map<String, String> searchCount() {
        Map<String, String> map = new HashMap<>();
        map.put("totalUserU", (String) redisTemplate.boundValueOps("totalUserU").get());
        map.put("totalUserP", (String) redisTemplate.boundValueOps("totalUserP").get());
        return map;
    }

    @Override
    public Long searchCountU() {
        Long size = redisTemplate.boundSetOps("userName").size();
        return size;
    }

    /**
     * 在登录后，调用这个方法，来存储到缓存库的同时， 也更新数据库中的最后登录时间
     *
     * @param name
     */
    @Override
    public void addUserCount(String name) {
        redisTemplate.boundSetOps("userName").add(name);
        UserQuery query = new UserQuery();
        query.createCriteria().andUsernameEqualTo(name);
        List<User> userList = userDao.selectByExample(query);
        User user = userList.get(0);
        user.setLastLoginTime(new Date());
        userDao.updateByPrimaryKeySelective(user);
    }

    @Override
    public Map<String, Integer> searchActive() {
        Map<String, Integer> map = new HashMap<>();
        long now = System.currentTimeMillis();
        List<User> userList = userDao.selectByExample(null);
        int total = userList.size();
        map.put("totalRegister", total);
        int count = 0;
        for (User user : userList) {
            Date lastLoginTime = user.getLastLoginTime();
            long last = 0L;
            if (lastLoginTime != null) {
                last = lastLoginTime.getTime();
            }
            double time = (now - last) * 1.0 / (3600 * 24 * 1000);    // 3天以内的都是活跃用户
            if (time < 3) {
                count++;
            }
        }
        map.put("totalActive", count);
        return map;
    }

    @Override
    public PageResult<User> search(Integer page, Integer rows, User user) {
        PageHelper.startPage(page, rows);
        Page<User> p = null;
        if (user != null) {
            p = (Page<User>) userDao.selectByExample(null);
        } else {
            UserQuery query = new UserQuery();
            if (null != user.getName() && !"".equals(user.getName().trim())) {
                query.createCriteria().andNameEqualTo(user.getName());
            }
            p = (Page<User>) userDao.selectByExample(query);
        }
        return new PageResult<>(p.getTotal(),p.getResult());
    }

    @Override
    public void pointsStatus(Long[] ids) {
        User user = new User();
        user.setPoints(0);
        for (Long id : ids) {
            user.setId(id);
            userDao.updateByPrimaryKeySelective(user);
        }
    }

    @Override
    public void downdele(Long[] ids) {
        User user = new User();
        user.setPoints(1);
        for (Long id : ids) {
            user.setId(id);
            userDao.updateByPrimaryKeySelective(user);
        }
    }
}
