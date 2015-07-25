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
public class UserManagerTest {

    UserManager manager = null;
    public UserManagerTest() {
        EntityManagerFactory emf = DBUtils.getEntityManagerFactory();
        manager = new UserJpaController(emf);
    }
    
    @BeforeClass
    public static void setUpClass() {
        
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        for(User user:manager.findUserEntities()){
            try {
                manager.destroy(user.getLogin());
            } catch (IllegalOrphanException | NonexistentEntityException ex) {
                Logger.getLogger(UserManagerTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
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
        try{
            manager.create(user);
            fail("Null user created");
        }catch(Exception ex){
            
        }
        user = DBUtils.createUser();
        try{
            manager.create(user);
            User retValue = manager.findUser(user.getLogin());
            assertNotNull("User not found",retValue);
            assertEquals("User login differs.",retValue.getLogin(), user.getLogin());
        }catch(Exception ex){
            fail("Unexpected exception thrown: "+ex);
        }
        try{
            manager.create(user);
            fail("Duplicit user created");
        }catch(PreexistingEntityException ex){
            
        }
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of destroy method, of class UserManager.
     */
    /*
    @Test
    public void testDestroy() throws Exception {
        System.out.println("destroy");
        String id = "";
        UserManager instance = new UserManagerImpl();
        instance.destroy(id);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    */
    /**
     * Test of edit method, of class UserManager.
     */
    @Test
    public void testEdit() throws Exception {
        testNullEdit();
        User user = DBUtils.createUser();
        String login = user.getLogin();
        manager.create(user);
        user.setLogin(null);
        try{
            manager.edit(user);
            fail("Login changed to null");
        }catch(Exception ex){
            Logger.getLogger(UserManagerTest.class.getName()).log(Level.SEVERE,"Setting username to null.",ex);
        }
        user.setLogin(login);
        testCorrectEdits(user, login);
    }

    private void testCorrectEdits(User user, String login) {
        StringBuilder name=new StringBuilder(user.getName());
        StringBuilder email = new StringBuilder(user.getEmail());
        StringBuilder passwd = new StringBuilder(user.getPassword());
        user.setName(name.append(" z Brna").toString());
        user.setEmail(email.append(".cz").toString());
        user.setPassword(passwd.append("xx").toString());
        try{
            manager.edit(user);
            User returned = manager.findUser(login);
            assertEquals("E-mail does not match:",returned.getEmail(), email.toString());
            assertEquals("Name does not match:",returned.getName(), name.toString());
            assertEquals("Password does not match:", returned.getPassword(), passwd.toString());
            
        }catch(Exception ex){
            fail("Unexpected exception thrown: "+ex);
        }
    }

    private void testNullEdit() {
        try{
            manager.edit(null);
            fail("Null user edited");
        }catch(Exception ex){
            Logger.getLogger(UserManagerTest.class.getName()).log(Level.INFO,"Correct exception thrown",ex);
        }
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
