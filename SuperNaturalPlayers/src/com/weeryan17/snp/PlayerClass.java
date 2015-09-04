package com.weeryan17.snp;

import com.weeryan17.snp.EntityHider;
import com.weeryan17.snp.Main;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
//import org.bukkit.scoreboard.Score;

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
    	for(Player p : Bukkit.getOnlinePlayers()) {
    		playerName = p.getName();
            World world = p.getWorld();
            int days = (int)world.getFullTime() / 24000;
            int phase = days % 8;
            if (this.instance.getConfig().get("Players." + playerName + ".type").toString().equals("Vampire")) {
                //Score score = Events.objective.getScore(ChatColor.RED + "Blood");
                //double d = this.instance.getConfig().getDouble("Players." + playerName + ".Blood");
                //i = (int)d;
                //score.setScore(i);
                double rad = Sun.calcPlayerIrradiation(p);
                if (rad >= 0.25 && p.getGameMode() != GameMode.CREATIVE && p.getGameMode() != GameMode.SPECTATOR) {
                    p.damage(rad);
                    p.sendMessage(ChatColor.RED + "The day light is burning you");
                    p.setFireTicks(100);
                }
                
            }
        	Entity playerEntity = (Entity)p;
            if (this.instance.getConfig().get("Players." + playerName + ".type").toString().equals("Werewolf")){
            	EntityHider hide = new EntityHider((Plugin)this.instance, EntityHider.Policy.BLACKLIST);
            if (phase == 0 && world.getTime() >= 13000) {
                p.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
                p.removePotionEffect(PotionEffectType.SPEED);
                p.removePotionEffect(PotionEffectType.INVISIBILITY);
                p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 300, 4));
                p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 300, 4));
                p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 300, 0));
                p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 300, 0));
                if (this.instance.getConfig().getBoolean("Players." + playerName + ".Wolf") == false){
                p.sendMessage(ChatColor.DARK_PURPLE + "The moon is full and bright tonight...");
                Location loc = p.getLocation();
                p.playSound(loc, Sound.WOLF_HOWL, 5.0f, 0.0f);
                Wolf wolf = (Wolf)loc.getWorld().spawnEntity(loc, EntityType.WOLF);
                hide.hideEntity(p, wolf);
                for(Player pl : Bukkit.getOnlinePlayers()) {
                	hide.hideEntity(pl, playerEntity);
                }
                this.instance.Timer2(p, wolf);
                this.instance.getConfig().set("Players." + playerName + ".Wolf", true);
            }
        } else {
        	p.removePotionEffect(PotionEffectType.SPEED);
            p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 300, 0));
            if (this.instance.getConfig().getBoolean("Players." + playerName + ".Wolf") == true){
            	for(Player pl : Bukkit.getOnlinePlayers()) {
                	hide.showEntity(pl, playerEntity);
                }
            Bukkit.getScheduler().cancelTask(Main.stop);
            this.instance.getConfig().set("Players." + playerName + ".Wolf", false);
            }
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
