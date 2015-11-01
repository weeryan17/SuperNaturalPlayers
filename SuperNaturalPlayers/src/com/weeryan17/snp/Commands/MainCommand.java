package com.weeryan17.snp.Commands;

import com.weeryan17.snp.Main;

import net.md_5.bungee.api.ChatColor;

//import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MainCommand implements CommandExecutor {
    private Main instance;
    public String player;
    public String race;
    public MainCommand(Main instance) {
        this.instance = instance;
    }
    //@SuppressWarnings("unchecked")
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("snp") && 
        		sender.hasPermission("snp.command") || sender.isOp()) {
            Player p;
            if (args.length == 0) {
                sender.sendMessage(ChatColor.RED + "[SNP Help]");
                sender.sendMessage(ChatColor.YELLOW + "/snp toggle <player> <race>");
                sender.sendMessage(ChatColor.YELLOW + "/snp item <player> <item>");
                sender.sendMessage(ChatColor.YELLOW + "/snp give <player> <thing you're giving> <amount>");
            }
            if(args.length == 1){
                if (args[0].equals("toggle")) {
                    sender.sendMessage(ChatColor.RED + "[SNP Help]");
                    sender.sendMessage(ChatColor.YELLOW + "/snp toggle <player> <race>");
                    sender.sendMessage(ChatColor.YELLOW + "Races:");
                    sender.sendMessage(ChatColor.YELLOW + "Demon, Werewolf, Vampire, Angel, and Necromancer");
                    //sender.sendMessage(ChatColor.YELLOW + "Races from addons:");
                    //sender.sendMessage(ChatColor.YELLOW + api.getAPIConfig().get("List." + ".Clans").toString());
                } else if (args[0].equals("item")) {
                    sender.sendMessage(ChatColor.RED + "[SNP Help]");
                    sender.sendMessage(ChatColor.YELLOW + "/snp item <player> <item>");
                    sender.sendMessage(ChatColor.YELLOW + "items:");
                    sender.sendMessage(ChatColor.YELLOW + "BloodPot, Soulstone, Tools");
            } else if (args[0].equals("give")){
            	sender.sendMessage(ChatColor.RED + "[SNP Help]");
            	sender.sendMessage(ChatColor.YELLOW + "/snp give <player> <thing you're giving> <amount>");
            	sender.sendMessage(ChatColor.YELLOW + "things you can give:");
            	sender.sendMessage(ChatColor.YELLOW + "Blood, Souls");
            }
            }
            if (args.length == 3 && args[0].equals("toggle")) {
                player = args[1];
                race = args[2];
        		//ArrayList<String> clan = (ArrayList<String>) api.getAPIConfig().get("List." + ".Clans");
                if (args[2].equals("Demon") || args[2].equals("Werewolf") || args[2].equals("Vampire") || args[2].equals("Angel") || args[2].equals("Necromancer") || args[2].equals("Human")) {
                	/**
                	 * add to if statment when api goes back in
                	 *  || clan.contains(args[2])
                	 * 
                	 */
                    sender.sendMessage("You turned " + player + " into a(n) " + race);
                    p = Bukkit.getServer().getPlayer(this.player);
                    p.sendMessage("You are now a(n) " + race);
                    instance.getDataConfig().set("Players." + player + ".Blood", 0);
                    instance.getDataConfig().set("Players." + player + ".Kills", 0);
                    instance.getDataConfig().set("Players." + player + ".Souls", 0);
                    instance.getDataConfig().set("Players." + player + ".Bat", false);
                    instance.getDataConfig().set("Players." + player + ".BloodTotal", 0);
                    instance.getDataConfig().set("Players." + player + ".Vamplvl", 0);
                    instance.getDataConfig().set("Players." + player + ".FullMoons", 0);
                    instance.getDataConfig().set("Players." + player + ".Clan", "none");
                    instance.getDataConfig().set("Players." + player + ".TotalSouls", 0);
                    if(instance.getDataConfig().getBoolean("Players." + player + ".Bat") == true){
                    }
                    instance.getDataConfig().set("Players." + player + ".type", race);
                } else {
                    sender.sendMessage("not a valid race");
                }
            }
            if (args.length == 3 && args[0].equals("item") && args[2].equalsIgnoreCase("BloodPot")) {
                p = Bukkit.getServer().getPlayer(args[1]);
                ItemStack item = new ItemStack(Material.POTION);
                ItemMeta meta = item.getItemMeta();
                meta.setDisplayName(ChatColor.RED + "Blood Potion");
                item.setItemMeta(meta);
                Main.addLore(item, ChatColor.YELLOW + "Used to turn blood into food");
                Main.addLore(item, ChatColor.YELLOW + "Only useable as a Vampire");
                p.getInventory().addItem(new ItemStack[]{item});
            }
            if(args.length == 3 && args[0].equals("item") && args[2].equalsIgnoreCase("Soulstone")){
            	p = Bukkit.getServer().getPlayer(args[1]);
            	ItemStack item = new ItemStack(Material.CLAY_BALL);
            	ItemMeta meta = item.getItemMeta();
            	meta.setDisplayName(ChatColor.BLUE + "Soulstone");
            	item.setItemMeta(meta);
            	Main.addLore(item, ChatColor.DARK_GRAY + "Used to summon a magical wither sull that will paralize enemies");
            	Main.addLore(item, ChatColor.DARK_GRAY + "only useable by necromancers");
            	p.getInventory().addItem(new ItemStack[]{item});
            }
            if(args.length == 3 && args[0].equals("item") && args[2].equalsIgnoreCase("Tools")){
            	p = Bukkit.getServer().getPlayer(args[1]);
            	ItemStack item = new ItemStack(Material.CHEST);
            	ItemMeta meta = item.getItemMeta();
            	meta.setDisplayName(ChatColor.GREEN + "Tools");
            	item.setItemMeta(meta);
            	Main.addLore(item, ChatColor.AQUA + "Admin tools");
            	p.getInventory().addItem(new ItemStack[]{item});
            }
            if (args.length == 4 && args[0].equals("give")){
            	Player player = Bukkit.getPlayer(args[1]);
        		String name = player.getName();
            	if(args[2].equalsIgnoreCase("Blood")){
            		double blood = instance.getDataConfig().getDouble("Players." + name + ".Blood");
            		double addedBlood = Double.parseDouble(args[3]);
            		blood = blood + addedBlood;
            		instance.getDataConfig().set("Players." + name + ".Blood", blood);
            	} else if(args[2].equalsIgnoreCase("Souls")){
            		int souls = instance.getDataConfig().getInt("Players." + name + ".Souls");
            		int addedSouls = Integer.parseInt(args[3]);
            		souls = souls + addedSouls;
            		instance.getDataConfig().set("Players." + name + ".Souls", souls);
            	}
        }
    }else {
        sender.sendMessage(ChatColor.RED + "You don't have permision to preform this acction");
    }
        instance.saveDataConfig();
		return false;
}
}
