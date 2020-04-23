package com.freaxjj.util;

import java.io.*;

/**
 * @author 刘亚林
 * @description 通过序列化方式实现深拷贝, 被拷贝对象以及其内容, 需要实现Serializable接口
 * @create 2020/4/1 18:03
 **/
public class CopyUtils {
    public static<T> T deepClone(T src) throws IOException, ClassNotFoundException {
        Object obj = null;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(src);
        objectOutputStream.close();
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        obj = objectInputStream.readObject();
        objectInputStream.close();
        return (T) obj;
    }
}
