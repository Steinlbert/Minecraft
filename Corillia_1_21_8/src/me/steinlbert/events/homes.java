package me.steinlbert.events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import me.steinlbert.configmanager.Homesconfig;

import java.util.HashMap;

public class homes implements CommandExecutor, Listener {
    private static HashMap<String, HashMap<String, Location>> playerHomes = new HashMap<>();
    private static HashMap<String, Location> lastLocations = new HashMap<>();

    public static void loadHomesData() {
        playerHomes.clear();
        if (Homesconfig.getCustomConfig() == null) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[ERROR] Homes config is null, cannot load homesData!");
            return;
        }
        ConfigurationSection homesSection = Homesconfig.getCustomConfig().getConfigurationSection("Homes");
        if (homesSection != null) {
            for (String player : homesSection.getKeys(false)) {
                if (!player.equals("settings")) {
                    HashMap<String, Location> homes = new HashMap<>();
                    ConfigurationSection playerHomesSection = homesSection.getConfigurationSection(player);
                    if (playerHomesSection != null) {
                        for (String homeName : playerHomesSection.getKeys(false)) {
                            ConfigurationSection locSection = playerHomesSection.getConfigurationSection(homeName);
                            if (locSection != null) {
                                double x = locSection.getDouble("x");
                                double y = locSection.getDouble("y");
                                double z = locSection.getDouble("z");
                                String worldName = locSection.getString("world");
                                World world = Bukkit.getServer().getWorld(worldName);
                                if (world != null) {
                                    Location loc = new Location(world, x, y, z);
                                    homes.put(homeName, loc);
                                }
                            }
                        }
                    }
                    playerHomes.put(player, homes);
                }
            }
        }
        //Bukkit.getConsoleSender().sendMessage(ChatColor.BLUE + "[DEBUG] Loaded playerHomes: " + playerHomes.toString());
    }

    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        Player player = event.getPlayer();
        String playerName = player.getName(); // Use getName() for consistency
        lastLocations.put(playerName, event.getFrom());
        //Bukkit.getConsoleSender().sendMessage(ChatColor.BLUE + "[DEBUG] Saved last location for " + playerName + ": " + event.getFrom().toString() + ", Cause: " + event.getCause());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "This command can only be used by players!");
            return true;
        }

        Player player = (Player) sender;
        String playerName = player.getName(); // Use getName() for consistency

        if (command.getName().equalsIgnoreCase("set-home")) {
            if (args.length != 1) {
                player.sendMessage(ChatColor.RED + "Usage: /set-home <name>");
                return true;
            }
            String homeName = args[0];
            String maxHomesStr = Homesconfig.getCustomConfig().getString("Homes.settings.allowed homes", "3");
            int maxHomes;
            try {
                maxHomes = Integer.parseInt(maxHomesStr);
            } catch (NumberFormatException e) {
                maxHomes = 3;
                Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[ERROR] Invalid allowed homes value: " + maxHomesStr + ", using default 3");
            }
            playerHomes.computeIfAbsent(playerName, k -> new HashMap<>());
            if (playerHomes.get(playerName).size() >= maxHomes) {
                player.sendMessage(ChatColor.RED + "You have reached the maximum number of homes (" + maxHomes + ")!");
                return true;
            }
            Location loc = player.getLocation();
            playerHomes.get(playerName).put(homeName, loc);
            ConfigurationSection homesSection = Homesconfig.getCustomConfig().createSection("Homes." + playerName + "." + homeName);
            homesSection.set("x", loc.getX());
            homesSection.set("y", loc.getY());
            homesSection.set("z", loc.getZ());
            homesSection.set("world", loc.getWorld().getName());
            Homesconfig.markConfigDirty();
            player.sendMessage(ChatColor.GREEN + "Home " + homeName + " set at " + loc.getBlockX() + ", " + loc.getBlockY() + ", " + loc.getBlockZ());
            return true;
        }

        if (command.getName().equalsIgnoreCase("home")) {
            if (args.length != 1) {
                player.sendMessage(ChatColor.RED + "Usage: /home <name>");
                return true;
            }
            String homeName = args[0];
            HashMap<String, Location> homes = playerHomes.get(playerName);
            if (homes == null || !homes.containsKey(homeName)) {
                player.sendMessage(ChatColor.RED + "Home " + homeName + " does not exist!");
                return true;
            }
            Location loc = homes.get(homeName);
            player.teleport(loc);
            player.sendMessage(ChatColor.GREEN + "Teleported to home " + homeName);
            return true;
        }

        if (command.getName().equalsIgnoreCase("list-homes")) {
            HashMap<String, Location> homes = playerHomes.get(playerName);
            if (homes == null || homes.isEmpty()) {
                player.sendMessage(ChatColor.RED + "You have no homes set!");
                return true;
            }
            player.sendMessage(ChatColor.GREEN + "Your homes:");
            for (String homeName : homes.keySet()) {
                Location loc = homes.get(homeName);
                player.sendMessage(ChatColor.YELLOW + "- " + homeName + ": " + loc.getBlockX() + ", " + loc.getBlockY() + ", " + loc.getBlockZ() + " (" + loc.getWorld().getName() + ")");
            }
            return true;
        }

        if (command.getName().equalsIgnoreCase("remove-home")) {
            if (args.length != 1) {
                player.sendMessage(ChatColor.RED + "Usage: /remove-home <name>");
                return true;
            }
            String homeName = args[0];
            HashMap<String, Location> homes = playerHomes.get(playerName);
            if (homes == null || !homes.containsKey(homeName)) {
                player.sendMessage(ChatColor.RED + "Home " + homeName + " does not exist!");
                return true;
            }
            homes.remove(homeName);
            Homesconfig.getCustomConfig().set("Homes." + playerName + "." + homeName, null);
            Homesconfig.markConfigDirty();
            player.sendMessage(ChatColor.GREEN + "Home " + homeName + " removed!");
            return true;
        }

        if (command.getName().equalsIgnoreCase("back")) {
            Location lastLoc = lastLocations.get(playerName);
            if (lastLoc == null) {
                player.sendMessage(ChatColor.RED + "No previous location found!");
                //Bukkit.getConsoleSender().sendMessage(ChatColor.BLUE + "[DEBUG] No last location for " + playerName);
                return true;
            }
            player.teleport(lastLoc);
            player.sendMessage(ChatColor.GREEN + "Teleported to previous location: " + lastLoc.getBlockX() + ", " + lastLoc.getBlockY() + ", " + lastLoc.getBlockZ());
            return true;
        }

        return false;
    }
}