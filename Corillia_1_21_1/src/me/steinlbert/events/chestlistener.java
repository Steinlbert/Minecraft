package me.steinlbert.events;

import java.util.HashMap;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Chest;
import org.bukkit.block.data.type.Chest.Type;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockEvent;
import org.bukkit.event.entity.EntityBreakDoorEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import me.steinlbert.main;
import me.steinlbert.configmanager.Chestconfig;

public class chestlistener implements Listener {
    public static HashMap<String, HashMap<String, String>> chestData = new HashMap<>();

    public static void loadChestData() {
        chestData.clear();
        if (Chestconfig.getCustomConfig() == null) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[ERROR] Chest config is null, cannot load chestData!");
            return;
        }
        ConfigurationSection chests = Chestconfig.getCustomConfig().getConfigurationSection("chests");
        if (chests != null) {
            for (String chestLoc : chests.getKeys(false)) {
                ConfigurationSection chest = chests.getConfigurationSection(chestLoc);
                if (chest != null) {
                    HashMap<String, String> permissions = new HashMap<>(); 				//Lädt die persmissions für jede kiste wenn nichts in hasmap ist
                    for (String player : chest.getKeys(false)) {
                        permissions.put(player, chest.getString(player));
                    }
                    String newLoc = chestLoc.replace(".", ","); //ist notwendig damit die werte richtig gesetzt werden
                    chestData.put(newLoc, permissions);
                    //chestsettings.set(newLoc, permissions);
                    //chestsettings.set(chestLoc, null);
                } 
                
                //Migration script a bit buggy komplette klasse neu erstellt anderer struktur
                /*else {
                    // Migrate nested structure (world.-178.74.128)
                    ConfigurationSection worldSection = chests.getConfigurationSection(chestLoc);
                    if (worldSection != null) {
                        for (String x : worldSection.getKeys(false)) {
                            ConfigurationSection xSection = worldSection.getConfigurationSection(x);
                            if (xSection != null) {
                                for (String y : xSection.getKeys(false)) {
                                    ConfigurationSection ySection = xSection.getConfigurationSection(y);
                                    if (ySection != null) {
                                        for (String z : ySection.getKeys(false)) {
                                            ConfigurationSection zSection = ySection.getConfigurationSection(z);
                                            if (zSection != null) {
                                                HashMap<String, String> permissions = new HashMap<>();
                                                for (String player : zSection.getKeys(false)) {
                                                    permissions.put(player, zSection.getString(player));
                                                }
                                                String newLoc = chestLoc + "," + x + "," + y + "," + z;
                                                chestData.put(newLoc, permissions);
                                                Chestconfig.getCustomConfig().set("chests." + newLoc, permissions);
                                                Chestconfig.getCustomConfig().set("chests." + chestLoc + "." + x + "." + y + "." + z, null);
                                            }
                                        }
                                        if (ySection.getKeys(false).isEmpty()) {
                                            Chestconfig.getCustomConfig().set("chests." + chestLoc + "." + x + "." + y, null);
                                        }
                                    }
                                }
                                if (xSection.getKeys(false).isEmpty()) {
                                    Chestconfig.getCustomConfig().set("chests." + chestLoc + "." + x, null);
                                }
                            }
                        }
                        if (worldSection.getKeys(false).isEmpty()) {
                            Chestconfig.getCustomConfig().set("chests." + chestLoc, null);
                        }
                    }
                }*/
                
            }
            //Chestconfig.saveCustomConfig();
        }
        //Bukkit.getConsoleSender().sendMessage(ChatColor.BLUE + "[DEBUG] Loaded chestData: " + chestData.toString());
    }

    //Methoden wenn auf die kisten zugegriffen wird...
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent Inv) {
        if (Inv.getAction().toString().contains("RIGHT_CLICK_BLOCK") && Inv.getClickedBlock() != null) {
            Block block = Inv.getClickedBlock();
            Player p = Inv.getPlayer();
            
            String chestloc = chests.locationToString(block.getLocation());
            ConfigurationSection playersettings = Chestconfig.getCustomConfig().getConfigurationSection("settings.players");
            ConfigurationSection chestsettings = Chestconfig.getCustomConfig().getConfigurationSection("chests");
            
            //p.sendMessage(ChatColor.BLUE + "[DEBUG] Interacting with block at " + chestloc + ", Type: " + block.getType());

            if (chests.isPrivateChestsDisabled()) {
                p.sendMessage(ChatColor.GREEN + "Private chest and door protection disabled during conquest!");
                return;
            }

            boolean isProtected = chests.isChest(block) || chests.isDoor(block);
            if (!isProtected) {
                String playerSetting = chests.playerSettings.getOrDefault(p.getDisplayName(), "0");
                if ("1".equals(playerSetting)) {
                    p.sendMessage(ChatColor.RED + "That's not a chest or door, try again!");
                    chests.playerSettings.put(p.getDisplayName(), "0");
                    chestsettings.set(p.getDisplayName(), "0");
                    Chestconfig.markConfigDirty();;
                }
                return;
            }

            HashMap<String, String> chestPermissions = chestData.get(chestloc);
            if (chestPermissions != null) {
                if (!chestPermissions.containsKey(p.getDisplayName())) {
                    p.sendMessage(ChatColor.RED + "You are not allowed to access this " + (chests.isChest(block) ? "chest" : "door") + "!");
                    Inv.setCancelled(true);
                    return;
                }
            }

            String playerSetting = chests.playerSettings.getOrDefault(p.getDisplayName(), "0");
            if ("1".equals(playerSetting)) {
                if (chestPermissions == null) {
                    HashMap<String, String> newPermissions = new HashMap<>();
                    newPermissions.put(p.getDisplayName(), "owner");
                    chestData.put(chestloc, newPermissions);
                    if (Chestconfig.getCustomConfig() != null) {
                        chestsettings.set(chestloc, newPermissions);
                        chests.playerSettings.put(p.getDisplayName(), "0");
                        playersettings.set(p.getDisplayName(), "0");
                        Chestconfig.markConfigDirty();;
                        p.sendMessage(ChatColor.GREEN + "Private " + (chests.isChest(block) ? "Chest" : "Door") + " created!");
                        Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "[Chests] " + p.getName() + " set " + (chests.isChest(block) ? "chest" : "door") + " to private at " + chestloc);
                    } else {
                        p.sendMessage(ChatColor.RED + "Error: Chest config not loaded!");
                    }
                } else {
                    if ("owner".equals(chestPermissions.get(p.getDisplayName()))) {
                        chestData.remove(chestloc);
                        if (Chestconfig.getCustomConfig() != null) {
                            chestsettings.set(chestloc, null);
                            chests.playerSettings.put(p.getDisplayName(), "0");
                            playersettings.set(p.getDisplayName(), "0");
                            Chestconfig.markConfigDirty();
                            p.sendMessage(ChatColor.GREEN + "This " + (chests.isChest(block) ? "chest" : "door") + " is no longer private!");
                            Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "[Chests] " + p.getName() + " removed private status from " + (chests.isChest(block) ? "chest" : "door") + " at " + chestloc);
                        } else {
                            p.sendMessage(ChatColor.RED + "Error: Chest config not loaded!");
                        }
                    } else {
                        p.sendMessage(ChatColor.YELLOW + "This " + (chests.isChest(block) ? "chest" : "door") + " is already locked!");
                        Inv.setCancelled(true);
                    }
                }
                return;
            }

            String addPlayer = chests.playerSettings.getOrDefault(p.getDisplayName() + "_add", "0");
            if (!addPlayer.equals("0")) {
                if (chestPermissions != null && "owner".equals(chestPermissions.get(p.getDisplayName()))) {
                    chestPermissions.put(addPlayer, "access");
                    chestData.put(chestloc, chestPermissions);
                    if (Chestconfig.getCustomConfig() != null) {
                        chestsettings.set(chestloc + "." + addPlayer, "access");
                        chests.playerSettings.put(p.getDisplayName(), "0");
                        chests.playerSettings.put(p.getDisplayName() + "_add", "0");
                        playersettings.set(p.getDisplayName(), "0");
                        playersettings.set(p.getDisplayName() + "_add", "0");
                        Chestconfig.markConfigDirty();;
                        p.sendMessage(ChatColor.GREEN + "Player " + addPlayer + " successfully added to " + (chests.isChest(block) ? "chest" : "door") + "!");
                    } else {
                        p.sendMessage(ChatColor.RED + "Error: Chest config not loaded!");
                    }
                } else {
                    p.sendMessage(ChatColor.RED + "You are not the owner of this " + (chests.isChest(block) ? "chest" : "door") + "!");
                    Inv.setCancelled(true);
                }
                return;
            }

            String removePlayer = chests.playerSettings.getOrDefault(p.getDisplayName() + "_remove", "0");
            if (!removePlayer.equals("0")) {
                if (chestPermissions != null && "owner".equals(chestPermissions.get(p.getDisplayName()))) {
                    if (removePlayer.equals(p.getDisplayName())) {
                        p.sendMessage(ChatColor.RED + "You cannot remove yourself as the owner!");
                        chests.playerSettings.put(p.getDisplayName(), "0");
                        chests.playerSettings.put(p.getDisplayName() + "_remove", "0");
                        if (Chestconfig.getCustomConfig() != null) {
                            playersettings.set(p.getDisplayName(), "0");
                            playersettings.set(p.getDisplayName() + "_remove", "0");
                            Chestconfig.markConfigDirty();
                        }
                        Inv.setCancelled(true);
                        return;
                    }
                    if ("all".equals(removePlayer)) {
                        chestData.remove(chestloc);
                        if (Chestconfig.getCustomConfig() != null) {
                            chestsettings.set(chestloc, null);
                            chests.playerSettings.put(p.getDisplayName(), "0");
                            chests.playerSettings.put(p.getDisplayName() + "_remove", "0");
                            playersettings.set(p.getDisplayName(), "0");
                            playersettings.set(p.getDisplayName() + "_remove", "0");
                            Chestconfig.markConfigDirty();
                            p.sendMessage(ChatColor.GREEN + "All permissions removed from this " + (chests.isChest(block) ? "chest" : "door") + "!");
                        } else {
                            p.sendMessage(ChatColor.RED + "Error: Chest config not loaded!");
                        }
                    } else {
                        chestPermissions.remove(removePlayer);
                        chestData.put(chestloc, chestPermissions);
                        if (Chestconfig.getCustomConfig() != null) {
                            chestsettings.set(chestloc + "." + removePlayer, null);
                            chests.playerSettings.put(p.getDisplayName(), "0");
                            chests.playerSettings.put(p.getDisplayName() + "_remove", "0");
                            playersettings.set("settings.players." + p.getDisplayName(), "0");
                            playersettings.set("settings.players." + p.getDisplayName() + "_remove", "0");
                            Chestconfig.markConfigDirty();;
                            p.sendMessage(ChatColor.GREEN + "Player " + removePlayer + " successfully removed from " + (chests.isChest(block) ? "chest" : "door") + "!");
                        } else {
                            p.sendMessage(ChatColor.RED + "Error: Chest config not loaded!");
                        }
                    }
                } else {
                    p.sendMessage(ChatColor.RED + "You are not the owner of this " + (chests.isChest(block) ? "chest" : "door") + "!");
                    Inv.setCancelled(true);
                }
                return;
            }
        }
    }

    @EventHandler
    public void Doorklicker(PlayerInteractEvent e) {
    	
    	if(e.getClickedBlock() != null) {
    		
    		Location Blockloc = e.getClickedBlock().getLocation();
        	Material block = e.getClickedBlock().getType();
        	String hashloc1 = chests.locationToString(e.getClickedBlock().getLocation());
        	String hashloc2 = chests.locationToStringdoor1(e.getClickedBlock().getLocation());
        	String hashloc3 = chests.locationToStringdoor2(e.getClickedBlock().getLocation());

        	
        	
        	if(block.toString().endsWith("DOOR")) {
        			
        		if(chestData.get(hashloc1)!= null) {
        			HashMap<String, String> doorpermission =  chestData.get(hashloc1);
        				if(doorpermission.containsKey(e.getPlayer().getName())) {
        					e.getPlayer().sendMessage(ChatColor.GREEN + "You are allowed to access this Door!");
        				}else {
        				e.setCancelled(true);
        				e.getPlayer().sendMessage(ChatColor.RED + "You are not allowed to access this Door!");
        			}
        		}
        		
        		if(chestData.get(hashloc2)!= null) {
        			HashMap<String, String> doorpermission =  chestData.get(hashloc2);
        			
        			
        				if(doorpermission.containsKey(e.getPlayer().getName())) {
        				
        				
        					e.getPlayer().sendMessage(ChatColor.GREEN + "You are allowed to access this Door!");
        				}else {
        				e.setCancelled(true);
        				e.getPlayer().sendMessage(ChatColor.RED + "You are not allowed to access this Door!");
        			}
        		}
        		
        		if(chestData.get(hashloc3)!= null) {
        			HashMap<String, String> doorpermission =  chestData.get(hashloc3);
        			
        			
        				if(doorpermission.containsKey(e.getPlayer().getName())) {
        				
        				
        					e.getPlayer().sendMessage(ChatColor.GREEN + "You are allowed to access this Door!");
        				}else {
        				e.setCancelled(true);
        				e.getPlayer().sendMessage(ChatColor.RED + "You are not allowed to access this Door!");
        			}
        		}
        		
        		
        		//Bukkit.broadcastMessage(doorpermission+"");
        		
        		//Bukkit.broadcastMessage(hashloc+name.hashCode()+"");
        		//if(chestData.containsKey(hashloc.toString())) { // || chestData.containsKey(hashloc2) || chestData.containsKey(Blockloc3)
        			
        			
        			
        		
        			
        			
        			
        		
        		
        		//chests.locationToString(Blockloc);
        		//if(e) {
        			
        		//}
        		
        	}
        	
        	
        	
        	/*if(chestData.containsKey(Blockloc.toString())) {
        		e.setCancelled(true);
        	}*/
    		
    		
    		
    		
    	}
    	
    	
    	
    		
    	
    	
    }
    
    @EventHandler
    public void chestklicker(InventoryOpenEvent Inv) {
        if (Inv.getInventory().getType() != InventoryType.CHEST) {
            return;
        }

        Player p = (Player) Inv.getPlayer();
        String chestloc = chests.locationToString(Inv.getInventory().getLocation());

        //p.sendMessage(ChatColor.BLUE + "[DEBUG] Opening chest at " + chestloc + ", playerSettings: " + chests.playerSettings.getOrDefault(p.getDisplayName(), "0"));

        if (chests.isPrivateChestsDisabled()) {
            p.sendMessage(ChatColor.GREEN + "Private chest and door protection disabled during conquest!");
            return;
        }

        HashMap<String, String> chestPermissions = chestData.get(chestloc);
        if (chestPermissions == null) {
            //p.sendMessage(ChatColor.GREEN + "Access Unprotected Chest!");
        } else {
            if (chestPermissions.containsKey(p.getDisplayName())) {
                p.sendMessage(ChatColor.GREEN + "You are allowed to access this chest!");
            } else {
                p.sendMessage(ChatColor.RED + "You are not allowed to access this chest!");
                Inv.setCancelled(true);
            }
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    public void Chestbreaker(BlockBreakEvent aa) {
        ConfigurationSection playersettings = Chestconfig.getCustomConfig().getConfigurationSection("settings.players");
        ConfigurationSection chestsettings = Chestconfig.getCustomConfig().getConfigurationSection("chests");
        Player player = aa.getPlayer();
        String chestloc2 = chests.locationToString(aa.getBlock().getLocation());
        Block block = aa.getBlock();
        BlockData data = block.getBlockData();

        //player.sendMessage(ChatColor.BLUE + "[DEBUG] Breaking block at " + chestloc2 + ", Type: " + block.getType() + ", GameMode: " + player.getGameMode());

        if (!chests.isChest(block)) {
            return;
        }

        if (Chestconfig.getCustomConfig() == null) {
            player.sendMessage(ChatColor.RED + "Error: Chest config not loaded!");
            aa.setCancelled(true);
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[ERROR] Chest config is null in Chestbreaker!");
            return;
        }

        if (chests.isPrivateChestsDisabled()) {
            player.sendMessage(ChatColor.GREEN + "Private chest and door protection disabled during conquest!");
            return;
        }

        String adjacentLoc = null;
        if (data instanceof Chest) {
            Chest chest = (Chest) data;
            Chest.Type type = chest.getType();
            if (type == Chest.Type.LEFT || type == Chest.Type.RIGHT) {
                String world = block.getWorld().getName();
                int Xlock = block.getX();
                int Ylock = block.getY();
                int Zlock = block.getZ();
                if (chest.getFacing() == BlockFace.WEST) {
                    adjacentLoc = type == Chest.Type.LEFT ? world + "," + Xlock + "," + Ylock + "," + (Zlock - 1) : world + "," + Xlock + "," + Ylock + "," + (Zlock + 1);
                } else if (chest.getFacing() == BlockFace.EAST) {
                    adjacentLoc = type == Chest.Type.LEFT ? world + "," + Xlock + "," + Ylock + "," + (Zlock + 1) : world + "," + Xlock + "," + Ylock + "," + (Zlock - 1);
                } else if (chest.getFacing() == BlockFace.SOUTH) {
                    adjacentLoc = type == Chest.Type.LEFT ? world + "," + (Xlock - 1) + "," + Ylock + "," + Zlock : world + "," + (Xlock + 1) + "," + Ylock + "," + Zlock;
                } else if (chest.getFacing() == BlockFace.NORTH) {
                    adjacentLoc = type == Chest.Type.LEFT ? world + "," + (Xlock + 1) + "," + Ylock + "," + Zlock : world + "," + (Xlock - 1) + "," + Ylock + "," + Zlock;
                }
                //player.sendMessage(ChatColor.BLUE + "[DEBUG] Checking adjacent chest at " + adjacentLoc);
            }
        }

        HashMap<String, String> chestPermissions = chestData.get(chestloc2);
        //player.sendMessage(ChatColor.BLUE + "[DEBUG] Chest permissions: " + (chestPermissions != null ? chestPermissions.toString() : "none"));

        if (player.getGameMode() == GameMode.CREATIVE) {
            player.sendMessage(ChatColor.GREEN + "Creative mode: Unlocking chest!");
            if (chestPermissions != null) {
                chestData.remove(chestloc2);
                chestsettings.set(chestloc2, null);
                Chestconfig.markConfigDirty();
            }
            if (adjacentLoc != null) {
                HashMap<String, String> adjPermissions = chestData.get(adjacentLoc);
                if (adjPermissions != null) {
                    chestData.remove(adjacentLoc);
                    chestsettings.set(adjacentLoc, null);
                    Chestconfig.markConfigDirty();
                    //player.sendMessage(ChatColor.BLUE + "[DEBUG] Removed adjacent chest at " + adjacentLoc);
                }
            }
            return;
        }

        if (adjacentLoc != null) {
            HashMap<String, String> adjPermissions = chestData.get(adjacentLoc);
            if (adjPermissions != null) {
                aa.setCancelled(true);
                player.sendMessage(ChatColor.RED + "Baue zuerst die gelockte benachbarte Kiste ab!");
                return;
            }
        }

        if (chestPermissions != null) {
            if (chestPermissions.containsKey(player.getDisplayName()) && "owner".equals(chestPermissions.get(player.getDisplayName()))) {
                player.sendMessage(ChatColor.GREEN + "Unlocking chest!");
                chestData.remove(chestloc2);
                chestsettings.set(chestloc2, null);
                Chestconfig.markConfigDirty();;
            } else {
                aa.setCancelled(true);
                player.sendMessage(ChatColor.RED + "You are not the Owner!");
            }
        }
    }
}