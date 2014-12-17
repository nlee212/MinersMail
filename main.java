package com.nicholaslee.testerPlugin;

import java.util.Arrays;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
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

	private MailServer ms;
	
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
			Player player = (Player) sender;
			MailNode myMail = ms.getMail(currentUser);
					
			while( myMail.isNotPlaceHolder() )
			{
				int nextIndex = player.getInventory().firstEmpty();
				if(nextIndex == -1){ //no available spaces
					sender.sendMessage("Insufficient inventory space for remaining mail");
					break;
				}
				ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
				BookMeta meta = (BookMeta) book.getItemMeta();
				meta.setTitle(myMail.getTimestamp().toString() );
				meta.setAuthor(myMail.getSender());
				meta.setPages(Arrays.asList(myMail.getMessageBody()));
				book.setItemMeta(meta);		
				player.getInventory().setItem(nextIndex,book);
				myMail = myMail.getNext();
			}
			ms.putMail(currentUser, myMail);		
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
				playerJoined.sendMessage("New Mail!");
			}
			else{
				playerJoined.sendMessage("No New Mail");
			}
			
			
		}
	}
	
	
	@Override
	public void onEnable ()
	{
		getLogger().info ("Starting MinersMail");
	      try
	      {
	         FileInputStream fileIn = new FileInputStream("MailServer.ser");
	         ObjectInputStream in = new ObjectInputStream(fileIn);
	         ms = (MailServer) in.readObject();
	         in.close();
	         fileIn.close();
	         getLogger().info("Loaded MailServer From File");
		}catch(Exception e){
			ms = new MailServer();
			getLogger().info("New MailServer Created");
		}
		
		
		
		getServer().getPluginManager().registerEvents(new LoginListener(), this);
	}
	
	@Override
	public void onDisable ()
	{
		ms.writeToFile();
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
