<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>json으로 데이터 가져오기</title>
<script src="http://code.jquery.com/jquery-3.7.0.min.js"></script>
</head>
<body>
	<h2>json을 이용해서 데이터 통신하기</h2>
	<p>
		서버와 클라이언트가 데이터를 주고받을 때 문자열을 js object 표현 방식으로 처리하자<br>
		-> {객체}, [배열(리스트)]
	</p>
	<ul>
		java에서 생성한 객체(vo, Collection들...)를 자동으로 변환해주는 라이브러리를 사용함
		<li>jsonsimple.jar -> JSONObject JSONArray클래스를 이용해서 처리</li>
		<li>gson.jar -> Gson클래스를 이용</li>
		<li>jackson.jar -> ObjectMapper클래스를 이용 *Spring에서 기본사용</li> 
	</ul>
	<button id="jsonBtn">jsonSimple</button>
	<button id="gsonBtn">gsonTest</button>
	<button id="jsonparse">test</button>
	<script>
		$("#jsonparse").click(e=>{
			fetch("<%=request.getContextPath()%>/gsonTest.do",
					{ // request 객체 자체를 보냄
						method:"post",
						body:JSON.stringify({"userId":"bsyoo",
							  "password":"1234",
							  "age":19,
							  "userName":"유병승",
							  "gender":'M',
							  "email":"teacherdev09@gmail.com"})
							  // stringify -> 문자열화해서 보내겠음(java에선 사용할 수 없는 객체이기 때문에)
					}).then(response=>response.json())
					.then(data=>{
						console.log(data);
					});
		});
	
		$("#gsonBtn").click(e=>{
			$.get("<%=request.getContextPath()%>/gsonTest.do",
					function(data){
						console.log(data);
			});
		});
		
	
		$("#jsonBtn").click(e=>{
			$.get("<%=request.getContextPath()%>/basicJson.do",
					function(data){
				/* console.log(data.userId);
				console.log(data.userName);
				console.log(data["age"] + 10);
				console.log(data.height + 20);
				if(data.flag){
					alert("실행!");
				} */
				console.log(data);
			});
		});
	</script>
	
	
</body>
</html>