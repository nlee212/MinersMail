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
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerLoginEvent;


public class main extends JavaPlugin {

	private final String HELLO_MESSAGE = "Hello world... (this is the example bukkit plugin.)";
	private final String GOODBYE_MESSAGE = "Goodbye world... (this is the example bukkit plugin.)";
	private MailServer ms;
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) 
	{
		
		if (cmd.getLabel().startsWith("m")) 
		{
			int i = 2;
			String username = "";
			String message = "";
			while( i < 17 && cmd.getLabel().charAt(i) != ' ' )
			{
				username = username + cmd.getLabel().charAt(i);
			}
			while(i < cmd.getLabel().length())
			{
				message = message + cmd.getLabel().charAt(i);
			}
			
			sender.sendMessage("Message sent?: " + ms.sendMail(sender.getName(), username, message));		
			
			
			return true;
		}
		else if (cmd.getName().equalsIgnoreCase("m_get"))
		{
			String currentUser = sender.getName();
			
			MailNode myMail = ms.getMail(currentUser);
			
			while( myMail.isNotPlaceHolder() )
			{
				ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
				BookMeta meta = (BookMeta) book.getItemMeta();
				meta.setTitle("Message From: " + myMail.getSender() );
				meta.setAuthor(myMail.getSender());
				meta.setPages(Arrays.asList(ChatColor.GREEN + "Message: " + myMail.getMessageBody()));

				book.setItemMeta(meta);
				Player player = (Player) sender;
				player.getInventory().addItem(book);
				
				myMail = myMail.getNext();
			}
		}
		else if (cmd.getName().equalsIgnoreCase("m_help"))
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
		public void onLogin(PlayerLoginEvent event) 
		{
			Player playerJoined = event.getPlayer();
			String user = playerJoined.getDisplayName();
			if(ms.hasMail(user)){
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