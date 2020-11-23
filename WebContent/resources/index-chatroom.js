
function scrollToContent(){
 	
	let chatListId = ".active_chat"; 
    let activeChatId = ".last_msg";
    let msgBox = ".container";
     	
    //Arg0 = "align_top"
    //Arg0 = true --> show the content at the top of the view
    //Arg0 = false --> show the content at the bottom of the view
    document.querySelector(msgBox).scrollIntoView(true);
    //document.querySelector(chatListId).scrollIntoView(true);
    document.querySelector(activeChatId).scrollIntoView(false);    
 }

 
 $(document).ready(function(){
		$('.chat_list').click(function(){			
			let chatRef = this.getAttribute("chat-ref");
			let chatElem = this;		
			$.ajax({
				type: 'POST',
				data: {
					chatRef: chatRef,					
				},
				url: 'swapchat',
				success: function(result){									
					document.querySelector('#i_openedChat').setAttribute("value", chatRef);
					$('.active_chat').removeClass("active_chat");
					chatElem.classList.add("active_chat");					
					$('#msg_history').html(result);
					
				}
				
			});
			
		});
		
	});
	
/**
* Parses the input (String) to an array of Strings,
* dividing the input String using the separator ', ' 
*/
function parseResult(result){

	//remove the first and the last characters, the '[ ]'
	//Split using the separator ', '
	return result.substring(1, result.length - 1).split(', ');
	
}