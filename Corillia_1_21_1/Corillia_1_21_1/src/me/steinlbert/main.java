package me.steinlbert;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;
import me.steinlbert.configmanager.Conquestconfig;
import me.steinlbert.configmanager.Cityconfig;
import me.steinlbert.configmanager.Chestconfig;
import me.steinlbert.configmanager.Homesconfig;
import me.steinlbert.events.chestlistener;
import me.steinlbert.events.chests;
import me.steinlbert.events.cmd;
import me.steinlbert.events.combatlock;
import me.steinlbert.events.homes;
import me.steinlbert.events.land;
import me.steinlbert.scoreboard.ConquestScoreboard;
import me.steinlbert.events.ConquestManager;

import java.util.HashMap;

public class main extends JavaPlugin {
    private static main plugin;

    public static HashMap<String, HashMap<String, Location>> homesData = new HashMap<>();
    public static HashMap<String, HashMap<String, String>> chestData = new HashMap<>();

    @Override
    public void onEnable() {
        plugin = this;
        Conquestconfig.saveDefaultConfig();
        Cityconfig.saveDefaultConfig();
        Homesconfig.saveDefaultConfig();
        Chestconfig.saveDefaultConfig();
        homes.loadHomesData();
        chestlistener.loadChestData();
        chests.loadPlayerSettings();
        land.loadCityData();

        String pluginname = getDescription().getName();
        getServer().getConsoleSender().sendMessage(ChatColor.GOLD + pluginname + " Spigot-Plugin Activate!");
        //commands
        getCommand("corilia-config").setExecutor((CommandExecutor) new cmd());
        getCommand("corilia-radius").setExecutor((CommandExecutor) new cmd());
        getCommand("conquest").setExecutor((CommandExecutor) new cmd());
        getCommand("corilia-add-player").setExecutor((CommandExecutor) new cmd());
        getCommand("corilia-remove-player").setExecutor((CommandExecutor) new cmd());
        getCommand("corilia-new-city").setExecutor((CommandExecutor) new cmd());
        getCommand("corilia-remove-city").setExecutor((CommandExecutor) new cmd());
        getCommand("home").setExecutor((CommandExecutor) new homes());
        getCommand("set-home").setExecutor((CommandExecutor) new homes());
        getCommand("list-homes").setExecutor((CommandExecutor) new homes());
        getCommand("remove-home").setExecutor((CommandExecutor) new homes());
        getCommand("back").setExecutor((CommandExecutor) new homes());
        getCommand("private").setExecutor((CommandExecutor) new chests());    
        //Events
        getServer().getPluginManager().registerEvents(new land(), this);
        getServer().getPluginManager().registerEvents(new Homesconfig(), this);
        getServer().getPluginManager().registerEvents(new chestlistener(), this);
        getServer().getPluginManager().registerEvents(new homes(), this);
        getServer().getPluginManager().registerEvents(new ConquestManager(), this);
        getServer().getPluginManager().registerEvents(new Conquestconfig(), this);
        getServer().getPluginManager().registerEvents(new ConquestScoreboard(), this);
        getServer().getPluginManager().registerEvents(new combatlock(), this);
    }

    @Override
    public void onDisable() {
        if (Conquestconfig.needsSave()) {
            Conquestconfig.saveCustomConfig();
        }
        if (Chestconfig.needsSave()) {
            Chestconfig.saveCustomConfig();
        }
        if (Cityconfig.needsSave()) {
            Cityconfig.saveCustomConfig();
        }
        if (Homesconfig.needsSave()) {
            Homesconfig.saveCustomConfig();
        }
    }

    public static main getPlugin() {
        return plugin;
    }
}