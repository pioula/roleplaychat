package me.pioula111.roleplaychat.NameTagVisibility;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scoreboard.*;

//quit/join and name tag
public class NickVisibility implements Listener {
    Scoreboard board;
    Team admins, players;

    public NickVisibility() {
        this.board = Bukkit.getScoreboardManager().getNewScoreboard();
        this.admins = board.registerNewTeam("Admins");
        this.players = board.registerNewTeam("Players");

        admins.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.FOR_OTHER_TEAMS);
        players.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.FOR_OWN_TEAM);
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
