package com.weeryan17.snp.Commands;

import com.weeryan17.snp.Main;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
//import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class VampBlCommand
implements CommandExecutor {
    int stop;
    int thing2;
    private Main instance;

    public VampBlCommand(Main instance) {
        this.instance = instance;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            String playerName = sender.getName().toString();
            if (cmd.getName().equalsIgnoreCase("bl") && this.instance.getConfig().get("Players." + playerName + ".type").toString().equals("Vampire")) {
                if (args.length == 0) {
                    this.blood(playerName, sender);
                    sender.sendMessage(ChatColor.RED + "You went into blood lust mode. This will drain your blood");
                }
            } else {
                sender.sendMessage("You are not a Vampire");
            }
        }
        if (args.length == 1 && args[0].equals(this.stop)) {
            this.stop();
        }
        return true;
    }

    public void blood(final String sender, final CommandSender player) {
        double blood = this.instance.getConfig().getDouble("Players." + sender + ".Blood");
        if (blood >= 0.1) {
            this.instance.getConfig().set("Players." + sender + ".Blood", (blood - 0.2));
            Player p = Bukkit.getServer().getPlayer(sender);
            p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 3, 1));
            p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 3, 1));
            this.stop = Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin)this.instance, new Runnable(){

                @Override
                public void run() {
                    VampBlCommand.this.blood2(sender, player);
                }
            }, 0, 1);
        } else {
            this.stop();
            player.sendMessage(ChatColor.DARK_RED + "no blood left");
        }
    }

    public void blood2(String sender, CommandSender player) {
        double blood = this.instance.getConfig().getDouble("Players." + sender + ".Blood");
        if (blood >= 0.1) {
            this.instance.getConfig().set("Players." + sender + ".Blood", (blood - 0.2));
            Player p = Bukkit.getServer().getPlayer(sender);
            p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 1, 1));
            p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 1, 1));
        } else {
            this.stop();
            player.sendMessage(ChatColor.DARK_RED + "no blood left");
        }
    }

    public void stop() {
        Bukkit.getScheduler().cancelTask(this.stop);
    }

}