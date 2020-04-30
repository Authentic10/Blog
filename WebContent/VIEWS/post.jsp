<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<jsp:include page="/VIEWS/layout_head.jsp" />   
<title>Post information</title>
</head>
<body>

<jsp:include page="/VIEWS/layout_nav.jsp" />

<div class="container">
<br><br>
<div class="row">
	<img class="rounded-circle float-left" alt="" width="63" height="55" src="PICTURES/${post.avatar}">
	<p class="my-2"><a class="nav-link text-dark" href="Profile?username=${post.username}"><c:out value="${post.username}" /></a></p>
</div>

<br>
<h3><c:out value="${post.title }"/></h3>
<div class="mb-3 text-muted"><c:out value="${post.timestamp}" /></div>
<br>
<c:if test="${!empty post.picture }">
	<img src="PICTURES/${post.picture}" class="img-fluid my-4" id="profile" alt="post picture"/>
</c:if>


<p style="text-align:justify;"><c:out value="${post.text }"/></p>


<br>
<br>
<c:if test="${ comment.queryResult eq 'ok' }">
	<script>
		$('.msg_error').addClass("alert alert-success");
		$('.msg_error').text("Your comment is added ! ");
		$('.msg_error').show().delay(5000).fadeOut();
	</script>
</c:if>
<div class="msg_error" role="alert">
 		Message d'erreur
</div>
<form action="commentServlet?id=${post.id}" method="post" id="comment_form">
  <div class="form-group">
  	<label for="comment">Add a comment</label>
    <textarea class="form-control" id="comment" name="comment" rows="2" placeholder="Your comment"><c:out value="${comment.comment }"/></textarea>
  </div>
  <div class="my-4 text-right">
  	<button type="button" class="btn btn-primary" id="sub_button">Submit</button>
  </div> 
</form>


<h4>Comments</h4><br>
<c:if test="${empty comments}">
	<p>This post does not have comments
</c:if>
 <c:forEach var="comment" items="${comments }" varStatus="i">
 <div class="ml-3 mb-4">
 	<div class="row">
		<img class="rounded-circle float-left" alt="" width="45" height="40" src="PICTURES/${comment.avatar}">
		<p class=""><a class="nav-link text-dark" href="Profile?username=${comment.username}"><c:out value="${comment.username}" /></a></p>
	</div>

	<p class="mb-2 text-muted ml-4" style="font-size:12px;"><c:out value="${comment.timestamp}" /></p>
	
	<p class="ml-4" style="text-align:justify;"><c:out value="${comment.comment }"/></p>
 </div>
 	
 </c:forEach>
	
</div>

<script type="text/javascript">
	 $("#search_form").hide();
	 $('.msg_error').hide();
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
		    		 $("#search_result").hide();
		    		 $(".container").show();
		    	 }        
		    });
		    
		    $('#sub_button').click(function () {
		        var comment = $('#comment').val();
		        
		        if(comment.trim().length==0){
		        	$('.msg_error').addClass("alert alert-danger");
		        	$('.msg_error').text(" The field is empty, please retry ! ");
		        	$('.msg_error').show();
		        }
		        else{
		        	$('.msg_error').hide();
		        	$('#comment_form').submit();
		        }		        
		    });

	    });
</script>
</body>
</html>