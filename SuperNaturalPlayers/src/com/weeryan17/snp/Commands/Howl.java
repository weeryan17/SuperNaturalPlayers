package com.weeryan17.snp.Commands;

import com.weeryan17.snp.Main;
import com.weeryan17.snp.Util.CustomConfig;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Howl implements CommandExecutor {
    CustomConfig data;
	public Howl(Main instance){
        this.data = new CustomConfig(instance, "data");
	}
    public boolean onCommand(CommandSender sender, Command cmd, String lable, String[] args) {
        String player = sender.getName();
        if (cmd.getName().equalsIgnoreCase("howl") && data.getConfig().get("Players." + player + ".type").toString().equals("Werewolf")) {
            Player p = Bukkit.getServer().getPlayer(player);
            Location loc = p.getLocation();
            for(Player pl : Bukkit.getOnlinePlayers()) {
            	  pl.playSound(loc, Sound.WOLF_HOWL, 1.0f, 0.0f);
        }
    }
        return false;
}
}