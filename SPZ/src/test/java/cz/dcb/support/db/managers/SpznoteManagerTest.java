/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.dcb.support.db.managers;

import cz.dcb.support.db.jpa.Spznote;
import cz.dcb.support.db.managers.exceptions.IllegalOrphanException;
import cz.dcb.support.db.managers.exceptions.NonexistentEntityException;
import cz.dcb.support.db.managers.exceptions.PreexistingEntityException;
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
public class SpznoteManagerTest {
    
    public SpznoteManagerTest() {
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
     * Test of create method, of class SpznoteManager.
     */
    @Test
    public void testCreate() throws Exception {
        System.out.println("create");
        Spznote spznote = null;
        SpznoteManager instance = new SpznoteManagerImpl();
        instance.create(spznote);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of destroy method, of class SpznoteManager.
     */
    @Test
    public void testDestroy() throws Exception {
        System.out.println("destroy");
        Integer id = null;
        SpznoteManager instance = new SpznoteManagerImpl();
        instance.destroy(id);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of edit method, of class SpznoteManager.
     */
    @Test
    public void testEdit() throws Exception {
        System.out.println("edit");
        Spznote spznote = null;
        SpznoteManager instance = new SpznoteManagerImpl();
        instance.edit(spznote);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findSpznote method, of class SpznoteManager.
     */
    @Test
    public void testFindSpznote() {
        System.out.println("findSpznote");
        Integer id = null;
        SpznoteManager instance = new SpznoteManagerImpl();
        Spznote expResult = null;
        Spznote result = instance.findSpznote(id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findSpznoteEntities method, of class SpznoteManager.
     */
    @Test
    public void testFindSpznoteEntities_0args() {
        System.out.println("findSpznoteEntities");
        SpznoteManager instance = new SpznoteManagerImpl();
        List<Spznote> expResult = null;
        List<Spznote> result = instance.findSpznoteEntities();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findSpznoteEntities method, of class SpznoteManager.
     */
    @Test
    public void testFindSpznoteEntities_int_int() {
        System.out.println("findSpznoteEntities");
        int maxResults = 0;
        int firstResult = 0;
        SpznoteManager instance = new SpznoteManagerImpl();
        List<Spznote> expResult = null;
        List<Spznote> result = instance.findSpznoteEntities(maxResults, firstResult);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSpznoteCount method, of class SpznoteManager.
     */
    @Test
    public void testGetSpznoteCount() {
        System.out.println("getSpznoteCount");
        SpznoteManager instance = new SpznoteManagerImpl();
        int expResult = 0;
        int result = instance.getSpznoteCount();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    public class SpznoteManagerImpl implements SpznoteManager {

        public void create(Spznote spznote) throws PreexistingEntityException, Exception {
        }

        public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        }

        public void edit(Spznote spznote) throws IllegalOrphanException, NonexistentEntityException, Exception {
        }

        public Spznote findSpznote(Integer id) {
            return null;
        }

        public List<Spznote> findSpznoteEntities() {
            return null;
        }

        public List<Spznote> findSpznoteEntities(int maxResults, int firstResult) {
            return null;
        }

        public int getSpznoteCount() {
            return 0;
        }
    }
    
}
