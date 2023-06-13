package com.ajax.controller;

import java.io.IOException;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.web.admin.service.AdminService;
import com.web.member.model.vo.Member;

/**
 * Servlet implementation class JsonSimpleServlet
 */
@WebServlet("/basicJson.do")
public class JsonSimpleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public JsonSimpleServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		List<Member> list = new AdminService().selectMemberByKeyword("userId", "a", 1, 30);
		Member m = list.get(0);
		System.out.println(m);
		
		// jsonsimple라이브러리를 이용해서 간편하게 객체를 전송할 수 있다.
		// 단일객체를 전송할 때 -> JSONObject클래스 이용
		// 다수객체를 전송할 때(List, Collection) -> JSONArray클래스 이용
		// 		JSONObject, JSONArray 모두 jsonsimple 라이브러리 제공 객체
		
//		**** 단일객체 전송하기 **** 
		JSONObject jo = new JSONObject();
		// JSONObject가 제공하는 멤버 메소드를 이용해서 전송할 데이터를 저장
		// -> key, value형식으로 저장
		// put() 메소드 제공
		jo.put("userId", m.getUserId());
		jo.put("userName", m.getUserName());
		jo.put("age", m.getAge());
		jo.put("height", 180.5);
		jo.put("flag", true);
		
		
//		**** 다수객체 전송하기 ****
		JSONArray joa = new JSONArray();
		// JSONArray에는 리스트 방식으로 JSONObject를 저장 -> add()를 이용해서 저장
		// *char타입 꼭 String으로 형변환 해줘야 함
		for(Member m1 : list) {
			JSONObject j = new JSONObject();
			j.put("userId", m1.getUserId());
			j.put("userName", m1.getUserName());
			j.put("age", m1.getAge());
			j.put("gender", String.valueOf(m1.getGender())); 
			// gender는 char타입이라서 파싱 못해줌 -> 자료형에 자유롭지 못함
			j.put("phone", m1.getPhone());
			j.put("height", 180.5);
			j.put("flag", true);
			joa.add(j);
		}
		
		// 생성된 JSONObject를 전송
		response.setContentType("application/json; charset=utf-8");
		// 현재는 java코드 -> JSON이 자동으로 js객체로 변환해서 보내줌
		// response.getWriter().print(jo); // 단일객체 전송
		response.getWriter().print(joa); // 다수객체 전송(똑같음)
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
