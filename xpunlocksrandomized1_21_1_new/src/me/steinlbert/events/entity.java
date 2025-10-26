package me.steinlbert.events;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import me.steinlbert.main;

public class entity implements Listener{

	main plugin = main.getPlugin(main.class);
	
    

	
	
	@EventHandler
	public void deathevent (EntityDeathEvent d) {
		
		
		LivingEntity entity = d.getEntity();
		
		
		if(!(d.getEntity().getKiller() == null)) {
			
			if(d.getEntityType().isAlive()) {
				
				if(!entity.getType().equals(EntityType.PLAYER)) {
					
						Player spiela = d.getEntity().getKiller().getPlayer();
						
						int A1 = plugin.getConfig().getInt(spiela.getDisplayName()+".A");
						
						if (spiela.getPlayer().getLevel() < A1) {
							
							spiela.sendMessage(ChatColor.AQUA + "Not enough LVL for this skill");
							d.getDrops().clear();
							
							if(entity.getType().equals(EntityType.ZOMBIE)) {
								d.getDrops().add(new ItemStack(Material.ROTTEN_FLESH));	
								}
					}
						
						/*
						if(d.getEntityType() == EntityType.ZOMBIE_VILLAGER) {
							d.getDrops().add(new ItemStack(Material.CRAFTING_TABLE));
						}*/
				
					
					
				}
				
			
			
			
			
		}
			
			
			
		}
			
			
		
		//Player p = d.getEntity().getCustomName();
		//Player spiela = d.getEntity().getKiller().getPlayer();
		
		
		
		
		//if(d.getEntity().isDead(true))) {
			
			//if (d.getEntity().getKiller().getExpToLevel() < 15) {
				
			//	d.getDrops().clear();
				
			//}
			
			
		//}
		
		
		
		
		
	
	

	}


}
//	}
