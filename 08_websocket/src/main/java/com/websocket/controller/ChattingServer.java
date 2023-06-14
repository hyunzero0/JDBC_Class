package com.websocket.controller;

import java.io.IOException;
import java.util.Set;

import javax.websocket.EncodeException;
import javax.websocket.EndpointConfig;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import com.google.gson.Gson;
import com.websocket.model.vo.Message;

@ServerEndpoint(value = "/chatting",
		decoders = {JsonDecoder.class}, // json형식의 데이터를 자바클래스로 변경
		encoders = {JsonEncoder.class} // 자바클래스를 json형식으로 변경
)
public class ChattingServer {

//	private Set<Session> clients = new ArrayList();
	// session은 겹치면 안됨(-> Set)
	// but WebSocket객체가 만들어지면 새로 session만들어짐

	// session은 달라지면 안되어서
	// session 실제 접속유지중인지 확인할 수 있는 메소드가 있음 -> 번거로움
	// -> getOpenSessions()메소드 제공

	@OnOpen
	public void open(Session session, EndpointConfig config) {
		// 클라이언트가 접속요청을 하면 실행되는 메소드
		System.out.println(session.getId());
		// getId는 그냥 PK같은 고유 번호, 접속할 때마다 1씩 추가됨
		System.out.println("서버접속!");

//		clients.add(session);
		// 접속될 때마다 session 추가
	}

	@OnMessage
//	public void message(Session session, String msg) {
	public void message(Session session, Message m) {
		// js에서 socket.send("메세지") 함수를 실행했을 때 실행되는 메소드
		// send() 함수 = 데이터를 서버에 보냄
		// send() 함수의 인자값이 두번째 String msg 매개변수에 저장이 된다.
		// -> 클라이언트가 보낸 데이터가 두번째 매개변수에 저장
		// System.out.println(msg);

		// 나중에는 필터 적용해서 자동으로 되게 만들거임
		// Message m = new Gson().fromJson(msg, Message.class);

		System.out.println(m);

		// 귓속말
		switch (m.getType()) {
		// EnumType 쓰면 좋음
		case "접속":
			addClient(session, m);
			break;
		case "채팅":
			sendMessage(session, m);
			break;
		}

		// 접속한 session을 가져올 수 있는 메소드 제공
//		Set<Session> clients = session.getOpenSessions();
//		System.out.println(clients.size());

		// session 구별을 위한 데이터 저장(session에 대한 구분자 값 정하기)
		// session.getUserProperties().put("msg", msg);

		// **접속한 사용자에게 받은 메세지를 전달**
		// 매개변수 session은 send한 사용자의 session
		// -> 여러 브라우저가 접속할 수 있는데 각 브라우저의 session이 들어옴
		/*
		 * try{ for(Session client : clients) { session. //
		 * session.getBasicRemote().sendText(msg);
		 * client.getBasicRemote().sendText(msg); // 접속된 사람 모두에게 msg전달
		 * 
		 * // 귓속말 : sendText 보낼 때부터 분기처리 해줘야함(console에 보이지않게)
		 * 
		 * // getBasicRemote() => // senText() => 메세지 전달 } } catch(IOException e) {
		 * e.printStackTrace(); }
		 */

	}

	private void addClient(Session session, Message msg) {
		// session을 구분할 수 있는 데이터 저장하기
		session.getUserProperties().put("msg", msg);
		sendMessage(session, Message.builder().type("알람").msg(msg.getSender() + "님 접속").build());
	}

	private void sendMessage(Session session, Message msg) {
		// 접속한 클라이언트에게 메세지를 전송해주는 기능
		Set<Session> clients = session.getOpenSessions();
		try {
			if(msg.getReceiver()==null || msg.getReceiver().isBlank()) {
				// 전체 접속자에게 전송
				for(Session client : clients) {
					client.getBasicRemote().sendObject(msg);
					//.sendText(new Gson().toJson(msg));
					
				}
			} else {
				// 받는 사람에게만 전송
				for(Session client : clients) {
					Message c = (Message)client.getUserProperties().get("msg");
					if(c.getSender().equals(msg.getReceiver())) {
					client.getBasicRemote().sendObject(msg); //sendText(new Gson().toJson(msg));
					}
				}
			}
		} catch(IOException|EncodeException e) {
			e.printStackTrace();
		}
		
	}

}
