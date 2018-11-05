package com.wei.demo.rocketmq.integrated.event;

import com.alibaba.fastjson.JSONArray;
import org.springframework.stereotype.Service;

/**
 * @Author: weiwenfeng
 * @Date: 2018/11/5
 */
public interface IEventHandler {

    void insert(String tableName, JSONArray jsonArray);

    void update(String tableName,JSONArray jsonArray);

    void delete(String tableName,JSONArray jsonArray);
}
