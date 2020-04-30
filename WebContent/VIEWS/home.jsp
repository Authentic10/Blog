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
<c:if test="${post.queryResult == 'success' }">
		<script>
			$(document).ready(function () {
				$('.msg_info').addClass("alert alert-success");
				$('.msg_info').text(" Your post id added ! ");
				$('.msg_info').show().delay(5000).fadeOut();
			});
		</script>
</c:if>   
 
<jsp:include page="/VIEWS/layout_nav.jsp" />   

<c:if test="${!empty sessionScope.username }">
	<div class="msg_info" role="alert">
		
	</div>
</c:if>
<div class="container">
<br><br>
<c:if test="${empty posts }">
	<p>You do not have posts yet ! </p>
</c:if>
<c:forEach var="post" items="${posts}" varStatus="i">
   <c:choose>
       <c:when test="${!empty post.picture }">
        <div class="row mb-2">
        <div class="col-md-2">
          
        </div>
        <div class="col-md-10" >
          <div class="card flex-md-row mb-4 shadow-sm h-md-250">
            <div class="card-body d-flex flex-column align-items-start">
            <div class="row">
				<img class="rounded-circle float-left" alt="" width="45" height="40" src="PICTURES/${post.avatar}">
				<p><a class="nav-link text-dark" href="Profile?username=${post.username}"><c:out value="${post.username}" /></a></p>
			</div>
              <h4 class="mb-0">
                <c:out value="${post.title}" />
              </h4>
              <div class="mb-3 text-muted"><c:out value="${post.timestamp}" /></div>
              <c:set var="text"  value="${fn:substring(post.text, 0, 90)} ..." />
              <p class="card-text mb-auto" style="height:2em; text-align:justify;"><c:out value="${text }"/></p>
              <a href="ShowPost?id=${post.id}">Continue reading</a>
            </div>
            <img class="card-img-right flex-auto d-none d-lg-block" src="PICTURES/${post.picture}" width="250px" alt="Post image">
          </div>
        </div>
      </div>
      
	  </c:when>
	  <c:otherwise>
	  <div class="row mb-2">
        <div class="col-md-2">
          
        </div>
        <div class="col-md-10" >
          <div class="card flex-md-row mb-4 shadow-sm h-md-250">
            <div class="card-body d-flex flex-column align-items-start">
              <div class="row">
				<img class="rounded-circle float-left" alt="" width="45" height="40" src="PICTURES/${post.avatar}">
				<p ><a class="nav-link text-dark" href="Profile?username=${post.username}"><c:out value="${post.username}" /></a></p>
			  </div>
              <h4 class="mb-0">
                <c:out value="${post.title}" />
              </h4>
              <div class="mb-3 text-muted"><c:out value="${post.timestamp}" /></div>
              <c:set var="text"  value="${fn:substring(post.text, 0, 120)} ..." />
              <p class="card-text mb-auto" style="height:2em; text-align:justify;"><c:out value="${text }"/></p>
              <a href="ShowPost?id=${post.id}">Continue reading</a>
            </div>
          </div>
        </div>
      </div>
	  </c:otherwise>
	</c:choose>	
  </c:forEach> 
</div>

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
		    		 $("#search_result").hide();
		    		 $(".container").show();
		    	 }        
		    });

	    });
</script>
</body>
</html>