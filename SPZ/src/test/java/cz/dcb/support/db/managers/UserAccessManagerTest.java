/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.dcb.support.db.managers;

import cz.dcb.support.db.jpa.controllers.ConfigurationJpaController;
import cz.dcb.support.db.jpa.controllers.ConfigurationManager;
import cz.dcb.support.db.jpa.controllers.UserAccessJpaController;
import cz.dcb.support.db.jpa.controllers.UserAccessManager;
import cz.dcb.support.db.jpa.controllers.UserJpaController;
import cz.dcb.support.db.jpa.controllers.UserManager;
import cz.dcb.support.db.jpa.entities.Configuration;
import cz.dcb.support.db.jpa.entities.User;
import cz.dcb.support.db.jpa.entities.Useraccess;
import cz.dcb.support.db.managers.exceptions.NonexistentEntityException;
import cz.dcb.support.db.managers.exceptions.PreexistingEntityException;
import cz.dcb.support.db.managers.utils.DBUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.RollbackException;
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
    private static final Logger LOGGER = Logger.getLogger(UserAccessManagerTest.class.getName());
    private static final int MAX_ACCESSES = 90;
    
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
        clearDB();
    }
    
    @After
    public void tearDown() {
        clearDB();
    }

    /**
     * Test of create method, of class UserAccessManager.
     */
    @Test
    public void testCreate() throws Exception {
        System.out.println("create");
        Useraccess userAccess = null;
//        UserAccessManager instance = new UserAccessManagerImpl();
        try{
            manager.create(userAccess);
            fail("Exception should be thrown.");
        }catch(IllegalArgumentException ex){
            LOGGER.log(Level.INFO,"Exception ",ex);
        }catch(Exception ex){
            fail("Unexpected exception " + ex);
        }
        userAccess = DBUtils.createUserAccess();
        manager.create(userAccess);
        assertNotNull(userAccess.getId());
        Useraccess result = manager.findUseraccess(userAccess.getId());
        assertEquals(userAccess,result);
    }

    /**
     * Test of edit method, of class UserAccessManager.
     */
    @Test
    public void testEdit() throws Exception {
        System.out.println("edit");
        Useraccess userAccess = null;
//        UserAccessManager instance = new UserAccessManagerImpl();
        try{
            manager.edit(userAccess);
            fail("Exception should be thrown.");
        }catch(IllegalArgumentException ex){
            LOGGER.log(Level.INFO,"Exception ",ex);
        }catch(Exception ex){
            fail("Unexpected exception thrown "+ex);
        }
        // TODO review the generated test code and remove the default call to fail.
        Random rand = new Random();
        int count = rand.nextInt(MAX_ACCESSES);
        List<Useraccess> accessList = createUserAccessList(count);
        for(Useraccess access:accessList){
            access.setRole(access.getRole()+"-new");
            manager.edit(access);
            userAccess = manager.findUseraccess(access.getId());
            assertEquals(access,userAccess);
        }
    }
    
    @Test
    public void testEditInvalidRole() throws Exception{
        System.out.println("testEditInvalidRole");
        Useraccess value=DBUtils.createUserAccess();
        String newRole = createRole(32);
        value.setRole(newRole);
        try{
            manager.create(value);
        }catch(Exception ex){
            fail("Unexpected exception thrown "+ex);
        }
        
        newRole = createRole(33);
        value.setRole(newRole);
        try{
             manager.edit(value);
             fail("Exception should be thrown.");
        }catch(RollbackException ex){
            LOGGER.log(Level.INFO,ex.toString());
        }catch(Exception ex){
            fail("Unexpected exception was thrown "+ex);
        }
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
        Useraccess result = null;
        try{
            result = manager.findUseraccess(id);
            fail("Exception should be thrown.");
        }catch(IllegalArgumentException ex){
            LOGGER.log(Level.INFO,"Exception ",ex);
        }catch(Exception ex){
            fail("Invalid excepiton thrown "+ex);
        }
        assertEquals(expResult, result);
        Random rand = new Random();
        int count = rand.nextInt(MAX_ACCESSES);
        List<Useraccess> accessList = createUserAccessList(count);
        for(Useraccess access:accessList){
            result = manager.findUseraccess(access.getId());
            assertEquals(access, result);
        }
    }

    @Test
    public void testFindUseraccessInvalidId() {
        System.out.println("findUseraccess");
        Random rand = new Random();
        int count = rand.nextInt(MAX_ACCESSES)+10;
        List<Useraccess> accessList = createUserAccessList(count);
        Comparator<Useraccess> comp = new Comparator<Useraccess>() {

            @Override
            public int compare(Useraccess o1, Useraccess o2) {
                return o1.getId() - o2.getId();
            }
        };
        int minId = Collections.min(accessList, comp).getId()-1;
        int maxId = Collections.max(accessList, comp).getId()+1;
        Useraccess result = manager.findUseraccess(minId);
        assertNull(result);
        result = manager.findUseraccess(maxId);
        assertNull(result);
    }
    /**
     * Test of findUseraccessEntities method, of class UserAccessManager.
     */
    @Test
    public void testFindUseraccessEntities_0args() {
        System.out.println("findUseraccessEntities");
        //UserAccessManager instance = new UserAccessManagerImpl();
        Random rand = new Random();
        int count = rand.nextInt(MAX_ACCESSES)+10;
        List<Useraccess> expResult = new ArrayList<>();
        List<Useraccess> result = manager.findUseraccessEntities();
        assertEquals(expResult, result);
        expResult = createUserAccessList(count);
        result = manager.findUseraccessEntities();
        assertArrayEquals(expResult.toArray(), result.toArray());
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
        List<Useraccess> expResult = new ArrayList<>();
        List<Useraccess> result = null;
        try{
            result = manager.findUseraccessEntities(maxResults, firstResult);
        }catch(IllegalArgumentException ex){
            LOGGER.log(Level.INFO,ex.toString());
        }catch(Exception ex){
            fail(ex.toString());
        }
        assertEquals(expResult, result);
        Random rand = new Random();
        int count = rand.nextInt(MAX_ACCESSES)+10;
        List<Useraccess> values = createUserAccessList(count);
        for(firstResult = 1;firstResult<count - 2;firstResult++){
            for(maxResults = 1;maxResults<count - firstResult;maxResults++){
                expResult = values.subList(firstResult, firstResult+maxResults);
                result = manager.findUseraccessEntities(maxResults, firstResult);
                assertArrayEquals(expResult.toArray(), result.toArray());
            }
        }
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
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
        Random rand = new Random();
        expResult = rand.nextInt(MAX_ACCESSES)+10;
        createUserAccessList(expResult);
        result = manager.getUseraccessCount();
        assertEquals(expResult, result);
    }

/*    public class UserAccessManagerImpl implements UserAccessManager {

        public void create(Useraccess userAccess) throws PreexistingEntityException, Exception {
        }

        public void destroy(Integer id) throws NonexistentEntityException {
        }

        public void edit(Useraccess userAccess) throws NonexistentEntityException, Exception {
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

    private void clearDB() {
        EntityManagerFactory emf = DBUtils.getEntityManagerFactory();
        ConfigurationManager confManager = new ConfigurationJpaController(emf);
        UserManager userManager = new UserJpaController(emf);
        
        for(Useraccess access:manager.findUseraccessEntities()){
            try {
                manager.destroy(access.getId());
            } catch (cz.dcb.support.db.jpa.controllers.exceptions.NonexistentEntityException ex) {
                Logger.getLogger(UserAccessManagerTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        for(User user:userManager.findUserEntities()){
            try {
                userManager.destroy(user.getId());
            } catch (cz.dcb.support.db.jpa.controllers.exceptions.NonexistentEntityException ex) {
                Logger.getLogger(UserAccessManagerTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        for(Configuration conf:confManager.findConfigurationEntities()){
            try {
                confManager.destroy(conf.getId());
            } catch (cz.dcb.support.db.jpa.controllers.exceptions.NonexistentEntityException ex) {
                Logger.getLogger(UserAccessManagerTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }

    private List<Useraccess> createUserAccessList(int count) {
        List<Useraccess> values = new ArrayList<>();
        for(int i=0 ; i<count ; i++){
            Useraccess access = DBUtils.createUserAccess();
            manager.create(access);
            values.add(access);
        }
        return values;
    }

    private String createRole(int len) {
        Random rand = new Random();
        StringBuilder role = new StringBuilder();
        for(int i=0;i<len;i++){
            char c = (char) ('A'+(rand.nextInt('Z'-'A')));
            role.append(c);
        }
        return role.toString();
    }
}
