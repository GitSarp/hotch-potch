package com.test.service.impl;


import com.test.service.Humen;

public class XiaoWang implements Humen {

    @Override
    public void say() {
        System.out.println("humen say...");
    }

    @Override
    public void eat() {
        System.out.println("humen eat...");
    }
}
