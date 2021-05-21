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
    private static final Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
    private static final Team admins = board.registerNewTeam("Admins");
    private static final Team players = board.registerNewTeam("Players");

    private void initTeams() {
        admins.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.FOR_OTHER_TEAMS);
        players.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.FOR_OWN_TEAM);
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        initTeams();

        config.addDefault("chat.distance", 5.0);
        config.addDefault("whisper.distance", 2.5);
        config.addDefault("shout.distance", 10.0);
        config.options().copyDefaults();
        saveConfig();

        getServer().getPluginManager().registerEvents(new InCharacterChat(config), this);
        getServer().getPluginManager().registerEvents(new NickVisibility(board, admins, players), this);

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
