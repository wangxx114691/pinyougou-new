package com.itheima.core.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.core.GoodsService;
import com.itheima.core.StaticPageService;
import com.itheima.core.dao.good.BrandDao;
import com.itheima.core.dao.good.GoodsDao;
import com.itheima.core.dao.good.GoodsDescDao;
import com.itheima.core.dao.item.ItemCatDao;
import com.itheima.core.dao.item.ItemDao;
import com.itheima.core.dao.seller.SellerDao;
import com.itheima.core.pojo.good.Goods;
import com.itheima.core.pojo.good.GoodsQuery;
import com.itheima.core.pojo.item.Item;
import com.itheima.core.pojo.item.ItemQuery;
import entity.PageResult;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.data.solr.core.query.SolrDataQuery;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.transaction.annotation.Transactional;
import pojogroup.GoodsVo;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@Transactional
public class GoodsServiceImpl implements GoodsService {
    @Autowired
    private GoodsDao goodsDao;
    @Autowired
    private GoodsDescDao goodsDescDao;
    @Autowired
    private ItemDao itemDao;
    @Autowired
    private ItemCatDao itemCatDao;
    @Autowired
    private SellerDao sellerDao;
    @Autowired
    private BrandDao brandDao;
    @Override
    public void add(GoodsVo vo) {
        // 封装商品Goods
        // 设置装填信息
        vo.getGoods().setAuditStatus("0");
        goodsDao.insertSelective(vo.getGoods());    // 同时因为要返回id以供Desc进行添加所以需要使用回显
        // 封装商品详情GoodsDesc
        vo.getGoodsDesc().setGoodsId(vo.getGoods().getId());    // 封装会显得商品ID
        goodsDescDao.insertSelective(vo.getGoodsDesc());
        // 封装库存商品结果集
        // 先判断规格是否被勾选
        if ("1".equals(vo.getGoods().getIsEnableSpec())){
            List<Item> itemList = vo.getItemList(); //获取前端页面传过来的库存列表
            for (Item item : itemList) {
                // 封装商品表的名
                String title = vo.getGoods().getGoodsName();
                String itemSpec = item.getSpec();// 获取的规格名就是为了和商品表名拼接, 但是一个字符串
                Map<String,String> map = JSON.parseObject(itemSpec, Map.class);
                Set<Map.Entry<String, String>> entries = map.entrySet();
                for (Map.Entry<String, String> entry : entries) {
                    title += " "+entry.getValue(); // 拼接商品名和规格
                }
                item.setTitle(title);   // 拼接好的放回到每一个库存商品
                String itemImages = vo.getGoodsDesc().getItemImages();  // 此时是一个数组的字符串形式
                // 因为这个虽然是商品详情的图片,但是搜索的时候还是需要显示到页面的, 所以需要需要先从详情中获取到图片 信息, 然后再写到库存信息中
                List<Map> maps = JSON.parseArray(itemImages, Map.class);
                if (null != maps && maps.size() > 0){
                    item.setImage((String) maps.get(0).get("url")); // 这里只显示第一张图片
                }
                Long category3Id = vo.getGoods().getCategory3Id();
                item.setCategoryid(category3Id);    // 设置分类ID
                item.setCategory(itemCatDao.selectByPrimaryKey(category3Id).getName());     // 设置分类名
                item.setGoodsId(vo.getGoods().getId()); // 商品表的id是本表的外键
                String sellerId = vo.getGoods().getSellerId();
                item.setSellerId(sellerId);  // 存入商家id, 因为是一对多关系, 所以需要进行设置
                item.setSeller(sellerDao.selectByPrimaryKey(sellerId).getName());   // 商家名字
                item.setBrand(brandDao.selectByPrimaryKey(vo.getGoods().getBrandId()).getName());
                item.setCreateTime(new Date()); // 添加时间
                item.setUpdateTime(new Date()); // 修改时间
                itemDao.insertSelective(item);
            }
        }
    }

    /**
     * 分页查询
     */
    @Override
    public PageResult search(Integer page, Integer rows, Goods goods) {
        // 封装分页信息
        PageHelper.startPage(page,rows);
        PageHelper.orderBy("id desc");
        GoodsQuery query = new GoodsQuery();
        GoodsQuery.Criteria criteria = query.createCriteria();
        if (null != goods.getSellerId() && !"".equals(goods.getSellerId())){
            criteria.andSellerIdEqualTo(goods.getSellerId()); // 先根据公司名进行查询
        }
        if (null != goods.getAuditStatus() && !"".equals(goods.getAuditStatus())){
            criteria.andAuditStatusEqualTo(goods.getAuditStatus()); // 再判断审核状态
        }
        if (null != goods.getGoodsName() && !"".equals(goods.getGoodsName().trim())){
            criteria.andGoodsNameLike("%"+goods.getGoodsName().trim()+"%");  // 其次是商品名
        }
        criteria.andIsDeleteIsNull();   // 最后是未删除的,已删除的就不显示了
        Page<Goods> p = (Page<Goods>) goodsDao.selectByExample(query);
        return new PageResult(p.getTotal(),p.getResult());
    }

    @Override
    public void update(GoodsVo vo) {
        // 首先更新goods对象
        goodsDao.updateByPrimaryKeySelective(vo.getGoods());
        // 其次更新goodsDesc对象
//        vo.getGoodsDesc().setGoodsId(vo.getGoods().getId());
//        GoodsDescQuery query = new GoodsDescQuery();
//        GoodsDescQuery.Criteria criteria = query.createCriteria();
//        criteria.andGoodsIdEqualTo(vo.getGoods().getId());
        goodsDescDao.updateByPrimaryKeySelective(vo.getGoodsDesc());
        // 更新库存对象item
        ItemQuery query = new ItemQuery();
        query.createCriteria().andGoodsIdEqualTo(vo.getGoods().getId());
        itemDao.deleteByExample(query); // 先根据GoodsId进行循环删除
        if ("1".equals(vo.getGoods().getIsEnableSpec())) {
            List<Item> itemList = vo.getItemList(); //获取前端页面传过来的库存列表
            for (Item item : itemList) {
                String title = vo.getGoods().getGoodsName();
                String itemSpec = item.getSpec();// 获取的规格名就是为了和商品表名拼接, 但是一个字符串
                Map<String, String> map = JSON.parseObject(itemSpec, Map.class);
                Set<Map.Entry<String, String>> entries = map.entrySet();
                for (Map.Entry<String, String> entry : entries) {
                    title += " " + entry.getValue(); // 拼接商品名和规格
                }
                item.setTitle(title);   // 拼接好的放回到每一个库存商品
                String itemImages = vo.getGoodsDesc().getItemImages();  // 此时是一个数组的字符串形式
                // 因为这个虽然是商品详情的图片,但是搜索的时候还是需要显示到页面的, 所以需要需要先从详情中获取到图片 信息, 然后再写到库存信息中
                List<Map> maps = JSON.parseArray(itemImages, Map.class);
                if (null != maps && maps.size() > 0) {
                    item.setImage((String) maps.get(0).get("url")); // 这里只显示第一张图片
                }
                Long category3Id = vo.getGoods().getCategory3Id();
                item.setCategoryid(category3Id);    // 设置分类ID
                item.setCategory(itemCatDao.selectByPrimaryKey(category3Id).getName());     // 设置分类名
                item.setGoodsId(vo.getGoods().getId()); // 商品表的id是本表的外键
                String sellerId = vo.getGoods().getSellerId();
                item.setSellerId(sellerId);  // 存入商家id, 因为是一对多关系, 所以需要进行设置
                item.setSeller(sellerDao.selectByPrimaryKey(sellerId).getName());   // 商家名字
                item.setBrand(brandDao.selectByPrimaryKey(vo.getGoods().getBrandId()).getName());
                item.setUpdateTime(new Date()); // 修改时间
                itemDao.insertSelective(item);
            }
        }
    }

    @Override
    public GoodsVo findOne(Long id) {
        GoodsVo vo = new GoodsVo();
        // goods对象
        vo.setGoods(goodsDao.selectByPrimaryKey(id));
        // goodsDesc
        vo.setGoodsDesc(goodsDescDao.selectByPrimaryKey(vo.getGoods().getId()));
        // 获取Item结果集
        ItemQuery query = new ItemQuery();
        ItemQuery.Criteria criteria = query.createCriteria();
        criteria.andGoodsIdEqualTo(vo.getGoods().getId());
        vo.setItemList(itemDao.selectByExample(query));
        return vo;
    }
    @Autowired
    private JmsTemplate jmsTemplate;
    @Autowired  // TODO 这步可能有问题
    private Destination topicPageAndSolrDestination;
    private Destination queueSolrDeleteDestination;
    @Override
    public void updateStatus(Long[] ids, String status) {
        // 1.更新数据库中的状态
        Goods goods = new Goods();
        goods.setAuditStatus(status);
        for (Long id : ids) {
            goods.setId(id);
            goodsDao.updateByPrimaryKeySelective(goods);
            // 使用jmsTemplate实现 发消息
            jmsTemplate.send(topicPageAndSolrDestination , new MessageCreator() {
                @Override
                public Message createMessage(Session session) throws JMSException {
                    return session.createTextMessage(String.valueOf(id));
                }
            });
            // 原先有的步骤
            // 2.更新索引库
            // 3.生成静态化页面


        }

    }

    @Override
    public void delete(Long[] ids) {
        Goods goods = new Goods();
        goods.setIsDelete("1");
        for (Long id : ids) {
            goods.setId(id);
            goodsDao.updateByPrimaryKeySelective(goods);
            // 使用jmsTemplate实现 发消息
            jmsTemplate.send(queueSolrDeleteDestination , new MessageCreator() {
                @Override
                public Message createMessage(Session session) throws JMSException {
                    return session.createTextMessage(String.valueOf(id));
                }
            });
     /*       // 删除索引库
            Criteria criteria = new Criteria("item_goodsid").is(id);
            SolrDataQuery query = new SimpleQuery(criteria);
            solrTemplate.delete(query);
            solrTemplate.commit();
            // 不删除静态化页面
            */
        }
    }


}
