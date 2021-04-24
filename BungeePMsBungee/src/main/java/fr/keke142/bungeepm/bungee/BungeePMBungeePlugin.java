package fr.keke142.bungeepm.bungee;

import fr.keke142.bungeepm.bungee.commands.MessageCommand;
import fr.keke142.bungeepm.bungee.commands.ReplyCommand;
import fr.keke142.bungeepm.bungee.commands.SocialSpyCommand;
import fr.keke142.bungeepm.bungee.managers.*;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;

import java.io.*;
import java.util.Locale;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

public class BungeePMBungeePlugin extends Plugin {
    private ConfigManager configManager;
    private DatabaseManager databaseManager;
    private SocialSpyManager socialSpyManager;
    private ReplyManager replyManager;
    private MessageManager messageManager;

    @Override
    public void onEnable() {
        configManager = new ConfigManager(this);
        databaseManager = new DatabaseManager(this);
        databaseManager.loadDatabase();
        socialSpyManager = new SocialSpyManager(this);
        replyManager = new ReplyManager();
        messageManager = new MessageManager(this);

        LocaleManager.loadLocale(this, Locale.forLanguageTag(configManager.getBungeePMConfig().getLanguageTag()));

        PluginManager pluginManager = ProxyServer.getInstance().getPluginManager();

        pluginManager.registerCommand(this, new MessageCommand(this));
        pluginManager.registerCommand(this, new ReplyCommand(this));
        pluginManager.registerCommand(this, new SocialSpyCommand(this));

    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }

    public SocialSpyManager getSocialSpyManager() {
        return socialSpyManager;
    }

    public ReplyManager getReplyManager() {
        return replyManager;
    }

    public MessageManager getMessageManager() {
        return messageManager;
    }

    /**
     * Create a default configuration file from the .jar.
     *
     * @param actual      The destination file
     * @param defaultName The name of the file inside the jar's defaults folder
     */
    public void createDefaultConfiguration(File actual, String defaultName) {

        // Make parent directories
        File parent = actual.getParentFile();
        if (!parent.exists()) {
            parent.mkdirs();
        }

        if (actual.exists()) {
            return;
        }

        JarFile file = null;
        InputStream input = null;
        try {
            file = new JarFile(getFile());
            ZipEntry copy = file.getEntry(defaultName);
            if (copy == null) {
                file.close();
                throw new FileNotFoundException();
            }
            input = file.getInputStream(copy);
        } catch (IOException e) {
            getLogger().severe("Unable to read default configuration: " + defaultName);
        }

        if (input != null) {
            FileOutputStream output = null;

            try {
                output = new FileOutputStream(actual);
                byte[] buf = new byte[8192];
                int length;
                while ((length = input.read(buf)) > 0) {
                    output.write(buf, 0, length);
                }

                getLogger().info("Default configuration file written: " + actual.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    input.close();
                } catch (IOException ignore) {
                }

                try {
                    if (output != null) {
                        output.close();
                    }
                } catch (IOException ignore) {
                }
            }
        }
        if (file != null) {
            try {
                file.close();
            } catch (IOException ignore) {
            }
        }
    }
}
