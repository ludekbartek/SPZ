/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.dcb.support.db.managers;

import cz.dcb.support.db.jpa.User;
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
public class UserManagerTest {
    
    public UserManagerTest() {
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
     * Test of create method, of class UserManager.
     */
    @Test
    public void testCreate() throws Exception {
        System.out.println("create");
        User user = null;
        UserManager instance = new UserManagerImpl();
        instance.create(user);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of destroy method, of class UserManager.
     */
    @Test
    public void testDestroy() throws Exception {
        System.out.println("destroy");
        String id = "";
        UserManager instance = new UserManagerImpl();
        instance.destroy(id);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of edit method, of class UserManager.
     */
    @Test
    public void testEdit() throws Exception {
        System.out.println("edit");
        User user = null;
        UserManager instance = new UserManagerImpl();
        instance.edit(user);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findUser method, of class UserManager.
     */
    @Test
    public void testFindUser() {
        System.out.println("findUser");
        String id = "";
        UserManager instance = new UserManagerImpl();
        User expResult = null;
        User result = instance.findUser(id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findUserEntities method, of class UserManager.
     */
    @Test
    public void testFindUserEntities_0args() {
        System.out.println("findUserEntities");
        UserManager instance = new UserManagerImpl();
        List<User> expResult = null;
        List<User> result = instance.findUserEntities();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findUserEntities method, of class UserManager.
     */
    @Test
    public void testFindUserEntities_int_int() {
        System.out.println("findUserEntities");
        int maxResults = 0;
        int firstResult = 0;
        UserManager instance = new UserManagerImpl();
        List<User> expResult = null;
        List<User> result = instance.findUserEntities(maxResults, firstResult);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getUserCount method, of class UserManager.
     */
    @Test
    public void testGetUserCount() {
        System.out.println("getUserCount");
        UserManager instance = new UserManagerImpl();
        int expResult = 0;
        int result = instance.getUserCount();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    public class UserManagerImpl implements UserManager {

        public void create(User user) throws PreexistingEntityException, Exception {
        }

        public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException {
        }

        public void edit(User user) throws IllegalOrphanException, NonexistentEntityException, Exception {
        }

        public User findUser(String id) {
            return null;
        }

        public List<User> findUserEntities() {
            return null;
        }

        public List<User> findUserEntities(int maxResults, int firstResult) {
            return null;
        }

        public int getUserCount() {
            return 0;
        }
    }
    
}
