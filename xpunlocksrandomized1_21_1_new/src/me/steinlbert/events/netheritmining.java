package me.steinlbert.events;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import me.steinlbert.main;

public class netheritmining implements Listener{

	main plugin = main.getPlugin(main.class);

	@EventHandler
public void braking (BlockBreakEvent e) {
		
		
		
		if (e.getBlock().getType().isBlock() == true) {
			
			

			
			Material material = e.getBlock().getType();
			Player p = e.getPlayer();
			
			int A7 = plugin.getConfig().getInt(p.getDisplayName()+".H");
			

			
			if(!p.getGameMode().equals(GameMode.CREATIVE)) {
			if(p.getLevel() < A7) {

				if(material.equals(Material.ANCIENT_DEBRIS)||(material.equals(Material.NETHERITE_BLOCK))){
				e.setCancelled(true);
				if(!p.isOp()) {e.setCancelled(true); p.sendMessage(ChatColor.AQUA + "Not enough XP");}
				if(p.isOp())  {e.setCancelled(true); p.sendMessage(ChatColor.YELLOW + "get more XP or use /gamemode 1");}
				}
			}
		}
			
			
			
		}
			
		//(Material.DIRT ||Material.TORCH||Material.BROWN_MUSHROOM||Material.LEAVES||Material.LEAVES_2)
		
			//if(Material == Material.DIRT||Material.TORCH||Material.BROWN_MUSHROOM||Material.LEAVES||Material.LEAVES_2)
				//= Material.DIRT ||Material.TORCH||Material.BROWN_MUSHROOM||Material.LEAVES||Material.LEAVES_2
				
					//{}
		
		
	}
	
	
	
	
	
	
	
	
	
}
