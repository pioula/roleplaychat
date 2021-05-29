package me.pioula111.roleplaychat.jsonManager;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class AllPlayersData {
    private Map<String, Map<String, String>> playersData;

    public AllPlayersData() {
        this.playersData = new HashMap<>();
    }

    public String getFriendsName(Player player, Player friend) {
        if (playersData.containsKey(player.getName()) && playersData.get(player.getName()).containsKey(friend.getName()))
            return playersData.get(player.getName()).get(friend.getName());
        else
            return null;
    }

    public void addPlayersFriend(Player player, Player friend, String customName) {
        if (!playersData.containsKey(player.getName()))
            playersData.put(player.getName(), new HashMap<>());

            playersData.get(player.getName()).put(friend.getName(), customName);
    }
}