package com.whg.websocket.server.handler;

import java.util.List;

import com.whg.util.json.JsonUtil;
import com.whg.websocket.server.framework.response.JsonResponse;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

@Sharable
public class WebSocketJsonEncoder extends MessageToMessageEncoder<JsonResponse> {
	
	@Override
	protected void encode(ChannelHandlerContext ctx, JsonResponse msg, List<Object> out) throws Exception {
		//String json = JSON.toJSONString(msg);
		String json = JsonUtil.toJSON(msg);
		out.add(new TextWebSocketFrame(json));
	}
	
}
