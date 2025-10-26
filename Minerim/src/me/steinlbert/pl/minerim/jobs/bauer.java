package me.steinlbert.pl.minerim.jobs;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class bauer implements Listener {
  //public bauer(JavaPlugin plugin) {
   // plugin.getServer().getPluginManager().registerEvents(this, (Plugin)plugin);
  //}
  
  @EventHandler
  public void test(BlockBreakEvent b) {
    Block Gemuese = b.getBlock();
    Player pa = b.getPlayer();
    if (Gemuese.getType().equals(Material.MELON) && (
      !pa.hasPermission("Minerim.Bauer") || !pa.hasPermission("Minerim.Farmer"))) {
      b.setCancelled(true);
      pa.sendMessage("Da du kein Bauer weidu nicht wie das geht..");
    } 
    if (Gemuese.getType().equals(Material.PUMPKIN) && (
      !pa.hasPermission("Minerim.Bauer") || !pa.hasPermission("Minerim.Farmer"))) {
      b.setCancelled(true);
      pa.sendMessage("Da du kein Bauer bist weidu nicht wie das geht..");
    } 
    if (Gemuese.getType().equals(Material.CARROT) && (
      !pa.hasPermission("Minerim.Bauer") || !pa.hasPermission("Minerim.Farmer"))) {
      b.setCancelled(true);
      pa.sendMessage("Da du kein Bauer bist weidu nicht wie das geht..");
    } 
    if (Gemuese.getType().equals(Material.POTATO) && (
      !pa.hasPermission("Minerim.Bauer") || !pa.hasPermission("Minerim.Farmer"))) {
      b.setCancelled(true);
      pa.sendMessage("Da du kein Bauer bist weidu nicht wie das geht..");
    } 
    if (Gemuese.getType().equals(Material.CROPS) && (
      !pa.hasPermission("Minerim.Bauer") || !pa.hasPermission("Minerim.Farmer")) && 
      Gemuese.getData() == 7) {
      b.setCancelled(true);
      pa.sendMessage("Da du kein Bauer bist weidu nicht wie das geht..");
    } 
    if (Gemuese.getType().equals(Material.BEETROOT) && (
      !pa.hasPermission("Minerim.Bauer") || !pa.hasPermission("Minerim.Farmer"))) {
      b.setCancelled(true);
      pa.sendMessage("Da du kein Bauer bist weidu nicht wie das geht..");
    } 
    if (Gemuese.getType().equals(Material.SUGAR_CANE) && (
      !pa.hasPermission("Minerim.Bauer") || !pa.hasPermission("Minerim.Farmer"))) {
      b.setCancelled(true);
      pa.sendMessage("Da du kein Bauer bist weidu nicht wie das geht..");
    } 
  }
  
  @EventHandler
  public void Springen(PlayerInteractEvent event) {
    if (event.getAction() == Action.PHYSICAL && event.getClickedBlock().getType() == Material.SOIL)
      event.setCancelled(true); 
    if (event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getClickedBlock().getType() == Material.BEETROOT)
      event.setCancelled(true); 
    if (event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getClickedBlock().getType() == Material.SUGAR_CANE)
      event.setCancelled(true); 
    if (event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getClickedBlock().getType() == Material.CROPS && 
      event.getClickedBlock().getData() == 7)
      event.setCancelled(true); 
    if (event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getClickedBlock().getType() == Material.POTATO)
      event.setCancelled(true); 
    if (event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getClickedBlock().getType() == Material.CARROT)
      event.setCancelled(true); 
  }
  
  @EventHandler
  public void Wasser(BlockFromToEvent b) {
    if (b.getBlock().getType() == Material.BEETROOT)
      b.setCancelled(true); 
    if (b.getToBlock().getType() == Material.SUGAR_CANE)
      b.setCancelled(true); 
    if (b.getToBlock().getType() == Material.CROPS)
      b.setCancelled(true); 
    if (b.getToBlock().getType() == Material.POTATO)
      b.setCancelled(true); 
    if (b.getToBlock().getType() == Material.CARROT)
      b.setCancelled(true); 
  }
}
