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
	
	public MailServer(){
		mailboxes = new HashMap<String, MailNode>();
		
	}
	
	public boolean createInbox(String user){
		if(!mailboxes.containsKey(user)){
			mailboxes.put(user, PLACEHOLDER);
			return true;
		}
		return false;
		
	}
	
	/**
	 * Method to be called whenever a user logs in. Creates an inbox if the user doesnt already have one
	 * @param user the given user
	 * @return  True has at least one unclaimed message, false otherwise
	 */
	public boolean onLogin(String user){
		if(!mailboxes.containsKey(user)){
			mailboxes.put(user, PLACEHOLDER);
		}
		return mailboxes.get(user).isNotPlaceHolder();
	}
	
	
	
	/**
	 * Gets the mail for the given user and empties their mailbox
	 * @param user the user to find mail for
	 * @return returns the first message in the user's inbox, or a placeholder if their mailbox was empty.
	 */
	public MailNode getMail(String user){
		if(mailboxes.containsKey(user)){
			MailNode m = mailboxes.get(user);
			mailboxes.put(user, PLACEHOLDER);
			return m;
		}
		return PLACEHOLDER;
		
	}
	

	
	/**
	 * do da dansing
	 * @param sender the sender of the mail
	 * @param recipient the recpient of the mail
	 * @param message the message to send
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
