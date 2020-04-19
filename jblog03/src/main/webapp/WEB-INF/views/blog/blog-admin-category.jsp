<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JBlog</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/jblog.css">
<link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script type="text/javascript" src="${pageContext.request.contextPath }/assets/js/jquery/jquery-3.4.1.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/assets/js/ejs/ejs.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script>
var id = '${authUser.id}';

var listItemTemplate = new EJS({
	url: "${pageContext.request.contextPath }/assets/js/ejs/list-item-template.ejs"
});

var listTemplate = new EJS({
	url: "${pageContext.request.contextPath }/assets/js/ejs/list-template.ejs"
});

// 목록 불러오기
var fetchList = function(){
	$.ajax({
		url: '${pageContext.request.contextPath }/api/category/list/' + id,
		async: true,
		type: 'get',
		dataType: 'json',
		data: '',
		success: function(response){
			if(response.result != "success"){
				console.error(response.message);
				return;
			}
			
			response.pageContext = '${pageContext.request.contextPath }/assets/images/delete.jpg';
			
			
			var html = listTemplate.render(response);
			$(".admin-cat").append(html);
		},
		error: function(xhr, status, e){
			console.error(status + ":" + e);
		}
	});	
}

var messageBox = function(title, message, callback){
	$("#dialog-message p").text(message);
	$("#dialog-message")
		.attr("title", title)
		.dialog({
			modal: true,
			buttons: {
				"확인": function() {
					$(this).dialog( "close" );
		        }
			},
			close: callback
		});
}

// ------------------------------------------------------------------------------------------
$(function(){
	// ------------------- 추가 ----------------------------
	$('#admin-cat-add').submit(function(event){
		event.preventDefault();
		var vo = {};
		vo.name = $("#category-name").val();
		if(vo.name == ''){
			messageBox("카테고리 추가", "카테고리명은 필수 항목 입니다.", function(){
				$("#category-name").focus();
			});
			return;	
		}
		vo.description = $("#category-description").val();

		$.ajax({
			url: '${pageContext.request.contextPath }/api/category/add/' + id,
			async: true,
			type: 'post',
			dataType: 'json',
			contentType: 'application/json',
			data: JSON.stringify(vo),
			success: function(response){
				if(response.result != "success"){
					console.error(response.message);
					return;
				}
				
				response.data.pageContext = '${pageContext.request.contextPath }/assets/images/delete.jpg';
				
				// rendering
				var html = listItemTemplate.render(response.data);
				$(".admin-cat").append(html);
				
				$("#admin-cat-add")[0].reset();
				
			},
			error: function(xhr, status, e){
				console.error(status + ":" + e);
			}
		});
	});

	
	// ------------------- 삭제 ----------------------------
	var dialogDelete = $("#dialog-delete-form").dialog({
		autoOpen: false,
		width: 300,
		height: 150,
		modal: true,
		buttons: {
			"삭제": function(){
				var no = $("#hidden-no").val();
				
				$.ajax({
					url: '${pageContext.request.contextPath }/api/category/delete/' + no,
					async: true,
					type: 'delete',
					dataType: 'json',
					data: '',
					success: function(response){
						if(response.result != "success"){
							console.error(response.message);
							return;
						}
						
						if(response.data != -1){
							$(".admin-cat tr[data-no=" + no + "]").remove();
							dialogDelete.dialog('close');
							return;
						}
						
					},
					error: function(xhr, status, e){
						console.error(status + ":" + e);
					}
				});
			},
			"취소": function(){
				$(this).dialog('close');
			}
		},
		close: function(){
			$("#hidden-no").val("");
		}
	});
	
	// -------------------------- x버튼 클릭 했을때 -------------------------------
	$(document).on('click', '.admin-cat tr td a', function(event){
		event.preventDefault();
		
		var no = $(this).data('no');
		$("#hidden-no").val(no);		
		dialogDelete.dialog("open");
	});
	
	fetchList();


});
</script>
</head>
<body>
	<div id="container">
		<div id="header">
			<h1>${blog.title }</h1>
			<c:import url="/WEB-INF/views/includes/blog-header.jsp" />
		</div>
		<div id="wrapper">
			<div id="content" class="full-screen">
				<ul class="admin-menu">
					<li><a
						href="${pageContext.request.contextPath}/${authUser.id}/admin/basic">기본설정</a></li>
					<li class="selected">카테고리</li>
					<li><a
						href="${pageContext.request.contextPath}/${authUser.id}/admin/write">글작성</a></li>
				</ul>
				<table class="admin-cat">
					<tr>
						<th>번호</th>
						<th>카테고리명</th>
						<th>포스트 수</th>
						<th>설명</th>
						<th>삭제</th>
					</tr>

				</table>

				<h4 class="n-c">새로운 카테고리 추가</h4>
				<form id="admin-cat-add" action="" method="post">
					<table>
						<tr>
							<td>카테고리명</td>
							<td><input id="category-name" type="text" name="name"></td>
						</tr>
						<tr>
							<td>설명</td>
							<td><input id="category-description" type="text" name="description"></td>
						</tr>
						<tr>
							<td class="s">&nbsp;</td>
							<td><input type="submit" value="카테고리 추가"></td>
						</tr>
					</table>
				</form>
			</div>
			<div id="dialog-delete-form" class="delete-form" title="카테고리 삭제" style="display: none">
				<p>삭제하시겠습니까?</p>
				<form>
					<input type="hidden" id="hidden-no" value="">
  				</form>
			</div>
			<div id="dialog-message" title="" style="display:none">
  				<p></p>
			</div>
		</div>
		<div id="footer">
			<p>
				<strong>Spring 이야기</strong> is powered by JBlog (c)2016
			</p>
		</div>
	</div>
</body>
</html>