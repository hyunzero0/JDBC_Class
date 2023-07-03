<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- <%@ include file="/views/common/header.jsp" %> --%>
<%request.setCharacterEncoding("utf-8"); %>
<!-- jsp액션태그 이용해서 헤더 불러오기 -->
<jsp:include page="/views/common/header.jsp">
	<jsp:param name="title" value="메인화면"/>
</jsp:include>
<!-- 단일태그일 때 그냥 닫아도됨, 닫기태그 따로 써줄 때는 파라미터 필요 -->
<section>
	<h3>본문내용을 출력하자.</h3>
	<%-- <p>접속한 회원 : <%=userId %></p> 액션태그 사용 시 데이터는 전달 안됨 --%>
</section>

	<%-- <jsp:forward page="/views/board.jsp"/> --%>
	<!-- board페이지로 바로 이동(서블릿에서 사용하기 때문에 잘 안씀) -->
	
</body>
</html>