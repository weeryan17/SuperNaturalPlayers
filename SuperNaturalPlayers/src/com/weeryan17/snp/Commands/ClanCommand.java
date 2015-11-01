package com.weeryan17.snp.Commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import com.weeryan17.snp.Main;

public class ClanCommand implements CommandExecutor {
	Main instance;
    public ClanCommand(Main instance) {
    	this.instance = instance;
    }
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lable, String[] args) {
		if(cmd.getName().equalsIgnoreCase("clan")){
			String playerName = sender.getName();
			if(args.length == 0){
				sender.sendMessage(ChatColor.RED + "[SNP Help]");
				sender.sendMessage(ChatColor.YELLOW + "/clan list");
				sender.sendMessage(ChatColor.YELLOW + "   list all clans and wether there open or not");
				sender.sendMessage(ChatColor.YELLOW + "/clan join <name>, or /clan join <player> <name>");
				sender.sendMessage(ChatColor.YELLOW + "   makes you join the clan if it is open, or requests you to join the clan. ");
				sender.sendMessage(ChatColor.YELLOW + "   if you have the permision or you're op you can force another person to join the clan wether open or closed");
				sender.sendMessage(ChatColor.YELLOW + "/clan create <name>");
				sender.sendMessage(ChatColor.YELLOW + "   creates a new clan with the specifyed name");
				sender.sendMessage(ChatColor.YELLOW + "/clan opntions");
				sender.sendMessage(ChatColor.YELLOW + "   brings up the clan options that you can set if you made the clan");
			} else {
			if(sender instanceof Player){
				String race = instance.getClansConfig().getString("Players." + playerName + ".type");
			if(args.length == 1 && args[0].equals("list")){
	            List<String> list = new ArrayList<String>();
				ConfigurationSection Race = instance.getClansConfig().getConfigurationSection("Clans." + race + ".Clans");
		        for (String key : Race.getKeys(false)) {
		        	String configKey = "." + key;
		            boolean open = instance.getClansConfig().getBoolean("Clans." + race + ".Clans" + configKey + ".Open");
		            if(open == true){
		            	String clan = ChatColor.BLUE + key + ChatColor.AQUA + "[Open]";
		            	list.add(clan);
		            } else {
		            	String clan = ChatColor.BLUE + key + ChatColor.AQUA;
		            	list.add(clan);
		            }
		            
			}
		        String stringlist0 = list.toString();
		        String stringlist1 = Main.removeCharAt(stringlist0, 0);
		        int length = stringlist1.length() - 1;
		        String stringlist2 = Main.removeCharAt(stringlist1, length);
		        sender.sendMessage(ChatColor.RED + "[Clan list]");
		        sender.sendMessage(stringlist2);
		} else if(args.length == 1 && args[0].equals("join")){
				sender.sendMessage(ChatColor.YELLOW + "Please specify a clan name.");
			} else if(args.length == 2 && args[0].equals("join")){
				if(!args[1].equals(instance.getClansConfig().getString("Players." + playerName + ".Clan") )){
				List<String> list = new ArrayList<String>();
				Map<String, Boolean> map = new HashMap<String, Boolean>();
				ConfigurationSection Race = instance.getClansConfig().getConfigurationSection("Clans." + race + ".Clans");
		        for (String key : Race.getKeys(false)) {
		        	String configKey = "." + key;
		        	boolean open = instance.getClansConfig().getBoolean("Clans." + race + ".Clans" + configKey + ".Open");
		        	list.add(key);
		        	map.put(key, open);
		        }
		        if(list.contains(args[1])){
		        	if(map.get(args[1].toString()) == true){
		        		instance.getClansConfig().set("Players." + playerName + ".Clan", args[1].toString());
		        		sender.sendMessage(ChatColor.YELLOW + "You joined the " + args[1].toString() + " clan.");
		        	} else {
		        		String configKey = "." + args[1];
		        		sender.sendMessage(ChatColor.YELLOW + "This clan isn't open so a request to join the clan has been sent to the owner");
		        		instance.getClansConfig().set("Players." + playerName + ".requesting", args[1]);
		        		String owner = instance.getClansConfig().getString("Clans." + race + ".Clans" + configKey + ".Owner");
		        		Player playerOwner = Bukkit.getPlayer(owner);
		        		playerOwner.sendMessage(ChatColor.YELLOW + playerName + " is trying to join your clan.");
		        		playerOwner.sendMessage(ChatColor.YELLOW + "You can accept them in by doing /clan accept " + playerName);
		        	}
		        } else {
		        	sender.sendMessage(ChatColor.YELLOW + "That clan doesn't exist");
		        }
			} else {
				sender.sendMessage(ChatColor.YELLOW + "You are alredy in that clan");
			}
			} else if(args.length == 1 && args[0].equals("accept")){
				if(instance.getDataConfig().getBoolean("Players." + playerName + ".ClanOwner") == true){
				sender.sendMessage(ChatColor.YELLOW + "Please specify a player name to allow into your clan");
				} else {
					sender.sendMessage(ChatColor.YELLOW + "You arn't a clan owner so you can't accept anyone");
				}
			} else if(args.length == 2 && args[0].equals("accept")){
				if(instance.getDataConfig().getBoolean("Players." + playerName + ".ClanOwner") == true){
					
					ConfigurationSection Race = instance.getClansConfig().getConfigurationSection("Clans." + race + ".Clans");
						for(Player p : Bukkit.getOnlinePlayers()){
							String PlayerName = p.getName();
							for (String key : Race.getKeys(false)) {
					        	String configKey = "." + key;
					            String owner = instance.getClansConfig().getString("Clans." + race + ".Clans" + configKey + ".Owner");
					            if(args[1].equals(PlayerName)){
					            	if(owner.equals(playerName)) {
					            		if(instance.getDataConfig().getString("Players." + PlayerName + ".requesting").equals(instance.getDataConfig().getString("Players." + owner + ".Clan"))){
					            			instance.getDataConfig().set("Players." + args[1] + ".Clan", instance.getDataConfig().getString("Players." + owner + ".Clan"));
					            		}
									}
					            }
							}
						}
				} else {
					sender.sendMessage(ChatColor.YELLOW + "You arn't a clan owner so you can't accept anyone");
				}
			}
		}
		
	}
		}
			instance.saveDataConfig();
			instance.saveClansConfig();
		return false;
	}

}
