package com.weeryan17.snp.Util;

import org.bukkit.Location;
import org.bukkit.entity.Bat;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class BatTimer implements Runnable {
	Bat Bat;
	Player p;
	public BatTimer(Player player, Bat bat) {
		p = player;
		Bat = bat;
	}

	@Override
	public void run() {
		Location loc = p.getLocation();
	    Bat.teleport(loc);
	    p.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 300, 9));
	    p.removePotionEffect(PotionEffectType.INVISIBILITY);
	    p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 300, 9));
	}

}
