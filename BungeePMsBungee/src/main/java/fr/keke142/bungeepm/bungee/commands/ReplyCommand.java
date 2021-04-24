package fr.keke142.bungeepm.bungee.commands;

import fr.keke142.bungeepm.bungee.BungeePMBungeePlugin;
import fr.keke142.bungeepm.bungee.managers.LocaleManager;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class ReplyCommand extends Command {
    private final BungeePMBungeePlugin plugin;

    public ReplyCommand(BungeePMBungeePlugin plugin) {
        super("reply", "bungeepm.reply", "r");
        this.setPermissionMessage(LocaleManager.msg("commands.noPermission"));
        this.plugin = plugin;
    }

    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof ProxiedPlayer)) {
            sender.sendMessage(TextComponent.fromLegacyText(LocaleManager.msg("commands.noConsole")));
            return;
        }

        if (args.length < 1) {
            sender.sendMessage(TextComponent.fromLegacyText(LocaleManager.msg("commands.invalidSyntax", LocaleManager.msg("commands.reply.usage"))));
            return;
        }

        ProxiedPlayer p = (ProxiedPlayer) sender;

        ProxiedPlayer lastMessenger = plugin.getReplyManager().getLastMessenger(p);

        if (lastMessenger == null) {
            sender.sendMessage(TextComponent.fromLegacyText(LocaleManager.msg("commands.reply.nobodyToReply")));
            return;
        }

        StringBuilder messageContentArgs = new StringBuilder();

        for (String arg : args) {
            messageContentArgs.append(arg).append(" ");
        }

        plugin.getMessageManager().sendMessage(sender, lastMessenger, messageContentArgs.toString());

    }
}
