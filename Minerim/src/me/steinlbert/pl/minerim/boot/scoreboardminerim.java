package me.steinlbert.pl.minerim.boot;

import me.steinlbert.pl.minerim.main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.Statistic;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public class scoreboardminerim implements Listener, CommandExecutor {
  public main plugin;
  
  int E;
  
  public scoreboardminerim(JavaPlugin plugin) {
    this.E = 100;
  }
  

  
  
  
  public void score(Player player) {
    Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
    Objective objective = scoreboard.registerNewObjective("abcd", "abcd");
    objective.setDisplayName("Minerim");
    objective.setDisplaySlot(DisplaySlot.SIDEBAR);
    objective.getScore(ChatColor.GOLD + "erlaubte Tote").setScore(100);
    for (Player all : Bukkit.getOnlinePlayers())
   objective.getScore(player.getPlayer()).setScore(player.getStatistic(Statistic.DEATHS));
    player.setScoreboard(scoreboard);
    
    if (player.isDead()) {
    	   objective.getScore(player.getPlayer()).setScore(player.getStatistic(Statistic.DEATHS));
    	 player.setScoreboard(scoreboard);

    
    }
    
  }
  
  
  
  @EventHandler
  public void Death(final PlayerDeathEvent t) {
    int I = t.getEntity().getStatistic(Statistic.DEATHS);
    t.setDeathMessage("");
    t.getEntity().sendMessage((new StringBuilder(String.valueOf(this.E))).toString());
    Player p = t.getEntity();
    score(t.getEntity().getPlayer());
  }
  
  @EventHandler
  public void playerJoin(PlayerJoinEvent e) {
   score(e.getPlayer());
    if (!e.getPlayer().hasPermission("Minerim.Admin") && 
      e.getPlayer().getStatistic(Statistic.DEATHS) > this.E)
      e.getPlayer().kickPlayer("du kannst erst in 7 tagen wieder mitmachen!"); 
  }
  
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (sender instanceof Player) {
      Player p = (Player)sender;
      if (label.equalsIgnoreCase("Score0"))
        if (args.length == 1) {
          Player target = p.getServer().getPlayer(args[0]);
          if (target != null)
            if (p.hasPermission("Minerim.Admin")) {
              target.setStatistic(Statistic.DEATHS, 0);
              target.resetPlayerTime();
              p.sendMessage("Score von " + target.getName() + " auf 0 gesetzt!");
              score(p);
            } else {
              p.sendMessage("Du bist kein Admin!");
            }  
        } else {
          p.sendMessage("Zu wenig Argumente! /score0 <player> angeben und ein Admin sein!");
        }  
    } 
    return true;
  }
}
