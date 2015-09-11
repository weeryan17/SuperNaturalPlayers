package com.weeryan17.snp.Util;

import java.util.ArrayList;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wither;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class WitherStuff implements Runnable {
	Player player;
	static Wither wither;
	public WitherStuff(Player player, Wither wither) {
		this.player = player;
		WitherStuff.wither = wither;
	}

	@Override
	public void run() {
		wither.teleport(player);
		for(Player p : getNearbyPlayers(player, 5)){
			p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 30, 100));
		}
		
	}
	public ArrayList<Player> getNearbyPlayers(Player pl, double range){
        ArrayList<Player> nearby = new ArrayList<Player>();
        for (Entity e : pl.getNearbyEntities(range, range, range)){
            if (e instanceof Player){
                nearby.add((Player) e);
            }
        }
        return nearby;
    }
	public static Wither Wither(){
		
		return wither;
	}
}
