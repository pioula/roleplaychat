package me.pioula111.roleplaychat;

import me.pioula111.roleplaychat.NameTagVisibility.CommandShowHideNicks;
import me.pioula111.roleplaychat.NameTagVisibility.NickVisibility;
import me.pioula111.roleplaychat.costomName.CommandImie;
import me.pioula111.roleplaychat.customNameTag.*;
import me.pioula111.roleplaychat.mask.MaskManager;
import me.pioula111.roleplaychat.proximityCommands.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
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

        NickVisibility nickVisibility = new NickVisibility();

        getServer().getPluginManager().registerEvents(new InCharacterChat(this), this);
        getServer().getPluginManager().registerEvents(nickVisibility, this);

        getServer().getPluginManager().registerEvents(new CustomNameTag(this), this);
        getServer().getPluginManager().registerEvents(new NameTagManager(this), this);
        getServer().getPluginManager().registerEvents(new MaskManager(this), this);

        Objects.requireNonNull(this.getCommand("me")).setExecutor(new CommandMe(this));
        Objects.requireNonNull(this.getCommand("do")).setExecutor(new CommandDo(this));
        Objects.requireNonNull(this.getCommand("ooc")).setExecutor(new CommandOOC(this));
        Objects.requireNonNull(this.getCommand("sz")).setExecutor(new CommandSz(this));
        Objects.requireNonNull(this.getCommand("k")).setExecutor(new CommandK(this));

        Objects.requireNonNull(this.getCommand("showhidenicks")).setExecutor(new CommandShowHideNicks(nickVisibility));
        Objects.requireNonNull(this.getCommand("imie")).setExecutor(new CommandImie());
    }

    @Override
    public void onDisable() {
        //when server is shutting down name tags don't clear so you need to clear them
        NameTagManager.clearNameTags();
        // Plugin shutdown logic
    }
}