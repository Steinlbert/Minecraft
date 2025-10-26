package me.steinlbert.events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;

import me.steinlbert.main;

public class smithing implements Listener {
	
	main plugin = main.getPlugin(main.class);
	
	
  @EventHandler
  public void Schmiedmeistern(CraftItemEvent craft) {
	  Player p = (Player) craft.getWhoClicked();
	  int A3 = plugin.getConfig().getInt(p.getDisplayName()+".C");
	  if(p.getLevel() < A3) { //lvl 5= 17 lvl 4 15 usw... lvl 20 = 62

		  
		  	//abfrag Admin & Creativmodus somst canceln
		    if (!craft.getWhoClicked().getGameMode().equals(GameMode.CREATIVE) && !craft.getWhoClicked().isOp()) 
		    	{    	
		    		if (craft.getCurrentItem().getType().equals(Material.IRON_CHESTPLATE)||craft.getCurrentItem().getType().equals(Material.IRON_AXE)
		    		  ||craft.getCurrentItem().getType().equals(Material.IRON_BOOTS)||craft.getCurrentItem().getType().equals(Material.IRON_HELMET)
		    		  ||craft.getCurrentItem().getType().equals(Material.IRON_INGOT)||craft.getCurrentItem().getType().equals(Material.IRON_LEGGINGS)
		    		  ||craft.getCurrentItem().getType().equals(Material.IRON_SWORD)||craft.getCurrentItem().getType().equals(Material.IRON_HORSE_ARMOR)
		    		  ||craft.getCurrentItem().getType().equals(Material.IRON_BLOCK)||craft.getCurrentItem().getType().equals(Material.IRON_TRAPDOOR)
		    		  ||craft.getCurrentItem().getType().equals(Material.IRON_TRAPDOOR)||craft.getCurrentItem().getType().equals(Material.IRON_PICKAXE)
		    		  ||craft.getCurrentItem().getType().equals(Material.IRON_HOE)||craft.getCurrentItem().getType().equals(Material.DIAMOND_CHESTPLATE)
		    		  ||craft.getCurrentItem().getType().equals(Material.DIAMOND_AXE)||craft.getCurrentItem().getType().equals(Material.DIAMOND_BOOTS)
		    		  ||craft.getCurrentItem().getType().equals(Material.DIAMOND_HELMET)||craft.getCurrentItem().getType().equals(Material.DIAMOND_LEGGINGS)
		    		  ||craft.getCurrentItem().getType().equals(Material.DIAMOND_SWORD)||craft.getCurrentItem().getType().equals(Material.DIAMOND_HORSE_ARMOR)
		    		  ||craft.getCurrentItem().getType().equals(Material.DIAMOND_BLOCK)||craft.getCurrentItem().getType().equals(Material.DIAMOND)
		    		  ||craft.getCurrentItem().getType().equals(Material.LEGACY_DIAMOND_SPADE)||craft.getCurrentItem().getType().equals(Material.DIAMOND_PICKAXE)
		    		  ||craft.getCurrentItem().getType().equals(Material.DIAMOND_HOE)||craft.getCurrentItem().getType().equals(Material.GOLDEN_AXE)
		    		  ||craft.getCurrentItem().getType().equals(Material.GOLDEN_HORSE_ARMOR)||craft.getCurrentItem().getType().equals(Material.GOLD_BLOCK)
		    		  ||craft.getCurrentItem().getType().equals(Material.GOLD_ORE)||craft.getCurrentItem().getType().equals(Material.GOLDEN_CHESTPLATE)
		    		  ||craft.getCurrentItem().getType().equals(Material.GOLDEN_HELMET)||craft.getCurrentItem().getType().equals(Material.GOLDEN_HOE)
		    		  ||craft.getCurrentItem().getType().equals(Material.GOLD_INGOT)||craft.getCurrentItem().getType().equals(Material.GOLDEN_LEGGINGS)
		    		  ||craft.getCurrentItem().getType().equals(Material.GOLD_NUGGET)||craft.getCurrentItem().getType().equals(Material.GOLD_ORE)
		    		  ||craft.getCurrentItem().getType().equals(Material.GOLDEN_PICKAXE)||craft.getCurrentItem().getType().equals(Material.LEGACY_GOLD_PLATE)
		    		  ||craft.getCurrentItem().getType().equals(Material.LEGACY_GOLD_RECORD)||craft.getCurrentItem().getType().equals(Material.LEGACY_GOLD_SPADE)
		    		  ||craft.getCurrentItem().getType().equals(Material.NETHERITE_SWORD)||craft.getCurrentItem().getType().equals(Material.NETHERITE_BOOTS)
		    		  ||craft.getCurrentItem().getType().equals(Material.NETHERITE_AXE)||craft.getCurrentItem().getType().equals(Material.NETHERITE_CHESTPLATE)
		    		  ||craft.getCurrentItem().getType().equals(Material.NETHERITE_HELMET)||craft.getCurrentItem().getType().equals(Material.NETHERITE_HOE)
		    		  ||craft.getCurrentItem().getType().equals(Material.NETHERITE_LEGGINGS)||craft.getCurrentItem().getType().equals(Material.NETHERITE_PICKAXE)
		    		  ||craft.getCurrentItem().getType().equals(Material.NETHERITE_SHOVEL)||craft.getCurrentItem().getType().equals(Material.SHIELD))
		      			{
		    			craft.setCancelled(true);;
		    	  		craft.getWhoClicked().sendMessage(ChatColor.AQUA + "Not enough XP");
		    	  		
		    	  		
		      			} 
		
		    	}  
		    
		    		//abfrag Admin & Creativmodus somst canceln
		    		if(craft.getWhoClicked().isOp()){ 	    			
		    			if(!craft.getWhoClicked().getGameMode().equals(GameMode.CREATIVE)) {
		    				if (craft.getCurrentItem().getType().equals(Material.IRON_CHESTPLATE)||craft.getCurrentItem().getType().equals(Material.IRON_AXE)
		    			    		  ||craft.getCurrentItem().getType().equals(Material.IRON_BOOTS)||craft.getCurrentItem().getType().equals(Material.IRON_HELMET)
		    			    		  ||craft.getCurrentItem().getType().equals(Material.IRON_INGOT)||craft.getCurrentItem().getType().equals(Material.IRON_LEGGINGS)
		    			    		  ||craft.getCurrentItem().getType().equals(Material.IRON_SWORD)||craft.getCurrentItem().getType().equals(Material.IRON_HORSE_ARMOR)
		    			    		  ||craft.getCurrentItem().getType().equals(Material.IRON_BLOCK)||craft.getCurrentItem().getType().equals(Material.IRON_TRAPDOOR)
		    			    		  ||craft.getCurrentItem().getType().equals(Material.IRON_TRAPDOOR)||craft.getCurrentItem().getType().equals(Material.IRON_PICKAXE)
		    			    		  ||craft.getCurrentItem().getType().equals(Material.IRON_HOE)||craft.getCurrentItem().getType().equals(Material.DIAMOND_CHESTPLATE)
		    			    		  ||craft.getCurrentItem().getType().equals(Material.DIAMOND_AXE)||craft.getCurrentItem().getType().equals(Material.DIAMOND_BOOTS)
		    			    		  ||craft.getCurrentItem().getType().equals(Material.DIAMOND_HELMET)||craft.getCurrentItem().getType().equals(Material.DIAMOND_LEGGINGS)
		    			    		  ||craft.getCurrentItem().getType().equals(Material.DIAMOND_SWORD)||craft.getCurrentItem().getType().equals(Material.DIAMOND_HORSE_ARMOR)
		    			    		  ||craft.getCurrentItem().getType().equals(Material.DIAMOND_BLOCK)||craft.getCurrentItem().getType().equals(Material.DIAMOND)
		    			    		  ||craft.getCurrentItem().getType().equals(Material.LEGACY_DIAMOND_SPADE)||craft.getCurrentItem().getType().equals(Material.DIAMOND_PICKAXE)
		    			    		  ||craft.getCurrentItem().getType().equals(Material.DIAMOND_HOE)||craft.getCurrentItem().getType().equals(Material.GOLDEN_AXE)
		    			    		  ||craft.getCurrentItem().getType().equals(Material.GOLDEN_HORSE_ARMOR)||craft.getCurrentItem().getType().equals(Material.GOLD_BLOCK)
		    			    		  ||craft.getCurrentItem().getType().equals(Material.GOLD_ORE)||craft.getCurrentItem().getType().equals(Material.GOLDEN_CHESTPLATE)
		    			    		  ||craft.getCurrentItem().getType().equals(Material.GOLDEN_HELMET)||craft.getCurrentItem().getType().equals(Material.GOLDEN_HOE)
		    			    		  ||craft.getCurrentItem().getType().equals(Material.GOLD_INGOT)||craft.getCurrentItem().getType().equals(Material.GOLDEN_LEGGINGS)
		    			    		  ||craft.getCurrentItem().getType().equals(Material.GOLD_NUGGET)||craft.getCurrentItem().getType().equals(Material.GOLD_ORE)
		    			    		  ||craft.getCurrentItem().getType().equals(Material.GOLDEN_PICKAXE)||craft.getCurrentItem().getType().equals(Material.LEGACY_GOLD_PLATE)
		    			    		  ||craft.getCurrentItem().getType().equals(Material.LEGACY_GOLD_RECORD)||craft.getCurrentItem().getType().equals(Material.LEGACY_GOLD_SPADE)
		    			    		  ||craft.getCurrentItem().getType().equals(Material.NETHERITE_SWORD)||craft.getCurrentItem().getType().equals(Material.NETHERITE_BOOTS)
		    			    		  ||craft.getCurrentItem().getType().equals(Material.NETHERITE_AXE)||craft.getCurrentItem().getType().equals(Material.NETHERITE_CHESTPLATE)
		    			    		  ||craft.getCurrentItem().getType().equals(Material.NETHERITE_HELMET)||craft.getCurrentItem().getType().equals(Material.NETHERITE_HOE)
		    			    		  ||craft.getCurrentItem().getType().equals(Material.NETHERITE_LEGGINGS)||craft.getCurrentItem().getType().equals(Material.NETHERITE_PICKAXE)
		    			    		  ||craft.getCurrentItem().getType().equals(Material.NETHERITE_SHOVEL)||craft.getCurrentItem().getType().equals(Material.SHIELD))
		    			      			{
		    			    			craft.setCancelled(true);;
		    			    			craft.getWhoClicked().sendMessage(ChatColor.AQUA + "Not enough XP " + ChatColor.YELLOW + "Admins can use /gamemode 1");
		    			    	  		
		    			    	  		
		    			      			} 
		    				
		    				
		    				
		    				
				    		
		    			}
		    		
		    		}
    
	  } 
    
    
  }
    
    
  @EventHandler
  public void Schmiedtisch(InventoryClickEvent tischle) {
	    Player p2 = Bukkit.getPlayer(tischle.getWhoClicked().getName());
		  int A3spare1 = plugin.getConfig().getInt(p2.getDisplayName()+".C");

	    if(tischle.isLeftClick()) {
	    	if(tischle.getInventory().getType() == InventoryType.SMITHING) {
	    		if (p2.getLevel() < A3spare1) {        	  
	          	  if (!p2.getGameMode().equals(GameMode.CREATIVE)) {
	          		  tischle.setCancelled(true);
	                    p2.sendMessage(ChatColor.AQUA + "Not enough XP");
	                  }  
	   
	            } 
	    	}
	    	
	    	
	    }
	    
	    if (tischle.isRightClick()) {
	        if (tischle.getInventory().getType() == InventoryType.SMITHING) {
	          if (p2.getGameMode() != GameMode.CREATIVE)
	            if (p2.getLevel() < A3spare1) {
	              tischle.setCancelled(true);
	              p2.sendMessage(ChatColor.AQUA + "Not enough XP");      
	            } 
	        } 
	      } 
	  
  }
  
  
  
  //überlegen ob Amboss erlaubt ist oder nicht....
  /*
  
  @EventHandler
  public void Schmieden(InventoryClickEvent Amboss) {
	  
    Player p = Bukkit.getPlayer(Amboss.getWhoClicked().getName());
    if (Amboss.isLeftClick()) {
      if (Amboss.getInventory().getType() == InventoryType.ANVIL) {
          if (Amboss.getWhoClicked().getExpToLevel() < 62) {        	  
        	  if (!p.getGameMode().equals(GameMode.CREATIVE)) {
        		  Amboss.setCancelled(true);
                  p.sendMessage(ChatColor.AQUA + "Not enough XP");
                }  
 
          } 
      } 
    } 
    
    if (Amboss.isRightClick()) {
      if (Amboss.getInventory().getType() == InventoryType.ANVIL) {
        if (p.getGameMode() != GameMode.CREATIVE)
          if (Amboss.getWhoClicked().getExpToLevel() < 62) {
            Amboss.setCancelled(true);
            p.sendMessage(ChatColor.AQUA + "Not enough XP");      
          } 
      } 
    } 
  }*/
  
  
}
