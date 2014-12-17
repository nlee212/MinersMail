package com.nicholaslee.testerPlugin;

import java.util.HashMap;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Calendar;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;

public class MailServer implements Serializable{
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
	
	public boolean createInbox(String user){
		if(!mailboxes.containsKey(user)){
			mailboxes.put(user, PLACEHOLDER);
			return true;
		}
		return false;
		
	}
	
	/**
	 * Determine if the given user had a mailbox
	 * @param user the given user
	 * @return  True if the given user has a mailbox and has at least one unclaimed message, false otherwise
	 */
	public boolean onLogin(String user){
		if(!mailboxes.containsKey(user)){
			mailboxes.put(user, PLACEHOLDER);
			return false;
		}
		return true;
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
	
	public void PutMail(String user, MailNode m){
		if(mailboxes.containsKey(user))
			mailboxes.put(user, m);
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
