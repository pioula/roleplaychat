package me.pioula111.roleplaychat;

import me.pioula111.roleplaychat.NameTagVisibility.NickVisibility;
import me.pioula111.roleplaychat.proximityCommands.*;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.Objects;

public final class Roleplaychat extends JavaPlugin {
    FileConfiguration config = getConfig();

    @Override
    public void onEnable() {
        // Plugin startup logic

        config.addDefault("chat.distance", 5.0);
        config.addDefault("whisper.distance", 2.5);
        config.addDefault("shout.distance", 10.0);
        config.options().copyDefaults();
        saveConfig();

        getServer().getPluginManager().registerEvents(new InCharacterChat(config), this);
        getServer().getPluginManager().registerEvents(new NickVisibility(), this);

        Objects.requireNonNull(this.getCommand("me")).setExecutor(new CommandMe(config));
        Objects.requireNonNull(this.getCommand("do")).setExecutor(new CommandDo(config));
        Objects.requireNonNull(this.getCommand("ooc")).setExecutor(new CommandOOC(config));
        Objects.requireNonNull(this.getCommand("sz")).setExecutor(new CommandSz(config));
        Objects.requireNonNull(this.getCommand("k")).setExecutor(new CommandK(config));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public FileConfiguration getThisConfig() {
        return config;
    }
}
