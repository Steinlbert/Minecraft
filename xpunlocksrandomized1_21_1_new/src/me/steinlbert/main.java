package me.steinlbert;

import java.io.File;

import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;

import me.steinlbert.commands.cmd;
import me.steinlbert.config.JoinListener;
import me.steinlbert.events.achivements;
import me.steinlbert.events.brewer;
import me.steinlbert.events.crafter;
import me.steinlbert.events.entity;
import me.steinlbert.events.farmer;
import me.steinlbert.events.mining;
import me.steinlbert.events.netheritmining;
import me.steinlbert.events.shulker;
import me.steinlbert.events.smithing;
import me.steinlbert.events.star;
import me.steinlbert.events.wizzard;
import me.steinlbert.worldspawn.craftboxspawn;
import net.md_5.bungee.api.ChatColor;

public final class main extends JavaPlugin {
	
	public void onEnable() {
	    starten();
	 	}

	   File file = new File(getDataFolder() + File.separator + "config.yml");
	   
		private void starten() {
			String nameofplugin = getDescription().getName();
			
		
			
			getServer().getConsoleSender().sendMessage(ChatColor.GOLD + nameofplugin + " activate!");
			//Events
			getServer().getPluginManager().registerEvents(new smithing(), this);  									//fix for 1.19.3 abgeändert schmieden Erlaubt nur netherit schmieden...
			getServer().getPluginManager().registerEvents(new entity(), this);										//fix for 1.19.3 	
			getServer().getPluginManager().registerEvents(new mining(), this); 										//fix for 1.19.3	//+Steine abbauen <- randomized true or false??? what´s the better option.....
			getServer().getPluginManager().registerEvents(new wizzard(), this);										//fix for 1.19.3
			getServer().getPluginManager().registerEvents(new brewer(this), this);									//fix for 1.19.3
			getServer().getPluginManager().registerEvents(new crafter(), this);										//fix for 1.19.3
			getServer().getPluginManager().registerEvents(new farmer(), this);										//fix for 1.19.3
			getServer().getPluginManager().registerEvents(new achivements(), this);									//fix for 1.19.3
			getServer().getPluginManager().registerEvents(new shulker(), this);										//fix for 1.19.3
			getServer().getPluginManager().registerEvents(new star(), this);										//fix for 1.19.3
			getServer().getPluginManager().registerEvents(new JoinListener(this), this);							//fix for 1.19.3
			getServer().getPluginManager().registerEvents(new netheritmining(), this);								//fix for 1.19.3
			getServer().getPluginManager().registerEvents(new craftboxspawn(), this);								//Befüllt kisten mit Craftboxen
			
			getConfig().createSection("*******************************xpunlocks*******************************");
			saveConfig();; //saves the default config.yml
			
			//commands
			getCommand("xpunlocks").setExecutor((CommandExecutor)new cmd());
			getCommand("xpunlocks-help").setExecutor((CommandExecutor)new cmd());	
			getCommand("xpunlocks-help-en").setExecutor((CommandExecutor)new cmd());
			getCommand("xpunlocks-config").setExecutor((CommandExecutor)new cmd());
			getCommand("xpunlocks-resetxp").setExecutor((CommandExecutor)new cmd());
			getCommand("xpunlocks-rewards").setExecutor((CommandExecutor)new cmd());
			getCommand("xpunlocks-kit").setExecutor((CommandExecutor)new cmd());

		}
}