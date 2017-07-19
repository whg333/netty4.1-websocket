package com.whg.websocket.server.framework.response;

import com.google.protobuf.GeneratedMessage;
import com.whg.websocket.server.framework.protobuf.ProtobufCodec;

@SuppressWarnings("rawtypes")
public interface ProtobufResponse<T extends GeneratedMessage> extends ProtobufCodec{
	
}
