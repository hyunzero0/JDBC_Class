package com.web.notice.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.web.notice.model.service.NoticeService;
import com.web.notice.model.vo.Notice;

/**
 * Servlet implementation class NoticeInsertEndServlet
 */
@WebServlet("/notice/insertNotice.do")
public class NoticeInsertEndServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NoticeInsertEndServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 파일 업로드 처리하기(클라이언트 컴퓨터에 있는 데이터 -> 서버 컴퓨터로 옮기는 것 = 업로드)
		// -> cos.jar 라이브러리가 제공하는 클래스를 이용
		
//		 1. multipart/form-data형식의 요청인지 확인
		if(!ServletFileUpload.isMultipartContent(request)) {
			// multipart형식이 아니면
			request.setAttribute("msg", "잘못된 접근입니다. 관리자에게 문의하세요 :(");
			request.setAttribute("loc", "/");
			request.getRequestDispatcher("/views/common/msg.jsp").forward(request, response);
			return ;
		}

//		 2. 데이터 업로드 처리하기
		// cos.jar에서 제공하는 MultipartRequest 클래스를 이용해서 처리
		// MultipartRequest 클래스를 생성하면 자동으로 request에 담겨있는
		// 데이터(file)를 지정된 위치에 저장함
		// 매개변수 있는 생성자를 이용해서 생성
		// 		매개변수
		// 		1) HttpServletRequest를 대입 -> HttpServletRequest
		//		2) 파일을 저장할 위치 설정 = 절대경로 -> String
		//		3) 업로드 파일의 최대크기 설정 -> int(1024(1byte)*1024(MB)*1024(GB)*1024(TB))
		//		4) 인코딩 방식 -> String(utf-8)
		//		5) rename규칙설정(클래스) -> 기본제공클래스(DefaultFileRenamePolicy)
		
		// 2) String으로 저장할 위치(절대경로) 가져오기
		// ServletContext객체를 이용해서 웹애플리케이션의 절대경로를 가져올 수 있다.
		String path = getServletContext().getRealPath("/upload/notice");
		// "/" = webapp까지의 하드 절대경로 불러옴
		System.out.println(path);
		// 3) 최대 파일크기 설정
		int maxSize = 1024 * 1024 * 100; // 100MB
		// 4) 인코딩 설정
		String encode = "UTF-8";
		// 5) 리네임클래스 생성
		DefaultFileRenamePolicy dfr = new DefaultFileRenamePolicy();
		
		// MultipartRequest 클래스 생성하기
		// -> 생성과 동시에 지정된 위치에 업로드된 파일을 저장시킴
		MultipartRequest mr = new MultipartRequest(request, path, maxSize, encode, dfr);
		
		// multipart/form-data형식 request는 parameter로 데이터 가져올 수 없음
		// 클라이언트가 보낸 데이터는 생성된 MultipartRequest객체를 이용해서 가져온다.
		// -> getParameter("name")
		String noticeTitle = mr.getParameter("noticeTitle");
		String noticeWriter = mr.getParameter("noticeWriter");
		String noticeContent = mr.getParameter("noticeContent");
		// 저장된 파일에 대한 정보도 가져올 수 있음
		// 원본파일명, 재정의파일명 등의 정보를 가져올 수 있다.
		String orifilename = mr.getOriginalFileName("upFile");
		String renamefilename = mr.getFilesystemName("upFile");
		
//		System.out.println(noticeTitle + " " + noticeWriter + " " + noticeContent
//						+ " " + orifilename + " " + renamefilename);
		
		Notice n = Notice.builder()
				.noticeTitle(noticeTitle)
				.noticeWriter(noticeWriter)
				.noticeContent(noticeContent)
				.filePath(renamefilename)
				.build();
		
		int result = new NoticeService().insertNotice(n);
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