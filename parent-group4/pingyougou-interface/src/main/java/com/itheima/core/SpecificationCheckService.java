package com.itheima.core;


import entity.PageResult;

public interface SpecificationCheckService {
    PageResult search(Integer page, Integer rows);

    void updateStatus(Long[] ids, String status);
}
