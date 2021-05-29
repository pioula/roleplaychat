package me.pioula111.roleplaychat.jsonManager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;

public class JsonConfig {
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private File file;
    private JsonReader reader;
    private AllPlayersData allPlayersData;

    public JsonConfig(File file) {
        this.file = file;
        allPlayersData = new AllPlayersData();
        try {
            reader = new JsonReader(new FileReader(file.getPath()));
        }
        catch(FileNotFoundException ex) {
            Bukkit.getLogger().info("Json file not found!");
        }
        reload();
    }

    private void reload() {
        try {
            if (!file.getParentFile().exists()) {
                if (!file.getParentFile().mkdirs()) {
                    Bukkit.getLogger().info("Couldn't create new file!");
                }
            }

            if (file.exists())
                allPlayersData = gson.fromJson(reader, allPlayersData.getClass());

            } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    //Debug
    public void wypisz() {
        Bukkit.getLogger().info(gson.toJson(allPlayersData));
    }

    public void save() {
        try {
            PrintWriter pw = new PrintWriter(file, "UTF-8");
            pw.print(gson.toJson(allPlayersData));
            pw.flush();
            pw.close();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    public AllPlayersData getAllPlayersData() {
        return allPlayersData;
    }
}
