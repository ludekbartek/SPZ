/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.dcb.support.db.managers;

import cz.dcb.support.db.jpa.controllers.ConfigurationJpaController;
import cz.dcb.support.db.jpa.controllers.ConfigurationManager;
import cz.dcb.support.db.jpa.controllers.exceptions.NonexistentEntityException;
import cz.dcb.support.db.jpa.entities.Configuration;
import cz.dcb.support.db.managers.utils.DBUtils;
import java.util.ArrayList;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    private final Logger logger = Logger.getLogger(ConfigurationManagerTest.class.getName());
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
        cleanDB();
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
        try{
            manager.create(configuration);
            fail("Exception should be thrown.");
        }catch(IllegalArgumentException iae){
            logger.log(Level.INFO, "Correct exception thrown.",iae);
        }catch(Exception ex){
            fail("Wrong exception is thrown: "+ex);
        }
        configuration = DBUtils.createConfiguration();
        try{
            manager.create(configuration);
        }catch(Exception ex){
            fail("Wrong exception thrown "+ex);
        }
        if(configuration.getId()==null){
            fail("ID not set.");
        }
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of destroy method, of class ConfigurationManager.
     */
    @Test
    public void testDestroy() throws Exception {
        /*System.out.println("destroy");
        Integer id = null;
 //       ConfigurationManager instance = new ConfigurationManagerImpl();
        //manager.destroy(id);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
        */
    }

    /**
     * Test of edit method, of class ConfigurationManager.
     */
    @Test
    public void testEdit() throws Exception {
        System.out.println("edit");
        Configuration configuration = null;
        //ConfigurationManager instance = new ConfigurationManagerImpl();
        try{
            manager.edit(configuration);
            fail("Exception should be thrown.");
        }catch(IllegalArgumentException iae){
            logger.log(Level.INFO, "Correct exception thrown.", iae);
        }catch(Exception ex){
            fail("Incorrect exception thrown: "+ex);
        }
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
        Random rand = new Random();
        int count = rand.nextInt(5);
        List<Configuration> configs=createConfigurations(count+5);
        int i=0;
        for(Configuration conf:configs){
            conf.setDescription(conf.getDescription()+i);
            i++;
            manager.edit(conf);
        }
        List<Configuration> changed = manager.findConfigurationEntities();
        assertListEquals(configs,changed);
        logger.log(Level.INFO, changed.toString());
    }
    @Test()
    public void testNullIdConfigurationEdit(){
        Configuration config = DBUtils.createConfiguration();
        config.setId(null);
        try {
            manager.edit(config);
        }catch(IllegalArgumentException iae){
            logger.log(Level.INFO,"Correct exception thrown.",iae);
            return;
        } catch (Exception ex) {
            Logger.getLogger(ConfigurationManagerTest.class.getName()).log(Level.SEVERE, null, ex);
            fail("Wrong exception thrown.");
        }
    }
    
    /**
     * Test of findConfiguration method, of class ConfigurationManager.
     */
    @Test
    public void testFindNullConfiguration() {
        System.out.println("findConfiguration");
        Integer id = null;
//        ConfigurationManager instance = new ConfigurationManagerImpl();
        Configuration expResult = null;
        Configuration result = null;
        try{
            result = manager.findConfiguration(id);
            fail("Exception should be thrown.");
        }catch(IllegalArgumentException iae){
            logger.log(Level.INFO,"",iae);
        }catch(Exception ex){
            fail("Incorrect exception thrown.");
        }
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    @Test
    public void testFindConfiguration(){
        Random rand = new Random();
        int count = rand.nextInt(5)+5;
        List<Configuration> configs = createConfigurations(count);
        for(Configuration config:configs)
        {
            Configuration conf=manager.findConfiguration(config.getId());
            assertEquals("Nesouhlasi configurace: ",config,conf);
        }
    }
    /**
     * Test of findConfigurationEntities method, of class ConfigurationManager.
     */
    @Test
    public void testFindConfigurationEntities_0args() {
        System.out.println("findConfigurationEntities");
//        ConfigurationManager instance = new ConfigurationManagerImpl();
        List<Configuration> expResult = new ArrayList<>();
        List<Configuration> result = manager.findConfigurationEntities();
        assertEquals("Empty db contains elements.",expResult, result);
        Random rand = new Random();
        int count = rand.nextInt(5)+5;
        expResult = createConfigurations(count);
        result = manager.findConfigurationEntities();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        
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
        List<Configuration> expResult = new ArrayList<>();
        List<Configuration> result = manager.findConfigurationEntities(maxResults, firstResult);
        assertEquals(expResult, result);
        Random rand = new Random();
        int count = rand.nextInt(10)+10;
        // TODO review the generated test code and remove the default call to fail.
        List<Configuration> values = createConfigurations(count);
        for(int first=0;first<count-1;first++){
            for(maxResults=1;maxResults<count-first;maxResults++){
                result = manager.findConfigurationEntities(maxResults, first);
                expResult = values.subList(first,first+maxResults);
                
                assertEquals("Result size doesn't match.",expResult.size(), result.size());
                assertArrayEquals("Result doesn't match the expectation.",expResult.toArray(new Configuration[1]), result.toArray(new Configuration[1]));
            }
        }
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
        Random rand =  new Random();
        int count = rand.nextInt(10)+5;
        List<Configuration> configs = createConfigurations(count);
        result = manager.getConfigurationCount();
        assertEquals("Pocet prvku nesouhlasi.",count, result);
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

    private void cleanDB() {
        for(Configuration config:manager.findConfigurationEntities()){
            try {
                manager.destroy(config.getId());
            } catch (NonexistentEntityException ex) {
                Logger.getLogger(ConfigurationManagerTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private List<Configuration> createConfigurations(int count) {
        List<Configuration> configs = new ArrayList<>();
        for(int i=0;i<count;i++){
            Configuration conf = DBUtils.createConfiguration();
            manager.create(conf);
            configs.add(conf);
        }
        return configs;
    }

    private void assertListEquals(List<Configuration> configs, List<Configuration> changed) {
        assertEquals("Number of elements differs.",configs.size(),changed.size());
        for(Configuration config:configs){
            assertTrue("Configuration "+config+" not found in changed list.",changed.contains(config));
        }
    }
}
