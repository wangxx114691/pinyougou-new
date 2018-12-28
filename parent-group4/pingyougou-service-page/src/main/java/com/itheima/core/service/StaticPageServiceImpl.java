package com.itheima.core.service;

import com.alibaba.dubbo.config.ServiceConfig;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.dubbo.rpc.cluster.Cluster;
import com.itheima.core.StaticPageService;
import com.itheima.core.dao.good.GoodsDao;
import com.itheima.core.dao.good.GoodsDescDao;
import com.itheima.core.dao.item.ItemCatDao;
import com.itheima.core.dao.item.ItemDao;
import com.itheima.core.pojo.good.Goods;
import com.itheima.core.pojo.good.GoodsDesc;
import com.itheima.core.pojo.item.Item;
import com.itheima.core.pojo.item.ItemQuery;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.servlet.ServletContext;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class StaticPageServiceImpl implements StaticPageService, ServletContextAware {
    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;
    private ServletContext servletContext;
    @Autowired
    private GoodsDescDao goodsDescDao;
    @Autowired
    private GoodsDao goodsDao;
    @Autowired
    private ItemCatDao itemCatDao;
    @Autowired
    private ItemDao itemDao;

    @Override
    public void index(Long id) {
        Configuration conf = freeMarkerConfigurer.getConfiguration();
        String AbsPath = getPath("/" + id + ".html");   // 根据相对路径获取绝对路径
        Writer out = null;
        Map<String, Object> root = new HashMap();   // 定义数据
        GoodsDesc goodsDesc = goodsDescDao.selectByPrimaryKey(id);
        root.put("goodsDesc", goodsDesc);// 显示商品详情
        ItemQuery query = new ItemQuery();
        query.createCriteria().andGoodsIdEqualTo(id).andStatusEqualTo("1");
        List<Item> itemList = itemDao.selectByExample(query);
        root.put("itemList", itemList); //显示库存信息列表
        Goods goods = goodsDao.selectByPrimaryKey(id);
        root.put("goods", goods);    // 显示商品
        // 显示分类
        root.put("itemCat1", itemCatDao.selectByPrimaryKey(goods.getCategory1Id()).getName());
        root.put("itemCat2", itemCatDao.selectByPrimaryKey(goods.getCategory2Id()).getName());
        root.put("itemCat3", itemCatDao.selectByPrimaryKey(goods.getCategory3Id()).getName());
        try {
            Template template = conf.getTemplate("item.ftl");   // 获取模板

            out = new OutputStreamWriter(new FileOutputStream(AbsPath), "utf-8");//获取输出流
            template.process(root, out);// 处理数据
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
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
