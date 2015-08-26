/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.dcb.support.db.managers;

import cz.dcb.support.db.jpa.controllers.SpzJpaController;
import cz.dcb.support.db.jpa.controllers.SpzManager;
import cz.dcb.support.db.jpa.entities.Spz;
import cz.dcb.support.db.managers.exceptions.IllegalOrphanException;
import cz.dcb.support.db.managers.exceptions.NonexistentEntityException;
import cz.dcb.support.db.managers.exceptions.PreexistingEntityException;
import cz.dcb.support.db.managers.utils.DBUtils;
import java.math.BigInteger;
import java.sql.SQLDataException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class SpzManagerTest {
    
    private final SpzManager manager = new SpzJpaController(DBUtils.getEntityManagerFactory());
    private static final Logger LOGGER=Logger.getLogger(SpzManagerTest.class.getName());
    private static final int MAX_SPZS = 95;
    
    public SpzManagerTest() {
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
     * Test of create method, of class SpzManager.
     */
    @Test
    public void testCreate() throws Exception {
        System.out.println("create");
        Spz spz = null;
//        SpzManager instance = new SpzManagerImpl();
        try{
            manager.create(spz);
            fail("Exception should be thrown.");
        }catch(IllegalArgumentException iae){
            LOGGER.log(Level.INFO,"Exception ",iae);
        }catch(Exception ex){
            fail("Invalid exception thrown " + ex);
        }
        spz = DBUtils.createSpz();
        manager.create(spz);
        assertNotNull(spz.getId());
        Spz result = manager.findSpz(spz.getId());
        assertEquals(spz, result);
    }

    
    /**
     * Test of edit method, of class SpzManager.
     */
    @Test
    public void testEdit() throws Exception {
        System.out.println("edit");
        Spz spz = null;
        //SpzManager instance = new SpzManagerImpl();
        try{
            manager.edit(spz);
            fail("Exception should be thrown.");
        }catch(IllegalArgumentException iae){
            LOGGER.log(Level.INFO,"Exception ",iae);
        }catch(Exception ex){
            fail("Unexpected exception trhown "+ex);
        }
        
        Random rand = new Random();
        int count = rand.nextInt(MAX_SPZS)+5;
        List<Spz> spzs = createSpzs(count);
        int idx = rand.nextInt(count);
        spz = spzs.get(idx);
        
        spz.setContactperson("Novy uzivatel 1");
         
        manager.edit(spz);
        
        Spz result = manager.findSpz(spz.getId());
        assertEquals(result, spz);
        
        Calendar cal = new GregorianCalendar();
        
        spz.setImplementationacceptdate(cal.getTime());
        checkResult(spz);
        
        spz.setIssuedate(cal.getTime());
        checkResult(spz);
        
        spz.setPriority(Short.MAX_VALUE);
        checkResult(spz);
        
        spz.setPriority(Short.MIN_VALUE);
        checkResult(spz);
        
        spz.setReqnumber("ZERO");
        checkResult(spz);
        
        spz.setRequestdescription("Testing request");
        checkResult(spz);
        
        spz.setRequesttype("Modifikace datovych struktur.");
        checkResult(spz);
        
        spz.setShortname("Nove kratke jmeno.");
        checkResult(spz);
        
        spz.setTs(new BigInteger("10000000000"));
        checkResult(spz);
    }

    @Test(expected = RollbackException.class)
    public void testContactPerson() throws Exception{
        Spz spz = DBUtils.createSpz();
        String validName = createString(32);
        spz.setContactperson(validName);
        manager.create(spz);
        String invalidName = createString(34);
        spz.setContactperson(invalidName);
        manager.edit(spz);
        fail("Exception should be thrown.");
    }
    
    @Test(expected = RollbackException.class) 
    public void testRequestType()throws Exception{
        Spz spz = DBUtils.createSpz();
        spz.setReqnumber(createNumber(10));
        manager.create(spz);
        spz.setReqnumber(createNumber(11));
        manager.edit(spz);
        fail("Invalid request number accepted."+spz.getReqnumber());
        
    }
    
    @Test(expected = RollbackException.class)
    public void testInvalidShortName()throws Exception{
        Spz spz = DBUtils.createSpz();
        spz.setShortname(createString(50));
        manager.create(spz);
        spz.setShortname(createString(51));
        manager.edit(spz);
        fail("Invalid short name accepted "+spz.getShortname());    
        
    }
    
   /* @Test(expected = RollbackException.class)
    public void testInvalidRequestDescription()throws Exception{
        Spz spz = DBUtils.createSpz();
        spz.setRequestdescription(createString(9000));
        manager.create(spz);
        spz.setRequestdescription(createString(9001));
        manager.edit(spz);
        fail("Invalid short name accepted "+spz.getShortname());    
        
    }*/
    
    @Test(expected = RollbackException.class)
    public void testInvalidRequestNumber()throws Exception{
        Spz spz = DBUtils.createSpz();
        spz.setReqnumber(createNumber(10));
        manager.create(spz);
        spz.setReqnumber(createNumber(11));
        manager.edit(spz);
        fail("Invalid short name accepted "+spz.getShortname());    
        
    }
    private void checkResult(Spz spz) throws Exception {
        manager.edit(spz);
        assertEquals(manager.findSpz(spz.getId()), spz);
    }

    /**
     * Test of findSpz method, of class SpzManager.
     */
    @Test
    public void testFindSpz() {
        System.out.println("findSpz");
        Integer id = null;
        //SpzManager instance = new SpzManagerImpl();
        Spz expResult = null,result=null;
        try{
            result = manager.findSpz(id);
            fail("Exception should be thrown.");
        }catch(IllegalArgumentException iae){
            LOGGER.log(Level.INFO,"Exception ",iae);
        }catch(Exception ex){
            fail("Unexpected exception "+ex);
        }
        assertEquals(expResult, result);
        Random rand = new Random();
        int count = rand.nextInt(MAX_SPZS)+5;
        List<Spz> spzs = createSpzs(count);
        for(Spz spz:manager.findSpzEntities()){
            result = manager.findSpz(spz.getId());
            assertEquals(spz, result);
        }
    }

    /**
     * Test of findSpzEntities method, of class SpzManager.
     */
    @Test
    public void testFindSpzEntities_0args() {
        System.out.println("findSpzEntities");
        //SpzManager instance = new SpzManagerImpl();
        List<Spz> expResult = new ArrayList<>();
        List<Spz> result = manager.findSpzEntities();
        assertEquals(expResult, result);
        Random rand = new Random();
        int count=rand.nextInt(MAX_SPZS);
        expResult = createSpzs(count);
        result = manager.findSpzEntities();
        assertArrayEquals(expResult.toArray(), result.toArray());
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of findSpzEntities method, of class SpzManager.
     */
    @Test
    public void testFindSpzEntities_int_int() {
        System.out.println("findSpzEntities");
        int maxResults = 0;
        int firstResult = 0;
        //SpzManager instance = new SpzManagerImpl();
        Random rand = new Random();
        int count = rand.nextInt(MAX_SPZS)+10;
        List<Spz> expResult = new ArrayList<>(),
                values = createSpzs(count),
                result = null;
                
        for(firstResult = 0;firstResult<count - 3;firstResult++){
            for(maxResults = 1;maxResults< count - firstResult;maxResults++){
                expResult = values.subList(firstResult, firstResult + maxResults);
                result = manager.findSpzEntities(maxResults, firstResult);
                assertArrayEquals(expResult.toArray(), result.toArray());
            }
        }
    }

    @Test
    public void testFindSpzEntities_int_intInvalidParams() {
        System.out.println("findSpzEntities_int_intInvalidParams");
        Random rand = new Random();
        int count = rand.nextInt(MAX_SPZS)+10;
        List<Spz> values= createSpzs(count);
        try{
            manager.findSpzEntities(-1, 0);
            fail("Exception should be thrown.");
        }catch(IllegalArgumentException iae){
            LOGGER.log(Level.INFO,"Exception ", iae);
        }catch(Exception ex){
            fail("Unexpected exception thrown "+ex);
        }
        
        try{
            manager.findSpzEntities(1, -1);
            fail("Exception should be thrown.");
        }catch(IllegalArgumentException iae){
            LOGGER.log(Level.INFO,"Exception ",iae);
        }catch(Exception ex){
            fail("Unexpected exception thrown " + ex);
        }
        
        List<Spz> result = manager.findSpzEntities(1, count+1),
                expResult = new ArrayList<>();
        assertEquals(expResult, result);
    }

    /**
     * Test of getSpzCount method, of class SpzManager.
     */
    @Test
    public void testGetSpzCount() {
        System.out.println("getSpzCount");
       // SpzManager instance = new SpzManagerImpl();
        Random rand = new Random();
        int expResult = 0;
        int result = manager.getSpzCount();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        expResult = rand.nextInt(MAX_SPZS)+10;
        createSpzs(expResult);
        result = manager.getSpzCount();
        assertEquals(expResult, result);
    }

   /* public class SpzManagerImpl implements SpzManager {

        public void create(Spz spz) throws PreexistingEntityException, Exception {
        }

        public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        }

        public void edit(Spz spz) throws IllegalOrphanException, NonexistentEntityException, Exception {
        }

        public Spz findSpz(Integer id) {
            return null;
        }

        public List<Spz> findSpzEntities() {
            return null;
        }

        public List<Spz> findSpzEntities(int maxResults, int firstResult) {
            return null;
        }

        public int getSpzCount() {
            return 0;
        }
    }*/
    
    private void clearDB() {
        for(Spz spz:manager.findSpzEntities()){
            try {
                manager.destroy(spz.getId());
            } catch (cz.dcb.support.db.jpa.controllers.exceptions.NonexistentEntityException ex) {
                Logger.getLogger(SpzManagerTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private List<Spz> createSpzs(int count) {
        List<Spz> values = new ArrayList<>();
        for(int i=0;i<count;i++){
            Spz spz = DBUtils.createSpz();
            manager.create(spz);
            values.add(spz);
        }
        return values;
    }

    private String createString(int length) {
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ abcdefghijklmnopqrstuvwxyz";
        StringBuilder result = new StringBuilder();
        Random rand = new Random();
        for(int i=0;i<length;i++){
            result.append(alphabet.charAt(rand.nextInt(alphabet.length())));
        }
        return result.toString();
        
    }

    private String createNumber(int length) {
        String alphabet="0123456789";
        StringBuilder result = new StringBuilder();
        Random rand = new Random();
        for(int i=0;i<length;i++){
            result.append(alphabet.charAt(rand.nextInt(alphabet.length())));
        }
        return result.toString();
    }
    
}
