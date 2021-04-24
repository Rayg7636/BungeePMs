package fr.keke142.bungeepm.bukkit;

import org.bukkit.plugin.java.JavaPlugin;

public class BungeePMBukkitPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        getCommand("msg").setExecutor(new EmptyCommand());
        getCommand("tell").setExecutor(new EmptyCommand());
    }
}
