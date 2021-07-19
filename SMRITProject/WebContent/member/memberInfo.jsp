<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
       <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
       <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript" 
	src="http://code.jquery.com/jquery-latest.js" ></script>
<script type="text/javascript">
$(function(){
	$("#modify").click(function(){
		location.href="memberInfoPw.mem?userId=${memberDTO.userId}"
	});
	$("#memDel").click(function(){
		if(confirm("정말 진짜로 사실 리얼 탈퇴?")){
			location.href="memberDel.mem?userId=${memberDTO.userId}"
		}
	});
});
</script>
</head>
<body>

이름 : ${memberDTO.userName }<br />
아이디 : ${memberDTO.userId }<br />
이메일  : ${memberDTO.userEmail }<br />
생년월일  : ${memberDTO.userBirth }<br />
성별 : <c:if test="${memberDTO.userGender == 'M' }">남자</c:if>
	  <c:if test="${memberDTO.userGender == 'F' }">여자</c:if> 
<br />
연락처 1 : ${memberDTO.userPh1 }<br />
연락처 2 : ${memberDTO.userPh2 }<br />
등록일 : ${memberDTO.userRegist }<br />
주소  : ${memberDTO.userAddr }<br />
<input type="button" name="modify" id ="modify" value="수   정" >
<input type="button" value="취  소" 
				onclick = "javascript:history.back();" />
<input type="button" value="탈퇴" id ="memDel"/>
</body>
</html>