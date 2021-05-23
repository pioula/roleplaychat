package me.pioula111.roleplaychat;

import me.pioula111.roleplaychat.NameTagVisibility.NickVisibility;
import me.pioula111.roleplaychat.cloudChat.MarkerManager;
import me.pioula111.roleplaychat.lightchatbubbles.ChatBubbles;
import me.pioula111.roleplaychat.lightchatbubbles.ChatBuffer;
import me.pioula111.roleplaychat.proximityCommands.*;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.Objects;

public final class Roleplaychat extends JavaPlugin {
    FileConfiguration config = getConfig();
    private ChatBuffer buffer;
    private ChatBubbles bubbles;

    @Override
    public void onEnable() {
        // Plugin startup logic
        BukkitScheduler sheduler = getServer().getScheduler();

        config.addDefault("chat.distance", 5.0);
        config.addDefault("whisper.distance", 2.5);
        config.addDefault("shout.distance", 10.0);
        config.addDefault("maxBubbleHeight", 3);
        config.addDefault("maxBubbleWidth", 15);
        config.addDefault("bubblesInterval", 5);
        config.addDefault("readSpeed", 800);
        config.addDefault("handicapChars", 10);
        config.addDefault("disableChatWindow", false);
        config.options().copyDefaults();
        saveConfig();

        bubbles = new ChatBubbles(this);
        buffer = new ChatBuffer(this);

        getServer().getPluginManager().registerEvents(new InCharacterChat(this), this);
        getServer().getPluginManager().registerEvents(new NickVisibility(), this);
        getServer().getPluginManager().registerEvents(new MarkerManager(), this);

        Objects.requireNonNull(this.getCommand("me")).setExecutor(new CommandMe(this));
        Objects.requireNonNull(this.getCommand("do")).setExecutor(new CommandDo(this));
        Objects.requireNonNull(this.getCommand("ooc")).setExecutor(new CommandOOC(this));
        Objects.requireNonNull(this.getCommand("sz")).setExecutor(new CommandSz(this));
        Objects.requireNonNull(this.getCommand("k")).setExecutor(new CommandK(this));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public ChatBubbles getBubbles() {
        return bubbles;
    }

    public ChatBuffer getBuffer() {
        return buffer;
    }
}
