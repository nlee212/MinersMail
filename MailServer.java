package com.nicholaslee.testerPlugin;

import java.util.HashMap;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Calendar;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;

public class MailServer implements Serializable{
	
	private static final MailNode PLACEHOLDER = new MailNode();
	private HashMap<String, MailNode> mailboxes;

	/**
	 * Creates a new MailServer with no users or mail
	 */
	public MailServer(){
		mailboxes = new HashMap<String, MailNode>();
	}
	
	/**
	 * Serialize the current MailServer
	 */
	public void writeToFile(){
		 try
	      {
	         FileOutputStream fileOut = new FileOutputStream("MailServer.ser");
	         ObjectOutputStream out = new ObjectOutputStream(fileOut);
	         out.writeObject(this);
	         out.close();
	         fileOut.close();
	      }catch(IOException i)
	      {
	          i.printStackTrace();
	      }
	}
	
	
	/**
	 * Action to be performed whenever a user logs in. Creates an inbox for new users and
	 * returns whether or not the given user has any unclaimed mail
	 * @param user the given user
	 * @return  True if the given user has at least one unclaimed message, false otherwise
	 */
	public boolean onLogin(String user){
		if(!mailboxes.containsKey(user)){
			mailboxes.put(user, PLACEHOLDER);
			return false;
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
	 * Used to place arbitrary mail back into the system for the given user. 
	 * Needed due to inventory constraints
	 * @param user the given user
	 * @param m the MailNode to place into the given user's inbox
	 */
	public void putMail(String user, MailNode m){
		if(mailboxes.containsKey(user))
			mailboxes.put(user, m);
	}
	
	
	/**
	 * Send mail from one player to another
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
