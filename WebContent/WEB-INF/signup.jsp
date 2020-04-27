<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0"> 
<script src="JQUERY/jquery-3.5.0.js" type="text/javascript" ></script>
<link href="BOOTSTRAP/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<script src="BOOTSTRAP/js/bootstrap.min.js" type="text/javascript" ></script>
<title>Sign-up</title>
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
	<c:when test="${form.queryResult == 'ut' }">
		<script>
			$(document).ready(function () {
				$('.msg_error').addClass("alert alert-danger");
				$('.msg_error').text(" Username already taken, please try a new one ! ");
				$('.msg_error').show();
			});
		</script>
	</c:when>
	<c:when test="${empty form.queryResult  }">
		<script>
			$(document).ready(function () {
				$('.msg_error').hide();
			});
		</script>
	</c:when>
	<c:when test="${not empty form.queryResult  }">
		<script>
			$(document).ready(function () {
				$('.msg_error').addClass("alert alert-danger");
				$('.msg_error').text('<c:out value="${form.queryResult}"/>');
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
	<h3 align="center">Sign-up</h3> <br/>
	<div class="d-flex justify-content-center">
		<form action="signup" method="post" id="form_aut">
			  <div class="form-group">
			    <label for="email">Email address *</label>
			    <input type="email" class="form-control" id="email" name="email" placeholder="xyz@mail.com" value='<c:out value="${form.email }"/>'>
			  </div>
			  <div class="form-group">
			    <label for="username">Username *</label>
			    <input type="text" class="form-control" id="username" name="username" placeholder="xyz" value='<c:out value="${form.username }"/>'>
			  </div>
			  <div class="form-group">
			    <label for="password">Password *</label>
			    <input type="password" class="form-control" id="password" name="password" aria-describedby="passHelp" placeholder="******">
			    <small id="passHelp" class="form-text text-muted">Use at least 6 carachters</small>
			  </div>
			  <div class="form-group">
			    <label for="password_confirmation">Confirm password *</label>
			    <input type="password" class="form-control" id="password_confirmation" name="password_confirmation" placeholder="******">
			  </div>
			  <div class="col text-center">
			  	<button type="button" class="btn btn-primary" id="btn-aut" >Submit</button>
			  </div>
			  <div class="col text center my-4">
		      	<p align="center"><a href="Index">Sign-in ?</a></p>
		      </div>
		</form>
	</div>
</div>
	
	
<script type="text/javascript">
	 $('.msg_error').hide();
	 $(document).ready(function () {		 
		    $('#btn-aut').click(function () {
		        var username = $('#username').val();
		        var pass = $('#password').val();
		        var pass_confirmation = $('#password_confirmation').val();
		        var email = $('#email').val();
		      

		        if((username.trim().length==0) || (pass.trim().length==0) || (email.trim().length==0) || (pass_confirmation.trim().length==0)){
		        	$('.msg_error').addClass("alert alert-danger");
		        	$('.msg_error').text(" The fields are empty, please retry ! ");
		        	$('.msg_error').show();
		        }
		        else{
		        	$('.msg_error').hide();
		        	$('#form_aut').submit();
		        }		        
		    });

	    });
</script>
</body>
</html>