<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ include file="/views/common/header.jsp" %>
<section id="content">
	<h2 align="center" style="margin-top:200px">
		안녕하세요,MVC입니다.
	</h2>
	<button id="memberAll">전체회원조회</button>
	<input type="text" id="id"><button>아이디조회</button>
	<div id="memberList"></div>
	<!-- jsp이용x, 데이터 받아와서 출력 -->
	
	<script>
		$("#memberAll").click(e=>{
			$.get("<%=request.getContextPath()%>/agaxSelectMemberAll.do",
					function(data){
						console.log(data);
						const table = $("<table>");
						const header = $("<tr>");
						const headerdata = ["아이디", "이름", "나이", "성별", "이메일", "전화번호", "주소", "취미", "가입일"];
						headerdata.forEach(e=>{
							const th = $("<th>").text(e);
							header.append(th);
						});
						table.append(header);
						
						// 테이블에 값 넣어야함
						
						$("#memberList").html(table);
			})
		});
		
		
		$("#id").keyup(e=>{
			// 버튼에 아이디 값을 줘야하나?
		});
	</script>
	
</section>
<%@ include file="/views/common/footer.jsp" %>		
