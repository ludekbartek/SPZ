/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.dcb.support.db.managers;

import cz.dcb.support.db.jpa.Configuration;
import cz.dcb.support.db.jpa.Project;
import cz.dcb.support.db.jpa.Spz;
import cz.dcb.support.db.jpa.User;
import cz.dcb.support.db.managers.exceptions.IllegalOrphanException;
import cz.dcb.support.db.managers.exceptions.NonexistentEntityException;
import cz.dcb.support.db.managers.exceptions.PreexistingEntityException;
import cz.dcb.support.db.managers.utils.DBUtils;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class SpzManagerTest {
    
    private SpzManager manager;
    private UserManager userManager;
    
    private static final Logger LOGGER = Logger.getLogger(SpzManagerTest.class.getName());
    
    public SpzManagerTest() {
        EntityManagerFactory emf = DBUtils.getEntityManagerFactory();
        manager = new SpzJpaController(emf);
        userManager = new UserJpaController(emf);
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
        EntityManagerFactory emf = DBUtils.getEntityManagerFactory();
        deleteSpzs(emf);
        deleteConfigurations(emf);
        deleteProjects(emf);
        deleteUsers(emf);
    }

    /**
     * Test of create method, of class SpzManager.
     */
    @Test
    public void testCreate() throws Exception {
        System.out.println("create correct Spz");
        Spz spz = DBUtils.createSpz();
        manager.create(spz);
        
        
    }

    /**
     * Test of destroy method, of class SpzManager.
     */
    @Test
    public void testDestroy() throws Exception {
        System.out.println("destroy");
        Integer id = null;
        SpzManager instance = new SpzManagerImpl();
        instance.destroy(id);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of edit method, of class SpzManager.
     */
    @Test
    public void testEdit() throws Exception {
        System.out.println("edit");
        Spz spz = null;
        SpzManager instance = new SpzManagerImpl();
        instance.edit(spz);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findSpz method, of class SpzManager.
     */
    @Test
    public void testFindSpz() {
        System.out.println("findSpz");
        Integer id = null;
        SpzManager instance = new SpzManagerImpl();
        Spz expResult = null;
        Spz result = instance.findSpz(id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findSpzEntities method, of class SpzManager.
     */
    @Test
    public void testFindSpzEntities_0args() {
        System.out.println("findSpzEntities");
        SpzManager instance = new SpzManagerImpl();
        List<Spz> expResult = null;
        List<Spz> result = instance.findSpzEntities();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findSpzEntities method, of class SpzManager.
     */
    @Test
    public void testFindSpzEntities_int_int() {
        System.out.println("findSpzEntities");
        int maxResults = 0;
        int firstResult = 0;
        SpzManager instance = new SpzManagerImpl();
        List<Spz> expResult = null;
        List<Spz> result = instance.findSpzEntities(maxResults, firstResult);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSpzCount method, of class SpzManager.
     */
    @Test
    public void testGetSpzCount() {
        System.out.println("getSpzCount");
        SpzManager instance = new SpzManagerImpl();
        int expResult = 0;
        int result = instance.getSpzCount();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    private void deleteSpzs(EntityManagerFactory emf) {
        SpzManager manager = new SpzJpaController(emf);
        for(Spz spz:manager.findSpzEntities()){
            try {
                manager.destroy(spz.getId());
            } catch (IllegalOrphanException | NonexistentEntityException ex) {
                Logger.getLogger(SpzManagerTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void deleteConfigurations(EntityManagerFactory emf) {
        ConfigurationManager manager = new ConfigurationJpaController(emf);
        for(Configuration conf:manager.findConfigurationEntities()){
            try {
                manager.destroy(conf.getId());
            } catch (IllegalOrphanException | NonexistentEntityException ex) {
                Logger.getLogger(SpzManagerTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void deleteProjects(EntityManagerFactory emf) {
        ProjectManager manager = new ProjectJpaController(emf);
        for(Project proj:manager.findProjectEntities()){
            try {
                manager.destroy(proj.getName());
            } catch (IllegalOrphanException | NonexistentEntityException ex) {
                Logger.getLogger(SpzManagerTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void deleteUsers(EntityManagerFactory emf) {
        UserManager manager = new UserJpaController(emf);
        for(User user:manager.findUserEntities()){
            try {
                manager.destroy(user.getLogin());
            } catch (IllegalOrphanException | NonexistentEntityException ex) {
                Logger.getLogger(SpzManagerTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public class SpzManagerImpl implements SpzManager {

        public void create(Spz spz) throws PreexistingEntityException, Exception {
        }

        public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        }

        public void edit(Spz spz) throws IllegalOrphanException, NonexistentEntityException, Exception {
        }

        public Spz findSpz(Integer id) {
            return null;
        }

        public List<Spz> findSpzEntities() {
            return null;
        }

        public List<Spz> findSpzEntities(int maxResults, int firstResult) {
            return null;
        }

        public int getSpzCount() {
            return 0;
        }
    }
    
}
