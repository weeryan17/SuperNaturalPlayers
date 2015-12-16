package com.weeryan17.snp.Commands;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Skeleton.SkeletonType;

import com.weeryan17.snp.Main;
import com.weeryan17.snp.Util.EntityHider;
import com.weeryan17.snp.Util.WitherStuff;

public class WitherCommand implements CommandExecutor{
	Map<Player, Integer> map = new HashMap<Player, Integer>();
	private Main instance;
    public WitherCommand(Main instance) {
        this.instance = instance;
    }
	public boolean onCommand(CommandSender sender, Command cmd, String lable, String[] args) {
		if(cmd.getName().equalsIgnoreCase("wither")){
			final String name = sender.getName().toString();
			if(sender instanceof Player && instance.getDataConfig().get("Players." + name + ".type").toString().equals("Necromancer")){
				if(instance.getDataConfig().getBoolean("Players." + name + ".Truce") == true){
				final Player player = Bukkit.getPlayer(name);
				if(instance.getDataConfig().getBoolean("Player." + name + ".WC") == false){
					Location loc = player.getLocation();
					final Skeleton skely = (Skeleton)loc.getWorld().spawnEntity(loc, EntityType.SKELETON);
					skely.setSkeletonType(SkeletonType.WITHER);
					Main.noAI(skely);
					EntityHider hide = new EntityHider(this.instance, EntityHider.Policy.BLACKLIST);
					hide.hideEntity(player, skely);
					
					int stop1 = Bukkit.getScheduler().scheduleSyncRepeatingTask(this.instance, new WitherStuff(player, skely, instance.getConfig().getInt("General." + "Timings" + ".Entity Discusier Teloporting(ticks)")), 0, instance.getConfig().getInt("General." + "Timings" + ".Entity Discusier Teloporting(ticks)"));
					map.put(player, stop1);
					for(Player pl : Bukkit.getOnlinePlayers()){
						pl.hidePlayer(player);
					}
					instance.getDataConfig().set("Player." + name + ".WC", true);
					Bukkit.getScheduler().scheduleSyncDelayedTask(this.instance, new Runnable(){

						public void run() {
							WC(name);
							
						}
						
					}, 6000);
					Bukkit.getScheduler().scheduleSyncDelayedTask(this.instance, new Runnable(){

						public void run() {
							stop(player, skely);
							
						}
						
					}, 600);
				} else {
					sender.sendMessage(ChatColor.DARK_GRAY + "Wither is on cool down.");
				}
			} else {
				sender.sendMessage(ChatColor.DARK_GRAY + "You don't have a truce with the monsters so you can't be a wither");
			}
			
		}else {
			sender.sendMessage(ChatColor.DARK_GRAY + "You arn't a necromancer so you can't do this command");
		}
		}
		
		
		instance.saveDataConfig();
		return false;
	}
	public void WC(String name){
		instance.getDataConfig().set("Player." + name + ".WC", false);
		instance.saveDataConfig();
	}
	public void stop(Player player, Skeleton skely){
		skely.remove();
		for(Player pl : Bukkit.getOnlinePlayers()){
		pl.showPlayer(player);
		}
		Bukkit.getScheduler().cancelTask(map.get(player));
	}

}
