package com.weeryan17.snp.Commands;

import com.weeryan17.snp.EntityHider;
import com.weeryan17.snp.Main;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
//import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
//import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Bat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class VampBatCommand
implements CommandExecutor {
    Player player;
    int stop;
    private Main instance;

    public VampBatCommand(Main instance) {
        this.instance = instance;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        String playerRaw = sender.getName().toString();
        if (sender instanceof Player && this.instance.getConfig().get("Players." + playerRaw + ".type").toString().equals("Vampire")) {
            if (cmd.getName().equalsIgnoreCase("bat")) {
                if (!(this.instance.getConfig().getBoolean("Player." + playerRaw + ".Bat") || args.length != 0)) {
                    this.instance.getConfig().set("Player." + playerRaw + ".Bat", (Object)true);
                    this.player = Bukkit.getServer().getPlayer(playerRaw);
                    Location loc = this.player.getLocation();
                    final Bat bat = (Bat)loc.getWorld().spawnEntity(loc, EntityType.BAT);
                    EntityHider hide = new EntityHider((Plugin)this.instance, EntityHider.Policy.BLACKLIST);
                    hide.hideEntity(this.player, (Entity)bat);
                    this.player.setAllowFlight(true);
                    this.player.setMaxHealth(2.0);
                    this.player.sendMessage((Object)ChatColor.BLACK + "you became a bat");
                    this.stop = Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin)this.instance, new Runnable(){

                        @Override
                        public void run() {
                            Location loc = VampBatCommand.this.player.getLocation();
                            bat.teleport(loc);
                            VampBatCommand.this.player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 300, 9));
                            VampBatCommand.this.player.removePotionEffect(PotionEffectType.INVISIBILITY);
                            VampBatCommand.this.player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 300, 9));
                        }
                    }, 0, 1);
                } else {
                    this.instance.getConfig().set("Player." + playerRaw + ".Bat", (Object)false);
                    this.player.setAllowFlight(false);
                    this.player.setMaxHealth(20.0);
                    this.player.sendMessage((Object)ChatColor.RED + "You are no longer a bat");
                    this.player.removePotionEffect(PotionEffectType.INVISIBILITY);
                    this.player.removePotionEffect(PotionEffectType.WEAKNESS);
                    Bukkit.getScheduler().cancelTask(this.stop);
                }
            }
        } else {
            sender.sendMessage((Object)ChatColor.RED + "You need to be a vampire to become a bat");
        }
        if (args.length == 1 && args[0].equals("stop")) {
            this.instance.getConfig().set("Player." + playerRaw + ".Bat", (Object)false);
            this.player.setAllowFlight(false);
            this.player.setMaxHealth(20.0);
            this.player.sendMessage((Object)ChatColor.RED + "You are no longer a bat");
            this.player.removePotionEffect(PotionEffectType.INVISIBILITY);
            this.player.removePotionEffect(PotionEffectType.WEAKNESS);
            Bukkit.getScheduler().cancelTask(this.stop);
        }
        return false;
    }

}
