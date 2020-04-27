<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<jsp:include page="/VIEWS/layout_head.jsp" />   
<title>Following</title>
</head>
<body>

<jsp:include page="/VIEWS/layout_nav.jsp" />   

	<div class="container"><br><br>
	  <ul class="nav nav-tabs ">
		  <li class="nav-item">
		    <a class="nav-link " href="Followers">Followers</a>
		  </li>
		  <li class="nav-item">
		    <a class="nav-link active" href="Following">Following</a>
		  </li>
		</ul>
	  <br>
	  <p><strong>Note:</strong> This example shows how to create a basic navigation tab. It is not toggleable/dynamic yet (you can't click on the links to display different content)- see the last example in the Bootstrap Tabs and Pills Tutorial to find out how this can be done.</p>
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
		    	 }        
		    });

	    });
</script>
</body>
</html>