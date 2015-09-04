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

public class MainCommand
implements CommandExecutor {
    private Main instance;
    public String test;
    public String test2;

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
                    sender.sendMessage(ChatColor.YELLOW + "BloodPot");
            }
            }
            if (args.length == 3 && args[0].equals("toggle")) {
                this.test = args[1];
                this.test2 = args[2];
                if (args[2].equals("Demon") || args[2].equals("Werewolf") || args[2].equals("Vampire") || args[2].equals("Angel") || args[2].equals("Necromancer") || args[2].equals("Human")) {
                    sender.sendMessage("You turned " + this.test + " into a(n) " + this.test2);
                    this.instance.getConfig().set("Players." + this.test + ".type", this.test2);
                    p = Bukkit.getServer().getPlayer(this.test);
                    p.sendMessage("You are now a(n) " + this.test2);
                    this.instance.getConfig().set("Players." + this.test + ".Wolf", false);
                    this.instance.getConfig().set("Players." + this.test + ".Blood", 0);
                    this.instance.getConfig().set("Players." + this.test + ".Kills", 0);
                    this.instance.getConfig().set("Players." + this.test + ".Souls", 0);
                    this.instance.getConfig().set("Players." + this.test + ".Bat", false);
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
        } else {
            sender.sendMessage(ChatColor.RED + "You don't have permision to preform this acction");
        }
        return false;
    }
}
