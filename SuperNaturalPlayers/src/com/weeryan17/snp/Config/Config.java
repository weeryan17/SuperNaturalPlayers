package com.weeryan17.snp.Config;

import com.weeryan17.snp.Main;

public class Config {
	static CustomConfig config;
	static CustomConfig data;
	static CustomConfig clans;
	public Config(Main instance){
		config = new CustomConfig(instance, "config");
		data = new CustomConfig(instance, "data");
		clans = new CustomConfig(instance, "clans");
	}
	public CustomConfig data(){
		return data;
	}
	public CustomConfig config(){
		return config;
	}
	public CustomConfig clans(){
		return clans;
	}
}
