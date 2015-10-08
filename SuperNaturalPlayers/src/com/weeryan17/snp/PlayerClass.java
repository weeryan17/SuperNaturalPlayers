package com.weeryan17.snp;

import java.util.ArrayList;

import com.weeryan17.snp.Main;
import com.weeryan17.snp.Util.EntityHider;
import com.weeryan17.snp.Util.Sun;

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

public class PlayerClass {
	ArrayList<Entity> wolves = new ArrayList<Entity>();
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
                double rad = Sun.calcPlayerIrradiation(p);
                if (rad >= 0.25 && p.getGameMode() != GameMode.CREATIVE && p.getGameMode() != GameMode.SPECTATOR) {
                    p.damage(rad);
                    p.sendMessage(ChatColor.RED + "The day light is burning you");
                    p.setFireTicks(100);
                }
                
            }
            	EntityHider hide = new EntityHider((Plugin)this.instance, EntityHider.Policy.BLACKLIST);
            if (this.instance.getConfig().get("Players." + playerName + ".type").toString().equals("Werewolf")){
            if (phase == 0 && world.getTime() >= 13000) {
                p.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
                p.removePotionEffect(PotionEffectType.SPEED);
                p.removePotionEffect(PotionEffectType.INVISIBILITY);
                p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 300, 4));
                p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 300, 4));
                p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 300, 4));
                p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 300, 0));
                if (this.instance.getConfig().getBoolean("Players." + playerName + ".Wolf") == false){
                p.sendMessage(ChatColor.DARK_PURPLE + "The moon is full and bright tonight...");
                int moon = this.instance.getConfig().getInt("Players." + playerName + ".FullMoons");
                this.instance.getConfig().set("Players." + playerName + ".FullMoons", moon+1);
                Location loc = p.getLocation();
                Wolf wolf = (Wolf)loc.getWorld().spawnEntity(loc, EntityType.WOLF);
                Main.noAI(wolf);
                hide.hideEntity(p, wolf);
                wolves.add(wolf);
            	for(Player pl : Bukkit.getOnlinePlayers()) {
                    pl.playSound(loc, Sound.WOLF_HOWL, 1.0f, 0.0f);
                    pl.hidePlayer(p);
            	}
                this.instance.Timer2(p, wolf);
                this.instance.getConfig().set("Players." + playerName + ".Wolf", true);
        			this.instance.saveConfig();
            }
        } else {
        	p.removePotionEffect(PotionEffectType.SPEED);
            p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 300, 0));
            if (this.instance.getConfig().getBoolean("Players." + playerName + ".Wolf") == true){
            	for(Player pl : Bukkit.getOnlinePlayers()) {
            		pl.showPlayer(p);
            	}
            	if(wolves.size() >= 1){
            		for(Entity wolf : wolves){
            			wolf.remove();
            		}
            	}
                this.instance.getConfig().set("Players." + playerName + ".Wolf", false);
        			this.instance.saveConfig();
            Bukkit.getScheduler().cancelTask(Main.stop);
            }
        }
        }
    	}
    }

    public void run2(Player player, Wolf wolf) {
        Location loc = player.getLocation();
        wolf.teleport(loc);
        wolf.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 300, 10));
        wolf.addPotionEffect(new PotionEffect(PotionEffectType.HEAL, 300, 10));
  
    }
}
