package me.steinlbert.events;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.material.MaterialData;

import me.steinlbert.main;

public class mining implements Listener {
	main plugin = main.getPlugin(main.class);
	@EventHandler
	public void braking (BlockBreakEvent e) {
		
		
		
		if (e.getBlock().getType().isBlock() == true) {
			
			

			
			Material material = e.getBlock().getType();
			Player p = e.getPlayer();
			
			int A1 = plugin.getConfig().getInt(p.getDisplayName()+".A");
			int A3 = plugin.getConfig().getInt(p.getDisplayName()+".C");
	
			
			
		
			
			if(!p.getGameMode().equals(GameMode.CREATIVE)) {
			if(p.getLevel() < A3) {

				if(material.equals(Material.DIAMOND_ORE)||material.equals(Material.COAL_ORE)||material.equals(Material.EMERALD_ORE)||material.equals(Material.LEGACY_GLOWING_REDSTONE_ORE)
					||material.equals(Material.GOLD_ORE)||material.equals(Material.IRON_ORE)||material.equals(Material.LAPIS_ORE)||material.equals(Material.LEGACY_QUARTZ_ORE)||material.equals(Material.COPPER_ORE)||material.equals(Material.LEGACY_REDSTONE_ORE)||material.equals(Material.DEEPSLATE_GOLD_ORE)||material.equals(Material.DEEPSLATE_COAL_ORE)||material.equals(Material.DEEPSLATE_COPPER_ORE)||material.equals(Material.DEEPSLATE_DIAMOND_ORE)||material.equals(Material.DEEPSLATE_EMERALD_ORE)||material.equals(Material.DEEPSLATE_GOLD_ORE)||material.equals(Material.DEEPSLATE_IRON_ORE)||material.equals(Material.DEEPSLATE_LAPIS_ORE)||material.equals(Material.DEEPSLATE_REDSTONE_ORE)||material.equals(Material.REDSTONE_ORE)||material.equals(Material.NETHER_GOLD_ORE)){
				e.setCancelled(true);
				if(!p.isOp()) {e.setCancelled(true); p.sendMessage(ChatColor.AQUA + "Not enough XP");}
				if(p.isOp())  {e.setCancelled(true); p.sendMessage(ChatColor.YELLOW + "get more XP or use /gamemode 1");}
				}
			}
			
			
			
			//stonecutter
			if(p.getLevel() < A1) {
				
				if(material.equals(Material.STONE)||material.equals(Material.COBBLESTONE)||material.equals(Material.MOSSY_COBBLESTONE)||material.equals(Material.COBBLESTONE_STAIRS)
						||material.equals(Material.GRANITE)||material.equals(Material.DIORITE)||material.equals(Material.DEEPSLATE)||material.equals(Material.CALCITE)||material.equals(Material.ANDESITE)||material.equals(Material.TUFF)) {
					
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
