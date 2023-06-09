package com.web.board.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.web.board.model.service.BoardService;
import com.web.board.model.vo.Board;
import com.web.board.model.vo.BoardComment;

/**
 * Servlet implementation class BoardViewServlet
 */
@WebServlet("/board/boardView.do")
public class BoardViewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public BoardViewServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int no = Integer.parseInt(request.getParameter("no"));

		// 조회수 쿠키별로 1 올리기(새로고침으로 올라가는 것 막기)
		Cookie[] cookies = request.getCookies();
		String boardRead = "";
		boolean isRead = false;
		if (cookies != null) {
			for (Cookie c : cookies) {
				if (c.getName().equals("boardRead")) {
					// key이름이 boardRead인 것을 찾음
					boardRead = c.getValue();
					if (boardRead.contains("|" + no + "|")) {
						// 같은 글을 여러번 읽었을 때
						isRead = true;
					}
					break;
				}
			}
		}
		if (!isRead) {
			Cookie c = new Cookie("boardRead", boardRead + "|" + no + "|");
			// |1||2||3| -> | 는 1인지 12인지 123인지를 구별하기 위해 넣어줌
			// 이전 값 없이 게시글 두 개를 읽으면 boardNo 덮어쓰기 됨, 이전 값 확인 필요
			c.setMaxAge(60 * 60 * 24); // 하루동안 게시글 하나만 읽을 수 있음
			response.addCookie(c);
		}
		
		Board b = new BoardService().selectBoardByNo(no, isRead);

		
		// 댓글 가져와 출력하기
		List<BoardComment> comments = new BoardService().selectBoardComment(no);
		request.setAttribute("comments", comments);
		
		
		
		// 원래는 null일 경우 분기처리 해줘야 함(b가 null일 경우 jsp 넘어가서 에러 뜸)
		request.setAttribute("board", b);

		request.getRequestDispatcher("/views/board/boardView.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
