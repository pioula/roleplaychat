package me.pioula111.roleplaychat.proximityCommands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;


public class CommandOOC extends ProximityCommands implements CommandExecutor {
    public CommandOOC(FileConfiguration config) {
        super(config);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player) || args.length == 0) {
            return false;
        }
        Player commandSender = (Player)sender;

        StringBuilder message = new StringBuilder();
        message.append(ChatColor.GOLD).append("[OOC]").append(ChatColor.YELLOW).append(commandSender.getDisplayName());
        message.append(ChatColor.GOLD).append(": ").append(ChatColor.YELLOW);

        sendMessages(commandSender, message, args, config.getDouble("chat.distance"));
        return true;
    }
}
