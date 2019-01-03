package com.itheima.core.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.dubbo.config.annotation.Service;

import com.itheima.core.dao.good.GoodsDao;
import com.itheima.core.dao.good.GoodsDescDao;
import com.itheima.core.dao.item.ItemDao;
import com.itheima.core.dao.seckill.SeckillGoodsDao;
import com.itheima.core.pojo.good.Goods;
import com.itheima.core.pojo.good.GoodsDesc;
import com.itheima.core.pojo.good.seckillGoodVO;
import com.itheima.core.pojo.item.Item;
import com.itheima.core.pojo.item.ItemQuery;
import com.itheima.core.pojo.seckill.SeckillGoods;
import com.itheima.core.seckillGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;


import java.util.List;
import java.util.Map;

@Service
public class seckillGoodsServiceImpl implements seckillGoodsService {

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private SeckillGoodsDao seckillGoodsDao;

    @Autowired
    private GoodsDescDao goodsDescDao;

    @Autowired
    private ItemDao itemDao;


    @Autowired
    private GoodsDao goodsDao;

    @Override
    public List<SeckillGoods> findSeckillGoods() {
      List<SeckillGoods> seckillGoodsList = (List<SeckillGoods>) redisTemplate.boundHashOps("seckillGood").values();
        for (SeckillGoods seckillGoods : seckillGoodsList) {
            String smallPic = seckillGoods.getSmallPic();
            if(smallPic != null){
                List<Map> mapList = JSON.parseArray(smallPic, Map.class);
                for (Map map : mapList) {
                    String url = (String) map.get("url");
                    seckillGoods.setSmallPic(url);
                }
            }
        }
      return seckillGoodsList ;

    }

    @Override
    public seckillGoodVO findOneFromRedis(Long id) {
        List<SeckillGoods> seckillGoodList = redisTemplate.boundHashOps("seckillGood").values();

        seckillGoodVO seckillGoodVO = new seckillGoodVO();
        SeckillGoods seckillGoods = null;
        GoodsDesc goodsDesc = null;
        Goods goods1 = null;
        for (SeckillGoods goods : seckillGoodList) {
            if(id.equals(goods.getGoodsId())){
                seckillGoods = (SeckillGoods)redisTemplate.boundHashOps("seckillGood").get(goods.getId());
                goodsDesc = goodsDescDao.selectByPrimaryKey(seckillGoods.getGoodsId());
                ItemQuery itemQuery = new ItemQuery();
                itemQuery.createCriteria().andGoodsIdEqualTo(seckillGoods.getGoodsId());
                List<Item> itemList = itemDao.selectByExample(itemQuery);
                 goods1 = goodsDao.selectByPrimaryKey(id);
                seckillGoodVO.setGoods(goods1);
                seckillGoodVO.setGoodsDesc(goodsDesc);
                seckillGoodVO.setSeckillGoods(seckillGoods);
                seckillGoodVO.setItemList(itemList);
                break;
            }
        }
        return seckillGoodVO;
    }

   /* @Override
    public SeckillGoods findOneFromRedis(Long id) {
         List<SeckillGoods> seckillGoodsList = redisTemplate.boundHashOps("seckillGood").values();
         SeckillGoods goods = null;
        for (SeckillGoods seckillGoods : seckillGoodsList) {
            if(id.equals(seckillGoods.getGoodsId())){
               goods = (SeckillGoods) redisTemplate.boundHashOps("seckillGood").get(seckillGoods.getId());
               break;
            }
        }
        return goods;
    }*/


}
