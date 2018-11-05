package com.wei.demo.rocketmq;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wei.demo.rocketmq.integrated.mq.Producer;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * @Author: weiwenfeng
 * @Date: 2018/11/5
 */
public class EventTest extends RocketmqApplicationTests {

    @Resource
    private Producer producer;

    @Test
    public void test(){

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("tableName","table_test_insert");
        jsonObject.put("event","insert");
        JSONArray jsonArray = new JSONArray();
        jsonArray.add("row1");
        jsonArray.add("row2");
        jsonArray.add("row3");
        jsonObject.put("rowList",jsonArray);

        producer.send("table_test_insert",null,jsonObject.toJSONString().getBytes());
    }
}
