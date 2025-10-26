package me.steinlbert.events;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import me.steinlbert.main;

public class crafter implements Listener {
	main plugin = main.getPlugin(main.class);
	
	@EventHandler
	public void craft (CraftItemEvent e) 
	{
		
		//ItemStack Struktur = new ItemStack(Material.STRUCTURE_BLOCK);
		//player.setItemInHand(Struktur);
		Player p = (Player) e.getWhoClicked();
		int A1 = plugin.getConfig().getInt(p.getDisplayName()+".A");
		if(p.getLevel() < A1) //A1
		{
			
			if(e.getCurrentItem().getType().equals(Material.CRAFTING_TABLE)||e.getCurrentItem().getType().equals(Material.LEGACY_WORKBENCH)) {
				
				if(!p.isOp()) {e.setCancelled(true); p.sendMessage(ChatColor.AQUA + "find a workbench or get more XP");}
				if(p.isOp())  {e.setCancelled(true); p.sendMessage(ChatColor.YELLOW + "get more XP or use /gamemode 1");}

				
			}
		}else {}
		
	}
	
}
