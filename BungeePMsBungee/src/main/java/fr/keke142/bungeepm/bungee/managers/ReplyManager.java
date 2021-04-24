package fr.keke142.bungeepm.bungee.managers;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ReplyManager {
    private final Map<UUID, UUID> lastMessenger = new HashMap<>();

    public void setLastMessenger(ProxiedPlayer targetPlayer, ProxiedPlayer messengerPlayer) {
        lastMessenger.put(targetPlayer.getUniqueId(), messengerPlayer.getUniqueId());
    }

    public ProxiedPlayer getLastMessenger(ProxiedPlayer player) {
        if (!lastMessenger.containsKey(player.getUniqueId())) return null;

        UUID lastMessengerUuid = lastMessenger.get(player.getUniqueId());

        return ProxyServer.getInstance().getPlayer(lastMessengerUuid);
    }
}
