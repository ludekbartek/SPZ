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
            PreparedStatement schemas = con.prepareStatement("select * from SYS.SYSSCHEMAS where SCHEMANAME='SUSER'");
            PreparedStatement tables = con.prepareStatement("select * from SYS.SYSTABLES where SCHEMAID=?");
            ResultSet res = stat.executeQuery();
            while(res.next()){
                System.out.println(String.format("%s:%s", res.getString("name"),res.getString("login")));
            } 
            res = schemas.executeQuery();
            while(res.next()){
                String id=res.getString("SCHEMAID");
                String schema = res.getString("SCHEMANAME");
                tables.setString(1, id);
                res = tables.executeQuery();
                while(res.next()){
                    System.out.println(String.format("%s.%s",schema,res.getString("TABLENAME")));
                }
            }
        }
    
    }
}
