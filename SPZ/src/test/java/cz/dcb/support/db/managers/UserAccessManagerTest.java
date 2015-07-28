/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.dcb.support.db.managers;

import cz.dcb.support.db.jpa.controllers.UserAccessJpaController;
import cz.dcb.support.db.jpa.controllers.UserAccessManager;
import cz.dcb.support.db.jpa.entities.Useraccess;
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
public class UserAccessManagerTest {
    
    private final UserAccessManager manager = new UserAccessJpaController(DBUtils.getEntityManagerFactory());
    public UserAccessManagerTest() {
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
     * Test of create method, of class UserAccessManager.
     */
    @Test
    public void testCreate() throws Exception {
        System.out.println("create");
        Useraccess useraccess = null;
//        UserAccessManager instance = new UserAccessManagerImpl();
  
        manager.create(useraccess);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of destroy method, of class UserAccessManager.
     */
    @Test
    public void testDestroy() throws Exception {
        System.out.println("destroy");
        Integer id = null;
//        UserAccessManager instance = new UserAccessManagerImpl();
        manager.destroy(id);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of edit method, of class UserAccessManager.
     */
    @Test
    public void testEdit() throws Exception {
        System.out.println("edit");
        Useraccess useraccess = null;
//        UserAccessManager instance = new UserAccessManagerImpl();
        manager.edit(useraccess);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findUseraccess method, of class UserAccessManager.
     */
    @Test
    public void testFindUseraccess() {
        System.out.println("findUseraccess");
        Integer id = null;
        //UserAccessManager instance = new UserAccessManagerImpl();
        Useraccess expResult = null;
        Useraccess result = manager.findUseraccess(id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findUseraccessEntities method, of class UserAccessManager.
     */
    @Test
    public void testFindUseraccessEntities_0args() {
        System.out.println("findUseraccessEntities");
        //UserAccessManager instance = new UserAccessManagerImpl();
        List<Useraccess> expResult = null;
        List<Useraccess> result = manager.findUseraccessEntities();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findUseraccessEntities method, of class UserAccessManager.
     */
    @Test
    public void testFindUseraccessEntities_int_int() {
        System.out.println("findUseraccessEntities");
        int maxResults = 0;
        int firstResult = 0;
        //UserAccessManager instance = new UserAccessManagerImpl();
        List<Useraccess> expResult = null;
        List<Useraccess> result = manager.findUseraccessEntities(maxResults, firstResult);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getUseraccessCount method, of class UserAccessManager.
     */
    @Test
    public void testGetUseraccessCount() {
        System.out.println("getUseraccessCount");
       // UserAccessManager instance = new UserAccessManagerImpl();
        int expResult = 0;
        int result = manager.getUseraccessCount();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

/*    public class UserAccessManagerImpl implements UserAccessManager {

        public void create(Useraccess useraccess) throws PreexistingEntityException, Exception {
        }

        public void destroy(Integer id) throws NonexistentEntityException {
        }

        public void edit(Useraccess useraccess) throws NonexistentEntityException, Exception {
        }

        public Useraccess findUseraccess(Integer id) {
            return null;
        }

        public List<Useraccess> findUseraccessEntities() {
            return null;
        }

        public List<Useraccess> findUseraccessEntities(int maxResults, int firstResult) {
            return null;
        }

        public int getUseraccessCount() {
            return 0;
        }
    }
  */  
}
