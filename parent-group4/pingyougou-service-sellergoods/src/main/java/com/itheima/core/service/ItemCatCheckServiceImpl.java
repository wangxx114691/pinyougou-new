package com.itheima.core.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.core.ItemCatCheckService;
import com.itheima.core.dao.item.ItemCatCheckDao;
import com.itheima.core.dao.item.ItemCatDao;
import com.itheima.core.dao.seller.SellerDao;
import com.itheima.core.dao.template.TypeTemplateDao;
import com.itheima.core.pojo.item.ItemCat;
import com.itheima.core.pojo.item.ItemCatCheck;
import com.itheima.core.pojo.item.ItemCatCheckQuery;
import com.itheima.core.pojo.item.ItemCatQuery;
import com.itheima.core.pojo.seller.Seller;
import entity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ItemCatCheckServiceImpl implements ItemCatCheckService {

    @Autowired
    private ItemCatDao itemCatDao;
    @Autowired
    private ItemCatCheckDao itemCatCheckDao;
    @Autowired
    private SellerDao sellerDao;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private TypeTemplateDao typeTemplateDao;
    @Override
    public List<ItemCatCheck> findByParentId(Long parentId, String name) {
        // 第一次查询的时候访问数据库， 并保存到缓冲中， 之后访问缓存
//        List<ItemCatCheck> itemCatList = findAll2();
//        for (ItemCatCheck itemCat : itemCatList) {
            //redisTemplate.boundHashOps("itemCat").put(itemCat.getName(),itemCat.getTypeId());   // 这个hash是 （分类名，模板id）
//        }

        ItemCatCheckQuery query = new ItemCatCheckQuery();
        ItemCatCheckQuery.Criteria criteria = query.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        return itemCatCheckDao.selectByExample(query);
    }

    @Override
    public PageResult search(Integer page, Integer rows, Long parentId, String name) {
        // 分页
        PageHelper.startPage(page,rows);
        ItemCatQuery query = new ItemCatQuery();
        ItemCatQuery.Criteria criteria = query.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        Page<ItemCat> p = (Page<ItemCat>) itemCatDao.selectByExample(query);
        return new PageResult(p.getTotal(),p.getResult());
    }

    @Override
    public void add(ItemCatCheck itemCat, String name) {
        ItemCat cat = new ItemCat();
        cat.setName(itemCat.getName());
        cat.setTypeId(35L);
        itemCatDao.insertSelective(cat);
        itemCat.setSellerId(name);
        Seller seller = sellerDao.selectByPrimaryKey(name);
        itemCat.setSellerName(seller.getName());
        itemCatCheckDao.insertSelective(itemCat);
    }

    @Override
    public void update(ItemCat itemCat) {
        itemCatDao.updateByPrimaryKeySelective(itemCat);
    }

    @Override
    public void delete(Long[] ids) {
//        ItemCatQuery query = new ItemCatQuery();
//        query.createCriteria().andIdIn(Arrays.asList(ids));
//        itemCatDao.deleteByExample(query);
        for (Long id : ids) {
            itemCatDao.deleteByPrimaryKey(id);
        }
    }

    @Override
    public ItemCatCheck findOne(Long id) {
        return itemCatCheckDao.selectByPrimaryKey(id);
    }

    @Override
    public List<ItemCatCheck> findAll2() {
        return itemCatCheckDao.selectByExample(null);
    }
}
