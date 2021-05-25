package me.pioula111.roleplaychat.proximityCommands;

import me.pioula111.roleplaychat.Roleplaychat;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandMe extends ProximityCommands implements CommandExecutor {
    public CommandMe(Roleplaychat plugin) {
        super(plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player) || args.length == 0) {
            return false;
        }
        Player commandSender = (Player)sender;

        StringBuilder message = new StringBuilder();
        message.append(ChatColor.DARK_PURPLE).append("[ME]").append(ChatColor.LIGHT_PURPLE).append(commandSender.getDisplayName());
        message.append(ChatColor.DARK_PURPLE).append(": ").append(ChatColor.LIGHT_PURPLE);

        sendMessages(commandSender, message, args, config.getDouble("chat.distance"));
        return true;
    }
}
