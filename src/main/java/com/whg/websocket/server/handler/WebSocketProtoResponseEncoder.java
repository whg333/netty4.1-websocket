package com.whg.websocket.server.handler;

import java.util.Arrays;
import java.util.List;

import com.whg.websocket.server.framework.response.ProtobufResponse;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;

@SuppressWarnings("rawtypes")
@Sharable
public class WebSocketProtoResponseEncoder extends MessageToMessageEncoder<ProtobufResponse> {
	
	@Override
	protected void encode(ChannelHandlerContext ctx, ProtobufResponse msg, List<Object> out) throws Exception {
		byte[] data = msg.toByteArray();
		ByteBuf buf = Unpooled.buffer(Integer.BYTES+data.length);
		buf.writeInt(1669);
		buf.writeBytes(data);
		out.add(new BinaryWebSocketFrame(buf));
		System.out.println(Arrays.toString(buf.array()));
		//out.add(new BinaryWebSocketFrame(Unpooled.wrappedBuffer(data)));
	}
	
}
