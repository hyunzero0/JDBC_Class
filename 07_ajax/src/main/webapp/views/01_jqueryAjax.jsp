<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>jquery ajax</title>
<script src="http://code.jquery.com/jquery-3.7.0.min.js"></script>
</head>
<body>
	<h2>jquery가 제공하는 함수 이용하기</h2>
	<ol>
		<li>$.ajax({}) : 요청에 대한 상세설정을 할 때 사용 -> header, 요청내용 설정</li>
		<li>$.get("", (data)=>{}) : 기본 get방식 요청 처리할 때 사용 -> 간편 함수(요청주소,
			callback함수)</li>
		<li>$.post("", {}, (data)=>{}) : 기본 post방식 요청 처리할 때 사용 -> 간편 함수</li>
	</ol>

	<h2>$.ajax() 함수 활용하기</h2>
	<p>
		매개변수로 요청에 대한 설정을 한 객체를 전달<br> 매개변수 객체의 key값은 $.ajax() 함수에서 정해놓음<br>
		url : 요청주소 -> String<br> [type : 요청방식(get, post) -> String]
		(default : get)<br> [data : 서버에 요청할 때 전송할 데이터 ->
		Object({key:value, ...})]<br> [dataType : 응답데이터 타입설정 ->
		String(json, html, text, ...)]<br> success : 응답이 완료되고 실행되는
		callback함수 status 200일 때(성공) 실행하는 함수 -> (data)=>{}<br> [error :
		응답이 완료되고 실행되는 callback함수 status 200이 아닐 때(실패) 실행하는 함수 -> (r, m,
		e)=>{}]<br> [complete : 응답이 성공, 실패 되어도 무조건 실행되는 함수 -> ()=>{}]<br>
		* url, success는 필수 매개변수
	</p>

	<button id="btn">기본 $.ajax 이용하기</button>
	<button id="btnGet">기본 $.get 이용하기</button>
	<button id="btnPost">기본 $.post 이용하기</button>
	<div id="container"></div>
	<script>
		/* $.ajax */
		$("#btn").click(e=>{
			/* const requst ={
				// 저장공간을 하나 더 쓰는 거라 변수 굳이 만들진 않아도 됨
			}
			$.ajax(request); */
			$.ajax({
				url:"<%=request.getContextPath()%>/jquery/ajax.do",
				// type:"get",
				type:"post",
				// data : get방식일 때 데이터 알아서 파싱해서 쿼리스트링으로 주소창에 요청
				data:{name:"유병승", age:19},
				// status 값에 따라 success, error 둘 중 하나의 함수 실행
				success:(data)=>{
					// responseText 속성에 저장된 값이 data에 자동으로 들어감
					// 특정 시점이 되면 ajax가 자동으로 실행(callback함수)
					// console.log(data);
					$("<h3>").text(data).css("color", "magenta")
					.appendTo($("#container"));
					// 데이터를 갖고와서 프론트에서 태그 만들어줌(CSR)
				},
				error:(r,m)=>{
					// XML 데이터
					console.log(r);
					console.log(m);
					// r : request객체, m : msg, e : errormsg?(모르겠음!)
					if(e.status==404) alert("요청한 페이지가 없습니다.");
				},
				complete:()=>{
					alert("서버와 통신 끝 ! >0<");
				}
			});
		});
		
		/* $.get */
		$("#btnGet").click(e=>{
			$.get("<%=request.getContextPath()%>/jquery/ajax.do?name=최주영&age=26",
					data=>{
						console.log(data);
						$("<h4>").text(data).css("color", "lime").appendTo($("#container"));
					})	
		});
		
		/* $.post */
		$("#btnPost").click(e=>{
			// Network -> payload에 저장되어야 함(쿼리스트링 x)
			$.post("<%=request.getContextPath()%>/jquery/ajax.do",
					{name:"김재훈", age:29},
					data=>{
						$("<h1>").text(data).css("color", "cornflowerblue").appendTo($("#container"));
					}
			);
		})
	</script>

	<h2>서버에 저장되어있는 문자 파일 가져오기</h2>
	<button id="btnFile">test.txt 가져오기</button>
	<div id="result2"></div>
	<script>
		$("#btnFile").click(e=>{
			$.get("<%=request.getContextPath()%>/test/test.txt",
					data=>{
						const arr=data.split("\n");
						console.log(arr);
						arr.forEach(e=>{
							$("#result2").append($("<p>").text(e));
						});
						
						const person = arr[arr.length-1];
						console.log(person);
						const persons = person.split("\\n");
						console.log(persons);
						const table = $("<table>");
						persons.forEach(e=>{
							const tr = $("<tr>");
							const p = e.split(",");
							p.forEach(d=>{
								tr.append($("<td>").text(d).css("border", "1px solid black"));
							});
							table.append(tr);
						});
						$("#result2").append(table);
					});
			
			<%-- $.ajax({
				url:"<%=request.getContextPath()%>/test/test.txt",
				dataType:"text",
				success:data=>{
					console.log(data);
					const arr=data.split("\n");
					console.log(arr);
					arr.forEach(e=>{
						$("#result2").append($("<p>").text(e));
					});
					
					const person = arr[arr.length-1];
					console.log(person);
					const persons = person.split("\\n");
					console.log(persons);
					const table = $("<table>");
					persons.forEach(e=>{
						const tr = $("<tr>");
						const p = e.split(",");
						p.forEach(d=>{
							tr.append($("<td>").text(d).css("border", "1px solid black"));
						});
						table.append(tr);
					});
					$("#result2").append(table);
				}
			}); --%>
		});
	</script>

	<h2>서버에서 전송하는 csv방식의 데이터 처리하기</h2>
	<p>
		csv : 문자열을 데이터별로 구분할 수 있게 만들어놓은 것!<br>
		-> , \n $ 등으로 구분할 수 있는 문자열<br>
		예) 유병승,19,남,경기도시흥시$최솔,29,여,경기도안양시$
	</p>
	<button id="btncsv">csv데이터 가져오기</button>
	<div id="csvcontainer"></div>
	<script>
		$("#btncsv").click(e=>{
			$.ajax({
				url:"<%=request.getContextPath()%>/ajax/csvdata.do",
				dataType:"text",
				success:data=>{
					// console.log(data);
					const actors = data.split("\n");
					console.log(actors);
					const table = $("<table>");
					const header = "<tr><th>이름</th><th>전화번호</th><th>프로필</th></tr>";
					table.html(header);
					actors.forEach(e=>{
						const tr = $("<tr>");
						const actor = e.split(",");
						const name = $("<td>").text(actor[0]);
						const phone = $("<td>").text(actor[1]);
						const profile = $("<td>").append($("<img>").attr({
							src:"<%=request.getContextPath()%>/images/" + actor[2],
							"width":100,
							"height":100
							}));
						tr.append(name).append(phone).append(profile);
						table.append(tr);
					});
					$("#csvcontainer").html(table);
				}
			});
		});
	</script>




</body>
</html>