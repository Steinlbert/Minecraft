package me.steinlbert.pl.minerim;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.Statistic;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class help implements CommandExecutor {
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (sender instanceof Player) {
      if (cmd.getName().equalsIgnoreCase("Minerim-Permissions") && sender.hasPermission("Minerim.Admin")) {
        sender.sendMessage(ChatColor.GOLD + "Aufzaller Permissions!");
        sender.sendMessage("Minerim.Angler, Minerim.Bauer, Minerim.Jaeger");
        sender.sendMessage("Minerim.Jaeger, Minerim.Jarl, Minerim.Segen");
        sender.sendMessage("Minerim.Miner, Minerim.Schmied, Minerim.Stadtwache");
        sender.sendMessage("Minerim.Tavernenwirt, Minerim.Verzauberer");
        sender.sendMessage(ChatColor.AQUA + "Befehl zum rechte vergeben: pex user <user> add <Berechtigung>");
        return true;
      } 
      if (cmd.getName().equalsIgnoreCase("Minerim-Help")) {
        sender.sendMessage(ChatColor.RED + "Hilfe Bereich:");
        sender.sendMessage("Bewohner in den Furstentumern: " + Bukkit.getWhitelistedPlayers().size());
      } 
      cmd.getName().equalsIgnoreCase("Minerimtest");
      if (cmd.getName().equalsIgnoreCase("Minerim-time")) {
        int ticks = ((Player)sender).getPlayer().getStatistic(Statistic.TIME_SINCE_DEATH);
        int day = ticks / 24000;
        sender.sendMessage("seit letzten Tot! " + day);
        return true;
      } 
      if (label.equalsIgnoreCase("Minerim-playtime")) {
        Player p = ((Player)sender).getPlayer();
        int minute = p.getStatistic(Statistic.RECORD_PLAYED); //old -> PLAY_ONE_MINUTE
        int hours = minute / 60;
        p.sendMessage("hast Minerim " + hours + "h gespielt Gratulation!");
        return true;
      } 
      if (cmd.getName().equalsIgnoreCase("Minerim-Deaths")) {
        Player p = ((Player)sender).getPlayer();
        if (p.hasPermission("Minerim.Admin")) {
          if (args.length == 1) {
            OfflinePlayer target = p.getServer().getOfflinePlayer(args[0]);
            if (target != null) {
              p.sendMessage(+ target.getPlayer().getStatistic(Statistic.DEATHS) + " Tote bei 100 scheidest du aus dem Projekt!");
            } else {
              p.sendMessage("musst einen Spieler angeben um dessen Tote zu sehen mit /Score0 kannst du sie resetten!");
            } 
          } 
        } else {
          p.sendMessage("Du bist kein Admin!");
        } 
      } 
    } 
    return true;
  }
}