package me.pioula111.roleplaychat.proximityCommands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;


public class CommandSz extends ProximityCommands implements CommandExecutor {
    public CommandSz(FileConfiguration config) {
        super(config);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player) || args.length == 0) {
            return false;
        }
        Player commandSender = (Player)sender;

        StringBuilder message = new StringBuilder();
        message.append(ChatColor.DARK_BLUE).append("[Szept]").append(ChatColor.BLUE).append(commandSender.getDisplayName());
        message.append(ChatColor.DARK_BLUE).append(": ").append(ChatColor.BLUE);

        sendMessages(commandSender, message, args, config.getDouble("whisper.distance"));
        return true;
    }
}
