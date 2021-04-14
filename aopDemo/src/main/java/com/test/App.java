package com.test;

import com.test.service.Humen;
import com.test.service.Rebot;
import com.test.service.impl.RebotNo1;
import com.test.service.impl.Vague;
import com.test.service.impl.XiaoWang;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        //小王的代理
        Humen xiaowang = new XiaoWang();
        Humen xiaoWangProxy = new ProxyTest<>(xiaowang).getProxy();
        xiaoWangProxy.say();

        //机器人代理
        Rebot dahuangfeng = new RebotNo1();
        Rebot dahuangfengProxy = new ProxyTest<>(dahuangfeng).getProxy();
        dahuangfengProxy.dance();

        //xx代理
        Rebot vague = new Vague();
        Rebot proxy = new ProxyTest<>(vague).getProxy();
        proxy.say();
    }
}
