package com.itheima.core.pojo.good;



import com.itheima.core.pojo.item.Item;
import com.itheima.core.pojo.seckill.SeckillGoods;

import java.io.Serializable;
import java.util.List;


public class seckillGoodVO implements Serializable {
    private SeckillGoods seckillGoods;
    private GoodsDesc goodsDesc;
    private List<Item> itemList;
    private Goods goods;

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    public SeckillGoods getSeckillGoods() {
        return seckillGoods;
    }

    public void setSeckillGoods(SeckillGoods seckillGoods) {
        this.seckillGoods = seckillGoods;
    }

    public GoodsDesc getGoodsDesc() {
        return goodsDesc;
    }

    public void setGoodsDesc(GoodsDesc goodsDesc) {
        this.goodsDesc = goodsDesc;
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }
}
