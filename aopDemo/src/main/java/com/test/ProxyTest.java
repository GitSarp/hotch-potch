package com.test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyTest<T> implements InvocationHandler {

    private final T target;

    public ProxyTest(T target) {
        this.target = target;
    }

    /**
     * 获取实现了接口的类的代理
     */
    public T getProxy(){
        // ClassLoader loader， 被代理对象的类加载器
        // Class<?>[] interfaces，被代理类实现的接口列表
        // InvocationHandler h，动态处理器
        return (T)Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(),this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //增加前置操作
        System.out.println("\nready to execute method: " + method);
        Object res = method.invoke(target, args);
        //增加后置操作
        System.out.println("finish execute method: " + method + "\n");
        return res;
    }
}
