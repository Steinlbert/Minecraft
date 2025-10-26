package me.steinlbert.configmanager;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.logging.Level;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;


import me.steinlbert.main;

public class Homesconfig implements Listener {
    private static FileConfiguration customConfig = null;
    private static File customConfigFile = null;
    static main plugin = main.getPlugin(main.class);
    private static boolean needsSave = false;

    public static void reloadCustomConfig() {
        if (customConfigFile == null) {
            customConfigFile = new File(plugin.getDataFolder(), "homes.yml");
            if (!customConfigFile.getParentFile().exists()) {
                customConfigFile.getParentFile().mkdirs();
            }
        }
        customConfig = YamlConfiguration.loadConfiguration(customConfigFile);
        //plugin.getServer().getConsoleSender().sendMessage(ChatColor.BLUE + "[DEBUG] Reloaded homes.yml");
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
            //plugin.getServer().getConsoleSender().sendMessage(ChatColor.BLUE + "[DEBUG] Saved homes.yml");
        } catch (IOException ex) {
            plugin.getLogger().log(Level.SEVERE, "Could not save config to " + customConfigFile, ex);
        }
    }

    public static void saveDefaultConfig() {
        if (customConfigFile == null) {
            customConfigFile = new File(plugin.getDataFolder(), "homes.yml");
            if (!customConfigFile.getParentFile().exists()) {
                customConfigFile.getParentFile().mkdirs();
            }
        }
        if (!customConfigFile.exists()) {
            try {
                customConfigFile.createNewFile();
                customConfig = YamlConfiguration.loadConfiguration(customConfigFile);
                customConfig.createSection("Homes");
                customConfig.createSection("Homes.settings");
                customConfig.set("Homes.settings.allowed homes", 3); // Integer statt String
                saveCustomConfig();
                plugin.getServer().getConsoleSender().sendMessage(ChatColor.WHITE + "Homes Config Erstellt");
            } catch (IOException ex) {
                plugin.getLogger().log(Level.SEVERE, "Could not create homes.yml", ex);
            }
        } else {
            reloadCustomConfig();
        }
    }

    public static boolean needsSave() {
        return needsSave;
    }
}