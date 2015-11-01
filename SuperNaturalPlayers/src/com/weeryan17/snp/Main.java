package com.weeryan17.snp;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.weeryan17.snp.Commands.ClanCommand;
import com.weeryan17.snp.Commands.ClassCommand;
import com.weeryan17.snp.Commands.Howl;
import com.weeryan17.snp.Commands.MainCommand;
import com.weeryan17.snp.Commands.MobCommand;
import com.weeryan17.snp.Commands.VampBatCommand;
import com.weeryan17.snp.Commands.VampBlCommand;
import com.weeryan17.snp.Commands.WitherCommand;
import com.weeryan17.snp.Util.Events;
import com.weeryan17.snp.PlayerClass;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;

import net.minecraft.server.v1_8_R3.NBTTagCompound;

import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.InvalidDescriptionException;
import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.UnknownDependencyException;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener, CommandExecutor {
    ProtocolManager protocolManager;
    public static int stop;
    public static Main plugin;
    ArrayList<String> clan = new ArrayList<String>();
    ArrayList<String> element = new ArrayList<String>();
    String type;
    String player;
	@Override
	public void onEnable() {
        plugin = this;
    	File protocolLib = new File("plugins/ProtocolLib.jar");
    	Plugin protocol = getServer().getPluginManager().getPlugin("ProtocolLib");
    	if(protocol == null){
    		plugin.getLogger().info("ProtocolLib not detected instaling it");
				try {
					URL url = new URL("http://dev.bukkit.org/media/files/888/760/ProtocolLib.jar");
					FileUtils.copyURLToFile(url, protocolLib);
					getServer().getPluginManager().loadPlugin(protocolLib);
					plugin.getLogger().info("ProtocolLib instaled please restart the server to get the plugin to work");
					getServer().getPluginManager().disablePlugin(this);
				} catch (IOException e) {
					e.printStackTrace();
				} catch (UnknownDependencyException e) {
					e.printStackTrace();
				} catch (InvalidPluginException e) {
					e.printStackTrace();
				} catch (InvalidDescriptionException e) {
					e.printStackTrace();
				}
    	}
        this.protocolManager = ProtocolLibrary.getProtocolManager();
        this.Timer(plugin);
        ClanCommand exec8 = new ClanCommand(plugin);
        MobCommand exec6 = new MobCommand(plugin);
        WitherCommand exec7 = new WitherCommand(plugin);
        VampBlCommand exec2 = new VampBlCommand(plugin);
        Howl exec4 = new Howl(plugin);
        ClassCommand exec5 = new ClassCommand(plugin);
        MainCommand exec = new MainCommand(plugin);
        VampBatCommand exec3 = new VampBatCommand(plugin);
        Events event = new Events(plugin);
        this.getCommand("class").setExecutor(exec5);
        this.getCommand("wither").setExecutor(exec7);
        this.getCommand("snp").setExecutor(exec);
        this.getCommand("bl").setExecutor(exec2);
        this.getCommand("bat").setExecutor(exec3);
        this.getCommand("howl").setExecutor(exec4);
        this.getCommand("mob").setExecutor(exec6);
        Bukkit.getServer().getPluginManager().registerEvents(event, this);
        if(!this.getConfig().contains("General.")){
        	this.getLogger().info("General info not found creating it");
        	this.getConfig().set("General." + "Clans" + ".Enabled", false);
        	this.getConfig().set("General." + "Timings" + ".Player Cheaker(ticks)", 10);
        	this.getConfig().set("General." + "Timings" + ".Entity Discusier Teloporting(ticks)", 10);
        }
        if(this.getConfig().getBoolean("General." + "Clans" + ".Enabled") == true){
        	this.getCommand("clan").setExecutor(exec8);
        if(!this.getClansConfig().contains("Clans.")){
        	this.getLogger().info("Clan info not found adding clan info and default clans");
        	this.getClansConfig().set("Clans." + "Necromancer" + ".Clans" + ".Noximperius" + ".Open" , true);
        	this.getClansConfig().set("Clans." + "Necromancer" + ".Clans" + ".Noximperius" + ".Owner" , "Server");
        	this.getClansConfig().set("Clans." + "Necromancer" + ".Clans" + ".Witherheart" + ".Open" , true);
        	this.getClansConfig().set("Clans." + "Necromancer" + ".Clans" + ".Witherheart" + ".Owner" , "Server");
        	this.getClansConfig().set("Clans." + "Necromancer" + ".Clans" + ".Deathskull" + ".Open" , true);
        	this.getClansConfig().set("Clans." + "Necromancer" + ".Clans" + ".Deathskull" + ".Owner" , "Server");
        	this.getClansConfig().set("Clans." + "Werewolf" + ".Clans" + ".Darkclaw" + ".Open" , true);
        	this.getClansConfig().set("Clans." + "Werewolf" + ".Clans" + ".Darkclaw" + ".Owner" , "Server");
        	this.getClansConfig().set("Clans." + "Werewolf" + ".Clans" + ".Silverclaw" + ".Open" , true);
        	this.getClansConfig().set("Clans." + "Werewolf" + ".Clans" + ".Silverclaw" + ".Owner" , "Server");
        	this.getClansConfig().set("Clans." + "Werewolf" + ".Clans" + ".Bloodvenom" + ".Open" , true);
        	this.getClansConfig().set("Clans." + "Werewolf" + ".Clans" + ".Bloodvenom" + ".Owner" , "Server");
        	this.getClansConfig().set("Clans." + "Vampire" + ".Clans" + ".Nightwing" + ".Open" , true);
        	this.getClansConfig().set("Clans." + "Vampire" + ".Clans" + ".Nightwing" + ".Owner" , "Server");
        	this.getClansConfig().set("Clans." + "Vampire" + ".Clans" + ".Ashborn" + ".Open" , true);
        	this.getClansConfig().set("Clans." + "Vampire" + ".Clans" + ".Ashborn" + ".Owner" , "Server");
        	this.getClansConfig().set("Clans." + "Vampire" + ".Clans" + ".Darkblood" + ".Open" , true);
        	this.getClansConfig().set("Clans." + "Vampire" + ".Clans" + ".Darkblood" + ".Owner" , "Server");
        }
        }
        this.saveConfig();
        this.saveClansConfig();
        this.getLogger().info("Super Natural Players plugin enabled");
    }

    public void Timer(final Main plugin) {
        final PlayerClass playerClass = new PlayerClass(plugin);
		Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable(){
			
			@Override
            public void run() {
                playerClass.runClass();
            }
        }, 0, getConfig().getInt("General." + "Timings" + ".Player Cheaker(ticks)"));
    }

    public void Timer2(final Player player, final Wolf wolf) {
        final PlayerClass playerClass = new PlayerClass(plugin);
        stop = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable(){

            @Override
            public void run() {
                playerClass.run2(player, wolf);
            }
        }, 0, getConfig().getInt("General." + "Timings" + ".Entity Discusier Teloporting(ticks)"));
    }

    public void onDisable() {
    	for(Player pl : Bukkit.getOnlinePlayers()){
    		String name = pl.getName().toString();
    	this.getDataConfig().set("Players." + name + ".WC", false);
    	this.getDataConfig().set("Players." + name + ".Truce", true);
    	}
    	this.saveDataConfig();
    	this.saveClansConfig();
    	this.saveConfig();
        this.getLogger().info("Super Natural Players plugin disabled");
    }

    public static void error(String message) {
        Main.log(Level.WARNING, message);
    }

    public static void log(Level level, String message) {
    	Main.plugin.getLogger().log(level, message);
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
    public static void noAI(Entity bukkitEntity) {
        net.minecraft.server.v1_8_R3.Entity nmsEntity = ((CraftEntity) bukkitEntity).getHandle();
        NBTTagCompound tag = nmsEntity.getNBTTag();
        if (tag == null) {
            tag = new NBTTagCompound();
        }
        nmsEntity.c(tag);
        tag.setInt("NoAI", 1);
        nmsEntity.f(tag);
    }
	public static ArrayList<Entity> getNearbyEntitys(Entity entity, double range){
        ArrayList<Entity> nearby = new ArrayList<Entity>();
        for (Entity e : entity.getNearbyEntities(range, range, range)){
                nearby.add(e);
        }
        return nearby;
    }
	public static boolean isAlive(EntityType type){
		if(type != EntityType.ARMOR_STAND && type != EntityType.ARROW && type != EntityType.BOAT && type != EntityType.COMPLEX_PART && type != EntityType.DROPPED_ITEM && type != EntityType.EGG && type != EntityType.ENDER_CRYSTAL && type != EntityType.ENDER_PEARL && type != EntityType.ENDER_SIGNAL && type != EntityType.EXPERIENCE_ORB && type != EntityType.FALLING_BLOCK && type != EntityType.FIREBALL && type != EntityType.FIREWORK && type != EntityType.FISHING_HOOK && type != EntityType.ITEM_FRAME && type != EntityType.LEASH_HITCH && type != EntityType.LIGHTNING && type != EntityType.MINECART && type != EntityType.MINECART_CHEST && type != EntityType.MINECART_COMMAND && type != EntityType.MINECART_FURNACE && type != EntityType.MINECART_HOPPER && type != EntityType.MINECART_MOB_SPAWNER && type != EntityType.MINECART_TNT && type != EntityType.PAINTING && type != EntityType.PRIMED_TNT && type != EntityType.SMALL_FIREBALL && type != EntityType.SNOWBALL && type != EntityType.SPLASH_POTION && type != EntityType.THROWN_EXP_BOTTLE && type != EntityType.UNKNOWN && type != EntityType.WEATHER && type != EntityType.WITHER_SKULL){
		return true;
		} else {
			return false;
		}
	}
	public static int randInt(int min, int max) {

	    Random rand = new Random();

	    int randomNum = rand.nextInt((max - min) + 1) + min;

	    return randomNum;
	}
	   public static String removeCharAt(String s, int pos) {
		      return s.substring(0, pos) + s.substring(pos + 1);
		   }
	   private FileConfiguration data;
	   private FileConfiguration config(String name){
		   final File config = new File(getDataFolder(), name + ".yml");
		   if(data == null){
			   data = (FileConfiguration) YamlConfiguration.loadConfiguration(config);
			   final InputStream defConfigStream = getResource(name + ".yml");
			   if (defConfigStream != null) {
	                @SuppressWarnings({ "deprecation"})
	                final YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
	                data.setDefaults((Configuration) defConfig);
			   }
		   }
		   return data;
	   }
	   public FileConfiguration getDataConfig(){
		   return this.config("data");
	   }
	   public FileConfiguration getClansConfig(){
		   return this.config("clans");
	   }
	   private void saveConfigs(String name){
		   final File config = new File(getDataFolder(), name + ".yml");
		   try {
	            this.getConfig().options().copyDefaults(true);
	            this.getClansConfig().save(config);
	            this.config(name);
		   } catch (IOException ex) {
	            getLogger().log(Level.WARNING, "Couldn''t save {0}.yml", name);
	        }
	   }
	   public void saveClansConfig(){
		   this.saveConfigs("clans");
	   }
	   public void saveDataConfig(){
		   this.saveConfigs("data");
	   }
}