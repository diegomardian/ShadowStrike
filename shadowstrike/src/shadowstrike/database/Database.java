/* 
 * BSD 3-Clause License
 * 
 * Copyright (c) 2021, Diego Mardian
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * 3. Neither the name of the copyright holder nor the names of its
 *    contributors may be used to endorse or promote products derived from
 *    this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package shadowstrike.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.table.DefaultTableModel;

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
    }
    public boolean addScript(String path, String status) {
        String sqlInstert = "INSERT INTO Scripts(Path, Status) VALUES(?, ?)";
        try {
            PreparedStatement pstmt = this.con.prepareStatement(sqlInstert);
            pstmt.setString(1, path);
            pstmt.setString(2, status);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            this.addEvent(" *** Unexpected Database Error: "+e.getMessage());
            return false;
        }
        return true;
    }
    public boolean addEvent(String message) {
        String sqlInstert = "INSERT INTO EventLog(Time, Message) VALUES(?, ?)";
        try {
            PreparedStatement pstmt = this.con.prepareStatement(sqlInstert);
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
            LocalDateTime now = LocalDateTime.now();  
            String time = dtf.format(now);  
            pstmt.setString(1, time);
            pstmt.setString(2, message);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            return false;
        }
        return true;
    }
}