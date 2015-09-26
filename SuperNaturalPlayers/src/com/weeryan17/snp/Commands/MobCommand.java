package com.weeryan17.snp.Commands;

import java.util.HashMap;
import java.util.Map;

import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.GenericAttributes;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.entity.CaveSpider;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.HorseInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.weeryan17.snp.Main;

public class MobCommand implements CommandExecutor {
    private Main instance;
    Map<Entity, Integer> map = new HashMap<Entity, Integer>();
    public MobCommand(Main instance) {
        this.instance = instance;
    }
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
    	if(cmd.getName().equalsIgnoreCase("mob")){
    		String player = sender.getName().toString();
    		if(sender instanceof Player){
    			Location loc = ((Player) sender).getLocation();
    			if(this.instance.getConfig().get("Players." + player + ".type").toString().equals("Necromancer")){
    			if(args.length == 0){
    				sender.sendMessage(ChatColor.DARK_BLUE + "Necromancers can summon:");
    				sender.sendMessage(ChatColor.GOLD + "Zombies for 4 souls");
    				sender.sendMessage(ChatColor.GOLD + "Skeletons for 6 souls");
    				sender.sendMessage(ChatColor.GOLD + "Cavespider for 20 souls");
    				sender.sendMessage(ChatColor.GOLD + "ZombieHorse for 100 souls");
    				sender.sendMessage(ChatColor.GOLD + "SkeletonHorse for 100 souls");
    			}
    			if(args.length == 1){
    				if(this.instance.getConfig().getBoolean("Players." + player + ".Truce") == true){
    				if(args[0].equals("Zombie") || args[0].equals("zombie")){
    					if(this.instance.getConfig().getInt("Players." + player + ".Souls") >= 4){
    						int souls = this.instance.getConfig().getInt("Players." + player + ".Souls");
    					Zombie zombie = (Zombie)loc.getWorld().spawnEntity(loc, EntityType.ZOMBIE);
    					zombie.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 3, 0));
    					sender.sendMessage(ChatColor.DARK_BLUE + "You summon a zombie for 4 souls");
    					this.instance.getConfig().set("Players." + player + ".Souls", souls - 4);
    					} else {
    						sender.sendMessage(ChatColor.BLACK + "You don't have enough souls to do this");
    					}
    				} else if(args[0].equals("Skeleton") || args[0].equals("skeleton")){
    					if(this.instance.getConfig().getInt("Players." + player + ".Souls") >= 6){
    						int souls = this.instance.getConfig().getInt("Players." + player + ".Souls");
    						Skeleton skely = (Skeleton)loc.getWorld().spawnEntity(loc, EntityType.SKELETON);
    						skely.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 3, 0));
    						sender.sendMessage(ChatColor.DARK_BLUE + "You summon a skeleton for 6 souls");
    						this.instance.getConfig().set("Players." + player + ".Souls", souls - 6);
    					} else {
    						sender.sendMessage(ChatColor.BLACK + "You don't have enough souls to do this");
    					}
    				} else if(args[0].equals("Cavespider") || args[0].equals("cavespider")){
    					if(this.instance.getConfig().getInt("Players." + player + ".Souls") >= 20){
    						int souls = this.instance.getConfig().getInt("Players." + player + ".Souls");
    						CaveSpider spidy = (CaveSpider)loc.getWorld().spawnEntity(loc, EntityType.CAVE_SPIDER);
    						spidy.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 3, 0));
    						sender.sendMessage(ChatColor.DARK_BLUE + "You summon a cave spider for 20 souls");
    						this.instance.getConfig().set("Players." + player + ".Souls", souls - 20);
    					} else {
        						sender.sendMessage(ChatColor.BLACK + "You don't have enough souls to do this");
        					}
    					} else if(args[0].equals("ZombieHorse") || args[0].equals("zombiehorse")){
    						if(this.instance.getConfig().getInt("Players." + player + ".Souls") >= 100){
    							int souls = this.instance.getConfig().getInt("Players." + player + ".Souls");
    							final Player pl = Bukkit.getPlayer(player);
    							final Horse horse = (Horse)loc.getWorld().spawnEntity(loc, EntityType.HORSE);
    							horse.setAdult();
    							horse.setHealth(10);
    							horse.setTamed(true);
    							horse.setCarryingChest(true);
    							horse.setVariant(Horse.Variant.UNDEAD_HORSE);
    							horse.setJumpStrength(1);
    							horse.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 1000000, 10));
    							horse.setPassenger(pl);
    							int stop = Bukkit.getScheduler().scheduleSyncRepeatingTask(instance, new Runnable(){

									@Override
									public void run() {
										if(horse.getPassenger() != pl){
											horse.remove();
											Bukkit.getScheduler().cancelTask(map.get(horse));
											pl.sendMessage(ChatColor.DARK_GRAY + "You got off you're horse so it died");
										}
										if(horse.isDead()){
											Bukkit.getScheduler().cancelTask(map.get(horse));
											pl.sendMessage(ChatColor.DARK_GRAY + "You're horse died");
										}
									}
    								
    							}, 1, 0);
    							map.put(horse, stop);
    							HorseInventory inv = horse.getInventory();
    							ItemStack item = new ItemStack(Material.SADDLE);
    							inv.addItem(item);
    							((EntityLiving)((CraftEntity)horse).getHandle()).getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.5);
    							sender.sendMessage(ChatColor.DARK_BLUE + "You summon a zombie horse for 100 souls");
    							this.instance.getConfig().set("Players." + player + ".Souls", souls - 100);
    						} else {
        						sender.sendMessage(ChatColor.BLACK + "You don't have enough souls to do this");
        					}
    					} else if(args[0].equals("SkeletonHorse") || args[0].equals("skeletonhorse")){
    						if(this.instance.getConfig().getInt("Players." + player + ".Souls") >= 100){
    							int souls = this.instance.getConfig().getInt("Players." + player + ".Souls");
    							final Player pl = Bukkit.getPlayer(player);
    							final Horse horse = (Horse)loc.getWorld().spawnEntity(loc, EntityType.HORSE);
    							horse.setAdult();
    							horse.setHealth(10);
    							horse.setTamed(true);
    							horse.setCarryingChest(true);
    							horse.setVariant(Horse.Variant.SKELETON_HORSE);
    							horse.setJumpStrength(1);
    							horse.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 1000000, 10));
    							horse.setPassenger(pl);
    							int stop = Bukkit.getScheduler().scheduleSyncRepeatingTask(instance, new Runnable(){

									@Override
									public void run() {
										if(horse.getPassenger() != pl){
											horse.remove();
											Bukkit.getScheduler().cancelTask(map.get(horse));
											pl.sendMessage(ChatColor.DARK_GRAY + "You unsummon your horse");
										} else if(horse.isDead()){
											Bukkit.getScheduler().cancelTask(map.get(horse));
											pl.sendMessage(ChatColor.DARK_GRAY + "You're horse died");
										}
									}
    								
    							}, 1, 0);
    							map.put(horse, stop);
    							HorseInventory inv = horse.getInventory();
    							ItemStack item = new ItemStack(Material.SADDLE);
    							inv.addItem(item);
    							((EntityLiving)((CraftEntity)horse).getHandle()).getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.5);
    							sender.sendMessage(ChatColor.DARK_BLUE + "You summon a skeleton horse for 100 souls");
    							this.instance.getConfig().set("Players." + player + ".Souls", souls - 100);
    						} else {
        						sender.sendMessage(ChatColor.BLACK + "You don't have enough souls to do this");
        					}
    					}
    				}
    			} 
    		}
    		} else {
    			sender.sendMessage(ChatColor.DARK_BLUE + "This is a Necromancer only command.");
    		}
    	}

    	
		return false;
    }
}
