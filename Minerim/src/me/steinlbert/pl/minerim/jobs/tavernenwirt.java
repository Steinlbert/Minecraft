package me.steinlbert.pl.minerim.jobs;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class tavernenwirt implements Listener {
  
  @EventHandler
  public void Rezepte(InventoryClickEvent g) {
    ItemStack ArgonischeBier = new ItemStack(Material.POTION);
    PotionMeta ArgonischeBiermeta = (PotionMeta)ArgonischeBier.getItemMeta();
    ArgonischeBiermeta.setDisplayName(ChatColor.GOLD + "Argonisches Bier");
    ArgonischeBiermeta.addCustomEffect(new PotionEffect(PotionEffectType.CONFUSION, 2000, 2), true);
    ArgonischeBier.setItemMeta((ItemMeta)ArgonischeBiermeta);
    ShapedRecipe ArgBier = new ShapedRecipe(ArgonischeBier);
    ArgBier.shape(new String[] { "AAA", "ABA", "APA" });
    ArgBier.setIngredient('B', Material.WHEAT);
    ArgBier.setIngredient('P', Material.GLASS_BOTTLE);
    Bukkit.addRecipe((Recipe)ArgBier);
    ItemStack Skoma = new ItemStack(Material.POTION);
    PotionMeta Skomameta = (PotionMeta)ArgonischeBier.getItemMeta();
    Skomameta.setDisplayName(ChatColor.GOLD + "Skoma");
    Skomameta.addCustomEffect(new PotionEffect(PotionEffectType.LUCK, 2000, 2), true);
    Skoma.setItemMeta((ItemMeta)Skomameta);
    ShapedRecipe Skoma1 = new ShapedRecipe(Skoma);
    Skoma1.shape(new String[] { "AAA", "ABA", "ACA" });
    Skoma1.setIngredient('B', Material.APPLE);
    Skoma1.setIngredient('C', Material.GLASS_BOTTLE);
    Bukkit.addRecipe((Recipe)Skoma1);
    ItemStack Altowein = new ItemStack(Material.POTION);
    PotionMeta Altoweinmeta = (PotionMeta)Altowein.getItemMeta();
    Altoweinmeta.setDisplayName(ChatColor.GOLD + "Alto-Wein");
    Altoweinmeta.addCustomEffect(new PotionEffect(PotionEffectType.CONFUSION, 2000, 2), true);
    Altowein.setItemMeta((ItemMeta)Altoweinmeta);
    ShapedRecipe Altowein1 = new ShapedRecipe(Altowein);
    Altowein1.shape(new String[] { "AAA", "ABA", "ACA" });
    Altowein1.setIngredient('B', Material.MELON_STEM);
    Altowein1.setIngredient('C', Material.GLASS_BOTTLE);
    Bukkit.addRecipe((Recipe)Altowein1);
    ItemStack Bier = new ItemStack(Material.POTION);
    PotionMeta BierMeta = (PotionMeta)Bier.getItemMeta();
    BierMeta.setDisplayName(ChatColor.GOLD + "Bier");
    BierMeta.addCustomEffect(new PotionEffect(PotionEffectType.CONFUSION, 2000, 2), true);
    Bier.setItemMeta((ItemMeta)BierMeta);
    ShapedRecipe Bier1 = new ShapedRecipe(Bier);
    Bier1.shape(new String[] { "ABA", "ABA", "ACA" });
    Bier1.setIngredient('B', Material.WHEAT);
    Bier1.setIngredient('C', Material.GLASS_BOTTLE);
    Bukkit.addRecipe((Recipe)Bier1);
  }
  
  @EventHandler
  public void Crafting(CraftItemEvent craft) {
    if (!craft.getWhoClicked().hasPermission("Minerim.Tavernenwirt")) {
      if (craft.getRecipe().getResult().getItemMeta().getDisplayName() == "Argonisches Bier") {
        craft.setCancelled(true);
        craft.getWhoClicked().sendMessage("Du Darfst das nicht!");
      } 
      if (craft.getRecipe().getResult().getItemMeta().getDisplayName() == "Skoma") {
        craft.setCancelled(true);
        craft.getWhoClicked().sendMessage("Du Darfst das nicht!");
      } 
      if (craft.getRecipe().getResult().getItemMeta().getDisplayName() == "Alto-Wein") {
        craft.setCancelled(true);
        craft.getWhoClicked().sendMessage("Du Darfst das nicht!");
      } 
      if (craft.getRecipe().getResult().getItemMeta().getDisplayName() == "Bier") {
        craft.setCancelled(true);
        craft.getWhoClicked().sendMessage("Du Darfst das nicht!");
      } 
    } 
  }
  
  @EventHandler
  public void itemAdd(InventoryClickEvent e) {
    e.isLeftClick();
  }
}
