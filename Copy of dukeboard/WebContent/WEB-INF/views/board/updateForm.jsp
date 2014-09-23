<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>게시글 수정</title>
	<link rel="stylesheet" href="../css/board.css">
	<script src="../ckeditor/ckeditor.js"></script>
	<script src="../js/board.js"></script>
</head>
<body>
	<div class="boardpage">
		<form name="writeForm" action="update?num=${board.num}&searchType=${param.searchType}&searchText=${param.searchText}&pageNumber=${currentPageNumber}" method="POST">
			<table id="updatetable" class="maintable">
				<caption>게시글 수정</caption>
				<thead>
					<tr>
						<th>제 목</th>
						<td><input class="titleinput" type="text" name="title" maxlength="100" value = "${board.title }"></td>
					</tr>
					<tr>
						<th>글쓴이</th>
						<td><input class="writerinput" type="text" name="writer" maxlength="20" value = "${board.writer }"></td>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td colspan="2">
							<textarea class="contentsinput ckeditor" name="contents"> ${board.contents }</textarea>
						</td>
					</tr>
				</tbody>
			</table>
			<div class="buttonbar">
				<input type="button" value="수정" onclick="boardWriteCheck(this.form)&searchType=${param.searchType}&searchText=${param.searchText}&pageNumber=${currentPageNumber}">
				<input type="button" value="취소" onclick="goUrl('read?num=${board.num}&searchType=${param.searchType}&searchText=${param.searchText}&pageNumber=${currentPageNumber}');">
			</div>
		</form>
	</div>
</body>
</html>
