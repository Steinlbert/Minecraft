package me.steinlbert.events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;
import me.steinlbert.main;
import me.steinlbert.configmanager.Cityconfig;
import me.steinlbert.configmanager.Conquestconfig;
import org.bukkit.WorldBorder;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.GameMode;
import org.bukkit.Server;
import org.bukkit.ServerTickManager;
import org.bukkit.scoreboard.*;


import java.util.HashMap;
import java.util.Set;

public class ConquestManager implements Listener {
    private static final main plugin = main.getPlugin(main.class);
    private static boolean conquestInProgress = false;
    private static String activeCity;
    private static int currentBorderSize;
    private static int originalRadius;
    private static int currentProtectionRadius;
    private static final HashMap<String, Boolean> deadPlayers = new HashMap<>();
    private static BukkitRunnable borderShrinkTask;
    private static BukkitRunnable protectionShrinkTask;
    private static BukkitRunnable scoreboardUpdateTask;
    private static Scoreboard scoreboard;
    private static Objective objective;
    private static long lastBorderShrinkTick;
    private static long lastProtectionShrinkTick;

    public ConquestManager() {
        checkConquestStatus();
    }

    private void checkConquestStatus() {
        if (Conquestconfig.getCustomConfig().getString("conquest.settings.conquest-set", "off").equalsIgnoreCase("on")) {
            conquestInProgress = true;
            activeCity = Conquestconfig.getCustomConfig().getString("conquest.settings.active-city");
            currentBorderSize = Conquestconfig.getCustomConfig().getInt("conquest.settings.boarder-size", 400);
            currentProtectionRadius = Conquestconfig.getCustomConfig().getInt("conquest.settings.conquest-radius", 50);
            originalRadius = Conquestconfig.getCustomConfig().getInt("conquest.settings.original-conquest-radius", currentProtectionRadius);
            ConfigurationSection deadPlayersSection = Conquestconfig.getCustomConfig().getConfigurationSection("conquest.settings.dead-players");
            if (deadPlayersSection != null) {
                for (String player : deadPlayersSection.getKeys(false)) {
                    deadPlayers.put(player, true);
                }
            }
            Conquestconfig.getCustomConfig().set("conquest.settings.conquest-in-progress", true);
            Conquestconfig.markConfigDirty();
            Conquestconfig.saveCustomConfig();
            Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "[Conquest] Loaded conquest: " + activeCity + ", radius: " + currentProtectionRadius);
            Bukkit.broadcastMessage(ChatColor.RED + "[Conquest] Conquest started!");
            Bukkit.broadcastMessage(ChatColor.YELLOW + "[Conquest] Protection radius for " + activeCity + ": " + currentProtectionRadius + " blocks");
            land.loadCityData();
            chests.setPrivateChestsDisabled(true);
            startBorderShrink();
            startProtectionShrink();
            startScoreboard();
        }
    }

    public static void startConquest(String cityName, Player player, World world, int centerX, int centerZ) {
        if (conquestInProgress) {
            player.sendMessage(ChatColor.RED + "[Conquest] A conquest is already active in " + activeCity + "!");
            return;
        }
        conquestInProgress = true;
        activeCity = cityName;
        currentBorderSize = Conquestconfig.getCustomConfig().getInt("conquest.settings.boarder-size", 400);
        ConfigurationSection city = Cityconfig.getCustomConfig().getConfigurationSection(cityName);
        currentProtectionRadius = city != null ? city.getInt("radius", 50) : 50;
        originalRadius = currentProtectionRadius;
        Conquestconfig.getCustomConfig().set("conquest.settings.conquest-in-progress", true);
        Conquestconfig.getCustomConfig().set("conquest.settings.original-conquest-radius", originalRadius);
        Conquestconfig.getCustomConfig().set("conquest.settings.active-city", cityName);
        Conquestconfig.getCustomConfig().set("conquest.settings.conquest-radius", currentProtectionRadius);
        Conquestconfig.markConfigDirty();
        Conquestconfig.saveCustomConfig();
        deadPlayers.clear();
        
        WorldBorder border = world.getWorldBorder();
        border.setCenter(centerX, centerZ);
        border.setSize(currentBorderSize);
        border.setDamageAmount(1.0);
        border.setDamageBuffer(0);
        
        Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "[Conquest] Conquest started: " + cityName + ", radius: " + currentProtectionRadius);
        Bukkit.broadcastMessage(ChatColor.RED + "[Conquest] Conquest started!");
        Bukkit.broadcastMessage(ChatColor.YELLOW + "[Conquest] Protection radius for " + cityName + ": " + currentProtectionRadius + " blocks");
        land.loadCityData();
        chests.setPrivateChestsDisabled(true);
        startBorderShrink();
        startProtectionShrink();
        startScoreboard();
    }

    public static void stopConquest(String cityKey, String cityName, Player player, World world) {
        if (!conquestInProgress || !cityName.equalsIgnoreCase(activeCity)) {
            player.sendMessage(ChatColor.RED + "[Conquest] No active conquest for " + cityName + "!");
            return;
        }
        conquestInProgress = false;
        activeCity = null;
        currentProtectionRadius = 0;
        Conquestconfig.getCustomConfig().set("conquest.settings.conquest-in-progress", false);
        Conquestconfig.getCustomConfig().set("conquest.settings.conquest-set", "off");
        Conquestconfig.getCustomConfig().set("conquest.settings.active-city", null);
        Conquestconfig.getCustomConfig().set("conquest.settings.original-conquest-radius", null);
        Conquestconfig.getCustomConfig().set("conquest.settings.dead-players", null);
        Conquestconfig.getCustomConfig().set("conquest.settings.conquest-radius", 0);
        Conquestconfig.markConfigDirty();
        Conquestconfig.saveCustomConfig();
        deadPlayers.clear();
        if (borderShrinkTask != null) {
            borderShrinkTask.cancel();
        }
        if (protectionShrinkTask != null) {
            protectionShrinkTask.cancel();
        }
        if (scoreboardUpdateTask != null) {
            scoreboardUpdateTask.cancel();
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
            }
            scoreboard = null;
            objective = null;
        }
        world.getWorldBorder().reset();
        Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "[Conquest] Conquest stopped: " + cityName);
        Bukkit.broadcastMessage(ChatColor.GREEN + "[Conquest] Conquest for " + cityName + " stopped!");
        land.loadCityData();
        chests.setPrivateChestsDisabled(false);
    }

    public static void disableProtectionRadius() {
        if (!conquestInProgress) return;
        currentProtectionRadius = 0;
        Conquestconfig.getCustomConfig().set("conquest.settings.conquest-radius", 0);
        Conquestconfig.markConfigDirty();
        Conquestconfig.saveCustomConfig();
        if (protectionShrinkTask != null) {
            protectionShrinkTask.cancel();
        }
        Bukkit.broadcastMessage(ChatColor.RED + "[Conquest] Protection radius for " + activeCity + " disabled!");
        Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "[Conquest] Protection radius set to 0");
        land.loadCityData();
    }

    public static void updateSettings() {
        if (!conquestInProgress) return;
        int newShrinkRate = Conquestconfig.getCustomConfig().getInt("conquest.settings.boarder-shrinking-rate", 10);
        int newBorderShrinkTime = Conquestconfig.getCustomConfig().getInt("conquest.settings.boarder-shrinking-time", 5);
        int newProtectionShrinkTime = Conquestconfig.getCustomConfig().getInt("conquest.settings.protection-shrinking-time", 1);
        int newProtectionShrinkAmount = Conquestconfig.getCustomConfig().getInt("conquest.settings.protection-shrinking-amount", 5);
        String deathSpectator = Conquestconfig.getCustomConfig().getString("conquest.settings.death-spectator", "off");

        if (borderShrinkTask != null) {
            borderShrinkTask.cancel();
            startBorderShrink();
        }
        if (protectionShrinkTask != null) {
            protectionShrinkTask.cancel();
            startProtectionShrink();
        }
        Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "[Conquest] Updated settings: boarder-shrinking-rate=" + newShrinkRate + ", boarder-shrinking-time=" + newBorderShrinkTime + ", protection-shrinking-time=" + newProtectionShrinkTime + ", protection-shrinking-amount=" + newProtectionShrinkAmount + ", death-spectator=" + deathSpectator);
    }

    private static void startBorderShrink() {
        lastBorderShrinkTick = Bukkit.getServer().getWorlds().getFirst().getTime();
        borderShrinkTask = new BukkitRunnable() {
            @Override
            public void run() {
                if (!conquestInProgress) {
                    cancel();
                    return;
                }
                int shrinkRate = Conquestconfig.getCustomConfig().getInt("conquest.settings.boarder-shrinking-rate", 10);
                currentBorderSize -= shrinkRate;
                Conquestconfig.getCustomConfig().set("conquest.settings.boarder-size", currentBorderSize);
                Conquestconfig.markConfigDirty();
                Conquestconfig.saveCustomConfig();
                
                World world = Bukkit.getWorld(Conquestconfig.getCustomConfig().getString("conquest.settings.active-cities.city1.world", "world"));
                if (world != null) {
                    WorldBorder border = world.getWorldBorder();
                    border.setSize(currentBorderSize, 60);
                    Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "[Conquest] Border shrunk to: " + currentBorderSize);
                }

                int protectionFallSize = Conquestconfig.getCustomConfig().getInt("conquest.settings.border-protection-fall-size", 200);
                if (currentBorderSize <= protectionFallSize) {
                    Bukkit.broadcastMessage(ChatColor.RED + "[Conquest] All protection is now disabled, the fight will end soon!");
                    currentProtectionRadius = 0;
                    Conquestconfig.getCustomConfig().set("conquest.settings.conquest-radius", 0);
                    Conquestconfig.markConfigDirty();
                    Conquestconfig.saveCustomConfig();
                    land.loadCityData();
                    cancel();
                }
                lastBorderShrinkTick = Bukkit.getServerTickManager().hashCode();
            }
        };
        int shrinkTimeMinutes = Conquestconfig.getCustomConfig().getInt("conquest.settings.boarder-shrinking-time", 5);
        if (shrinkTimeMinutes < 1) {
            shrinkTimeMinutes = 5;
            Conquestconfig.getCustomConfig().set("conquest.settings.boarder-shrinking-time", 5);
            Conquestconfig.markConfigDirty();
            Conquestconfig.saveCustomConfig();
            Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "[Conquest] Invalid border-shrinking-time, set to 5 minutes");
        }
        long shrinkTimeTicks = shrinkTimeMinutes * 60 * 20L;
        borderShrinkTask.runTaskTimer(plugin, shrinkTimeTicks, shrinkTimeTicks);
    }

    private static void startProtectionShrink() {
        lastProtectionShrinkTick = Bukkit.getServerTickManager().hashCode();
        protectionShrinkTask = new BukkitRunnable() {
            @Override
            public void run() {
                if (!conquestInProgress || currentProtectionRadius <= 0) {
                    cancel();
                    return;
                }
                int protectionFallSize = Conquestconfig.getCustomConfig().getInt("conquest.settings.border-protection-fall-size", 200);
                if (currentBorderSize <= protectionFallSize) {
                    cancel();
                    return;
                }
                int shrinkAmount = Conquestconfig.getCustomConfig().getInt("conquest.settings.protection-shrinking-amount", 5);
                currentProtectionRadius = Math.max(0, currentProtectionRadius - shrinkAmount);
                Conquestconfig.getCustomConfig().set("conquest.settings.conquest-radius", currentProtectionRadius);
                Conquestconfig.markConfigDirty();
                Conquestconfig.saveCustomConfig();
                Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "[Conquest] Protection radius shrunk to: " + currentProtectionRadius);
                Bukkit.broadcastMessage(ChatColor.YELLOW + "[Conquest] Protection radius for " + activeCity + " reduced to: " + currentProtectionRadius + " blocks due to periodic shrinking");
                land.loadCityData();
                if (currentProtectionRadius == 0) {
                    cancel();
                }
                lastProtectionShrinkTick = Bukkit.getServerTickManager().hashCode();
            }
        };
        int shrinkTimeMinutes = Conquestconfig.getCustomConfig().getInt("conquest.settings.protection-shrinking-time", 1);
        if (shrinkTimeMinutes < 1) {
            shrinkTimeMinutes = 1;
            Conquestconfig.getCustomConfig().set("conquest.settings.protection-shrinking-time", 1);
            Conquestconfig.markConfigDirty();
            Conquestconfig.saveCustomConfig();
            Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "[Conquest] Invalid protection-shrinking-time, set to 1 minute");
        }
        long shrinkTimeTicks = shrinkTimeMinutes * 60 * 20L;
        protectionShrinkTask.runTaskTimer(plugin, shrinkTimeTicks, shrinkTimeTicks);
    }

    private static void startScoreboard() {
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        if (manager == null) return;
        scoreboard = manager.getNewScoreboard();
        objective = scoreboard.registerNewObjective("conquest", "dummy", ChatColor.RED + "Conquest: " + activeCity);
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        updateScoreboard();
        for (Player p : Bukkit.getOnlinePlayers()) {
            String worldName = Conquestconfig.getCustomConfig().getString("conquest.settings.active-cities.city1.world", "world");
            if (p.getWorld().getName().equals(worldName)) {
                p.setScoreboard(scoreboard);
            }
        }

        scoreboardUpdateTask = new BukkitRunnable() {
            @Override
            public void run() {
                if (!conquestInProgress) {
                    cancel();
                    return;
                }
                updateScoreboard();
            }
        };
        scoreboardUpdateTask.runTaskTimer(plugin, 0L, 20L);
    }

    private static void updateScoreboard() {
        if (scoreboard == null || objective == null) return;

        for (String entry : scoreboard.getEntries()) {
            scoreboard.resetScores(entry);
        }

        ConfigurationSection attackers = Conquestconfig.getCustomConfig().getConfigurationSection("conquest-attacker");
        ConfigurationSection defenders = Conquestconfig.getCustomConfig().getConfigurationSection("conquest-defender");
        int attackersAlive = 0, attackersDead = 0;
        int defendersAlive = 0, defendersDead = 0;

        if (attackers != null) {
            for (String attacker : attackers.getKeys(false)) {
                if (deadPlayers.getOrDefault(attacker, false)) {
                    attackersDead++;
                } else {
                    attackersAlive++;
                }
            }
        }
        if (defenders != null) {
            for (String defender : defenders.getKeys(false)) {
                if (deadPlayers.getOrDefault(defender, false)) {
                    defendersDead++;
                } else {
                    defendersAlive++;
                }
            }
        }

        int borderShrinkMinutes = Conquestconfig.getCustomConfig().getInt("conquest.settings.boarder-shrinking-time", 5);
        int protectionShrinkMinutes = Conquestconfig.getCustomConfig().getInt("conquest.settings.protection-shrinking-time", 1);
        long ticksSinceBorderShrink = Bukkit.getServerTickManager().hashCode() - lastBorderShrinkTick;
        long ticksSinceProtectionShrink = Bukkit.getServerTickManager().hashCode() - lastProtectionShrinkTick;
        long borderTimeLeft = Math.max(0, (borderShrinkMinutes * 60 * 20L - ticksSinceBorderShrink) / 1200);
        long protectionTimeLeft = Math.max(0, (protectionShrinkMinutes * 60 * 20L - ticksSinceProtectionShrink) / 1200);

        String[] lines = new String[] {
            ChatColor.YELLOW + "City: " + activeCity,
            ChatColor.YELLOW + "Protection Radius: " + currentProtectionRadius,
            ChatColor.YELLOW + "Border Size: " + currentBorderSize,
            ChatColor.RED + "Attackers: " + attackersAlive + "/" + (attackersAlive + attackersDead),
            ChatColor.GREEN + "Defenders: " + defendersAlive + "/" + (defendersAlive + defendersDead),
            ChatColor.YELLOW + "Next Border Shrink: " + borderTimeLeft + " min",
            ChatColor.YELLOW + "Next Prot. Shrink: " + protectionTimeLeft + " min"
        };

        for (int i = 0; i < lines.length; i++) {
            Score score = objective.getScore(lines[i]);
            score.setScore(lines.length - i);
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        if (!conquestInProgress) return;
        Player player = event.getEntity();
        String playerName = player.getName();
        
        if (!isConquestParticipant(playerName)) return;

        deadPlayers.put(playerName, true);
        Conquestconfig.getCustomConfig().set("conquest.settings.dead-players." + playerName, true);
        Conquestconfig.markConfigDirty();
        Conquestconfig.saveCustomConfig();

        int shrinkAmount = Conquestconfig.getCustomConfig().getInt("conquest.settings.protection-shrinking-amount", 5);
        ConfigurationSection city = Cityconfig.getCustomConfig().getConfigurationSection(activeCity);
        int maxRadius = city != null ? city.getInt("radius", 50) : 50;
        if (Conquestconfig.getCustomConfig().getConfigurationSection("conquest-defender").getKeys(false).contains(playerName)) {
            currentProtectionRadius = Math.max(0, currentProtectionRadius - shrinkAmount);
            Conquestconfig.getCustomConfig().set("conquest.settings.conquest-radius", currentProtectionRadius);
            Conquestconfig.markConfigDirty();
            Conquestconfig.saveCustomConfig();
            Bukkit.broadcastMessage(ChatColor.YELLOW + "[Conquest] The attacking team is making progress!");
            Bukkit.broadcastMessage(ChatColor.YELLOW + "[Conquest] Protection radius for " + activeCity + " reduced to: " + currentProtectionRadius + " blocks");
        } else if (Conquestconfig.getCustomConfig().getConfigurationSection("conquest-attacker").getKeys(false).contains(playerName)) {
            currentProtectionRadius = Math.min(maxRadius, currentProtectionRadius + shrinkAmount);
            Conquestconfig.getCustomConfig().set("conquest.settings.conquest-radius", currentProtectionRadius);
            Conquestconfig.markConfigDirty();
            Conquestconfig.saveCustomConfig();
            Bukkit.broadcastMessage(ChatColor.YELLOW + "[Conquest] Defending team is making progress!");
            Bukkit.broadcastMessage(ChatColor.YELLOW + "[Conquest] Protection radius for " + activeCity + " increased to: " + currentProtectionRadius + " blocks");
        }

        String deathSpectator = Conquestconfig.getCustomConfig().getString("conquest.settings.death-spectator", "off");
        if (deathSpectator.equalsIgnoreCase("on")) {
            player.setGameMode(GameMode.SPECTATOR);
            player.sendMessage(ChatColor.YELLOW + "[Conquest] You died in the conquest and are now in spectator mode!");
        } else {
            player.kickPlayer(ChatColor.RED + "[Conquest] You died in the conquest and cannot rejoin until it ends!");
        }
        checkConquestEnd();
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (!conquestInProgress) return;
        Player player = event.getPlayer();
        String playerName = player.getName();
        String worldName = Conquestconfig.getCustomConfig().getString("conquest.settings.active-cities.city1.world", "world");
        if (player.getWorld().getName().equals(worldName)) {
            player.setScoreboard(scoreboard);
        }
        if (deadPlayers.getOrDefault(playerName, false)) {
            if (player.isOp()) {
                player.setGameMode(GameMode.SPECTATOR);
                player.sendMessage(ChatColor.YELLOW + "[Conquest] You are an admin and have joined in spectator mode due to being marked as dead.");
            } else {
                String deathSpectator = Conquestconfig.getCustomConfig().getString("conquest.settings.death-spectator", "off");
                if (deathSpectator.equalsIgnoreCase("on")) {
                    player.setGameMode(GameMode.SPECTATOR);
                    player.sendMessage(ChatColor.YELLOW + "[Conquest] You are in spectator mode due to being marked as dead.");
                } else {
                    player.kickPlayer(ChatColor.RED + "[Conquest] You are dead and cannot rejoin the conquest until it ends!");
                }
            }
        }
    }

    @EventHandler
    public void onPlayerChangeWorld(PlayerChangedWorldEvent event) {
        if (!conquestInProgress) return;
        Player player = event.getPlayer();
        String playerName = player.getName();
        if (isConquestParticipant(playerName)) {
            player.setHealth(0);
            deadPlayers.put(playerName, true);
            Conquestconfig.getCustomConfig().set("conquest.settings.dead-players." + playerName, true);
            Conquestconfig.markConfigDirty();
            Conquestconfig.saveCustomConfig();
            Bukkit.broadcastMessage(ChatColor.RED + "[Conquest] " + playerName + " tried to escape the conquest and suffocated between two worlds!");
            String deathSpectator = Conquestconfig.getCustomConfig().getString("conquest.settings.death-spectator", "off");
            if (deathSpectator.equalsIgnoreCase("on")) {
                player.setGameMode(GameMode.SPECTATOR);
                player.sendMessage(ChatColor.YELLOW + "[Conquest] You are now in spectator mode!");
            } else {
                player.kickPlayer(ChatColor.RED + "[Conquest] You tried to escape the conquest and died!");
            }
            checkConquestEnd();
        }
        String worldName = Conquestconfig.getCustomConfig().getString("conquest.settings.active-cities.city1.world", "world");
        if (player.getWorld().getName().equals(worldName)) {
            player.setScoreboard(scoreboard);
        } else {
            player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
        }
    }

    private boolean isConquestParticipant(String playerName) {
        Set<String> attackers = Conquestconfig.getCustomConfig().getConfigurationSection("conquest-attacker").getKeys(false);
        Set<String> defenders = Conquestconfig.getCustomConfig().getConfigurationSection("conquest-defender").getKeys(false);
        return attackers.contains(playerName) || defenders.contains(playerName);
    }

    private void checkConquestEnd() {
        Set<String> attackers = Conquestconfig.getCustomConfig().getConfigurationSection("conquest-attacker").getKeys(false);
        Set<String> defenders = Conquestconfig.getCustomConfig().getConfigurationSection("conquest-defender").getKeys(false);
        
        boolean allAttackersDead = true;
        for (String attacker : attackers) {
            if (!deadPlayers.getOrDefault(attacker, false)) {
                allAttackersDead = false;
                break;
            }
        }
        
        boolean allDefendersDead = true;
        for (String defender : defenders) {
            if (!deadPlayers.getOrDefault(defender, false)) {
                allDefendersDead = false;
                break;
            }
        }

        if (allAttackersDead) {
            Bukkit.broadcastMessage(ChatColor.GREEN + "[Conquest] Conquest ended! Defenders of " + activeCity + " win!");
            endConquest();
        } else if (allDefendersDead) {
            ConfigurationSection city = Cityconfig.getCustomConfig().getConfigurationSection(activeCity);
            if (city != null) {
                city.set("Spieler", null); // Remove all existing players
                for (String attacker : attackers) {
                    city.set("Spieler." + attacker, attacker); // Add attackers as new citizens
                }
                Cityconfig.markConfigDirty();
                Cityconfig.saveCustomConfig();
                land.loadCityData();
                Bukkit.broadcastMessage(ChatColor.GREEN + "[Conquest] " + activeCity + " conquered! New citizens: " + String.join(", ", attackers));
                Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "[Conquest] Updated " + activeCity + " citizens: " + String.join(", ", attackers));
            }
            endConquest();
        }
    }

    private void endConquest() {
        conquestInProgress = false;
        activeCity = null;
        currentProtectionRadius = 0;
        Conquestconfig.getCustomConfig().set("conquest.settings.conquest-in-progress", false);
        Conquestconfig.getCustomConfig().set("conquest.settings.conquest-set", "off");
        Conquestconfig.getCustomConfig().set("conquest.settings.active-city", null);
        Conquestconfig.getCustomConfig().set("conquest.settings.original-conquest-radius", null);
        Conquestconfig.getCustomConfig().set("conquest.settings.dead-players", null);
        Conquestconfig.getCustomConfig().set("conquest.settings.conquest-radius", 0);
        Conquestconfig.markConfigDirty();
        Conquestconfig.saveCustomConfig();
        deadPlayers.clear();
        if (borderShrinkTask != null) {
            borderShrinkTask.cancel();
        }
        if (protectionShrinkTask != null) {
            protectionShrinkTask.cancel();
        }
        if (scoreboardUpdateTask != null) {
            scoreboardUpdateTask.cancel();
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
            }
            scoreboard = null;
            objective = null;
        }
        World world = Bukkit.getWorld(Conquestconfig.getCustomConfig().getString("conquest.settings.active-cities.city1.world", "world"));
        if (world != null) {
            world.getWorldBorder().reset();
        }
        Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "[Conquest] Conquest ended, protection disabled");
        land.loadCityData();
        chests.setPrivateChestsDisabled(false);
    }

    public static boolean isConquestActive() {
        return conquestInProgress;
    }

    public static String getActiveCity() {
        return activeCity;
    }

    public static int getCurrentProtectionRadius() {
        return currentProtectionRadius;
    }

    public static int getCurrentBorderSize() {
        return currentBorderSize;
    }

    public static int getCenterX() {
        return Conquestconfig.getCustomConfig().getInt("conquest.settings.active-cities.city1.centerX", 0);
    }

    public static int getCenterZ() {
        return Conquestconfig.getCustomConfig().getInt("conquest.settings.active-cities.city1.centerZ", 0);
    }
}