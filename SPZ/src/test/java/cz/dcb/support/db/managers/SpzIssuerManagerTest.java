/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.dcb.support.db.managers;

import cz.dcb.support.db.jpa.controllers.SpzIssuerJpaController;
import cz.dcb.support.db.jpa.controllers.SpzIssuerManager;
import cz.dcb.support.db.jpa.controllers.SpzJpaController;
import cz.dcb.support.db.jpa.controllers.SpzManager;
import cz.dcb.support.db.jpa.controllers.UserJpaController;
import cz.dcb.support.db.jpa.controllers.UserManager;
import cz.dcb.support.db.jpa.controllers.exceptions.NonexistentEntityException;
import cz.dcb.support.db.jpa.entities.Spz;
import cz.dcb.support.db.jpa.entities.Spzissuer;
import cz.dcb.support.db.jpa.entities.User;
import cz.dcb.support.db.managers.utils.DBUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import java.util.List;
import java.util.Random;
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
public class SpzIssuerManagerTest {

    private final SpzIssuerManager manager = new SpzIssuerJpaController(DBUtils.getEntityManagerFactory());
    private static final Logger logger = Logger.getLogger(SpzIssuerManagerTest.class.getName());
    private static final int MAX_ISSUERS=90;
    
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
        clearDB();
    }
    
    @After
    public void tearDown() {
        clearDB();
    }

    /**
     * Test of create method, of class SpzIssuerManager.
     */
    @Test
    public void testCreate() {
        System.out.println("create");
        Spzissuer spzissuer = null, result = null;
        //SpzIssuerManager instance = new SpzIssuerManagerImpl();
        try{
            manager.create(spzissuer);
            fail("Created null spzissuer record.");
        }catch(IllegalArgumentException iae){
            logger.log(Level.INFO,"Exception ", iae);
        }catch(Exception ex){
            fail("Invalid exception thrown.");
        }
        
        spzissuer = DBUtils.createSpzIssuer();
        try{
            manager.create(spzissuer);
        }catch(Exception ex){
            fail("Unexpected exception "+ex);
        }
        result = manager.findSpzissuer(spzissuer.getId());
        assertEquals(spzissuer, result);
    }

    /**
     * Test of edit method, of class SpzIssuerManager.
     */
    @Test
    public void testEdit() throws Exception {
        EntityManagerFactory emf = DBUtils.getEntityManagerFactory();
        UserManager userMan = new UserJpaController(emf);
        SpzManager spzMan = new SpzJpaController(emf);
        
        System.out.println("edit");
        Random rand = new Random();
        int count = rand.nextInt(MAX_ISSUERS)+5;
        List<Spzissuer> issuers = createSpzIssuers(count);
        int idx=rand.nextInt(count);
        Spzissuer toChange = issuers.get(idx);
        
        Spz spz=DBUtils.createSpz();
        spzMan.create(spz);
        User user=DBUtils.createUser();
        userMan.create(user);
        
        toChange.setSpzid(spz.getId());
        toChange.setUserid(user.getId());
        
        List<Spzissuer> result = manager.findSpzissuerEntities();
        
        assertArrayEquals(issuers.toArray(), result.toArray());
        
    }

    @Test
    public void testEditNull() throws Exception {
        try{
            manager.edit(null);
            fail("Exception should be thrown.");
        }catch(IllegalArgumentException iae){
            logger.log(Level.INFO,"Exception ",iae);
        }catch(Exception ex){
            fail("Unexpected exception thrown "+ex);
        }
    }
    /**
     * Test of findSpzissuer method, of class SpzIssuerManager.
     */
    @Test
    public void testFindSpzissuer() {
        System.out.println("findSpzissuer");
        Random rand = new Random();
        int count = rand.nextInt(MAX_ISSUERS)+10;
        List<Spzissuer> issuers = createSpzIssuers(count);
        for(Spzissuer issuer:issuers){
            Spzissuer result = manager.findSpzissuer(issuer.getId());
            assertEquals(issuer, result);
        }
        
    }
    
    @Test
    public void testFindSpzissuerWrongId(){
        System.out.println("findSpzissuerWrongId");
        Random rand = new Random();
        int count = rand.nextInt(MAX_ISSUERS)+5;
        
        Spzissuer result = null;
        try{
            result = manager.findSpzissuer(-1);
            assertNull(result);
        }catch(IllegalArgumentException iae){
            logger.log(Level.INFO,"Eception ",iae);
        }catch(Exception ex){
            fail("Wrong exception has been thrown "+ex);
        }
        createSpzIssuers(count);
        List<Spzissuer> issuers = manager.findSpzissuerEntities();
        int maxId = Collections.max(issuers, new Comparator<Spzissuer>(){

            @Override
            public int compare(Spzissuer o1, Spzissuer o2) {
                return o1.getId()-o2.getId();
            }
        
        }).getId();
        result = manager.findSpzissuer(maxId+10);
        assertNull(result);
    }

    /**
     * Test of findSpzissuerEntities method, of class SpzIssuerManager.
     */
    @Test
    public void testFindSpzissuerEntities_0args() {
        System.out.println("findSpzissuerEntities");
        List<Spzissuer> expResult = new ArrayList<>();
        List<Spzissuer> result = manager.findSpzissuerEntities();
        assertEquals(expResult, result);
        
        Random rand = new Random();
        int count = rand.nextInt(MAX_ISSUERS)+3;
        expResult = createSpzIssuers(count);
        result = manager.findSpzissuerEntities();
        assertArrayEquals(expResult.toArray(), result.toArray());
    }

    /**
     * Test of findSpzissuerEntities method, of class SpzIssuerManager.
     */
    @Test
    public void testFindSpzissuerEntities_int_int() {
        System.out.println("findSpzissuerEntities");
        int maxResults = 0;
        int firstResult = 0;
        //SpzIssuerManager instance = manager;
        List<Spzissuer> expResult = new ArrayList<>(), values = null;
        List<Spzissuer> result = manager.findSpzissuerEntities(maxResults, firstResult);
        assertEquals(expResult, result);
        
        Random rand = new Random();
        int count = rand.nextInt(MAX_ISSUERS)+5;
        values = createSpzIssuers(count);
        for(firstResult=0;firstResult < count -3;firstResult++)
        {
            for(maxResults=1;maxResults < count - firstResult;maxResults++){
                expResult = values.subList(firstResult, firstResult+maxResults);
                result = manager.findSpzissuerEntities(maxResults, firstResult);
                assertArrayEquals(expResult.toArray(), result.toArray());
            }
        }
    }
    
    @Test
    public void testFindSpzissuerEntities_int_intWrongParams() {
        System.out.println("findSpzissuerEntitiesWrongParams");
        Random rand = new Random();
        int count = rand.nextInt(MAX_ISSUERS)+5;
        List<Spzissuer> values = createSpzIssuers(count);
        List<Spzissuer> expResult = null;
        List<Spzissuer> result = null;
        try{
            result = manager.findSpzissuerEntities(-1, 1);
            fail("Exception should be thrown.");
        }catch(IllegalArgumentException iae){
            logger.log(Level.INFO,"Exception ",iae);
        }catch(Exception ex){
            fail("Invalid excepiton thrown "+ex);
        }
        assertEquals(expResult, result);
        try{
            result = manager.findSpzissuerEntities(1, -1);
            fail("Exception should be thrown.");
        }catch(IllegalArgumentException iae){
            logger.log(Level.INFO,"Exception should be thrown.",iae);
        }catch(Exception ex){
            fail("Wrong exception thrown "+ex);
        }
        assertEquals(expResult, result);
        expResult = new ArrayList<>();
        result = manager.findSpzissuerEntities(1, count+1);
        assertEquals(expResult, result);
    }

    /**
     * Test of getSpzissuerCount method, of class SpzIssuerManager.
     */
    @Test
    public void testGetSpzissuerCount() {
        System.out.println("getSpzissuerCount");
        
        int expResult = 0;
        int result = manager.getSpzissuerCount();
        assertEquals(expResult, result);
        
        Random rand = new Random();
        expResult = rand.nextInt(MAX_ISSUERS)+5;
        createSpzIssuers(expResult);
        result = manager.getSpzissuerCount();
        assertEquals(expResult, result);
    }

    private void clearDB() {
        EntityManagerFactory emf = DBUtils.getEntityManagerFactory();
        SpzManager spzManager = new SpzJpaController(emf);
        UserManager userManager = new UserJpaController(emf);
        
        for(Spzissuer issuer:manager.findSpzissuerEntities()){
            try {
                manager.destroy(issuer.getId());
            } catch (NonexistentEntityException ex) {
                Logger.getLogger(SpzIssuerManagerTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        for(Spz spz:spzManager.findSpzEntities()){
            try {
                spzManager.destroy(spz.getId());
            } catch (NonexistentEntityException ex) {
                Logger.getLogger(SpzIssuerManagerTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        for(User user:userManager.findUserEntities()){
            try {
                userManager.destroy(user.getId());
            } catch (NonexistentEntityException ex) {
                Logger.getLogger(SpzIssuerManagerTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }

    private List<Spzissuer> createSpzIssuers(int count) {
        List<Spzissuer> issuers = new ArrayList<>();
        for(int i=0;i<count;i++){
            Spzissuer issuer = DBUtils.createSpzIssuer();
            manager.create(issuer);
            issuers.add(issuer);
        }
        return issuers;
    }
}
