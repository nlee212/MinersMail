package com.nicholaslee.testerPlugin;

import java.sql.Timestamp;
import java.io.Serializable;


/**
 * Object representation of a piece of mail. Chained together LinkList style for use in HashMap
 * Placeholder mail is neccesary due to HashMap's inability to hold NULL as a value. As such,
 * placeholder messages simply have all of it's fields set to NULL.
 * @author Nicholas
 *
 */
public class MailNode implements Serializable{
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
	
	/**
	 * Creates a MailNode based on the given parameters
	 * @param messageBody the message to be contained in the mail
	 * @param sender the name of the person who sent the mail
	 * @param next the next MailNode in the sequence. 
	 * @param timestamp the SQL Timestamp for when the message was created
	 */
	public MailNode(String messageBody, String sender, MailNode next, Timestamp timestamp){
		this.messageBody = messageBody;
		this.sender = sender;
		this.next = next;
		this.timestamp = timestamp;
	}
	
	public boolean isNotPlaceHolder(){
		return messageBody != null && sender != null;
	}
	
	public boolean hasNext(){
		return next != null;
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