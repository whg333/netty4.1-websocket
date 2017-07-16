package com.whg.websocket.server.handler;

import java.util.Arrays;
import java.util.List;

import com.google.protobuf.GeneratedMessage;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;

@Sharable
public class WebSocketProtobufEncoder extends MessageToMessageEncoder<GeneratedMessage> {
	
	@Override
	protected void encode(ChannelHandlerContext ctx, GeneratedMessage msg, List<Object> out) throws Exception {
		byte[] data = msg.toByteArray();
		System.out.println(Arrays.toString(data));
		out.add(new BinaryWebSocketFrame(Unpooled.wrappedBuffer(data)));
	}
	
}
