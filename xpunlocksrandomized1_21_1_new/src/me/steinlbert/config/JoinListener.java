package me.steinlbert.config;

import org.bukkit.event.Listener;

import java.io.File;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.regex.Pattern;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.loot.LootTable;
import org.bukkit.loot.LootTables;

import me.steinlbert.main;

public class JoinListener implements Listener {

    private main plugin;

    public JoinListener(main plugin) {
        this.plugin = plugin;
    }
    
    //random macht es randomized evtl. vzwischen 3 und 55
    Random rand = new Random();
    
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
    	Player p = e.getPlayer();
    	
    	
    	//***************************************************************//

    	
    	
    	
    	
    	
    	
    	
    	
    	
	
    	
	    //String ganzeconfig = plugin.getConfig().saveToString(); 									//ganze config to string
    	
    		//randomized Werte..
    	   	int A = rand.nextInt(30);
    	    int B = rand.nextInt(30);
    	    int C = rand.nextInt(30);
    	    int D = rand.nextInt(30);
    	    int E = rand.nextInt(30);
    	    int F = rand.nextInt(30);
    	    int G = rand.nextInt(30);
    	    int H = rand.nextInt(30);
    
    	    //Lädt die Int werte aus config und einen String vergleichswert
    	    int A1 = plugin.getConfig().getInt(p.getDisplayName()+".A");
    	    int A2 = plugin.getConfig().getInt(p.getDisplayName()+".B");
    	    int A3 = plugin.getConfig().getInt(p.getDisplayName()+".C");
    	    int A4 = plugin.getConfig().getInt(p.getDisplayName()+".D");
    	    int A5 = plugin.getConfig().getInt(p.getDisplayName()+".E");
    	    int A6 = plugin.getConfig().getInt(p.getDisplayName()+".F");
    	    int A7 = plugin.getConfig().getInt(p.getDisplayName()+".G");
    	    int A8 = plugin.getConfig().getInt(p.getDisplayName()+".H");
    	    
    	    
    	    String A1String = plugin.getConfig().getString(p.getDisplayName()+".A");
    	    String A2String = plugin.getConfig().getString(p.getDisplayName()+".B");
    	    String A3String = plugin.getConfig().getString(p.getDisplayName()+".C");
    	    String A4String = plugin.getConfig().getString(p.getDisplayName()+".D");
    	    String A5String = plugin.getConfig().getString(p.getDisplayName()+".E");
    	    String A6String = plugin.getConfig().getString(p.getDisplayName()+".F");
    	    String A7String = plugin.getConfig().getString(p.getDisplayName()+".G");
    	    String A8String = plugin.getConfig().getString(p.getDisplayName()+".H");
    	    String A9String = plugin.getConfig().getString(p.getDisplayName()+".I");

    	
    	    
    	    if(p == e.getPlayer()) {
            String Spieler = this.plugin.getConfig().getString(p.getDisplayName()); 						//lädt die section Spieler wenn nicht vorhanden -> null
            //p.sendMessage(""+A1test);
            if(p.getLevel() == 0) {
            	if(plugin.getConfig().getInt(p.getDisplayName()+".I") <1) {
            		p.sendMessage("You can chose a reward only available at LVL 0 " + ChatColor.LIGHT_PURPLE + " /xpunlocks-kit 1 or /xpunlocks-kit 2 or /xpunlocks-kit 3 or /xpunlocks-kit 4 or /xpunlocks-kit 4");
    				p.sendMessage(ChatColor.YELLOW + "/xpunlocks-kit 1 = 10 Torches");
    				p.sendMessage(ChatColor.ITALIC + "/xpunlocks-kit 2 = 1 wooden Sword");
    				p.sendMessage(ChatColor.DARK_AQUA + "/xpunlocks-kit 3 = 10 Cobbelstones");
    				p.sendMessage(ChatColor.AQUA + "/xpunlocks-kit 4 = 5 bread");
    				p.sendMessage(ChatColor.GOLD + "/xpunlocks-kit 5 = 1 Bed");
    				p.sendMessage("you have this once in a livetime chance to select a kit so what will you do?");	
            	}

            }
            //checkt ob ein section mit dem Spieler namen bereits besteht...
    		if(Spieler == null) {
    			ConfigurationSection s = plugin.getConfig().createSection(p.getDisplayName());
        		s.set("A", A); //s.set("A:", A);
        		s.set("B", B);
        		s.set("C", C);
        		s.set("D", D);
        		s.set("E", E);
        		s.set("F", F);
        		s.set("G", G);
        		s.set("H", H);
        		s.set("I", 0);
        		p.sendMessage(ChatColor.AQUA + "xprandomized werte für "+ p.getDisplayName()+ " erfolgreich gesetzt!");
        		plugin.saveConfig();
    		}else {
    			//Setzt die werte in der Config auch wenn Spieler bereits vorhanden ist checkt jeden config Wert
    			if(!(this.plugin.getConfig().getString(p.getDisplayName())==null)) {
    				ConfigurationSection s2 = plugin.getConfig().getConfigurationSection(p.getDisplayName());				

      	    	  
      	    	  
    				if(A1String==null)
  	    			{
    				s2.set("A", A);
  	    			}
    				if(A2String==null)
    				{
    				s2.set("B", B);
  	    			}
    				if(A3String==null)
    				{
    				s2.set("C", C);
  	    			}
    				if(A4String==null)
    				{
    				s2.set("D", D);
  	    			}
    				if(A5String==null)
    				{
    				s2.set("E", E);
  	    			}
    				if(A6String==null)
    				{
    				s2.set("F", F);
  	    			}
    				if(A7String==null)
    				{
    				s2.set("G", G);
  	    			}
    				if(A8String==null)
    				{
    				s2.set("H", H);
  	    			}
    				if(A9String==null)
    				{
    				s2.set("I", 0);
  	    			}
    				
    				
    				//checkt ob nicht irgendein Text in der config steht ersetztdiesen sonst...
    				if(!(A1String==null)&&!(A2String==null)&&!(A3String==null)&&!(A4String==null)&&!(A5String==null)&&!(A6String==null)&&!(A7String==null)&&!(A8String==null)&&!(A9String==null)) {
    				if (Pattern.matches("[a-zA-Z]+", A1String)||Pattern.matches("[a-zA-Z]+", A2String)||Pattern.matches("[a-zA-Z]+", A3String)||Pattern.matches("[a-zA-Z]+", A4String)||Pattern.matches("[a-zA-Z]+", A5String)||Pattern.matches("[a-zA-Z]+", A6String)||Pattern.matches("[a-zA-Z]+", A7String)||Pattern.matches("[a-zA-Z]+", A8String)) 
    				{
      	    		s2.set("A", A);s2.set("B", B);s2.set("C", C);s2.set("D", D);s2.set("E", E);s2.set("F", F);s2.set("G", G);s2.set("H", H);s2.set("I", 0);
      	    		p.sendMessage(ChatColor.GOLD + "Ein wert war falsch deswegen alle Zahlen Werte für "+p.getDisplayName()+" neu gesetzt");
      	    	    }
    				}
      	    	    
      	    	    }
      	    	    	
      	    	    	
      	    	    }
      	    	    
      	    	    
      	    	    
      	    }
    			
    			
    			//if(isDouble(A1test)) {}else {}
    			//if() {p.sendMessage(ChatColor.GOLD + "A1 ist ein intger HUraaa");} 
    			//p.sendMessage(p.getDisplayName() + " bereits in Config -xpunlocksrandomized- vorhanden!");

				plugin.saveConfig();

    				}
    			
    			
    			
    			

    		

    	}


  

