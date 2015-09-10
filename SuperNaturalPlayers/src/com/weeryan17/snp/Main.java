package com.weeryan17.snp;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.weeryan17.snp.Commands.ClassCommand;
import com.weeryan17.snp.Commands.Howl;
import com.weeryan17.snp.Commands.MainCommand;
import com.weeryan17.snp.Commands.MobCommand;
import com.weeryan17.snp.Commands.VampBatCommand;
import com.weeryan17.snp.Commands.VampBlCommand;
import com.weeryan17.snp.Util.Events;
import com.weeryan17.snp.PlayerClass;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;

public class Main
extends JavaPlugin
implements Listener,
CommandExecutor {
    ProtocolManager protocolManager;
    public static int stop;
    Scoreboard score;
    public static Main plugin;
    String type;
    String player;

    public void onEnable() {
        this.protocolManager = ProtocolLibrary.getProtocolManager();
        this.Timer();
        this.saveConfig();
        plugin = this;
        MobCommand exec6 = new MobCommand(plugin);
        VampBlCommand exec2 = new VampBlCommand(plugin);
        Howl exec4 = new Howl(plugin);
        ClassCommand exec5 = new ClassCommand(plugin);
        MainCommand exec = new MainCommand(plugin);
        VampBatCommand exec3 = new VampBatCommand(plugin);
        Events event = new Events(plugin);
        this.getCommand("class").setExecutor(exec5);
        this.getCommand("snp").setExecutor(exec);
        this.getCommand("bl").setExecutor(exec2);
        this.getCommand("bat").setExecutor(exec3);
        this.getCommand("howl").setExecutor(exec4);
        this.getCommand("mob").setExecutor(exec6);
        Bukkit.getServer().getPluginManager().registerEvents(event, this);
        this.getLogger().info("Super Natural Players plugin enabled");
    }

    public void Timer() {
        @SuppressWarnings("unused")
		int thing = Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable(){

            @Override
            public void run() {
                PlayerClass playerClass = new PlayerClass(Main.plugin);
                playerClass.run();
            }
        }, 0, 10);
    }

    public void Timer2(final Player player, final Wolf wolf) {
        stop = Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin)this, new Runnable(){

            @Override
            public void run() {
                PlayerClass playerClass = new PlayerClass(Main.plugin);
                playerClass.run2(player, wolf);
            }
        }, 0, 1);
    }

    public void onDisable() {
        this.getLogger().info("Super Natural Players plugin disabled");
        this.saveConfig();
    }

    public static void error(String message) {
        Main.log(Level.WARNING, message);
    }

    public static void log(Level level, String message) {
        plugin.getLogger().log(level, message);
    }

    public static void addLore(ItemStack i, String s) {
        if (i.getItemMeta().getLore() == null) {
            ArrayList<String> lore = new ArrayList<String>();
            lore.add(s);
            ItemMeta imeta = i.getItemMeta();
            imeta.setLore(lore);
            i.setItemMeta(imeta);
        } else {
            List<String> lore = i.getItemMeta().getLore();
            lore.add(s);
            ItemMeta imeta = i.getItemMeta();
            imeta.setLore(lore);
            i.setItemMeta(imeta);
        }
    }
}