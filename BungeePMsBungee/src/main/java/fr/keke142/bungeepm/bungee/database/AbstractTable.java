package fr.keke142.bungeepm.bungee.database;

import fr.keke142.bungeepm.bungee.managers.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class AbstractTable {
    private final DatabaseManager databaseManager;

    public AbstractTable(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    public abstract String[] getTable();

    public void createTable() {
        String[] tableInformation = getTable();

        String createTableQuery = "CREATE TABLE IF NOT EXISTS `" + tableInformation[0] + "` (" + tableInformation[1] + ");";

        try (Connection connection = getDatabaseManager().getConnection();
             PreparedStatement createTable = connection.prepareStatement(createTableQuery)) {
            createTable.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }
}