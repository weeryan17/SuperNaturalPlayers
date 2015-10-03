package com.weeryan17.snp.Commands;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.weeryan17.snp.Main;

public class ClanCommand implements CommandExecutor {
	@SuppressWarnings("unused")
	private Main instance;
	
	public ClanCommand(Main instance){
		this.instance = instance;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lable, String[] args) {
		if(cmd.getLabel().equalsIgnoreCase("clan")){
			if(args.length == 0){
				sender.sendMessage(ChatColor.RED + "[SNP Help]");
				sender.sendMessage(ChatColor.YELLOW + "/clan list");
				sender.sendMessage(ChatColor.YELLOW + "   list all clans and wether there open or not");
				sender.sendMessage(ChatColor.YELLOW + "/clan join <name>, or /clan join <player> <name>");
				sender.sendMessage(ChatColor.YELLOW + "   makes you join the clan if it is open, or requests you to join the clan. ");
				sender.sendMessage(ChatColor.YELLOW + "   if you have the permision or you're op you can force another person to join the clan wether open or closed");
				sender.sendMessage(ChatColor.YELLOW + "/clan create <name>");
				sender.sendMessage(ChatColor.YELLOW + "   creates a new clan with the specifyed name");
				sender.sendMessage(ChatColor.YELLOW + "/clan options");
				sender.sendMessage(ChatColor.YELLOW + "   brings up the clan options that you can set if you made the clan");
			}
		}
		return false;
	}

}
