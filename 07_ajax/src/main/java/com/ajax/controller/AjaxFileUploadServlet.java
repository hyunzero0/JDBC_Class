package com.ajax.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

/**
 * Servlet implementation class AjaxFileUploadServlet
 */
@WebServlet("/fileUpload.do")
public class AjaxFileUploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AjaxFileUploadServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = getServletContext().getRealPath("/test/");
		MultipartRequest mr = new MultipartRequest(request, path, 1024*1024*10, "UTF-8", new DefaultFileRenamePolicy());
		
		String name = mr.getParameter("name");
		System.out.println(name);
		// 리네임 파일
//		System.out.println(mr.getFilesystemName("upFile0"));
		// form.append("upFile" + i, f); -> key값 동일하게
		// 원본 파일
//		System.out.println(mr.getOriginalFileName("upFile0"));
		// -> cos.jar가 있어야 함
	
		// 다중사진 저장해서 DB에 넘겨야 함
		List<Map<String, String>> files = new ArrayList();
		Enumeration<String> names = mr.getFileNames();
		while(names.hasMoreElements()) {
			String key = names.nextElement();
			System.out.println(key);
			// 위 upFile0 부분에 들어갈 key값 알 수 있음
			System.out.println(mr.getFilesystemName(key));
			System.out.println(mr.getOriginalFileName(key));
			files.add(Map.of("rename", mr.getFilesystemName(key), "oriname", mr.getOriginalFileName(key)));
		}
		System.out.println(files);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
