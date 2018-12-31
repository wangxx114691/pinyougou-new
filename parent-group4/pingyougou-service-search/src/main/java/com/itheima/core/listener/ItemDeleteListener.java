package com.itheima.core.listener;

import org.apache.activemq.command.ActiveMQTextMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.data.solr.core.query.SolrDataQuery;
import org.springframework.web.context.ServletContextAware;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.servlet.ServletContext;

public class ItemDeleteListener implements MessageListener {
    @Autowired
    private SolrTemplate solrTemplate;


    @Override
    public void onMessage(Message message) {
        ActiveMQTextMessage atm = (ActiveMQTextMessage) message;
        try {
            String id = atm.getText();
            System.out.println("search层删除传过来的id相关信息: "+id);
            // 删除索引库
            Criteria criteria = new Criteria("item_goodsid").is(id);
            SolrDataQuery query = new SimpleQuery(criteria);
            solrTemplate.delete(query);
            solrTemplate.commit();


        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

}
