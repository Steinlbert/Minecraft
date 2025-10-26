package me.steinlbert.pl.minerim.boot;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import me.steinlbert.pl.minerim.main;

public class OnJoin implements Listener {
 //public main plugin;
  
// public OnJoin(main instance) {
//  this.plugin = instance;
 // }
  
  @EventHandler
  public void OnJoinevent(PlayerJoinEvent e) {
    for (Player all : Bukkit.getOnlinePlayers()) {
      final Player player = e.getPlayer();
      if (!e.getPlayer().hasPlayedBefore()) {
        player.sendMessage(ChatColor.GOLD + "Willkomen in Minerim " + ChatColor.GREEN + "Reisender");
        player.sendMessage(ChatColor.BLUE + "Ein Projekt von Sheapsed");
        final Inventory inv = Bukkit.getServer().createInventory(null, 9, "Zum Starten auf den Block -> klicken");
        inv.setItem(1, new ItemStack(Material.AIR, 1));
        inv.setItem(2, new ItemStack(Material.AIR, 1));
        inv.setItem(3, new ItemStack(Material.AIR, 1));
        inv.setItem(4, new ItemStack(Material.AIR, 1));
        inv.setItem(5, new ItemStack(Material.AIR, 1));
        inv.setItem(6, new ItemStack(Material.AIR, 1));
        inv.setItem(7, new ItemStack(Material.AIR, 1));
        inv.setItem(8, new ItemStack(Material.AIR, 1));
               if (player != null && player.isOnline())     
               {

               }

        e.getPlayer().setDisplayName(ChatColor.WHITE + "Ein unbekannter Reisender");
        //continue;
      } 
      if (e.getPlayer().hasPlayedBefore()) {
        player.sendMessage(ChatColor.BLUE + "Willkommen zurück " + ChatColor.GREEN + player.getDisplayName());
        if (e.getPlayer().hasPermission("Minerim.Schmied") && !e.getPlayer().hasPermission("Minerim.Admin")) {
          int lvl = e.getPlayer().getLevel();
          byte b = 10;
        } 
      } 
    } 
    e.setJoinMessage(null);
  }
  
  @EventHandler
  public void onInventoryClick(InventoryClickEvent event) {
    Player player = (Player)event.getWhoClicked();
    if (!player.hasPlayedBefore()) {
      ItemStack clicked = event.getCurrentItem();
      if (event.getWhoClicked() instanceof Player && 
        clicked.getType() == Material.COMMAND) {
        event.setCancelled(true);
        Location loc = new Location(Bukkit.getWorld("world"), -50.0D, 68.0D, -58.0D);
        player.getInventory().addItem(new ItemStack[] { new ItemStack(Material.EMERALD, 0) });
        player.teleport(loc);
        player.sendMessage("0 Septime erhalten du bist in Weiviel Spaß");
        event.getWhoClicked().closeInventory();
        player.kickPlayer("Nochmal Joinen!");
      } 
    } 
  }
  
  @EventHandler
  public void OnQuit(PlayerQuitEvent f) {
    f.setQuitMessage(null);
  }
  
  @EventHandler
  public void onspielerInteract(PlayerBedLeaveEvent h) {
    h.getPlayer().sendMessage(ChatColor.AQUA + "Du fühlst dich ausgeruht");
  }
}
