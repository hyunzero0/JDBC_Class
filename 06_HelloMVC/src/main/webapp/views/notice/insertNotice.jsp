<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/views/common/header.jsp"%>
<section id="notice-container">
	<h2>공지사항 작성</h2>
	<!-- 첨부파일 있을 때는 post형식과 enctype을 사용해서 바이너리파일로 받음-->
	<!-- enctype은 post형식일 때만 사용 가능 -->
	<!-- enctype 속성은 폼 데이터(form data)가 서버로 제출될 때 해당 데이터가 인코딩되는 방법 -->
	<form action="<%=request.getContextPath() %>/notice/insertNotice.do" method="post" enctype="multipart/form-data">
		<table id="tbl-notice">
			<tr>
				<th>제 목</th>
				<td><input type="text" name="noticeTitle" required></td>
			</tr>
			<tr>
				<th>작성자</th>
				<td><input type="text" name="noticeWriter"
					value="<%=loginMember.getUserId()%>" readonly></td>
			</tr>
			<tr>
				<th>첨부파일</th>
				<td><input type="file" name="upFile"></td>
			</tr>
			<tr>
				<th>내 용</th>
				<td><textarea rows="5" cols="42" name="noticeContent"></textarea></td>
			</tr>
			<tr>
				<th colspan="2"><input type="submit" value="등록하기" onclick="">
				</th>
			</tr>
		</table>
	</form>
</section>
<%@ include file="/views/common/footer.jsp"%>

<style>
section#notice-container {
	width: 600px;
	margin: 0 auto;
	text-align: center;
}

section#notice-container h2 {
	margin: 10px 0;
}

table#tbl-notice {
	width: 500px;
	margin: 0 auto;
	border: 1px solid black;
	border-collapse: collapse;
	clear: both;
}

table#tbl-notice th {
	width: 125px;
	border: 1px solid;
	padding: 5px 0;
	text-align: center;
}

table#tbl-notice td {
	border: 1px solid;
	padding: 5px 0 5px 10px;
	text-align: left;
}
</style>