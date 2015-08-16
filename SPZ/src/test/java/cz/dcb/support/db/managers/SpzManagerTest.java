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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
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

    @Test
    public void testInvalidEdits() throws Exception{
        fail("Test case prototype");
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
        Spz expResult = null;
        Spz result = manager.findSpz(id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findSpzEntities method, of class SpzManager.
     */
    @Test
    public void testFindSpzEntities_0args() {
        System.out.println("findSpzEntities");
        //SpzManager instance = new SpzManagerImpl();
        List<Spz> expResult = null;
        List<Spz> result = manager.findSpzEntities();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
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
        List<Spz> expResult = null;
        List<Spz> result = manager.findSpzEntities(maxResults, firstResult);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSpzCount method, of class SpzManager.
     */
    @Test
    public void testGetSpzCount() {
        System.out.println("getSpzCount");
       // SpzManager instance = new SpzManagerImpl();
        int expResult = 0;
        int result = manager.getSpzCount();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
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
    
}
