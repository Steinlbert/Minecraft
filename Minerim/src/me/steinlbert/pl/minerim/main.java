package me.steinlbert.pl.minerim;

import java.io.File;

import me.steinlbert.pl.minerim.boot.OnJoin;
import me.steinlbert.pl.minerim.boot.angler;
import me.steinlbert.pl.minerim.boot.playtime;
import me.steinlbert.pl.minerim.boot.scoreboardminerim;
import me.steinlbert.pl.minerim.jobs.bauer;
import me.steinlbert.pl.minerim.jobs.braumeister;
import me.steinlbert.pl.minerim.jobs.jaeger;
import me.steinlbert.pl.minerim.jobs.mara;
import me.steinlbert.pl.minerim.jobs.miner;
import me.steinlbert.pl.minerim.jobs.schmied;
import me.steinlbert.pl.minerim.jobs.stadtwache;
import me.steinlbert.pl.minerim.jobs.tavernenwirt;
import me.steinlbert.pl.minerim.jobs.verzauberer;
import me.steinlbert.pl.minerim.kopfgeld.einsamkeit;
import me.steinlbert.pl.minerim.kopfgeld.weisslauf;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class main extends JavaPlugin {
  public void onEnable() {
// super.onEnable();
    Starten();

  }
  
  private void Starten() {
    String NameDesPlugins = getDescription().getName();
    System.out.println("Bukkit Plugin " + NameDesPlugins + " wurde erfolgreich Geladen, Version 1.0.0");
    getCommand("Minerim").setExecutor(new help());
    getCommand("Minerim-Permissions").setExecutor(new help());
    getCommand("Minerim-Help").setExecutor(new help());
    getCommand("Minerim-time").setExecutor(new help());
    getCommand("Minerim-playtime").setExecutor(new help());
    getCommand("Score").setExecutor((CommandExecutor)new playtime(this));
    getCommand("invadmin").setExecutor((CommandExecutor)new stadtwache());
    getCommand("Minerim-Deaths").setExecutor(new help());
    getCommand("INV").setExecutor((CommandExecutor)new stadtwache());
    getCommand("Invaccept").setExecutor((CommandExecutor)new stadtwache());
    getCommand("Invdeny").setExecutor((CommandExecutor)new stadtwache());
    getCommand("segen").setExecutor((CommandExecutor)new mara());
    getCommand("Kopfgeld-set-Weisslauf").setExecutor((CommandExecutor)new weisslauf(this));
    getCommand("Kopfgeld-set-Weisslauf").setExecutor((CommandExecutor)new weisslauf(this));
    getCommand("Kopfgeld-remove-Weisslauf").setExecutor((CommandExecutor)new weisslauf(this));
    getCommand("Kopfgeld-set-einsamkeit").setExecutor((CommandExecutor)new einsamkeit(this));
    getCommand("Kopfgeld-list-einsamkeit").setExecutor((CommandExecutor)new einsamkeit(this));
    getCommand("Kopfgeld-remove-einsamkeit").setExecutor((CommandExecutor)new einsamkeit(this));
    getServer().getPluginManager().registerEvents((Listener)new playtime(this), (Plugin)this);
    getServer().getPluginManager().registerEvents((Listener)new angler(), (Plugin)this);
    getServer().getPluginManager().registerEvents((Listener)new bauer(), (Plugin)this);
    getServer().getPluginManager().registerEvents((Listener)new braumeister(), (Plugin)this);
    getServer().getPluginManager().registerEvents((Listener)new miner(), (Plugin)this);
    getServer().getPluginManager().registerEvents((Listener)new schmied(), (Plugin)this);
    getServer().getPluginManager().registerEvents((Listener)new tavernenwirt(), (Plugin)this);
    getServer().getPluginManager().registerEvents((Listener)new jaeger(), (Plugin)this);

    getServer().getPluginManager().registerEvents((Listener)new verzauberer(), (Plugin)this);
    
    getServer().getPluginManager().registerEvents((Listener)new OnJoin(), (Plugin)this);
    getServer().getPluginManager().registerEvents((Listener)new scoreboardminerim(null), (Plugin)this);
    
    File file = new File(getDataFolder() + File.separator + "config.yml");
    if (!file.exists()) {
      getConfig().addDefault("Tote", Integer.valueOf(98));
      getConfig().addDefault("Kopfgeld", " ");
      getConfig().options().copyDefaults(true);
      saveConfig();
    } else {
      CheckConfig();
      saveConfig();
      reloadConfig();
    } 
  }
  
  public void CheckConfig() {
    if (getConfig().get("Tote") == null) {
      getConfig().set("Tote", Integer.valueOf(98));
      saveConfig();
      reloadConfig();
    } 
  }
}
