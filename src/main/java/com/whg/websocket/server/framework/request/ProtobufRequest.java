package com.whg.websocket.server.framework.request;

import com.whg.protobuf.BoProtobuf.RequestProto;

public class ProtobufRequest extends DefaultRequest{

	public ProtobufRequest(RequestProto proto){
		s = proto.getS();
		m = proto.getM();
		args = proto.getArgsList();
	}

	@Override
	public String toString() {
		return "ProtobufRequest [s=" + s + ", m=" + m + ", args=" + args + "]";
	}
	
}
