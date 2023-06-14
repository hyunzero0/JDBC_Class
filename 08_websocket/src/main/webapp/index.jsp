<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>웹소켓 테스트</title>
<script src="http://code.jquery.com/jquery-3.7.0.min.js"></script>
</head>
<body>
	<div id="container">
		<input type="text" id="sender" placeholder="보내는 사람"><br>
		<button id="chattingBtn">채팅접속</button>
		<input type="text" id="receiver" placeholder="받는 사람"><br>
		<input type="text" id="sendMsg">&nbsp;<button id="sendBtn">전송</button>
	</div>
	<div id="msgcontainer"></div>
	<script>
		// 웹소켓 서버에 접속하기
		// ws:// -> http프로토콜을 이용할 때 사용
		// wss:// -> https프로토콜을 이용할 때 사용
		
		// 1. 객체 생성
		let socket;
		$("#chattingBtn").click(e=>{
			socket = new WebSocket("ws://localhost:9090/<%=request.getContextPath()%>/chatting");
			// 자동으로 바로 연결(세션 받아옴) -> 서버 없으면 접속실패
			
			// 2. 접속이 완료되면 실행되는 함수 등록
			socket.onopen=e=>{
				// websocket서버와 접속이 완료되면 실행되는 함수 -> 접속이 됐는지 확인 가능
				// console.log(e);
				// socket.send("안녕");
				sendMsg(new Message("접속", $("#sender").val(), "", "", ""));
			}
			
			// 3. 서버에서 전송된 메세지를 처리하는 함수 등록
			socket.onmessage=e=>{
				// 접속된 websocket서버에서 sendText() || sendObject() 메소드를 실행했을 때 실행되는 함수
				// 서버에서 보낸 데이터를 처리하는 함수
				// console.log(e);
				
				// 서버에서 보낸 데이터는 매개변수 객체의 data속성에 저장되어 있음
				/* const msg = e.data.split(",");
				const sup = $("<sup>").text(msg[0]);
				const span = $("<span>").text(msg[1]).css("fontSize","20px"); */
				
				// 채팅 외 서비스
				const sendData = JSON.parse(e.data);
				console.log(sendData);
				switch(sendData.type){
					case "채팅" : printData(sendData); break;
					case "알람" : alamPrint(sendData); break;
				}
				/* const textContainer = $("<div>").append(sup).append(span);
				$("#msgcontainer").append(textContainer); */
			}
		});
		
		const printData=(sendData)=>{
			const sup = $("<sup>").text(sendData.sender).css("marginRight", "2%");
			const text = $("<span>").text(sendData.msg).css("fontSize", "20px");
			const container = $("<div>").css("width","100%").append(sup).append(text);
			if(sendData.sender == $("#sender").val()){
				container.css({
					"display":"flex",
					"justifyContent":"end"
					});
			} else {
				container.css({
					"display":"flex",
					"justifyContent":"start"
					});
			}
			$("#msgcontainer").append(container);
		}
		
		const alamPrint=(sendData)=>{
			const h3 = $("<h3>").text(sendData.msg).css("color", "green");
			const container = $("<div>").css({"display":"flex", "justifyContent":"center"});
			container.append(h3);
			$("#msgcontainer").append(container);
		}
		
		
		// **input창 채팅 구현하기**
		$("#sendBtn").click(e=>{
			const msg = $("#sendMsg").val();
			
			// 보낸사람 붙이기
			const sender = $("#sender").val(); 
			// sendMsg(sender + "," + msg);
			
			// 귓속말
			const receiver = $("#receiver").val();
			
			// 채팅 외 서비스
			const sendData = new Message("채팅", sender, receiver, msg, "");
			sendMsg(sendData);
		});
		
		function sendMsg(msg){
			/* if(msg.length>0) socket.send(msg);
			else throw new Error("메세지가 비어있습니다."); */
			
			// 채팅 외 서비스
			// msg에 sendData 객체가 들어옴
			// js, java 객체 호환 안됨 -> JSON으로 변환해서 보내줌
			socket.send(JSON.stringify(msg));
		}
		
		
		// **채팅 외 서비스**
		// type : 채팅, 사용현황, 알림 등
		// sender : 보낸 사람
		// receiver : 받는 사람
		// msg(data) : type별 메세지
		// room : 방 정보
		// split으로 하면 인덱스 번호가 변경되어서 구분된 데이터는 객체로 처리하는게 좋음
		
		// ECMA6에서 클래스 생성 가능
		class Message{
			constructor(type, sender, receiver, msg, room){
				this.type = type;
				this.sender = sender;
				this.receiver = receiver;
				this.msg = msg;
				this.room = room;
			}
		}
		
	</script>
</body>
</html>