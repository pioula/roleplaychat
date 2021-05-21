package me.pioula111.roleplaychat.NameTagVisibility;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scoreboard.*;

//quit/join and name tag
public class NickVisibility implements Listener {
    Scoreboard board;
    Team admins, players;

    public NickVisibility(Scoreboard board, Team admins, Team players) {
        this.board = board;
        this.admins = admins;
        this.players = players;
    }

    //nametag visibility and player join and tab list
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.setScoreboard(board);
        event.setJoinMessage(null);

        if (player.isOp()) {
            admins.addEntry(player.getName());
            player.setPlayerListName(ChatColor.RED + "Admin");
        }
        else {
            players.addEntry(player.getName());
            player.setPlayerListName(ChatColor.GRAY+ "Gracz");
        }
    }

    //player left
    @EventHandler
    public void onPlayerLeft(PlayerQuitEvent event) {
        event.setQuitMessage(null);
    }
}
