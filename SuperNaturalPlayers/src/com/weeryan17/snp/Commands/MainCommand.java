package com.weeryan17.snp.Commands;

import com.weeryan17.snp.Main;

import net.md_5.bungee.api.ChatColor;

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
    VampBatCommand vamp = new VampBatCommand(instance);

    public MainCommand(Main instance) {
        this.instance = instance;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("snp") && sender.hasPermission("snp.command")) {
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
                if (args[2].equals("Demon") || args[2].equals("Werewolf") || args[2].equals("Vampire") || args[2].equals("Angel") || args[2].equals("Necromancer") || args[2].equals("Human")) {
                    sender.sendMessage("You turned " + player + " into a(n) " + race);
                    this.instance.config().set("Players." + player + ".type", race);
                    p = Bukkit.getServer().getPlayer(this.player);
                    p.sendMessage("You are now a(n) " + race);
                    this.instance.config().set("Players." + player + ".Blood", 0);
                    this.instance.config().set("Players." + player + ".Kills", 0);
                    this.instance.config().set("Players." + player + ".Souls", 0);
                    this.instance.config().set("Players." + player + ".Bat", false);
                    this.instance.config().set("Players." + player + ".BloodTotal", 0);
                    this.instance.config().set("Players." + player + ".Vamplvl", 0);
                    this.instance.config().set("Players." + player + ".FullMoons", 0);
                    this.instance.config().set("Players." + player + ".Clan", "none");
                    this.instance.config().set("Players." + player + ".TotalSouls", 0);
                    this.instance.saveConfig();
                    int i = Main.randInt(1, 3);
                    if(i == 1){
                        this.instance.getLogger().info("Class number " + i);
                    	if(args[2].equals("Necromancer")){
                    		this.instance.config().set("Players." + player + ".Clan", "Noximperius");
                    	} else if(args[2].equals("Werewolf")){
                    		this.instance.config().set("Players." + player + ".Clan", "Darkclaw");
                    	} else if(args[2].equals("Vampire")){
                    		this.instance.config().set("Players." + player + ".Clan", "Nightwing");
                    	}
                    } else if(i == 2){
                        this.instance.getLogger().info("Class number " + i);
                    	if(args[2].equals("Necromancer")){
                    		this.instance.config().set("Players." + player + ".Clan", "Witherheart");
                    	} else if(args[2].equals("Werewolf")){
                    		this.instance.config().set("Players." + player + ".Clan", "Silverclaw");
                    	} else if(args[2].equals("Vampire")){
                    		this.instance.config().set("Players." + player + ".Clan", "Ashborn");
                    	}
                    } else if (i == 3){
                        this.instance.getLogger().info("Class number " + i);
                    	if(args[2].equals("Necromancer")){
                    		this.instance.config().set("Players." + player + ".Clan", "Deathskull");
                    	} else if(args[2].equals("Werewolf")){
                    		this.instance.config().set("Players." + player + ".Clan", "Bloodvenom");
                    	} else if(args[2].equals("Vampire")){
                    		this.instance.config().set("Players." + player + ".Clan", "Darkblood");
                    	}
                    }
                    if(this.instance.config().getBoolean("Players." + player + ".Bat") == true){
                    vamp.untrans(vamp.map(), p);
                    }
                    this.instance.saveConfig();
                } else {
                    sender.sendMessage("not a valid race");
                }
            }
            if (args.length == 3 && args[0].equals("item") && args[2].equals("BloodPot")) {
                p = Bukkit.getServer().getPlayer(args[1]);
                ItemStack item = new ItemStack(Material.POTION);
                ItemMeta meta = item.getItemMeta();
                meta.setDisplayName(ChatColor.RED + "Blood Potion");
                item.setItemMeta(meta);
                Main.addLore(item, ChatColor.YELLOW + "Used to turn blood into food");
                Main.addLore(item, ChatColor.YELLOW + "Only useable as a Vampire");
                p.getInventory().addItem(new ItemStack[]{item});
            }
            if(args.length == 3 && args[0].equals("item") && args[2].equals("Soulstone")){
            	p = Bukkit.getServer().getPlayer(args[1]);
            	ItemStack item = new ItemStack(Material.CLAY_BALL);
            	ItemMeta meta = item.getItemMeta();
            	meta.setDisplayName(ChatColor.BLUE + "Soulstone");
            	item.setItemMeta(meta);
            	Main.addLore(item, ChatColor.DARK_GRAY + "Used to summon a magical wither sull that will paralize enemies");
            	Main.addLore(item, ChatColor.DARK_GRAY + "only useable by necromancers");
            	p.getInventory().addItem(new ItemStack[]{item});
            }
            if(args.length == 3 && args[0].equals("item") && args[2].equals("Tools")){
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
            	if(args[2].equals("Blood")){
            		double blood = this.instance.config().getDouble("Players." + name + ".Blood");
            		double addedBlood = Double.parseDouble(args[3]);
            		blood = blood + addedBlood;
            		this.instance.config().set("Players." + name + ".Blood", blood);
            	} else if(args[2].equals("Souls")){
            		int souls = this.instance.config().getInt("Players." + name + ".Souls");
            		int addedSouls = Integer.parseInt(args[3]);
            		souls = souls + addedSouls;
            		this.instance.config().set("Players." + name + ".Souls", souls);
            	}
            	this.instance.saveConfig();
        } else {
            sender.sendMessage(ChatColor.RED + "You don't have permision to preform this acction");
        }
    }
		return false;
}
}
