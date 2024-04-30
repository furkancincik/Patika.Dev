package com.patikadev.Model;

import com.patikadev.Helper.DBConnector;
import com.patikadev.Helper.Helper;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.ArrayList;
import java.util.Stack;

public class User {
    private int id;
    private String name;
    private String username;
    private String password;
    private String type;

    public User() {
    }


    public User(int id, String name, String username, String password, String type) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public static ArrayList<User> getList() {
        ArrayList<User> userList = new ArrayList<>();
        String sqlQuery = "SELECT * FROM users;";
        User obj;
        try {
            Statement st = DBConnector.getInstance().createStatement();
            ResultSet rs = st.executeQuery(sqlQuery);
            while (rs.next()) {
                obj = new User();
                obj.setId(rs.getInt("id"));
                obj.setName(rs.getString("name"));
                obj.setUsername(rs.getString("username"));
                obj.setPassword(rs.getString("password"));
                obj.setType(rs.getString("type"));
                userList.add(obj);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userList;
    }


    public static boolean add(String name, String username, String password, String type) {
        String sqlQuery = "INSERT INTO users (name, username, password, type) VALUES (?,?,?,?)";
        User findUser = User.getFetch(username);
        if (findUser != null) {
            Helper.showMsg("Bu kullanıcı adı kullanılıyor.Başka bir kullanıcı adı giriniz.");
            return false;
        }
        try {
            PreparedStatement pst = DBConnector.getInstance().prepareStatement(sqlQuery);
            pst.setString(1, name);
            pst.setString(2, username);
            pst.setString(3, password);
            pst.setString(4, type);
            int result = pst.executeUpdate();

            if (result == -1) {
                Helper.showMsg("error");
            }
            return result != -1;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return true;
    }


    public static User getFetch(String username) {
        User obj = null;

        String sqlQuery = "SELECT * FROM users WHERE username = ?";

        try {
            PreparedStatement pst = DBConnector.getInstance().prepareStatement(sqlQuery);
            pst.setString(1, username);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                obj = new User();
                obj.setId(rs.getInt("id"));
                obj.setName(rs.getString("name"));
                obj.setUsername(rs.getString("username"));
                obj.setPassword(rs.getString("password"));
                obj.setType(rs.getString("type"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return obj;
    }


    public static boolean delete(int id) {
        String sqlQuery = "DELETE FROM users WHERE id = ?";
        try {
            PreparedStatement pst = DBConnector.getInstance().prepareStatement(sqlQuery);
            pst.setInt(1, id);
            return pst.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }


    public static boolean update(int id, String name, String username, String password, String type) {
        String sqlQuery = "UPDATE users SET name = ?, username=?, password=?,type=? WHERE id=?";
        User findUser = User.getFetch(username);
        if (findUser != null && findUser.getId() != id) {
            Helper.showMsg("Bu kullanıcı adı kullanılıyor.Başka bir kullanıcı adı giriniz.");
            return false;
        }
        try {
            PreparedStatement pst = DBConnector.getInstance().prepareStatement(sqlQuery);
            pst.setString(1, name);
            pst.setString(2, username);
            pst.setString(3, password);
            pst.setString(4, type);
            pst.setInt(5, id);
            return pst.executeUpdate() != -1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;
    }


    public static ArrayList<User> searchUserList(String sqlQuery) {
        ArrayList<User> userList = new ArrayList<>();
        User obj;
        try {
            Statement st = DBConnector.getInstance().createStatement();
            ResultSet rs = st.executeQuery(sqlQuery);
            while (rs.next()) {
                obj = new User();
                obj.setId(rs.getInt("id"));
                obj.setName(rs.getString("name"));
                obj.setUsername(rs.getString("username"));
                obj.setPassword(rs.getString("password"));
                obj.setType(rs.getString("type"));
                userList.add(obj);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return userList;
    }

    public static String searchQuery(String name, String username, String type) {
        String sqlQuery = "SELECT * FROM users WHERE name LIKE ? AND username LIKE ? AND type LIKE = ?";
        try {
            PreparedStatement pst = DBConnector.getInstance().prepareStatement(sqlQuery);
            pst.setString(1,"%" + name + "%");
            pst.setString(2,"%" + username + "%");
            pst.setString(3,"%" + type + "%");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return sqlQuery;
    }


}

