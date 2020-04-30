<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<jsp:include page="/VIEWS/layout_head.jsp" />   
<title>Followers</title>
</head>
<body>

<jsp:include page="/VIEWS/layout_nav.jsp" />   

	<div class="container"><br><br>
	  <ul class="nav nav-tabs ">
		  <li class="nav-item">
		    <a class="nav-link active" href="Followers?username=${username }">Followers</a>
		  </li>
		  <li class="nav-item">
		    <a class="nav-link" href="Following?username=${username }">Following</a>
		  </li>
		</ul>
	  <br>
	  <c:if test="${empty followers }">
	  		<h5><c:out value="${username }"/> does not have followers !</h5>
	  </c:if>
	  <table class="table table-hover">
	   <c:forEach var="follow" items="${followers}" varStatus="i">
		    <tr>	      
		      <c:choose>
			  <c:when test="${!empty follow.biography }">
			     <td>
			       <a href="Profile?username=${follow.username }" class="nav-link" style="color:black;">
			       	  <img class="rounded-circle float-left" alt="" width="50" height="46" src="PICTURES/${follow.profile}"> 
		       		  &nbsp;&nbsp;<c:out value="${follow.firstname }   ${follow.lastname } (${follow.username })"/><br>
		      	 	  &nbsp;&nbsp;<c:out value="${follow.biography }" />
			       </a>
		         </td>
			  </c:when>
			  <c:otherwise>
			     <td>
			        <a href="Profile?username=${follow.username }" class="nav-link" style="color:black;">
			           <img class="rounded-circle float-left" alt="" width="50" height="46" src="PICTURES/${follow.profile}"> 
		       		   &nbsp;&nbsp;<c:out value="${follow.firstname }   ${follow.lastname } (${follow.username })"/><br>
		      	 	   &nbsp;&nbsp;<c:out value="No biography" />
			        </a>		        
		        </td>
			  </c:otherwise>
			</c:choose>	
		    </tr>
	   </c:forEach> 
	 </table>
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