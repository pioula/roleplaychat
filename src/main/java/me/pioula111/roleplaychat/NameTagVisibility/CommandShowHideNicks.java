package me.pioula111.roleplaychat.NameTagVisibility;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandShowHideNicks implements CommandExecutor {
    NickVisibility nickVisibility;

    public CommandShowHideNicks(NickVisibility nickVisibility) {
        this.nickVisibility = nickVisibility;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 0)
            return false;

        if (!(sender instanceof Player))
            return true;
        Player player = (Player) sender;

        if (nickVisibility.getAdmins().getEntries().contains(player.getName())) {
            nickVisibility.getAdmins().removeEntry(player.getName());
            nickVisibility.getPlayers().addEntry(player.getName());
        }
        else {
            nickVisibility.getPlayers().removeEntry(player.getName());
            nickVisibility.getAdmins().addEntry(player.getName());
        }


        return true;
    }
}
