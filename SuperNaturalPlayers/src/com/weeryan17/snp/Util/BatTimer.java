package com.weeryan17.snp.Util;

import org.bukkit.Location;
import org.bukkit.entity.Bat;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.weeryan17.snp.Main;

public class BatTimer implements Runnable {
	Bat Bat;
	Player p;
	Main instance;
	public BatTimer(Player player, Bat bat, Main instance) {
		this.instance = instance;
		p = player;
		Bat = bat;
	}

	@Override
	public void run() {
		double ticks = this.instance.getConfig().getDouble("General." + "Timings" + ".Entity Discusier Teloporting(ticks)");
		double seconds = ticks / 20;
		int effect = (int) seconds +1;
		Location loc = p.getLocation();
	    Bat.teleport(loc);
	    p.removePotionEffect(PotionEffectType.WEAKNESS);
	    p.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, effect, 9));
	    p.removePotionEffect(PotionEffectType.INVISIBILITY);
	    p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, effect, 9));
	}

}
