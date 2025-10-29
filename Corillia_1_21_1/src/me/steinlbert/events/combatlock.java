package me.steinlbert.events;

import java.awt.TextComponent;
import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.conversations.PlayerNamePrompt;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.ServerLoadEvent;

import me.steinlbert.configmanager.combatconfig;
import net.md_5.bungee.api.ChatMessageType;

public class combatlock implements Listener {
	public static HashMap<String, Long> Damagertime = new HashMap<String, Long>();

private String isEnabled = combatconfig.getCustomConfig().getString("enabled");


private int getCombatTime =combatconfig.getCustomConfig().getInt("time-seconds")* 1000;

	
long combatTime = getCombatTime;
	
	@EventHandler
	public void Player (EntityDamageByEntityEvent e) {
		if (!(isEnabled.contains("true"))) return;
					if (e.getDamageSource().getCausingEntity().getType().equals(EntityType.PLAYER)) 
					{
						if(e.getEntity().toString().contains("CraftPlayer")){
							e.getEntity().sendMessage(ChatColor.RED + "You where Combat tagged by Player "+e.getDamageSource().getCausingEntity().getName() +"!");
							Damagertime.put(e.getEntity().getName(), System.currentTimeMillis()); //Defender
							Damagertime.put(e.getDamageSource().getCausingEntity().getName(), System.currentTimeMillis()); // Angreifer		
						}
					}
			}			

					@EventHandler
					public void playermovement (PlayerMoveEvent g) {
						if (!(isEnabled.contains("true"))) return;
						
						if(!(Damagertime.get(g.getPlayer().getName()) == null)){
							
							if (System.currentTimeMillis() - Damagertime.get(g.getPlayer().getName()) > combatTime) {
								g.getPlayer().sendMessage(ChatColor.GREEN + "You are no longer in combat!" );
								Damagertime.clear();

							}
							
						}
						
						
					
						}
						@EventHandler
						public void playerleave(PlayerQuitEvent  g) {
							if (!(isEnabled.contains("true"))) return;
							if(!(Damagertime.get(g.getPlayer().getName()) == null)) {
								
								g.getPlayer().setHealth(0);
								Damagertime.remove(g.getPlayer().getName());    //funzt -> der andere muss halt noch warten 3-4 mehr zeilen zu faul..
							}

						}
						
						
						@EventHandler
						public void Playerjoinevent(PlayerJoinEvent on) 
						{
							if (!(isEnabled.contains("true"))) return;
								on.getPlayer().sendMessage("This Plugin include Combatlog "+ ChatColor.RED +"do not Logout mid Combat!");
							}
							
						}
						

	
						
