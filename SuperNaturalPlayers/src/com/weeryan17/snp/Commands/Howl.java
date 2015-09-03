package com.weeryan17.snp.Commands;

import com.weeryan17.snp.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
//import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class Howl
implements CommandExecutor {
    private Main instance;

    public Howl(Main instance) {
        this.instance = instance;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String lable, String[] args) {
        String player = sender.getName();
        if (cmd.getName().equalsIgnoreCase("howl") && this.instance.getConfig().get("Players." + player + ".type").toString().equals("Werewolf")) {
            Player p = Bukkit.getServer().getPlayer(player);
            Location loc = p.getLocation();
            p.playSound(loc, Sound.WOLF_HOWL, 5.0f, 0.0f);
        }
        return false;
    }
}