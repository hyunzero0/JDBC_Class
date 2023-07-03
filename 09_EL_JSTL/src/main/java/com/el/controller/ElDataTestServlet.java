package com.el.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.el.model.vo.Snack;

/**
 * Servlet implementation class ElDataTestServlet
 */
@WebServlet("/dataTest.do")
public class ElDataTestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ElDataTestServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Snack s = Snack.builder().type("초콜릿").name("m&n").price(1000).weight(50).build();
		Snack s2 = Snack.builder().type("사탕").name("츄파춥스").price(300).weight(10).build();
		Snack s3 = Snack.builder().type("젤리").name("하리보").price(2000).weight(60).build();
		Snack s4 = Snack.builder().type("과자").name("꼬북칩").price(2000).weight(60).build();
		List<Snack> list = List.of(s, s2, s3, s4);
		request.setAttribute("snacks", list);
		
		// key값이 중복일 때
		// request
		request.setAttribute("snack", "request과자");
		// session
		HttpSession session = request.getSession();
		session.setAttribute("snack", "맛있는 과자");
		// context
		ServletContext context = getServletContext();
		context.setAttribute("snack", "Context영역에 과자");
		
		request.getRequestDispatcher("/views/dataTest.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
