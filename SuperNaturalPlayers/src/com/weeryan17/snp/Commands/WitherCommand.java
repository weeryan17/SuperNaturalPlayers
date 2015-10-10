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
import com.weeryan17.snp.Util.CustomConfig;
import com.weeryan17.snp.Util.EntityHider;
import com.weeryan17.snp.Util.WitherStuff;

public class WitherCommand implements CommandExecutor{
	Map<Player, Integer> map = new HashMap<Player, Integer>();
	private Main instance;
    CustomConfig data;
    public WitherCommand(Main instance) {
        this.instance = instance;
        this.data = new CustomConfig(instance, "data");
    }
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lable, String[] args) {
		if(cmd.getName().equalsIgnoreCase("wither")){
			final String name = sender.getName().toString();
			CustomConfig config = new CustomConfig(this.instance, "config");
			if(sender instanceof Player && data.getConfig().get("Players." + name + ".type").toString().equals("Necromancer")){
				if(data.getConfig().getBoolean("Players." + name + ".Truce") == true){
				final Player player = Bukkit.getPlayer(name);
				if(data.getConfig().getBoolean("Player." + name + ".WC") == false){
					Location loc = player.getLocation();
					final Skeleton skely = (Skeleton)loc.getWorld().spawnEntity(loc, EntityType.SKELETON);
					skely.setSkeletonType(SkeletonType.WITHER);
					Main.noAI(skely);
					EntityHider hide = new EntityHider(this.instance, EntityHider.Policy.BLACKLIST);
					hide.hideEntity(player, skely);
					int stop1 = Bukkit.getScheduler().scheduleSyncRepeatingTask(this.instance, new WitherStuff(player, skely), 0, config.getConfig().getInt("General." + "Timings" + ".Entity Discusier Teloporting(ticks)"));
					map.put(player, stop1);
					for(Player pl : Bukkit.getOnlinePlayers()){
						pl.hidePlayer(player);
					}
					data.getConfig().set("Player." + name + ".WC", true);
					Bukkit.getScheduler().scheduleSyncDelayedTask(this.instance, new Runnable(){

						@Override
						public void run() {
							WC(name);
							
						}
						
					}, 6000);
					Bukkit.getScheduler().scheduleSyncDelayedTask(this.instance, new Runnable(){

						@Override
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
		
		
		
		return false;
	}
	public void WC(String name){
		data.getConfig().set("Player." + name + ".WC", false);
	}
	public void stop(Player player, Skeleton skely){
		skely.remove();
		for(Player pl : Bukkit.getOnlinePlayers()){
		pl.showPlayer(player);
		}
		Bukkit.getScheduler().cancelTask(map.get(player));
	}

}
