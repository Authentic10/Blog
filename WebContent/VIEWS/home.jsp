<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<jsp:include page="/VIEWS/layout_head.jsp" />   
<title>Home</title>
</head>
<body>
<c:if test="${form.queryResult == 'ok' }">
		<script>
			$(document).ready(function () {
				$('.msg_info').addClass("alert alert-info");
				$('.msg_info').text(" Welcome ! ");
				$('.msg_info').show().delay(5000).fadeOut();
			});
		</script>
</c:if>   
 
<jsp:include page="/VIEWS/layout_nav.jsp" />   

<c:if test="${!empty sessionScope.username }">
	<div class="msg_info" role="alert">
		
	</div>
</c:if>

<script type="text/javascript">
	 $("#search_form").hide();
	 $(document).ready(function () {		 
		    $("#search_icon").click(function () {
		    	 $("#search_form").toggle('slow');
		    	 var search = $("#search_icon").attr("class");
		    	 if(search=='fas fa-search'){
		    		 $("#search_icon").removeClass("fas fa-search");
		    		 $("#search_icon").addClass("fas fa-times"); 
		    		 $("#search_input").focus();
		    	 } else{
		    		 $("#search_icon").removeClass("fas fa-times");
		    		 $("#search_icon").addClass("fas fa-search");
		    	 }        
		    });

	    });
</script>
</body>
</html>