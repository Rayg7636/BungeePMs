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

public class SocialSpyCommand extends Command implements TabExecutor {
    private static final String SOCIALSPY_OTHERS_PERMISSION = "bungeepm.socialspy.others";
    private final BungeePMBungeePlugin plugin;

    public SocialSpyCommand(BungeePMBungeePlugin plugin) {
        super("socialspy", "bungeepm.socialspy", "spy");
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
            if (player.getName().toLowerCase().startsWith(search)) {
                matches.add(player.getName());
            }
        }

        return matches;
    }

    public void execute(CommandSender sender, String[] args) {
        if (args.length >= 1) {
            if (!sender.hasPermission(SOCIALSPY_OTHERS_PERMISSION)) {
                sender.sendMessage(TextComponent.fromLegacyText(LocaleManager.msg("commands.noPermission")));
                return;
            }

            String targetPlayerArg = args[0];

            ProxiedPlayer targetPlayer = ProxyServer.getInstance().getPlayer(targetPlayerArg);

            if (targetPlayer == null) {
                sender.sendMessage(TextComponent.fromLegacyText(LocaleManager.msg("commands.targetNotFound")));
                return;
            }

            if (plugin.getSocialSpyManager().isSocialSpyEnabled(targetPlayer)) {
                plugin.getSocialSpyManager().disableSocialSpy(targetPlayer);
                sender.sendMessage(TextComponent.fromLegacyText(LocaleManager.msg("commands.socialspy.others.disabled", targetPlayer)));
            } else {
                plugin.getSocialSpyManager().enableSocialSpy(targetPlayer);
                sender.sendMessage(TextComponent.fromLegacyText(LocaleManager.msg("commands.socialspy.others.enabled", targetPlayer)));
            }

        } else {
            if (!(sender instanceof ProxiedPlayer)) {
                sender.sendMessage(TextComponent.fromLegacyText(LocaleManager.msg("commands.noConsole")));
                return;
            }

            ProxiedPlayer p = (ProxiedPlayer) sender;

            if (plugin.getSocialSpyManager().isSocialSpyEnabled(p)) {
                plugin.getSocialSpyManager().disableSocialSpy(p);
                sender.sendMessage(TextComponent.fromLegacyText(LocaleManager.msg("commands.socialspy.disabled")));
            } else {
                plugin.getSocialSpyManager().enableSocialSpy(p);
                sender.sendMessage(TextComponent.fromLegacyText(LocaleManager.msg("commands.socialspy.enabled")));
            }
        }
    }
}
