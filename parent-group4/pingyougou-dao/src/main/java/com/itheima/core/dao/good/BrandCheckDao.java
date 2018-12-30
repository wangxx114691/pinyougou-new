package com.itheima.core.dao.good;

import com.itheima.core.pojo.good.BrandCheck;
import com.itheima.core.pojo.good.BrandCheckQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BrandCheckDao {
    int countByExample(BrandCheckQuery example);

    int deleteByExample(BrandCheckQuery example);

    int deleteByPrimaryKey(Long id);

    int insert(BrandCheck record);

    int insertSelective(BrandCheck record);

    List<BrandCheck> selectByExample(BrandCheckQuery example);

    BrandCheck selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") BrandCheck record, @Param("example") BrandCheckQuery example);

    int updateByExample(@Param("record") BrandCheck record, @Param("example") BrandCheckQuery example);

    int updateByPrimaryKeySelective(BrandCheck record);

    int updateByPrimaryKey(BrandCheck record);
}