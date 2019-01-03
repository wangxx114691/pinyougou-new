package com.itheima.core.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.core.TypeTemplateService;
import com.itheima.core.dao.specification.SpecificationDao;
import com.itheima.core.dao.specification.SpecificationOptionDao;
import com.itheima.core.dao.template.TypeTemplateDao;
import com.itheima.core.pojo.specification.Specification;
import com.itheima.core.pojo.specification.SpecificationOption;
import com.itheima.core.pojo.specification.SpecificationOptionQuery;
import com.itheima.core.pojo.template.TypeTemplate;
import com.itheima.core.pojo.template.TypeTemplateQuery;
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
import java.util.Map;

@Service
@Transactional
public class TypeTemplateServiceImpl implements TypeTemplateService {

    @Autowired
    private TypeTemplateDao typeTemplateDao;
    @Autowired
    private SpecificationOptionDao specificationOptionDao;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public PageResult search(Integer page, Integer rows, TypeTemplate typeTemplate) {
        // 1.查询mysql，并保存到缓冲中，之后访问redis就行了
        List<TypeTemplate> typeTemplates = typeTemplateDao.selectByExample(null);
        for (TypeTemplate template : typeTemplates) {
            String brandIds = template.getBrandIds();
            List<Map> brandList = JSON.parseArray(brandIds, Map.class);
            redisTemplate.boundHashOps("brandList").put(template.getId(),brandList);
            List<Map> specList = findBySpecList(template.getId());
            redisTemplate.boundHashOps("specList").put(template.getId(),specList);
        }

        // 分页助手
        PageHelper.startPage(page, rows);
        TypeTemplateQuery query = new TypeTemplateQuery();
        query.setOrderByClause("id desc");  // 设置主键降序, 这样对于新添加的选项会出现在第一页比较方便
        if (typeTemplate.getName() != null && !"".equals(typeTemplate.getName().trim())) {
            query.createCriteria().andNameLike("%" + typeTemplate.getName().trim() + "%");
        }
        Page<TypeTemplate> p = (Page<TypeTemplate>) typeTemplateDao.selectByExample(query);
        return new PageResult(p.getTotal(), p.getResult());
    }

    @Override
    public void add(TypeTemplate typeTemplate) {
        typeTemplateDao.insertSelective(typeTemplate);
    }

    @Override
    public void update(TypeTemplate typeTemplate) {
        typeTemplateDao.updateByPrimaryKeySelective(typeTemplate);
    }

    @Override
    public TypeTemplate findOne(Long id) {
        return typeTemplateDao.selectByPrimaryKey(id);
    }

    @Override
    public void delete(Long[] ids) {
        TypeTemplateQuery query = new TypeTemplateQuery();
        query.createCriteria().andIdIn(Arrays.asList(ids));
        typeTemplateDao.deleteByExample(query);
    }

    @Override
    public List<Map> selectOptionList() {
        return typeTemplateDao.selectOptionList();
    }

    @Override
    public List<Map> findBySpecList(Long id) {
        TypeTemplate typeTemplate = typeTemplateDao.selectByPrimaryKey(id);
        String specIds = typeTemplate.getSpecIds();
        // 封装List<Map>
        List<Map> mapList = JSON.parseArray(specIds, Map.class);    // 后面的参数决定返回什么泛型
        for (Map map : mapList) {
            SpecificationOptionQuery query = new SpecificationOptionQuery();
            query.createCriteria().andSpecIdEqualTo((long)(Integer)map.get("id"));
            List<SpecificationOption> optionList = specificationOptionDao.selectByExample(query);
            map.put("options",optionList);
        }
        return mapList;
    }

    @Override
    public void addTemplates(File fo) throws Exception{
        List<TypeTemplate> list = new ArrayList<>();
        XSSFWorkbook workbook = null;


        //创建Excel，读取文件内容
        try {
            workbook = new XSSFWorkbook(FileUtils.openInputStream(fo));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //获取第一个工作表
        XSSFSheet sheet = workbook.getSheet("typeTemplate");

        //获取sheet中第一行行号
        //int firstRowNum = sheet.getFirstRowNum();
        //获取sheet中最后一行行号
        int lastRowNum = sheet.getLastRowNum();


        //循环插入数据
        for (int i =  1; i <= lastRowNum; i++) {
            XSSFRow row = sheet.getRow(i);

            TypeTemplate typeTemplate = new TypeTemplate();

            XSSFCell name = row.getCell(1);//name
            if (name != null) {
                name.setCellType(Cell.CELL_TYPE_STRING);
                typeTemplate.setName(name.getStringCellValue());
            }

            XSSFCell spec_ids = row.getCell(2);//spec_ids
            if (spec_ids != null) {
                spec_ids.setCellType(Cell.CELL_TYPE_STRING);
                typeTemplate.setSpecIds(spec_ids.getStringCellValue());
            }

            XSSFCell birtbrand_ids = row.getCell(3);//brand_ids
            if (birtbrand_ids != null) {
                birtbrand_ids.setCellType(Cell.CELL_TYPE_STRING);
                typeTemplate.setBrandIds(birtbrand_ids.getStringCellValue());
            }

            XSSFCell custom_attribute_items = row.getCell(4);//custom_attribute_items
            if (custom_attribute_items != null) {
                custom_attribute_items.setCellType(Cell.CELL_TYPE_STRING);
                typeTemplate.setCustomAttributeItems(custom_attribute_items.getStringCellValue());
            }

            list.add(typeTemplate);
        }
        for (int i = 0; i < list.size(); i++) {
            TypeTemplate typeTemplate=list.get(i);
            typeTemplateDao.insertSelective(typeTemplate);//往数据库插入数据
        }

        workbook.close();


    }
}
