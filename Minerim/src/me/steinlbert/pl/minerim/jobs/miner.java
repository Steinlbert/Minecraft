package me.steinlbert.pl.minerim.jobs;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class miner implements Listener {
 // public miner(JavaPlugin plugin) {
   // plugin.getServer().getPluginManager().registerEvents(this, (Plugin)plugin);
//  }
  
  @EventHandler
  public void onBreak(BlockBreakEvent event) {
	  
    Block block = event.getBlock();
    Player p = event.getPlayer();
    if (block.getType().equals(Material.DIAMOND_ORE))
      if (!p.hasPermission("Minerim.Miner")) {
        event.setCancelled(true);
        p.sendMessage(ChatColor.GOLD + "Du kannst" + ChatColor.AQUA + " Diamant" + ChatColor.GREEN + " nicht abbauen");
      } else {
        event.setCancelled(false);
      }  
    if (block.getType().equals(Material.IRON_ORE))
      if (!p.hasPermission("Minerim.Miner")) {
        event.setCancelled(true);
        p.sendMessage(ChatColor.GOLD + "Du kannst" + ChatColor.AQUA + " Eisen" + ChatColor.GREEN + " nicht abbauen");
      } else {
        event.setCancelled(false);
      }  
    if (block.getType().equals(Material.GOLD_ORE))
      if (!p.hasPermission("Minerim.Miner")) {
        event.setCancelled(true);
        p.sendMessage(ChatColor.GOLD + "Du kannst" + ChatColor.AQUA + " Gold" + ChatColor.GREEN + " nicht abbauen");
      } else {
        event.setCancelled(false);
      }  
    if (block.getType().equals(Material.EMERALD_ORE))
      if (!p.hasPermission("Minerim.Miner")) {
        event.setCancelled(true);
        p.sendMessage(ChatColor.GOLD + "Du kannst" + ChatColor.AQUA + " Smaragde" + ChatColor.GREEN + " nicht abbauen");
      } else {
        event.setCancelled(false);
      }  
    if (block.getType().equals(Material.REDSTONE_ORE))
      if (!p.hasPermission("Minerim.Miner")) {
        event.setCancelled(true);
        p.sendMessage(ChatColor.GOLD + "Du kannst" + ChatColor.AQUA + " Redstone" + ChatColor.GREEN + " nicht abbauen");
      } else {
        event.setCancelled(false);
      }  
    if (block.getType().equals(Material.LAPIS_ORE))
      if (!p.hasPermission("Minerim.Miner")) {
        event.setCancelled(true);
        p.sendMessage(ChatColor.GOLD + "Du kannst" + ChatColor.AQUA + " Lapislazuli" + ChatColor.GREEN + " nicht abbauen");
      } else {
        event.setCancelled(false);
      }  
    if (block.getType().equals(Material.COAL_ORE))
      if (!p.hasPermission("Minerim.Miner")) {
        event.setCancelled(true);
        p.sendMessage(ChatColor.GOLD + "Du kannst" + ChatColor.AQUA + " Kohle" + ChatColor.GREEN + " nicht abbauen");
      } else {
        event.setCancelled(false);
      }  
    if (block.getType().equals(Material.QUARTZ_ORE))
      if (!p.hasPermission("Minerim.Miner")) {
        event.setCancelled(true);
        p.sendMessage(ChatColor.GOLD + "Du kannst" + ChatColor.AQUA + " Kohle" + ChatColor.GREEN + " nicht abbauen");
      } else {
        event.setCancelled(false);
      }  
  }
  
  @EventHandler
  public void onPlace(BlockPlaceEvent event) {
    Block block = event.getBlock();
    Player p = event.getPlayer();
    if (block.getType().equals(Material.DIAMOND_ORE))
      if (!p.hasPermission("Minerim.Miner")) {
        event.setCancelled(true);
        p.sendMessage(ChatColor.GOLD + "Du kannst" + ChatColor.AQUA + " Diamant" + ChatColor.GREEN + " nicht...");
      } else {
        event.setCancelled(false);
      }  
    if (block.getType().equals(Material.IRON_ORE))
      if (!p.hasPermission("Minerim.Miner")) {
        event.setCancelled(true);
        p.sendMessage(ChatColor.GOLD + "Du kannst" + ChatColor.AQUA + " Eisen" + ChatColor.GREEN + " nicht Platzieren");
      } else {
        event.setCancelled(false);
      }  
    if (block.getType().equals(Material.GOLD_ORE))
      if (!p.hasPermission("Minerim.Miner")) {
        event.setCancelled(true);
        p.sendMessage(ChatColor.GOLD + "Du kannst" + ChatColor.AQUA + " Gold" + ChatColor.GREEN + " nicht Platzieren");
      } else {
        event.setCancelled(false);
      }  
    if (block.getType().equals(Material.EMERALD_ORE))
      if (!p.hasPermission("Minerim.Miner")) {
        event.setCancelled(true);
        p.sendMessage(ChatColor.GOLD + "Du kannst" + ChatColor.AQUA + " Smaragde" + ChatColor.GREEN + " nicht Platzieren");
      } else {
        event.setCancelled(false);
      }  
    if (block.getType().equals(Material.REDSTONE_ORE))
      if (!p.hasPermission("Minerim.Miner")) {
        event.setCancelled(true);
        p.sendMessage(ChatColor.GOLD + "Du kannst" + ChatColor.AQUA + " Redstone" + ChatColor.GREEN + " nicht Platzieren");
      } else {
        event.setCancelled(false);
      }  
    if (block.getType().equals(Material.LAPIS_ORE))
      if (!p.hasPermission("Minerim.Miner")) {
        event.setCancelled(true);
        p.sendMessage(ChatColor.GOLD + "Du kannst" + ChatColor.AQUA + " Lapislazuli" + ChatColor.GREEN + " nicht Platzieren");
      } else {
        event.setCancelled(false);
      }  
    if (block.getType().equals(Material.COAL_ORE))
      if (!p.hasPermission("Minerim.Miner")) {
        event.setCancelled(true);
        p.sendMessage(ChatColor.GOLD + "Du kannst" + ChatColor.AQUA + " Kohle" + ChatColor.GREEN + " nicht Platzieren");
      } else {
        event.setCancelled(false);
      }  
    if (block.getType().equals(Material.QUARTZ_ORE))
      if (!p.hasPermission("Minerim.Miner")) {
        event.setCancelled(true);
        p.sendMessage(ChatColor.GOLD + "Du kannst" + ChatColor.AQUA + " Kohle" + ChatColor.GREEN + " nicht Platzieren");
      } else {
        event.setCancelled(false);
      }  
  }
}
