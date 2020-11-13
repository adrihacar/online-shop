package beans;

import java.io.Serializable;
import java.util.Calendar;

public class ChatMessage implements Serializable {
	private static final long serialVersionUID = 1L;

	/*Attributes*/
	private String msg;
	
	private long deliveryTime;
	
	private int senderID;
	
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
		
		msgDate += "     " + cal.get(Calendar.DAY_OF_MONTH) + "/" + month + "/" + cal.get(Calendar.YEAR);
							
		return msgDate;	
	}
	
}
