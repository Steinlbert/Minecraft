package me.steinlbert.events;

import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.ServerLoadEvent;



public class combatlock implements Listener {

	public static HashMap<String, String> Damager = new HashMap<>();
	
	
	
	
	
	
	@EventHandler
	public static void Player (EntityDamageByEntityEvent e) {
		long lastatack = Bukkit.getServer().getWorlds().getFirst().getTime();
		long combattime = 144000;//1 * 60 * 20L;
		long Time = 0;
		String Stdwert = "Stdwert";
		
				Entity  ent =e.getEntity();
				int damager = e.getDamageSource().getDirectEntity().getEntityId();
				

				
					
					if (e.getDamageSource().getCausingEntity().getType().equals(EntityType.PLAYER)) 
					{
						if(e.getEntity().toString().contains("CraftPlayer")){
							//e.getEntity().sendMessage(ChatColor.YELLOW + "You where COmbat tagged by Player "+ ChatColor.RED+ e.getDamageSource().getCausingEntity().getName() +e.getEntity()+" do not Logout!"+e.getEntity().getName());
							Damager.put(e.getEntity().getName(), "1");
							
						}
						
					}
	}			
					
					
					
						@EventHandler
					public static void playermovement (PlayerMoveEvent g) {
						
							if(!Damager.isEmpty()) {
								if(Damager.get(g.getPlayer().getDisplayName()).contains("1")){
									g.getPlayer().sendMessage(ChatColor.RED + "You where Combat tagged by Player "+g.getPlayer().getLastDamageCause().getEntity().getName() +"!" );
									Damager.put(g.getPlayer().getDisplayName(), "1");
								}
								if(Damager.get(g.getPlayer().getDisplayName()).contains("3")){
									
									g.getPlayer().sendMessage(ChatColor.GREEN + "You may log off, youre no longer Combat tagged!" );
									Damager.put(g.getPlayer().getDisplayName(), "0");
								}
		
								 final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();	    
								 if(Damager.get(g.getPlayer().getDisplayName()).contains("1")) {
									 executorService.scheduleAtFixedRate(new Runnable() {
									       	
									    	@Override
									        public void run() {
									    		if(Damager.get(g.getPlayer().getDisplayName()).contains("1")) {
													Damager.put(g.getPlayer().getDisplayName(), "2");
									    		}else {     if(Damager.get(g.getPlayer().getDisplayName()).contains("2")) {}  Damager.put(g.getPlayer().getDisplayName(), "3"); }
									        }
									    }, 0, 40, TimeUnit.SECONDS);
									 
								 }

	
	
							}
						}
						@EventHandler
						public static void playerleave(PlayerQuitEvent  g) {
							
							
							
								if(Damager.get(g.getPlayer().getDisplayName()).contains("2") || (Damager.get(g.getPlayer().getDisplayName()).contains("1"))){
									g.getPlayer().setHealth(0);

								}
							
						}
}
	
						
