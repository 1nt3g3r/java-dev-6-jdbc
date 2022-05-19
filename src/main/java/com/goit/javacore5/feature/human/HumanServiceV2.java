package com.goit.javacore5.feature.human;

import com.goit.javacore5.feature.storage.Storage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class HumanServiceV2 {
    private PreparedStatement insertSt;
    private PreparedStatement selectByIdSt;
    private PreparedStatement selectAllSt;

    public HumanServiceV2(Storage storage) throws SQLException {
        Connection conn = storage.getConnection();

        insertSt = conn.prepareStatement(
                "INSERT INTO human (name, birthday) VALUES(?, ?)"
        );
        selectByIdSt = conn.prepareStatement(
                "SELECT name, birthday FROM human WHERE id = ?"
        );
        selectAllSt = conn.prepareStatement("SELECT id FROM human");
    }

    public void createNewHumans(String[] names, LocalDate[] birthdays) throws SQLException {
        for (int i = 0; i < names.length; i++) {
            String name = names[i];
            LocalDate birthday = birthdays[i];

            insertSt.setString(1, name);
            insertSt.setString(2, birthday.toString());

            insertSt.addBatch();
        }

        insertSt.executeBatch();
    }

    public boolean createNewHuman(String name, LocalDate birthday) {
        try {
            insertSt.setString(1, name);
            insertSt.setString(2, birthday.toString());
            return insertSt.executeUpdate() == 1;
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return false;
    }

    public String getHumanInfo(long id) {
        try {
            selectByIdSt.setLong(1, id);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

        try(ResultSet rs = selectByIdSt.executeQuery()) {
            if (!rs.next()) {
                System.out.println("Human with id " + id + " not found!");
                return null;
            }

            String name = rs.getString("name");
            String birthday = rs.getString("birthday");

            return "name: " + name + ", birthday: " + birthday;
        } catch (Exception ex) {
            return null;
        }
    }

    public List<Long> getIds() {
        List<Long> result = new ArrayList<>();

        try (ResultSet rs = selectAllSt.executeQuery()) {
               while(rs.next()) {
                   result.add(rs.getLong("id"));
               }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return result;
    }
}
