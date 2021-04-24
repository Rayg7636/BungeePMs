package fr.keke142.bungeepm.bungee.managers;

import fr.keke142.bungeepm.bungee.BungeePMBungeePlugin;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.regex.Pattern;

public class MessageManager {
    private static final String CHATCOLOR_PERMISSION = "bungeepm.chatcolor";
    private static final Pattern STRIP_COLOR_PATTERN = Pattern.compile("(?i)" + '&' + "[0-9A-FK-OR]");
    private final BungeePMBungeePlugin plugin;

    public MessageManager(BungeePMBungeePlugin plugin) {
        this.plugin = plugin;
    }

    public String stripColor(String message) {
        return message == null ? null : STRIP_COLOR_PATTERN.matcher(message).replaceAll("");
    }


    public void sendMessage(CommandSender sender, ProxiedPlayer targetPlayer, String messageContent) {
        messageContent = messageContent.trim();

        String messageWithoutColor = stripColor(messageContent);

        System.out.println(messageWithoutColor);

        if (messageWithoutColor.isEmpty()) {
            sender.sendMessage(TextComponent.fromLegacyText(LocaleManager.msg("commands.emptyMessageContent")));
            return;
        }

        if (sender.hasPermission(CHATCOLOR_PERMISSION)) {
            messageContent = ChatColor.translateAlternateColorCodes('&', messageContent);
        } else {
            messageContent = messageWithoutColor;
        }

        String formattedSenderMessage = ChatColor.translateAlternateColorCodes('&', plugin.getConfigManager().getBungeePMConfig().getMessageSenderFormat()
                .replaceAll("\\{receiver}", targetPlayer.getName())
                .replaceAll("\\{message}", messageContent));

        sender.sendMessage(TextComponent.fromLegacyText(formattedSenderMessage));

        String formattedReceiverMessage = ChatColor.translateAlternateColorCodes('&', plugin.getConfigManager().getBungeePMConfig().getMessageReceiverFormat()
                .replaceAll("\\{sender}", sender.getName())
                .replaceAll("\\{message}", messageContent));

        targetPlayer.sendMessage(TextComponent.fromLegacyText(formattedReceiverMessage));

        String formattedSocialSpyMessage = ChatColor.translateAlternateColorCodes('&', plugin.getConfigManager().getBungeePMConfig().getSocialSpyFormat()
                .replaceAll("\\{sender}", sender.getName())
                .replaceAll("\\{receiver}", targetPlayer.getName())
                .replaceAll("\\{message}", messageContent));

        for (ProxiedPlayer spyP : plugin.getSocialSpyManager().getSocialSpyPlayers()) {
            if (spyP == sender || spyP == targetPlayer) continue;

            spyP.sendMessage(TextComponent.fromLegacyText(formattedSocialSpyMessage));
        }

        ProxyServer.getInstance().getConsole().sendMessage(TextComponent.fromLegacyText(formattedSocialSpyMessage));

        if (sender instanceof ProxiedPlayer)
            plugin.getReplyManager().setLastMessenger(targetPlayer, (ProxiedPlayer) sender);
    }
}
