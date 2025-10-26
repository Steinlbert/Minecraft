package me.steinlbert.events;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;

import me.steinlbert.main;

public class star implements Listener {

	main plugin = main.getPlugin(main.class);
	@EventHandler
	public void craft (CraftItemEvent e) 
	{
		int A8 = plugin.getConfig().getInt(e.getWhoClicked().getName()+".G");
		//ItemStack Struktur = new ItemStack(Material.STRUCTURE_BLOCK);
		//player.setItemInHand(Struktur);
		Player player = (Player) e.getWhoClicked();
		
		if(!player.getGameMode().equals(GameMode.CREATIVE)) {
		
		if(player.getLevel() < A8) 
		{
			if(e.getCurrentItem().getType().equals(Material.BEACON)) {
				
				if(!player.isOp()) {e.setCancelled(true); player.sendMessage(ChatColor.AQUA + "get more XP");}
				if(player.isOp())  {e.setCancelled(true); player.sendMessage(ChatColor.YELLOW + "get more XP or use /gamemode 1");}

				
				}
			}
		
		}
		
	}
	
}
