package entities;

import java.io.Serializable;
import java.util.Calendar;

import javax.jms.JMSException;
import javax.jms.TextMessage;

public class ChatMessage implements Serializable {
	private static final long serialVersionUID = 1L;

	/*Attributes*/
	private String msg;

	private long deliveryTime;

	private int senderID;


	/**
	 * Creates a new ChatMessage instance based on a given Message object 
	 * 
	 * @param oMessage Reference to the Message object
	 * @return a new instance of ChatMessage created from the information contained in the Message object
	 */
	public ChatMessage(Object oMessage) {

		String txt = "";
		long deliveryTime = 0L;
		int iSenderID = -1;

		try {

			//Set the message text
			txt = ((TextMessage) oMessage).getText();

			//Set the delivery time of the message
			deliveryTime = ((TextMessage) oMessage).getJMSTimestamp();

			//Set the sender (userID) of the message
			iSenderID = ((TextMessage) oMessage).getIntProperty("senderID");

		} catch (JMSException eJMS) {
			System.out.println("ERROR reading message content: " + eJMS.getMessage());
			txt = "This message is corrupt. We are working to solve it. Sorry for the issue";
		}finally {
			this.setMsg(txt);
			this.setDeliveryTime(deliveryTime);
			this.setSenderID(iSenderID);			
		}

	}


	/*Setters & Getters*/
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public long getDeliveryTime() {

		return deliveryTime;
	}
	public void setDeliveryTime(long l) {
		this.deliveryTime = l;
	}

	public int getSenderID() {
		return senderID;
	}

	public void setSenderID(int senderID) {
		this.senderID = senderID;
	}	

	public String getDeliveryTimeToString() {
		String msgDate = "";
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(this.deliveryTime);		
		//Calendar represents the month with indexes from 0 to 11
		//Thus adding one the real month number is displayed 
		int month = cal.get(Calendar.MONTH) + 1;
		//Assure that both hours and minutes are represented with 2 digits
		msgDate += cal.get(Calendar.HOUR_OF_DAY) <= 9 ? "0" + cal.get(Calendar.HOUR_OF_DAY): cal.get(Calendar.HOUR_OF_DAY);
		msgDate += cal.get(Calendar.MINUTE) <= 9 ? ":0" + cal.get(Calendar.MINUTE): ":" + cal.get(Calendar.MINUTE);

		msgDate += "       [" + cal.get(Calendar.DAY_OF_MONTH) + "/" + month + "/" + cal.get(Calendar.YEAR) + "]";

		return msgDate;	
	}

	/**
	 * Returns the HTML representation of the message. Although the visualization is not part of the model, this auxiliary method was created to support the AJAX event triggered when swapping the active chat 
	 * @param loggedUser User logged in. Is the one requesting the message
	 * @param isLastMsg If true the message contains an extra CSS class named 'last_msg'
	 * @return The HTML code generated for this ChatMessage object
	 */
	public String toHtml(int loggedUser, boolean isLastMsg) {
		String result = "";
		String cssClasses= "";
		if(isLastMsg){									
			cssClasses += "last_msg";
		}
		if(this.getSenderID() == loggedUser) { 
			//Outgoing message
			result += "<div class=\"outgoing_msg " + cssClasses + "\"><div class=\"sent_msg\"><p>" + this.getMsg() +"</p>" + "<span class=\"time_date\">"+ this.getDeliveryTimeToString() +"</span></div></div>";
		}else{
			//Incoming message
			result += "<div class=\"incoming_msg " + cssClasses
					+ "\"><div class=\"received_msg\"><div class=\"received_withd_msg\"><p>"
					+ this.getMsg() + "</p><span class=\"time_date\">" + this.getDeliveryTimeToString()
					+ "</span></div></div></div>";
		}
		return result;
	}

}
