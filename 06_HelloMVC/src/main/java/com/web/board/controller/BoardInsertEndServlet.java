package com.web.board.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.web.board.model.vo.Board;
import com.web.board.model.service.BoardService;

/**
 * Servlet implementation class BoardInsertEndServlet
 */
@WebServlet("/board/insertBoard.do")
public class BoardInsertEndServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardInsertEndServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(!ServletFileUpload.isMultipartContent(request)) {
			// multipart형식이 아니면
			request.setAttribute("msg", "잘못된 접근입니다. 관리자에게 문의하세요 :(");
			request.setAttribute("loc", "/");
			request.getRequestDispatcher("/views/common/msg.jsp").forward(request, response);
			return ;
		}
		
		String path = getServletContext().getRealPath("/upload/board");
		int maxSize = 1024 * 1024 * 100; // 100MB
		String encode = "UTF-8";
		DefaultFileRenamePolicy dfr = new DefaultFileRenamePolicy();

		MultipartRequest mr = new MultipartRequest(request, path, maxSize, encode, dfr);

		String boardTitle = mr.getParameter("boardTitle");
		String boardWriter = mr.getParameter("boardWriter");
		String boardContent = mr.getParameter("boardContent");

		String orifilename = mr.getOriginalFileName("upFile");
		String renamefilename = mr.getFilesystemName("upFile");
		
		 Board b = Board.builder()
			.boardTitle(boardTitle)
			.boardWriter(boardWriter)
			.boardContent(boardContent)
			.boardOriFilename(orifilename)
			.boardRnFilename(renamefilename)
			.build();
		
		 int result = new BoardService().insertBoard(b);
			String msg="공지사항 등록완료", loc="/notice/noticeList.do";
			if(result==0) {
				msg = "공지사항 등록실패";
				loc = "/notice/insertForm.do";
			}
			
			request.setAttribute("msg", msg);
			request.setAttribute("loc", loc);
			
			request.getRequestDispatcher("/views/common/msg.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
