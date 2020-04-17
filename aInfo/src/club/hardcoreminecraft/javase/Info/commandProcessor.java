package club.hardcoreminecraft.javase.Info;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class commandProcessor implements Listener {

	/*
	 * This is some of the worst code I have ever written. This code will inject itself into the
	 * command preprocessor, and redirect the command /info into this command
	 * */
	
	@EventHandler
	public void onCommandProcess(PlayerCommandPreprocessEvent  event) {
		
		if(event.getMessage().toLowerCase().startsWith("/info")) {
			event.setCancelled(true);
			Bukkit.getServer().dispatchCommand(event.getPlayer(), event.getMessage().replace("/", "e"));
		}
		
	}
	
}
