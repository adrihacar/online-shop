
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