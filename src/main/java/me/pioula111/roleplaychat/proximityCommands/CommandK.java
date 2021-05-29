package me.pioula111.roleplaychat.proximityCommands;

import me.pioula111.roleplaychat.Roleplaychat;
import me.pioula111.roleplaychat.jsonManager.AllPlayersData;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandK extends ProximityCommands implements CommandExecutor {
    public CommandK(Roleplaychat plugin, AllPlayersData allPlayersData) {
        super(plugin, allPlayersData);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player) || args.length == 0) {
            return false;
        }
        Player commandSender = (Player)sender;

        StringBuilder message = new StringBuilder();
        message.append(ChatColor.DARK_RED).append("[Krzyk]").append(ChatColor.RED);

        sendMessages(commandSender, message, args, config.getDouble("shout.distance"));
        return true;
    }
}