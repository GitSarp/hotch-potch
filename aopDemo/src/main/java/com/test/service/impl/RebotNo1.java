package com.test.service.impl;

import com.test.service.Rebot;

/**
 * @Author: freaxjj
 * @Desc:
 * @Date: 4/9/21 2:07 PM
 */
public class RebotNo1 implements Rebot {
    @Override
    public void dance() {
        System.out.println("rebot no1 dance...");
    }

    @Override
    public void say() {
        System.out.println("rebot no1 say...");
    }
}
