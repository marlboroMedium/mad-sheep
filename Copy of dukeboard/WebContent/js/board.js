// 해당 url로 이동하는 기능 수행
function goUrl(url) {
	location.href = url;
}

// 삭제 여부를 확인 후 해당 url로 이동하는 기능 수행
function deleteCheck(url){
	if(confirm("정말로 삭제하시겠습니까?")){
		goUrl(url);
	}
}

function searchCheck(form){
	if(form.searchText.value ==''){
		alert('검색어 입력하세요');
		form.searchText.focus();
		return;		
	}
	form.submit;
}

// 작성/수정 폼의 공백을 체크하는 기능
function boardWriteCheck(form){
	
	if(form.title.value.length ==0){
		alert('제목 입력하세요');
		form.title.focus();
		return;
	}
	
	if(form.writer.value.length ==0){
		alert('이름 입력하세요');
		form.writer.focus();
		return;
	}
	
	if(form.contents.value.length ==0){
		alert('내용 입력하세요');
		form.contents.focus();
		return;
	}
	form.submit();
}

// 폼 필드가 비어있는지 여부를 체크하여 에러메시지를 출력
function checkNotEmpty(inputField, errorSpan){
	if(inputField.value.length == 0){
		errorSpan.innerHTML = '이름을 입력하세요';
		}else{
			errorSpan.innerHTML = '';			
		}
}
