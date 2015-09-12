package com.weeryan17.snp.Util;

import java.util.ArrayList;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class WitherStuff implements Runnable {
	Player player;
	static Skeleton skely;
	public WitherStuff(Player player, Skeleton skely) {
		this.player = player;
		WitherStuff.skely = skely;
	}

	@Override
	public void run() {
		skely.teleport(player);
		for(Entity e : getNearbyPlayers(player, 5, skely)){
			if(e != player){
			((LivingEntity) e).addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 30, 15));
			}
		}
		
	}
	public ArrayList<Entity> getNearbyPlayers(Player pl, double range, Skeleton skely){
        ArrayList<Entity> nearby = new ArrayList<Entity>();
        for (Entity e : pl.getNearbyEntities(range, range, range)){
            if (e != skely){
                nearby.add(e);
            }
        }
        return nearby;
    }
}
