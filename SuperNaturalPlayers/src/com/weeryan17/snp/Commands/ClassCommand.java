package com.weeryan17.snp.Commands;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.weeryan17.snp.Main;

public class ClassCommand implements CommandExecutor {
    private Main instance;
	public ClassCommand(Main instance) {
        this.instance = instance;
    }
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    	if(sender instanceof Player){
    		if(cmd.getName().equalsIgnoreCase("class")){
    			String playerName = sender.getName().toString();
    			Player player = Bukkit.getPlayer(playerName);
				sender.sendMessage(ChatColor.BLUE + "==========================================");
				sender.sendMessage(ChatColor.RED + "[Super natural players class info]");
				sender.sendMessage("");
    			if(this.instance.getConfig().get("Players." + playerName + ".type").toString().equals("Vampire")){
    				double lvl = this.instance.getConfig().getDouble("Players." + playerName + ".Vamplvl");
    				int i = (int) lvl;
    				double blood = this.instance.getConfig().getDouble("Players." + playerName + ".Blood");
    				int i2 = (int) blood;
    				sender.sendMessage(ChatColor.DARK_GRAY + "You are currently a lvl " + ChatColor.GOLD + i + " Vampire");
    				sender.sendMessage(ChatColor.DARK_GRAY + "You curently have " + ChatColor.GOLD + i2 + ChatColor.DARK_GRAY + " blood");
    			} else if(this.instance.getConfig().get("Players." + playerName + ".type").toString().equals("Werewolf")){
    				int lvl = this.instance.getConfig().getInt("Players." + playerName + ".FullMoons");
    				World world = player.getWorld();
    	            int days = (int)world.getFullTime() / 24000;
    	            int phase = days % 8;
    				sender.sendMessage(ChatColor.DARK_GRAY + "You are currently a lvl " + ChatColor.GOLD + lvl + " Werewolf");
    				sender.sendMessage(ChatColor.DARK_GRAY + "The next full moon is in " + ChatColor.GOLD + phase + ChatColor.DARK_GRAY + " days.");
    			} else if(this.instance.getConfig().get("Players." + playerName + ".type").toString().equals("Human")){
    				sender.sendMessage(ChatColor.DARK_GRAY + "You are a human so you don't have class info");
    			} else if(this.instance.getConfig().get("Players." + playerName + ".type").toString().equals("Necromancer")){
    				int souls = this.instance.getConfig().getInt("Players." + playerName + ".Souls");
    				sender.sendMessage(ChatColor.DARK_GRAY + "You currently have " + ChatColor.GOLD + souls + ChatColor.DARK_GRAY + " souls."); 
    			}
				sender.sendMessage("");
				sender.sendMessage(ChatColor.BLUE + "==========================================");
    		}
    	}
    	
    	
    	
		return false;
    	
    }
}
