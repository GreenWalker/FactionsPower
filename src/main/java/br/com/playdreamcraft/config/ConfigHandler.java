package me.floydz69.CraftX1Plus.config;

import java.io.*;
import java.util.logging.Level;

import br.com.playdreamcraft.FactionsPower;
import com.google.common.base.Charsets;
import org.apache.commons.lang.Validate;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

/**
 * Created by gusta on 01/05/2017.
 */
public class ConfigHandler extends YamlConfiguration {

    private File file = null;

    public ConfigHandler(Plugin pl, String name) throws IOException, InvalidConfigurationException {
        file = new File(pl.getDataFolder(), name);
        if (!this.file.exists()) {
            if (pl.getResource(name) != null) {
                pl.saveResource(name, false);
                pl.getServer().getConsoleSender().sendMessage("[" + pl.getName() + "] arquivo " + name + " foi criado com exito no diretorio " + pl.getDataFolder().getAbsolutePath() + " !");
            }
        } else {
            this.file.mkdirs();
            this.file.createNewFile();
        }
        load(this.file);
    }

    public void save() throws IOException {
        save(this.file);
    }

    public void reload() {
        reload();
    }

    @Override
    public void load(File file) throws FileNotFoundException, IOException, InvalidConfigurationException {
        Validate.notNull(file, "file nao pode ser null");
        this.load(new InputStreamReader(new FileInputStream(file), Charsets.UTF_8));
    }

    @Override
    public void load(InputStream stream) throws IOException, InvalidConfigurationException {
        Validate.notNull(stream, "stream nao pode ser null");
        this.load(new InputStreamReader(stream, Charsets.UTF_8));
    }

    public boolean trySave() {
        try {
            save();
            return true;
        } catch (IOException ex) {
            FactionsPower.getMain().getLogger().log(Level.SEVERE,
                    "NAO FOI POSSIVEL SALVAR " + this.file.getName() + " NO DISCO!", ex);
        }
        return false;
    }

    public String getStringReplaced(String path) {
        return this.getString(path).replaceAll("&", "§");
    }

    public String getStringReplaced(String path, String regex, String after) {
        return this.getString(path).replaceAll("&", "§").replaceAll(regex, after);
    }

    public String getStringReplaced(String path, String regex, String after, String regex2, String after2) {
        return this.getString(path).replaceAll("&", "§").replaceAll(regex, after).replaceAll(regex2, after2);
    }

    public String getStringReplaced(String path, String regex, String after, String regex2, String after2,
                                    String regex3, String after3) {
        return this.getString(path).replaceAll("&", "§").replaceAll(regex, after).replaceAll(regex2, after2).replaceAll(regex3,
                after3);
    }
}
