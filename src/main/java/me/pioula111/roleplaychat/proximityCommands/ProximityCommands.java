package me.pioula111.roleplaychat.proximityCommands;

import jdk.javadoc.internal.doclets.formats.html.markup.HtmlAttr;
import me.pioula111.roleplaychat.Roleplaychat;
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

    protected void sendMessages(Player sender, StringBuilder prefix, String[] words, double distance) {
        for (String word : words) {
            prefix.append(word).append(" ");
        }

        ArrayList<Player> nearByPlayers = getNearByPlayers(sender, distance);

        for (Player p : nearByPlayers) {
            p.sendMessage(prefix.toString());
        }

        plugin.getBuffer().receiveChat(sender, prefix.toString());
    }
}
