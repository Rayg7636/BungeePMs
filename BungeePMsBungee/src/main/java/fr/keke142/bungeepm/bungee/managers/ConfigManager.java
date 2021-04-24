package fr.keke142.bungeepm.bungee.managers;

import fr.keke142.bungeepm.bungee.BungeePMBungeePlugin;
import fr.keke142.bungeepm.bungee.configs.BungeePMConfig;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

public class ConfigManager {
    private final BungeePMConfig bungeePM = new BungeePMConfig();

    public ConfigManager(BungeePMBungeePlugin plugin) {
        File file = new File(plugin.getDataFolder(), "config.yml");
        plugin.createDefaultConfiguration(file, "config.yml");

        try {
            Configuration config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);

            bungeePM.load(config);
        } catch (IOException e) {
            plugin.getLogger().log(Level.SEVERE, "Failed to load configuration file", e);
        }
    }

    public BungeePMConfig getBungeePMConfig() {
        return bungeePM;
    }
}
