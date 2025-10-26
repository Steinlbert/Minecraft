package me.steinlbert.pl.minerim.kopfgeld;

import java.util.HashMap;
import java.util.Map;
import me.steinlbert.pl.minerim.main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public class einsamkeit implements Listener, CommandExecutor {
  main plugin;
  
  public static HashMap<String, String> KopfgeldEinsamkeit = new HashMap<>();
  
  public einsamkeit(main instance) {
    this.plugin = instance;
    this.plugin.getServer().getPluginManager().registerEvents(this, (Plugin)this.plugin);
  }
  
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    Player p = (Player)sender;
    if (label.equalsIgnoreCase("Kopfgeld-set-Einsamkeit"))
      if (sender.hasPermission("Minerim.Einsamkeit")) {
        if (sender instanceof Player)
          if (args.length == 3) {
            if (args[0] != null) {
              if (args[1] != null) {
                if (args[2] != null) {
                  KopfgeldEinsamkeit.put(args[0], String.valueOf(args[1]) + " " + args[2]);
                  Player player = Bukkit.getPlayer(args[1]);
                  player.sendMessage("Dein Kopfgeld wurde auf " + args[2] + " Septime erhöht");
                  saveHashMap(KopfgeldEinsamkeit);
                } else {
                  p.sendMessage("Kopfgeld angeben");
                } 
              } else {
                p.sendMessage("Spieler angeben");
              } 
            } else {
              p.sendMessage("Du musst einen Zahl angeben!");
            } 
          } else {
            p.sendMessage("Du musst es so eingeben Beispiel: <1 Steinlbert 10>");
          }  
      } else {
        sender.sendMessage("Darf nur der Jarl!");
      }  
    if (label.equalsIgnoreCase("kopfgeld-remove-Einsamkeit")) {
      Player player = (Player)sender;
      if (sender.hasPermission("Minerim.Einsamkeit")) {
        if (args.length == 1) {
          KopfgeldEinsamkeit.remove(args[0]);
          this.plugin.getConfig().set("Kopfgeld." + args[0], null);
          this.plugin.saveConfig();
          saveHashMap(KopfgeldEinsamkeit);
        } else {
          player.sendMessage("Beispiel: /kopfgeld-remove 1 (Hier wird Platz 1 gelusw.)");
        } 
      } else {
        p.sendMessage("Darf nur der Jarl!");
      } 
    } 
    if (label.equalsIgnoreCase("Kopfgeld-list-Einsamkeit")) {
      loadHashMap();
      if (sender instanceof Player)
        for (Map.Entry<String, String> kopf : loadHashMap().entrySet()) {
          String s = kopf.getKey();
          String d = kopf.getValue();
          sender.sendMessage(String.valueOf(s) + " " + d);
        }  
    } 
    return true;
  }
  
  public void saveHashMap(HashMap<String, String> Kopfgeld) {
    int I = 0;
    for (String key : Kopfgeld.keySet())
      this.plugin.getConfig().set("Kopfgeld-Einsamkeit." + key, Kopfgeld.get(key)); 
    this.plugin.saveConfig();
  }
  
  public HashMap<String, String> loadHashMap() {
    HashMap<String, String> Kopfgeld = new HashMap<>();
    for (String key : this.plugin.getConfig().getConfigurationSection("Kopfgeld-Einsamkeit").getKeys(false))
      Kopfgeld.put(key, this.plugin.getConfig().getString("Kopfgeld-Einsamkeit." + key)); 
    return Kopfgeld;
  }
}
