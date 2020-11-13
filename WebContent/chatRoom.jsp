<%@page import="beans.ChatBean"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.util.ArrayList,beans.ChatMessage"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
 <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

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
            <input type="text" class="search-bar"  placeholder="Search" >
            <span class="input-group-addon">
            <button type="button"> <i class="fa fa-search" aria-hidden="true"></i> </button>
            </span> </div>
        </div>
      </div>
       <!-- For each chat create a new div element to print the chat data -->
      <div id="inbox_chat"class="inbox_chat">      
        <%
              	/*
                      	int myID = 1312;
                      	ArrayList<ChatBean> chatList;
                      	Object oList = request.getAttribute("chatList");
                      	if(oList == null){                   
                      		out.println("<div><p>Oh no! Something went wrong when we tried to retrieve your messages. Please reload the page</p></div>");
                      	}else{
                  			chatList = ((ArrayList<ChatBean>)oList);
                  			if(chatList.isEmpty()){
                  				out.println("<div><p>Oh! You have no chats right now. A good ocasion to read a book maybe :)</p></div>");
                  			}else{
                  				for(int i = 0; i<chatList.size(); i++){
                  					String cssClasses = "";
                  					String chatName = "";
                  					ChatBean chat = chatList.get(i);    					
                  					if(i == 0) cssClasses += " active_chat ";
                  					cssClasses += chat.getBuyerID() == myID ? " buyer_chat ":" seller_chat ";
                  					if(chat.getBuyerID() == myID){
                  						cssClasses += " buyer_chat ";
                  						chatName = String.valueOf(chat.getSellerID());
                  						
                  					}else{
                  						cssClasses += " seller_chat ";
                  						//Show the name of the user you are talking to
                  						chatName = String.valueOf(chat.getBuyerID());
                  					}
              				out.println("<div class=\"chat_list" + cssClasses + "\" href=\"/swapActiveChat\"><div class=\"chat_people\"><div class=\"chat_img\"> <img src=\"https://bootdey.com/img/Content/avatar/avatar2.png\" alt=\"sunil\"></div><div class=\"chat_ib\"><h5>" + chatName +"<span class=\"chat_date\">"+ chat.lastMsg.date + "</span></h5><p>" + chat.lastMsg.msg + "</p></div></div></div>");    					
                  				}
                  			}
              	        }
                      	*/
                      
              %>        
        <div class="chat_list active_chat">
          <div class="chat_people">
            <div class="chat_img"> <img src="https://bootdey.com/img/Content/avatar/avatar7.png" alt="sunil"> </div>
            <div class="chat_ib">
              <h5>Sunil Rajput <span class="chat_date">Dec 25</span></h5>
              <p>Test, which is a new approach to have all solutions 
                astrology under one roof.</p>
            </div>
          </div>
        </div>
        <div class="chat_list">
          <div class="chat_people">
            <div class="chat_img"> <img src="https://bootdey.com/img/Content/avatar/avatar3.png" alt="sunil"> </div>
            <div class="chat_ib">
              <h5>Kumar Warpuj<span class="chat_date">Dec 25</span></h5>
              <p>Test, which is a new approach to have all solutions 
                astrology under one roof.</p>
            </div>
          </div>
        </div>
        
      </div>
    </div>
    <div class="mesgs">
      <div id="msg_history" class="msg_history" onload="scrollToContent()">
        <!-- Servlet to get the messages of the active chat -->
     	<!-- 1) Request the list of messages of the current chat (pass the parameter convID) -->
     	<!-- 2) Read the response.listMessages and print each message consecutively -->
     			
		
		<%
 			ArrayList<ChatMessage> msgHistory = null;
 			Object msgList = request.getAttribute("msgList");
 			int myID = 1312;//This value is for test purposes
 			if(msgList == null){
 				out.println("<div><p>Oh no! Something went wrong when we tried to retrieve your messages. Please reload the page</p></div>");
 			}else{
 				msgHistory = ((ArrayList<ChatMessage>)msgList);
 				if(msgHistory.isEmpty()){
 					out.println("<div><p>Oh! You have no chats right now. A good ocasion to read a book maybe :)</p></div>");
 				}else{
 					for (int i = 0; i < msgHistory.size(); i++){
 						String cssClasses= "";
 						ChatMessage msg = msgHistory.get(i);
 						//The last message element has an special CSS class 
 						if(i == msgHistory.size() - 1){									
 							cssClasses += "last_msg";
 						}
 						if(msg.getSenderID() == myID) { //session.myID
 							//Outgoing message
 							out.println("<div class=\"outgoing_msg " + cssClasses + "\"><div class=\"sent_msg\"><p>" + msg.getMsg() +"</p>" + "<span class=\"time_date\">"+ msg.getDeliveryTimeToString() +"</span></div></div>");
 						}else{
 							//Incoming message
 							out.println("<div class=\"incoming_msg " + cssClasses
 														+ "\"><div class=\"incoming_msg_img\"> <img src=\"https://bootdey.com/img/Content/avatar/avatar1.png\" alt=\"sunil\"></div><div class=\"received_msg\"><div class=\"received_withd_msg\"><p>"
 														+ msg.getMsg() + "</p><span class=\"time_date\">" + msg.getDeliveryTimeToString()
 														+ "</span></div></div></div>");
 						}
 					}
 				}
 			}
     							%>      
      </div>
      <button class="msg_send_btn" onclick="scrollToContent()">Scroll to content</button>
      <div class="type_msg">
        <div class="input_msg_write">
          <form method="POST" action="SendMessageQueue.html">
          	<input type="text" class="write_msg" placeholder="Type a message" name="message" />
          	<button class="msg_send_btn" type="submit" ><i class="fa fa-paper-plane-o" aria-hidden="true"></i></button>
          </form>
        </div>
      </div>
    </div>
  </div>
</div>
</div>




</body>
</html>