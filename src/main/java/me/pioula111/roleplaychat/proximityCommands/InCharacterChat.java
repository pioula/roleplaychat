package me.pioula111.roleplaychat.proximityCommands;

import me.pioula111.roleplaychat.Roleplaychat;
import me.pioula111.roleplaychat.chatColors.ChatFormating;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;


public class InCharacterChat extends ProximityCommands implements Listener {
    public InCharacterChat(Roleplaychat plugin) {
        super(plugin);
    }

    @EventHandler
    public void onSendChat(AsyncPlayerChatEvent event) {
        StringBuilder ic = new StringBuilder();
        ic.append(ChatColor.DARK_GRAY).append("[IC]").append(ChatColor.GRAY);
        String message = ChatFormating.formatMessage(event.getMessage());
        String[] wholeMessage = {message};

        sendMessages(event.getPlayer(), ic, wholeMessage, config.getDouble("chat.distance"));
        event.setCancelled(true);
    }
}
