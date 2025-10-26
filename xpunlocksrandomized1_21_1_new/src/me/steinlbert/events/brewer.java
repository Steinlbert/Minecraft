package me.steinlbert.events;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;

import me.steinlbert.main;

	public class brewer implements Listener {

		private main plugin;

	    public brewer(main plugin) {
	        this.plugin = plugin;
	    }
		
	    
	    
	 
	    

	    
		
		  @EventHandler
		  public void Brauen(InventoryClickEvent brau) {
			  
			  
			  int A4 = plugin.getConfig().getInt(brau.getWhoClicked().getName()+".D");

			  Player p = (Player) brau.getWhoClicked();
			  //int lvl = p.getLevel();
			  //brau.getWhoClicked().sendMessage(brau.getWhoClicked().getName()+lvl);
			  
			  //plugin.getServer().getConsoleSender().sendMessage("trolloolo");
			  
		    if (brau.isLeftClick()) {
		      if (!brau.getWhoClicked().getGameMode().equals(GameMode.CREATIVE) && 
				brau.getWhoClicked().isOp() && brau.getInventory().getType() == InventoryType.BREWING && p.getLevel() < A4) {

		        brau.getWhoClicked().sendMessage(ChatColor.GOLD + brau.getWhoClicked().getName() + ChatColor.WHITE + " get xp or use /gamemode 1");
		        brau.setCancelled(true);
		        
		      } 
		      if (brau.getInventory().getType() == InventoryType.BREWING && 
		        p.getLevel() < A4 && !brau.getWhoClicked().isOp()) {
		        brau.setCancelled(true);
		        brau.getWhoClicked().sendMessage(ChatColor.AQUA + "Not enough XP");
		      } 
		    } else if (brau.isRightClick()) {
		      if (!brau.getWhoClicked().getGameMode().equals(GameMode.CREATIVE) && 
		        brau.getWhoClicked().isOp() && brau.getInventory().getType() == InventoryType.BREWING && p.getLevel() < A4) {
		    	brau.setCancelled(true);
		        brau.getWhoClicked().sendMessage(ChatColor.GOLD + brau.getWhoClicked().getName() + ChatColor.WHITE + " get xp or use /gamemode 1");
		        
		      } 
		      if (brau.getInventory().getType() == InventoryType.BREWING && 
		    		  p.getLevel() < A4) {
		        brau.setCancelled(true);
		        brau.getWhoClicked().sendMessage(ChatColor.AQUA + "Not enough XP");
		      } 
		    } 
		  }
		}


