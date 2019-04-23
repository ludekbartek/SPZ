/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.dcb.support.db.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author bar
 */
public class DBUtils {
    /**
     * Creates new EntityManagerFactory using either configuration $SPZ_CONF_HOME/spz.properties
     * or the default using persistence.xml settings.
     * @return new JPA EntityManagerFactory.
     */
    public static EntityManagerFactory getEntityManagerFactory()
    {
        EntityManagerFactory factory;
        Map<String,String> properties = new HashMap<>();
        try {
            String confDir = System.getProperty("SPZ_CONF_HOME");
            Properties props = new Properties();
            props.load(new FileReader(new File(confDir+"/spz.properties")));
            props.entrySet().forEach((entry) -> {
                properties.put(entry.getKey().toString(),entry.getValue().toString());
            });
            factory = Persistence.createEntityManagerFactory("support_JPA",properties);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DBUtils.class.getName()).log(Level.INFO,"Properties not found. Using default.",ex);
            factory = Persistence.createEntityManagerFactory("support_JPA");
        } catch (IOException ex) {
            Logger.getLogger(DBUtils.class.getName()).log(Level.INFO, "Error reading properties. Using default.", ex);
            factory = Persistence.createEntityManagerFactory("support_JPA");
        }
        return factory;
    }
}
