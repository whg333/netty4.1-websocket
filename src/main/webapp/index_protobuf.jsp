<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
			TestProto = TestProtobuf.TestProto;

		var socket;
		if (!window.WebSocket) {
			window.WebSocket = window.MozWebSocket;
		}
		if (window.WebSocket) {
			socket = new WebSocket("ws://127.0.0.1:8080/websocket");
			socket.onmessage = function(event) {
				var ta = document.getElementById('responseText');
				ta.value = ta.value + '\n' + event.data
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
				var testJson = {id:1001, name:'whg'};
				var protobufMsg = new TestProto(testJson).toBuffer()
				socket.send(protobufMsg);
				//socket.send(message);
			} else {
				alert("The socket is not open.");
			}
		}
	</script>
	<form onsubmit="return false;">
		<input size="122px" type="text" name="message"
			value="{'s':'userService','m':'login','args':['whg','test']}" />
		<input
			type="button" value="Send Web Socket Data"
			onclick="send(this.form.message.value)" />
		<h3>Output</h3>
		<textarea id="responseText" style="width: 1000px; height: 450px;"></textarea>
	</form>
</body>
</html>
