
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.ArrayList"
	import="entities.ChatMessage" import="entities.ChatBean"
	import="entities.UserChat" import="java.util.List"
	import="utils.ChatList"%>

<!DOCTYPE html>
<html>
<head>
<!-- Required meta tags -->
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">

<!-- Bootstrap CSS -->
<link rel="stylesheet" href="./resources/chat.css">
<script type="text/javascript"
	src="https://code.jquery.com/jquery-3.5.1.js"></script>
<script src="resources/index-chatroom.js"></script>

<title>chat room</title>

</head>
<body onload="scrollToContent()">
<header style="background: #8ab8dc">
		<%@ include file="header.jsp" %>
    </header>
	<div class="container">
		<h3 class=" text-center">Online-shop Chat room</h3>
		<%
				
				
				ChatList<UserChat> userChats = null;
				Object oList = null; 
				
				oList = session.getAttribute("chats");
				if(oList == null) {
					throw new Exception("Error retrieving the chats");	
				}
				userChats = (ChatList<UserChat>) oList;
																			
				if (userChats.isEmpty()) {%>						
					<div><p style="margin: 25px; padding: 20px;background: aliceblue;font-size: xx-large;">Oh! You have no chats right now. May be a good occasion to read a book :)</p></div>
					
				<% }else{%>
					
					
							<div class="messaging">			
			<div class="inbox_msg">				
				<div class="inbox_people">
					<div class="headind_srch">
						<div class="recent_heading">
							<h4>Recent</h4>
						</div>
						<div class="srch_bar" style="display: none;">
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
														
											for (int i = 0; i < userChats.size(); i++) {												
												
												UserChat chat = userChats.get(i);												

												//Only the first chat of the list has the CSS class 'active_chat'
												String isActiveChat = (i == 0)? " active_chat ": "";
												%>
												
												<div chat-ref="<%= chat.getChatId() %>" class="chat_list <%=chat.getChatType() + " " + isActiveChat%> onclick="swapActiveChat(this)>
												<div class="chat_people">												
												<div class="chat_ib">
													<h5><%= chat.getRecipientUserName() %> 
														<span class="chat_date"><%=chat.getLastMsgDateToString(request.getLocale())%></span>
													</h5>
													<p><%= chat.getLastMsgText()%> </p>
												</div> 														
												</div>
												</div>
											<% }%>
					</div>
				</div>
				<div class="mesgs">
					<div id="msg_history" class="msg_history"
						onload="scrollToContent()">						

					<%
 					if(userChats.getActiveChat().getMessages().isEmpty()){%>
 	 					<div><p style="text-align: center;">Oh! You have not chat with this user before</p></div>
 	 				<% }else{
 	 					
 	 					out.println(userChats.getActiveChat().printMsgHistoryHtml());
 	 				}%>
					</div>					
					<div class="type_msg">
						<div class="input_msg_write">												
							<form id="sendMsgForm" method="POST" action="/online_shop/SendMessage">
								<input type="text" class="write_msg"
									placeholder="Type a message" name="message" />									
									<input type="hidden" id="i_openedChat" name="chatId" value="<%= userChats.getActiveChat().getChatId()%>">
								<button class="msg_send_btn" type="submit" form="sendMsgForm">
									<i class="fa fa-paper-plane-o" aria-hidden="true"></i>
								</button>
							</form>
						</div>
					</div>														
				</div>
			</div>
		</div>														
				<% 
				}								
				%>

	</div>




</body>
</html>