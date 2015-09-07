package com.weeryan17.snp.Commands;

import com.weeryan17.snp.Main;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Bat;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
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
                if (this.instance.getConfig().getBoolean("Players." + playerRaw + ".Bat") == false && args.length == 0) {
                    this.instance.getConfig().set("Players." + playerRaw + ".Bat", true);
                    player = Bukkit.getServer().getPlayer(playerRaw);
                    Location loc = this.player.getLocation();
                    final Bat bat = (Bat)loc.getWorld().spawnEntity(loc, EntityType.BAT);
                    for(Player pl : Bukkit.getOnlinePlayers()) {
                    	pl.hidePlayer(player);
                    }
                    player.setAllowFlight(true);
                    player.setMaxHealth(2.0);
                    player.sendMessage(ChatColor.BLACK + "you became a bat");
                    stop = Bukkit.getScheduler().scheduleSyncRepeatingTask(this.instance, new Runnable(){
                        @Override
                        public void run() {
                            Location loc = VampBatCommand.this.player.getLocation();
                            bat.teleport(loc);
                            player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 300, 9));
                            player.removePotionEffect(PotionEffectType.INVISIBILITY);
                            player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 300, 9));
                        }
                    }, 0, 1);
                    this.instance.getConfig().set("Players." + player + ".Task", stop);
                } else {
                	for(Player pl : Bukkit.getOnlinePlayers()) {
                    	pl.showPlayer(player);
                    }
                    this.instance.getConfig().set("Players." + playerRaw + ".Bat", false);
                    this.player.setAllowFlight(false);
                    this.player.setMaxHealth(20.0);
                    this.player.sendMessage(ChatColor.RED + "You are no longer a bat");
                    this.player.removePotionEffect(PotionEffectType.INVISIBILITY);
                    this.player.removePotionEffect(PotionEffectType.WEAKNESS);
                    Bukkit.getScheduler().cancelTask(this.instance.getConfig().getInt("Players." + player + ".Task"));
                }
            }
        } else {
            sender.sendMessage(ChatColor.RED + "You need to be a vampire to become a bat");
        }
        if (args.length == 1 && args[0].equals("stop")) {
            this.instance.getConfig().set("Players." + playerRaw + ".Bat", false);
            this.player.setAllowFlight(false);
            this.player.setMaxHealth(20.0);
            this.player.sendMessage(ChatColor.RED + "You are no longer a bat");
            this.player.removePotionEffect(PotionEffectType.INVISIBILITY);
            this.player.removePotionEffect(PotionEffectType.WEAKNESS);
            Bukkit.getScheduler().cancelTask(this.instance.getConfig().getInt("Players." + playerRaw + ".Task"));
        }
        return false;
    }

}
