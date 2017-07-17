package com.whg.util.protobuf;

import com.google.protobuf.GeneratedMessage;
import com.whg.websocket.server.framework.response.ProtobufResponse;

/**
 * <p>描述：使用Protobuf二进制协议持久化BO对象的接口</p>
 * @author whg
 * @date 2016-6-29 下午09:50:19
 */
public interface ProtobufSerializable<T extends GeneratedMessage> extends ProtobufResponse<T> {
	
    void parseFrom(byte[] bytes);

    void copyFrom(T proto);
    
    
}