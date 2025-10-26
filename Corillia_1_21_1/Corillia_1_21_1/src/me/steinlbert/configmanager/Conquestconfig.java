package me.steinlbert.configmanager;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import me.steinlbert.main;
import org.bukkit.ChatColor;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

public class Conquestconfig implements Listener {
    private static FileConfiguration customConfig = null;
    private static File customConfigFile = null;
    static main plugin = main.getPlugin(main.class);
    private static boolean needsSave = false;

    public static void reloadCustomConfig() {
        if (customConfigFile == null) {
            customConfigFile = new File(plugin.getDataFolder(), "conquest.yml");
            if (!customConfigFile.getParentFile().exists()) {
                customConfigFile.getParentFile().mkdirs();
            }
        }
        customConfig = YamlConfiguration.loadConfiguration(customConfigFile);
        //plugin.getServer().getConsoleSender().sendMessage(ChatColor.BLUE + "[DEBUG] Reloaded conquest.yml");
    }

    public static FileConfiguration getCustomConfig() {
        if (customConfig == null) {
            reloadCustomConfig();
        }
        return customConfig;
    }

    public static void markConfigDirty() {
        needsSave = true;
    }

    public static void saveCustomConfig() {
        if (customConfig == null || customConfigFile == null) {
            plugin.getLogger().log(Level.SEVERE, "Cannot save config: customConfig or customConfigFile is null");
            return;
        }
        try {
            customConfig.save(customConfigFile);
            needsSave = false;
            //plugin.getServer().getConsoleSender().sendMessage(ChatColor.BLUE + "[DEBUG] Saved conquest.yml");
        } catch (IOException ex) {
            plugin.getLogger().log(Level.SEVERE, "Could not save config to " + customConfigFile, ex);
        }
    }

    public static void saveDefaultConfig() {
        if (customConfigFile == null) {
            customConfigFile = new File(plugin.getDataFolder(), "conquest.yml");
            if (!customConfigFile.getParentFile().exists()) {
                customConfigFile.getParentFile().mkdirs();
            }
        }
        if (!customConfigFile.exists()) {
            try {
                customConfigFile.createNewFile();
                customConfig = YamlConfiguration.loadConfiguration(customConfigFile);
                customConfig.createSection("conquest.settings");
                customConfig.set("conquest.settings.conquest-set", "off");
                customConfig.set("conquest.settings.boarder-size", 777); // Korrigiert von 200
                customConfig.set("conquest.settings.boarder-shrinking-rate", 50);
                customConfig.set("conquest.settings.death-spectator", "off");
                customConfig.set("conquest.settings.protection-shrinking-time", 11);
                customConfig.set("conquest.settings.protection-shrinking-amount", 10);
                customConfig.set("conquest.settings.conquest-radius", 0);            
                customConfig.createSection("conquest-attacker");
                customConfig.createSection("conquest-defender");
                saveCustomConfig();
                plugin.getServer().getConsoleSender().sendMessage(ChatColor.WHITE + "Conquest Config Created");
            } catch (IOException ex) {
                plugin.getLogger().log(Level.SEVERE, "Could not create conquest.yml", ex);
            }
        } else {
            reloadCustomConfig();
        }
    }

    public static boolean needsSave() {
        return needsSave;
    }
}