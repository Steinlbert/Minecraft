package me.steinlbert.pl.minerim.jobs;

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
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class schmied implements Listener {

  @EventHandler
  public void Schmiedmeistern(CraftItemEvent craft) {
    if (!craft.getWhoClicked().hasPermission("Minerim.Schmied")) {
      if (craft.getCurrentItem().getType().equals(Material.IRON_CHESTPLATE)) {
        craft.getWhoClicked().sendMessage("Du kannst das nicht!");
        craft.setCurrentItem(null);
      } 
      if (craft.getCurrentItem().getType().equals(Material.IRON_AXE)) {
        craft.getWhoClicked().sendMessage("Du kannst das nicht!");
        craft.setCurrentItem(null);
      } 
      if (craft.getCurrentItem().getType().equals(Material.IRON_BOOTS)) {
        craft.getWhoClicked().sendMessage("Du kannst das nicht!");
        craft.setCurrentItem(null);
      } 
      if (craft.getCurrentItem().getType().equals(Material.IRON_HELMET)) {
        craft.getWhoClicked().sendMessage("Du kannst das nicht!");
        craft.setCurrentItem(null);
      } 
      if (craft.getCurrentItem().getType().equals(Material.IRON_INGOT)) {
        craft.getWhoClicked().sendMessage("Du kannst das nicht!");
        craft.setCurrentItem(null);
      } 
      if (craft.getCurrentItem().getType().equals(Material.IRON_LEGGINGS)) {
        craft.getWhoClicked().sendMessage("Du kannst das nicht!");
        craft.setCurrentItem(null);
      } 
      if (craft.getCurrentItem().getType().equals(Material.IRON_SWORD)) {
        craft.getWhoClicked().sendMessage("Du kannst das nicht!");
        craft.setCurrentItem(null);
      } 
      if (craft.getCurrentItem().getType().equals(Material.IRON_BARDING)) {
        craft.getWhoClicked().sendMessage("Du kannst das nicht!");
        craft.setCurrentItem(null);
      } 
      if (craft.getCurrentItem().getType().equals(Material.IRON_BLOCK)) {
        craft.getWhoClicked().sendMessage("Du kannst das nicht!");
        craft.setCurrentItem(null);
      } 
      if (craft.getCurrentItem().getType().equals(Material.IRON_TRAPDOOR)) {
        craft.getWhoClicked().sendMessage("Du kannst das nicht!");
        craft.setCurrentItem(null);
      } 
      if (craft.getCurrentItem().getType().equals(Material.IRON_TRAPDOOR)) {
        craft.getWhoClicked().sendMessage("Du kannst das nicht!");
        craft.setCurrentItem(null);
      } 
      if (craft.getCurrentItem().getType().equals(Material.IRON_PICKAXE)) {
        craft.getWhoClicked().sendMessage("Du kannst das nicht!");
        craft.setCurrentItem(null);
      } 
      if (craft.getCurrentItem().getType().equals(Material.IRON_HOE)) {
        craft.getWhoClicked().sendMessage("Du kannst das nicht!");
        craft.setCurrentItem(null);
      } 
      if (craft.getCurrentItem().getType().equals(Material.DIAMOND_CHESTPLATE)) {
        craft.getWhoClicked().sendMessage("Du kannst das nicht!");
        craft.setCurrentItem(null);
      } 
      if (craft.getCurrentItem().getType().equals(Material.DIAMOND_AXE)) {
        craft.getWhoClicked().sendMessage("Du kannst das nicht!");
        craft.setCurrentItem(null);
      } 
      if (craft.getCurrentItem().getType().equals(Material.DIAMOND_BOOTS)) {
        craft.getWhoClicked().sendMessage("Du kannst das nicht!");
        craft.setCurrentItem(null);
      } 
      if (craft.getCurrentItem().getType().equals(Material.DIAMOND_HELMET)) {
        craft.getWhoClicked().sendMessage("Du kannst das nicht!");
        craft.setCurrentItem(null);
      } 
      if (craft.getCurrentItem().getType().equals(Material.DIAMOND_LEGGINGS)) {
        craft.getWhoClicked().sendMessage("Du kannst das nicht!");
        craft.setCurrentItem(null);
      } 
      if (craft.getCurrentItem().getType().equals(Material.DIAMOND_SWORD)) {
        craft.getWhoClicked().sendMessage("Du kannst das nicht!");
        craft.setCurrentItem(null);
      } 
      if (craft.getCurrentItem().getType().equals(Material.DIAMOND_BARDING)) {
        craft.getWhoClicked().sendMessage("Du kannst das nicht!");
        craft.setCurrentItem(null);
      } 
      if (craft.getCurrentItem().getType().equals(Material.DIAMOND_BLOCK)) {
        craft.getWhoClicked().sendMessage("Du kannst das nicht!");
        craft.setCurrentItem(null);
      } 
      if (craft.getCurrentItem().getType().equals(Material.DIAMOND)) {
        craft.getWhoClicked().sendMessage("Du kannst das nicht!");
        craft.setCurrentItem(null);
      } 
      if (craft.getCurrentItem().getType().equals(Material.DIAMOND_SPADE)) {
        craft.getWhoClicked().sendMessage("Du kannst das nicht!");
        craft.setCurrentItem(null);
      } 
      if (craft.getCurrentItem().getType().equals(Material.DIAMOND_PICKAXE)) {
        craft.getWhoClicked().sendMessage("Du kannst das nicht!");
        craft.setCurrentItem(null);
      } 
      if (craft.getCurrentItem().getType().equals(Material.DIAMOND_HOE)) {
        craft.getWhoClicked().sendMessage("Du kannst das nicht!");
        craft.setCurrentItem(null);
      } 
      if (!craft.getWhoClicked().hasPermission("Minerim.Schmied")) {
        if (craft.getCurrentItem().getType().equals(Material.GOLD_AXE)) {
          craft.getWhoClicked().sendMessage("Du kannst das nicht!");
          craft.setCurrentItem(null);
        } 
        if (craft.getCurrentItem().getType().equals(Material.GOLD_BARDING)) {
          craft.getWhoClicked().sendMessage("Du kannst das nicht!");
          craft.setCurrentItem(null);
        } 
        if (craft.getCurrentItem().getType().equals(Material.GOLD_BLOCK)) {
          craft.getWhoClicked().sendMessage("Du kannst das nicht!");
          craft.setCurrentItem(null);
        } 
        if (craft.getCurrentItem().getType().equals(Material.GOLD_BOOTS)) {
          craft.getWhoClicked().sendMessage("Du kannst das nicht!");
          craft.setCurrentItem(null);
        } 
        if (craft.getCurrentItem().getType().equals(Material.GOLD_CHESTPLATE)) {
          craft.getWhoClicked().sendMessage("Du kannst das nicht!");
          craft.setCurrentItem(null);
        } 
        if (craft.getCurrentItem().getType().equals(Material.GOLD_HELMET)) {
          craft.getWhoClicked().sendMessage("Du kannst das nicht!");
          craft.setCurrentItem(null);
        } 
        if (craft.getCurrentItem().getType().equals(Material.GOLD_HOE)) {
          craft.getWhoClicked().sendMessage("Du kannst das nicht!");
          craft.setCurrentItem(null);
        } 
        if (craft.getCurrentItem().getType().equals(Material.GOLD_INGOT)) {
          craft.getWhoClicked().sendMessage("Du kannst das nicht!");
          craft.setCurrentItem(null);
        } 
        if (craft.getCurrentItem().getType().equals(Material.GOLD_LEGGINGS)) {
          craft.getWhoClicked().sendMessage("Du kannst das nicht!");
          craft.setCurrentItem(null);
        } 
        if (craft.getCurrentItem().getType().equals(Material.GOLD_NUGGET)) {
          craft.getWhoClicked().sendMessage("Du kannst das nicht!");
          craft.setCurrentItem(null);
        } 
        if (craft.getCurrentItem().getType().equals(Material.GOLD_ORE)) {
          craft.getWhoClicked().sendMessage("Du kannst das nicht!");
          craft.setCurrentItem(null);
        } 
        if (craft.getCurrentItem().getType().equals(Material.GOLD_PICKAXE)) {
          craft.getWhoClicked().sendMessage("Du kannst das nicht!");
          craft.setCurrentItem(null);
        } 
        if (craft.getCurrentItem().getType().equals(Material.GOLD_PLATE)) {
          craft.getWhoClicked().sendMessage("Du kannst das nicht!");
          craft.setCurrentItem(null);
        } 
        if (craft.getCurrentItem().getType().equals(Material.GOLD_RECORD)) {
          craft.getWhoClicked().sendMessage("Du kannst das nicht!");
          craft.setCurrentItem(null);
        } 
        if (craft.getCurrentItem().getType().equals(Material.GOLD_SPADE)) {
          craft.getWhoClicked().sendMessage("Du kannst das nicht!");
          craft.setCurrentItem(null);
        } 
        if (craft.getCurrentItem().getType().equals(Material.GOLD_SPADE)) {
          craft.getWhoClicked().sendMessage("Du kannst das nicht!");
          craft.setCurrentItem(null);
        } 
      } 
    } 
  }
  
  @EventHandler
  public void Schmieden(InventoryClickEvent Amboss) {
    Player p = Bukkit.getPlayer(Amboss.getWhoClicked().getName());
    if (Amboss.isLeftClick()) {
      p.getGameMode();
      if (Amboss.getInventory().getType() == InventoryType.ANVIL) {
        p.getGameMode();
        if (p.getGameMode() != GameMode.CREATIVE)
          if (!Amboss.getWhoClicked().hasPermission("Minerim.Schmied")) {
            Amboss.setCancelled(true);
          } else if (p.hasPermission("Minerim.Admin")) {
            Amboss.setCancelled(true);
            p.sendMessage(ChatColor.GOLD + p.getDisplayName() + ChatColor.WHITE + " /gamemode 1");
          }  
      } 
    } 
    if (Amboss.isRightClick()) {
      p.getGameMode();
      if (Amboss.getInventory().getType() == InventoryType.ANVIL) {
        p.getGameMode();
        if (p.getGameMode() != GameMode.CREATIVE)
          if (!Amboss.getWhoClicked().hasPermission("Minerim.Schmied")) {
            Amboss.setCancelled(true);
          } else if (p.hasPermission("Minerim.Admin")) {
            Amboss.setCancelled(true);
            p.sendMessage(ChatColor.GOLD + p.getDisplayName() + ChatColor.WHITE + " /gamemode 1");
          }  
      } 
    } 
  }
}
