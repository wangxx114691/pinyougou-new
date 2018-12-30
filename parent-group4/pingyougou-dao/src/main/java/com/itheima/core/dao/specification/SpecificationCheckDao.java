package com.itheima.core.dao.specification;

import com.itheima.core.pojo.specification.SpecificationCheck;
import com.itheima.core.pojo.specification.SpecificationCheckQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SpecificationCheckDao {
    int countByExample(SpecificationCheckQuery example);

    int deleteByExample(SpecificationCheckQuery example);

    int deleteByPrimaryKey(Long id);

    int insert(SpecificationCheck record);

    int insertSelective(SpecificationCheck record);

    List<SpecificationCheck> selectByExample(SpecificationCheckQuery example);

    SpecificationCheck selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") SpecificationCheck record, @Param("example") SpecificationCheckQuery example);

    int updateByExample(@Param("record") SpecificationCheck record, @Param("example") SpecificationCheckQuery example);

    int updateByPrimaryKeySelective(SpecificationCheck record);

    int updateByPrimaryKey(SpecificationCheck record);
}