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

#wrap {
	width: 1000px;
	margin: 0 auto;
}

table {
	border: 0px;
	width: 900px;
}

.title h3 {
	font-size: 1.5em;
	color: rgb(0, 200, 80);
	text-shadow: 0 0 4px deepskyblue;
}

a {
	display: inline-block;
	border-radius: 3px;
	background-color: rgb(0, 200, 80);
	color: #fff;
	font-weight: bold;
	text-decoration: none;
}

.header_area a {
	width: 100px;
	padding: 10px;
	text-align: center;
}

.header_area span {
	font-weight: bold;
}

.btn_area a {
	text-align: center;
	padding: 10px;
	width: 100px;
}
/* img.thumbnail{
	display: block;
	clear:both;
	width: 100px;
}
#boardForm tr:nth-child(n+4){
	height: 50px;
}
#boardForm a{
	padding: 5px 10px;
} 
input[type="text"], textarea{
	border:none;
	padding: 5px;
	outline: none;
}*/
.board_area {
	border-collapse: collapse;
}

.board_area a {
	background-color: transparent;
	color: rgb(0, 200, 80);
	margin-left: 20px;
}

.board_area input, .board_area textarea {
	border: none;
	outline: none;
}

.reply_line {
	width: 900px;
	margin-top: 20px;
	border-top: 1px solid grey;
	padding-top: 30px;
}

.write_box {
	padding-bottom: 40px;
}

.write_box>tbody>tr>td+td {
	position: relative;
}

.write_box textarea, .update_box textarea {
	padding: 5px 10px;
	height: 70px;
	resize: none;
	width: 600px;
	outline: none;
	border: 1px solid rgba(0, 200, 80.0 .5);
	border-radius: 5px;
}

.update_box textarea {
	width: 500px;
	height: 80px;
}

.write_box a {
	display: inline-block;
	text-align: center;
	padding: 10px;
	width: 90px;
	position: absolute;
	top: 20px;
	right: 0px;
}

.update_box {
	border-top: 1px solid gray;
	padding-top: 30px;
}

.update-box td:last-child {
	vertical-align: top;
}

.update_box .btns a {
	display: inline-block;
	width: 70px;
	height: 30px;
	text-align: center;
	vertical-align: middle;
	line-height: 30px;
	margin-top: 20px;
}
.reply_line textarea{
	border-color: rgb(0,200,80);
}
</style>
</head>
<body>
	<c:set var="cp" value="${pageContext.request.contextPath }"/>
	<c:if test="${loginUser == null }">
		<script>
			let cp = "${pageContext.request.contextPath}";
			alert("로그인 후 이용하세요!");
			location.replace(cp + "/user/userlogin.us");
		</script>
	</c:if>
	<div id="wrap">
		<table class="header_area">
			<tr align="right" valign="middle">
				<td><span>${loginUser}님 환영합니다.</span>&nbsp;&nbsp; <a
					href="${cp}/user/userlogout.us">로그아웃</a></td>
			</tr>
		</table>
		<table class="title">
			<tr align="center" valign="middle">
				<td><h3>MVC 게시판</h3></td>
			</tr>
		</table>
		<form>
			<%-- <c:set var="board" value="${board}"/> --%>
			<table border="1" style="border-collapse: collapse;"
				class="board_area">
				<tr height="30px">
					<th align="center" width="150px">제목</th>
					<td><input type="text" name="boardtitle" size="50"
						maxlength="50" value="${board.boardtitle}" readonly></td>
				</tr>
				<tr height="30px">
					<th align="center" width="150px">작성자</th>
					<td><input type="text" name="userid" size="50" maxlength="50"
						value="${board.userid}" readonly></td>
				</tr>
				<tr>
					<th>작성일</th>
					<td>${board.regdate}<c:if test="${board.regdate != board.updatedate}">(수정됨 : <span>${board.updatedate}</span>)</c:if></td>
				</tr>
				<tr>
					<th>조회수</th>
					<td>${board.readcount}</td>
				</tr>
				<tr height="300px">
					<th align="center" width="150px">내 용</th>
					<td><textarea name="boardcontents"
							style="width: 700px; height: 290px; resize: none" readonly>${board.boardcontents}</textarea>
					</td>
				</tr>

				<c:choose>
					<c:when test="${files != null and files.size()>0}">
						<c:forEach var="i" begin="0" end="${files.size()-1}">
							<c:set var="file" value="${files[i]}" />
							<tr>
								<th>첨부파일${i+1}</th>
								<td><a
									href="${cp}/board/filedownload.bo?systemname=${file.systemname}&orgname=${file.orgname}">${file.orgname}</a></td>
							</tr>
							<c:forTokens items="${file.orgname}" delims="." var="token"
								varStatus="status">
								<c:if test="${status.last}">
									<c:if
										test="${token eq 'jpg' or token eq 'jpeg' or token eq 'png' or token eq 'gif' or token eq 'webp' }">
										<tr>
											<td></td>
											<td><img style="width: 100px;"
												src="${cp}/file/${file.systemname}" /></td>
										</tr>
									</c:if>
								</c:if>
							</c:forTokens>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr>
							<td colspan="2" style="text-align: center; font-size: 20px">첨부
								파일이 없습니다.</td>
						</tr>
					</c:otherwise>
				</c:choose>
			</table>
			<br>
		</form>
		<table class="btn_area">
			<tr align="right" valign="middle">
				<td><c:if test="${board.userid==loginUser}">
						<a href="${cp }/board/boardmodify.bo?boardnum=${board.boardnum}&page=${param.page}&keyword=${param.keyword}">수정</a>&nbsp;&nbsp;
						<a href="${cp }/board/boardremove.bo?boardnum=${board.boardnum}">삭제</a>&nbsp;&nbsp;
					</c:if> 
					<a href="${cp }/board/boardlist.bo?page=${param.page}&keyword=${param.keyword}">목록</a></td>
			</tr>
		</table>
		<div class="reply_line">
			<form name="replyForm" method="post" action="${cp}/reply/replywrite.bo">
				<input type="hidden" name="boardnum" value="${board.boardnum }" />
				<input type="hidden" name="page" value="${param.page}" /> 
				<input type="hidden" name="keyword" value="${param.keyword}" />

				<table class="write_box">
					<tr>
						<td>댓글</td>
						<td><textarea name="replycontents"></textarea> 
						<a href="javascript:document.replyForm.submit()">등록</a></td>
					</tr>
				</table>
			</form>
			
			<form name="updateForm" method="post">
				<input type="hidden" name="boardnum" value="${board.boardnum }" />
				<input type="hidden" name="page" value="${param.page }" /> 
				<input type="hidden" name="keyword" value="${param.keyword}" />
				<table class="update_box">
				<c:set var="i" value="0"/>
					<c:forEach var="reply" items="${replies}">
						<tr>
							<td>${reply.userid}</td>
							<td><textarea readonly name="reply${i}" id="reply${i}">${reply.replycontents}</textarea></td>
							<td>
								${reply.regdate} 
								<c:if test="${reply.updatechk=='o'}">
									<br>(수정됨)
								</c:if> 
								<c:if test="${reply.userid==loginUser}">
									<div class="btns">
										<a href="javascript:updateReply(${i})" id="start${i}">수정</a> 
										<a href="javascript:updateOk(${i},${reply.replynum})" style="display: none;" id="end${i}">수정완료</a> 
										<a href="javascript:deleteReply(${reply.replynum})">삭제</a>
									</div>
								</c:if>
							</td>
						</tr>
						<c:set var="i" value="${i+1}"/>
					</c:forEach>
				</table>
			</form>
		</div>
	</div>
</body>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<!-- jQuery에 주요 업데이트가 있을 경우 콘솔에 경고 표시, 해결할 수 있는 문제들은 스스로 해결 -->
<script src="https://code.jquery.com/jquery-migrate-1.2.1.js"></script>
<script>
	let flag = true;
	const updateForm=document.updateForm;
	function updateReply(i) {
		if(flag){
		const start = document.getElementById("start"+i);
		const end = document.getElementById("end"+i);
		const reply = document.getElementById("reply"+i);
		
		start.style.display="none";
		end.style.display="inline-block";
		reply.removeAttribute("readonly");
		flag=false;			
		}else{
			alert("수정 중인 댓글이 있습니다!")
		}
	}
	function updateOk(i,replynum){
		updateForm.setAttribute("action","${cp}/reply/replymodify.bo?replynum="+replynum+"&i="+i);
		updateForm.submit();
	}
	function deleteReply(replynum){
		updateForm.setAttribute("action","${cp}/reply/replyremove.bo?replynum="+replynum);
		updateForm.submit();
	}
</script>
</html>