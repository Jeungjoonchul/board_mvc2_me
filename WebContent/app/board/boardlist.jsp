<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MVC 게시판</title>
<style>
body {
	background-color: #fff;
}

table {
	border: 0px;
	width: 900px;
}

.title a{
	background-color: white;
}

.title h3 {
	font-size: 1.5em;
	color: rgb(0, 200, 80);
	text-shadow: 0 0 4px deepskyblue;
}

.list {
	border-collapse: collapse;
	border-spacing: 0;
}

.list>tbody>tr:nth-child(2n) {
	background-color: rgb(240, 255, 240);
}

.list>tbody>tr:nth-child(n+2):hover {
	background-color: rgb(230, 233, 252);
}

.list>tbody>tr>th {
	border-top: 1px solid #384d75;
	border-bottom: 1px solid #ccc;
	padding: 5px;
}

.list>tbody>tr>td {
	border-bottom: 1px solid #ccc;
	padding: 5px;
	text-align: center;
}

a {
	display: inline-block;
	border-radius: 3px;
	background-color: rgb(0, 200, 80);
	color: #fff;
	font-weight: bold;
	text-decoration: none;
}

.list a {
	background-color: transparent;
	color: #424242;
}

.header_area a {
	width: 100px;
	padding: 10px;
	text-align: center;
}

.header_area span {
	width: 100px;
	padding: 10px;
	text-align: center;
	font-weight: bold;
}

.pagination a {
	padding: 5px;
	width: 20px;
	height: 20px;
}

.pagination a:hover {
	background-color: rgb(198, 175, 245);
}

.nowPage {
	padding: 5px;
	width: 20px;
	height: 20px;
	display: inline-block;
	border-radius: 3px;
	background-color: rgb(198, 175, 245);
	color: #fff;
	font-weight: bold;
}

a.write {
	padding: 5px;
	width: 70px;
	height: 25px;
	text-align: center;
}

.search_area {
	text-align: center;
	display: flex;
	justify-content: center;
}

.search_area #q {
	outline: none;
	border: 2px solid rgb(0, 200, 80);
	border-radius: 3px;
	width: 300px;
	padding: 10px;
	margin-right: 5px;
}

.search_area [type=button] {
	background-color: rgb(0, 200, 80);
	width: 70px;
	padding: 10px;
	display: inline-block;
	border: none;
	border-radius: 3px;
	color: #fff;
	font-weight: bold;
}
</style>
</head>
<body>

<%
	int cookieCnt= 0;
	Cookie[] cookies = request.getCookies();
	for(Cookie cookie:cookies){
		System.out.println(cookie.getName()+":"+cookie.getValue());
		cookieCnt++;
	}
	System.out.println(cookieCnt);
%>
	<c:set var="cp" value="${pageContext.request.contextPath}"></c:set>
	<c:if test="${loginUser == ''}">
		<script>
			alert("로그인 후 이용하세요!");
			location.replace("${cp}/user/userlogin.us");
		</script>
	</c:if>
	<c:if test="${writeResult.writeResult.value=='fail'}">
		<script>
			alert("게시글 작성 실패!");
			//자바 스크립트로 쿠키삭제 하기			
		</script>
	</c:if>
	<div style="margin: 0 auto; width: 1000px;">
		<!-- 로그아웃 버튼 배치할 테이블 -->
		<table class="header_area">
			<tr align="right" valign="middle">
				<td><span>${loginUser}님 환영합니다.</span>&nbsp;&nbsp; <a href="${cp}/user/userlogout.us">로그아웃</a></td>
			</tr>
		</table>

		<!-- 타이틀과 글 개수 띄워주는 테이블 -->
		<table class="title">
			<tr align="center" valign="middle">
				<td><a href="${cp }/board/boardlist.bo"><h3>MVC 게시판</h3></a></td>
			</tr>
			<tr align="right" valign="middle">
				<td>글 개수 :${totalCnt}</td>
			</tr>
		</table>

		<!-- 게시글 리스트 띄우는 테이블 -->
		<table class="list">
			<tr align="center" valign="middle">
				<th width="8%">번호</th>
				<th width="50%">제목</th>
				<th width="15%">작성자</th>
				<th width="17%">날짜</th>
				<th width="10%">조회수</th>
			</tr>
			<c:choose>
				<c:when test="${list != null and list.size()>0 }">
					<c:forEach items="${list }" var="board">
						<tr>
							<td>${board.boardnum}</td>
							<td><a href="${cp}/board/boardview.bo?boardnum=${board.boardnum}&page=${page}&keyword=${keyword}">${board.boardtitle}</a></td>
							<td>${board.userid}</td>
							<td>${board.regdate}<c:if test="${board.regdate!=board.updatedate}">(수정됨)</c:if></td>
							<td>${board.readcount}</td>
						</tr>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<tr>
						<td colspan="5" style="text-align: center; font-size: 20px;">등록된 게시글이 없습니다.</td>
					</tr>
				</c:otherwise>
			</c:choose>
		</table>
		<br>

		<!-- 페이징 처리하는 테이블 -->
		<table class="pagination">
			<tr align="center" valign="middle">
				<td><c:if test="${startPage!=1 }">
						<a href="${cp}/board/boardlist.bo?page=${startPage-1}&keyword=${keyword}">&lt;</a>
					</c:if> <c:forEach begin="${startPage}" end="${endPage }" var="i">
						<c:choose>
							<c:when test="${page==i}">
								<span class="nowPage">${i}</span>
							</c:when>
							<c:otherwise>
								<a href="${cp}/board/boardlist.bo?page=${i}&keyword=${keyword}">${i}</a>
							</c:otherwise>
						</c:choose>
					</c:forEach> <c:if test="${endPage!=totalPage}">
						<a href="${cp}/board/boardlist.bo?page=${endPage+1}&keyword=${keyword}">&gt;</a>
					</c:if></td>
			</tr>
		</table>
		<!-- 글쓰기 버튼 배치하는 테이블 -->
		<table style="border: 0px; width: 900px;">
			<tr align="right" valign="middle">
				<td><a class="write" href="${cp }/board/boardwrite.bo?page=${page}&keyword=${keyword}">글쓰기</a></td>
			</tr>
		</table>
		<div class="search_area">
			<input type="search" id="q" name="q" value="${keyword==null ? '' : keyword}"> <input type="button"
				value="검색" onclick="search()" style="cursor: pointer">
		</div>
	</div>
</body>
<script>
	function search(){
		const q = document.getElementById("q");
		location.href="${cp}/board/boardlist.bo?keyword="+q.value;
	}
</script>
</html>