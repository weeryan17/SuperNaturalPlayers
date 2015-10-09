package com.weeryan17.snp.Commands;

import java.util.HashMap;
import java.util.Map;

import com.weeryan17.snp.Main;
import com.weeryan17.snp.Util.CustomConfig;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class VampBlCommand implements CommandExecutor {
	Map<Player, Integer> map = new HashMap<Player, Integer>();
    int stop;
    int thing2;
    private Main instance;
    CustomConfig data;
    public VampBlCommand(Main instance) {
        this.instance = instance;
        this.data = new CustomConfig(instance, "data");
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        String playerName = sender.getName().toString();
    	Player p = Bukkit.getPlayer(playerName);
        if (sender instanceof Player) {
            if (cmd.getName().equalsIgnoreCase("bl") && data.getConfig().get("Players." + playerName + ".type").toString().equals("Vampire")) {
                if (args.length == 0) {
                	if(data.getConfig().getBoolean("Players." + playerName + ".BL") == false){
                		data.getConfig().set("Players." + playerName + ".BL", true);
                    blood(playerName, p);
                    sender.sendMessage(ChatColor.RED + "You went into blood lust mode. This will drain your blood");
                } else {
                	data.getConfig().set("Players." + playerName + ".BL", false);
                	stop(p);
                	p.sendMessage(ChatColor.RED + "You exited bloodlust mode");
                }
                }
            } else {
                sender.sendMessage("You are not a Vampire");
            }
        }
        if (args.length == 1 && args[0].equals("stop")) {
            stop(p);
        }
        return true;
    }

    public void blood(final String sender, final Player p) {
        double blood = data.getConfig().getDouble("Players." + sender + ".Blood");
        if (blood >= 0.1) {
            data.getConfig().set("Players." + sender + ".Blood", (blood - 0.8));
            p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 3, 5));
            p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 3, 5));
            stop = Bukkit.getScheduler().scheduleSyncRepeatingTask(this.instance, new Runnable(){

                @Override
                public void run() {
                    VampBlCommand.this.blood2(sender, p);
                }
            }, 0, 10);
            map.put(p, stop);
        } else {
        	map.put(p, stop);
            stop(p);
            p.sendMessage(ChatColor.DARK_RED + "no blood left");
        }
    }

    public void blood2(String sender, Player p) {
        double blood = data.getConfig().getDouble("Players." + sender + ".Blood");
        if (blood >= 0.1) {
            data.getConfig().set("Players." + sender + ".Blood", (blood - 0.2));
            p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 1, 1));
            p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 1, 1));
        } else {
            stop(p);
            p.sendMessage(ChatColor.DARK_RED + "no blood left");
        }
    }

    public void stop(Player p) {
        Bukkit.getScheduler().cancelTask(map.get(p));
    }

}