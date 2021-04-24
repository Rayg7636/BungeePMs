package fr.keke142.bungeepm.bungee.managers;

import fr.keke142.bungeepm.bungee.BungeePMBungeePlugin;
import fr.keke142.bungeepm.bungee.database.SocialSpyTable;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;

public class DatabaseManager {
    private final BungeePMBungeePlugin plugin;
    private final File sqliteDataFolder;

    private SocialSpyTable socialSpyTable;
    private Connection connection;

    public DatabaseManager(BungeePMBungeePlugin plugin) {
        this.plugin = plugin;

        sqliteDataFolder = new File(plugin.getDataFolder(), "data.db");
    }

    public void loadDatabase() {
        if (!sqliteDataFolder.exists()) {
            try {
                sqliteDataFolder.createNewFile();
            } catch (IOException e) {
                plugin.getLogger().log(Level.SEVERE, "Failed to create SQLite database file.", e);
            }
        }

        socialSpyTable = new SocialSpyTable(this);
        socialSpyTable.createTable();
    }


    public Connection getConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            return connection;
        }

        try {
            Class.forName("org.sqlite.JDBC");

            connection = DriverManager.getConnection("jdbc:sqlite:" + sqliteDataFolder);
            return connection;
        } catch (ClassNotFoundException e) {
            plugin.getLogger().log(Level.SEVERE, "Failed to load SQLite Drivers", e);
        }

        return null;
    }

    public SocialSpyTable getSocialSpyTable() {
        return socialSpyTable;
    }
}
