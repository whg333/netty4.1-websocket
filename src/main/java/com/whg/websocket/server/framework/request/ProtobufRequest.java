package com.whg.websocket.server.framework.request;

import com.whg.protobuf.BoProtobuf.RequestProto;

public class ProtobufRequest extends DefaultRequest{

	public ProtobufRequest(RequestProto proto){
		s = proto.getS();
		m = proto.getM();
		args = proto.getArgsList().toArray(new String[proto.getArgsCount()]);
	}

	@Override
	public String toString() {
		return "ProtobufRequest [s=" + s + ", m=" + m + ", args=" + args + "]";
	}

	@Override
	public Object[] methodArgs(Class<?>[] argTypes) throws Exception {
		throw new UnsupportedOperationException();
	}
	
}
