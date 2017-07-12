package com.whg.websocket.server.handler;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

@Sharable
public class WebSocketJsonEncoder extends MessageToMessageEncoder<Map<String, Object>> {
	
	@Override
	protected void encode(ChannelHandlerContext ctx, Map<String, Object> msg, List<Object> out) throws Exception {
		String json = JSON.toJSONString(msg);
		//String json = JSONUtil.toJSON(msg);
		out.add(new TextWebSocketFrame(json));
	}
	
}
