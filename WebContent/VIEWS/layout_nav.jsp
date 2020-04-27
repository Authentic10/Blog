
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
 <a class="navbar-brand ml-4" href="UserServlet">Blog</a>
  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
    <span class="navbar-toggler-icon"></span>
  </button>

  <div class="collapse navbar-collapse" id="navbarSupportedContent">
    <form class="form-inline ml-auto" id="search_form">
      <input class="form-control mr-sm-2" type="search" name="search" id="search_input" placeholder="Search" autocomplete="off">
      <button id="submit_button" class="btn btn-outline-info my-2 my-sm-0" type="button">Submit</button>
    </form>
    <ul class="navbar-nav mr-5 ml-auto">
	    <li class="nav-item active">
	      <a class="nav-link" href="#"><i class="fas fa-search" title="Search" style="font-size:1.2em" id="search_icon"></i></a>
	    </li>
	    <li class="nav-item active">
	      <a class="nav-link" href="#"><i class="fas fa-bell" title="Notifications" style="font-size:1.2em"></i></a>
	    </li>
	    <li class="nav-item dropdown mr-5">
	        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
	          <img class="rounded-circle float-left" alt="" width="24" height="24" src='<c:out value="${sessionScope.avatar}"/>'>
	        </a>
	        <div class="dropdown-menu" aria-labelledby="navbarDropdown">
	          <a class="dropdown-item" href="#"><i class="fas fa-plus" title="New Post"></i>  New Post</a>
	          <a class="dropdown-item" href="Followers?username=${sessionScope.username}"><i class="fas fa-user-friends" title="Relations"></i> Relations</a>
	          <a class="dropdown-item" href="Profile?username=${sessionScope.username}"><i class="fas fa-user" title="Profile"></i>  Profile</a>
	          <div class="dropdown-divider"></div>
	          <a class="dropdown-item" href="LogoutServlet"><i class="fas fa-sign-out-alt" title="Logout"></i>  Sign-out</a>
	        </div>
        </li>
    </ul>
  </div>
</nav>
<div class="container">
	<div class="dropdown-menu"  id="search_result" style="position:sticky;width:30%;left: 45% !important;transform: translateX(-45%) !important;">
	</div>
</div>


<script type="text/javascript">
$(document).ready(function() {
    $('#search_input').keyup(function() {
            var name = $('#search_input').val();
            
            $('#search_result').empty();
      
            if(name.length!=0) {
            	$('#search_result').show();
            } else {
            	$('#search_result').hide();
            }
            
            $.ajax({
    			type: "POST",
                url: "search",
    			data : {
    				username : name
    			},
    			dataType: "json",
    			success : function(data, textStatus, jqXHR) {
    				var count = Object.keys(data.users).length;
    				
    				if(data.success){
    					for(var i=0;i<count;i++){
    						//alert(data.users[i].username);
    						$('#search_result').append('<a class="dropdown-item" href="Profile?username='+data.users[i].username+'"><img class="rounded-circle float-left" alt="" width="35" height="30" src="PICTURES/'+data.users[i].profile+'">&nbsp;'+data.users[i].username+'</a>');
    						$('#search_result').append('<div class="dropdown-divider"></div>');
    					}
    					
    				} else {
						$('#search_result').append('<a class="dropdown-item">User not found</a>');

    				}
    			},
    			  //If there was no resonse from the server
                error: function(jqXHR, textStatus, errorThrown){
                     console.log("Something really bad happened " + textStatus);
                      $("#search_result").html(jqXHR.responseText);
                },
    		});
 
    });
    
    $('#submit_button').click(function() {
        var name = $('#search_input').val();
        
        $('#search_result').empty();
  
        if(name.length!=0) {
        	$('#search_result').show();
        } else {
        	$('#search_result').hide();
        }
        
        $.ajax({
			type: "POST",
            url: "search",
			data : {
				username : name
			},
			dataType: "json",
			success : function(data, textStatus, jqXHR) {
				var count = Object.keys(data.users).length;
				
				if(data.success){
					for(var i=0;i<count;i++){
						//alert(data.users[i].username);
						$('#search_result').append('<a class="dropdown-item" href="Profile?username='+data.users[i].username+'"><img class="rounded-circle float-left" alt="" width="35" height="30" src="PICTURES/'+data.users[i].profile+'">&nbsp;'+data.users[i].username+'</a>');
						$('#search_result').append('<div class="dropdown-divider"></div>');
					}
					
				} else {
					$('#search_result').append('<a class="dropdown-item">User not found</a>');

				}
			},
			  //If there was no resonse from the server
            error: function(jqXHR, textStatus, errorThrown){
                 console.log("Something really bad happened " + textStatus);
                  $("#search_result").html(jqXHR.responseText);
            },
		});

});
    
});
</script>
