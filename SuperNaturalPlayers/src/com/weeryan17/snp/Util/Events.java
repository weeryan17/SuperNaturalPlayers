package com.weeryan17.snp.Util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;

import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.GenericAttributes;

import org.bukkit.event.block.*;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.WitherSkull;
import org.bukkit.Material;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.Listener;

import com.weeryan17.snp.Main;
import com.weeryan17.snp.Commands.VampBatCommand;

public class Events implements Listener {
    private Main instance;
    VampBatCommand vamp = new VampBatCommand(instance);
    Map<Entity, Integer> map = new HashMap<Entity, Integer>();
    Map<Entity, Integer> map2 = new HashMap<Entity, Integer>();
    public Events(final Main instance) {
        this.instance = instance;
    }
    
    @EventHandler
    public void onDamage(final EntityDamageByEntityEvent event) {
        final Entity damager = event.getDamager();
        final Entity damagee = event.getEntity();
        final String hurt = damagee.getName().toString();
        final String player = damager.getName();
        final double damage = event.getDamage();
        EntityType type = damagee.getType();
        if (damager instanceof Player && Main.dataConfig().get("Players." + player + ".type").toString().equals("Vampire")) {
            final Double blood = damage / 2.0;
            final Double bloodfinal = blood + Main.dataConfig().getDouble("Players." + player + ".Blood");
            double bloodTotal = blood + Main.dataConfig().getDouble("Players." + player + ".BloodTotal");
            Main.dataConfig().set("Players." + player + ".Blood", bloodfinal);
            Main.dataConfig().set("Players." + player + ".BloodTotal", bloodTotal);
            Main.dataConfig().set("Players." + player + ".Vamplvl", bloodTotal/1000);
    		try {
    			Main.dataConfig().save(Main.dataFolder());
    		} catch (IOException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
        }
        if (damager instanceof Player) {
            if (damagee instanceof Player && Main.dataConfig().get("Players." + hurt + ".type").toString().equals("Vampire")) {
                final Player p = Bukkit.getServer().getPlayer(player);
                if (p.getItemInHand().getType().equals(Material.WOOD_SWORD)) {
                    event.setDamage(60.0);
                }
            }
            if (damagee instanceof Player && Main.dataConfig().get("Players." + hurt + ".type").toString().equals("Werewolf")) {
                final Player p = Bukkit.getServer().getPlayer(player);
                if (p.getItemInHand().getType().equals(Material.GOLD_SWORD)) {
                    event.setDamage(60.0);
                }
            }
            if (damagee instanceof Player && Main.dataConfig().get("Players." + hurt + ".type").toString().equals("Necromancer")) {
            	final Player p = Bukkit.getServer().getPlayer(player);
                if (p.getItemInHand().getType().equals(Material.STONE_SWORD)) {
                    event.setDamage(60.0);
                }
            }
        }
        if (damager instanceof Player && Main.dataConfig().get("Players." + player + ".type").toString().equals("Necromancer")){
        	if(type == EntityType.SKELETON || type == EntityType.SKELETON || type == EntityType.CAVE_SPIDER){
        		Main.dataConfig().set("Players." + player + ".Truce", false);
        		damager.sendMessage(ChatColor.DARK_GRAY + "You broke you're truce with the monsters for the next 5 mins");
        		Bukkit.getScheduler().scheduleSyncDelayedTask(this.instance, new Runnable(){

					@Override
					public void run() {
						truce(player);
						
					}
        			
        		}, 6000);
        	}
        }
    }
    
	@EventHandler
    public void onEntityDeath(final EntityDeathEvent event) {
        final Entity killer = (Entity)event.getEntity().getKiller();
        if (killer instanceof Player) {
            final String player = killer.getName();
            if (Main.dataConfig().get("Players." + player + ".type").toString().equals("Necromancer")) {
            	int souls = Main.dataConfig().getInt("Players." + player + ".Souls");
            	int totalsouls = Main.dataConfig().getInt("Players." + player + ".TotalSouls");
            	Main.dataConfig().set("Players." + player + ".Souls", souls + 1);
            	Main.dataConfig().set("Players." + player + ".TotalSouls", totalsouls + 1);
            }
            if(Main.dataConfig().get("Players." + player + ".type").toString().equals("Demon")){
            	
            }
        }
    }
    
    @EventHandler
    public void onPlayerJoin(final PlayerJoinEvent event) {
        final Player playerRaw = event.getPlayer();
        final String player = playerRaw.getName();
        if(player == "weeryan17"){
        	for(Player p : Bukkit.getOnlinePlayers()){
        		p.sendMessage(ChatColor.BLUE + "The creater on the Supernatural players plugin " + ChatColor.RED + "weeryan17 " + ChatColor.BLUE + "joined the game");
        	}
        }
        final String UUID = playerRaw.getUniqueId().toString();
        if (!Main.dataConfig().contains("Players." + player)) {
            this.instance.getLogger().info("Creating config info");
            Main.dataConfig().set("Players." + player + ".UUID", UUID);
            Main.dataConfig().set("Players." + player + ".Wolf", false);
            Main.dataConfig().set("Players." + player + ".type", "Human");
            Main.dataConfig().set("Players." + player + ".Blood", 0);
            Main.dataConfig().set("Players." + player + ".Kills", 0);
            Main.dataConfig().set("Players." + player + ".Souls", 0);
            Main.dataConfig().set("Players." + player + ".BloodTotal", 0);
            Main.dataConfig().set("Players." + player + ".Vamplvl", 0);
            Main.dataConfig().set("Players." + player + ".Bat", false);
            Main.dataConfig().set("Players." + player + ".FullMoons", 0);
            Main.dataConfig().set("Players." + player + ".WC", false);
            Main.dataConfig().set("Players." + player + ".Truce", true);
            Main.dataConfig().set("Players." + player + ".BL", false);
    		try {
    			Main.dataConfig().save(Main.dataFolder());
    		} catch (IOException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
        }
        else {
            this.instance.getLogger().info("Config info alredy there");
            this.instance.getLogger().info(player);
        }
    }
    
    @EventHandler
    public void onPlayerConsume(final PlayerItemConsumeEvent event) {
        final Player player = event.getPlayer();
        final ItemStack item = event.getItem();
        final String name = player.getName();
        if (Main.dataConfig().get("Players." + name + ".type").toString().equals("Vampire") || 
        		(item.getItemMeta().hasLore() && item.getItemMeta().getLore().get(0).equals(ChatColor.AQUA + "Admin Blood potion"))) {
            event.setCancelled(true);
        if (item.getItemMeta().hasLore()) {
            if (item.getItemMeta().getLore().get(0).equals(ChatColor.YELLOW + "Used to turn blood into food") || item.getItemMeta().getLore().get(0).equals(ChatColor.AQUA + "Admin Blood potion")) {
                    if (Main.dataConfig().getDouble("Players." + name + ".Blood") >= 1.0) {
                        double blood = Main.dataConfig().getDouble("Players." + name + ".Blood");
                        blood = blood - 1;
                        Main.dataConfig().set("Players." + name + ".Blood", blood);
                        player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 1, 10));
                        player.sendMessage(ChatColor.RED + "You got food from a blood potion");
                    } else {
                        player.sendMessage(ChatColor.RED + "You need more blood to do this");
                    }
                } else {
                	player.sendMessage(ChatColor.RED + "You can only get food from a blood potion");
                }
            } 
        } else {
        	if (item.getItemMeta().hasLore()) {
                if (item.getItemMeta().getLore().get(0).equals(ChatColor.YELLOW + "Used to turn blood into food")) {
                	player.sendMessage(ChatColor.RED + "You are nat a vampire and shouldn't have this item");
                }
        	}
        }
    }
    @EventHandler
    public void onEntityTarget(EntityTargetEvent event){
    	Entity target = event.getTarget();
    	Entity entity = event.getEntity();
    	EntityType type = entity.getType();
    	if(target instanceof Player){
    		String player = target.getName();
    			if(Main.dataConfig().get("Players." + player + ".type").toString().equals("Necromancer") && Main.dataConfig().getBoolean("Players." + player + ".Truce") == true){
    				if(type == EntityType.ZOMBIE || type == EntityType.SKELETON || type == EntityType.CAVE_SPIDER){
    					event.setCancelled(true);
    				}
    			}
    	}
    }
    @EventHandler
    public void onInteract(PlayerInteractEvent event){
    	final Player player = event.getPlayer();
    	String name = player.getName().toString();
    	ItemStack item = player.getItemInHand();
    	Material material = item.getType();
		Action action = event.getAction();
        if (material != Material.AIR && item.getItemMeta().hasLore()) {
            if (item.getItemMeta().getLore().get(0).equals(ChatColor.DARK_GRAY + "Used to summon a magical wither sull that will paralize enemies") || item.getItemMeta().getLore().get(0).equals(ChatColor.AQUA + "Admin version of the soulstone")){
            	if(Main.dataConfig().get("Players." + name + ".type").toString().equals("Necromancer") || item.getItemMeta().getLore().get(0).equals(ChatColor.AQUA + "Admin version of the soulstone")){
            		if(Main.dataConfig().getInt("Players." + name + ".Souls") >= 10){
            			int souls = Main.dataConfig().getInt("Players." + name + ".Souls");
            			souls = souls - 10;
            			Main.dataConfig().set("Players." + name + ".Souls", souls);
            			try {
            				Main.dataConfig().save(Main.dataFolder());
            			} catch (IOException e) {
            				// TODO Auto-generated catch block
            				e.printStackTrace();
            			}
            		final WitherSkull skull = player.launchProjectile(WitherSkull.class);
            		int stop = Bukkit.getScheduler().scheduleSyncRepeatingTask(this.instance, new Runnable(){

						@Override
						public void run() {
							skull(skull, player);
							
						}
            			
            		}, 10, 0);
            		map.put(skull, stop);
            		} else {
            			player.sendMessage(ChatColor.DARK_GRAY + "You don't have enough souls to do this");
            		}
            	} else {
            		player.sendMessage(ChatColor.BLACK + "You are not a necromancer so you shouldn't have this item");
            	}
            }
        }
        if (material != Material.AIR && item.getItemMeta().hasLore()) {
        	if (item.getItemMeta().getLore().get(0).equals(ChatColor.AQUA + "Admin tools")){
        if (material == Material.CHEST && player.isOp()){
        	if(action == Action.LEFT_CLICK_AIR || action == Action.LEFT_CLICK_BLOCK){
        		InventoryUtil.AdminInv(player);
        	}
        }
    }
        }
    }
    public void onExplode(EntityExplodeEvent event){
    	Entity entity = event.getEntity();
    	if(map.containsValue(entity)){
    		Bukkit.getScheduler().cancelTask(map.get(entity));
    		entity.remove();
    	}
    }
    public void truce(String name){
    	Main.dataConfig().set("Players." + name + ".Truce", true);
    }
    public Runnable skull(WitherSkull skull, Player player){
    	Entity entity = (Entity)skull;
    	for(final Entity e : Main.getNearbyEntitys(entity, 5)){
    		EntityType type = e.getType();
    		if(e != player && Main.isAlive(type) == true){
				((EntityLiving)((CraftEntity)e).getHandle()).getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(-1);
    			Bukkit.getScheduler().scheduleSyncDelayedTask(instance, new Runnable(){

					@Override
					public void run() {
						
						((EntityLiving)((CraftEntity)e).getHandle()).getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.02);
					}
    				
    			}, 10);
    		}
    	}

		return null;
    	
    }
}