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
    public String player2;
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
                sender.sendMessage(ChatColor.YELLOW + "/snp reload (reloads config)");
            }
            if(args.length == 1){
                if (args[0].equals("toggle")) {
                    sender.sendMessage(ChatColor.RED + "[SNP Help]");
                    sender.sendMessage(ChatColor.YELLOW + "/snp toggle <player> <race>");
                    sender.sendMessage(ChatColor.YELLOW + "Races:");
                    sender.sendMessage(ChatColor.YELLOW + "Demon, Werewolf, Vampire, Angel, Necromancer, and Ghoul");
                } else if (args[0].equals("item")) {
                    sender.sendMessage(ChatColor.RED + "[SNP Help]");
                    sender.sendMessage(ChatColor.YELLOW + "/snp item <player> <item>");
                    sender.sendMessage(ChatColor.YELLOW + "items:");
                    sender.sendMessage(ChatColor.YELLOW + "BloodPot, Soulstone");
            }
            }
            if (args.length == 3 && args[0].equals("toggle")) {
                player = args[1];
                player2 = args[2];
                if (args[2].equals("Demon") || args[2].equals("Werewolf") || args[2].equals("Vampire") || args[2].equals("Angel") || args[2].equals("Necromancer") || args[2].equals("Human")) {
                    sender.sendMessage("You turned " + player + " into a(n) " + player2);
                    this.instance.getConfig().set("Players." + player + ".type", player2);
                    p = Bukkit.getServer().getPlayer(this.player);
                    p.sendMessage("You are now a(n) " + player2);
                    this.instance.getConfig().set("Players." + player + ".Blood", 0);
                    this.instance.getConfig().set("Players." + player + ".Kills", 0);
                    this.instance.getConfig().set("Players." + player + ".Souls", 0);
                    this.instance.getConfig().set("Players." + player + ".Bat", false);
                    this.instance.getConfig().set("Players." + player + ".BloodTotal", 0);
                    this.instance.getConfig().set("Players." + player + ".Vamplvl", 0);
                    this.instance.getConfig().set("Players." + player + ".FullMoons", 0);
                    if(this.instance.getConfig().getBoolean("Players." + player + ".Bat") == true){
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
            	Main.addLore(item, ChatColor.DARK_GRAY + "Used to summon a magical wither sull that will paralize enemies");
            	Main.addLore(item, ChatColor.DARK_GRAY + "only useable by necromancers");
            	p.getInventory().addItem(new ItemStack[]{item});
            }
        } else {
            sender.sendMessage(ChatColor.RED + "You don't have permision to preform this acction");
        }
        return false;
    }
}
