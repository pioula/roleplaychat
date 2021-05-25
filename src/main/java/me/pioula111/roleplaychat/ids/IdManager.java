package me.pioula111.roleplaychat.ids;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.UUID;

public class IdManager implements Listener {
    private PriorityQueue<Integer> freeIds;
    private int maxNumberOfPlayers;
    private Player[] players;
    private Map<UUID, Integer> ids = new HashMap<>();

    public IdManager() {
        freeIds = new PriorityQueue<>();
        maxNumberOfPlayers = Bukkit.getServer().getMaxPlayers();
        for (int i = 1; i <= maxNumberOfPlayers; i++) {
            freeIds.add(i);
        }
        players = new Player[maxNumberOfPlayers + 1];
    }

    public Player getPlayerById(int id) {
        return players[id];
    }

    public int getIdByPlayer(Player player) {
        return ids.get(player.getUniqueId());
    }

    private int getNewId() {
        assert freeIds.peek() != null;
        return freeIds.poll();
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        int newId = getNewId();
        players[newId] = event.getPlayer();
        ids.put(event.getPlayer().getUniqueId(), newId);
        Bukkit.getLogger().info("idManager przydzielil: " + newId);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        int playerId = ids.get(event.getPlayer().getUniqueId());
        ids.remove(event.getPlayer().getUniqueId());
        players[playerId] = null;
        freeIds.add(playerId);
    }
}
