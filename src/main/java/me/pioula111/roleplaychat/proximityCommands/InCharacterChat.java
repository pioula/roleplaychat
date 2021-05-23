package me.pioula111.roleplaychat.proximityCommands;

import me.pioula111.roleplaychat.Roleplaychat;
import me.pioula111.roleplaychat.chatColors.ChatFormating;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.scheduler.BukkitScheduler;


public class InCharacterChat extends ProximityCommands implements Listener {
    public InCharacterChat(Roleplaychat plugin) {
        super(plugin);
    }

    @EventHandler
    public void onSendChat(AsyncPlayerChatEvent event) {
        String ic = ChatColor.DARK_GRAY + "[IC]" +  ChatColor.RESET;
        String player = ChatColor.GRAY + event.getPlayer().getDisplayName() + ChatColor.DARK_GRAY + ": " + ChatColor.RESET;
        String message = ChatFormating.formatMessage(event.getMessage());
        String[] wholeMessage = {ic + player + message};

        sendMessages(event.getPlayer(), new StringBuilder(), wholeMessage, config.getDouble("chat.distance"));
        event.setCancelled(true);
    }
}
