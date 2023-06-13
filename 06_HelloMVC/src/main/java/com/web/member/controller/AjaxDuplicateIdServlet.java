package com.web.member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.web.member.model.service.MemberService;
import com.web.member.model.vo.Member;

/**
 * Servlet implementation class AjaxDuplicateIdServlet
 */
@WebServlet("/ajaxDuplicateId.do")
public class AjaxDuplicateIdServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AjaxDuplicateIdServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// ajax용 응답서블릿
		String userId = request.getParameter("userId");
		// $(e.target).val() 값 가져옴
		Member m = new MemberService().selectByUserId(userId);
		// m이 null이면 사용가능아이디, null이 아니면 이미 있는 아이디
		
		response.setContentType("text/csv; charset=utf-8");
		
		// 출력해줘야 함 -> getWriter() 사용
		response.getWriter().print(m==null?true:false);
		// msg자체를 보내줘도 되지만 값을 보낸 후 jsp에서 분기처리 가능
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
