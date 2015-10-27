package com.weeryan17.snp.Api;

import java.util.ArrayList;

import org.bukkit.plugin.Plugin;

import com.weeryan17.snp.Config.CustomConfig;

public class Classes {
    ArrayList<String> clan = new ArrayList<String>();
    ArrayList<String> element = new ArrayList<String>();
	Plugin plugin;
	public Classes(Plugin plugin){
		this.plugin = plugin;
	}
	@SuppressWarnings("unchecked")
	public void makeClass(String name){
		CustomConfig api = new CustomConfig(plugin, "Classes");
		clan = (ArrayList<String>) api.getAPIConfig().get("List." + ".Clans");
		clan.add(name);
	}
	public void AddElemnt(String name){
		
	}
}
