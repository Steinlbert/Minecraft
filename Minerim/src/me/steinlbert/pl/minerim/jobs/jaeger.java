package me.steinlbert.pl.minerim.jobs;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class jaeger implements Listener {
  //public jaeger(JavaPlugin plugin) {
    //plugin.getServer().getPluginManager().registerEvents(this, (Plugin)plugin);
  //}
  
  @EventHandler
  public void tieret(EntityDeathEvent v) {
    LivingEntity entity = v.getEntity();
    if (entity.getType() == EntityType.CHICKEN && 
      entity.getKiller() == null)
      v.getDrops().clear(); 
    if (entity.getType() == EntityType.COW && 
      entity.getKiller() == null)
      v.getDrops().clear(); 
    if (entity.getType() == EntityType.SHEEP && 
      entity.getKiller() == null)
      v.getDrops().clear(); 
    if (entity.getType() == EntityType.PIG && 
      entity.getKiller() == null)
      v.getDrops().clear(); 
    if (entity.getType() == EntityType.RABBIT && 
      entity.getKiller() == null)
      v.getDrops().clear(); 
    if (entity.getType() == EntityType.LLAMA && 
      entity.getKiller() == null)
      v.getDrops().clear(); 
    if (entity.getType() == EntityType.DONKEY && 
      entity.getKiller() == null)
      v.getDrops().clear(); 
    if (entity.getType() != EntityType.PLAYER)
      if (entity.getKiller() != null && (
        !entity.getKiller().hasPermission("Minerim.Jaeger") || !entity.getKiller().hasPermission("Minerim.Bauer"))) {
        if (entity.getType() == EntityType.CHICKEN)
          v.getDrops().clear(); 
        if (entity.getType() == EntityType.COW)
          v.getDrops().clear(); 
        if (entity.getType() == EntityType.SHEEP)
          v.getDrops().clear(); 
        if (entity.getType() == EntityType.PIG)
          v.getDrops().clear(); 
        if (entity.getType() == EntityType.RABBIT)
          v.getDrops().clear(); 
        if (entity.getType() == EntityType.LLAMA)
          v.getDrops().clear(); 
        if (entity.getType() == EntityType.DONKEY)
          v.getDrops().clear(); 
      }  
  }
}
