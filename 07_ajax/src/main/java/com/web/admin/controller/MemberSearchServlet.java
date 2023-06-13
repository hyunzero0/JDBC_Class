package com.web.admin.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.web.admin.service.AdminService;
import com.web.member.model.vo.Member;

/**
 * Servlet implementation class MemberSearchServlet
 */
@WebServlet("/admin/searchMember")
public class MemberSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberSearchServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 클라이언트가 보낸 데이터를 기준으로 Member테이블에서 
		// 해당하는 데이터를 조회해서 보내줌
		String type=request.getParameter("searchType");
		String keyword=request.getParameter("searchKeyword");
		int cPage, numPerpage;
		try {
			cPage = Integer.parseInt(request.getParameter("cPage"));
		}catch(NumberFormatException e) {
			cPage = 1;
		}
		try {
			numPerpage = Integer.parseInt(request.getParameter("numPerpage"));
		}catch(NumberFormatException e) {
			numPerpage = 5;
		}
		
		// 페이징 처리를 위해 매개변수 cPage, numperPage 추가
		List<Member> members=new AdminService().selectMemberByKeyword(type, keyword, cPage, numPerpage);
		
		request.setAttribute("members", members);
		String pageBar = "";
		
		// DB에 저장된 전체 데이터 수
		int totalData = new AdminService().selectMemberByKeywordCount(type, keyword);
		// 전체 페이지 수
		int totalPage = (int)Math.ceil((double)totalData/numPerpage);
		// 페이지바에 출력될 페이지 수
		int pageBarSize = 5;
		
		int pageNo = ((cPage-1)/pageBarSize)*pageBarSize + 1;
		int pageEnd = pageNo + pageBarSize - 1;
		
		if(pageNo == 1) {
			pageBar += "<span>[이전]</span>";
		} else {
			pageBar += "<a href='"+request.getRequestURI()+"?searchType=" + type + "&searchKeyword=" + keyword
					+ "&cPage=" + (pageNo-1) + "&numPerpage=" + numPerpage + "'>[이전]</a>";
		}
		
		while(!(pageNo > pageEnd || pageNo > totalPage)) {
			if(pageNo==cPage) {
				pageBar += "<span>" + pageNo + "</span>";
			} else {
				pageBar += "<a href='"+request.getRequestURI()+"?searchType=" + type + "&searchKeyword=" + keyword
						+ "&cPage=" + pageNo + "&numPerpage=" + numPerpage + "'>" + pageNo + "</a>";
			}
			pageNo++;
			// ++ 안되면 무한루프
		}
		
		if(pageNo > totalPage) {
			pageBar += "<span>[다음]</span>";
		} else {
			pageBar += "<a href='"+request.getRequestURI()+"?searchType=" + type + "&searchKeyword=" + keyword
					+ "&cPage=" + pageNo + "&numPerpage=" + numPerpage + "'>[다음]</a>";
		}
		
		request.setAttribute("pageBar", pageBar);
		
		request.getRequestDispatcher("/views/admin/manageMember.jsp")
		.forward(request, response);
		
	
	
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
