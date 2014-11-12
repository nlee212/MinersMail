package com.nicholaslee.testerPlugin;

import java.util.HashMap;
import java.sql.Timestamp;
import java.util.Calendar;


public class MailServer {
	private static final MailNode PLACEHOLDER = new MailNode();
	private HashMap<String, MailNode> mailboxes;

	/**
	 * Creates a mailbox for the given user
	 * @param user the user to create a mailbox for
	 * @return true if creation is successful, false otherwise
	 */
	public boolean createInbox(String user){
		if(!mailboxes.containsKey(user)){
			mailboxes.put(user, PLACEHOLDER);
			return true;
		}
		return false;
		
	}
	
	/**
	 * Determine if the given user has mail. Also usable to see if the user has a mailbox.
	 * @param user the given user
	 * @return  True if the given user has a mailbox and has at least one unclaimed message, false otherwise
	 */
	public boolean hasMail(String user){
		return (mailboxes.containsKey(user) && mailboxes.get(user).isNotPlaceHolder()); 
	}
	
	/**
	 * Gets the mail for the given user and empties their mailbox
	 * @param user the user to find mail for
	 * @return returns the first message in the user's inbox, or a placeholder if their mailbox was empty.
	 */
	public MailNode getMail(String user){
		if(hasMail(user)){
			MailNode m = mailboxes.get(user);
			mailboxes.put(user, PLACEHOLDER);
			return m;
		}
		return PLACEHOLDER;
		
	}
	
	/**
	 * do da dansing
	 * @param sender
	 * @param recipient
	 * @param message
	 * @return
	 */
	public boolean sendMail(String sender, String recipient, String message){
		if(mailboxes.containsKey(recipient)){
			MailNode n = mailboxes.get(recipient);
			Timestamp now = new Timestamp(Calendar.getInstance().getTime().getTime());
			mailboxes.put(recipient, new MailNode(message, sender, n, now));
			return true;
		}
		return false;
	}
	
}
