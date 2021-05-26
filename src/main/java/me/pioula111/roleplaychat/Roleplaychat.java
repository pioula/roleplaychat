package me.pioula111.roleplaychat;

import me.pioula111.roleplaychat.NameTagVisibility.CommandShowHideNicks;
import me.pioula111.roleplaychat.NameTagVisibility.NickVisibility;
import me.pioula111.roleplaychat.ids.*;
import me.pioula111.roleplaychat.proximityCommands.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class Roleplaychat extends JavaPlugin {
    FileConfiguration config = getConfig();

    @Override
    public void onEnable() {
        // Plugin startup logic

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

        NickVisibility nickVisibility = new NickVisibility();

        getServer().getPluginManager().registerEvents(new InCharacterChat(this), this);
        getServer().getPluginManager().registerEvents(nickVisibility, this);

        IdManager idManager = new IdManager();
        getServer().getPluginManager().registerEvents(idManager, this);
        getServer().getPluginManager().registerEvents(new CustomNameTag(idManager, this), this);
        getServer().getPluginManager().registerEvents(new NameTagManager(this, idManager), this);

        Objects.requireNonNull(this.getCommand("me")).setExecutor(new CommandMe(this));
        Objects.requireNonNull(this.getCommand("do")).setExecutor(new CommandDo(this));
        Objects.requireNonNull(this.getCommand("ooc")).setExecutor(new CommandOOC(this));
        Objects.requireNonNull(this.getCommand("sz")).setExecutor(new CommandSz(this));
        Objects.requireNonNull(this.getCommand("k")).setExecutor(new CommandK(this));

        Objects.requireNonNull(this.getCommand("showhidenicks")).setExecutor(new CommandShowHideNicks(nickVisibility));
        Objects.requireNonNull(this.getCommand("checkid")).setExecutor(new CommandCheckId(idManager));
        Objects.requireNonNull(this.getCommand("checkplayerid")).setExecutor(new CommandCheckPlayerId(idManager));
    }

    @Override
    public void onDisable() {
        NameTagManager.clearNameTags();
        // Plugin shutdown logic
    }
}