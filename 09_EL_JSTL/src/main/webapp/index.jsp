<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>액션태그</title>
</head>
<body>
	<h2>****액션태그 활용하기****</h2>
	<p>jsp페이지에서 java코드를 html태그 방식으로 작성할 수 있게 해주는 태그</p>
	<ul>
		<li>
			표준액션태그 : 기본 jsp에서 제공하는 태그
		</li>
		<li>
			커스텀액션태그(JSTL) : 별도 jar파일로 제공하는 태그 * jar파일 추가해야 사용 가능
		</li>
	</ul>
	
	<h3>**표준액션태그 이용하기**</h3>
	<p>태그를 작성할 때 jsp prefix를 사용</p>
	<%-- 예) <jsp:태그명></jsp:태그명> 반드시 닫기 태그 명시(자바코드 {}와 같음) --%>

	<h3>1. jsp:include태그 활용하기</h3>
	<p>다른 jsp페이지를 불러와 출력해주''는 태그</p>
	<%-- <%@ include %> 태그와 비슷한 기능 --%>
	<p>jsp:include page="불러올 페이지 경로"</p>
	<a href="<%=request.getContextPath() %>/views/includeTest.jsp">jsp:include테스트</a>
	
	
	<h3>**커스텀 액션태그 -> JSTL 이용하기**</h3>
	
	<h4>1. EL표현식 활용하기</h4>
	<p>java코드로 생성된 데이터를 페이지에 출력해주는 표현식</p>
	<p><%-- ${} --%> -> 공용객체(request, session, Application)에 저장된 데이터를 불러와 출력함<br>
			  jsp에서 `백틱 못 쓴 이유</p>
	<h3>
		<a href="<%=request.getContextPath() %>/views/el/eltest.jsp">EL사용하기</a>
	</h3>
	
	
</body>
</html>