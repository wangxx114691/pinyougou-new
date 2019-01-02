package com.itheima.core.dao.user;

import com.itheima.core.pojo.user.User2;
import com.itheima.core.pojo.user.User2Query;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface User2Dao {
    int countByExample(User2Query example);

    int deleteByExample(User2Query example);

    int deleteByPrimaryKey(Long uid);

    int insert(User2 record);

    int insertSelective(User2 record);

    List<User2> selectByExample(User2Query example);

    User2 selectByPrimaryKey(Long uid);

    int updateByExampleSelective(@Param("record") User2 record, @Param("example") User2Query example);

    int updateByExample(@Param("record") User2 record, @Param("example") User2Query example);

    int updateByPrimaryKeySelective(User2 record);

    int updateByPrimaryKey(User2 record);
}