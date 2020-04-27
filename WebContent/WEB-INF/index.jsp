<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0"> 
<script src="JQUERY/jquery-3.5.0.js" type="text/javascript" ></script>
<link href="BOOTSTRAP/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<script src="BOOTSTRAP/js/bootstrap.min.js" type="text/javascript" ></script>
<!-- <link href="css/css.css" rel="stylesheet" type="text/css" /> -->
<!-- <script src="js/js.js"  ></script>    -->
<title>Index</title>
</head>
<body>

<c:choose>
    <c:when test="${form.queryResult == 'no' }">
		<script>
			$(document).ready(function () {
				$('.msg_error').addClass("alert alert-danger");
				$('.msg_error').text(" Your credentials are wrong, please retry ! ");
				$('.msg_error').show();
			});
		</script>
	</c:when> 
	 <c:when test="${form.queryResult == 'na' }">
		<script>
			$(document).ready(function () {
				$('.msg_error').addClass("alert alert-danger");
				$('.msg_error').text(" Credentials not found, if you don't have an account yet, create a new one ! ");
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
	<h3 align="center">Sign-in</h3> <br/>
	<div class="d-flex justify-content-center">
		<form action="index" method="post" id="form_aut">
			  <div class="form-group">
			    <label for="username">Username</label>
			    <input type="text" class="form-control" id="username" name="username" placeholder="xyz" value='<c:out value="${form.username }"/>'>
			  </div>
			  <div class="form-group">
			    <label for="password">Password</label>
			    <input type="password" class="form-control" id="password" name="password" placeholder="******">
			  </div>
			  <div class="col text-center">
			  	<button type="button" class="btn btn-primary" id="btn-aut" >Submit</button>
			  </div>
			  <div class="row my-4">
		      	<a class="mr-5" href="SignUp">Sign-up ?</a>
		      	<a class="ml-2" href="#">Forgot password ?</a>
		      </div>
		</form>
	</div>
</div>
	
	
<script type="text/javascript">
	 $('.msg_error').hide();
	 $(document).ready(function () {		 
		    $('#btn-aut').click(function () {
		        var login = $('#username').val();
		        var pass = $('#password').val();

		        if((login.trim().length==0) || (pass.trim().length==0 )){
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