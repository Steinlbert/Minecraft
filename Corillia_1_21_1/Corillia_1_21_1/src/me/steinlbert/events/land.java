package me.steinlbert.events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import me.steinlbert.main;
import me.steinlbert.configmanager.Cityconfig;
import me.steinlbert.configmanager.Conquestconfig;

import java.util.HashMap;

public class land implements Listener {
    public static HashMap<String, CityData> cityData = new HashMap<>();

    public static class CityData {
        String name;
        int locX, locY, locZ;
        HashMap<String, String> players;
        int originalRadius;
        public int currentRadius;

        public CityData(String name, int locX, int locY, int locZ, HashMap<String, String> players, int radius) {
            this.name = name;
            this.locX = locX;
            this.locY = locY;
            this.locZ = locZ;
            this.players = players;
            this.originalRadius = Math.max(radius, 10);
            this.currentRadius = this.originalRadius;
        }
    }

    public static void loadCityData() {
        cityData.clear();
        if (Cityconfig.getCustomConfig() == null) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[ERROR] City config is null, cannot load cityData!");
            return;
        }
        ConfigurationSection cities = Cityconfig.getCustomConfig();
        for (String cityName : cities.getKeys(false)) {
            ConfigurationSection city = cities.getConfigurationSection(cityName);
            if (city != null) {
                int locX = city.getInt("LocX");
                int locY = city.getInt("LocY");
                int locZ = city.getInt("LocZ");
                HashMap<String, String> players = new HashMap<>();
                ConfigurationSection playersSection = city.getConfigurationSection("Spieler");
                if (playersSection != null) {
                    for (String player : playersSection.getKeys(false)) {
                        players.put(player, playersSection.getString(player));
                    }
                }
                int radius = city.getInt("radius", 50);
                if (radius <= 0) {
                    radius = 50;
                    city.set("radius", 50);
                    Cityconfig.markConfigDirty();
                    Cityconfig.saveCustomConfig();
                    Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "[WARNING] Invalid radius for " + cityName + ", set to 50");
                }
                cityData.put(cityName, new CityData(cityName, locX, locY, locZ, players, radius));
            }
        }
        Cityconfig.saveCustomConfig();
        //Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "[DEBUG] Loaded cityData: " + cityData.keySet().toString());
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (event.isCancelled()) return;
        Player player = event.getPlayer();
        Location blockLoc = event.getBlock().getLocation();
        int blockX = blockLoc.getBlockX();
        int blockZ = blockLoc.getBlockZ();

        if (ConquestManager.isConquestActive()) {
            int protectionFallSize = Conquestconfig.getCustomConfig().getInt("conquest.settings.border-protection-fall-size", 200);
            if (ConquestManager.getCurrentBorderSize() <= protectionFallSize) {
                return; // No protection when border size is at or below threshold
            }
            String activeCity = ConquestManager.getActiveCity();
            CityData city = cityData.get(activeCity);
            if (city != null) {
                double distance = Math.sqrt(Math.pow(blockX - city.locX, 2) + Math.pow(blockZ - city.locZ, 2));
                if (distance <= ConquestManager.getCurrentProtectionRadius()) {
                    if (player.getGameMode() == GameMode.CREATIVE) {
                        player.sendMessage(ChatColor.GREEN + "Creative mode: Block break allowed in " + city.name + "!");
                        return;
                    }
                    if (city.players.containsKey(player.getName())) {
                        return; // Defenders can break
                    }
                    if (Conquestconfig.getCustomConfig().getConfigurationSection("conquest-attacker").getKeys(false).contains(player.getName())) {
                        player.sendMessage(ChatColor.RED + "You cannot break blocks within " + city.name + "'s protection radius during conquest!");
                        event.setCancelled(true);
                        return;
                    }
                }
                return; // Allow breaking outside protection radius during conquest
            }
        }

        // Normal city protection
        for (CityData city : cityData.values()) {
            double distance = Math.sqrt(Math.pow(blockX - city.locX, 2) + Math.pow(blockZ - city.locZ, 2));
            if (distance <= city.currentRadius) {
                if (player.getGameMode() == GameMode.CREATIVE) {
                    player.sendMessage(ChatColor.GREEN + "Creative mode: Block break allowed in " + city.name + "!");
                    return;
                }
                if (city.players.containsKey(player.getName())) {
                    return;
                }
                player.sendMessage(ChatColor.RED + "You are not a member of " + city.name + "!");
                event.setCancelled(true);
                return;
            }
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (event.isCancelled()) return;
        Player player = event.getPlayer();
        Location blockLoc = event.getBlock().getLocation();
        int blockX = blockLoc.getBlockX();
        int blockZ = blockLoc.getBlockZ();

        if (ConquestManager.isConquestActive()) {
            int protectionFallSize = Conquestconfig.getCustomConfig().getInt("conquest.settings.border-protection-fall-size", 200);
            if (ConquestManager.getCurrentBorderSize() <= protectionFallSize) {
                return; // No protection when border size is at or below threshold
            }
            String activeCity = ConquestManager.getActiveCity();
            CityData city = cityData.get(activeCity);
            if (city != null) {
                double distance = Math.sqrt(Math.pow(blockX - city.locX, 2) + Math.pow(blockZ - city.locZ, 2));
                if (distance <= ConquestManager.getCurrentProtectionRadius()) {
                    if (player.getGameMode() == GameMode.CREATIVE) {
                        player.sendMessage(ChatColor.GREEN + "Creative mode: Block place allowed in " + city.name + "!");
                        return;
                    }
                    if (city.players.containsKey(player.getName())) {
                        return; // Defenders can place
                    }
                    if (Conquestconfig.getCustomConfig().getConfigurationSection("conquest-attacker").getKeys(false).contains(player.getName())) {
                        player.sendMessage(ChatColor.RED + "You cannot place blocks within " + city.name + "'s protection radius during conquest!");
                        event.setCancelled(true);
                        return;
                    }
                }
                return; // Allow placing outside protection radius during conquest
            }
        }

        // Normal city protection
        for (CityData city : cityData.values()) {
            double distance = Math.sqrt(Math.pow(blockX - city.locX, 2) + Math.pow(blockZ - city.locZ, 2));
            if (distance <= city.currentRadius) {
                if (player.getGameMode() == GameMode.CREATIVE) {
                    player.sendMessage(ChatColor.GREEN + "Creative mode: Block place allowed in " + city.name + "!");
                    return;
                }
                if (city.players.containsKey(player.getName())) {
                    return;
                }
                player.sendMessage(ChatColor.RED + "You are not a member of " + city.name + "!");
                event.setCancelled(true);
                return;
            }
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK || event.isCancelled()) return;
        Player player = event.getPlayer();
        ItemStack item = event.getItem();
        if (item == null) return;

        Material[] restrictedItems = {
            Material.WATER_BUCKET, Material.LAVA_BUCKET, Material.MILK_BUCKET,
            Material.POWDER_SNOW_BUCKET, Material.AXOLOTL_BUCKET, Material.COD_BUCKET,
            Material.PUFFERFISH_BUCKET, Material.SALMON_BUCKET, Material.TADPOLE_BUCKET,
            Material.TROPICAL_FISH_BUCKET
        };
        boolean isRestricted = false;
        for (Material mat : restrictedItems) {
            if (item.getType() == mat) {
                isRestricted = true;
                break;
            }
        }
        if (!isRestricted) return;

        Location blockLoc = event.getClickedBlock().getLocation();
        int blockX = blockLoc.getBlockX();
        int blockZ = blockLoc.getBlockZ();

        if (player.getGameMode() == GameMode.CREATIVE) {
            return;
        }

        if (ConquestManager.isConquestActive()) {
            int protectionFallSize = Conquestconfig.getCustomConfig().getInt("conquest.settings.border-protection-fall-size", 200);
            if (ConquestManager.getCurrentBorderSize() <= protectionFallSize) {
                return; // No protection when border size is at or below threshold
            }
            String activeCity = ConquestManager.getActiveCity();
            CityData city = cityData.get(activeCity);
            if (city != null) {
                double distance = Math.sqrt(Math.pow(blockX - city.locX, 2) + Math.pow(blockZ - city.locZ, 2));
                if (distance <= ConquestManager.getCurrentProtectionRadius()) {
                    if (city.players.containsKey(player.getName())) {
                        return; // Defenders can use items
                    }
                    if (Conquestconfig.getCustomConfig().getConfigurationSection("conquest-attacker").getKeys(false).contains(player.getName())) {
                        event.setCancelled(true);
                        player.setItemInHand(new ItemStack(Material.BUCKET));
                        player.sendMessage(ChatColor.RED + "You cannot use this item within " + city.name + "'s protection radius during conquest!");
                        return;
                    }
                }
                return; // Allow usage outside protection radius during conquest
            }
        }

        // Normal city protection
        for (CityData city : cityData.values()) {
            double distance = Math.sqrt(Math.pow(blockX - city.locX, 2) + Math.pow(blockZ - city.locZ, 2));
            if (distance <= city.currentRadius) {
                if (city.players.containsKey(player.getName())) {
                    return;
                }
                event.setCancelled(true);
                player.setItemInHand(new ItemStack(Material.BUCKET));
                player.sendMessage(ChatColor.RED + "You cannot use this item in " + city.name + "!");
                return;
            }
        }
    }

    public static int getCenterX(String cityName) {
        CityData city = cityData.get(cityName);
        return city != null ? city.locX : 0;
    }

    public static int getCenterZ(String cityName) {
        CityData city = cityData.get(cityName);
        return city != null ? city.locZ : 0;
    }
}
/*
public class land implements Listener {
    public static HashMap<String, CityData> cityData = new HashMap<>();

    public static class CityData {
        String name;
        int locX, locY, locZ;
        HashMap<String, String> players;
        int originalRadius;
        public int currentRadius;

        public CityData(String name, int locX, int locY, int locZ, HashMap<String, String> players, int radius) {
            this.name = name;
            this.locX = locX;
            this.locY = locY;
            this.locZ = locZ;
            this.players = players;
            this.originalRadius = Math.max(radius, 10);
            this.currentRadius = this.originalRadius;
        }
    }

    public static void loadCityData() {
        cityData.clear();
        if (Cityconfig.getCustomConfig() == null) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[ERROR] City config is null, cannot load cityData!");
            return;
        }
        for (int i = 1; i <= 10; i++) {
            String cityKey = "City" + i;
            ConfigurationSection city = Cityconfig.getCustomConfig().getConfigurationSection(cityKey);
            if (city != null) {
                String name = city.getString("Stadtname");
                int locX = city.getInt("LocX");
                int locY = city.getInt("LocY");
                int locZ = city.getInt("LocZ");
                HashMap<String, String> players = new HashMap<>();
                ConfigurationSection playersSection = city.getConfigurationSection("Spieler");
                if (playersSection != null) {
                    for (String player : playersSection.getKeys(false)) {
                        players.put(player, playersSection.getString(player));
                    }
                }
                int radius = city.getInt("radius", 50);
                if (radius <= 0) {
                    radius = 50;
                    city.set("radius", 50);
                    Cityconfig.markConfigDirty();
                    Cityconfig.saveCustomConfig();
                    Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "[WARNING] Invalid radius for " + name + ", set to 50");
                }
                cityData.put(cityKey, new CityData(name, locX, locY, locZ, players, radius));
            }
        }
        Cityconfig.saveCustomConfig();
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (event.isCancelled()) return;
        Player player = event.getPlayer();
        Location blockLoc = event.getBlock().getLocation();
        int blockX = blockLoc.getBlockX();
        int blockZ = blockLoc.getBlockZ();

        if (ConquestManager.isConquestActive()) {
            if (ConquestManager.getCurrentBorderSize() <= 200) {
                return; // No protection when border size is 200 or less
            }
            String activeCity = ConquestManager.getActiveCity();
            CityData city = cityData.values().stream()
                .filter(c -> c.name.equalsIgnoreCase(activeCity))
                .findFirst().orElse(null);
            if (city != null) {
                double distance = Math.sqrt(Math.pow(blockX - city.locX, 2) + Math.pow(blockZ - city.locZ, 2));
                if (distance <= ConquestManager.getCurrentProtectionRadius()) {
                    if (player.getGameMode() == GameMode.CREATIVE) {
                        player.sendMessage(ChatColor.GREEN + "Creative mode: Block break allowed in " + city.name + "!");
                        return;
                    }
                    if (city.players.containsKey(player.getName())) {
                        return; // Defenders can break
                    }
                    if (Conquestconfig.getCustomConfig().getConfigurationSection("conquest-attacker").getKeys(false).contains(player.getName())) {
                        player.sendMessage(ChatColor.RED + "You cannot break blocks within " + city.name + "'s protection radius during conquest!");
                        event.setCancelled(true);
                        return;
                    }
                }
                return; // Allow breaking outside protection radius during conquest
            }
        }

        // Normal city protection
        for (CityData city : cityData.values()) {
            double distance = Math.sqrt(Math.pow(blockX - city.locX, 2) + Math.pow(blockZ - city.locZ, 2));
            if (distance <= city.currentRadius) {
                if (player.getGameMode() == GameMode.CREATIVE) {
                    player.sendMessage(ChatColor.GREEN + "Creative mode: Block break allowed in " + city.name + "!");
                    return;
                }
                if (city.players.containsKey(player.getName())) {
                    return;
                }
                player.sendMessage(ChatColor.RED + "You are not a member of " + city.name + "!");
                event.setCancelled(true);
                return;
            }
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (event.isCancelled()) return;
        Player player = event.getPlayer();
        Location blockLoc = event.getBlock().getLocation();
        int blockX = blockLoc.getBlockX();
        int blockZ = blockLoc.getBlockZ();

        if (ConquestManager.isConquestActive()) {
            if (ConquestManager.getCurrentBorderSize() <= 200) {
                return; // No protection when border size is 200 or less
            }
            String activeCity = ConquestManager.getActiveCity();
            CityData city = cityData.values().stream()
                .filter(c -> c.name.equalsIgnoreCase(activeCity))
                .findFirst().orElse(null);
            if (city != null) {
                double distance = Math.sqrt(Math.pow(blockX - city.locX, 2) + Math.pow(blockZ - city.locZ, 2));
                if (distance <= ConquestManager.getCurrentProtectionRadius()) {
                    if (player.getGameMode() == GameMode.CREATIVE) {
                        player.sendMessage(ChatColor.GREEN + "Creative mode: Block place allowed in " + city.name + "!");
                        return;
                    }
                    if (city.players.containsKey(player.getName())) {
                        return; // Defenders can place
                    }
                    if (Conquestconfig.getCustomConfig().getConfigurationSection("conquest-attacker").getKeys(false).contains(player.getName())) {
                        player.sendMessage(ChatColor.RED + "You cannot place blocks within " + city.name + "'s protection radius during conquest!");
                        event.setCancelled(true);
                        return;
                    }
                }
                return; // Allow placing outside protection radius during conquest
            }
        }

        // Normal city protection
        for (CityData city : cityData.values()) {
            double distance = Math.sqrt(Math.pow(blockX - city.locX, 2) + Math.pow(blockZ - city.locZ, 2));
            if (distance <= city.currentRadius) {
                if (player.getGameMode() == GameMode.CREATIVE) {
                    player.sendMessage(ChatColor.GREEN + "Creative mode: Block place allowed in " + city.name + "!");
                    return;
                }
                if (city.players.containsKey(player.getName())) {
                    return;
                }
                player.sendMessage(ChatColor.RED + "You are not a member of " + city.name + "!");
                event.setCancelled(true);
                return;
            }
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK || event.isCancelled()) return;
        Player player = event.getPlayer();
        ItemStack item = event.getItem();
        if (item == null) return;

        Material[] restrictedItems = {
            Material.WATER_BUCKET, Material.LAVA_BUCKET, Material.MILK_BUCKET,
            Material.POWDER_SNOW_BUCKET, Material.AXOLOTL_BUCKET, Material.COD_BUCKET,
            Material.PUFFERFISH_BUCKET, Material.SALMON_BUCKET, Material.TADPOLE_BUCKET,
            Material.TROPICAL_FISH_BUCKET
        };
        boolean isRestricted = false;
        for (Material mat : restrictedItems) {
            if (item.getType() == mat) {
                isRestricted = true;
                break;
            }
        }
        if (!isRestricted) return;

        Location blockLoc = event.getClickedBlock().getLocation();
        int blockX = blockLoc.getBlockX();
        int blockZ = blockLoc.getBlockZ();

        if (player.getGameMode() == GameMode.CREATIVE) {
            return;
        }

        if (ConquestManager.isConquestActive()) {
            if (ConquestManager.getCurrentBorderSize() <= 200) {
                return; // No protection when border size is 200 or less
            }
            String activeCity = ConquestManager.getActiveCity();
            CityData city = cityData.values().stream()
                .filter(c -> c.name.equalsIgnoreCase(activeCity))
                .findFirst().orElse(null);
            if (city != null) {
                double distance = Math.sqrt(Math.pow(blockX - city.locX, 2) + Math.pow(blockZ - city.locZ, 2));
                if (distance <= ConquestManager.getCurrentProtectionRadius()) {
                    if (city.players.containsKey(player.getName())) {
                        return; // Defenders can use items
                    }
                    if (Conquestconfig.getCustomConfig().getConfigurationSection("conquest-attacker").getKeys(false).contains(player.getName())) {
                        event.setCancelled(true);
                        player.setItemInHand(new ItemStack(Material.BUCKET));
                        player.sendMessage(ChatColor.RED + "You cannot use this item within " + city.name + "'s protection radius during conquest!");
                        return;
                    }
                }
                return; // Allow usage outside protection radius during conquest
            }
        }

        // Normal city protection
        for (CityData city : cityData.values()) {
            double distance = Math.sqrt(Math.pow(blockX - city.locX, 2) + Math.pow(blockZ - city.locZ, 2));
            if (distance <= city.currentRadius) {
                if (city.players.containsKey(player.getName())) {
                    return;
                }
                event.setCancelled(true);
                player.setItemInHand(new ItemStack(Material.BUCKET));
                player.sendMessage(ChatColor.RED + "You cannot use this item in " + city.name + "!");
                return;
            }
        }
    }
}*/