package com.wei.demo.junit.junit.impl;

import com.wei.demo.springboottest.junit.CalculatorService;
import org.springframework.stereotype.Service;

/**
 * @Author: weiwenfeng
 * @Date: 2018/9/14
 */
@Service
public class CalculatorServiceImpl implements CalculatorService {

    @Override
    public int add(int a, int b) {
        System.out.println("=======正在执行加法");
        return a + b;
    }

    @Override
    public int minus(int a, int b) {
        System.out.println("=======正在执行减法");
        return a - b;
    }

    @Override
    public int square(int n) {
        System.out.println("=======正在执行平方计算");
        return n * n;
    }

    @Override
    //Bug : 死循环
    public void squareRoot(int n) {
        System.out.println("=======正在执行死循环的方法");
        for(; ;)
            ;
    }

    @Override
    public int multiply(int a, int b) {
        System.out.println("=======正在执行乘法");
        return   a * b;
    }

    @Override
    public int divide(int a, int b) throws Exception {
        System.out.println("=======正在执行除法");
        if (0 == b) {
            throw new Exception("除数不能为零");
        }
        return a / b;
    }

}
