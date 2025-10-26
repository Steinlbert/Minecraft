package me.steinlbert.pl.minerim.boot;

import me.steinlbert.pl.minerim.main;
import org.bukkit.ChatColor;
import org.bukkit.Statistic;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class playtime implements Listener, CommandExecutor {
  main plugin;
  
  public playtime(main instance) {
    this.plugin = instance;
  }
  
  public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
    if (cs instanceof Player)
      if (label.equalsIgnoreCase("Score")) {
        if (cs.hasPermission("Minerim.Admin")) {
          cs.sendMessage((new StringBuilder(String.valueOf(((Player)cs).getStatistic(Statistic.DEATHS)))).toString());
          if (args.length == 2) {
            Player player = cs.getServer().getPlayer(args[0]);
            if (player != null) {
              if (args[1] != null) {
                int h = (new Integer(args[1])).intValue();
                player.getPlayer().setStatistic(Statistic.DEATHS, h);
              } 
            } else {
              player.isOnline();
            } 
          } 
        } else {
          cs.sendMessage("Du musst einen spieler + Tote angeben!");
        } 
      } else {
        cs.sendMessage("Du bist kein Admin");
      }  
    return true;
  }
  
  @EventHandler
  public void onJoin(PlayerJoinEvent e) {}
  
  @EventHandler
  public void onDeath(PlayerDeathEvent t) {
    Player p = t.getEntity();
    if (p.getStatistic(Statistic.DEATHS) > this.plugin.getConfig().getInt("Tote"))
      p.kickPlayer(ChatColor.RED + "war dein 100 Tot auf dem Server!"); 
  }
}
