<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0"> 
<script src="JQUERY/jquery-3.5.0.js" type="text/javascript" ></script>
<link href="BOOTSTRAP/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<script src="BOOTSTRAP/js/bootstrap.min.js" type="text/javascript" ></script>
<title>Update profile</title>
</head>
<body>

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
	 <c:when test="${form.queryResult == 'ok' }">
		<script>
			$(document).ready(function () {
				$('.msg_error').addClass("alert alert-success");
				$('.msg_error').text(" Your account validation is done, complete your profile ! ");
				$('.msg_error').show();
			});
		</script>
	</c:when>
</c:choose>

<div class="container">
	<div class="msg_error" role="alert">
 		Message d'erreur
	</div>
	<br/>
	<h3 align="center">Update profile</h3> <br/>
	<div class="d-flex justify-content-center">
		<form action="updateProfile" method="post" id="form_profile" enctype="multipart/form-data">
		<div class="form-group" >
			<div class="image-upload text-center">
			  <label for="img">
			    <img src='<c:out value="${user.profile}"/>' class="rounded-circle img-fluid" id="profile" width="120" height="110"/>
			  </label>
			  <input id="img" type="file" name="img" accept="image/*" style="display:none;" />
			</div>
		</div>
		  <div class="row">
		  	 <div class="col">
			    <label for="firstname">Firstname *</label>
			    <input type="text" class="form-control" id="firstname" name="firstname" placeholder="xyz" value='<c:out value="${user.firstname }"/>'>
			  </div>
			  <div class="col">
			    <label for="lastname">Lastname *</label>
			    <input type="text" class="form-control" id="lastname" name="lastname" placeholder="abc" value='<c:out value="${user.lastname }"/>'>
			  </div>
		  </div><br>
		  <div class="form-group">
		    <label for="biography">Biography</label>
		    <textarea class="form-control" id="biography" name="biography" rows="3" ><c:out value="${user.biography }"/></textarea>
		  </div>
		  <div class="col text-center">
		  	<button type="button" class="btn btn-primary" id="btn-profile" >Submit</button>
		  </div>
		</form>
	</div>
</div>

<script type="text/javascript">
	 $('.msg_error').hide();
	 $(document).ready(function () {	
		 
		 	/*Form validation*/
		    $('#btn-profile').click(function () {
		        var firstname = $('#firstname').val();
		        var lastname = $('#lastname').val();
		        var biography = $('#biography').val();	
		        
		        
		        if((firstname.trim().length==0) || (lastname.trim().length==0)){
		        	$('.msg_error').addClass("alert alert-danger");
		        	$('.msg_error').text(" The fields are empty, please retry ! ");
		        	$('.msg_error').show();
		        }
		        else{
		        	$('.msg_error').hide();
		        	$('#form_profile').submit();
		        }		        
		    });
		    
		    function readURL(input) {
		        if (input.files && input.files[0]) {
		            var reader = new FileReader();

		            reader.onload = function (e) {
		                $('#profile')
		                    .attr('src', e.target.result)
		                    .width(120)
		                    .height(110);
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