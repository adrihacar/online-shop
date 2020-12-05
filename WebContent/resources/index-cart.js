
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
		/*
		$('.openChat').click(function(){
			let userRef = this.getAttribute("value");
			
			$.post("chatroom", 
			{
				sendTo: userRef
			},
			function(result){
				console.log("chat created successfully");				
				//location.assign("/online_shop/chatroom");
			});
		
		});
		*/	

});

