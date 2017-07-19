package com.whg.websocket.server.framework.protobuf;

import com.google.protobuf.GeneratedMessage;

/**
 * <p>描述：使用Protobuf二进制协议持久化/序列化BO对象的接口</p>
 * @author whg
 * @date 2016-6-29 下午09:50:19
 */
public interface ProtobufCodec<T extends GeneratedMessage> {
	
	/** 数据序列化 */
    byte[] toByteArray();
    
    T copyTo();
    
}