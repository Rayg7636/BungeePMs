package fr.keke142.bungeepm.bungee.commands;

import com.google.common.collect.ImmutableSet;
import fr.keke142.bungeepm.bungee.BungeePMBungeePlugin;
import fr.keke142.bungeepm.bungee.managers.LocaleManager;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

import java.util.HashSet;
import java.util.Set;


public class MessageCommand extends Command implements TabExecutor {
    private final BungeePMBungeePlugin plugin;

    public MessageCommand(BungeePMBungeePlugin plugin) {
        super("message", "bungeepm.message", "msg", "m", "tell");
        this.setPermissionMessage(LocaleManager.msg("commands.noPermission"));
        this.plugin = plugin;
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        if (args.length != 1) {
            return ImmutableSet.of();
        }

        Set<String> matches = new HashSet<>();
        String search = args[0].toLowerCase();

        for (ProxiedPlayer player : ProxyServer.getInstance().getPlayers()) {
            if (player == sender) continue;

            if (player.getName().toLowerCase().startsWith(search)) {
                matches.add(player.getName());
            }
        }

        return matches;
    }

    public void execute(CommandSender sender, String[] args) {
        if (args.length < 2) {
            sender.sendMessage(TextComponent.fromLegacyText(LocaleManager.msg("commands.invalidSyntax", LocaleManager.msg("commands.message.usage"))));
            return;
        }

        String targetPlayerArg = args[0];

        ProxiedPlayer targetPlayer = ProxyServer.getInstance().getPlayer(targetPlayerArg);

        if (targetPlayer == null) {
            sender.sendMessage(TextComponent.fromLegacyText(LocaleManager.msg("commands.targetNotFound", targetPlayerArg)));
            return;
        }

        if (sender == targetPlayer) {
            sender.sendMessage(TextComponent.fromLegacyText(LocaleManager.msg("commands.cannotYourself")));
            return;
        }

        StringBuilder messageContentArgs = new StringBuilder();

        for (int i = 1; i < args.length; i++) {
            messageContentArgs.append(args[i]).append(" ");
        }

        plugin.getMessageManager().sendMessage(sender, targetPlayer, messageContentArgs.toString());
    }
}
