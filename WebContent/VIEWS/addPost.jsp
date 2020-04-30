<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<jsp:include page="/VIEWS/layout_head.jsp" />   
<title>Add a post</title>
</head>
<body>

<jsp:include page="/VIEWS/layout_nav.jsp" />
<c:choose>
    <c:when test="${form.queryResult == 'no' }">
		<script>
			$(document).ready(function () {
				$('.msg_error').addClass("alert alert-danger");
				$('.msg_error').text(" There was an error, please retry ! ");
				$('.msg_error').show();
			});
		</script>
	</c:when>
</c:choose>

<div class="msg_error" role="alert">
 		Message d'erreur
</div>

<div class="container"><br>
	<h2>Add a new post</h2><br>
	<form action="AddPost" method="post" id="post_form" enctype="multipart/form-data">
	  <div class="form-group">
	    <label for="title">Post tile</label>
	    <input type="text" class="form-control" name="title" id="title" placeholder="My title" value='<c:out value="${post.title }"/>' autofocus>
	  </div>
	  <div class="form-group" >
			<div class="image-upload">
			  <label for="img">  Add a picture to your post<br>
			    <i class="btn btn-primary btn-sm">Choose the picture</i>
			  </label>	  
			  <input id="img" type="file" name="img" accept="image/*" style="display:none;" />
			</div>
			<div class="text-center">
				<img src="" class="img-fluid" id="profile" alt="post picture"/>
			</div>
	 </div>
     <div class="form-group">
  	 <label for="text-post">Post text</label>
     <textarea class="form-control" id="text-post" name="text-post" rows="8" placeholder="Your post text"><c:out value="${post.text }"/></textarea>
     </div>
	 <div class="my-4 text-right">
	  	<button type="button" class="btn btn-primary disabled" id="sub_button">Submit</button>
	 </div> 
	</form>
</div>

	
<script >
     $("#search_form").hide();
     $('#profile').hide();
	 $('.msg_error').hide();
	 $(document).ready(function () {
		 
		 /*Change the search icon onclick*/
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
	
			/*If the submit button is clicked*/
		    $('#sub_button').click(function () {
		        var title = $('#title').val();
		        var text_post = $('#text-post').val();
		        
		        if((title.trim().length==0)){
		        	$('.msg_error').addClass("alert alert-danger");
		        	$('.msg_error').text(" Your post must have a title ! ");
		        	$('.msg_error').show();
		        } else if(title.trim().length > 150){
		        	$('.msg_error').addClass("alert alert-danger");
		        	$('.msg_error').text(" Your post title must not have more than 150 carachters ! ");
		        	$('.msg_error').show();
		        }
		        else{
		        	$('.msg_error').hide();
		        	$('#post_form').submit();
		        }		        
		    });
		    
		    /*Enable or disable the submit button when the user is writing the post*/
		    $('#text-post').keyup(function(){
		    	var post = $('#text-post').val();
		    	var title = $('#title').val();
		    	
		    	if((post.trim().length > 30) && (title.trim().length < 150)){
		        	$('#sub_button').removeClass("disabled");
		    	} else {
			        $('#sub_button').addClass("disabled");
		    	}
		    });
		    
		    /*Enable or disable the submit button when the user is writing the post title*/
		    $('#title').keyup(function(){
		    	var post = $('#text-post').val();
		    	var title = $('#title').val();
		    	
		    	if((post.trim().length > 30) && (title.trim().length < 150)){
		        	$('#sub_button').removeClass("disabled");
		    	} else {
			        $('#sub_button').addClass("disabled");
		    	}
		    });
		    
		    /*Show the picture the user choose*/
		    function readURL(input) {
		        if (input.files && input.files[0]) {
		            var reader = new FileReader();

		            reader.onload = function (e) {
		                $('#profile')
		                    .attr('src', e.target.result);
		                $('#profile').show();
		                $('.btn-sm').text("Change picture");
		            };

		            reader.readAsDataURL(input.files[0]);
		        }
		    }
		    
		    $("#img").change(function(){
		        readURL(this);
		    });

	    });
</script>
</body>
</html>