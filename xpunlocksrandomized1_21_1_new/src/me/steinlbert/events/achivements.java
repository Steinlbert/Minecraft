package me.steinlbert.events;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerExpChangeEvent;

import me.steinlbert.main;

public class achivements implements Listener {
	main plugin = main.getPlugin(main.class);
	
	//hasmap der werte für jeden Player setzt das wäre eigentlich der plan...
	public HashMap<Player, Integer> Haschmap = new HashMap<>();
	int ev1 = 0;
	int ev2 = 0;
	int ev3 = 0;
	int ev4 = 0;
	int ev5 = 0;
	int ev6 = 0;
	int ev7 = 0;
	int ev8 = 0;
	

	
/*	int ev2 = 0;
	int ev3 = 0;
	int ev4 = 0;
	int ev5 = 0;
	int ev6 = 0;
	int ev7 = 0;
	int ev8 = 0;
	*/

	@EventHandler
		public void achiv (PlayerExpChangeEvent e) {
		Player p = e.getPlayer().getPlayer();
		
		//p.sendMessage("Double "+Haschmap.getOrDefault(p, ev1).doubleValue());
		
		//holt randomized lvl werte aus Config
		int A1 = plugin.getConfig().getInt(p.getDisplayName()+".A");
	    int A2 = plugin.getConfig().getInt(p.getDisplayName()+".B");
	    int A3 = plugin.getConfig().getInt(p.getDisplayName()+".C");
	    int A4 = plugin.getConfig().getInt(p.getDisplayName()+".D");
	    int A5 = plugin.getConfig().getInt(p.getDisplayName()+".E");
	    int A6 = plugin.getConfig().getInt(p.getDisplayName()+".F");
	    int A7 = plugin.getConfig().getInt(p.getDisplayName()+".G");
	    int A8 = plugin.getConfig().getInt(p.getDisplayName()+".H");
	    int A9 = plugin.getConfig().getInt(p.getDisplayName()+".I");

	    //wen spieler stirbt int auf 0 zurücksetzen
	    if(p.getLevel() == 0) {
	    		ev1 = 0;
	    		ev2 = 0;
	    		ev3 = 0;
	    		ev4 = 0;
	    		ev5 = 0;
	    		ev6 = 0;
	    		ev7 = 0;
	    		ev8 = 0;
	    		plugin.getConfig().set(p.getDisplayName()+".I", 1);
	    		plugin.saveConfig();
	    		p.sendMessage("Welcome Passanger! Xpunlocks current Lvl 0 starting from Zero Again! have fun and pls don´t taking live to serious seriously you won´t make it out alive.. (randomized version)");
	    }
	    

	    if((p.getLevel() == A1)||(p.getLevel() == A2)||(p.getLevel() == A3)||(p.getLevel() == A4)||(p.getLevel() == A5)||(p.getLevel() == A6)||(p.getLevel() == A7)||(p.getLevel() == A8)) {
	    	
	    }else {
	    	plugin.getConfig().set(p.getDisplayName()+".I", 1);
	    	plugin.saveConfig();
	    	
	    	
	    }
	    
		if(p.getLevel() == A1) {	
			if((A9 < 2)) {			
				p.sendMessage(ChatColor.GOLD + "congrats you obtained new skills" + ChatColor.YELLOW + " Hunter and Stone-miner");
				plugin.getConfig().set(p.getDisplayName()+".I", 2);
				//p.sendMessage(""+plugin.getConfig().getInt(p.getDisplayName()+".I"));
				plugin.saveConfig();
				}		
		}

		
		if(p.getLevel() == A1) {	
				if((Haschmap.getOrDefault(p, plugin.getConfig().getInt(p.getDisplayName()+".I")) < 3)) {
					p.sendMessage(ChatColor.GOLD + "congrats you obtained new skills" + ChatColor.YELLOW + "Crafter,");
					plugin.getConfig().set(p.getDisplayName()+".I", 3);
					//p.sendMessage(""+plugin.getConfig().getInt(p.getDisplayName()+".I"));					
					plugin.saveConfig();
					}		
			}
		
		
		if(p.getLevel() == A2) {	
			if((Haschmap.getOrDefault(p, plugin.getConfig().getInt(p.getDisplayName()+".I")) < 3)) {
				p.sendMessage(ChatColor.GOLD + "congrats you obtained a new skill" + ChatColor.YELLOW +" Farmer");
				plugin.getConfig().set(p.getDisplayName()+".I", 3);
				plugin.saveConfig();
				}		
		}
		
	
		if(p.getLevel() == A3) {	
				if((Haschmap.getOrDefault(p, plugin.getConfig().getInt(p.getDisplayName()+".I")) < 3)) {
					p.sendMessage(ChatColor.GOLD + "congrats you obtained new skills" + ChatColor.YELLOW + " mining and smithing");
					plugin.getConfig().set(p.getDisplayName()+".I", 3);
					plugin.saveConfig();
					}		
			}
		
		if(p.getLevel() == A4) {	
				if((Haschmap.getOrDefault(p, plugin.getConfig().getInt(p.getDisplayName()+".I")) < 3)) {
					p.sendMessage(ChatColor.GOLD + "congrats you obtained a new skill" + ChatColor.YELLOW + " Brewing");
					plugin.getConfig().set(p.getDisplayName()+".I", 4);
					plugin.saveConfig();
				}		
			}
		
		if(p.getLevel() == A5) {	
				if((Haschmap.getOrDefault(p, plugin.getConfig().getInt(p.getDisplayName()+".I")) < 3)) {
					p.sendMessage(ChatColor.GOLD + "congrats you obtained a new skill" + ChatColor.YELLOW + " Enchanter");
					plugin.getConfig().set(p.getDisplayName()+".I", 3);
					plugin.saveConfig();
					}		
			}
		
		if(p.getLevel() == A6) {	
				if((Haschmap.getOrDefault(p, plugin.getConfig().getInt(p.getDisplayName()+".I")) < 3)) {
					p.sendMessage(ChatColor.GOLD + "congrats you obtained a titel/skill" + ChatColor.YELLOW + " Mr. shulker");
					plugin.getConfig().set(p.getDisplayName()+".I", 3);
					plugin.saveConfig();
					}		
			}
		
		if(p.getLevel() == A7) {	
				if((Haschmap.getOrDefault(p, plugin.getConfig().getInt(p.getDisplayName()+".I")) < 3)) {
					p.sendMessage(ChatColor.GOLD + "congrats you obtained a new titel/skill" + ChatColor.YELLOW + " Nether Farming");
					plugin.getConfig().set(p.getDisplayName()+".I", 3);
					plugin.saveConfig();
					}		
			}
		if(p.getLevel() == A8) {	
			if((Haschmap.getOrDefault(p, plugin.getConfig().getInt(p.getDisplayName()+".I")) < 3)) {
				p.sendMessage(ChatColor.GOLD + "congrats you obtained a new titel/skill" + ChatColor.YELLOW + " Beacon of Hope");
				plugin.getConfig().set(p.getDisplayName()+".I", 3);
				plugin.saveConfig();
				}		
		}
	}
}
