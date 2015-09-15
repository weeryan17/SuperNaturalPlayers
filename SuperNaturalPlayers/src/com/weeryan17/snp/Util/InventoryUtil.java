package com.weeryan17.snp.Util;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;

import com.weeryan17.snp.Main;

public class InventoryUtil {
	public static void AdminInv(Player player){
		Inventory inv = Bukkit.createInventory(player, 9, ChatColor.YELLOW + "Admin Tools");
		ItemStack item1 = new ItemStack(Material.CLAY_BALL);
		ItemStack item2 = new ItemStack(Material.POTION);
		ItemMeta meta1 = item1.getItemMeta();
		ItemMeta meta2 = item2.getItemMeta();
		meta1.setDisplayName(ChatColor.BLUE + "Admin Soulstone");
		meta2.setDisplayName(ChatColor.BLUE + "Admin Blood Potion");
		item1.setItemMeta(meta1);
		item2.setItemMeta(meta2);
		Main.addLore(item1, ChatColor.AQUA + "Admin version of the soulstone");
		Main.addLore(item2, ChatColor.AQUA + "Admin Blood potion");
		inv.addItem(item1);
		inv.addItem(item2);
		player.openInventory(inv);
	}
}
