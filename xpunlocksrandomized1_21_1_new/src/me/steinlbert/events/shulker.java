package me.steinlbert.events;



import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;

import me.steinlbert.main;

public class shulker implements Listener{
	main plugin = main.getPlugin(main.class);
	
	@EventHandler
	public void craft (CraftItemEvent e) 
	{
		
		int A6 = plugin.getConfig().getInt(e.getWhoClicked().getName()+".F");
		
		//ItemStack Struktur = new ItemStack(Material.STRUCTURE_BLOCK);
		Player player = (Player) e.getWhoClicked();
		if(!player.getGameMode().equals(GameMode.CREATIVE)) {
		if(player.getLevel() < A6) 
		{
			if(e.getCurrentItem().getType().equals(Material.SHULKER_BOX)||e.getCurrentItem().getType().equals(Material.PURPLE_SHULKER_BOX)) {
				
				if(!player.isOp()) {e.setCancelled(true); player.sendMessage(ChatColor.AQUA + "get more XP");}
				if(player.isOp())  {e.setCancelled(true); player.sendMessage(ChatColor.YELLOW + "get more XP or use /gamemode 1");}

				
				}
			}
		
		}
		
	}
	
	
}
