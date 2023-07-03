<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!-- jstl을 적용하기 위해서는 반드시 페이지에 지시자로 taglib를 선언해야 함 -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- core태그라서 c, 컨트롤+스페이스바 누르면 uri 자동 생성 -->
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<!-- 텍스트 노드 없이 모두 속성으로 설정 -->
	<h3><c:out value="우와 코어태그로 출력"/></h3> <!-- 보안에 가장 좋음 -->
<%-- ${"우와 코어태그로 출력" }
	<%="우와 코어태그로 출력" %> --%>
	<!-- c:out 자체가 텍스트 노드가 됨 -->
	
	<h2>****set/out태그 이용하기****</h2>
	<p>
		c:out태그 : value속성에 있는 값을 페이지에 출력할 때 사용하는 태그<br>
		c:set태그 : 내장객체(pageContext)영역에 데이터를 key:value형식으로 저장할 때 사용하는 태그
	</p>
	<ul>c:set태그 속성
		<li>var : key값(변수명)</li>
		<li>value : key에 저장된 값 -> EL표현식, 리터럴을 사용</li>
		<li>scope : 변수가 선언될 내장객체를 지정, request, session, application</li>
	</ul>
	<ul>c:out태그 속성
		<li>value : 출력될 데이터 -> EL표현식, 리터럴을 사용</li>
		<li>default : 출력될 데이터가 없을 때 대체해서 출력하는 값</li>
		<li>escapeXml : value속성에 태그형식으로 작성했을 때 태그로 해석할지 여부를 선택</li>
	</ul>
	
	<h3>**c:set태그로 변수선언하기**</h3>
	<c:set var="comment" value="점심 맛있게 먹었나요?"/>
	<p>${comment }</p>
	<c:set var="path" value="${pageContext.request.contextPath }"/>
	<p>절대경로 : ${path }</p>
	
	<c:set var="test" value="requestData" scope="request"/>
	<c:set var="test" value="sessionData" scope="session"/>
	<c:set var="test" value="applicationData" scope="application"/>
	<p>${test }</p>
	<p>${sessionScope.test }</p>
	<p>${applicationScope.test }</p>
	
	<h3>**c:out태그로 데이터 출력하기**</h3>
	<p><c:out value="점심은 뭐 드셨나요?"/></p>
	<p><c:out value="${path }"/></p>
	<p>${path }</p>
	<c:set value="<script>location.assign('http://www.naver.com');</script>"
		   var="testData"/>
	<%-- <div>${testData }</div> --%>
	<div><c:out value="${testData }"/></div> <!-- 보안성이 더 뛰어남 -->
	<div><c:out value="${testData }" escapeXml="true"/></div> <!-- true면 태그 자체 출력, false면 태그 실행 -->
	
	<p>${test11==null ? "없음" : test11 }</p>
	<c:set value="있는 값" var="test11"/>
	<p><c:out value="${test11 }" default="없음"/></p>
	
	<h3>**c:url태그**</h3>
	<p>링크되는 주소값 데이터를 저장하는 태그 == c:set</p>
	<c:url var="cesearch" value="http://search.naver.com/search.naver">
		<c:param name="query" value="김찬은"/>
	</c:url>
	<a href="<c:out value='${cesearch }'/>">검색</a>
	
	
</body>
</html>