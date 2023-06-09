package com.web.board.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.web.board.model.service.BoardService;
import com.web.board.model.vo.BoardComment;

/**
 * Servlet implementation class BoardCommentInsertServlet
 */
@WebServlet("/board/insertComment.do")
public class BoardCommentInsertServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardCommentInsertServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BoardComment bc = BoardComment.builder()
				.boardRef(Integer.parseInt(request.getParameter("boardRef")))
				.level(Integer.parseInt(request.getParameter("level")))
				.boardCommentWriter(request.getParameter("boardCommentWriter"))
				.boardCommentContent(request.getParameter("content"))
				.boardCommentRef(Integer.parseInt(request.getParameter("boardCommentRef")))
				.build();
		
		int result = new BoardService().insertBoardComment(bc);
		
		String view = "";
		if(result>0) {
			// 댓글 입력 성공
			view = request.getContextPath() + "/board/boardView.do?no=" + bc.getBoardRef();
			response.sendRedirect(view);
//			view = "/board/boardView.do?no=" + bc.getBoardRef();
//			request.getRequestDispatcher(view).forward(request, response); -> 계속 등록됨
			
			// jsp로 바로 가면 getAttribute한 값을 불러올 수 없음 -> set 설정한 이전 주소로 가야함(서블릿)
			// boardView 서블릿에 parameter값 no를 받기 때문에 같이 보내줘야 함
		} else {
			// 댓글 입력 실패
			request.setAttribute("msg", "댓글등록 실패 :(");
			request.setAttribute("loc", "/board/boardView.do?no=" + bc.getBoardRef());
			view = "/views/common/msg.jsp";
			request.getRequestDispatcher(view).forward(request, response);
			// msg.jsp에서 주소가 아예 바뀌기 때문에 getRequestDispatcher로 해도 됨
		}
		
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
