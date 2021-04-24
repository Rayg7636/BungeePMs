package fr.keke142.bungeepm.bukkit;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import java.util.Collections;
import java.util.List;

public class EmptyCommand implements TabExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String st, String[] args) {
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        return Collections.emptyList();
    }
}
