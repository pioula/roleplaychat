package me.pioula111.roleplaychat.friends;

import me.pioula111.roleplaychat.Roleplaychat;
import me.pioula111.roleplaychat.ids.IdManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandPoznaj implements CommandExecutor {
    private IdManager idManager;
    private Roleplaychat plugin;

    public CommandPoznaj(IdManager idManager, Roleplaychat plugin) {
        this.idManager = idManager;
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Nie jesteś graczem!");
            return false;
        }
        if (args.length != 2) {
            sender.sendMessage("Jeden gracz może mieć tylko jeden pseudonim naraz!");
            return false;
        }

        Player friend;
        try {
            friend = idManager.getPlayerById(Integer.parseInt(args[0]));
        }
        catch(NumberFormatException ex) {
            sender.sendMessage("Zły format id!");
            return false;
        }

        if (friend == null) {
            sender.sendMessage("Nie znaleziono gracza o takim id!");
            return false;
        }

        if (friend.getName().equals(sender.getName())) {
            sender.sendMessage("Nie możesz zmienić sobie samemu pseudonimu!");
            return false;
        }

        for (int i = 0; i < args[1].length(); i++) {
            if (!Character.isAlphabetic(args[1].charAt(i))) {
                sender.sendMessage("Pseudonim może się składać tylko z liter alfabetu!");
                return false;
            }
        }

        plugin.getJsonConfig().getAllPlayersData().addPlayersFriend((Player) sender, friend, args[1]);

        return true;
    }
}
