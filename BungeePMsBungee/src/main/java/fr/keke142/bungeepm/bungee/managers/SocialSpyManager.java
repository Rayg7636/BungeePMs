package fr.keke142.bungeepm.bungee.managers;

import fr.keke142.bungeepm.bungee.BungeePMBungeePlugin;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.ArrayList;
import java.util.List;

public class SocialSpyManager {
    private final BungeePMBungeePlugin plugin;

    public SocialSpyManager(BungeePMBungeePlugin plugin) {
        this.plugin = plugin;
    }

    public void enableSocialSpy(ProxiedPlayer player) {
        plugin.getDatabaseManager().getSocialSpyTable().enableSocialSpy(player.getUniqueId().toString());
    }

    public void disableSocialSpy(ProxiedPlayer player) {
        plugin.getDatabaseManager().getSocialSpyTable().disableSocialSpy(player.getUniqueId().toString());
    }

    public boolean isSocialSpyEnabled(ProxiedPlayer player) {
        return plugin.getDatabaseManager().getSocialSpyTable().isSocialSpyEnabled(player.getUniqueId().toString());
    }

    public List<ProxiedPlayer> getSocialSpyPlayers() {
        List<String> dbSocialSpyPlayers = plugin.getDatabaseManager().getSocialSpyTable().getSocialSpyPlayers();

        List<ProxiedPlayer> socialSpyPlayers = new ArrayList<>();

        for (ProxiedPlayer p : ProxyServer.getInstance().getPlayers()) {
            for (String dbP : dbSocialSpyPlayers) {
                if (p.getUniqueId().toString().equals(dbP)) socialSpyPlayers.add(p);
            }
        }

        return socialSpyPlayers;
    }

}
