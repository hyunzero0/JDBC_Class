package com.web.board.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.web.board.model.service.BoardService;
import com.web.board.model.vo.Board;
import com.web.notice.model.service.NoticeService;

/**
 * Servlet implementation class BoardListServlet
 */
@WebServlet("/board/boardList.do")
public class BoardListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardListServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int cPage, numPerpage;
		try {
			cPage = Integer.parseInt(request.getParameter("cPage"));
		} catch (NumberFormatException e) {
			cPage = 1;
		}
		try {
			numPerpage = Integer.parseInt(request.getParameter("numPerpage"));
		} catch (NumberFormatException e) {
			numPerpage = 5;
		}
		String pageBar = "";
		int totalData = new BoardService().selectBoardCount();
		int totalPage = (int)Math.ceil((double)totalData/numPerpage);
		int pageBarSize = 5;
		int pageNo = ((cPage-1)/pageBarSize)*pageBarSize + 1;
		int pageEnd = pageNo + pageBarSize - 1;
		
		if(pageNo == 1) {
			pageBar += "<span>[이전]<span>";
		} else {
			pageBar += "<a href='" + request.getRequestURI()
				+ "?cPage=" + (pageNo-1) + "&numPerpage=" + numPerpage + "'>[이전]</a>";
		}
		while(!(pageNo>pageEnd||pageNo>totalPage)) {
			if(pageNo==cPage) {
				pageBar+="<span>"+pageNo+"</span>";
			}else {
				pageBar+="<a href='"+request.getRequestURI()
					+"?cPage="+pageNo+"'>"+pageNo+"</a>";
			}
			pageNo++;
		}
		if(pageNo>totalPage) {
			pageBar += "<span>[다음]<span>";
		} else {
			pageBar += "<a href='" + request.getRequestURI()
			+ "?cPage=" + pageNo + "&numPerpage=" + numPerpage + "'>[다음]</a>";
			// numPerpage는 선택, 나중에 유동적으로 바뀔 수 있게하려면 보내는 게 좋음(로직에 맞춰서)
		}
		request.setAttribute("pageBar", pageBar);
		
		List<Board> list = new BoardService().selectBoard(cPage, numPerpage);
		request.setAttribute("boards", list);
		request.getRequestDispatcher("/views/board/boardList.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
