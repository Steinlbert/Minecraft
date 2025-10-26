package me.steinlbert.pl.minerim.jobs;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class mara implements CommandExecutor {
  public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
    if (label.equalsIgnoreCase("segen")) {
      Player p = (Player)cs;
      if (args.length == 1) {
        Player target = cs.getServer().getPlayer(args[0]);
        if (target == null) {
          if (p.hasPermission("Minerim.Segen"))
            p.sendMessage(ChatColor.GOLD + "Um jemanden zu segnen" + ChatColor.AQUA + " /segen [Spieler]"); 
          return true;
        } 
        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
          if (player.hasPermission("Minerim.Segen")) {
            p.sendMessage(ChatColor.MAGIC + "ssfmdsgmnsdnnjknsdkjkgnkg");
            target.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 72000, 1));
            target.sendMessage(ChatColor.AQUA + "Du wurdest von einem" + ChatColor.GOLD + " Geistlichen" + ChatColor.AQUA + " in" + ChatColor.GOLD + " Maras" + ChatColor.AQUA + " Namen Gesegnet");
          } 
        } 
      } else {
        p.sendMessage(ChatColor.GOLD + "Benutze" + ChatColor.AQUA + " /maras-segen [Spieler]");
      } 
    } 
    return true;
  }
}
