package com.itheima.core.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.core.BrandService;
import com.itheima.core.pojo.good.Brand;
import entity.PageResult;
import entity.Result;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("brand")
public class BrandController {

    @Reference
    private BrandService brandService;
    @RequestMapping("findAll")
    public List<Brand> findAll(){
        return brandService.findAll();
    }

    @RequestMapping("findPage")
    public PageResult<Brand> findPage(Integer pageNum, Integer pageSize){
        return brandService.findPage(pageNum,pageSize);
    }

    @RequestMapping("add")
    public Result add(@RequestBody Brand brand){
        Result result = null;
        try {
            brandService.add(brand);
            result = new Result(true, "操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            result = new Result(false,"操作失败");
        }
        return result;
    }

    @RequestMapping("update")
    public Result update(@RequestBody Brand brand){
        Result result = null;
        try {
            boolean update = brandService.update(brand);
            if (update){
                result = new Result(true, "操作成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = new Result(false,"操作失败");
        }
        return result;
    }

    @RequestMapping("findOne")
    public Brand findOne(Long id){
        return brandService.findOne(id);
    }

    @RequestMapping("delete")
    public Result delete(Long[] ids){
        Result result = null;
        try {
            boolean dele = brandService.delete(ids);
            if (dele){
                result = new Result(true, "操作成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = new Result(false,"操作失败");
        }
        return result;

    }

    @RequestMapping("search")
//    public PageResult<Brand> search(Integer pageNum, Integer pageSize,@RequestBody(required = false) Brand brand){
    public PageResult<Brand> search(Integer pageNum, Integer pageSize,@RequestBody Brand brand){
        return brandService.search(pageNum,pageSize,brand);
    }
    // @RequestBody 注意加上这个注解后所传参数因为有required限制, 所以是必须有值, 如果位null则会报400
    // 但也可通过将@RequestBody的required=false来设置, 这样也能达到效果, 我觉得这样好点


    @RequestMapping("selectOptionList")
    public List<Map> selectOptionList(){    // 为什么这里不用PageResult进行封装,的原因是这里PageResult只有在查询分页时才使用
        return brandService.selectOptionList();
    }

    @RequestMapping("/addBrands")
    public Result addBrands(MultipartFile file){

        try {
            //把MultipartFile转化为File
            CommonsMultipartFile cmf= (CommonsMultipartFile)file;
            DiskFileItem dfi=(DiskFileItem) cmf.getFileItem();
            File fo=dfi.getStoreLocation();
            // file.getOriginalFilename();
            brandService.addBrands(fo);
            return new Result(true,"文件上传完成");   // 注意成功则返回url, 而不是成功信息方便图片通过src回显
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"文件上传失败");
        }
    }
}
