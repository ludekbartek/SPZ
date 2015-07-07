/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.dcb.support.db.managers;

import cz.dcb.support.db.jpa.Roles;
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
public class RoleManagerTest {
    
    public RoleManagerTest() {
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
     * Test of create method, of class RoleManager.
     */
    @Test
    public void testCreate() throws Exception {
        System.out.println("create");
        Roles role = null;
        RoleManager instance = new RoleManagerImpl();
        instance.create(role);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of destroy method, of class RoleManager.
     */
    @Test
    public void testDestroy() throws Exception {
        System.out.println("destroy");
        String id = "";
        RoleManager instance = new RoleManagerImpl();
        instance.destroy(id);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of edit method, of class RoleManager.
     */
    @Test
    public void testEdit() throws Exception {
        System.out.println("edit");
        Roles role = null;
        RoleManager instance = new RoleManagerImpl();
        instance.edit(role);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findRoles method, of class RoleManager.
     */
    @Test
    public void testFindRoles() {
        System.out.println("findRoles");
        String id = "";
        RoleManager instance = new RoleManagerImpl();
        Roles expResult = null;
        Roles result = instance.findRoles(id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findRolesEntities method, of class RoleManager.
     */
    @Test
    public void testFindRolesEntities_0args() {
        System.out.println("findRolesEntities");
        RoleManager instance = new RoleManagerImpl();
        List<Roles> expResult = null;
        List<Roles> result = instance.findRolesEntities();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findRolesEntities method, of class RoleManager.
     */
    @Test
    public void testFindRolesEntities_int_int() {
        System.out.println("findRolesEntities");
        int maxResults = 0;
        int firstResult = 0;
        RoleManager instance = new RoleManagerImpl();
        List<Roles> expResult = null;
        List<Roles> result = instance.findRolesEntities(maxResults, firstResult);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getRolesCount method, of class RoleManager.
     */
    @Test
    public void testGetRolesCount() {
        System.out.println("getRolesCount");
        RoleManager instance = new RoleManagerImpl();
        int expResult = 0;
        int result = instance.getRolesCount();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    public class RoleManagerImpl implements RoleManager {

        public void create(Roles role) throws PreexistingEntityException, Exception {
        }

        public void destroy(String id) throws NonexistentEntityException {
        }

        public void edit(Roles role) throws NonexistentEntityException, Exception {
        }

        public Roles findRoles(String id) {
            return null;
        }

        public List<Roles> findRolesEntities() {
            return null;
        }

        public List<Roles> findRolesEntities(int maxResults, int firstResult) {
            return null;
        }

        public int getRolesCount() {
            return 0;
        }
    }
    
}
