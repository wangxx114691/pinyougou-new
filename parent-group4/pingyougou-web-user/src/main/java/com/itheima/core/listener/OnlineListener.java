package com.itheima.core.listener;

import org.springframework.data.redis.core.RedisTemplate;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class OnlineListener implements HttpSessionListener{
    private int count = 0;


    public OnlineListener() {

    }

    public void sessionCreated(HttpSessionEvent se) {

        count++;
        ServletContext context = se.getSession().getServletContext();
        context.setAttribute("totalUserU", String.valueOf(count));
    }

    public void sessionDestroyed(HttpSessionEvent se) {

        count--;
        ServletContext context = se.getSession().getServletContext();
        context.setAttribute("totalUserU", String.valueOf(count));
    }
}
