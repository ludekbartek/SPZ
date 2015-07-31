/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.dcb.support.db.managers;

import cz.dcb.support.db.jpa.controllers.SpzIssuerManager;
import cz.dcb.support.db.jpa.controllers.exceptions.NonexistentEntityException;
import cz.dcb.support.db.jpa.entities.Spzissuer;
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
public class SpzIssuerManagerTest {
    
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
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of create method, of class SpzIssuerManager.
     */
    @Test
    public void testCreate() {
        System.out.println("create");
        Spzissuer spzissuer = null;
        SpzIssuerManager instance = new SpzIssuerManagerImpl();
        instance.create(spzissuer);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of destroy method, of class SpzIssuerManager.
     */
    @Test
    public void testDestroy() throws Exception {
        System.out.println("destroy");
        Integer id = null;
        SpzIssuerManager instance = new SpzIssuerManagerImpl();
        instance.destroy(id);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
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
