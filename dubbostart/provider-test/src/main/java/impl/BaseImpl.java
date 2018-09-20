package impl;

import interfaces.BaseInterface;

/**
 * @ Author     ：freaxjj.
 * @ Date       ：Created in 上午11:08 2018/9/13
 * @ Description：
 * @ Modified By：
 */
public class BaseImpl implements BaseInterface {

    public String sayHello(String content) {
        return "Hello!"+content;
    }
}
