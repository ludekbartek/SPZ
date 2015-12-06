/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.dcb.support.db.managers;

import cz.dcb.support.db.jpa.controllers.ConfigurationJpaController;
import cz.dcb.support.db.jpa.controllers.ConfigurationManager;
import cz.dcb.support.db.jpa.controllers.ProjectConfigurationJpaController;
import cz.dcb.support.db.jpa.controllers.ProjectConfigurationManager;
import cz.dcb.support.db.jpa.controllers.ProjectJpaController;
import cz.dcb.support.db.jpa.controllers.ProjectManager;
import cz.dcb.support.db.jpa.controllers.exceptions.NonexistentEntityException;
import cz.dcb.support.db.jpa.entities.Configuration;
import cz.dcb.support.db.jpa.entities.Project;
import cz.dcb.support.db.jpa.entities.Projectconfiguration;
import cz.dcb.support.db.managers.utils.DBUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
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
public class ProjectConfigurationManagerTest {
    
    private ProjectConfigurationManager manager = new ProjectConfigurationJpaController(DBUtils.getEntityManagerFactory());
    private final Logger logger = Logger.getLogger(ProjectConfigurationManagerTest.class.getName());
    private final int MAX_VALUES = 100;
    
    public ProjectConfigurationManagerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        clearDB();
    }
    
    @After
    public void tearDown() {
        clearDB();
    }

    /**
     * Test of create method, of class ProjectConfigurationManager.
     */
    @Test
    public void testCreate() {
        System.out.println("create");
        Projectconfiguration projectConfiguration = null;
        try{
            manager.create(projectConfiguration);
            fail("Exception should be thrown.");
        }catch(IllegalArgumentException iae){
            logger.log(Level.INFO,"Exception ", iae);
        }catch(Exception ex){
            fail("Wrong exception thrown.");
        }
        projectConfiguration = DBUtils.createProjectConfiguration(DBUtils.getEntityManagerFactory());
        manager.create(projectConfiguration);
        assertNotNull("ID should be set.",projectConfiguration.getId());
        Projectconfiguration result = manager.findProjectconfiguration(projectConfiguration.getId());
        assertEquals(projectConfiguration, result);
    }

    /**
     * Test of destroy method, of class ProjectConfigurationManager.
     */
    @Test
    public void testDestroy() throws Exception {
        /*System.out.println("destroy");
        Integer id = null;
        ProjectConfigurationManager instance = new ProjectConfigurationManagerImpl();
        instance.destroy(id);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");*/
    }

    /**
     * Test of edit method, of class ProjectConfigurationManager.
     */
    @Test
    public void testEdit() throws Exception {
        System.out.println("edit");
        Projectconfiguration projectconfiguration = null;
        try{
            manager.edit(projectconfiguration);
            fail("Exception should be thrown.");
        }catch(IllegalArgumentException iae){
            logger.log(Level.INFO,"Exception ",iae);
        }catch(Exception ex){
            fail("Invalid exception thrown.");
        }
        Random rand = new Random();
        int count = rand.nextInt(MAX_VALUES);
        List<Projectconfiguration> values = createProjectConfigurations(count);
        int idx = rand.nextInt(count);
        Projectconfiguration testVal= values.get(idx);
        testVal.setConfigurationid(testVal.getConfigurationid()+10);
        manager.edit(testVal);
        List<Projectconfiguration> realVals = manager.findProjectconfigurationEntities();
        assertArrayEquals("Values do not agree.", values.toArray(), realVals.toArray());
        testVal.setProjectid(testVal.getProjectid()+10);
        realVals = manager.findProjectconfigurationEntities();
        assertArrayEquals("Values do not agree.", values.toArray(), realVals.toArray());
        
    }

    /**
     * Test of findProjectconfiguration method, of class ProjectConfigurationManager.
     */
    @Test
    public void testFindProjectconfiguration() {
        System.out.println("findProjectconfiguration");
        Random rand = new Random();
        int count = rand.nextInt(20)+10;
        List<Projectconfiguration> values = createProjectConfigurations(count);
        
        Projectconfiguration expResult = null;
        Projectconfiguration result =  null;
        try{
            result = manager.findProjectconfiguration(null);
            fail("Exception should be thrown.");
        }catch(IllegalArgumentException iae){
            logger.log(Level.INFO,"Exception ",iae);
        }catch(Exception ex){
            fail("Invalid exception thrown "+ex);
        }
        for(Projectconfiguration conf:values){
            Projectconfiguration val = manager.findProjectconfiguration(conf.getId());
            assertEquals(conf, val);
        }
    }

    /**
     * Test of findProjectconfigurationEntities method, of class ProjectConfigurationManager.
     */
    @Test
    public void testFindProjectconfigurationEntities_0args() {
        System.out.println("findProjectconfigurationEntities");
        Random rand = new Random();
        int count = rand.nextInt(MAX_VALUES)+10;
        List<Projectconfiguration> empty = new ArrayList<>(),
                                   result = manager.findProjectconfigurationEntities();
        assertArrayEquals(result.toArray(),empty.toArray());
        List<Projectconfiguration> values = createProjectConfigurations(count);
        result = manager.findProjectconfigurationEntities();
        assertArrayEquals(values.toArray(), result.toArray());
        
    }

    /**
     * Test of findProjectconfigurationEntities method, of class ProjectConfigurationManager.
     */
    @Test
    public void testFindProjectconfigurationEntities_int_int() {
        Random rand = new Random();
        int count = rand.nextInt(MAX_VALUES)+10;
        logger.log(Level.INFO,"Count = "+count);
        List<Projectconfiguration> values = createProjectConfigurations(count);
        for(int first=0;first<values.size()-1;first++){
            for(int maxResults = 1;maxResults < values.size()-1 - first;maxResults++){
                List<Projectconfiguration> exp = values.subList(first, first + maxResults);
                List<Projectconfiguration> result = manager.findProjectconfigurationEntities(maxResults, first);
                assertArrayEquals(exp.toArray(), result.toArray());
            }
        }
        List<Projectconfiguration> result = null;
        try{
            result = manager.findProjectconfigurationEntities(-1, 1);
            fail("Exception should be thrown");
        }catch(IllegalArgumentException iae){
            logger.log(Level.INFO,"Exception ", iae);
            assertNull(result);
        }catch(Exception ex){
            fail("IllegalArgumentException should be thrown.");
        }
        
        result = null;
        try{
            result = manager.findProjectconfigurationEntities(1, -1);
            fail("Exception should be thrown.");
        }catch(IllegalArgumentException iae){
            logger.log(Level.INFO,"Exception ",iae);
            assertNull(result);
        }catch(Exception ex){
            fail("IllegalArgumentException should be thrown.");
        }
    }

    /**
     * Test of getEntityManager method, of class ProjectConfigurationManager.
     */
//    @Test
//    public void testGetEntityManager() {
//        System.out.println("getEntityManager");
//        ProjectConfigurationManager instance = new ProjectConfigurationManagerImpl();
//        EntityManager expResult = null;
//        EntityManager result = instance.getEntityManager();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    /**
     * Test of getProjectconfigurationCount method, of class ProjectConfigurationManager.
     */
    @Test
    public void testGetProjectconfigurationCount() {
        System.out.println("getProjectconfigurationCount");
        int expResult = 0;
        int result = manager.getProjectconfigurationCount();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        Random rand = new Random();
        int count = rand.nextInt(MAX_VALUES)+10;
        List<Projectconfiguration> values = createProjectConfigurations(count);
        result = manager.getProjectconfigurationCount();
        assertEquals(values.size(), result);
        
    }

    private void clearDB() {
        EntityManagerFactory emf = DBUtils.getEntityManagerFactory();
        ProjectManager projManager = new ProjectJpaController(emf);
        ConfigurationManager confManager = new ConfigurationJpaController(emf);
        
        for(Project proj:projManager.findProjectEntities()){
            try {
                projManager.destroy(proj.getId());
            } catch (NonexistentEntityException ex) {
                Logger.getLogger(ProjectConfigurationManagerTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        for(Configuration conf:confManager.findConfigurationEntities()){
            try {
                confManager.destroy(conf.getId());
            } catch (NonexistentEntityException ex) {
                Logger.getLogger(ProjectConfigurationManagerTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        for(Projectconfiguration projConf:manager.findProjectconfigurationEntities()){
            try {
                manager.destroy(projConf.getId());
            } catch (NonexistentEntityException ex) {
                Logger.getLogger(ProjectConfigurationManagerTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private List<Projectconfiguration> createProjectConfigurations(int count) {
        ProjectManager projMan = new ProjectJpaController(DBUtils.getEntityManagerFactory());
        ConfigurationManager confMan = new ConfigurationJpaController(DBUtils.getEntityManagerFactory());
        List<Projectconfiguration> values = new ArrayList<>();
        for(int i=0;i<count;i++){
            Project proj = DBUtils.createProject();
            Configuration config = DBUtils.createConfiguration();
            Projectconfiguration projConfig = new Projectconfiguration();
            projMan.create(proj);
            confMan.create(config);
            projConfig.setConfigurationid(config.getId());
            projConfig.setProjectid(proj.getId());
            manager.create(projConfig);
            values.add(projConfig);
        }
        return values;
    }

    public class ProjectConfigurationManagerImpl implements ProjectConfigurationManager {

        public void create(Projectconfiguration projectconfiguration) {
        }

        public void destroy(Integer id) throws NonexistentEntityException {
        }

        public void edit(Projectconfiguration projectconfiguration) throws NonexistentEntityException, Exception {
        }

        public Projectconfiguration findProjectconfiguration(Integer id) {
            return null;
        }

        public List<Projectconfiguration> findProjectconfigurationEntities() {
            return null;
        }

        public List<Projectconfiguration> findProjectconfigurationEntities(int maxResults, int firstResult) {
            return null;
        }

        public EntityManager getEntityManager() {
            return null;
        }

        public int getProjectconfigurationCount() {
            return 0;
        }
    }
    
}
