package com.cool.rpc.util;


import com.cool.rpc.protocol.CoolRequest;
import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.Schema;
import com.dyuproject.protostuff.runtime.RuntimeSchema;

import java.util.Arrays;

/**
 * serialize tool
 * @auther Vincent
 * @wechat luxiaotao1123
 * @data 2018/8/24
 */
public final class ProtostuffSerialize {

    private ProtostuffSerialize(){
    }

    /**
     *  serialize init
     */
    @SuppressWarnings("unchecked")
    public static <T> byte[] serialize(T obj){
        Class<T> cls = (Class<T>) obj.getClass();
        LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
        try {
            Schema<T> schema = RuntimeSchema.getSchema(cls);
            if (schema == null){
                schema = RuntimeSchema.createFrom(cls);
            }
            return ProtostuffIOUtil.toByteArray(obj, schema, buffer);
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        } finally {
            buffer.clear();
        }
    }

    /**
     *  analyze serialize
     */
    public static <T> T deserialize(byte[] data, Class<T> cls){
        try {
            Schema<T> schema = RuntimeSchema.getSchema(cls);
            if (schema == null){
                return null;
            }
            T message = schema.newMessage();
            ProtostuffIOUtil.mergeFrom(data, message, schema);
            return message;
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

}