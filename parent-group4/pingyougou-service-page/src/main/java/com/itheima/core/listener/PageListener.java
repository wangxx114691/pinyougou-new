package com.itheima.core.listener;

import com.itheima.core.StaticPageService;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

public class PageListener implements MessageListener {
    @Autowired
    private StaticPageService staticPageService;    // 这步注入,在静态化还在sellergoods完成时是非常麻烦, 因为这个静态化服务和pageListener不再一个service,这样是互相看不到的
    // 所以整体将这个service挪过来了, 把静态的化的功能完全放到这个层中完成

    @Override
    public void onMessage(Message message) {
        ActiveMQTextMessage atm = (ActiveMQTextMessage) message;
        try {
            String id = atm.getText();
            System.out.println("page层接收到了sellergoods发的信息: "+id);

            // 这一步也是由sellergoods导过来的,之前和更新到索引库是一起的
            // 3.生成静态化页面
            staticPageService.index(Long.parseLong(id));

        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
