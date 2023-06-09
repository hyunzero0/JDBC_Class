package com.web.notice.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class FileDownloadServlet
 */
@WebServlet("/fileDownload.do")
public class FileDownloadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FileDownloadServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 파일에 대한 다운로드 기능 구현하기
		// 1. 클라이언트가 보낸 파일이름 받기
		String fileName = request.getParameter("name");
		
		// 2. 파일에 대한 절대경로 가져오기(getRealPath)
		// 하드에 저장되어 있으므로 Stream 사용해야함
		String path = getServletContext().getRealPath("/upload/notice/");
		File f = new File(path + fileName);
		
		// 3. InputStream을 생성 -> FileInputStream을 생성
		// InputStream은 인터페이스이기 때문에 FileInputStream 구현체를 사용
		FileInputStream fis = new FileInputStream(f);
		// 속도개선을 위한 BufferedInputStream사용(선택)
		BufferedInputStream bis = new BufferedInputStream(fis);
		
		// 4. 한글파일명이 깨지지않도록 인코딩 처리하기
		// byte단위라서 한글 파일은 깨짐(한글파일이 없다면 안해도 됨)
		String fileRename = "";
		String header = request.getHeader("user-agent");
		// getHeader : 헤더 정보 가져옴 / user-agent : 브라우저에 대한 정보
		// IE, 그 외 브라우저랑 인코딩 처리방식이 달라서 인코딩을 분리해서 처리해줘야 함
		boolean isMSIE = header.indexOf("MSIE")!=-1 || header.indexOf("Trident")!=-1;
		if(isMSIE) {
			fileRename = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
			// \\+ 를 띄어쓰기 처리함 -> 인코딩 처리 
		} else {
			fileRename = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
		}
		
		// 5. 응답해더설정 -> contentType 설정
		response.setContentType("application/octet-stream");
		// 불특정한 파일(엑셀, 한글 파일 등이 아닌 불특정 파일)을 ContentType으로 설정
		response.setHeader("Content-disposition", "attachment;filename="+fileRename);
		// attachment 속성 = 파일 바로 다운로드 시킴(크롬 설정 바꾸면 다운로드할 경로 지정 가능)
		// index 속성 = 파일 바로 열림
		
		// 6. 사용자에게 파일 보내기
		// 클라이언트(response)의 스트림을 가져오기 -> getOutputStream(); = 바이너리파일 / response.getWriter은 String이라서 x
		ServletOutputStream sos = response.getOutputStream();
		BufferedOutputStream bos = new BufferedOutputStream(sos);
		
		int read = -1;
		while((read=bis.read())!=-1) {
			bos.write(read);
		}
		
		bis.close();
		bos.close();
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
