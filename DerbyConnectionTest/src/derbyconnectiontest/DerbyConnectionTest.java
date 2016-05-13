/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package derbyconnectiontest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author bar
 */
public class DerbyConnectionTest {

    /**
     * @param args the command line arguments
     * @throws java.sql.SQLException
     */
    public static void main(String[] args) throws SQLException {
        try(Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/support","suser","suser")){
            PreparedStatement stat = con.prepareStatement("select * from SUSER.USER_");
            ResultSet res = stat.executeQuery();
            while(res.next()){
                System.out.println(String.format("%s:%s", res.getString("name"),res.getString("login")));
            } 
        }
        
    }
    
}
