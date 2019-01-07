package com.netty.im.serialize;

/**
 * Created by timma on 2018/12/10.
 */
public interface Serializer {


    /**
     * json 序列化
     */
    byte JSON_SERIALIZER = 1;


    Serializer DEFAULT = new JsonSerializer();

    /**
     * 序列化算法
     * @return
     */
    byte getSerializerAlgorithm();

    /**
     * java对象转换成二进制
     *
     * @param object
     * @return
     */
    byte[] serialize(Object object);


    /**
     * 二进制转换成java对象
     *
     * @param clazz
     * @param bytes
     * @param <T>
     * @return
     */
    <T> T deserialize(Class<T> clazz, byte[] bytes);


}
