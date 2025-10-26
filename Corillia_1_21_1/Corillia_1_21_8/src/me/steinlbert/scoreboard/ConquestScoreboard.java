package me.steinlbert.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import me.steinlbert.main;
import me.steinlbert.configmanager.Conquestconfig;
import me.steinlbert.events.land;

import java.util.Set;

public class ConquestScoreboard implements Listener{
    private static ScoreboardManager manager = Bukkit.getScoreboardManager();

    public static void showScoreboard(String cityKey, String cityName) {
        Scoreboard scoreboard = manager.getNewScoreboard();
        Objective objective = scoreboard.registerNewObjective("conquest", "dummy", ChatColor.RED + "Conquest " + cityName);
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        // Timer (1 Stunde = 3600 Sekunden)
        Score timer = objective.getScore("Timer:");
        timer.setScore(3600);

        // Radius
        Score radius = objective.getScore("Radius:");
        radius.setScore(land.cityData.get(cityKey).currentRadius);

        // Teams
        Set<String> attackers = Conquestconfig.getCustomConfig().getConfigurationSection("conquest-attacker").getKeys(false);
        Set<String> defenders = Conquestconfig.getCustomConfig().getConfigurationSection("conquest-defender").getKeys(false);
        Score attackersScore = objective.getScore("Attackers:");
        attackersScore.setScore(attackers.size());
        Score defendersScore = objective.getScore("Defenders:");
        defendersScore.setScore(defenders.size());

        // Anzeige für alle Spieler
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.setScoreboard(scoreboard);
        }
    }

    public static void updateScoreboard(String cityKey, String cityName, int timerSeconds, int currentRadius, int attackersCount, int defendersCount) {
        Scoreboard scoreboard = manager.getMainScoreboard();
        Objective objective = scoreboard.getObjective("conquest");
        if (objective != null) {
            Score timer = objective.getScore("Timer:");
            timer.setScore(timerSeconds);

            Score radius = objective.getScore("Radius:");
            radius.setScore(currentRadius);

            Score attackersScore = objective.getScore("Attackers:");
            attackersScore.setScore(attackersCount);

            Score defendersScore = objective.getScore("Defenders:");
            defendersScore.setScore(defendersCount);
        }
    }

    public static void hideScoreboard() {
        Scoreboard scoreboard = manager.getMainScoreboard();
        Objective objective = scoreboard.getObjective("conquest");
        if (objective != null) {
            objective.unregister();
        }
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.setScoreboard(manager.getNewScoreboard());
        }
    }
}