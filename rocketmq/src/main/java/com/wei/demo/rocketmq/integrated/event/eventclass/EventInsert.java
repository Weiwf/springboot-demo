package com.wei.demo.rocketmq.integrated.event.eventclass;

import com.alibaba.fastjson.JSONArray;
import com.wei.demo.rocketmq.integrated.event.EventHandler;
import com.wei.demo.rocketmq.integrated.event.IEventHandler;
import org.springframework.stereotype.Component;

/**
 * @Author: weiwenfeng
 * @Date: 2018/11/5
 */
@EventHandler(tables = {"table_test_insert"})
@Component
public class EventInsert implements IEventHandler{
    @Override
    public void insert(String tableName, JSONArray jsonArray) {
        System.out.println("----------  test insert -----------");
    }

    @Override
    public void update(String tableName, JSONArray jsonArray) {

    }

    @Override
    public void delete(String tableName, JSONArray jsonArray) {

    }
}
