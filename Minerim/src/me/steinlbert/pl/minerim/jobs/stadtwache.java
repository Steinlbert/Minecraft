package me.steinlbert.pl.minerim.jobs;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.PlayerInventory;

public class stadtwache implements CommandExecutor {
  public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
    if (cs instanceof Player) {
      Player p = (Player)cs;
      if (label.equalsIgnoreCase("INV")) {
        if (args.length == 1) {
          Player target = p.getServer().getPlayer(args[0]);
          if (target != null)
            if (p.hasPermission("Minerim.Stadtwache")) {
              p.sendMessage("Anfrage verschickt!");
              target.sendMessage(ChatColor.GOLD + p.getName() + ChatColor.GREEN + " Stadtwache will dein Inventar Einsehen du hast die Wahl! /invaccept " + ChatColor.GOLD + p.getName() + ChatColor.GREEN + " oder /invdeny!");
            } else {
              p.sendMessage(ChatColor.GREEN + "Du gehnicht zur Stadtwache");
            }  
        } 
        return true;
      } 
      if (cs instanceof Player) {
        if (args.length == 0)
          p.sendMessage("Du musst einen Spieler angeben!"); 
        if (label.equalsIgnoreCase("invaccept")) {
          if (args.length == 1) {
            PlayerInventory playerInventory = p.getInventory();
            Player sender = p.getServer().getPlayer(args[0]);
            if (sender != null)
              if (sender.hasPermission("Minerim.Stadtwache")) {
                p.sendMessage(ChatColor.AQUA + "Du hast durchsuchen akzeptiert!");
                sender.openInventory((Inventory)playerInventory);
              } else {
                p.sendMessage("Nur die Stadtwache kann dich durchsuchen!");
              }  
          } 
          return true;
        } 
      } 
      if (cs instanceof Player && 
        label.equalsIgnoreCase("invdeny"))
        p.sendMessage(ChatColor.GREEN + "Du hast durchsuchen abgelehnt nichts passiert!"); 
      if (cs instanceof Player && 
        label.equalsIgnoreCase("invadmin")) {
        if (cs.hasPermission("Minerim.Admin"))
          if (args.length == 1) {
            Player target = Bukkit.getServer().getPlayer(args[0]);
            PlayerInventory playerInventory = target.getInventory();
            p.sendMessage("Du siehst nun das Inventar von" + target);
            p.openInventory((Inventory)playerInventory);
          } else {
            p.sendMessage(String.valueOf(p.getName()) + " du musst den Spieler angeben Beispiel -> /invadmin Steinlbert");
          }  
        return true;
      } 
    } 
    return true;
  }
}
