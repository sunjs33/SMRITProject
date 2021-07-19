<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>자료실</title>
</head>
<body>
<table width=50% border="0" cellpadding="0" cellspacing="0">
<c:if test="${!empty list }">

	<tr align="center" valign="middle">
		<td colspan="4">자료실</td>
		<td align=right>
			<font size=2>글 개수 : ${count}</font>
		</td>
	</tr>
	
	<tr align="center" valign="middle" bordercolor="#333333">
		<td >	번호
		</td>
		<td >
			제목
		</td>
		<td>
			작성자
		</td>
		<td>
			날짜
		</td>
		<td >
			조회수
		</td>
	</tr>
	
	
	
	<c:forEach items="${list}" var="dto" varStatus="cnt">
	<tr align="center" valign="middle" >
		<td height="23">${cnt.count}</td>
		<td><a href="libBoardDetail.lib?num=${dto.boardNum }">${dto.boardSubject }</a></td>
		<td >${dto.boardName}</td>
		<td>${dto.boardDate }</td>	
		<td>${dto.readCount }</td>
	</tr>
	</c:forEach>

	<tr align=center height=20>
		<td colspan=7 style=font-family:Tahoma;font-size:10pt;>

		<%@include file="/include/includePage.jsp" %>

		</td>
	</tr>
</c:if>
<c:if test="${empty list }">

	<tr align="center" valign="middle">
		<td colspan="4">자료 게시판</td>
		<td align=right>
			<font size=2>등록된 글이 없습니다.</font>
		</td>
	</tr>
</c:if>
	<tr align="right">
		<td colspan="5">
	   		<a href="libWrite.lib">[글쓰기]</a>
		</td>
	</tr>
	
</table>
</body>
</html>