package me.pioula111.roleplaychat;

import me.pioula111.roleplaychat.NameTagVisibility.CommandShowHideNicks;
import me.pioula111.roleplaychat.NameTagVisibility.NickVisibility;
import me.pioula111.roleplaychat.friends.CommandPoznaj;
import me.pioula111.roleplaychat.ids.*;
import me.pioula111.roleplaychat.jsonManager.JsonConfig;
import me.pioula111.roleplaychat.mask.MaskManager;
import me.pioula111.roleplaychat.proximityCommands.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Objects;

public final class Roleplaychat extends JavaPlugin {
    private JsonConfig jsonConfig;
    FileConfiguration config = getConfig();

    @Override
    public void onEnable() {
        // Plugin startup logic
        jsonConfig = new JsonConfig(new File("./plugins/Roleplaychat/config.json"));

        config.addDefault("chat.distance", 5.0);
        config.addDefault("whisper.distance", 2.5);
        config.addDefault("shout.distance", 10.0);
        config.options().copyDefaults();
        saveConfig();

        NickVisibility nickVisibility = new NickVisibility();

        getServer().getPluginManager().registerEvents(new InCharacterChat(this, getJsonConfig().getAllPlayersData()), this);
        getServer().getPluginManager().registerEvents(nickVisibility, this);

        IdManager idManager = new IdManager();
        getServer().getPluginManager().registerEvents(idManager, this);
        getServer().getPluginManager().registerEvents(new CustomNameTag(idManager, this), this);
        getServer().getPluginManager().registerEvents(new NameTagManager(this, idManager), this);
        getServer().getPluginManager().registerEvents(new MaskManager(idManager, this), this);

        Objects.requireNonNull(this.getCommand("me")).setExecutor(new CommandMe(this, getJsonConfig().getAllPlayersData()));
        Objects.requireNonNull(this.getCommand("do")).setExecutor(new CommandDo(this, getJsonConfig().getAllPlayersData()));
        Objects.requireNonNull(this.getCommand("ooc")).setExecutor(new CommandOOC(this, getJsonConfig().getAllPlayersData()));
        Objects.requireNonNull(this.getCommand("sz")).setExecutor(new CommandSz(this, getJsonConfig().getAllPlayersData()));
        Objects.requireNonNull(this.getCommand("k")).setExecutor(new CommandK(this, getJsonConfig().getAllPlayersData()));

        Objects.requireNonNull(this.getCommand("showhidenicks")).setExecutor(new CommandShowHideNicks(nickVisibility));
        Objects.requireNonNull(this.getCommand("checkid")).setExecutor(new CommandCheckId(idManager));
        Objects.requireNonNull(this.getCommand("checkplayerid")).setExecutor(new CommandCheckPlayerId(idManager));
        Objects.requireNonNull(this.getCommand("poznaj")).setExecutor(new CommandPoznaj(idManager, this));
    }

    @Override
    public void onDisable() {
       // jsonConfig.wypisz();
        jsonConfig.save();
        //when server is shutting down name tags don't clear so you need to clear them
        NameTagManager.clearNameTags();
        // Plugin shutdown logic
    }

    public JsonConfig getJsonConfig() {
        return jsonConfig;
    }
}