
 $(document).ready(function(){
 	let productRef;
		$('.delete_item').click(function(){	

			productRef = this.getAttribute("value");									
			document.querySelector("#popup_alert").style.display = "block";

		});

		$('.aceptCallButton').click(function(){
			console.log("Selected product = ", productRef);
			$.ajax({
				type: 'POST',
				data: {
					productRef: productRef,
					action: 'deleteProduct'
				},
				url: 'cart',				
				success: function(result){
					document.querySelector("#popup_alert").style.display = "none";
					productRef = -1;
					location.reload();				
				}

			});

		});

		$('.dismissCallButton').click(function(){			
			console.log("Selected product = ", productRef);
			document.querySelector("#popup_alert").style.display = "none";
			productRef = -1;
		});


});


function displayError(){	

	const error = document.querySelector(".error-desc");
	let i = 0, data = "", text = error.getAttribute("data-text");

	let typing = setInterval(() => {
	  if(i == text.length){
	    clearInterval(typing);
	  }else{
	    data += text[i] ;
	    document.querySelector(".error-desc").setAttribute("data-text", data);
	    i++;	    
	  }
	}, 50);	


}