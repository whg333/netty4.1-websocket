package com.whg.websocket.server.framework.protobuf;

import com.google.protobuf.GeneratedMessage;

/**
 * <p>描述：使用Protobuf二进制协议在BO对象之间互相转换的接口</p>
 * @author whg
 * @date 2016-6-29 下午09:50:19
 */
public interface ProtobufSerializable<T extends GeneratedMessage> extends ProtobufCodec<T> {
	
    void parseFrom(byte[] bytes);

    void copyFrom(T proto);
    
    
}