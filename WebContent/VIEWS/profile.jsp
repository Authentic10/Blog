<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<style type="text/css">

</style>
<meta charset="UTF-8">
<jsp:include page="/VIEWS/layout_head.jsp" />   
<title>Profile</title>
</head>
<body>

<jsp:include page="/VIEWS/layout_nav.jsp" />   
	<div class="msg_info" role="alert">
		
	</div>
<c:if test="${!empty user.username }">
	<div class="container">
		<br>
		<div style="">
			<img class="rounded-circle float-left" alt="" width="150" height="140" src='<c:out value="${user.profile}"/>'><br><br>
			<div class="row">
				<h3 class="ml-4"><c:out value="${user.firstname}"/> <c:out value="${user.lastname}"/> (<c:out value="${user.username}"/>)</h3>
				<c:choose>
				  <c:when test="${user.username == sessionScope.username }">
				    <button type="button" class="btn btn-link nav-link ml-auto">Edit profile <i class="fas fa-user-edit"></i></button>
				  </c:when>
				  <c:otherwise>
				    <c:choose>
					  <c:when test="${relation.follow > 0 }">
					    <button type="button" id="unfollow_button" class="btn btn-link nav-link ml-auto">Unfollow <i class="fas fa-user-minus"></i></button>
					  </c:when>
					  <c:otherwise>
					    <button type="button" id="follow_button" class="btn btn-link nav-link ml-auto">Follow <i class="fas fa-user-plus f_i"></i></button>
					  </c:otherwise>
					</c:choose>
				  </c:otherwise>
				</c:choose>
			</div>
			<div class="row">
				<div class="col-sm-9">
					<p class="ml-2" style="text-align:justify"><c:out value="${user.biography}"/> </p>
				</div>
			</div>
		</div>
		<div class="row float-right">
		<c:choose>
		  <c:when test="${relation.followersSize > 0}">
		    <a href="Followers?username=${user.username}" class="nav-link"><c:out value="${relation.followersSize}"/> Follower(s)</a>
		  </c:when>
		  <c:otherwise>
		    <a href="#" class="nav-link"><c:out value="${relation.followersSize}"/> Follower(s)</a>
		  </c:otherwise>
		</c:choose>
		<c:choose>
		  <c:when test="${relation.followingSize > 0}">
		    <a href="Following?username=${user.username}" class="nav-link"><c:out value="${relation.followingSize}"/> Following</a>
		  </c:when>
		  <c:otherwise>
		    <a href="#" class="nav-link"><c:out value="${relation.followingSize}"/> Following</a>
		  </c:otherwise>
		</c:choose>
		</div>
		<br>
		<hr>
		<h4>Posts</h4>
	</div>
</c:if>


<script type="text/javascript">
	 $("#search_form").hide();
	 $('.msg_info').hide();
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
		    
		    $('#follow_button').click(function(){
		    	 $.ajax({
		    			type: "POST",
		                url: "follow",
		    			data : {
		    				current_user : '<c:out value="${sessionScope.username}"/>',
		    				user : '<c:out value="${user.username}"/>'
		    			},
		    			success : function(responseText) {
		    				if(responseText=="ok"){
		    					$('.f_i').removeClass('fas fa-user-plus');
			    				$('#follow_button').html("Unfollow <i class='fas fa-user-minus'></i>");
			    				$('.f_i').addClass('fas fa-user-minus');
			    				
			    				$('.msg_info').addClass("alert alert-info");
			    				$('.msg_info').text('You just followed <c:out value="${user.username}"/> ');
			    				$('.msg_info').show().delay(5000).fadeOut();
		    				}		
		    	
		    			},
		    			  //If there was no resonse from the server
		                error: function(jqXHR, textStatus, errorThrown){
		                     console.log("Something really bad happened " + textStatus);
		                      //$("#search_result").html(jqXHR.responseText);
		                },
		    		});
		    });
		   

	    });
</script>
</body>
</html>