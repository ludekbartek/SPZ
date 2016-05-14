/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.dcb.support;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author bar
 */
public class TablesCreator {

    /**
     * @param args the command line arguments
     * @throws java.sql.SQLException
     * @throws java.io.IOException
     * @throws java.lang.ClassNotFoundException
     */
    public static void main(String[] args) throws SQLException, IOException, ClassNotFoundException {
        //Class.forName("org.apache.derby.jdbc.ClientDriver");
        try(Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/support;create=true","suser","suser")){
            BufferedReader reader = new BufferedReader(new InputStreamReader(TablesCreator.class.getResourceAsStream("/cz/dcb/support/support-derby.sql")));
            String line;
            Statement stat = con.createStatement();
            while((line = reader.readLine())!=null){
                System.out.println("Executing: "+line);
                try{
                    stat.execute(line);
                }catch(SQLSyntaxErrorException ex){
                    Logger.getAnonymousLogger().log(Level.SEVERE, "SQL Syntax error {0}", line);
                }
            }
        }
    }
    
}
