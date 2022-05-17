package com.goit.javacore5.feature.human;

import com.goit.javacore5.feature.storage.Storage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

public class HumanService {
    private Storage storage;

    public HumanService(Storage storage) {
        this.storage = storage;
    }

    public void printHumanInfo(long id) {
        try (Statement st = storage.getConnection().createStatement()) {
            String sql = "SELECT name, birthday FROM human WHERE id = " + id;

            try(ResultSet rs = st.executeQuery(sql)) {
                if (rs.next()) {
                    String name = rs.getString("name");
                    String birthday = rs.getString("birthday");

                    System.out.println("name: " + name + ", birthday: " + birthday);
                } else {
                    System.out.println("Human with id " + id + " not found!");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void printHumanIds() {
        try(Statement st = storage.getConnection().createStatement()) {
            try(ResultSet rs = st.executeQuery("SELECT id FROM human")) {
                while(rs.next()) {
                    long id = rs.getLong("id");
                    System.out.println("ID: " + id);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void createNewHuman(String name, LocalDate birthday) {
        String insertSql = String.format(
                "INSERT INTO human (name, birthday) VALUES ('%s', '%s')",
                name,
                birthday
        );

        storage.executeUpdate(insertSql);
    }
}
