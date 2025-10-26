package me.steinlbert.events;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;

import me.steinlbert.main;

public class wizzard implements Listener {
	main plugin = main.getPlugin(main.class);
	  @EventHandler
	  public void Verzaubern(InventoryClickEvent wiz) {
		  
		  Player p = (Player) wiz.getWhoClicked();
		  
		  int A5 = plugin.getConfig().getInt(wiz.getWhoClicked().getName()+".E");
	    if (wiz.isLeftClick()) {
	      if (wiz.getWhoClicked().getGameMode() != GameMode.CREATIVE && 
	        wiz.getInventory().getType() == InventoryType.ENCHANTING) {
		      if (p.getLevel() < A5) {
					if(!p.isOp()) {wiz.setCancelled(true); p.sendMessage(ChatColor.AQUA + "Not enough XP");}
					if(p.isOp())  {wiz.setCancelled(true); p.sendMessage(ChatColor.YELLOW + "get more XP or use /gamemode 1");}
		      }

	      } 
	    }
	    
	    if (wiz.isRightClick() && 
	      wiz.getWhoClicked().getGameMode() != GameMode.CREATIVE && 
	      wiz.getInventory().getType() == InventoryType.ENCHANTING) {
	      if (p.getLevel() < A5) {
				if(!p.isOp()) {wiz.setCancelled(true); p.sendMessage(ChatColor.AQUA + "Not enough XP");}
				if(p.isOp())  {wiz.setCancelled(true); p.sendMessage(ChatColor.YELLOW + "get more XP or use /gamemode 1");}
	      }
	       

	    } 
	  }
	}