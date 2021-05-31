package me.pioula111.roleplaychat.proximityCommands;

import me.pioula111.roleplaychat.Roleplaychat;
import me.pioula111.roleplaychat.customNameTag.CustomNameTag;
import me.pioula111.roleplaychat.mask.Mask;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public abstract class ProximityCommands {
    protected FileConfiguration config;
    private Roleplaychat plugin;

    public ProximityCommands(Roleplaychat plugin) {
        this.config = plugin.getConfig();
        this.plugin = plugin;
    }

    protected ArrayList<Player> getNearByPlayers(Player player, double distance) {
        int squaredDistance = (int)(distance * distance);
        ArrayList<Player> result = new ArrayList<>();
        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
            if (p.getWorld() == player.getWorld() && p.getLocation().distanceSquared(player.getLocation()) <= squaredDistance) {
                result.add(p);
            }
        }

        return result;
    }

    private String getCorrectName(Player sender, Player reciever) {
        String nick = "";
        try {
            if (reciever.getScoreboard().getTeam("Admins").hasEntry(reciever.getName()))
                nick = "(" + sender.getName() + ")";
        }
        catch (Exception ignored) {}

        if (Mask.isWearingMask(sender))
            return nick + "Zamaskowany";
        else
           return nick + sender.getDisplayName();
    }

    protected void sendMessages(Player sender, StringBuilder prefix, String[] words, double distance) {
        StringBuilder sufix = new StringBuilder();

        if (sender.getDisplayName().equals("Bezimienny")) {
            CustomNameTag.komunikatZmienImie(sender);
        }

        for (int i = 0; i < words.length; i++) {
            sufix.append(words[i]);
            if (i + 1 < words.length)
                sufix.append(" ");
        }

        ArrayList<Player> nearByPlayers = getNearByPlayers(sender, distance);

        for (Player p : nearByPlayers) {
            p.sendMessage(prefix.toString() + " " + getCorrectName(sender, p) + ": " + sufix);
        }
    }
}
