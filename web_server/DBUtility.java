package com.raspberrypiapp.utility;

import com.raspberrypiapp.action.Rspi;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;

public class DBUtility {

    Connection conn = null;
    Statement stmt = null;

    public boolean verifyPassword(String userId, String password) {
        String query = "select * from Users where email= '" + userId + "'and password = '" + password + "';";

        stmt = getConnection();

        try {
            log(query);
            
            ResultSet rs = stmt.executeQuery(query);
            String dbuserid;
            String dbpass;
            while (rs.next()) {

                dbuserid = rs.getString("email");
                dbpass = rs.getString("password");

                log(dbuserid);
                log(dbpass);
                if (userId.equals(dbuserid) && password.equals(dbpass)) {
                    return true;
                }

            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    Statement getConnection() {
            Statement st=null;
        try {
            String connUrl = "jdbc:mysql://173.194.240.87:3306/mydb";
            String username = "Awais";
            String password = "Awais1234";

            try {
                Class.forName("com.mysql.jdbc.Driver");

            } catch (ClassNotFoundException ex) {
                Logger.getLogger(DBUtility.class.getName()).log(Level.SEVERE, null, ex);
            }

            conn = DriverManager.getConnection(connUrl, username, password);
            st = conn.createStatement();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return st;
    }

    void log(String s) {
        System.out.println(s);
    }

    public ArrayList<Rspi> getRaspberryPi(int userId) {
       
        String query;
        stmt = getConnection();
        ArrayList<Rspi> raspberryPi = new ArrayList();
        Rspi rsp = null;
        try 
        {
            query = "select * from RSPi where IdUserOwner  = '"+userId+"' ;";
            log(query);
            ResultSet rs = stmt.executeQuery(query);
            
            while (rs.next()) 
            {
                //IdRSPi,IdSystem,IdUserOwner,OnlineStatus,pin1,pin2,pin3
                rsp = new Rspi();
                rsp.setIdRSPi(rs.getInt("IdRSPi"));
                rsp.setIdSystem(""+rs.getInt("IdSystem"));       
                rsp.setIdUserOwner(rs.getInt("IdUserOwner"));
                rsp.setOnlineStatus(rs.getInt("OnlineStatus"));
                rsp.setPin1(rs.getInt("pin1"));
                rsp.setPin2(rs.getInt("pin2"));
                rsp.setPin3(rs.getInt("pin3"));
                raspberryPi.add(rsp);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }

        return raspberryPi ;
    }
}
