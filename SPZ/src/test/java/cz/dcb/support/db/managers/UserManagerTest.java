/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.dcb.support.db.managers;

import cz.dcb.support.db.jpa.entities.User;
import cz.dcb.support.db.jpa.controllers.UserJpaController;
import cz.dcb.support.db.jpa.controllers.UserManager;

import cz.dcb.support.db.managers.utils.DBUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
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

    private UserManager manager = null;
    private static final Logger logger = Logger.getLogger(UserManagerTest.class.getName());
    private static final int MAX_USERS=95;
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
                manager.destroy(user.getId());
            } catch (cz.dcb.support.db.jpa.controllers.exceptions.NonexistentEntityException ex) {
                logger.log(Level.SEVERE, null, ex);
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
        User user = null;
        try{
            manager.create(user);
            fail("Null user created");
        }catch(Exception ex){
            
        }
        user = DBUtils.createUser();
        try{
            manager.create(user);
            User retValue = manager.findUser(user.getId());
            assertNotNull("User not found",retValue);
            assertEquals("User login differs.",retValue.getLogin(), user.getLogin());
        }catch(Exception ex){
            fail("Unexpected exception thrown: "+ex);
        }
//        manager.create(user);
//        fail("Duplicit user created");
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
            logger.log(Level.SEVERE,"Setting username to null.",ex);
        }
        user.setLogin(login);
        testCorrectEdits(user, login);
        
    }
    
    @Test
    public void testIncorrectEdits(){
        User user = DBUtils.createUser();
        try {
            manager.create(user);
        } catch (Exception ex) {
            fail("Unexpected exception: " + ex);
        }
        user.setName(null);
        try{
            manager.edit(user);
            User modified =manager.findUser(user.getId());
            
            assertEquals("Login differs.",user.getLogin(),modified.getLogin());
            assertEquals("Name differs.", user.getName(),modified.getName());
            assertNull("Name should be null",modified.getName());
        }catch(Exception ex){
            logger.log(Level.INFO,"Correct exception",ex);
        }
        
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
            User returned = manager.findUser(user.getId());
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
            logger.log(Level.INFO,"Correct exception thrown",ex);
        }
    }

    /**
     * Test of findUser method, of class UserManager.
     */
    @Test
    public void testFindUser() {
        //Logger logger = Logger.getLogger(UserAccessManagerTest.class.getName());
        try{
            User expResult = manager.findUser(null);
            fail("findUser accepts null parameter");
        }catch(NullPointerException | IllegalArgumentException ex){
            logger.log(Level.INFO,"findUser(null) je ok",ex);
        }catch(Exception ex){
            logger.log(Level.SEVERE,"findUser(null) vyhodilo neocekavanou vyjimku",ex);
            fail("vyhozena neocekavana vyjimka "+ex);
        }
        
        User expResult = null;
        User result = manager.findUser(-1);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
    
    @Test
    public void testFindExistingUser(){
        User user = DBUtils.createUser();
        try {
            manager.create(user);
            User found = manager.findUser(user.getId());
            assertEquals("Login differs", user.getLogin(),found.getLogin());
            assertEquals("Name differs",user.getName(),found.getName());
            assertEquals("E-mail differs",user.getEmail(), found.getEmail());
            assertEquals("Company differs",user.getCompany(), found.getCompany());
            assertEquals("Password differs",user.getPassword(), found.getPassword());
        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
        }
        
    }

    @Test
    public void testFindUserByLogin()throws Exception{
        Random rand = new Random();
        int count = rand.nextInt(MAX_USERS)+5;
        try{
            manager.findUserByLogin(null);
            fail("Exception should be thrown.");
        }catch(IllegalArgumentException iae){
            logger.log(Level.INFO,"",iae);
            
        }
        String login = new String();
        try{
                User result = manager.findUserByLogin(login);
                fail("Exceptin should be thrown - login is empty: "+login);
        }catch(IllegalArgumentException iae){
            logger.log(Level.INFO,"",iae);
        }
        
        List<User> users = createUsers(count);
        for(User user:users){
            User result = manager.findUserByLogin(user.getLogin());
            assertEquals(result, user);
        }
        User missed = DBUtils.createUser();
        missed.setLogin(missed.getLogin()+(count+3));
        User result = manager.findUserByLogin(missed.getLogin());
        assertNull(result);
    }
    /**
     * Test of findUserEntities method, of class UserManager.
     */
    @Test
    public void testFindUserEntities_0args() throws Exception {
        System.out.println("findUserEntities");
        Random rand = new Random();
        int count = rand.nextInt(MAX_USERS)+5;
        //UserManager instance = new UserManagerImpl();
        List<User> result = manager.findUserEntities();
        List<User> expResult = new ArrayList<>();
        assertEquals("List of users should be empty.",expResult, result);
        expResult = createUsers(count);
        result = manager.findUserEntities();
        assertEquals("nesouhlasi pocty prvku v kolekcich",expResult.size(), result.size());
        assertDeepEquals("Nesouhlasi kolekce",result,expResult);
        //assertEquals("Nesouhlasi zadana a vracena kolekce uzivatel",result,expResult);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    private List<User> createUsers(int count) {
        List<User> users = new ArrayList<>();
        for(int i=1;i<=10;i++){
            User user = DBUtils.createUser();
            user.setLogin(user.getLogin()+i);
            users.add(user);
            manager.create(user);
        }
        return users;
    }

    /**
     * Test of findUserEntities method, of class UserManager.
     */
    @Test
    public void testFindUserEntities_int_int() {
      //  Logger logger = Logger.getLogger(UserManagerTest.class.getName());
        int maxResults = 0;
        int firstResult = 0;
        //UserManager instance = new UserManagerImpl();
        List<User> expResult = null;
        try{
            List<User> result = manager.findUserEntities(maxResults, firstResult);
            assertEquals("Result should has 0 elements.",0,result.size());
        }catch(Exception ex){
            fail("Vyhozena vyjimka " + ex);
        }
    }
    @Test
    public void testFindUserEntitiesIntIntNonEmpty(){
        System.out.println("FindUserEntitiesIntIntNonEmpty");
        List<User> users = new ArrayList<>();
        for(int i=0;i<10;i++){
            try {
                User user = DBUtils.createUser();
                user.setLogin(user.getLogin()+i);
                manager.create(user);
                users.add(user);
            } catch (Exception ex) {
                logger.log(Level.SEVERE, "Unexpected exception thrown.", ex);
                fail("Unexpected exception thrown: "+ex);
            }
        }
        int size = users.size();
        for(int i=0;i<size;i++){
            List<User> result = manager.findUserEntities(size-i, i);
            logger.log(Level.INFO, result.toString());
            assertEquals("Wrong size of result.",users.size()-i,result.size());
            checkUserPresence(users,result,i);
        }
        
        //fail("Test prototype");   
    }

    /**
     * Test of getUserCount method, of class UserManager.
     */
    @Test
    public void testGetUserCount() throws Exception {
        int count = manager.getUserCount();
        assertEquals("User number does not match.",0, count);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
        User user = DBUtils.createUser();
        manager.create(user);
        List<User> users = new ArrayList<>();
        users.add(user);
        for(int i=1;i<10;i++){
            User userNext = DBUtils.createUser();
            userNext.setLogin(userNext.getLogin()+i);
        }
        int pomCount = users.size()-1;
        for(Iterator<User> it=users.iterator();it.hasNext();){
            User removeUser = it.next();
            manager.destroy(removeUser.getId());
            it.remove();
            assertEquals("User number does not match", users.size(),manager.getUserCount());
            assertTrue("User is not removed.",!manager.findUserEntities().contains(removeUser));
            pomCount--;
            
        }
        count = manager.getUserCount();
        assertEquals("User number does not match.",users.size(), count);
    }

    private void checkUserPresence(List<User> users, List<User> result,int first) {
       for(int i=first;i<users.size();i++){
           User user = users.get(i);
           assertTrue("user "+user.getLogin()+" is missing.",result.contains(user));
       }
    }

    private void assertDeepEquals(String msg, List<User> result, List<User> expResult) {
        for(User user:result){
            assertTrue(msg,expResult.contains(user));
        }
    }

/*    public class UserManagerImpl implements UserManager {

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
  */  
}
