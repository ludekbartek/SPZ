/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.dcb.support.db.managers;

import cz.dcb.support.db.jpa.controllers.SpzStateNoteManager;
import cz.dcb.support.db.jpa.controllers.exceptions.NonexistentEntityException;
import cz.dcb.support.db.jpa.entities.Spzstatenote;
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
public class SpzStateNoteManagerTest {
    
    public SpzStateNoteManagerTest() {
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
     * Test of create method, of class SpzStateNoteManager.
     */
    @Test
    public void testCreate() {
        System.out.println("create");
        Spzstatenote spzstatenote = null;
        SpzStateNoteManager instance = new SpzStateNoteManagerImpl();
        instance.create(spzstatenote);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of destroy method, of class SpzStateNoteManager.
     */
    @Test
    public void testDestroy() throws Exception {
        System.out.println("destroy");
        Integer id = null;
        SpzStateNoteManager instance = new SpzStateNoteManagerImpl();
        instance.destroy(id);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of edit method, of class SpzStateNoteManager.
     */
    @Test
    public void testEdit() throws Exception {
        System.out.println("edit");
        Spzstatenote spzstatenote = null;
        SpzStateNoteManager instance = new SpzStateNoteManagerImpl();
        instance.edit(spzstatenote);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findSpzstatenote method, of class SpzStateNoteManager.
     */
    @Test
    public void testFindSpzstatenote() {
        System.out.println("findSpzstatenote");
        Integer id = null;
        SpzStateNoteManager instance = new SpzStateNoteManagerImpl();
        Spzstatenote expResult = null;
        Spzstatenote result = instance.findSpzstatenote(id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findSpzstatenoteEntities method, of class SpzStateNoteManager.
     */
    @Test
    public void testFindSpzstatenoteEntities_0args() {
        System.out.println("findSpzstatenoteEntities");
        SpzStateNoteManager instance = new SpzStateNoteManagerImpl();
        List<Spzstatenote> expResult = null;
        List<Spzstatenote> result = instance.findSpzstatenoteEntities();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findSpzstatenoteEntities method, of class SpzStateNoteManager.
     */
    @Test
    public void testFindSpzstatenoteEntities_int_int() {
        System.out.println("findSpzstatenoteEntities");
        int maxResults = 0;
        int firstResult = 0;
        SpzStateNoteManager instance = new SpzStateNoteManagerImpl();
        List<Spzstatenote> expResult = null;
        List<Spzstatenote> result = instance.findSpzstatenoteEntities(maxResults, firstResult);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getEntityManager method, of class SpzStateNoteManager.
     */
    @Test
    public void testGetEntityManager() {
        System.out.println("getEntityManager");
        SpzStateNoteManager instance = new SpzStateNoteManagerImpl();
        EntityManager expResult = null;
        EntityManager result = instance.getEntityManager();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSpzstatenoteCount method, of class SpzStateNoteManager.
     */
    @Test
    public void testGetSpzstatenoteCount() {
        System.out.println("getSpzstatenoteCount");
        SpzStateNoteManager instance = new SpzStateNoteManagerImpl();
        int expResult = 0;
        int result = instance.getSpzstatenoteCount();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    public class SpzStateNoteManagerImpl implements SpzStateNoteManager {

        public void create(Spzstatenote spzstatenote) {
        }

        public void destroy(Integer id) throws NonexistentEntityException {
        }

        public void edit(Spzstatenote spzstatenote) throws NonexistentEntityException, Exception {
        }

        public Spzstatenote findSpzstatenote(Integer id) {
            return null;
        }

        public List<Spzstatenote> findSpzstatenoteEntities() {
            return null;
        }

        public List<Spzstatenote> findSpzstatenoteEntities(int maxResults, int firstResult) {
            return null;
        }

        public EntityManager getEntityManager() {
            return null;
        }

        public int getSpzstatenoteCount() {
            return 0;
        }
    }
    
}
