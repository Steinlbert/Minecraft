package me.steinlbert.events;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockFromToEvent;

import org.bukkit.event.player.PlayerInteractEvent;

import me.steinlbert.main;


public class farmer implements Listener{ //c0
	main plugin = main.getPlugin(main.class);


	
	 @EventHandler
	  public void Wasser(BlockFromToEvent b) {
	    if (b.getBlock().getType() == Material.BEETROOT)
	      b.setCancelled(true); 
	    if (b.getToBlock().getType() == Material.SUGAR_CANE)
	      b.setCancelled(true); 
	    if (b.getToBlock().getType() == Material.LEGACY_CROPS)
	      b.setCancelled(true); 
	    if (b.getToBlock().getType() == Material.POTATO)
	      b.setCancelled(true); 
	    if (b.getToBlock().getType() == Material.CARROT)
	      b.setCancelled(true); 
	  }
	
	 
	 @EventHandler
	 public void farming(BlockBreakEvent brack) {//c1
		 
		 int A2 = plugin.getConfig().getInt(brack.getPlayer().getDisplayName()+".B");
		 
		 
		 if(brack.getPlayer().getLevel() < A2 && !(brack.getPlayer().getGameMode().equals(GameMode.CREATIVE))) { //c2
			 
			 if(brack.getBlock().getType().equals(Material.LEGACY_SEEDS)||brack.getBlock().getType().equals(Material.POTATO)||brack.getBlock().getType().equals(Material.LEGACY_CROPS)||brack.getBlock().getType().equals(Material.CARROT)||
					 brack.getBlock().getType().equals(Material.BEETROOT)||brack.getBlock().getType().equals(Material.SUGAR_CANE)) 
			 {
				 
				 if(!brack.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {
					 if(!brack.getBlock().getDrops().isEmpty()) {
						 
						 brack.getPlayer().sendMessage("get more XP to get drop from farming!");
						 if (brack.getPlayer().isOp()) {brack.getPlayer().sendMessage(ChatColor.YELLOW + "get more XP or use /gamemode 1");} //abfrage ob der Spieler Admin ist
					 	
						 brack.setDropItems(false);
					 }

				 }
				 
				
			
			 }
			 
			 
		} //c2

		 
	} //c1
	 
	
	
	@EventHandler
	public void far (PlayerInteractEvent b) {
		int A2 = plugin.getConfig().getInt(b.getPlayer().getDisplayName()+".B");
		if(b.getPlayer().getLevel() < A2 && !(b.getPlayer().getGameMode().equals(GameMode.CREATIVE))) {			
			
			
		if(b.getPlayer().getItemInHand().getType().equals(Material.DIAMOND_HOE)||b.getPlayer().getItemInHand().getType().equals(Material.LEGACY_GOLD_HOE)||
				b.getPlayer().getItemInHand().getType().equals(Material.IRON_HOE)||b.getPlayer().getItemInHand().getType().equals(Material.STONE_HOE)||
				b.getPlayer().getItemInHand().getType().equals(Material.WOODEN_HOE)) {

			Action action = b.getAction();
			if(b.getAction().equals(Action.RIGHT_CLICK_BLOCK)) { //.RIGHT_CLICK_BLOCK == true
				b.getPlayer().sendMessage("you dont know ho to use that.. get more XP");
				if (b.getPlayer().isOp()) {b.getPlayer().sendMessage(ChatColor.YELLOW + "get more XP or use /gamemode 1");} //abfrage ob der Spieler Admin ist
				b.setCancelled(true);
				
				}
			}
		
		}
		
	}
}
