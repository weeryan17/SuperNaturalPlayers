package com.weeryan17.snp.Util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;

import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class CustomConfig {

    private final Plugin pl;
    private final String name;
    private final File file;
    private FileConfiguration fileConfig;

/**
 * @author Giovanni N. | Hurricanes
 * Thank that guy for this class ^
 */
    public CustomConfig(final Plugin pl, final String name) {
        this.pl = pl;
        this.name = name;
        this.file = new File(pl.getDataFolder(), name + (name.contains(".yml") ? "" : ".yml"));
    }

    public FileConfiguration getConfig() {
        if (this.fileConfig == null) {
            this.reloadConfig();
        }
        return this.fileConfig;
    }

    private void reloadConfig() {
        this.fileConfig = (FileConfiguration) YamlConfiguration.loadConfiguration(this.file);
        final InputStream defConfigStream = this.pl.getResource(this.name + ".yml");
        if (defConfigStream != null) {
            @SuppressWarnings({ "deprecation"})
            final YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
            this.fileConfig.setDefaults((Configuration) defConfig);
        }
    }

    public void saveConfig() {
        try {
            this.getConfig().options().copyDefaults(true);
            this.getConfig().save(this.file);
            this.reloadConfig();
        } catch (IOException ex) {
            this.pl.getLogger().log(Level.WARNING, "Couldn''t save {0}.yml", this.name);
        }
    }
}
