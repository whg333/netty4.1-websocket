package com.whg.websocket.server.framework.response;

import com.google.protobuf.GeneratedMessage;

public interface ProtobufResponse<T extends GeneratedMessage> {

	/** 数据序列化 */
    byte[] toByteArray();
    
    T copyTo();
	
}
