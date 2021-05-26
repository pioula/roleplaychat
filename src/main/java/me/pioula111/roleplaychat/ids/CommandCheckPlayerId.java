package me.pioula111.roleplaychat.ids;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandCheckPlayerId implements CommandExecutor {
    private IdManager idManager;

    public CommandCheckPlayerId(IdManager idManager) {
        this.idManager = idManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 1)
            return false;
        Player player = Bukkit.getPlayer(args[0]);

        if (player == null || !player.isOnline()) {
            sender.sendMessage(ChatColor.RED + "Nie ma takiego gracza!");
            return false;
        }

        sender.sendMessage(ChatColor.GRAY + "id szukanego gracza: " + idManager.getIdByPlayer(player));

        return true;
    }

}
