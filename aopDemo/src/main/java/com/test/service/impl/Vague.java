package com.test.service.impl;

import com.test.service.Humen;
import com.test.service.Rebot;

/**
 * @Author: freaxjj
 * @Desc:
 * @Date: 4/9/21 2:43 PM
 */
public class Vague implements Rebot, Humen {

    @Override
    public void eat() {
        System.out.println("vague eat...");
    }

    @Override
    public void dance() {
        System.out.println("vague dance...");
    }

    @Override
    public void say() {
        System.out.println("vague say...");
    }
}
