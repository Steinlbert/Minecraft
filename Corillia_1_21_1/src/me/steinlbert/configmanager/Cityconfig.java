package me.steinlbert.configmanager;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;

import me.steinlbert.main;

public class Cityconfig implements Listener {
    private static FileConfiguration customConfig = null;
    private static File customConfigFile = null;
    static main plugin = main.getPlugin(main.class);
    private static boolean needsSave = false;

    public static void reloadCustomConfig() {
        if (customConfigFile == null) {
            customConfigFile = new File(plugin.getDataFolder(), "city.yml");
            if (!customConfigFile.getParentFile().exists()) {
                customConfigFile.getParentFile().mkdirs();
            }
        }
        customConfig = YamlConfiguration.loadConfiguration(customConfigFile);
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
        } catch (IOException ex) {
            plugin.getLogger().log(Level.SEVERE, "Could not save config to " + customConfigFile, ex);
        }
    }

    public static void saveDefaultConfig() {
        if (customConfigFile == null) {
            customConfigFile = new File(plugin.getDataFolder(), "city.yml");
            if (!customConfigFile.getParentFile().exists()) {
                customConfigFile.getParentFile().mkdirs();
            }
        }
        if (!customConfigFile.exists()) {
            try {
                customConfigFile.createNewFile();
                customConfig = YamlConfiguration.loadConfiguration(customConfigFile);
                plugin.getServer().getConsoleSender().sendMessage(ChatColor.WHITE + "City Config Erstellt");
                saveCustomConfig();
            } catch (IOException ex) {
                plugin.getLogger().log(Level.SEVERE, "Could not create city.yml", ex);
            }
        } else {
            reloadCustomConfig();
        }
    }

    public static boolean needsSave() {
        return needsSave;
    }
}