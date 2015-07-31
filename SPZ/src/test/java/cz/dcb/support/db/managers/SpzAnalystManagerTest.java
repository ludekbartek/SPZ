/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.dcb.support.db.managers;

import cz.dcb.support.db.jpa.controllers.SpzAnalystManager;
import cz.dcb.support.db.jpa.controllers.exceptions.NonexistentEntityException;
import cz.dcb.support.db.jpa.entities.Spzanalyst;
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
public class SpzAnalystManagerTest {
    
    public SpzAnalystManagerTest() {
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
     * Test of create method, of class SpzAnalystManager.
     */
    @Test
    public void testCreate() {
        System.out.println("create");
        Spzanalyst spzanalyst = null;
        SpzAnalystManager instance = new SpzAnalystManagerImpl();
        instance.create(spzanalyst);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of destroy method, of class SpzAnalystManager.
     */
    @Test
    public void testDestroy() throws Exception {
        System.out.println("destroy");
        Integer id = null;
        SpzAnalystManager instance = new SpzAnalystManagerImpl();
        instance.destroy(id);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of edit method, of class SpzAnalystManager.
     */
    @Test
    public void testEdit() throws Exception {
        System.out.println("edit");
        Spzanalyst spzanalyst = null;
        SpzAnalystManager instance = new SpzAnalystManagerImpl();
        instance.edit(spzanalyst);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findSpzanalyst method, of class SpzAnalystManager.
     */
    @Test
    public void testFindSpzanalyst() {
        System.out.println("findSpzanalyst");
        Integer id = null;
        SpzAnalystManager instance = new SpzAnalystManagerImpl();
        Spzanalyst expResult = null;
        Spzanalyst result = instance.findSpzanalyst(id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findSpzanalystEntities method, of class SpzAnalystManager.
     */
    @Test
    public void testFindSpzanalystEntities_0args() {
        System.out.println("findSpzanalystEntities");
        SpzAnalystManager instance = new SpzAnalystManagerImpl();
        List<Spzanalyst> expResult = null;
        List<Spzanalyst> result = instance.findSpzanalystEntities();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findSpzanalystEntities method, of class SpzAnalystManager.
     */
    @Test
    public void testFindSpzanalystEntities_int_int() {
        System.out.println("findSpzanalystEntities");
        int maxResults = 0;
        int firstResult = 0;
        SpzAnalystManager instance = new SpzAnalystManagerImpl();
        List<Spzanalyst> expResult = null;
        List<Spzanalyst> result = instance.findSpzanalystEntities(maxResults, firstResult);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getEntityManager method, of class SpzAnalystManager.
     */
    @Test
    public void testGetEntityManager() {
        System.out.println("getEntityManager");
        SpzAnalystManager instance = new SpzAnalystManagerImpl();
        EntityManager expResult = null;
        EntityManager result = instance.getEntityManager();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSpzanalystCount method, of class SpzAnalystManager.
     */
    @Test
    public void testGetSpzanalystCount() {
        System.out.println("getSpzanalystCount");
        SpzAnalystManager instance = new SpzAnalystManagerImpl();
        int expResult = 0;
        int result = instance.getSpzanalystCount();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    public class SpzAnalystManagerImpl implements SpzAnalystManager {

        public void create(Spzanalyst spzanalyst) {
        }

        public void destroy(Integer id) throws NonexistentEntityException {
        }

        public void edit(Spzanalyst spzanalyst) throws NonexistentEntityException, Exception {
        }

        public Spzanalyst findSpzanalyst(Integer id) {
            return null;
        }

        public List<Spzanalyst> findSpzanalystEntities() {
            return null;
        }

        public List<Spzanalyst> findSpzanalystEntities(int maxResults, int firstResult) {
            return null;
        }

        public EntityManager getEntityManager() {
            return null;
        }

        public int getSpzanalystCount() {
            return 0;
        }
    }
    
}
