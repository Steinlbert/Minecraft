package me.steinlbert.events;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class combatlock implements Listener {

	public static void Player (PlayerDeathEvent e){
		// TODO Auto-generated method stub
		
	}
	@EventHandler
	public static void Player (PlayerInteractEvent e){
		
		Entity Ent = e.getPlayer().getLastDamageCause().getEntity();
		String players = Bukkit.getServer().getOnlinePlayers().toString();
		
		
		if(e.getPlayer().getLastDamageCause().getEntity().equals("name="+"")) {
			
		}
		
		if(e.getPlayer() != null) {
			//(e.getPlayer().getLastDamageCause().getEntity().equals(EntityDamageByEntityEvent.getHandlerList().)){
			//}
			
			
			if(players.contains(e.getPlayer().getDisplayName())) {}
			e.getPlayer().sendMessage("Player who attacked you"+players);
			
		}

	}
	
}
