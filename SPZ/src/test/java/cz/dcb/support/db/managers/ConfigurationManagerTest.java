/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.dcb.support.db.managers;

import cz.dcb.support.db.jpa.controllers.ConfigurationJpaController;
import cz.dcb.support.db.jpa.controllers.ConfigurationManager;
import cz.dcb.support.db.jpa.entities.Configuration;
import cz.dcb.support.db.managers.exceptions.IllegalOrphanException;
import cz.dcb.support.db.managers.exceptions.NonexistentEntityException;
import cz.dcb.support.db.managers.exceptions.PreexistingEntityException;
import cz.dcb.support.db.utils.DBUtils;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author bar
 */
public class ConfigurationManagerTest {
    
    private final ConfigurationManager manager = new ConfigurationJpaController(DBUtils.getEntityManagerFactory());
    
    public ConfigurationManagerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of create method, of class ConfigurationManager.
     */
    @Test
    public void testCreate() throws Exception {
        System.out.println("create");
        Configuration configuration = null;
//        ConfigurationManager instance = new ConfigurationManagerImpl();
        manager.create(configuration);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of destroy method, of class ConfigurationManager.
     */
    @Test
    public void testDestroy() throws Exception {
        System.out.println("destroy");
        Integer id = null;
 //       ConfigurationManager instance = new ConfigurationManagerImpl();
        manager.destroy(id);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of edit method, of class ConfigurationManager.
     */
    @Test
    public void testEdit() throws Exception {
        System.out.println("edit");
        Configuration configuration = null;
        //ConfigurationManager instance = new ConfigurationManagerImpl();
        manager.edit(configuration);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findConfiguration method, of class ConfigurationManager.
     */
    @Test
    public void testFindConfiguration() {
        System.out.println("findConfiguration");
        Integer id = null;
//        ConfigurationManager instance = new ConfigurationManagerImpl();
        Configuration expResult = null;
        Configuration result = manager.findConfiguration(id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findConfigurationEntities method, of class ConfigurationManager.
     */
    @Test
    public void testFindConfigurationEntities_0args() {
        System.out.println("findConfigurationEntities");
//        ConfigurationManager instance = new ConfigurationManagerImpl();
        List<Configuration> expResult = null;
        List<Configuration> result = manager.findConfigurationEntities();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findConfigurationEntities method, of class ConfigurationManager.
     */
    @Test
    public void testFindConfigurationEntities_int_int() {
        System.out.println("findConfigurationEntities");
        int maxResults = 0;
        int firstResult = 0;
//        ConfigurationManager instance = new ConfigurationManagerImpl();
        List<Configuration> expResult = null;
        List<Configuration> result = manager.findConfigurationEntities(maxResults, firstResult);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getConfigurationCount method, of class ConfigurationManager.
     */
    @Test
    public void testGetConfigurationCount() {
        System.out.println("getConfigurationCount");
//        ConfigurationManager instance = new ConfigurationManagerImpl();
        int expResult = 0;
        int result = manager.getConfigurationCount();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

/*    public class ConfigurationManagerImpl implements ConfigurationManager {

        public void create(Configuration configuration) throws PreexistingEntityException, Exception {
        }

        public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        }

        public void edit(Configuration configuration) throws IllegalOrphanException, NonexistentEntityException, Exception {
        }

        public Configuration findConfiguration(Integer id) {
            return null;
        }

        public List<Configuration> findConfigurationEntities() {
            return null;
        }

        public List<Configuration> findConfigurationEntities(int maxResults, int firstResult) {
            return null;
        }

        public int getConfigurationCount() {
            return 0;
        }
    }
  */  
}
