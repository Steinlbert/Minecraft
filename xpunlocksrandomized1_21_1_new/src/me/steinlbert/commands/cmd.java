package me.steinlbert.commands;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import me.steinlbert.main;





public class cmd implements Listener, CommandExecutor{
	
	
	main plugin = main.getPlugin(main.class); 
	String Configwert = plugin.getConfig().saveToString();
	int lvlwert = 0;
	
	
	@Override
	public boolean onCommand (CommandSender sender, Command com, String text, String[] args) {
		
		if (sender instanceof Player) {
			if(com.getName().equalsIgnoreCase("xpunlocks")) {
				sender.sendMessage(ChatColor.GOLD + "xpunlocks Version 1.0.0 [Author Steinlbert]");
			}
			if(com.getName().equalsIgnoreCase("xpunlocks-help")) {
				sender.sendMessage(ChatColor.GOLD + "xpunlocks Version 1.0.0");
				sender.sendMessage(ChatColor.GOLD + "Der Name ist Programm -> Lvl 5 schaltet die erste Fähigkeit frei");
				sender.sendMessage(ChatColor.GOLD + "Die ersten Fähigkeit ist das Craften einer Werkbank");
				sender.sendMessage(ChatColor.GOLD + "Es wird 2 Versionen von XPunlocks geben eine Randomized für jeden Spieler und eine mit fix werten");
				sender.sendMessage(ChatColor.GOLD + "Weitere Fähigkeiten sind: Minen, Schmieden, Brauen, Farmen, Get-Loot-from-Entitys und den Zauberer");
			}
			
			if(com.getName().equalsIgnoreCase("xpunlocks-help-en")) {
				sender.sendMessage(ChatColor.GOLD + "xpunlocks Version 1.0.0");
				sender.sendMessage(ChatColor.GOLD + "Lvl 5 unlocks the first ability");
				sender.sendMessage(ChatColor.GOLD + "The first skill is crafting a workbench");
				sender.sendMessage(ChatColor.GOLD + "There will be 2 versions of XPunlocks, one randomized for each player and one with fixed scoring");
				sender.sendMessage(ChatColor.GOLD + "Other skills include: Mining, Smithing, Brewing, Farming, Get-Loot-from-Entities and the Wizard\r\n");
			}
			
			if(com.getName().equalsIgnoreCase("xpunlocks-config")) {
				if(sender.isOp()) 
					{
					sender.sendMessage(ChatColor.GOLD + "xpunlocks Version 1.0.0 (randomized)");
					sender.sendMessage("Config file:");
					sender.sendMessage(Configwert);
					}
				}
			if(com.getName().equalsIgnoreCase("xpunlocks-resetxp")) {
				if(sender.isOp()) 
					{
					sender.sendMessage(ChatColor.GOLD + "xp resettet..");
					((Player) sender).setLevel(0);
					}
				if(!sender.isOp()) {sender.sendMessage(ChatColor.GOLD + "Nur ein Admin kann das machen...");} 
				}
			if(com.getName().equalsIgnoreCase("xpunlocks-rewards")) { 
					
					sender.sendMessage(ChatColor.GOLD + "XP rewards are Hunter, stonecutter and Crafter, Farmer, mining and smithing, Brewing, Enchanter, Mr. shulker and Beacon of Hope and Netherit farmer.");
					
				}
			
			
			if(com.getName().equalsIgnoreCase("Xpunlocks-kit")) { 
				if(args.length == 0) {
				sender.sendMessage("You need chose reward only available at LVL 0 " + ChatColor.LIGHT_PURPLE + " /xpunlocks-kit 1 or /xpunlocks-kit 2 or /xpunlocks-kit 3 or /xpunlocks-kit 4 or /xpunlocks-kit 5");
				sender.sendMessage(ChatColor.YELLOW + "/xpunlocks-kit 1 = 10 Torches");
				sender.sendMessage(ChatColor.ITALIC + "/xpunlocks-kit 2 = 1 wooden_sword");
				sender.sendMessage(ChatColor.DARK_AQUA + "/xpunlocks-kit 3 = 10 Cobbelstones");
				sender.sendMessage(ChatColor.AQUA + "/xpunlocks-kit 4 = 5 bread");
				sender.sendMessage(ChatColor.GOLD + "/xpunlocks-kit 5 = 1 Bed");
				sender.sendMessage("you have this once in a livetime chance to select a kit so what will you do?");
			}
				
				if (args.length == 1) {
				Player p = ((Player) sender).getPlayer();
				String A9String = plugin.getConfig().getString(p.getDisplayName()+".I");
				
					
					String Wert = args[0];	
					if(p.getLevel() == 0) {
						
						if(plugin.getConfig().getInt(p.getDisplayName()+".I") == 0) {
							
						if(Wert.equalsIgnoreCase("1")) {
							sender.sendMessage(ChatColor.YELLOW + "10 Torches useful indeed");
							p.setItemInHand(new ItemStack(Material.TORCH, 10));
							plugin.getConfig().set(p.getDisplayName()+".I", 1);
							plugin.saveConfig();
						}
						
						if(Wert.equalsIgnoreCase("2")) {
							sender.sendMessage(ChatColor.ITALIC + "At least you have a sword");
							p.setItemInHand(new ItemStack(Material.WOODEN_SWORD, 1));
							plugin.getConfig().set(p.getDisplayName()+".I", 1);
							plugin.saveConfig();
							}
						
						if(Wert.equalsIgnoreCase("3")) {
							sender.sendMessage(ChatColor.DARK_AQUA + "You select 10 Cobbelstones but what will you do with 10 Stone");
							p.setItemInHand(new ItemStack(Material.COBBLESTONE, 10));
							plugin.getConfig().set(p.getDisplayName()+".I", 1);
							plugin.saveConfig();
						}
						if(Wert.equalsIgnoreCase("4")) {
							sender.sendMessage(ChatColor.GOLD + "You selected 5 bread");
							p.setItemInHand(new ItemStack(Material.BREAD, 5));
							plugin.getConfig().set(p.getDisplayName()+".I", 1);
							plugin.saveConfig();
						}
						if(Wert.equalsIgnoreCase("5")) {
							sender.sendMessage(ChatColor.GOLD + "You selected 1 Bed");
							p.setItemInHand(new ItemStack(Material.BLUE_BED, 1));
							plugin.getConfig().set(p.getDisplayName()+".I", 1);
							plugin.saveConfig();
						}
						
						}else {sender.sendMessage(ChatColor.BOLD+ "Player: " + p.getDisplayName() + " has already chosen a kit...");}
						
				
					}else {
					sender.sendMessage(ChatColor.BOLD+ "Player: " + p.getDisplayName() + " You missed your change to select the Kit");}
				}
				
			}

		}			
		return false;		
	}
}
