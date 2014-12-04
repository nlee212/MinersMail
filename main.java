package com.nicholaslee.testerPlugin;

import java.util.Arrays;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.command.CommandSender;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;


public class main extends JavaPlugin {


	private final String GOODBYE_MESSAGE = "Goodbye world... (this is the example bukkit plugin.)";
	private MailServer ms;
	private final int MAX_INVENTORY_IN_SURVIVAL_MODE = 27;
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) 
	{
		String currentUser = sender.getName();
		if (cmd.getLabel().startsWith("m") && args.length > 1) //need to come up with a better way to catch msgs
		{
			String message = "";
					
			for(int i = 1; i < args.length; i++){
				message += args[i]+" ";
			}
			if( ms.sendMail(sender.getName(), args[0], message)){
				sender.sendMessage("Message sent!");
				getLogger().info (sender.getName()+" sent mail to "+args[0]);
			}
			else{
				sender.sendMessage("Mail not sent");
				getLogger().info ("Mail Failed");
			}
					
			
		}
		else if (cmd.getLabel().equalsIgnoreCase("m_get"))
		{
			
		
			MailNode myMail = ms.getMail(currentUser);
			
	
			int mailCount = 0;
			//To get current player instance
			Player player = (Player) sender;
			
			int currentInventorySize = player.getInventory().getSize();//get player current inventory size
			
			if(currentInventorySize < MAX_INVENTORY_IN_SURVIVAL_MODE)//if enough space then get mail
			{
				while( myMail.isNotPlaceHolder() )
				{
					
					ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
					BookMeta meta = (BookMeta) book.getItemMeta();
					
					
					meta.setTitle("Message From: " + myMail.getSender() );
					meta.setAuthor(myMail.getSender());
					meta.setPages(Arrays.asList(ChatColor.GREEN + "Message: " + myMail.getMessageBody()));
					book.setItemMeta(meta);
					
					
					player.getInventory().addItem(book);
					
					myMail = myMail.getNext();
					mailCount++;
				}
				getLogger().info (currentUser +" retrieved " + mailCount + "messages");
			}
			else
			{
				getLogger().info (currentUser +" No more space in your inventory.");
			}
		}
		
		else if (cmd.getLabel().equalsIgnoreCase("m_help"))
		{
			sender.sendMessage("/m (recipient) (message)");
			sender.sendMessage("This command will create a new message to the intended recipient.");
			sender.sendMessage("/m_get");
			sender.sendMessage("This command will get the user's email when they log in.");
			sender.sendMessage("/m_help");
			sender.sendMessage("This command will get you help.");
		}
		return true;
	}
	

	public final class LoginListener implements Listener{
		@EventHandler
		public void onLogin(PlayerJoinEvent event) 
		{
			Player playerJoined = event.getPlayer();
			String user = playerJoined.getDisplayName();
			if(ms.onLogin(user)){
				playerJoined.sendMessage("u hav new mail");
			}
			else{
				playerJoined.sendMessage("u hav no new mail");
			}
			
			
		}
	}
	
	
	@Override
	public void onEnable ()
	{
		getLogger().info ("Starting MinersMail");
		ms = new MailServer();
		getServer().getPluginManager().registerEvents(new LoginListener(), this);
	}
	
	@Override
	public void onDisable ()
	{
		getLogger().info (GOODBYE_MESSAGE);
	}
	
    public void logToFile(String message)
    {
    	try {
    		File dataFolder = getDataFolder();
    		if(!dataFolder.exists()) {
    			dataFolder.mkdir();
    		}
     
    		File saveTo = new File(getDataFolder(), "myplugin.log");
    		if (!saveTo.exists()) {
    			saveTo.createNewFile();
    		}
     
    		FileWriter fw = new FileWriter(saveTo, true);
     
    		PrintWriter pw = new PrintWriter(fw);
     
    		pw.println(message);
    		pw.flush();
    		pw.close();
     
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
     
    }
}
/*
 * 
 * 
 * 
 * sender.sendMessage("Send to: " + username + "\nMessage: " + message);
			
			ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
			BookMeta meta = (BookMeta) book.getItemMeta();
			meta.setTitle("Message For: " + username );
			meta.setAuthor(sender.getName());
			meta.setPages(Arrays.asList(ChatColor.GREEN + "Message: " + message));

			book.setItemMeta(meta);

*/