package fr.keke142.bungeepm.bungee.database;

import fr.keke142.bungeepm.bungee.managers.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SocialSpyTable extends AbstractTable {
    private static final String ENABLE_SOCIALSPY = "INSERT INTO bungeepm_socialspy (playerUuid, enabled) VALUES(?,?)";
    private static final String DISABLE_SOCIALSPY = "DELETE FROM bungeepm_socialspy WHERE playerUuid=?";
    private static final String IS_SOCIALSPY_ENABLED = "SELECT playerUuid FROM bungeepm_socialspy WHERE playerUuid=? AND enabled=true";
    private static final String GET_SOCIALSPY_PLAYERS = "SELECT playerUuid FROM bungeepm_socialspy WHERE enabled=true";

    public SocialSpyTable(DatabaseManager databaseManager) {
        super(databaseManager);
    }

    public void enableSocialSpy(String playerUuid) {
        try (Connection connection = getDatabaseManager().getConnection();
             PreparedStatement enableSocialspy = connection.prepareStatement(ENABLE_SOCIALSPY)) {

            enableSocialspy.setString(1, playerUuid);
            enableSocialspy.setBoolean(2, true);

            enableSocialspy.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void disableSocialSpy(String playerUuid) {
        try (Connection connection = getDatabaseManager().getConnection();
             PreparedStatement disableSocialspy = connection.prepareStatement(DISABLE_SOCIALSPY)) {

            disableSocialspy.setString(1, playerUuid);

            disableSocialspy.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isSocialSpyEnabled(String playerUuid) {
        try (Connection connection = getDatabaseManager().getConnection();
             PreparedStatement isSocialSpyEnabled = connection.prepareStatement(IS_SOCIALSPY_ENABLED)) {

            isSocialSpyEnabled.setString(1, playerUuid);

            ResultSet res = isSocialSpyEnabled.executeQuery();

            boolean found = false;
            if (res.next()) found = true;

            res.close();

            return found;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public List<String> getSocialSpyPlayers() {
        try (Connection connection = getDatabaseManager().getConnection();
             PreparedStatement getSocialSpyPlayers = connection.prepareStatement(GET_SOCIALSPY_PLAYERS)) {

            ResultSet res = getSocialSpyPlayers.executeQuery();

            List<String> socialSpyPlayers = new ArrayList<>();

            while (res.next()) {
                socialSpyPlayers.add(res.getString(1));
            }

            res.close();

            return socialSpyPlayers;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public String[] getTable() {
        return new String[]{"bungeepm_socialspy", "`playerUuid` VARCHAR(36) NOT NULL, " +
                "`enabled` BOOLEAN NOT NULL," +
                "PRIMARY KEY (`playerUuid`)"};
    }
}
