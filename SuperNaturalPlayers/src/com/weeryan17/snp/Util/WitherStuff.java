package com.weeryan17.snp.Util;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.weeryan17.snp.Main;

public class WitherStuff implements Runnable {
	Player player;
	static Skeleton skely;
	int ticks;
	public WitherStuff(Player player, Skeleton skely, Integer ticks) {
		this.player = player;
		WitherStuff.skely = skely;
		this.ticks = ticks;
	}

	public void run() {
		skely.teleport(player);
		int seconds = ticks / 20;
		for(Entity e : Main.getNearbyEntitys(player, 5)){
			EntityType type = e.getType();
			if(e != player && Main.isAlive(type) == true && e != skely){
				((LivingEntity) e).removePotionEffect(PotionEffectType.WITHER);
			((LivingEntity) e).addPotionEffect(new PotionEffect(PotionEffectType.WITHER, seconds + 1, 15));
			}
		}
		
	}
}
