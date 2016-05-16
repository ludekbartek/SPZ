/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.dcb.support;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
        try(Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/support_test;create=true","suser","suser")){
            InputStream sqlIn = TablesCreator.class.getResourceAsStream("/create-tables.sql");
            BufferedReader reader = new BufferedReader(new InputStreamReader(sqlIn));
            String line;
            Statement stat = con.createStatement();
            while((line = reader.readLine())!=null){
                System.out.println("Executing: "+line);
                try{
                    stat.execute(line);
                }catch(SQLException ex){
                    Logger.getAnonymousLogger().log(Level.SEVERE, String.format("SQL exception %s on %s.",ex,line));
                }
            }
            int projectId = insertSampleProject(con,"sample project");
            int configId = insertSampleConfiguration(con,"sample configuration");
            int managerId = insertUser(con,"manager","Pepa Manager");
            int developerId = insertUser(con,"analyst","Tonda Vyvojar");
            int clientId = insertUser(con,"user","Franta Uzivatel");
            int adminId = insertUser(con,"admin","Pavel Korinek",Roles.ADMIN);
            setRole(con,configId,managerId,Roles.PROJECT_MANAGER);
            setRole(con,configId,developerId,Roles.ANALYST);
            setRole(con,configId,clientId,Roles.CLIENT);
        }
    }

    private static int insertSampleProject(Connection con, String projectName) throws SQLException {
        PreparedStatement createProject = con.prepareStatement("insert into project(NAME,description) values(?,?)",Statement.RETURN_GENERATED_KEYS);
        createProject.setString(1, projectName);
        createProject.setString(2, "Testovaci projekt");
        createProject.executeUpdate();
        ResultSet keys = createProject.getGeneratedKeys();
        
        keys.next();
        return keys.getInt(1);
    }

    private static int insertSampleConfiguration(Connection con, String configurationName) throws SQLException {
        PreparedStatement createConfig = con.prepareStatement("insert into configuration(code,description) values (?,?)", Statement.RETURN_GENERATED_KEYS);
        createConfig.setString(1, configurationName);
        createConfig.setString(2,"Testovaci konfigurace");
        createConfig.executeUpdate();
        ResultSet keys = createConfig.getGeneratedKeys();
        keys.next();
        return keys.getInt(1);
    }

    private static int insertUser(Connection con, String login, String name, Roles role) throws SQLException {
        PreparedStatement createUser = con.prepareStatement("insert into USER_(LOGIN,NAME,CLASS_TYPE) values(?,?,?)", Statement.RETURN_GENERATED_KEYS);
        createUser.setString(1, login);
        createUser.setString(2,name);
        createUser.setShort(3, (short) role.ordinal());
        createUser.executeUpdate();
        ResultSet keys = createUser.getGeneratedKeys();
        keys.next();
        return keys.getInt(1);
    }

    private static int insertUser(Connection con,String login, String name) throws SQLException {
        return insertUser(con, login, name, Roles.CLIENT);
    }

    private static void setRole(Connection con,int configId, int userId, Roles role) throws SQLException {
        PreparedStatement setRole=con.prepareStatement("insert into USERACCESS(ROLE_,USERID,CONFIGURATIONID) values(?,?,?)");
        setRole.setShort(1, (short)role.ordinal());
        setRole.setInt(2, userId);
        setRole.setInt(3, configId);
        setRole.executeUpdate();
    }
    
}
