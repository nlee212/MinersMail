package com.nicholaslee.testerPlugin;

import java.sql.Timestamp;


/**
 * Object representation of a piece of mail. Chained together LinkList style for use in HashMap
 * Placeholder mail is neccesary due to HashMap's inability to hold NULL as a value. As such,
 * placeholder messages simply have all of it's fields set to NULL.
 * @author Nicholas
 *
 */
public class MailNode{
	private String messageBody;
	private String sender;
	private MailNode next;
	Timestamp timestamp;
	
	/**
	 * Creates a placeholder mail
	 */
	public MailNode(){
		messageBody = null;
		sender = null;
		next = null;
		timestamp = null;
	}
	
	public MailNode(String messageBody, String sender, MailNode next, Timestamp timestamp){
		this.messageBody = messageBody;
	}
	
	public boolean isNotPlaceHolder(){
		return messageBody != null && sender != null;
	}
	
	public MailNode getNext(){
		return next;
	}

	public String getMessageBody() {
		return messageBody;
	}

	public String getSender() {
		return sender;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}
	

	

	
}