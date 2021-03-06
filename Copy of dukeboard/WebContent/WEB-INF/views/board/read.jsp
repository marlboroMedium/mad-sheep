<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, casestudy.business.domain.Board"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>게시글 보기</title>
	<link rel="stylesheet" href="../css/board.css">
	<script src="../js/board.js"></script>
</head>
<body>
	<div class="boardpage">
		<table id="readtable" class="maintable">
			<caption>게시글 보기</caption>
			<thead>
				<tr>
					<th>제 목</th>
					<td class="title" colspan="5">${board.title}</td>
				</tr>
				<tr>
					<th>글쓴이</th>
					<td class="writer">${board.writer}</td>
					<th>조회</th>
					<td class="readcount">${board.readCount}</td>
					<th>작성일</th>
					<td class="regdate">${board.regDate}</td>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td class="contents" colspan="6">${board.contents}</td>
				</tr>
			</tbody>
		</table>
		<div class="buttonbar">
			<!-- <input type="button" value="목록" onclick="goUrl('board?action=list');"> -->
			<input type="button" value="목록" onclick="goUrl('list?searchType=${param.searchType}&searchText=${param.searchText}&pageNumber=${currentPageNumber}');">
			<input type="button" value="수정" onclick="goUrl('updateForm?num=${board.num}&searchType=${param.searchType}&searchText=${param.searchText}&pageNumber=${currentPageNumber}');">
			<input type="button" value="삭제" onclick="deleteCheck('remove?num=${board.num}&searchType=${param.searchType}&searchText=${param.searchText}&pageNumber=${currentPageNumber}');">
		</div>
		</div>
</body>
</html>
