package com.weeryan17.snp;

import com.weeryan17.snp.EntityHider;
//import com.weeryan17.snp.Events;
import com.weeryan17.snp.Main;
//import com.weeryan17.snp.Sun;
//import java.util.Collection;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
//import org.bukkit.Server;
import org.bukkit.Sound;
import org.bukkit.World;
//import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
//import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;

public class PlayerClass {
    public int stop;
    Player p;
    String playerString;
    String playerName;
    private Main instance;

    public PlayerClass(Main instance) {
        this.instance = instance;
    }

    void run() {
        Object[] players = this.instance.getServer().getOnlinePlayers().toArray();
        for (int i = 0; i < players.length; ++i) {
            String test = PlayerClass.removeCharAt(players[i].toString(), 0);
            String test2 = PlayerClass.removeCharAt(test, 0);
            String test3 = PlayerClass.removeCharAt(test2, 0);
            String test4 = PlayerClass.removeCharAt(test3, 0);
            String test5 = PlayerClass.removeCharAt(test4, 0);
            String test6 = PlayerClass.removeCharAt(test5, 0);
            String test7 = PlayerClass.removeCharAt(test6, 0);
            String test8 = PlayerClass.removeCharAt(test7, 0);
            String test9 = PlayerClass.removeCharAt(test8, 0);
            String test10 = PlayerClass.removeCharAt(test9, 0);
            String test11 = PlayerClass.removeCharAt(test10, 0);
            String test12 = PlayerClass.removeCharAt(test11, 0);
            String test13 = PlayerClass.removeCharAt(test12, 0);
            String test14 = PlayerClass.removeCharAt(test13, 0);
            String test15 = PlayerClass.removeCharAt(test14, 0);
            String test16 = PlayerClass.removeCharAt(test15, 0);
            String test17 = PlayerClass.removeCharAt(test16, 0);
            int length = test17.length() - 1;
            String test18 = PlayerClass.removeCharAt(test17, length);
            this.p = Bukkit.getServer().getPlayer(test18);
            World world = this.p.getWorld();
            int days = (int)world.getFullTime() / 24000;
            int phase = days % 8;
            if (this.instance.getConfig().get("Players." + test18 + ".type").toString().equals("Vampire")) {
                Score score = Events.objective.getScore(ChatColor.RED + "Blood");
                double d = this.instance.getConfig().getDouble("Players." + test18 + ".Blood");
                i = (int)d;
                score.setScore(i);
                double rad = Sun.calcPlayerIrradiation(p);
                if (rad >= 0.25 && this.p.getGameMode() != GameMode.CREATIVE && this.p.getGameMode() != GameMode.SPECTATOR) {
                    this.p.damage(rad);
                    this.p.sendMessage(ChatColor.RED + "The day light is burning you");
                    this.p.setFireTicks(100);
                }
                
            }
            if (this.instance.getConfig().get("Players." + test18 + ".type").toString().equals("Werewolf")){
            if (phase == 0 && world.getTime() >= 13000) {
                this.p.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
                this.p.removePotionEffect(PotionEffectType.SPEED);
                this.p.removePotionEffect(PotionEffectType.INVISIBILITY);
                this.p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 300, 4));
                this.p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 300, 4));
                this.p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 300, 0));
                this.p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 300, 0));
                if (this.instance.getConfig().getBoolean("Players." + test18 + ".Wolf") == false){
                this.p.sendMessage(ChatColor.DARK_PURPLE + "The moon is full and bright tonight...");
                Location loc = this.p.getLocation();
                this.p.playSound(loc, Sound.WOLF_HOWL, 5.0f, 0.0f);
                Wolf wolf = (Wolf)loc.getWorld().spawnEntity(loc, EntityType.WOLF);
                EntityHider hide = new EntityHider((Plugin)this.instance, EntityHider.Policy.BLACKLIST);
                hide.hideEntity(this.p, (Entity)wolf);
                this.instance.Timer2(this.p, wolf);
                this.instance.getConfig().set("Players." + test18 + ".Wolf", true);
            }
        } else {
        	this.p.removePotionEffect(PotionEffectType.SPEED);
            this.p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 300, 0));
            if (!this.instance.getConfig().getBoolean("Players." + test18 + ".Wolf"));
            Bukkit.getScheduler().cancelTask(Main.stop);
            this.instance.getConfig().set("Players." + test18 + ".Wolf", false);
        }
        }
        }
    }

    public static String removeCharAt(String s, int pos) {
        return String.valueOf(s.substring(0, pos)) + s.substring(pos + 1);
    }

    public void run2(Player player, Wolf wolf) {
        Location loc = player.getLocation();
        wolf.teleport(loc);
        wolf.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 300, 10));
        wolf.addPotionEffect(new PotionEffect(PotionEffectType.HEAL, 300, 10));
    }
}
