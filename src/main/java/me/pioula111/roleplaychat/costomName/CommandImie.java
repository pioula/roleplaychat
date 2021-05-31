package me.pioula111.roleplaychat.costomName;

import me.pioula111.roleplaychat.customNameTag.CustomNameTag;
import me.pioula111.roleplaychat.mask.Mask;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandImie implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player))
            return false;

        if (args.length != 1) {
            sender.sendMessage(ChatColor.RED + "Możesz mieć tylko jedno imię naraz!");
        }

        CustomNameTag.removeNameTag((Player) sender);
        ((Player) sender).setCustomName(args[0]);
        if (!Mask.isWearingMask((Player) sender))
            CustomNameTag.setCustomNametag((Player) sender, args[0]);

        sender.sendMessage(ChatColor.GREEN + "Imie zostało ustawione pomyślnie na: " + args[0]);
        return true;
    }
}
