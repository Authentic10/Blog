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
<title>Code verification</title>
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
	 <c:when test="${form.queryResult == 'ic' }">
		<script>
			$(document).ready(function () {
				$('.msg_error').addClass("alert alert-danger");
				$('.msg_error').text(" Your code is wrong, please retry ! ");
				$('.msg_error').show();
			});
		</script>
	</c:when> 
	<c:when test="${form.queryResult == 'ok' }">
		<script>
			$(document).ready(function () {
				$('.msg_error').addClass("alert alert-success");
				$('.msg_error').text(" Your subscription is done. We sent you an email to confirm your account ! ");
				$('.msg_error').show();
			});
		</script>
    </c:when> 
    <c:when test="${form.queryResult == 'av' }">
		<script>
			$(document).ready(function () {
				$('.msg_error').addClass("alert alert-success");
				$('.msg_error').text(" You must valid your subscription to access your account. Use the code we sent you on your subscription ");
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
	<h3 align="center">Enter the code</h3> <br/>
	<div class="d-flex justify-content-center">
		<form action="codeVerification" method="post" id="form_aut">
			  <div class="form-group">
			    <label for="code">Code</label>
			    <input type="text" class="form-control" id="code" name="code" value='<c:out value="${form.code }"/>' style="text-align:center;">
			  </div>
			  <div class="col text-center">
			  	<button type="button" class="btn btn-primary" id="btn-aut" >Submit</button>
			  </div>
		</form>
	</div>
</div>
	
	
<script type="text/javascript">
	 $('.msg_error').hide();
	 $(document).ready(function () {	
		 
		 	/*Form validation*/
		    $('#btn-aut').click(function () {
		        var code = $('#code').val();

		        if(code.trim().length==0){
		        	$('.msg_error').addClass("alert alert-danger");
		        	$('.msg_error').text("Th field is empty, please retry ! ");
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