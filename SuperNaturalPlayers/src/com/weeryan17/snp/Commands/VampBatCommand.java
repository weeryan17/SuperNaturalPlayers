package com.weeryan17.snp.Commands;

import java.util.HashMap;
import java.util.Map;

import com.weeryan17.snp.Main;
import com.weeryan17.snp.Util.BatTimer;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Bat;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

public class VampBatCommand implements CommandExecutor {
    Map<Player, Integer> map = new HashMap<Player, Integer>();
    Player player;
    private Main instance;

    public VampBatCommand(Main instance) {
        this.instance = instance;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        String playerRaw = sender.getName().toString();
        player = Bukkit.getServer().getPlayer(playerRaw);
        if (sender instanceof Player && this.instance.config().get("Players." + playerRaw + ".type").toString().equals("Vampire")) {
            if (cmd.getName().equalsIgnoreCase("bat")) {
                if (this.instance.config().getBoolean("Players." + playerRaw + ".Bat") == false && args.length == 0) {
                    this.instance.config().set("Players." + playerRaw + ".Bat", true);
                    Location loc = this.player.getLocation();
                    final Bat bat = (Bat)loc.getWorld().spawnEntity(loc, EntityType.BAT);
                    for(Player pl : Bukkit.getOnlinePlayers()) {
                    	pl.hidePlayer(player);
                    }
                    player.setAllowFlight(true);
                    player.setMaxHealth(2.0);
                    player.sendMessage(ChatColor.BLACK + "you became a bat");
                    int stop = Bukkit.getScheduler().scheduleSyncRepeatingTask(this.instance, new BatTimer(player, bat),0L,1L);
                    map.put(player, stop);
                } else {
                	untrans(map.get(player), player);
                }
            }
        } else {
            sender.sendMessage(ChatColor.RED + "You need to be a vampire to become a bat");
        }
        if (args.length == 1 && args[0].equals("stop")) {
        	untrans(map.get(player), player);
        }
        return false;
    }
    public void untrans(int i, Player player){
    	String playerRaw = player.getName().toString();
    	for(Player pl : Bukkit.getOnlinePlayers()) {
        	pl.showPlayer(player);
        }
        this.instance.config().set("Players." + playerRaw + ".Bat", false);
        this.player.setAllowFlight(false);
        this.player.setMaxHealth(20.0);
        this.player.sendMessage(ChatColor.RED + "You are no longer a bat");
        this.player.removePotionEffect(PotionEffectType.INVISIBILITY);
        this.player.removePotionEffect(PotionEffectType.WEAKNESS);
        Bukkit.getScheduler().cancelTask(i);
    }
    public int map(){
		return map.get(player);
    	
    }
}
