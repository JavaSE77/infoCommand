package club.hardcoreminecraft.javase.Info;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;


import net.md_5.bungee.api.ChatColor;



public class main extends JavaPlugin {

 
	private static main plugin;
	




	// Called when the plugin is enabled. It is used to set up variables and to register things such as commands.
	@Override
	public void onEnable() {
		plugin = getPlugin(main.class);
		PluginManager pluginManager = getServer().getPluginManager();

		
	    getConfig().options().copyDefaults(true); 
	    saveDefaultConfig();
		
		/*
		 * Register a command to the list of usable commands. If you don't register the
		 * command, it won't work! Also if you change the command name, make sure to
		 * also change in the plugin.yml file.
		 */
		this.getCommand("info").setExecutor(this);
		this.getCommand("einfo").setExecutor(this);
		
		pluginManager.registerEvents(new commandProcessor(), this);
		/*
		 * Make sure you register your listeners if you have any! If you have a class
		 * that implements Listener, you need to make sure to register it. Otherwise it
		 * will DO NOTHING!
		 */
		//pluginManager.registerEvents(new ExampleListener(), this);

		/*
		 * This line lets you send out information to the console. In this case it would
		 * say: Yay, Template-Plugin is now enabled!
		 */
		this.getLogger().info("Info plugin, created by JavaSE");
	}
	
	
	
	/* Called when the command is ran
	args variable is the commands arguments in an array of strings.
	sender variable is the sender who ran the command
	*/
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
	

		/* Checks if the sender is sending from the console
		   ChatColor is the variable for color
		   § <- Minecraft color code symbol or use chat_color
		*/


		if(cmdLabel.equalsIgnoreCase("info") || cmdLabel.equalsIgnoreCase("einfo")) {
			
			//Get the player
			final CommandSender sendert = sender;
			
			if((args.length == 1) && args[0].equalsIgnoreCase("reload"))  {
				if(sender.hasPermission("JavaSE.info.reload")) {
				reloadConfig();
				sender.sendMessage(ChatColor.LIGHT_PURPLE + "reloaded");
			} else sender.sendMessage(ChatColor.DARK_RED + "JavaSE has personally forbidden you from using this command.");
			} else {
			
			final String index;
			if(args.length < 1) {index = null;} else index = args[0];
			final String arg3;
			if(args.length < 2) {arg3 = null;} else arg3 = args[1];
			
			
			Bukkit.getScheduler().runTaskAsynchronously(plugin, new Runnable() {
			    @Override
			    public void run() {
			    	
			        threadInfo(sendert, index, arg3);
			    }


			});
			
		}
			  
			  }
		
		
		
		
		
		return false;
	}
	
	
	
	public void threadInfo(CommandSender sender, String index, String arg3) {
		// TODO Auto-generated method stub
		String filename = plugin.getDataFolder()  + File.separator + "info";
		
		if(index != null) {
			//Check if the player has permission to view the file they would like to view
			if(sender.hasPermission("JavaSE.info." + index)) {
				
				readFileToSender(sender, (filename + "_" + index +".txt"), arg3);
				
			} else sender.sendMessage(getConfig().getString("invalidPermissionForFile").replaceAll("&", "§"));
			
			
		} else {
		// Then we know that the sender has permission to view the default page. Less we need to check if they have permission
			readFileToSender(sender, (filename + ".txt"), arg3);
		}
		
	}
	
	public void readFileToSender(CommandSender sender, String fileName, String index) {
		
		//setup the path to the file
		  String filePath = plugin.getDataFolder() + "";
		  
	  File dr = new File(filePath);
		  
		  if(!dr.exists())
			 dr.mkdir();
		  
		  File file = new File(fileName);
		  
		  if(!file.exists())
			try {
				file.createNewFile();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		  
		  
		  //At this point we assume that the file exists, and if it does not, we already created it.
		  BufferedReader br;
		try {
			
			
			br = new BufferedReader(new FileReader(file));
			int upper = getConfig().getInt("linesPerPage");
			int lower = 0;
			
			if(!(index == null || index.isEmpty())) {

				lower = (Integer.parseInt(index) -1) * upper;
				upper = Integer.parseInt(index) * upper;
			}
		  
		  String st; 
		  int i = 0;
		  boolean wasSent = false;
		  
			while ((st = br.readLine()) != null)  {
				if(i >= lower && i < upper) {
			    sender.sendMessage(st.replaceAll("&", "§"));
			    wasSent = true;
				}
			i++;
			}
			
			 br.close();
			 
			 if(!wasSent)
				 sender.sendMessage(getConfig().getString("invalidPageMessage").replaceAll("&", "§"));
		
			   
			
			
			
				
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		  
		
		
	}

}
