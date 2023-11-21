 // ICalculator.aidl
package com.example.sdk;

import com.example.sdk.bean.Sample;

//计算接口
interface ICalculator {
    //加
    int add(int a, int b);
    //减
    int subtract(int a, int b);
    //乘
    int multiply(int a, int b);
    //除
    int divide(int a, int b);
    //传递对象
    Sample optionParcel(in Sample sample);
}