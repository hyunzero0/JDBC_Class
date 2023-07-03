<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h2>****페이지에 숫자 표현하기****</h2>
	<c:set var="numtest" value="123456012"/>
	<c:set var="numtest1" value="19883000"/>
	<c:set var="numtest2" value="1"/>
	<c:set var="numtest3" value="1234.567"/>
	<p>일반숫자 출력 : ${numtest }</p>
	<p>기본 fmt태그를 이용해서 출력 : <fmt:formatNumber value="${numtest }"/>원</p>
	<p>숫자단위 쉼표를 처리하는 속성 : groupingUsed = "true/false"</p>
	<p>true : <fmt:formatNumber value="${numtest }" groupingUsed="true"/></p>
	<p>false : <fmt:formatNumber value="${numtest }" groupingUsed="false"/></p>
	
	<h3>**숫자 화폐표시하기**</h3>
	<p>type속성을 currency로 설정</p>
	<p>원화로 표시 : <fmt:formatNumber value="${numtest1 }" type="currency"/></p>
	<p>원하는 화폐기호 표시 : <fmt:formatNumber value="${numtest1 }" type="currency" currencySymbol="^.~"/>
	<!-- 국가별 화폐 표시 다름, 국가 바꾸기(국가별 로케일 검색) -->
	<%-- <fmt:setLocale value="ja_JP"/> --%>
	<p>일본 표시 : <fmt:formatNumber value="${numtest1 }" type="currency"/></p>
	<%-- <fmt:setLocale value="fr_Fr"/> --%>
	<p>프랑스 표시 : <fmt:formatNumber value="${numtest1 }" type="currency"/></p>
	<p>현재 로케일 확인 : ${pageContext.request.locale }</p>
	
	<h3>**퍼센트 표시하기**</h3>
	<p>소수점으로 표시 1->100%, 0->0%</p>
	<p>numtest2 : ${numtest2 }</p>
	<p>퍼센트 : <fmt:formatNumber value="${numtest2 }" type="percent"/></p>
	<p>퍼센트 : <fmt:formatNumber value="0.5" type="percent"/></p>
	
	<h3>패턴으로 숫자를 표시하기</h3>
	<p>
		자릿수에 맞춰서 특정문구를 출력<br>
		0 : 지정한 자리에 수가 없으면 0으로 표시<br>
		# :	지정한 자리에 수가 없으면 표시를 생략
	</p>
	<p>0 : ${numtest3 } -> <fmt:formatNumber value="${numtest3 }" pattern="000,000,000"/></p>
	<p># : ${numtest3 } -> <fmt:formatNumber value="${numtest3 }" pattern="###,###,###"/></p>
	<p>소수점 : ${numtest3 } -> <fmt:formatNumber value="${numtest3 }" pattern="000,000.000000"/></p>
	<p>소수점 : ${numtest3 } -> <fmt:formatNumber value="${numtest3 }" pattern="###,###.######"/></p>
	<p>소수점 : ${numtest3 } -> <fmt:formatNumber value="${numtest3 }" pattern="###,###.000000"/></p>
	
	<h3>소수점 자리수 설정하기</h3>
	<p>
		minFractionDigits : 최소 소수점 자리<br>
		maxFractionDigits : 최대 소수점 자리
	</p>
	<h3><fmt:formatNumber value="123.123" minFractionDigits="2"/></h3>
	<h3><fmt:formatNumber value="123.1261231" maxFractionDigits="2"/></h3>
</body>
</html>