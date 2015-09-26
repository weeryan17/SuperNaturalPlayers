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
	public WitherStuff(Player player, Skeleton skely) {
		this.player = player;
		WitherStuff.skely = skely;
	}

	@Override
	public void run() {
		skely.teleport(player);
		for(Entity e : Main.getNearbyEntitys(player, 5)){
			EntityType type = e.getType();
			if(e != player && Main.isAlive(type) == true && e != skely){
			((LivingEntity) e).addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 30, 15));
			}
		}
		
	}
}
