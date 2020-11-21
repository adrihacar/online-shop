
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="java.util.ArrayList"
	import="entities.ChatMessage" import="entities.ChatBean"
	import="entities.ChatElement" import="java.util.List"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<!-- Required meta tags -->
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">

<!-- Bootstrap CSS -->
<link rel="stylesheet" href="./resources/chat.css">
<script src="resources/index-chatroom.js"></script>

<title>chat room</title>

</head>
<body onload="scrollToContent()">
	<div class="container">
		<h3 class=" text-center">Online-shop Chat room</h3>
		<div class="messaging">
			<div class="inbox_msg">
				<div class="inbox_people">
					<div class="headind_srch">
						<div class="recent_heading">
							<h4>Recent</h4>
						</div>
						<div class="srch_bar">
							<div class="stylish-input-group">
								<input type="text" class="search-bar" placeholder="Search">
								<span class="input-group-addon">
									<button type="button">
										<i class="fa fa-search" aria-hidden="true"></i>
									</button>
								</span>
							</div>
						</div>
					</div>
					<%-- For each chat create a new div element to print the chat data --%>
					<div id="inbox_chat" class="inbox_chat">
					<%						
						List<ChatElement> chatList;
						Object oList = session.getAttribute("chatList");
						if (oList == null) {
							out.println(
									"<div><p>Oh no! Something went wrong when we tried to retrieve your messages. Please reload the page</p></div>");
						} else {
							chatList = ((List<ChatElement>) oList);
							if (chatList.isEmpty()) {
								out.println(
										"<div><p>Oh! You have no chats right now. A good ocasion to read a book :)</p></div>");
							} else {
								for (int i = 0; i < chatList.size(); i++) {
									String cssClasses = "";
									String chatName = "";
									long chatRef = 0L;
									
									ChatElement chat = chatList.get(i);
									chatRef = chat.getChatID();

									//Only the first chat of the list has the CSS class 'active_chat'
									if (i == 0) cssClasses += " active_chat ";
									
									//The name of the chat is set to the full name of the recipient user
									chatName = chat.getRecipientUser();
									
									//Special CSS classes assigned based on the type of chat
									if (chat.getChatType().equals(ChatElement.CHAT_TO_BUY)) {
										cssClasses += " buyer_chat ";
									} else {
										cssClasses += " seller_chat ";
									}
									out.println("<div chat-ref=\"" + chatRef + "\" class=\"chat_list" + cssClasses
											+ "\" onclick=\"swapActiveChat(this)\"><div class=\"chat_people\"><div class=\"chat_img\"> <img src=\"https://bootdey.com/img/Content/avatar/avatar2.png\" alt=\"sunil\"></div><div class=\"chat_ib\"><h5>"
											+ chatName + "<span class=\"chat_date\">" + chat.getLastMsgDateToString(request.getLocale())
											+ "</span></h5><p>" + chat.getLastMsgText() + "</p></div></div></div>");
								}
							}
						}
					%>

					</div>
				</div>
				<div class="mesgs">
					<div id="msg_history" class="msg_history"
						onload="scrollToContent()">						

						<%
 			List<String> msgHistory = null;
 			Object msgList = request.getAttribute("msgList"); 			
 			if(msgList == null){
 				out.println("<div><p>Oh no! Something went wrong when we tried to retrieve your messages. Please reload the page</p></div>");
 			}else{
 				msgHistory = (List<String>)msgList;
 				if(msgHistory.isEmpty()){
 					out.println("<div><p>Oh! You have no chats right now. A good ocasion to read a book maybe :)</p></div>");
 				}else{
 					for(String chatMsg : msgHistory){
 						out.println(chatMsg);
 					}
 				}
 			}
     							%>
					</div>					
					<div class="type_msg">
						<div class="input_msg_write">
							<form method="POST" action="online_shop/SendMessage">
								<input type="text" class="write_msg"
									placeholder="Type a message" name="message" />
								<button class="msg_send_btn" type="submit">
									<i class="fa fa-paper-plane-o" aria-hidden="true"></i>
								</button>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>




</body>
</html>