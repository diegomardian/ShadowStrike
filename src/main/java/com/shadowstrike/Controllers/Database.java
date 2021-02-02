package com.shadowstrike.Controllers;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author root
 */
public class Database {
    public String connString;
    public Connection con;
    public Database(String connString) {
        this.connString = connString;
    }
    
    public boolean connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(this.connString);
        } catch (SQLException e) {
            return false;
        }
        this.con = conn;
        return true;
    }
    
    public void close() {
        try {
            this.con.close();
        }
        catch (SQLException e) {
            
        }
    }
    public String insertClient(int id, String name, String os, String username, String ipAddress, String listener, String architecture, String protocal, String shelltype) {
        String sqlInstert = "INSERT INTO Clients(ID, Name, \"Operating System\", Username, \"IP Address\", Listener, Architecture, Protocal, \"Shell Type\") VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement pstmt = this.con.prepareStatement(sqlInstert);
            pstmt.setInt(1, id);
            pstmt.setString(2, name);
            pstmt.setString(3, os);
            pstmt.setString(4, username);
            pstmt.setString(5, ipAddress);
            pstmt.setString(6, listener);
            pstmt.setString(7, architecture);
            pstmt.setString(8, protocal);
            pstmt.setString(9, shelltype);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            return "Error: "+e.getMessage();
        }
        return "Success";
    }
    public String insertListener(int id, String name, String payload, String lhost, String lport, String status) {
        String sqlInstert = "INSERT INTO Clients(ID, Name, Payload, LHOST, LPORT, Status) VALUES(?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement pstmt = this.con.prepareStatement(sqlInstert);
            pstmt.setInt(1, id);
            pstmt.setString(2, name);
            pstmt.setString(3, payload);
            pstmt.setString(4, lhost);
            pstmt.setString(5, lport);
            pstmt.setString(6, status);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            return "Error: "+e.getMessage();
        }
        return "Success";
    }
    public DefaultTableModel selectListeners(String query) {
        DefaultTableModel model = new DefaultTableModel(new String[]{"ID", "Name", "Payload", "LHOST", "LPORT", "Status"}, 0);
        try {
            Statement stmt = this.con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                model.addRow(new Object[]{rs.getInt("ID"), rs.getString("Name"), rs.getString("Payload"), rs.getString("LHOST"), rs.getString("LPORT"), rs.getString("Status")});
            }
            return model; 
        }
        catch (SQLException e){
            return null;
        }
        
    }
    public DefaultTableModel selectClients(String query) {
        DefaultTableModel model = new DefaultTableModel(new String[]{"ID", "Name", "Operating System", "Username", "IP Address", "Listener", "Architecture", "Protocal", "Shell Type"}, 0);
        try {
            Statement stmt = this.con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
            model.addRow(new Object[]{rs.getInt("ID"), rs.getString("Name"), rs.getString("Operating System"), rs.getString("Username"), rs.getString("IP Address"), rs.getString("Listener"), rs.getString("Architecture"), rs.getString("Protocal"), rs.getString("Shell Type")});
            }
            return model;

        }
        catch(SQLException e) {
            return null;
        }
        //s
        
    }
}
