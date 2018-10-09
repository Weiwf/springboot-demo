package com.wei.demo.junit.junit;

public interface CalculatorService {

    int add(int a, int b);

    int minus(int a, int b);

    int square(int n);

    //Bug : 死循环
    void squareRoot(int n);

    int multiply(int a, int b);

    int divide(int a, int b) throws Exception;

}
