<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ajax파일 업로드시키기</title>
<script src="http://code.jquery.com/jquery-3.7.0.min.js"></script>
</head>
<body>
	<h2>ajax를 이용해서 파일 업로드하기</h2>
	<input type="file" id="upFile" multiple>
	<button id="uploadBtn">업로드파일</button>
	<script>
		$("#uploadBtn").click(e=>{
			// js가 제공하는 FormData클래스 이용
			// -> form태그 만든 것과 동일하다고 보면 됨(key:value형식의 값을 넣을 수 있음)
			const form = new FormData();
			// append로 서버에 전송할 데이터를 넣을 수 있음
			const fileInput = $("#upFile");
			console.log(fileInput);
			// input[type="file"] -> files라는 속성이 있음(FileList)
			$.each(fileInput[0].files, (i, f)=>{
				// jquery는 배열!!
				// i는 인덱스번호
				form.append("upFile" + i, f);
			});
			form.append("name", "유병승");		
			$.ajax({
				url:"<%=request.getContextPath()%>/fileUpload.do",
				data:form,
				// file은 multipartform으로 넘어가야됨
				// -> type:"post" / processData, contentType:false
				type:"post",
				processData:false,
				contentType:false,
				success:data=>{
					// servlet 갔다와서 success로 도착
					alert("업로드가 완료되었습니다.");
				},
				error: (r, m)=>{
					alert("업로드 실패 :(" + m);
				},
				complete:()=>{
					// 성공, 실패 상관없이 인풋에 등록된 파일 삭제해줘야 함
					$("#upFile").val('');
				}
			});
		});
	</script>
	
	<h2>업로드 이미지 미리보기 기능</h2>
	<img src="https://img.freepik.com/free-icon/user_318-804790.jpg"
	width="100" height="100" id="profile">
	<input type="file" style="display:none" id="profileInput" accept="image/*">
	
	<input type="file" id="upfiles" multiple accept="images/*">
	<div id="uploadpreview"></div>
	
	
	<script>
		$("#upfiles").change(e=>{
			$("#uploadpreview").html('');
			// 변경누를 때마다 초기화
			const files = e.target.files;
			$.each(files, (i, f)=>{
				// f에 .png, .jpg 처럼 마지막 글자가 확장자가 되면~ 으로 분기처리 가능
				const reader = new FileReader();
				reader.onload=e=>{
					const img = $("<img>").attr({
						src:e.target.result,
						"width":"100",
						"heigth":"100"
					})
					$("#uploadpreview").append(img);
				}
				reader.readAsDataURL(f);
				// f는 파일명이 오는데 그 값 자체가 result값이 됨
			})
		});
	
		$("#profile").click(e=>{
			$("#profileInput").click();
		});
		
		// value 변경 시 change이벤트 발생
		$("#profileInput").change(e=>{
			// change했을 때 accept="image/*" 분기처리 해줘야 함
			// 아니면 모든 파일 눌러서 올릴 수 있음
			const reader = new FileReader();
			reader.onload = e =>{
				// 파일을 정상적으로 읽으면 실행됨
				// e.target.result속성에 변경된 file이 나옴
				$("#profile").attr("src", e.target.result);
			}
			reader.readAsDataURL(e.target.files[0]);
		});
		
	</script>
	<style>
		#profile{
			border-radius:100px;
			border:2px solid lightgray;
		}
	</style>
	
	
	
</body>
</html>