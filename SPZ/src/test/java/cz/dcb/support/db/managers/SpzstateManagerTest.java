/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.dcb.support.db.managers;

import cz.dcb.support.db.jpa.controllers.SpzStateJpaController;
import cz.dcb.support.db.jpa.controllers.SpzStateManager;
import cz.dcb.support.db.jpa.entities.Spzstate;
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
public class SpzstateManagerTest {
    
    private final SpzStateManager manager= new SpzStateJpaController(DBUtils.getEntityManagerFactory());
    
    public SpzstateManagerTest() {
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
     * Test of create method, of class SpzstateManager.
     */
    @Test
    public void testCreate() throws Exception {
        System.out.println("create");
        Spzstate spzstate = null;
//        SpzstateManager instance = new SpzstateManagerImpl();
        manager.create(spzstate);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of destroy method, of class SpzstateManager.
     */
    @Test
    public void testDestroy() throws Exception {
        System.out.println("destroy");
        Integer id = null;
       // SpzstateManager instance = new SpzstateManagerImpl();
        manager.destroy(id);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of edit method, of class SpzstateManager.
     */
    @Test
    public void testEdit() throws Exception {
        System.out.println("edit");
        Spzstate spzstate = null;
        //SpzstateManager instance = new SpzstateManagerImpl();
        manager.edit(spzstate);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findSpzstate method, of class SpzstateManager.
     */
    @Test
    public void testFindSpzstate() {
        System.out.println("findSpzstate");
        Integer id = null;
        //SpzstateManager instance = new SpzstateManagerImpl();
        Spzstate expResult = null;
        Spzstate result = manager.findSpzstate(id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findSpzstateEntities method, of class SpzstateManager.
     */
    @Test
    public void testFindSpzstateEntities_0args() {
        System.out.println("findSpzstateEntities");
        //SpzstateManager instance = new SpzstateManagerImpl();
        List<Spzstate> expResult = null;
        List<Spzstate> result = manager.findSpzstateEntities();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findSpzstateEntities method, of class SpzstateManager.
     */
    @Test
    public void testFindSpzstateEntities_int_int() {
        System.out.println("findSpzstateEntities");
        int maxResults = 0;
        int firstResult = 0;
        //SpzstateManager instance = new SpzstateManagerImpl();
        List<Spzstate> expResult = null;
        List<Spzstate> result = manager.findSpzstateEntities(maxResults, firstResult);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSpzstateCount method, of class SpzstateManager.
     */
    @Test
    public void testGetSpzstateCount() {
        System.out.println("getSpzstateCount");
        //SpzstateManager instance = new SpzstateManagerImpl();
        int expResult = 0;
        int result = manager.getSpzstateCount();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
/*
    public class SpzstateManagerImpl implements SpzstateManager {

        public void create(Spzstate spzstate) throws PreexistingEntityException, Exception {
        }

        public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        }

        public void edit(Spzstate spzstate) throws IllegalOrphanException, NonexistentEntityException, Exception {
        }

        public Spzstate findSpzstate(Integer id) {
            return null;
        }

        public List<Spzstate> findSpzstateEntities() {
            return null;
        }

        public List<Spzstate> findSpzstateEntities(int maxResults, int firstResult) {
            return null;
        }

        public int getSpzstateCount() {
            return 0;
        }
    }
  */  
}
