package me.pioula111.roleplaychat.ids;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandCheckId implements CommandExecutor {
    private IdManager idManager;

    public CommandCheckId(IdManager idManager) {
        this.idManager = idManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 1)
            return false;
        int id;
        try {
            id = Integer.parseInt(args[0]);
        }
        catch (NumberFormatException ex) {
            sender.sendMessage(ChatColor.RED + "Zły format id!");
            return false;
        }

        if (id <= 0) {
            sender.sendMessage(ChatColor.RED + "Nie ma takiego id!");
            return false;
        }

        Player playerFromId = idManager.getPlayerById(id);
        if (playerFromId == null) {
            sender.sendMessage(ChatColor.RED + "Nie ma takiego id!");
            return false;
        }

        sender.sendMessage(ChatColor.GRAY + "szukany gracz: " + playerFromId.getName());


        return true;
    }

}
