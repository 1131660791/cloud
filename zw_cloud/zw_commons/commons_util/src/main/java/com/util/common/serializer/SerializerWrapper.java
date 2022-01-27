package com.util.common.serializer;

import java.io.Serializable;

/**
 * @author hzw
 * @version 4.0.0
 * @date 2019-09-18
 */
public class SerializerWrapper<T> implements Serializable {

    private static final long serialVersionUID = 7789079670309074503L;
    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static <T> SerializerWrapper<T> builder(T data) {
        SerializerWrapper<T> wrapper = new SerializerWrapper<>();
        wrapper.setData(data);
        return wrapper;
    }
}
