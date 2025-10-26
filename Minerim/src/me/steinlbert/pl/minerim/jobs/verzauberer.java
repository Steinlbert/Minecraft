package me.steinlbert.pl.minerim.jobs;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class verzauberer implements Listener {
  
  @EventHandler
  public void Verzaubern(InventoryClickEvent wiz) {
    if (wiz.isLeftClick()) {
      if (wiz.getWhoClicked().getGameMode() != GameMode.CREATIVE && 
        wiz.getInventory().getType() == InventoryType.ENCHANTING) {
        if (!wiz.getWhoClicked().hasPermission("Minerim.Verzauberer"))
          wiz.setCancelled(true); 
        if (wiz.getWhoClicked().hasPermission("Minerim.Admin")) {
          wiz.getWhoClicked().sendMessage(ChatColor.GOLD + wiz.getWhoClicked().getName() + ChatColor.WHITE + " /gamemode 1");
          wiz.setCancelled(true);
        } 
      } 
    } else if (wiz.isRightClick() && 
      wiz.getWhoClicked().getGameMode() != GameMode.CREATIVE && 
      wiz.getInventory().getType() == InventoryType.ENCHANTING) {
      if (!wiz.getWhoClicked().hasPermission("Minerim.Verzauberer"))
        wiz.setCancelled(true); 
      if (wiz.getWhoClicked().hasPermission("Minerim.Admin")) {
        wiz.getWhoClicked().sendMessage(ChatColor.GOLD + wiz.getWhoClicked().getName() + ChatColor.WHITE + " /gamemode 1");
        wiz.setCancelled(true);
      } 
    } 
  }
}
