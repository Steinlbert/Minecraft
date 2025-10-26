package me.steinlbert.events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World.Environment;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import me.steinlbert.configmanager.Cityconfig;
import me.steinlbert.configmanager.Conquestconfig;

import java.util.HashMap;

public class cmd implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command com, String text, String[] arguments) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "This command can only be used by players!");
            return true;
        }

        Player player = (Player) sender;
        if (!player.isOp()) {
            player.sendMessage(ChatColor.RED + "You need to be an operator to use this command!");
            return true;
        }

        if (text.equalsIgnoreCase("corilia-config")) {
            player.sendMessage(ChatColor.GREEN + "Cities:");
            boolean hasCities = false;
            ConfigurationSection cities = Cityconfig.getCustomConfig();
            for (String cityName : cities.getKeys(false)) {
                ConfigurationSection city = cities.getConfigurationSection(cityName);
                if (city != null) {
                    int locX = city.getInt("LocX");
                    int locZ = city.getInt("LocZ");
                    int radius = city.getInt("radius", 50);
                    player.sendMessage(ChatColor.YELLOW + "- " + cityName + ": X=" + locX + ", Z=" + locZ + ", Radius=" + radius);
                    hasCities = true;
                }
            }
            if (!hasCities) {
                player.sendMessage(ChatColor.RED + "No cities found!");
            }
            return true;
        }

        if (text.equalsIgnoreCase("corilia-radius")) {
            if (arguments.length != 2) {
                player.sendMessage(ChatColor.RED + "Usage: /corilia-radius <city> <radius>");
                return true;
            }
            String cityName = arguments[0];
            String matchedCity = null;
            for (String key : Cityconfig.getCustomConfig().getKeys(false)) {
                if (key.equalsIgnoreCase(cityName)) {
                    matchedCity = key;
                    break;
                }
            }
            if (matchedCity == null) {
                player.sendMessage(ChatColor.RED + "City " + cityName + " not found!");
                return true;
            }
            try {
                int radius = Integer.parseInt(arguments[1]);
                if (radius < 10 || radius > 100) {
                    player.sendMessage(ChatColor.RED + "Radius must be between 10 and 100!");
                    return true;
                }
                ConfigurationSection city = Cityconfig.getCustomConfig().getConfigurationSection(matchedCity);
                city.set("radius", radius);
                Cityconfig.markConfigDirty();
                Cityconfig.saveCustomConfig();
                player.sendMessage(ChatColor.GREEN + "Set radius for " + matchedCity + " to " + radius);
                land.loadCityData();
            } catch (NumberFormatException e) {
                player.sendMessage(ChatColor.RED + "Radius must be a number!");
            }
            return true;
        }

        if (text.equalsIgnoreCase("corilia-add-player")) {
            if (arguments.length != 2) {
                player.sendMessage(ChatColor.RED + "Usage: /corilia-add-player <city> <player>");
                return true;
            }
            String cityName = arguments[0];
            String matchedCity = null;
            for (String key : Cityconfig.getCustomConfig().getKeys(false)) {
                if (key.equalsIgnoreCase(cityName)) {
                    matchedCity = key;
                    break;
                }
            }
            if (matchedCity == null) {
                player.sendMessage(ChatColor.RED + "City " + cityName + " not found!");
                return true;
            }
            String addPlayer = arguments[1];
            ConfigurationSection city = Cityconfig.getCustomConfig().getConfigurationSection(matchedCity);
            city.set("Spieler." + addPlayer, addPlayer);
            Cityconfig.markConfigDirty();
            Cityconfig.saveCustomConfig();
            player.sendMessage(ChatColor.GREEN + "Added " + addPlayer + " to " + matchedCity);
            land.loadCityData();
            return true;
        }

        if (text.equalsIgnoreCase("corilia-remove-player")) {
            if (arguments.length != 2) {
                player.sendMessage(ChatColor.RED + "Usage: /corilia-remove-player <city> <player>");
                return true;
            }
            String cityName = arguments[0];
            String matchedCity = null;
            for (String key : Cityconfig.getCustomConfig().getKeys(false)) {
                if (key.equalsIgnoreCase(cityName)) {
                    matchedCity = key;
                    break;
                }
            }
            if (matchedCity == null) {
                player.sendMessage(ChatColor.RED + "City " + cityName + " not found!");
                return true;
            }
            String removePlayer = arguments[1];
            ConfigurationSection city = Cityconfig.getCustomConfig().getConfigurationSection(matchedCity);
            city.set("Spieler." + removePlayer, null);
            Cityconfig.markConfigDirty();
            Cityconfig.saveCustomConfig();
            player.sendMessage(ChatColor.GREEN + "Removed " + removePlayer + " from " + matchedCity);
            land.loadCityData();
            return true;
        }

        if (text.equalsIgnoreCase("corilia-remove-city")) {
            if (arguments.length != 1) {
                player.sendMessage(ChatColor.RED + "Usage: /corilia-remove-city <city>");
                return true;
            }
            String cityName = arguments[0];
            String matchedCity = null;
            for (String key : Cityconfig.getCustomConfig().getKeys(false)) {
                if (key.equalsIgnoreCase(cityName)) {
                    matchedCity = key;
                    break;
                }
            }
            if (matchedCity == null) {
                player.sendMessage(ChatColor.RED + "City " + cityName + " not found!");
                return true;
            }
            Cityconfig.getCustomConfig().set(matchedCity, null);
            Cityconfig.markConfigDirty();
            Cityconfig.saveCustomConfig();
            player.sendMessage(ChatColor.GREEN + "Removed city " + matchedCity);
            land.loadCityData();
            return true;
        }

        if (text.equalsIgnoreCase("corilia-new-city")) {
            if (arguments.length != 1) {
                player.sendMessage(ChatColor.RED + "Usage: /corilia-new-city <city>");
                return true;
            }
            String cityName = arguments[0];
            if (Cityconfig.getCustomConfig().get(cityName) != null) {
                player.sendMessage(ChatColor.RED + "City " + cityName + " already exists!");
                return true;
            }
            Location loc = player.getLocation();
            if (loc.getWorld().getEnvironment() != Environment.NORMAL) {
                player.sendMessage(ChatColor.RED + "Cannot create city in Nether or End!");
                return true;
            }
            int locX = loc.getBlockX();
            int locY = loc.getBlockY();
            int locZ = loc.getBlockZ();
            int defaultRadius = 50;
            ConfigurationSection city = Cityconfig.getCustomConfig().createSection(cityName);
            city.set("LocX", locX);
            city.set("LocY", locY);
            city.set("LocZ", locZ);
            city.set("Spieler", new HashMap<>());
            city.set("radius", defaultRadius);
            Cityconfig.markConfigDirty();
            Cityconfig.saveCustomConfig();
            player.sendMessage(ChatColor.GREEN + "Created city " + cityName + " with radius " + defaultRadius);
            land.loadCityData();
            return true;
        }

        if (text.equalsIgnoreCase("conquest")) {
            if (arguments.length < 1) {
                player.sendMessage(ChatColor.RED + "Usage: /conquest <city> <on|off> | /conquest add-attacker <player> | /conquest add-defender <player> | /conquest remove-attacker <player> | /conquest remove-defender <player> | /conquest list-teams | /conquest set-border <size> | /conquest boarder-shrinking-rate <rate> | /conquest boarder-shrinking-time <minutes> | /conquest protection-shrinking-time <minutes> | /conquest protection-shrinking-amount <blocks> | /conquest death-spectator <on|off> | /conquest settings");
                return true;
            }
            if (arguments[0].equalsIgnoreCase("off") && arguments.length == 1) {
                boolean anyStopped = false;
                ConfigurationSection cities = Cityconfig.getCustomConfig();
                for (String cityName : cities.getKeys(false)) {
                    if (ConquestManager.isConquestActive()) {
                        ConquestManager.stopConquest(cityName, cityName, player, player.getWorld());
                        anyStopped = true;
                    }
                }
                if (anyStopped) {
                    player.sendMessage(ChatColor.GREEN + "All active conquests stopped!");
                } else {
                    player.sendMessage(ChatColor.RED + "No active conquests found!");
                }
                return true;
            }
            if (arguments[0].equalsIgnoreCase("set-border")) {
                if (arguments.length != 2) {
                    player.sendMessage(ChatColor.RED + "Usage: /conquest set-border <size>");
                    return true;
                }
                try {
                    int size = Integer.parseInt(arguments[1]);
                    if (size < 100 || size > 5000) {
                        player.sendMessage(ChatColor.RED + "Border size must be between 100 and 5000!");
                        return true;
                    }
                    Conquestconfig.getCustomConfig().set("conquest.settings.boarder-size", size);
                    Conquestconfig.markConfigDirty();
                    Conquestconfig.saveCustomConfig();
                    ConquestManager.updateSettings();
                    player.sendMessage(ChatColor.GREEN + "Set conquest border size to " + size);
                    Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "[Conquest] Border size set to " + size + " by " + player.getName());
                } catch (NumberFormatException e) {
                    player.sendMessage(ChatColor.RED + "Size must be a number!");
                }
                return true;
            }
            if (arguments[0].equalsIgnoreCase("boarder-shrinking-rate")) {
                if (arguments.length != 2) {
                    player.sendMessage(ChatColor.RED + "Usage: /conquest boarder-shrinking-rate <rate>");
                    return true;
                }
                try {
                    int rate = Integer.parseInt(arguments[1]);
                    if (rate < 1 || rate > 100) {
                        player.sendMessage(ChatColor.RED + "Shrinking rate must be between 1 and 100!");
                        return true;
                    }
                    Conquestconfig.getCustomConfig().set("conquest.settings.boarder-shrinking-rate", rate);
                    Conquestconfig.markConfigDirty();
                    Conquestconfig.saveCustomConfig();
                    ConquestManager.updateSettings();
                    player.sendMessage(ChatColor.GREEN + "Set boarder shrinking rate to " + rate + " blocks");
                    Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "[Conquest] Boarder shrinking rate set to " + rate + " by " + player.getName());
                } catch (NumberFormatException e) {
                    player.sendMessage(ChatColor.RED + "Rate must be a number!");
                }
                return true;
            }
            if (arguments[0].equalsIgnoreCase("boarder-shrinking-time")) {
                if (arguments.length != 2) {
                    player.sendMessage(ChatColor.RED + "Usage: /conquest boarder-shrinking-time <minutes>");
                    return true;
                }
                try {
                    int minutes = Integer.parseInt(arguments[1]);
                    if (minutes < 1 || minutes > 60) {
                        player.sendMessage(ChatColor.RED + "Shrinking time must be between 1 and 60 minutes!");
                        return true;
                    }
                    Conquestconfig.getCustomConfig().set("conquest.settings.boarder-shrinking-time", minutes);
                    Conquestconfig.markConfigDirty();
                    Conquestconfig.saveCustomConfig();
                    ConquestManager.updateSettings();
                    player.sendMessage(ChatColor.GREEN + "Set boarder shrinking time to " + minutes + " minutes");
                    Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "[Conquest] Boarder shrinking time set to " + minutes + " by " + player.getName());
                } catch (NumberFormatException e) {
                    player.sendMessage(ChatColor.RED + "Minutes must be a number!");
                }
                return true;
            }
            if (arguments[0].equalsIgnoreCase("protection-shrinking-time")) {
                if (arguments.length != 2) {
                    player.sendMessage(ChatColor.RED + "Usage: /conquest protection-shrinking-time <minutes>");
                    return true;
                }
                try {
                    int minutes = Integer.parseInt(arguments[1]);
                    if (minutes < 1 || minutes > 60) {
                        player.sendMessage(ChatColor.RED + "Shrinking time must be between 1 and 60 minutes!");
                        return true;
                    }
                    Conquestconfig.getCustomConfig().set("conquest.settings.protection-shrinking-time", minutes);
                    Conquestconfig.markConfigDirty();
                    Conquestconfig.saveCustomConfig();
                    ConquestManager.updateSettings();
                    player.sendMessage(ChatColor.GREEN + "Set protection shrinking time to " + minutes + " minutes");
                    Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "[Conquest] Protection shrinking time set to " + minutes + " by " + player.getName());
                } catch (NumberFormatException e) {
                    player.sendMessage(ChatColor.RED + "Minutes must be a number!");
                }
                return true;
            }
            if (arguments[0].equalsIgnoreCase("protection-shrinking-amount")) {
                if (arguments.length != 2) {
                    player.sendMessage(ChatColor.RED + "Usage: /conquest protection-shrinking-amount <blocks>");
                    return true;
                }
                try {
                    int blocks = Integer.parseInt(arguments[1]);
                    if (blocks < 1 || blocks > 50) {
                        player.sendMessage(ChatColor.RED + "Shrinking amount must be between 1 and 50 blocks!");
                        return true;
                    }
                    Conquestconfig.getCustomConfig().set("conquest.settings.protection-shrinking-amount", blocks);
                    Conquestconfig.markConfigDirty();
                    Conquestconfig.saveCustomConfig();
                    ConquestManager.updateSettings();
                    player.sendMessage(ChatColor.GREEN + "Set protection shrinking amount to " + blocks + " blocks");
                    Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "[Conquest] Protection shrinking amount set to " + blocks + " by " + player.getName());
                } catch (NumberFormatException e) {
                    player.sendMessage(ChatColor.RED + "Blocks must be a number!");
                }
                return true;
            }
            if (arguments[0].equalsIgnoreCase("death-spectator")) {
                if (arguments.length != 2 || (!arguments[1].equalsIgnoreCase("on") && !arguments[1].equalsIgnoreCase("off"))) {
                    player.sendMessage(ChatColor.RED + "Usage: /conquest death-spectator <on|off>");
                    return true;
                }
                String state = arguments[1].toLowerCase();
                Conquestconfig.getCustomConfig().set("conquest.settings.death-spectator", state);
                Conquestconfig.markConfigDirty();
                Conquestconfig.saveCustomConfig();
                ConquestManager.updateSettings();
                player.sendMessage(ChatColor.GREEN + "Set death spectator mode to " + state);
                Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "[Conquest] Death spectator mode set to " + state + " by " + player.getName());
                return true;
            }
            if (arguments[0].equalsIgnoreCase("settings")) {
                ConfigurationSection settings = Conquestconfig.getCustomConfig().getConfigurationSection("conquest.settings");
                player.sendMessage(ChatColor.GREEN + "Conquest Settings:");
                player.sendMessage(ChatColor.YELLOW + "- Active City: " + (settings.getString("active-city", "None")));
                player.sendMessage(ChatColor.YELLOW + "- Conquest Active: " + (settings.getBoolean("conquest-in-progress", false) ? "Yes" : "No"));
                player.sendMessage(ChatColor.YELLOW + "- Border Size: " + settings.getInt("boarder-size", 400) + " blocks");
                player.sendMessage(ChatColor.YELLOW + "- Border Shrinking Rate: " + settings.getInt("boarder-shrinking-rate", 10) + " blocks");
                player.sendMessage(ChatColor.YELLOW + "- Border Shrinking Time: " + settings.getInt("boarder-shrinking-time", 5) + " minutes");
                player.sendMessage(ChatColor.YELLOW + "- Protection Shrinking Time: " + settings.getInt("protection-shrinking-time", 1) + " minutes");
                player.sendMessage(ChatColor.YELLOW + "- Protection Shrinking Amount: " + settings.getInt("protection-shrinking-amount", 5) + " blocks");
                player.sendMessage(ChatColor.YELLOW + "- Protection Radius: " + settings.getInt("conquest-radius", 50) + " blocks");
                player.sendMessage(ChatColor.YELLOW + "- Death Spectator: " + settings.getString("death-spectator", "off"));
                return true;
            }
            if (arguments[0].equalsIgnoreCase("add-attacker")) {
                if (arguments.length != 2) {
                    player.sendMessage(ChatColor.RED + "Usage: /conquest add-attacker <player>");
                    return true;
                }
                String addPlayer = arguments[1];
                if (Conquestconfig.getCustomConfig().getConfigurationSection("conquest-defender").getKeys(false).contains(addPlayer)) {
                    player.sendMessage(ChatColor.RED + addPlayer + " is already a defender!");
                    return true;
                }
                Conquestconfig.getCustomConfig().set("conquest-attacker." + addPlayer, addPlayer);
                Conquestconfig.markConfigDirty();
                Conquestconfig.saveCustomConfig();
                player.sendMessage(ChatColor.GREEN + "Added " + addPlayer + " to conquest attackers");
                return true;
            }
            if (arguments[0].equalsIgnoreCase("add-defender")) {
                if (arguments.length != 2) {
                    player.sendMessage(ChatColor.RED + "Usage: /conquest add-defender <player>");
                    return true;
                }
                String addPlayer = arguments[1];
                if (Conquestconfig.getCustomConfig().getConfigurationSection("conquest-attacker").getKeys(false).contains(addPlayer)) {
                    player.sendMessage(ChatColor.RED + addPlayer + " is already an attacker!");
                    return true;
                }
                Conquestconfig.getCustomConfig().set("conquest-defender." + addPlayer, addPlayer);
                Conquestconfig.markConfigDirty();
                Conquestconfig.saveCustomConfig();
                player.sendMessage(ChatColor.GREEN + "Added " + addPlayer + " to conquest defenders");
                return true;
            }
            if (arguments[0].equalsIgnoreCase("remove-attacker")) {
                if (arguments.length != 2) {
                    player.sendMessage(ChatColor.RED + "Usage: /conquest remove-attacker <player>");
                    return true;
                }
                String removePlayer = arguments[1];
                if (Conquestconfig.getCustomConfig().get("conquest-attacker." + removePlayer) == null) {
                    player.sendMessage(ChatColor.RED + removePlayer + " is not an attacker!");
                    return true;
                }
                Conquestconfig.getCustomConfig().set("conquest-attacker." + removePlayer, null);
                Conquestconfig.markConfigDirty();
                Conquestconfig.saveCustomConfig();
                player.sendMessage(ChatColor.GREEN + "Removed " + removePlayer + " from conquest attackers");
                return true;
            }
            if (arguments[0].equalsIgnoreCase("remove-defender")) {
                if (arguments.length != 2) {
                    player.sendMessage(ChatColor.RED + "Usage: /conquest remove-defender <player>");
                    return true;
                }
                String removePlayer = arguments[1];
                if (Conquestconfig.getCustomConfig().get("conquest-defender." + removePlayer) == null) {
                    player.sendMessage(ChatColor.RED + removePlayer + " is not a defender!");
                    return true;
                }
                Conquestconfig.getCustomConfig().set("conquest-defender." + removePlayer, null);
                Conquestconfig.markConfigDirty();
                Conquestconfig.saveCustomConfig();
                player.sendMessage(ChatColor.GREEN + "Removed " + removePlayer + " from conquest defenders");
                return true;
            }
            if (arguments[0].equalsIgnoreCase("list-teams")) {
                player.sendMessage(ChatColor.GREEN + "Conquest Teams:");
                player.sendMessage(ChatColor.YELLOW + "Attackers:");
                ConfigurationSection attackers = Conquestconfig.getCustomConfig().getConfigurationSection("conquest-attacker");
                if (attackers != null && !attackers.getKeys(false).isEmpty()) {
                    for (String attacker : attackers.getKeys(false)) {
                        player.sendMessage(ChatColor.YELLOW + "- " + attacker);
                    }
                } else {
                    player.sendMessage(ChatColor.RED + "No attackers!");
                }
                player.sendMessage(ChatColor.YELLOW + "Defenders:");
                ConfigurationSection defenders = Conquestconfig.getCustomConfig().getConfigurationSection("conquest-defender");
                if (defenders != null && !defenders.getKeys(false).isEmpty()) {
                    for (String defender : defenders.getKeys(false)) {
                        player.sendMessage(ChatColor.YELLOW + "- " + defender);
                    }
                } else {
                    player.sendMessage(ChatColor.RED + "No defenders!");
                }
                return true;
            }
            if (arguments.length != 2 || (!arguments[1].equalsIgnoreCase("on") && !arguments[1].equalsIgnoreCase("off"))) {
                player.sendMessage(ChatColor.RED + "Usage: /conquest <city> <on|off>");
                return true;
            }
            String cityName = arguments[0];
            String matchedCity = null;
            for (String key : Cityconfig.getCustomConfig().getKeys(false)) {
                if (key.equalsIgnoreCase(cityName)) {
                    matchedCity = key;
                    break;
                }
            }
            if (matchedCity == null) {
                player.sendMessage(ChatColor.RED + "City " + cityName + " not found!");
                player.sendMessage(ChatColor.YELLOW + "[Conquest] Available cities: " + Cityconfig.getCustomConfig().getKeys(false).toString());
                return true;
            }
            boolean enable = arguments[1].equalsIgnoreCase("on");
            ConfigurationSection city = Cityconfig.getCustomConfig().getConfigurationSection(matchedCity);
            int centerX = city.getInt("LocX");
            int centerZ = city.getInt("LocZ");
            int radius = city.getInt("radius", 50);
            if (enable) {
                ConquestManager.startConquest(matchedCity, player, player.getWorld(), centerX, centerZ);
                Conquestconfig.getCustomConfig().set("conquest.settings.conquest-set", "on");
                Conquestconfig.getCustomConfig().set("conquest.settings.conquest-radius", radius);
                Conquestconfig.getCustomConfig().set("conquest.settings.active-city", matchedCity);
                Conquestconfig.getCustomConfig().set("conquest.settings.active-cities.city1.name", matchedCity);
                Conquestconfig.getCustomConfig().set("conquest.settings.active-cities.city1.centerX", centerX);
                Conquestconfig.getCustomConfig().set("conquest.settings.active-cities.city1.centerZ", centerZ);
                Conquestconfig.getCustomConfig().set("conquest.settings.active-cities.city1.world", player.getWorld().getName());
            } else {
                ConquestManager.stopConquest(matchedCity, matchedCity, player, player.getWorld());
            }
            Conquestconfig.markConfigDirty();
            Conquestconfig.saveCustomConfig();
            return true;
        }

        return false;
    }
}

/*
public class cmd implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command com, String text, String[] arguments) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "This command can only be used by players!");
            return true;
        }

        Player player = (Player) sender;
        if (!player.isOp()) {
            player.sendMessage(ChatColor.RED + "You need to be an operator to use this command!");
            return true;
        }

        if (text.equalsIgnoreCase("corilia-config")) {
            player.sendMessage(ChatColor.GREEN + "Cities:");
            boolean hasCities = false;
            for (int i = 1; i <= 10; i++) {
                String cityKey = "City" + i;
                ConfigurationSection city = Cityconfig.getCustomConfig().getConfigurationSection(cityKey);
                if (city != null) {
                    String name = city.getString("Stadtname");
                    int locX = city.getInt("LocX");
                    int locZ = city.getInt("LocZ");
                    int radius = city.getInt("radius", 50);
                    player.sendMessage(ChatColor.YELLOW + "- " + name + ": X=" + locX + ", Z=" + locZ + ", Radius=" + radius);
                    hasCities = true;
                }
            }
            if (!hasCities) {
                player.sendMessage(ChatColor.RED + "No cities found!");
            }
            return true;
        }

        if (text.equalsIgnoreCase("corilia-radius")) {
            if (arguments.length != 2) {
                player.sendMessage(ChatColor.RED + "Usage: /corilia-radius <city> <radius>");
                return true;
            }
            String cityName = arguments[0];
            try {
                int radius = Integer.parseInt(arguments[1]);
                if (radius < 10 || radius > 100) {
                    player.sendMessage(ChatColor.RED + "Radius must be between 10 and 100!");
                    return true;
                }
                boolean found = false;
                for (int i = 1; i <= 10; i++) {
                    String cityKey = "City" + i;
                    ConfigurationSection city = Cityconfig.getCustomConfig().getConfigurationSection(cityKey);
                    if (city != null && city.getString("Stadtname").equalsIgnoreCase(cityName)) {
                        city.set("radius", radius);
                        Cityconfig.markConfigDirty();
                        Cityconfig.saveCustomConfig();
                        player.sendMessage(ChatColor.GREEN + "Set radius for " + cityName + " to " + radius);
                        land.loadCityData();
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    player.sendMessage(ChatColor.RED + "City " + cityName + " not found!");
                }
            } catch (NumberFormatException e) {
                player.sendMessage(ChatColor.RED + "Radius must be a number!");
            }
            return true;
        }

        if (text.equalsIgnoreCase("corilia-add-player")) {
            if (arguments.length != 2) {
                player.sendMessage(ChatColor.RED + "Usage: /corilia-add-player <city> <player>");
                return true;
            }
            String cityName = arguments[0];
            String addPlayer = arguments[1];
            boolean found = false;
            for (int i = 1; i <= 10; i++) {
                String cityKey = "City" + i;
                ConfigurationSection city = Cityconfig.getCustomConfig().getConfigurationSection(cityKey);
                if (city != null && city.getString("Stadtname").equalsIgnoreCase(cityName)) {
                    city.set("Spieler." + addPlayer, addPlayer);
                    Cityconfig.markConfigDirty();
                    Cityconfig.saveCustomConfig();
                    player.sendMessage(ChatColor.GREEN + "Added " + addPlayer + " to " + cityName);
                    land.loadCityData();
                    found = true;
                    break;
                }
            }
            if (!found) {
                player.sendMessage(ChatColor.RED + "City " + cityName + " not found!");
            }
            return true;
        }

        if (text.equalsIgnoreCase("corilia-remove-player")) {
            if (arguments.length != 2) {
                player.sendMessage(ChatColor.RED + "Usage: /corilia-remove-player <city> <player>");
                return true;
            }
            String cityName = arguments[0];
            String removePlayer = arguments[1];
            boolean found = false;
            for (int i = 1; i <= 10; i++) {
                String cityKey = "City" + i;
                ConfigurationSection city = Cityconfig.getCustomConfig().getConfigurationSection(cityKey);
                if (city != null && city.getString("Stadtname").equalsIgnoreCase(cityName)) {
                    city.set("Spieler." + removePlayer, null);
                    Cityconfig.markConfigDirty();
                    Cityconfig.saveCustomConfig();
                    player.sendMessage(ChatColor.GREEN + "Removed " + removePlayer + " from " + cityName);
                    land.loadCityData();
                    found = true;
                    break;
                }
            }
            if (!found) {
                player.sendMessage(ChatColor.RED + "City " + cityName + " not found!");
            }
            return true;
        }

        if (text.equalsIgnoreCase("corilia-remove-city")) {
            if (arguments.length != 1) {
                player.sendMessage(ChatColor.RED + "Usage: /corilia-remove-city <city>");
                return true;
            }
            String cityName = arguments[0];
            boolean found = false;
            for (int i = 1; i <= 10; i++) {
                String cityKey = "City" + i;
                ConfigurationSection city = Cityconfig.getCustomConfig().getConfigurationSection(cityKey);
                if (city != null && city.getString("Stadtname").equalsIgnoreCase(cityName)) {
                    Cityconfig.getCustomConfig().set(cityKey, null);
                    Cityconfig.markConfigDirty();
                    Cityconfig.saveCustomConfig();
                    player.sendMessage(ChatColor.GREEN + "Removed city " + cityName);
                    land.loadCityData();
                    found = true;
                    break;
                }
            }
            if (!found) {
                player.sendMessage(ChatColor.RED + "City " + cityName + " not found!");
            }
            return true;
        }

        if (text.equalsIgnoreCase("corilia-new-city")) {
            if (arguments.length != 1) {
                player.sendMessage(ChatColor.RED + "Usage: /corilia-new-city <city>");
                return true;
            }
            String cityName = arguments[0];
            Location loc = player.getLocation();
            if (loc.getWorld().getEnvironment() != Environment.NORMAL) {
                player.sendMessage(ChatColor.RED + "Cannot create city in Nether or End!");
                return true;
            }
            int locX = loc.getBlockX();
            int locY = loc.getBlockY();
            int locZ = loc.getBlockZ();
            int defaultRadius = 50;
            for (int i = 1; i <= 10; i++) {
                String cityKey = "City" + i;
                if (Cityconfig.getCustomConfig().get(cityKey) == null) {
                    ConfigurationSection city = Cityconfig.getCustomConfig().createSection(cityKey);
                    city.set("LocX", locX);
                    city.set("LocY", locY);
                    city.set("LocZ", locZ);
                    city.set("Stadtname", cityName);
                    city.set("Spieler", new HashMap<>());
                    city.set("radius", defaultRadius);
                    Cityconfig.markConfigDirty();
                    Cityconfig.saveCustomConfig();
                    player.sendMessage(ChatColor.GREEN + "Created city " + cityName + " with radius " + defaultRadius);
                    land.loadCityData();
                    return true;
                }
            }
            player.sendMessage(ChatColor.RED + "Cannot create city: Maximum of 10 cities reached!");
            return true;
        }

        if (text.equalsIgnoreCase("conquest")) {
            if (arguments.length < 1) {
                player.sendMessage(ChatColor.RED + "Usage: /conquest <city> <on|off> | /conquest add-attacker <player> | /conquest add-defender <player> | /conquest remove-attacker <player> | /conquest remove-defender <player> | /conquest list-teams | /conquest set-border <size> | /conquest boarder-shrinking-rate <rate> | /conquest death-spectator <on|off> | /conquest protection-shrinking-time <time> | /conquest protection-shrinking-amount <amount> | /conquest conquest-radius <radius>");
                return true;
            }
            if (arguments[0].equalsIgnoreCase("off") && arguments.length == 1) {
                boolean anyStopped = false;
                for (int i = 1; i <= 10; i++) {
                    String cityKey = "City" + i;
                    ConfigurationSection city = Cityconfig.getCustomConfig().getConfigurationSection(cityKey);
                    if (city != null && ConquestManager.isConquestActive()) {
                        ConquestManager.stopConquest(cityKey, city.getString("Stadtname"), player, player.getWorld());
                        anyStopped = true;
                    }
                }
                if (anyStopped) {
                    player.sendMessage(ChatColor.GREEN + "All active conquests stopped!");
                } else {
                    player.sendMessage(ChatColor.RED + "No active conquests found!");
                }
                return true;
            }
            if (arguments[0].equalsIgnoreCase("set-border")) {
                if (arguments.length != 2) {
                    player.sendMessage(ChatColor.RED + "Usage: /conquest set-border <size>");
                    return true;
                }
                try {
                    int size = Integer.parseInt(arguments[1]);
                    if (size < 100 || size > 5000) {
                        player.sendMessage(ChatColor.RED + "Border size must be between 100 and 5000!");
                        return true;
                    }
                    Conquestconfig.getCustomConfig().set("conquest.settings.boarder-size", size);
                    Conquestconfig.markConfigDirty();
                    Conquestconfig.saveCustomConfig();
                    player.sendMessage(ChatColor.GREEN + "Set conquest border size to " + size);
                } catch (NumberFormatException e) {
                    player.sendMessage(ChatColor.RED + "Size must be a number!");
                }
                return true;
            }
            if (arguments[0].equalsIgnoreCase("boarder-shrinking-rate")) {
                if (arguments.length != 2) {
                    player.sendMessage(ChatColor.RED + "Usage: /conquest boarder-shrinking-rate <rate>");
                    return true;
                }
                try {
                    int rate = Integer.parseInt(arguments[1]);
                    if (rate < 10 || rate > 100) {
                        player.sendMessage(ChatColor.RED + "Border shrinking rate must be between 10 and 100!");
                        return true;
                    }
                    Conquestconfig.getCustomConfig().set("conquest.settings.boarder-shrinking-rate", rate);
                    Conquestconfig.markConfigDirty();
                    Conquestconfig.saveCustomConfig();
                    player.sendMessage(ChatColor.GREEN + "Set border shrinking rate to " + rate);
                } catch (NumberFormatException e) {
                    player.sendMessage(ChatColor.RED + "Rate must be a number!");
                }
                return true;
            }
            if (arguments[0].equalsIgnoreCase("death-spectator")) {
                if (arguments.length != 2 || (!arguments[1].equalsIgnoreCase("on") && !arguments[1].equalsIgnoreCase("off"))) {
                    player.sendMessage(ChatColor.RED + "Usage: /conquest death-spectator <on|off>");
                    return true;
                }
                String value = arguments[1].toLowerCase();
                Conquestconfig.getCustomConfig().set("conquest.settings.death-spectator", value);
                Conquestconfig.markConfigDirty();
                Conquestconfig.saveCustomConfig();
                player.sendMessage(ChatColor.GREEN + "Set death-spectator to " + value);
                return true;
            }
            if (arguments[0].equalsIgnoreCase("protection-shrinking-time")) {
                if (arguments.length != 2) {
                    player.sendMessage(ChatColor.RED + "Usage: /conquest protection-shrinking-time <time>");
                    return true;
                }
                try {
                    int time = Integer.parseInt(arguments[1]);
                    if (time < 1 || time > 60) {
                        player.sendMessage(ChatColor.RED + "Protection shrinking time must be between 1 and 60 minutes!");
                        return true;
                    }
                    Conquestconfig.getCustomConfig().set("conquest.settings.protection-shrinking-time", time);
                    Conquestconfig.markConfigDirty();
                    Conquestconfig.saveCustomConfig();
                    player.sendMessage(ChatColor.GREEN + "Set protection shrinking time to " + time + " minutes");
                } catch (NumberFormatException e) {
                    player.sendMessage(ChatColor.RED + "Time must be a number!");
                }
                return true;
            }
            if (arguments[0].equalsIgnoreCase("protection-shrinking-amount")) {
                if (arguments.length != 2) {
                    player.sendMessage(ChatColor.RED + "Usage: /conquest protection-shrinking-amount <amount>");
                    return true;
                }
                try {
                    int amount = Integer.parseInt(arguments[1]);
                    if (amount < 1 || amount > 50) {
                        player.sendMessage(ChatColor.RED + "Protection shrinking amount must be between 1 and 50!");
                        return true;
                    }
                    Conquestconfig.getCustomConfig().set("conquest.settings.protection-shrinking-amount", amount);
                    Conquestconfig.markConfigDirty();
                    Conquestconfig.saveCustomConfig();
                    player.sendMessage(ChatColor.GREEN + "Set protection shrinking amount to " + amount);
                } catch (NumberFormatException e) {
                    player.sendMessage(ChatColor.RED + "Amount must be a number!");
                }
                return true;
            }
            if (arguments[0].equalsIgnoreCase("conquest-radius")) {
                if (arguments.length != 2) {
                    player.sendMessage(ChatColor.RED + "Usage: /conquest conquest-radius <radius>");
                    return true;
                }
                try {
                    int radius = Integer.parseInt(arguments[1]);
                    if (radius < 10 || radius > 100) {
                        player.sendMessage(ChatColor.RED + "Conquest radius must be between 10 and 100!");
                        return true;
                    }
                    Conquestconfig.getCustomConfig().set("conquest.settings.conquest-radius", radius);
                    Conquestconfig.markConfigDirty();
                    Conquestconfig.saveCustomConfig();
                    player.sendMessage(ChatColor.GREEN + "Set conquest radius to " + radius);
                } catch (NumberFormatException e) {
                    player.sendMessage(ChatColor.RED + "Radius must be a number!");
                }
                return true;
            }
            /////
            if (arguments.length == 0) {
                player.sendMessage(ChatColor.LIGHT_PURPLE + "Available commands:");
                player.sendMessage(ChatColor.LIGHT_PURPLE + "/conquest <city> on - Start conquest for a city");
                player.sendMessage(ChatColor.LIGHT_PURPLE + "/conquest <city> off - Stop conquest for a city");
                player.sendMessage(ChatColor.LIGHT_PURPLE + "/conquest protection-shrinking-time <minutes> - Set protection shrinking time");
                player.sendMessage(ChatColor.LIGHT_PURPLE + "/conquest protection-shrinking-amount <blocks> - Set protection shrinking amount");
                player.sendMessage(ChatColor.LIGHT_PURPLE + "/conquest borderprotection-fall <size> - Set border size when protection falls");
                player.sendMessage(ChatColor.LIGHT_PURPLE + "/conquest no-protection - Disable city protection radius");
                return true;
            }
            if (arguments.length >= 2 && arguments[0].equalsIgnoreCase("protection-shrinking-time")) {
                try {
                    int minutes = Integer.parseInt(arguments[1]);
                    if (minutes < 1) {
                        player.sendMessage(ChatColor.RED + "Shrinking time must be at least 1 minute!");
                        return true;
                    }
                    Conquestconfig.getCustomConfig().set("conquest.settings.protection-shrinking-time", minutes);
                    Conquestconfig.markConfigDirty();
                    Conquestconfig.saveCustomConfig();
                    player.sendMessage(ChatColor.GREEN + "Protection shrinking time set to " + minutes + " minutes!");
                    Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "[DEBUG] Protection shrinking time set to " + minutes + " minutes by " + player.getName());
                    return true;
                } catch (NumberFormatException e) {
                    player.sendMessage(ChatColor.RED + "Please enter a valid number of minutes!");
                    return true;
                }
            }
            if (arguments.length >= 2 && arguments[0].equalsIgnoreCase("protection-shrinking-amount")) {
                try {
                    int blocks = Integer.parseInt(arguments[1]);
                    if (blocks < 1) {
                        player.sendMessage(ChatColor.RED + "Shrinking amount must be at least 1 block!");
                        return true;
                    }
                    Conquestconfig.getCustomConfig().set("conquest.settings.protection-shrinking-amount", blocks);
                    Conquestconfig.markConfigDirty();
                    Conquestconfig.saveCustomConfig();
                    player.sendMessage(ChatColor.GREEN + "Protection shrinking amount set to " + blocks + " blocks!");
                    Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "[DEBUG] Protection shrinking amount set to " + blocks + " blocks by " + player.getName());
                    return true;
                } catch (NumberFormatException e) {
                    player.sendMessage(ChatColor.RED + "Please enter a valid number of blocks!");
                    return true;
                }
            }

            if (arguments.length >= 2 && arguments[0].equalsIgnoreCase("borderprotection-fall")) {
                try {
                    int size = Integer.parseInt(arguments[1]);
                    if (size < 1) {
                        player.sendMessage(ChatColor.RED + "Border protection fall size must be at least 1 block!");
                        return true;
                    }
                    Conquestconfig.getCustomConfig().set("conquest.settings.border-protection-fall-size", size);
                    Conquestconfig.markConfigDirty();
                    Conquestconfig.saveCustomConfig();
                    player.sendMessage(ChatColor.GREEN + "Border protection fall size set to " + size + " blocks!");
                    Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "[DEBUG] Border protection fall size set to " + size + " blocks by " + player.getName());
                    return true;
                } catch (NumberFormatException e) {
                    player.sendMessage(ChatColor.RED + "Please enter a valid number of blocks!");
                    return true;
                }
            }
            if (arguments.length == 1 && arguments[0].equalsIgnoreCase("no-protection")) {
                if (!ConquestManager.isConquestActive()) {
                    player.sendMessage(ChatColor.RED + "No active conquest to disable protection for!");
                    return true;
                }
                ConquestManager.disableProtectionRadius();
                player.sendMessage(ChatColor.GREEN + "Protection radius for " + ConquestManager.getActiveCity() + " disabled!");
                Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "[DEBUG] Protection radius disabled by " + player.getName());
                return true;
            }
            if (arguments.length >= 2 && arguments[1].equalsIgnoreCase("on")) {
                String cityName = arguments[0]; // Normalize to lowercase
                String matchedCity = null;
                for (String key : land.cityData.keySet()) {
                    if (key.equalsIgnoreCase(cityName)) {
                        matchedCity = key;
                        break;
                    }
                }
                if (matchedCity == null) {
                    player.sendMessage(ChatColor.RED + "City " + cityName + " does not exist!");
                    Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "[DEBUG] CityData keys: " + land.cityData.keySet().toString());
                    return true;
                }
                ConquestManager.startConquest(matchedCity, player, player.getWorld(), land.cityData.get(cityName).locX, land.cityData.get(cityName).locZ);
                return true;
            }

            if (arguments.length >= 2 && arguments[1].equalsIgnoreCase("off")) {
                String cityName = arguments[0]; // Normalize to lowercase
                String matchedCity = null;
                for (String key : land.cityData.keySet()) {
                    if (key.equalsIgnoreCase(cityName)) {
                        matchedCity = key;
                        break;
                    }
                }
                if (matchedCity == null) {
                    player.sendMessage(ChatColor.RED + "City " + cityName + " does not exist!");
                    Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "[DEBUG] CityData keys: " + land.cityData.keySet().toString());
                    return true;
                }
            /////
            if (arguments[0].equalsIgnoreCase("add-attacker")) {
                if (arguments.length != 2) {
                    player.sendMessage(ChatColor.RED + "Usage: /conquest add-attacker <player>");
                    return true;
                }
                String addPlayer = arguments[1];
                if (Conquestconfig.getCustomConfig().getConfigurationSection("conquest-defender").getKeys(false).contains(addPlayer)) {
                    player.sendMessage(ChatColor.RED + addPlayer + " is already a defender!");
                    return true;
                }
                Conquestconfig.getCustomConfig().set("conquest-attacker." + addPlayer, addPlayer);
                Conquestconfig.markConfigDirty();
                Conquestconfig.saveCustomConfig();
                player.sendMessage(ChatColor.GREEN + "Added " + addPlayer + " to conquest attackers");
                return true;
            }
            if (arguments[0].equalsIgnoreCase("add-defender")) {
                if (arguments.length != 2) {
                    player.sendMessage(ChatColor.RED + "Usage: /conquest add-defender <player>");
                    return true;
                }
                String addPlayer = arguments[1];
                if (Conquestconfig.getCustomConfig().getConfigurationSection("conquest-attacker").getKeys(false).contains(addPlayer)) {
                    player.sendMessage(ChatColor.RED + addPlayer + " is already an attacker!");
                    return true;
                }
                Conquestconfig.getCustomConfig().set("conquest-defender." + addPlayer, addPlayer);
                Conquestconfig.markConfigDirty();
                Conquestconfig.saveCustomConfig();
                player.sendMessage(ChatColor.GREEN + "Added " + addPlayer + " to conquest defenders");
                return true;
            }
            if (arguments[0].equalsIgnoreCase("remove-attacker")) {
                if (arguments.length != 2) {
                    player.sendMessage(ChatColor.RED + "Usage: /conquest remove-attacker <player>");
                    return true;
                }
                String removePlayer = arguments[1];
                if (Conquestconfig.getCustomConfig().get("conquest-attacker." + removePlayer) == null) {
                    player.sendMessage(ChatColor.RED + removePlayer + " is not an attacker!");
                    return true;
                }
                Conquestconfig.getCustomConfig().set("conquest-attacker." + removePlayer, null);
                Conquestconfig.markConfigDirty();
                Conquestconfig.saveCustomConfig();
                player.sendMessage(ChatColor.GREEN + "Removed " + removePlayer + " from conquest attackers");
                return true;
            }
            if (arguments[0].equalsIgnoreCase("remove-defender")) {
                if (arguments.length != 2) {
                    player.sendMessage(ChatColor.RED + "Usage: /conquest remove-defender <player>");
                    return true;
                }
                String removePlayer = arguments[1];
                if (Conquestconfig.getCustomConfig().get("conquest-defender." + removePlayer) == null) {
                    player.sendMessage(ChatColor.RED + removePlayer + " is not a defender!");
                    return true;
                }
                Conquestconfig.getCustomConfig().set("conquest-defender." + removePlayer, null);
                Conquestconfig.markConfigDirty();
                Conquestconfig.saveCustomConfig();
                player.sendMessage(ChatColor.GREEN + "Removed " + removePlayer + " from conquest defenders");
                return true;
            }
            if (arguments[0].equalsIgnoreCase("list-teams")) {
                player.sendMessage(ChatColor.GREEN + "Conquest Teams:");
                player.sendMessage(ChatColor.YELLOW + "Attackers:");
                ConfigurationSection attackers = Conquestconfig.getCustomConfig().getConfigurationSection("conquest-attacker");
                if (attackers != null && !attackers.getKeys(false).isEmpty()) {
                    for (String attacker : attackers.getKeys(false)) {
                        player.sendMessage(ChatColor.YELLOW + "- " + attacker);
                    }
                } else {
                    player.sendMessage(ChatColor.RED + "No attackers!");
                }
                player.sendMessage(ChatColor.YELLOW + "Defenders:");
                ConfigurationSection defenders = Conquestconfig.getCustomConfig().getConfigurationSection("conquest-defender");
                if (defenders != null && !defenders.getKeys(false).isEmpty()) {
                    for (String defender : defenders.getKeys(false)) {
                        player.sendMessage(ChatColor.YELLOW + "- " + defender);
                    }
                } else {
                    player.sendMessage(ChatColor.RED + "No defenders!");
                }
                return true;
            }
            if (arguments.length != 2 || (!arguments[1].equalsIgnoreCase("on") && !arguments[1].equalsIgnoreCase("off"))) {
                player.sendMessage(ChatColor.RED + "Usage: /conquest <city> <on|off>");
                return true;
            }
         
            boolean enable = arguments[1].equalsIgnoreCase("on");
            boolean found = false;
            for (int i = 1; i <= 10; i++) {
                String cityKey = "City" + i;
                ConfigurationSection city = Cityconfig.getCustomConfig().getConfigurationSection(cityKey);
                if (city != null && city.getString("Stadtname").equalsIgnoreCase(cityName)) {
                    int centerX = city.getInt("LocX");
                    int centerZ = city.getInt("LocZ");
                    int radius = city.getInt("radius", 50);
                    if (enable) {
                        ConquestManager.startConquest(cityName, player, player.getWorld(), centerX, centerZ);
                        Conquestconfig.getCustomConfig().set("conquest.settings.conquest-set", "on");
                        Conquestconfig.getCustomConfig().set("conquest.settings.conquest-radius", radius);
                        Conquestconfig.getCustomConfig().set("conquest.settings.active-city", cityName);
                        Conquestconfig.getCustomConfig().set("conquest.settings.active-cities.city1.name", cityName);
                        Conquestconfig.getCustomConfig().set("conquest.settings.active-cities.city1.centerX", centerX);
                        Conquestconfig.getCustomConfig().set("conquest.settings.active-cities.city1.centerZ", centerZ);
                        Conquestconfig.getCustomConfig().set("conquest.settings.active-cities.city1.world", player.getWorld().getName());
                    } else {
                        ConquestManager.stopConquest(cityKey, cityName, player, player.getWorld());
                    }
                    Conquestconfig.markConfigDirty();
                    Conquestconfig.saveCustomConfig();
                    found = true;
                    break;
                }
            }
            if (!found) {
                player.sendMessage(ChatColor.RED + "City " + cityName + " not found!");
            }
            return true;
        }

        return false;
    }
		return false;
}
    }*/