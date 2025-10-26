package me.steinlbert.worldspawn;

import java.io.Console;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.block.Container;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.conversations.PlayerNamePrompt;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityCategory;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Skeleton.SkeletonType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.entity.VillagerAcquireTradeEvent;
import org.bukkit.event.world.AsyncStructureSpawnEvent;
import org.bukkit.event.world.ChunkPopulateEvent;
import org.bukkit.event.world.LootGenerateEvent;
import org.bukkit.event.world.SpawnChangeEvent;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.structure.Structure;
import org.bukkit.generator.structure.StructureType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.loot.LootTable;
import org.bukkit.loot.LootTables;
import org.bukkit.loot.Lootable;

import me.steinlbert.main;

public class craftboxspawn implements Listener{

	
	
	main plugin = main.getPlugin(main.class);

	
	
	
	
	
	/*@EventHandler
	public void crafter(LootGenerateEvent e) {
		Collection<ItemStack> test= (Collection<ItemStack>) new ItemStack(Material.ENDER_PEARL, 1);
			Entity state = (Entity) e.getEntity();
		if(e.getLootTable().equals(LootTables.SKELETON)) {
			e.getLoot().add(new ItemStack(Material.ENDER_PEARL, 1));
			
			if(state.equals(e.getEntity())) {
				state.setCustomName("NIcht Skelett");
				
			}
			
			
				LootTables g =LootTables.SKELETON;
				
				boolean skelet = e.getLootTable().equals(LootTables.SKELETON);
				
				g.getLootTable().populateLoot((Random) test, null);
				e.setLoot(test);
		}
		
		   }
		
		/*Random random = new Random();
		LootTable fishingLoot = LootTables.VILLAGER.getLootTable();
		List<ItemStack> fishingDrops = (List<ItemStack>) fishingLoot.populateLoot(random, null);

		for (ItemStack item:fishingDrops)
		    plugin.getServer().getConsoleSender().sendMessage(item + "");
		
		
		/*if (e.getLootTable().getKey().equals(LootTables.SKELETON.getKey())) {
		    
		       List<ItemStack> items = new ArrayList<>(e.getLoot());

		       Random random = new Random();

		       int result = random.nextInt(1, 2);

		           ItemStack stack2 = new ItemStack(Material.CRAFTING_TABLE);
		           ItemStack stack3 = new ItemStack(Material.STONE_AXE);
		           items.add(stack2);
		           items.add(stack3);

		           e.setLoot(items);
		       
		   } */
		
		
		
	//}
	

	@EventHandler
    public void onGeneration(ChunkPopulateEvent e) {
		
        ItemStack itemStack1 = new ItemStack(Material.CRAFTING_TABLE);
        ItemStack itemStack2 = new ItemStack(Material.BREAD, 7);
        for(BlockState state : e.getChunk().getTileEntities()) {
            if(state instanceof Chest) {	
            	
            	Chest chest = (Chest) state.getBlock().getState();
                chest.getBlockInventory().addItem(itemStack1);
                chest.getBlockInventory().addItem(itemStack2);
                Location  a = state.getLocation();
    		    //plugin.getServer().getConsoleSender().sendMessage("Laction of chest = " +a);
    		    //plugin.getServer().getConsoleSender().sendMessage("Spieler = " +e.getChunk().getPlayersSeeingChunk().toString());

            	
            	
            	//if(!e.getChunk().getPlayersSeeingChunk().isEmpty())
            	//{
                	//Collection<Player> p = e.getChunk().getPlayersSeeingChunk();
        		    //plugin.getServer().getConsoleSender().sendMessage("currentplayer = " + p);
        		 // thats the name -> [CraftPlayer{name=Steinlbert}]
        		    

            	//}
            	
            
            		

            	
            	
            	
            	
                
            }break;
        }
    }
	
}
