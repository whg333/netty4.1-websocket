<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Web Socket Test</title>
<script type="text/javascript" src="static/js/protobuf/long.js"></script>
<script type="text/javascript" src="static/js/protobuf/bytebuffer.js"></script>
<script type="text/javascript" src="static/js/protobuf/protobuf.js"></script>
</head>
<body>
	<script type="text/javascript">
		if (typeof dcodeIO === 'undefined' || !dcodeIO.ProtoBuf) {
			throw (new Error("ProtoBuf.js is not present. Please see www/index.html for manual setup instructions."));
		}

		var ProtoBuf = dcodeIO.ProtoBuf, 
			TestProtobuf = ProtoBuf.loadProtoFile('static/js/protobuf/TestProtobuf.proto').build('TestProtobuf'), 
			TestProto = TestProtobuf.TestProto,
			JsonProto = TestProtobuf.JsonProto,
			BoProtobuf = ProtoBuf.loadProtoFile('static/js/protobuf/BoProtobuf.proto').build('BoProtobuf'),
			RequestProto = BoProtobuf.RequestProto,
			S2CProto = ProtoBuf.loadProtoFile('static/js/protobuf/S2CProtobuf.proto').build('BoProtobuf'),
			LoginS2CVOProto = S2CProto.LoginS2CVOProto
			;

		var socket;
		if (!window.WebSocket) {
			window.WebSocket = window.MozWebSocket;
		}
		if (window.WebSocket) {
			socket = new WebSocket("ws://127.0.0.1:8080/websocket");
			//这句话特别关键！将属性设为“blob”或“arraybuffer”。默认格式为“blob”（您不必在发送时校正 binaryType参数）。
			socket.binaryType = "arraybuffer";
			
			socket.onmessage = function(event) {
				var ta = document.getElementById('responseText');
				var msg = event.data;
				//var protobufResp = TestProto.decode(str2bytes(event.data));
				
				var dataView = new DataView(msg.slice(0, 4));
				var responseId = dataView.getInt32(0);
				console.log(responseId);
				
				//var protobufResp = TestProto.decode(event.data);
				var protobufResp = LoginS2CVOProto.decode(msg.slice(4));
                var jsonResp = JSON.stringify(protobufResp);
				//ta.value = ta.value + '\n' + event.data
                ta.value = ta.value + '\n' + jsonResp
			};
			socket.onopen = function(event) {
				var ta = document.getElementById('responseText');
				ta.value = "Web Socket opened!\n";
			};
			socket.onclose = function(event) {
				var ta = document.getElementById('responseText');
				ta.value = ta.value + "\n\nWeb Socket closed\n";
			};
		} else {
			alert("Your browser does not support Web Socket.");
		}

		function send(message) {
			if (!window.WebSocket) {
				return;
			}
			if (socket.readyState == WebSocket.OPEN) {
				//var testJson = {id:1001, name:'whg'};
				//var testJson = {s:'userService',m:'login',args:['whg','test']};
				var testJson = JSON.parse(message);
				//var protobufMsg = new TestProto(testJson).toBuffer()
				var protobufMsg = new RequestProto(testJson).toBuffer();
				//var protobufMsg = new JsonProto({data:message}).toBuffer();
				socket.send(protobufMsg);
				//socket.send(message);
			} else {
				alert("The socket is not open.");
			}
		}
		
		function str2bytes(str){
            var bytes = [];
            for (var i = 0, len = str.length; i < len; ++i) {
                var c = str.charCodeAt(i);
                var b = c & 0xff;
                bytes.push(b);
            }
            return bytes;
        }
	</script>
	<form onsubmit="return false;">
		<input size="122px" type="text" name="message"
			value='{"s":"userService","m":"login","args":["whg","test"]}' /> <input
			type="button" value="Send Web Socket Data"
			onclick="send(this.form.message.value)" />
		<h3>Output</h3>
		<textarea id="responseText" style="width: 1000px; height: 450px;"></textarea>
	</form>
</body>
</html>
