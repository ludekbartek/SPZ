/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.dcb.support.db.managers;

import cz.dcb.support.db.jpa.controllers.SpzStatesManager;
import cz.dcb.support.db.jpa.controllers.exceptions.NonexistentEntityException;
import cz.dcb.support.db.jpa.entities.Spzstates;
import java.util.List;
import javax.persistence.EntityManager;
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
public class SpzStatesManagerTest {
    
    public SpzStatesManagerTest() {
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
     * Test of create method, of class SpzStatesManager.
     */
    @Test
    public void testCreate() {
        System.out.println("create");
        Spzstates spzstates = null;
        SpzStatesManager instance = new SpzStatesManagerImpl();
        instance.create(spzstates);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of destroy method, of class SpzStatesManager.
     */
    @Test
    public void testDestroy() throws Exception {
        System.out.println("destroy");
        Integer id = null;
        SpzStatesManager instance = new SpzStatesManagerImpl();
        instance.destroy(id);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of edit method, of class SpzStatesManager.
     */
    @Test
    public void testEdit() throws Exception {
        System.out.println("edit");
        Spzstates spzstates = null;
        SpzStatesManager instance = new SpzStatesManagerImpl();
        instance.edit(spzstates);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findSpzstates method, of class SpzStatesManager.
     */
    @Test
    public void testFindSpzstates() {
        System.out.println("findSpzstates");
        Integer id = null;
        SpzStatesManager instance = new SpzStatesManagerImpl();
        Spzstates expResult = null;
        Spzstates result = instance.findSpzstates(id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findSpzstatesEntities method, of class SpzStatesManager.
     */
    @Test
    public void testFindSpzstatesEntities_0args() {
        System.out.println("findSpzstatesEntities");
        SpzStatesManager instance = new SpzStatesManagerImpl();
        List<Spzstates> expResult = null;
        List<Spzstates> result = instance.findSpzstatesEntities();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findSpzstatesEntities method, of class SpzStatesManager.
     */
    @Test
    public void testFindSpzstatesEntities_int_int() {
        System.out.println("findSpzstatesEntities");
        int maxResults = 0;
        int firstResult = 0;
        SpzStatesManager instance = new SpzStatesManagerImpl();
        List<Spzstates> expResult = null;
        List<Spzstates> result = instance.findSpzstatesEntities(maxResults, firstResult);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getEntityManager method, of class SpzStatesManager.
     */
    @Test
    public void testGetEntityManager() {
        System.out.println("getEntityManager");
        SpzStatesManager instance = new SpzStatesManagerImpl();
        EntityManager expResult = null;
        EntityManager result = instance.getEntityManager();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSpzstatesCount method, of class SpzStatesManager.
     */
    @Test
    public void testGetSpzstatesCount() {
        System.out.println("getSpzstatesCount");
        SpzStatesManager instance = new SpzStatesManagerImpl();
        int expResult = 0;
        int result = instance.getSpzstatesCount();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    public class SpzStatesManagerImpl implements SpzStatesManager {

        public void create(Spzstates spzstates) {
        }

        public void destroy(Integer id) throws NonexistentEntityException {
        }

        public void edit(Spzstates spzstates) throws NonexistentEntityException, Exception {
        }

        public Spzstates findSpzstates(Integer id) {
            return null;
        }

        public List<Spzstates> findSpzstatesEntities() {
            return null;
        }

        public List<Spzstates> findSpzstatesEntities(int maxResults, int firstResult) {
            return null;
        }

        public EntityManager getEntityManager() {
            return null;
        }

        public int getSpzstatesCount() {
            return 0;
        }
    }
    
}
