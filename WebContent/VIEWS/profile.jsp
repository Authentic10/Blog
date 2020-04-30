<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<jsp:include page="/VIEWS/layout_head.jsp" />   
<title>Profile</title>
</head>
<body>

<jsp:include page="/VIEWS/layout_nav.jsp" />   

<div class="msg_info" role="alert">
	
</div>

<c:if test="${form.queryResult eq 'ok' }"></c:if>
	<script>
		$('.msg_info').addClass("alert alert-success");
		$('.msg_info').text('You profile update is done with success');
		$('.msg_info').show().delay(5000).fadeOut();
	</script>	
<c:if test="${!empty user.username }">
	<div class="container">
		<br>
		<div style="">
			<img class="rounded-circle float-left" alt="" width="150" height="140" src='<c:out value="${user.profile}"/>'><br><br>
			<div class="row">
				<h3 class="ml-4"><c:out value="${user.firstname}"/> <c:out value="${user.lastname}"/> (<c:out value="${user.username}"/>)</h3>
				<c:choose>
				  <c:when test="${user.username == sessionScope.username }">
				    <a type="button" href="UpdateProfile" class="btn btn-link nav-link ml-auto text-primary">Edit profile <i class="fas fa-user-edit"></i></a>
				  </c:when>
				  <c:otherwise>
				    <c:choose>
					  <c:when test="${relation.follow > 0 }">
					    <button type="button" id="unfollow_button" class="btn btn-link nav-link ml-auto">Unfollow <i class="fas fa-user-minus f_i"></i></button>
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
					<c:if test="${ empty user.biography }">
						<p class="ml-2" style="text-align:justify">No biography</p>
					</c:if>
					<p class="ml-2" style="text-align:justify"><c:out value="${user.biography}"/> </p>
				</div>
			</div>
		</div>
		<c:choose>
		  <c:when test="${relation.follow > 0  or user.username eq sessionScope.username }">
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
		<h4>Posts</h4><br>
		<table class="table" style="width:100%;">
		<c:if test="${empty posts }">
			<c:choose>
			  <c:when test="${user.username == sessionScope.username }">
			    <p>You do not have posts yet ! </p>
			  </c:when>
			  <c:otherwise>
			  	<p><c:out value="${user.username}" /> does not have posts yet ! </p>
			  </c:otherwise>
			</c:choose>	
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
								<img class="rounded-circle float-left" alt="" width="45" height="40" src="${user.profile}">
								<p class=""><a class="nav-link text-dark" href="Profile?username=${user.username}"><c:out value="${user.username}" /></a></p>
							 </div><br>
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
								<img class="rounded-circle float-left" alt="" width="45" height="40" src="${user.profile}">
								<p class=""><a class="nav-link text-dark" href="Profile?username=${user.username}"><c:out value="${user.username}" /></a></p>
							 </div>
				              <h4 class="mb-0">
				                <c:out value="${post.title} " />
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
		   </table>
		  </c:when>
		  <c:otherwise><br><br>
		    You have to follow <c:out value="${user.username}"/> to see information about him.
		  </c:otherwise>
		</c:choose>
		
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
		    		 $("#search_result").hide();
		    		 $(".container").show();
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
		    
		    $('#unfollow_button').click(function(){
		    	 $.ajax({
		    			type: "POST",
		                url: "unfollow",
		    			data : {
		    				current_user : '<c:out value="${sessionScope.username}"/>',
		    				user : '<c:out value="${user.username}"/>'
		    			},
		    			success : function(responseText) {
		    				if(responseText=="ok"){
		    					$('.f_i').removeClass('fas fa-user-minus');
			    				$('#unfollow_button').html("Follow <i class='fas fa-user-plus'></i>");
			    				$('.f_i').addClass('fas fa-user-plus');
			    				
			    				$('.msg_info').addClass("alert alert-info");
			    				$('.msg_info').text('You just stop following <c:out value="${user.username}"/> ');
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