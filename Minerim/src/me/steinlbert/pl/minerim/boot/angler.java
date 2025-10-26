package me.steinlbert.pl.minerim.boot;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class angler implements Listener {
	

	
	
  
  @EventHandler(priority = EventPriority.HIGHEST)
  public void onpInteract(PlayerFishEvent a) {
    Player p = a.getPlayer();
    if (a.getState() == PlayerFishEvent.State.FISHING && 
      !p.hasPermission("Minerim.Angler"))
    	p.sendMessage("KOa FIscher heascht");
      a.setCancelled(true); 
  }
}
