package me.steinlbert.pl.minerim.jobs;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class braumeister implements Listener {
 // public braumeister(JavaPlugin plugin) {
   // plugin.getServer().getPluginManager().registerEvents(this, (Plugin)plugin);
  //}
  
  @EventHandler
  public void Brauen(InventoryClickEvent brau) {
    if (brau.isLeftClick()) {
      if (brau.getWhoClicked().getGameMode() != GameMode.CREATIVE && 
        brau.getWhoClicked().hasPermission("Minerim.Admin") && 
        brau.getInventory().getType() == InventoryType.BREWING) {
        brau.setCancelled(true);
        brau.getWhoClicked().sendMessage(ChatColor.GOLD + brau.getWhoClicked().getName() + ChatColor.WHITE + " /gamemode 1");
      } 
      if (brau.getInventory().getType() == InventoryType.BREWING && 
        !brau.getWhoClicked().hasPermission("minerim.Brauer")) {
        brau.setCancelled(true);
        brau.getWhoClicked().sendMessage("Du bist kein " + ChatColor.GOLD + "Braumeister!");
      } 
    } else if (brau.isRightClick()) {
      if (brau.getWhoClicked().getGameMode() != GameMode.CREATIVE && 
        brau.getWhoClicked().hasPermission("Minerim.Admin") && 
        brau.getInventory().getType() == InventoryType.BREWING) {
        brau.setCancelled(true);
        brau.getWhoClicked().sendMessage(ChatColor.GOLD + brau.getWhoClicked().getName() + ChatColor.WHITE + " /gamemode 1");
      } 
      if (brau.getInventory().getType() == InventoryType.BREWING && 
        !brau.getWhoClicked().hasPermission("minerim.Brauer")) {
        brau.setCancelled(true);
        brau.getWhoClicked().sendMessage("Du bist kein " + ChatColor.GOLD + "Braumeister!");
      } 
    } 
  }
}
