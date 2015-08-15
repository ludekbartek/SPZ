/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.dcb.support.db.managers;

import cz.dcb.support.db.jpa.controllers.SpzIssuerJpaController;
import cz.dcb.support.db.jpa.controllers.SpzIssuerManager;
import cz.dcb.support.db.jpa.controllers.SpzJpaController;
import cz.dcb.support.db.jpa.controllers.SpzManager;
import cz.dcb.support.db.jpa.controllers.UserJpaController;
import cz.dcb.support.db.jpa.controllers.UserManager;
import cz.dcb.support.db.jpa.controllers.exceptions.NonexistentEntityException;
import cz.dcb.support.db.jpa.entities.Spz;
import cz.dcb.support.db.jpa.entities.Spzissuer;
import cz.dcb.support.db.jpa.entities.User;
import cz.dcb.support.db.managers.utils.DBUtils;

import java.util.List;
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
public class SpzIssuerManagerTest {

    private final SpzIssuerManager manager = new SpzIssuerJpaController(DBUtils.getEntityManagerFactory());
    private static final Logger logger = Logger.getLogger(SpzIssuerManagerTest.class.getName());
    
    public SpzIssuerManagerTest() {
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
     * Test of create method, of class SpzIssuerManager.
     */
    @Test
    public void testCreate() {
        System.out.println("create");
        Spzissuer spzissuer = null, result = null;
        //SpzIssuerManager instance = new SpzIssuerManagerImpl();
        try{
            manager.create(spzissuer);
            fail("Created null spzissuer record.");
        }catch(IllegalArgumentException iae){
            logger.log(Level.INFO,"Exception ", iae);
        }catch(Exception ex){
            fail("Invalid exception thrown.");
        }
        
        spzissuer = DBUtils.createSpzIssuer();
        try{
            manager.create(spzissuer);
        }catch(Exception ex){
            fail("Unexpected exception "+ex);
        }
        result = manager.findSpzissuer(spzissuer.getId());
        assertEquals(spzissuer, result);
    }

    /**
     * Test of edit method, of class SpzIssuerManager.
     */
    @Test
    public void testEdit() throws Exception {
        System.out.println("edit");
        Spzissuer spzissuer = null;
        SpzIssuerManager instance = new SpzIssuerManagerImpl();
        instance.edit(spzissuer);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findSpzissuer method, of class SpzIssuerManager.
     */
    @Test
    public void testFindSpzissuer() {
        System.out.println("findSpzissuer");
        Integer id = null;
        SpzIssuerManager instance = new SpzIssuerManagerImpl();
        Spzissuer expResult = null;
        Spzissuer result = instance.findSpzissuer(id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findSpzissuerEntities method, of class SpzIssuerManager.
     */
    @Test
    public void testFindSpzissuerEntities_0args() {
        System.out.println("findSpzissuerEntities");
        SpzIssuerManager instance = new SpzIssuerManagerImpl();
        List<Spzissuer> expResult = null;
        List<Spzissuer> result = instance.findSpzissuerEntities();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findSpzissuerEntities method, of class SpzIssuerManager.
     */
    @Test
    public void testFindSpzissuerEntities_int_int() {
        System.out.println("findSpzissuerEntities");
        int maxResults = 0;
        int firstResult = 0;
        SpzIssuerManager instance = new SpzIssuerManagerImpl();
        List<Spzissuer> expResult = null;
        List<Spzissuer> result = instance.findSpzissuerEntities(maxResults, firstResult);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getEntityManager method, of class SpzIssuerManager.
     */
    @Test
    public void testGetEntityManager() {
        System.out.println("getEntityManager");
        SpzIssuerManager instance = new SpzIssuerManagerImpl();
        EntityManager expResult = null;
        EntityManager result = instance.getEntityManager();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSpzissuerCount method, of class SpzIssuerManager.
     */
    @Test
    public void testGetSpzissuerCount() {
        System.out.println("getSpzissuerCount");
        SpzIssuerManager instance = new SpzIssuerManagerImpl();
        int expResult = 0;
        int result = instance.getSpzissuerCount();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    private void clearDB() {
        EntityManagerFactory emf = DBUtils.getEntityManagerFactory();
        SpzManager spzManager = new SpzJpaController(emf);
        UserManager userManager = new UserJpaController(emf);
        
        for(Spzissuer issuer:manager.findSpzissuerEntities()){
            try {
                manager.destroy(issuer.getId());
            } catch (NonexistentEntityException ex) {
                Logger.getLogger(SpzIssuerManagerTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        for(Spz spz:spzManager.findSpzEntities()){
            try {
                spzManager.destroy(spz.getId());
            } catch (NonexistentEntityException ex) {
                Logger.getLogger(SpzIssuerManagerTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        for(User user:userManager.findUserEntities()){
            try {
                userManager.destroy(user.getId());
            } catch (NonexistentEntityException ex) {
                Logger.getLogger(SpzIssuerManagerTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }

    public class SpzIssuerManagerImpl implements SpzIssuerManager {

        public void create(Spzissuer spzissuer) {
        }

        public void destroy(Integer id) throws NonexistentEntityException {
        }

        public void edit(Spzissuer spzissuer) throws NonexistentEntityException, Exception {
        }

        public Spzissuer findSpzissuer(Integer id) {
            return null;
        }

        public List<Spzissuer> findSpzissuerEntities() {
            return null;
        }

        public List<Spzissuer> findSpzissuerEntities(int maxResults, int firstResult) {
            return null;
        }

        public EntityManager getEntityManager() {
            return null;
        }

        public int getSpzissuerCount() {
            return 0;
        }
    }
    
}
