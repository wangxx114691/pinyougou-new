package com.itheima.core.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.core.BrandService;
import com.itheima.core.dao.good.BrandDao;
import com.itheima.core.pojo.good.Brand;
import com.itheima.core.pojo.good.BrandQuery;
import entity.PageResult;
import org.apache.activemq.broker.BrokerFactory;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class BrandServiceImpl implements BrandService {
    @Autowired
    private BrandDao brandDao;

    @Override
    public List<Brand> findAll() {
        return brandDao.selectByExample(null);
    }

    @Override
    public PageResult<Brand> findPage(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        Page<Brand> p = (Page<Brand>) brandDao.selectByExample(null);
        return new PageResult<>(p.getTotal(), p.getResult());
    }

    @Override
    public void add(Brand brand) {
        brandDao.insertSelective(brand);
        // 这里为什么选这个, 因为我们传入的id, name, firstChar属性其中之一肯定有空的
    }

    @Override
    public boolean update(Brand brand) {
        int row = brandDao.updateByPrimaryKeySelective(brand);
        return row > 0;
    }

    @Override
    public Brand findOne(Long id) {
        return brandDao.selectByPrimaryKey(id);
    }

    @Override
    public boolean delete(Long[] ids) {
        boolean flag = true;
        // 下面这种是比较常用的使用循环遍历实现删除
//        for (Long id : ids) {
//            int rows = brandDao.deleteByPrimaryKey(id);
//            if (rows < 1){
//                flag = false;
//            }
//        }     return flag;

        // 这个是使用条件删除, 需要调用XXXQuery条件
        BrandQuery query = new BrandQuery();
        query.createCriteria().andIdIn(Arrays.asList(ids));
        //注意这里不需要重新赋值, 只需要调用方法,query最后就会自行更新, 方法最后有一个 return (Criteria) this;
        int rows = brandDao.deleteByExample(query);
        return rows > 0;
    }

    @Override
    public PageResult<Brand> search(Integer pageNum, Integer pageSize, Brand brand) {
        PageHelper.startPage(pageNum, pageSize);
        BrandQuery query = new BrandQuery();
        BrandQuery.Criteria criteria = query.createCriteria();
        // 判断传入的名字不为空, 则进行查询条件的拼接
        if (null != brand.getName() && !"".equals(brand.getName().trim())){ // 注意这两项是条件查询的基本操作, 必须记住
            criteria.andNameLike("%"+brand.getName().trim()+"%");
        }
        // 判断传入的首字母不为空, 则进行查询条件的拼接
        if (null != brand.getFirstChar() && !"".equals(brand.getFirstChar().trim())){
            criteria.andFirstCharEqualTo(brand.getFirstChar().trim());
        }
        Page<Brand> p = (Page<Brand>) brandDao.selectByExample(query);
        return new PageResult<>(p.getTotal(), p.getResult());
    }

    @Override
    public List<Map> selectOptionList() {
        return brandDao.selectOptionList();
    }

    @Override
    public void addBrands(File fo) throws Exception {

        List<Brand> list = new ArrayList<>();
        XSSFWorkbook workbook = null;


        //创建Excel，读取文件内容
        try {
            workbook = new XSSFWorkbook(FileUtils.openInputStream(fo));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //获取第一个工作表
        XSSFSheet sheet = workbook.getSheet("brand");

        //获取sheet中第一行行号
        int firstRowNum = sheet.getFirstRowNum();
        //获取sheet中最后一行行号
        int lastRowNum = sheet.getLastRowNum();


        //循环插入数据
        for (int i = firstRowNum + 1; i <= lastRowNum; i++) {
            XSSFRow row = sheet.getRow(i);

            Brand brand = new Brand();


//                XSSFCell id = row.getCell(0);//id
//                if(id!=null){
//                    id.setCellType(Cell.CELL_TYPE_STRING);
//                    brand.setId();
//                }

            XSSFCell name = row.getCell(1);//name
            if (name != null) {
                name.setCellType(Cell.CELL_TYPE_STRING);
                brand.setName((name.getStringCellValue()));
            }

            XSSFCell first_char = row.getCell(2);//first_char
            if (first_char != null) {
                first_char.setCellType(Cell.CELL_TYPE_STRING);
                brand.setFirstChar((first_char.getStringCellValue()));
            }

//                XSSFCell birthday = row.getCell(3);//birthday
//                if(birthday.equals("")){
//                    birthday.setCellType(Cell.CELL_TYPE_STRING);
//                    brand.setBirthday(null);
//                }
            list.add(brand);
        }
        for (int i = 0; i < list.size(); i++) {
            Brand brand = list.get(i);
            brandDao.insertSelective(brand);//往数据库插入数据
        }

        workbook.close();


    }
}
