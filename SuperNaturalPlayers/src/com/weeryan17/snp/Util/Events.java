package com.weeryan17.snp.Util;

import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.event.player.PlayerItemConsumeEvent;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Wither;
import org.bukkit.Material;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.Listener;

import com.weeryan17.snp.Main;
import com.weeryan17.snp.Commands.VampBatCommand;

public class Events implements Listener
{
    private Main instance;
    VampBatCommand vamp = new VampBatCommand(instance);
    
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
        if (damager instanceof Player && this.instance.getConfig().get("Players." + player + ".type").toString().equals("Vampire")) {
            final Double blood = damage / 2.0;
            final Double bloodfinal = blood + this.instance.getConfig().getDouble("Players." + player + ".Blood");
            double bloodTotal = blood + this.instance.getConfig().getDouble("Players." + player + ".BloodTotal");
            this.instance.getConfig().set("Players." + player + ".Blood", bloodfinal);
            this.instance.getConfig().set("Players." + player + ".BloodTotal", bloodTotal);
            this.instance.getConfig().set("Players." + player + ".Vamplvl", bloodTotal/1000);
            this.instance.saveConfig();
        }
        if (damager instanceof Player) {
            if (damagee instanceof Player && this.instance.getConfig().get("Players." + hurt + ".type").toString().equals("Vampire")) {
                final Player p = Bukkit.getServer().getPlayer(player);
                if (p.getItemInHand().getType().equals(Material.WOOD_SWORD)) {
                    event.setDamage(60.0);
                }
            }
            if (damagee instanceof Player && this.instance.getConfig().get("Players." + hurt + ".type").toString().equals("Werewolf")) {
                final Player p = Bukkit.getServer().getPlayer(player);
                if (p.getItemInHand().getType().equals(Material.GOLD_SWORD)) {
                    event.setDamage(60.0);
                }
            }
            if (damagee instanceof Player && this.instance.getConfig().get("Players." + hurt + ".type").toString().equals("Necromancer")) {
            	final Player p = Bukkit.getServer().getPlayer(player);
                if (p.getItemInHand().getType().equals(Material.STONE_SWORD)) {
                    event.setDamage(60.0);
                }
            }
        }
    }
    
	@EventHandler
    public void onEntityDeath(final EntityDeathEvent event) {
        final Entity killer = (Entity)event.getEntity().getKiller();
        Entity killed = event.getEntity();
        if (killer instanceof Player) {
            final String player = killer.getName();
            if (this.instance.getConfig().get("Players." + player + ".type").toString().equals("Necromancer")) {
            	int souls = this.instance.getConfig().getInt("Players." + player + ".Souls");
            	this.instance.getConfig().set("Players." + player + ".Souls", souls + 1);
            }
            if(this.instance.getConfig().get("Players." + player + ".type").toString().equals("Demon")){
            	
            }
        }
        if(killed instanceof Player){
        	final String player = killed.getName();
        	Player p = Bukkit.getPlayer(player);
        	if(this.instance.getConfig().get("Players." + player + ".type").toString().equals("Vampire")){
        		vamp.untrans(vamp.map(), p);
        	}
        }
    }
    
    @EventHandler
    public void onPlayerJoin(final PlayerJoinEvent event) {
        final Player playerRaw = event.getPlayer();
        final String player = playerRaw.getName();
        final String UUID = playerRaw.getUniqueId().toString();
        if (!this.instance.getConfig().contains("Players." + player)) {
            this.instance.getLogger().info("Creating config info");
            this.instance.getConfig().set("Players." + player + ".UUID", UUID);
            this.instance.getConfig().set("Players." + player + ".Wolf", false);
            this.instance.getConfig().set("Players." + player + ".type", "Human");
            this.instance.getConfig().set("Players." + player + ".Blood", 0);
            this.instance.getConfig().set("Players." + player + ".Kills", 0);
            this.instance.getConfig().set("Players." + player + ".Souls", 0);
            this.instance.getConfig().set("Players." + player + ".BloodTotal", 0);
            this.instance.getConfig().set("Players." + player + ".Vamplvl", 0);
            this.instance.getConfig().set("Players." + player + ".Bat", false);
            this.instance.getConfig().set("Players." + player + ".FullMoons", 0);
            this.instance.getConfig().set("Players." + player + ".WC", false);
            this.instance.saveConfig();
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
        if (this.instance.getConfig().get("Players." + name + ".type").toString().equals("Vampire")) {
            event.setCancelled(true);
        if (item.getItemMeta().hasLore()) {
            if (item.getItemMeta().getLore().get(0).equals(ChatColor.YELLOW + "Used to turn blood into food")) {
                    if (this.instance.getConfig().getDouble("Players." + name + ".Blood") >= 1.0) {
                        double blood = this.instance.getConfig().getDouble("Players." + name + ".Blood");
                        blood = blood - 1;
                        this.instance.getConfig().set("Players." + name + ".Blood", blood);
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
    	Wither wither = WitherStuff.Wither();
    	Entity target = event.getTarget();
    	Entity entity = event.getEntity();
    	EntityType type = entity.getType();
    	if(target instanceof Player){
    		String player = target.getName();
    			if(this.instance.getConfig().get("Players." + player + ".type").toString().equals("Necromancer")){
    				if(type == EntityType.ZOMBIE || type == EntityType.SKELETON || type == EntityType.CAVE_SPIDER || entity == wither){
    					event.setCancelled(true);
    				}
    			}
    	}
    }
}