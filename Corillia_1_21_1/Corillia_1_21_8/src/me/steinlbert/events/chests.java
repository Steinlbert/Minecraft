package me.steinlbert.events;


import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.block.Block;
import org.bukkit.Location;
import org.bukkit.Material;

import me.steinlbert.configmanager.Chestconfig;

public class chests implements CommandExecutor {
    public static HashMap<String, String> playerSettings = new HashMap<>();
    private static boolean privateChestsDisabled = false;

    public static String locationToString(final Location loc) {
        return loc.getWorld().getName() + "," + loc.getBlockX() + "," + loc.getBlockY() + "," + loc.getBlockZ();
    }
    
    public static String locationToStringdoor1(final Location loc) {
    	int locY = loc.getBlockY() +1;
        return loc.getWorld().getName() + "," + loc.getBlockX() + "," + locY + "," + loc.getBlockZ();
    }
    
    public static String locationToStringdoor2(final Location loc) {
    	int locY = loc.getBlockY() -1;
        return loc.getWorld().getName() + "," + loc.getBlockX() + "," + locY + "," + loc.getBlockZ();
    }

    public static Location stringToLocation(final String string) {
        final String[] split = string.split(",");
        return new Location(Bukkit.getWorld(split[0]), Double.parseDouble(split[1]), Double.parseDouble(split[2]), Double.parseDouble(split[3]));
    }

    public static void loadPlayerSettings() {
        playerSettings.clear();
        ConfigurationSection players = Chestconfig.getCustomConfig().getConfigurationSection("settings.players");
        if (players != null) {
            for (String playerName : players.getKeys(false)) {
                String value = Chestconfig.getCustomConfig().getString("settings.players." + playerName);
                if (value != null) {
                    playerSettings.put(playerName, value);
                }
            }
        }
        //Bukkit.getConsoleSender().sendMessage(ChatColor.BLUE + "[DEBUG] Loaded playerSettings: " + playerSettings.toString());
    }

    public static void setPrivateChestsDisabled(boolean disabled) {
        privateChestsDisabled = disabled;
        //Bukkit.getConsoleSender().sendMessage(ChatColor.BLUE + "[DEBUG] Private chests and doors " + (disabled ? "disabled" : "enabled") + " for conquest");
        if (disabled) {
            Bukkit.broadcastMessage(ChatColor.YELLOW + "Private chest and door protection disabled during conquest!");
        } else {
            Bukkit.broadcastMessage(ChatColor.YELLOW + "Private chest and door protection re-enabled!");
        }
    }

    public static boolean isPrivateChestsDisabled() {
        return privateChestsDisabled;
    }

    public static boolean isChest(Block block) {
        return block.getType() == Material.CHEST || block.getType() == Material.TRAPPED_CHEST;
    }

    public static boolean isDoor(Block block) {
        return block.getType().toString().endsWith("DOOR");
    }

    @Override
    public boolean onCommand(CommandSender cs, Command cmd, String string, String[] args) {
    	
    	ConfigurationSection playersettings = Chestconfig.getCustomConfig().getConfigurationSection("settings.players");
    	
        if (!(cs instanceof Player)) {
            cs.sendMessage(ChatColor.RED + "Only players can use this command!");
            return true;
        }

        Player p = (Player) cs;
        //p.sendMessage(ChatColor.BLUE + "[DEBUG] Command: " + string + ", Args: " + Arrays.toString(args));

        if (ConquestManager.isConquestActive() && privateChestsDisabled) {
            p.sendMessage(ChatColor.RED + "Private chest and door commands are disabled during conquest!");
            return true;
        }

        if (string.equalsIgnoreCase("private")) {
            if (args.length == 0) {
                playerSettings.put(p.getDisplayName(), "1");
                Chestconfig.getCustomConfig().set("settings.players." + p.getDisplayName(), "1");
                Chestconfig.markConfigDirty();
                p.sendMessage(ChatColor.YELLOW + "Click a chest or door to lock it!");
                //p.sendMessage(ChatColor.BLUE + "[DEBUG] Set playerSettings." + p.getDisplayName() + " to 1");
                return true;
            }

            if (args.length == 1) {
                p.sendMessage(ChatColor.LIGHT_PURPLE + "Available commands: [/private] [/private remove] [/private add <player>]");
                return true;
            }

            if (args.length == 2) {
                if (args[0].equalsIgnoreCase("add")) {
                    if (args[1] != null && !args[1].isEmpty()) {
                        String playerName = args[1];
                        playerSettings.put(p.getDisplayName(), playerName);
                        playerSettings.put(p.getDisplayName() + "_add", playerName);
                        playersettings.set(p.getDisplayName(), playerName);
                        playersettings.set(p.getDisplayName() + "_add", playerName);
                        Chestconfig.markConfigDirty();
                        p.sendMessage(ChatColor.YELLOW + "Click a chest or door to add the player " + playerName + "!");
                        //p.sendMessage(ChatColor.BLUE + "[DEBUG] Set playerSettings." + p.getDisplayName() + " to " + playerName + " for add");
                    } else {
                        p.sendMessage(ChatColor.RED + "You need to specify a player!");
                    }
                    return true;
                }

                if (args[0].equalsIgnoreCase("remove")) {
                    if (args[1] != null && !args[1].isEmpty()) {
                        if (args[1].equalsIgnoreCase("all")) {
                            playerSettings.put(p.getDisplayName(), "all");
                            playerSettings.put(p.getDisplayName() + "_remove", "all");
                            playersettings.set(p.getDisplayName(), "all");
                            playersettings.set(p.getDisplayName() + "_remove", "all");
                            Chestconfig.markConfigDirty();
                            p.sendMessage(ChatColor.YELLOW + "Click a chest or door to remove all permissions!");
                            //p.sendMessage(ChatColor.BLUE + "[DEBUG] Set playerSettings." + p.getDisplayName() + " to all for remove");
                        } else {
                            String playerName = args[1];
                            playerSettings.put(p.getDisplayName(), playerName);
                            playerSettings.put(p.getDisplayName() + "_remove", playerName);
                            playersettings.set(p.getDisplayName(), playerName);
                            playersettings.set(p.getDisplayName() + "_remove", playerName);
                            Chestconfig.markConfigDirty();
                            p.sendMessage(ChatColor.YELLOW + "Click a chest or door to remove the player " + playerName + "!");
                            //p.sendMessage(ChatColor.BLUE + "[DEBUG] Set playerSettings." + p.getDisplayName() + " to " + playerName + " for remove");
                        }
                    } else {
                        p.sendMessage(ChatColor.YELLOW + "[/private remove <player>] - removes the player!");
                        p.sendMessage(ChatColor.YELLOW + "[/private remove all] - removes all permissions!");
                    }
                    return true;
                }
            }

            p.sendMessage(ChatColor.LIGHT_PURPLE + "Too many arguments! [/private add <player>] or [/private remove <player>] only one player at a time");
            return true;
        }

        return false;
    }
}