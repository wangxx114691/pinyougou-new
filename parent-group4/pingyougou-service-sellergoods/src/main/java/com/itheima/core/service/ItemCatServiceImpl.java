package com.itheima.core.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.core.ItemCatService;
import com.itheima.core.dao.item.ItemCatDao;
import com.itheima.core.dao.template.TypeTemplateDao;
import com.itheima.core.pojo.item.ItemCat;
import com.itheima.core.pojo.item.ItemCatQuery;
import entity.PageResult;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Transactional
public class ItemCatServiceImpl implements ItemCatService {

    @Autowired
    private ItemCatDao itemCatDao;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private TypeTemplateDao typeTemplateDao;
    @Override
    public List<ItemCat> findByParentId(Long parentId) {
        // 第一次查询的时候访问数据库， 并保存到缓冲中， 之后访问缓存
        List<ItemCat> itemCatList = findAll();
        for (ItemCat itemCat : itemCatList) {

            redisTemplate.boundHashOps("itemCat").put(itemCat.getName(),itemCat.getTypeId());   // 这个hash是 （分类名，模板id）

        }

        ItemCatQuery query = new ItemCatQuery();
        ItemCatQuery.Criteria criteria = query.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        return itemCatDao.selectByExample(query);
    }

    @Override
    public PageResult search(Integer page, Integer rows, Long parentId) {
        // 分页
        PageHelper.startPage(page,rows);
        ItemCatQuery query = new ItemCatQuery();
        ItemCatQuery.Criteria criteria = query.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        Page<ItemCat> p = (Page<ItemCat>) itemCatDao.selectByExample(query);
        return new PageResult(p.getTotal(),p.getResult());
    }

    @Override
    public void add(ItemCat itemCat) {
        itemCatDao.insertSelective(itemCat);
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
    public ItemCat findOne(Long id) {
        return itemCatDao.selectByPrimaryKey(id);
    }

    @Override
    public List<ItemCat> findAll() {
        return itemCatDao.selectByExample(null);
    }

    @Override
    public void addCategorys(File fo) throws Exception {
        List<ItemCat> list = new ArrayList<>();
        XSSFWorkbook workbook =null;

        //创建Excel，读取文件内容
        try {
            workbook = new XSSFWorkbook(FileUtils.openInputStream(fo));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //获取第一个工作表
        XSSFSheet sheet = workbook.getSheet("itemCat");

        //获取sheet中第一行行号
        // int firstRowNum = sheet.getFirstRowNum();
        //获取sheet中最后一行行号
        int lastRowNum = sheet.getLastRowNum();

        //循环插入数据
        for(int i=1;i<=lastRowNum;i++){
            XSSFRow row = sheet.getRow(i);

            ItemCat itemCat = new ItemCat();

            XSSFCell parentId = row.getCell(1);//parentId
            if(parentId!=null){
                parentId.setCellType(Cell.CELL_TYPE_STRING);
                itemCat.setParentId(Long.parseLong(parentId.getStringCellValue()));
            }

            XSSFCell name = row.getCell(2);//name
            if(name!=null){
                name.setCellType(Cell.CELL_TYPE_STRING);
                itemCat.setName(name.getStringCellValue());
            }

            XSSFCell typeId = row.getCell(3);//typeId
            if(typeId!=null){
                typeId.setCellType(Cell.CELL_TYPE_STRING);
                itemCat.setTypeId(Long.parseLong(typeId.getStringCellValue()));
            }

            list.add(itemCat);
        }
        for (int i = 0; i < list.size(); i++) {
            ItemCat itemCat=list.get(i);
            itemCatDao.insertSelective(itemCat);//往数据库插入数据
        }

        workbook.close();

    }
}
