package com.itheima.core.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.core.ItemSearchService;
import com.itheima.core.pojo.item.Item;
import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.*;
import org.springframework.data.solr.core.query.result.*;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ItemSearchServiceImpl implements ItemSearchService{
    @Autowired
    private SolrTemplate solrTemplate;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public Map<String, Object> search(Map<String, String> searchMap) {
        // 搜索的关键字应该处理下，因为中间可能有很多空格，需要进行去空格操作
        searchMap.put("keywords",searchMap.get("keywords").replaceAll(" ",""));

        // 1.结果集  总条数  总页数
        // 2.价格
        Map<String, Object> map = findItemMap(searchMap);
        // 3.分类
        List<String> categoryList = findCategoryList(searchMap);
        map.put("categoryList",categoryList);
        // 4.品牌
        // 5.规格
        if (null != categoryList && categoryList.size() >0 ){
            Object typeId = redisTemplate.boundHashOps("itemCat").get(categoryList.get(0));// 返回模板ID
            map.put("brandList",redisTemplate.boundHashOps("brandList").get(typeId));
            map.put("specList",redisTemplate.boundHashOps("specList").get(typeId));
        }
        return map;
    }

    // 定义搜索对象的结构  category:商品分类
    // $scope.searchMap={'keywords':'','category':'','brand':'','spec':{},'price':'','pageNo':1,'pageSize':40,'sort':'','sortField':''};
    // rows categoryList brandList specList

    // 查询商品分类 结果集
    public List<String> findCategoryList(Map<String, String> searchMap){
        // 创建查询条件
        Criteria criteria = new Criteria("item_keywords").is(searchMap.get("keywords"));
        Query query = new SimpleQuery(criteria);
        GroupOptions groupOptions = new GroupOptions().addGroupByField("item_category");//先绑定到具体的域上， 也就是说根据域来分组
        query.setGroupOptions(groupOptions);   //这一步就开启了分组查    注意分组查询不能使用criteria， 而只能使用单独一套groupOptions来查询
        // 执行分组查询
        GroupPage<Item> groupPage = solrTemplate.queryForGroupPage(query, Item.class); //选这个的原因是分类id是由重复的， 需要取出，但是solr没有distinct，只能使用分组了
        GroupResult<Item> category = groupPage.getGroupResult("item_category"); // 这个的意思是查出的结果只取一个字段item_category
        List<GroupEntry<Item>> itemList = category.getGroupEntries().getContent();
        ArrayList<String> categorylist = new ArrayList();
        if (null != itemList && itemList.size()>0){
            for (GroupEntry<Item> entry : itemList) {
                categorylist.add(entry.getGroupValue());
            }
        }
        return categorylist;
    }
    // 查询总条数    结果集
    public Map<String, Object> findItemMap(Map<String, String> searchMap){
        // 设置查询条件
        Criteria criteria = new Criteria("item_keywords").is(searchMap.get("keywords"));
        SimpleHighlightQuery query = new SimpleHighlightQuery(criteria);    // 为什么使用高亮条件的原因，是要高亮显示结果
        // 分类属于过滤条件， 是在搜索条件后面添加的    并且分类信息有可能为空
        if (null != searchMap.get("category") && !"".equals(searchMap.get("category").trim())) {
            FilterQuery filterQuery = new SimpleFilterQuery(new Criteria("item_category").is(searchMap.get("category").trim()));
            query.addFilterQuery(filterQuery);
        }
        // 品牌属于过滤条件， 是在搜索条件后面添加的    并且品牌信息有可能为空
        if (null != searchMap.get("brand") && !"".equals(searchMap.get("brand").trim())) {
            FilterQuery filterQuery = new SimpleFilterQuery(new Criteria("item_brand").is(searchMap.get("brand").trim()));
            query.addFilterQuery(filterQuery);
        }
        // 价格属于过滤条件， 是在搜索条件后面添加的    并且价格信息有可能为空
        if (null != searchMap.get("price") && !"".equals(searchMap.get("price").trim())) {
            String[] p = searchMap.get("price").trim().split("-");
            FilterQuery filterQuery = null;
            if (searchMap.get("price").contains("*")){
                filterQuery = new SimpleFilterQuery(new Criteria("item_price").greaterThanEqual(p[0]));
            }else {
                filterQuery = new SimpleFilterQuery(new Criteria("item_price").between(p[0],p[1],true,false));
            }
            query.addFilterQuery(filterQuery);
        }
        // 3个条件排序
        if (null != searchMap.get("sortField") && !"".equals(searchMap.get("sortField").trim())){
            if ("desc".equalsIgnoreCase(searchMap.get("sort"))) {
                query.addSort(new Sort(Sort.Direction.DESC, "item_" + searchMap.get("sortField").trim()));
            }else {
                query.addSort(new Sort( Sort.Direction.ASC,"item_" + searchMap.get("sortField").trim()));
            }
        }
        // 创建查询条件
        HighlightOptions highlightOptions = new HighlightOptions();
        highlightOptions.addField("item_title");
            //创建高亮查询的前后缀
        highlightOptions.setSimplePrefix("<em style='color:red'>");
        highlightOptions.setSimplePostfix("</em>");
        query.setHighlightOptions(highlightOptions);    //  这一步就开启了高亮查

        // 分页查
        String pageNo = searchMap.get("pageNo");
        String pageSize = searchMap.get("pageSize");
        query.setOffset((Integer.parseInt(pageNo)-1)*Integer.parseInt(pageSize));    // 起始值
        query.setRows(Integer.parseInt(pageSize));    // 每页数

        // 执行高亮查询
        HighlightPage<Item> page = solrTemplate.queryForHighlightPage(query, Item.class);
        List<HighlightEntry<Item>> highlighted = page.getHighlighted(); // 40条
        for (HighlightEntry<Item> highlightEntry : highlighted) {
            Item item = highlightEntry.getEntity(); // 查询出的每个高亮个体都是一个item但是数据不全,需要封装必要数据
            List<HighlightEntry.Highlight> highlights = highlightEntry.getHighlights();
            if (null != highlights && highlights.size()>0){ // 只有highlightlist有数据才替换
                item.setTitle(highlights.get(0).getSnipplets().get(0));
            }
        }
        Map<String,Object> map = new HashMap<>();   // 创建结果集容器
        map.put("total",page.getTotalElements());   // 封装总条数
        map.put("totalPages",page.getTotalPages()); // 封装总页数
        map.put("rows",page.getContent());   // 封装总结果集
        return map;
    }

}
