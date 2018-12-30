package com.itheima.core.listener;

import com.itheima.core.dao.good.GoodsDao;
import com.itheima.core.dao.item.ItemDao;
import com.itheima.core.pojo.item.Item;
import com.itheima.core.pojo.item.ItemQuery;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.SolrTemplate;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import java.util.List;

public class ItemSearchListener implements MessageListener {
    @Autowired
    private ItemDao itemDao;
    @Autowired
    private SolrTemplate solrTemplate;
    @Autowired
    private GoodsDao goodsDao;

    @Override
    public void onMessage(Message message) {
        ActiveMQTextMessage atm = (ActiveMQTextMessage) message;
        try {
            String id = atm.getText();
            System.out.println("search层接收到了sellergoods传来的信息: "+id);

            // 这步是由这钱sellgoods迁移过来的, sellergoods中是新建索引和生成惊天话页面都是在一起完成的
            //  2.新建索引,添加到索引库中

            ItemQuery query = new ItemQuery();
            query.createCriteria().andGoodsIdEqualTo(Long.parseLong(id)).andStatusEqualTo("1").andIsDefaultEqualTo("1");
            List<Item> itemList = itemDao.selectByExample(query);
            solrTemplate.saveBeans(itemList,100);

        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
