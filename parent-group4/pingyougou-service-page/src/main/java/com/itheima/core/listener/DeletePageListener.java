package com.itheima.core.listener;

import com.itheima.core.StaticPageService;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.ServletContextAware;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.servlet.ServletContext;

public class DeletePageListener implements MessageListener,ServletContextAware {
    @Autowired
    private StaticPageService staticPageService;    // 这步注入,在静态化还在sellergoods完成时是非常麻烦, 因为这个静态化服务和pageListener不再一个service,这样是互相看不到的
    // 所以整体将这个service挪过来了, 把静态的化的功能完全放到这个层中完成
    private ServletContext servletContext;

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
    // 获取全路径,当然这个方法可以不写,直接写在上面的方法中
    private String getPath(String path) {
        return servletContext.getRealPath(path);    // 记住这个方法, 获取的是绝对路径, 还有一个常用的getContextPath获取的是项目路径
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;   // 将获取到的servletContext赋值给这个类的成员变量，这样其他方法就可以通用了
    }
}
