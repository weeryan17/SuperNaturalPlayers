package com.weeryan17.snp;

import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.scoreboard.Scoreboard;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.entity.Entity;
import org.bukkit.Material;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.scoreboard.Objective;
import org.bukkit.event.Listener;

public class Events implements Listener
{
    public static Objective objective;
    private Main instance;
    
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
            final Double bloodTotal = blood + this.instance.getConfig().getDouble("Players." + player + ".BloodTotal");
            this.instance.getConfig().set("Players." + player + ".Blood", bloodfinal);
            this.instance.getConfig().set("Players." + player + ".BloodTotal", bloodTotal);
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
        }
    }
    
    @EventHandler
    public void onEntityDeath(final EntityDeathEvent event) {
        final Entity killer = (Entity)event.getEntity().getKiller();
        final EntityType killed = event.getEntityType();
        if (killer instanceof Player) {
            final String player = killer.getName();
            if (this.instance.getConfig().get("Players." + player + ".type").toString().equals("Demon") || this.instance.getConfig().get("Players." + player + ".type").toString().equals("Necromancer")) {
                killer.sendMessage("you killed " + killed);
            }
        }
    }
    
    @EventHandler
    public void onPlayerJoin(final PlayerJoinEvent event) {
        final Player playerRaw = event.getPlayer();
        final Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
        (Events.objective = board.registerNewObjective("Stats", "dummy")).setDisplaySlot(DisplaySlot.SIDEBAR);
        Events.objective.setDisplayName(ChatColor.GOLD + "Stats");
        playerRaw.setScoreboard(board);
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
            this.instance.getConfig().set("Players." + player + ".Bat", false);
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
                    }
                    else {
                        player.sendMessage(ChatColor.RED + "You need more blood to do this");
                    }
                }
            }
            else {
                player.sendMessage(ChatColor.YELLOW + "Vampires can only get food from a blood potion");
            }
        }
        else {
            player.sendMessage("You are not a vampire and shouldn't have this item");
        }
    }
}