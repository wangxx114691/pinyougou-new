package com.zero.chn.PackagePojo;


import com.zero.chn.core.pojo.good.Goods;
import com.zero.chn.core.pojo.good.GoodsDesc;
import com.zero.chn.core.pojo.item.Item;


import java.io.Serializable;
import java.util.List;

public class GoodsVO implements Serializable {
    private Goods goods;
    private GoodsDesc goodsDesc;
    private List<Item>  itemList;

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
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
