/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.dcb.support.db.managers;

import cz.dcb.support.db.jpa.controllers.SpzJpaController;
import cz.dcb.support.db.jpa.controllers.SpzManager;
import cz.dcb.support.db.jpa.controllers.SpzStateJpaController;
import cz.dcb.support.db.jpa.controllers.SpzStateManager;
import cz.dcb.support.db.jpa.entities.Spzstate;
import cz.dcb.support.db.managers.exceptions.IllegalOrphanException;
import cz.dcb.support.db.managers.exceptions.NonexistentEntityException;
import cz.dcb.support.db.managers.exceptions.PreexistingEntityException;
import cz.dcb.support.db.managers.utils.DBUtils;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;
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
public class SpzstateManagerTest {
    
    private final SpzStateManager manager = new SpzStateJpaController(DBUtils.getEntityManagerFactory());
    private static final Logger LOGGER = Logger.getLogger(SpzstateManagerTest.class.getName());
    private static final int MAX_COUNT = 90;
    
    public SpzstateManagerTest() {
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
     * Test of create method, of class SpzstateManager.
     */
    @Test
    public void testCreate() throws Exception {
        System.out.println("create");
        Spzstate spzstate = null;
//        SpzstateManager instance = new SpzstateManagerImpl();
        try{
            manager.create(spzstate);
            fail("Exception should be trhown.");
        }catch(IllegalArgumentException ex){
            LOGGER.log(Level.INFO,ex.toString());
        }catch(Exception ex){
            fail("Invalid exception thrown "+ex);
        }
        Random rand = new Random();
        int count = rand.nextInt(MAX_COUNT)+10;
        List<Spzstate> states = createSpzstate(count);
        List<Spzstate> result = manager.findSpzstateEntities();
        assertArrayEquals(states.toArray(), result.toArray());
    }

    
    /**
     * Test of edit method, of class SpzstateManager.
     * @throws java.lang.Exception
     */
    @Test
    public void testEdit() throws Exception {
        System.out.println("edit");
        Spzstate spzstate = null;
        //SpzstateManager instance = new SpzstateManagerImpl();
        try{
            manager.edit(spzstate);
            fail("Exception should be thrown.");
        }catch(IllegalArgumentException ex){
            LOGGER.log(Level.INFO,ex.toString());
        }catch(Exception ex){
            fail(ex.toString());
        }
        // TODO review the generated test code and remove the default call to fail.
        Random rand = new Random();
        int count = rand.nextInt(MAX_COUNT);
        Calendar cal = new GregorianCalendar();
        
        List<Spzstate> values = createSpzstate(MAX_COUNT);
        for(Spzstate state:values){
            state.setAssumedmandays(3.0);
            state.setClasstype((short)2);
            state.setIdate(cal.getTime());
            manager.edit(state);
        }
        List<Spzstate> result = manager.findSpzstateEntities();
        assertArrayEquals(values.toArray(), result.toArray());
    }

    /**
     * Test of findSpzstate method, of class SpzstateManager.
     */
    @Test
    public void testFindSpzstate() {
        System.out.println("findSpzstate");
        Integer id = null;
        //SpzstateManager instance = new SpzstateManagerImpl();
        Spzstate expResult = null;
        Spzstate result = null;
        try{
            result = manager.findSpzstate(id);
            fail("Exception should be thrown.");
        }catch(IllegalArgumentException ex){
            LOGGER.log(Level.INFO,ex.toString());
        }catch(Exception ex){
            fail("Illegal exception thrown "+ex);
        }
        assertEquals(expResult, result);
        Random rand = new Random();
        int count = rand.nextInt(MAX_COUNT)+10;
        List<Spzstate> states = createSpzstate(count);
        for(Spzstate state:states){
            result = manager.findSpzstate(state.getId());
            assertEquals(result, state);
        }
        Comparator<Spzstate> comp = new Comparator<Spzstate>() {

            @Override
            public int compare(Spzstate o1, Spzstate o2) {
                return o1.getId()-o2.getId();
            }
        };
        int minId = Collections.min(states,comp).getId()-1;
        int maxId = Collections.max(states,comp).getId()+1;
        
        result = manager.findSpzstate(minId);
        assertNull(result);
        result = manager.findSpzstate(maxId);
        assertNull(result);
        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of findSpzstateEntities method, of class SpzstateManager.
     */
    @Test
    public void testFindSpzstateEntities_0args() {
        System.out.println("findSpzstateEntities");
        //SpzstateManager instance = new SpzstateManagerImpl();
        List<Spzstate> expResult = new ArrayList<>();
        List<Spzstate> result = manager.findSpzstateEntities();
        assertEquals(expResult, result);
        
        Random rand = new Random();
        int count = rand.nextInt(MAX_COUNT)+10;
        expResult = createSpzstate(count);
        result = manager.findSpzstateEntities();
        assertArrayEquals(expResult.toArray(), result.toArray());
    }

    /**
     * Test of findSpzstateEntities method, of class SpzstateManager.
     */
    @Test
    public void testFindSpzstateEntities_int_int() {
        System.out.println("findSpzstateEntities");
        int maxResults = 0;
        int firstResult = 0;
        //SpzstateManager instance = new SpzstateManagerImpl();
        List<Spzstate> expResult = new ArrayList<>();
        List<Spzstate> result = manager.findSpzstateEntities(maxResults, firstResult);
        assertEquals(expResult, result);
        Random rand = new Random();
        int count = rand.nextInt(MAX_COUNT)+10;
        List<Spzstate> values = createSpzstate(count);
        for(firstResult=1;firstResult<count - 2; firstResult++){
            for(maxResults=1;maxResults<count -firstResult;maxResults++){
                expResult = values.subList(firstResult, firstResult+maxResults);
                result = manager.findSpzstateEntities(maxResults, firstResult);
                assertArrayEquals(expResult.toArray(), result.toArray());
            }
        }
    }
    @Test
    public void testFindSpzstateEntities_int_intInvalidIndeces() {
        System.out.println("findSpzstateEntitiesInvalidIndeces");
        Random rand = new Random();
        int count = rand.nextInt(MAX_COUNT)+10;
        List<Spzstate> states = createSpzstate(count);
        List<Spzstate> result = null;
        try{
            manager.findSpzstateEntities(-1, 1);
            fail("Exception should be thrown.");
        }catch(IllegalArgumentException ex){
            LOGGER.log(Level.INFO,ex.toString());
        }catch(Exception ex){
            fail(ex.toString());
        }
        try{
            manager.findSpzstateEntities(1, -1);
            fail("Exception should be thrown.");
        }catch(IllegalArgumentException ex){
            LOGGER.log(Level.INFO,ex.toString());
        }catch(Exception ex){
            fail(ex.toString());
        }
        result = manager.findSpzstateEntities(1,count+1);
        assertEquals(result, new ArrayList<Spzstate>());
    }
    /**
     * Test of getSpzstateCount method, of class SpzstateManager.
     */
    @Test
    public void testGetSpzstateCount() {
        System.out.println("getSpzstateCount");
        //SpzstateManager instance = new SpzstateManagerImpl();
        int expResult = 0;
        int result = manager.getSpzstateCount();
        assertEquals(expResult, result);
        Random rand = new Random();
        int count = rand.nextInt(MAX_COUNT)+10;
        List<Spzstate> states = createSpzstate(count);
        assertEquals(states.size(), manager.getSpzstateCount());
    }
/*
    public class SpzstateManagerImpl implements SpzstateManager {

        public void create(Spzstate spzstate) throws PreexistingEntityException, Exception {
        }

        public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        }

        public void edit(Spzstate spzstate) throws IllegalOrphanException, NonexistentEntityException, Exception {
        }

        public Spzstate findSpzstate(Integer id) {
            return null;
        }

        public List<Spzstate> findSpzstateEntities() {
            return null;
        }

        public List<Spzstate> findSpzstateEntities(int maxResults, int firstResult) {
            return null;
        }

        public int getSpzstateCount() {
            return 0;
        }
    }
  */  

    private void clearDB() {
        EntityManagerFactory emf = DBUtils.getEntityManagerFactory();
        for(Spzstate state:manager.findSpzstateEntities()){
            try {
                manager.destroy(state.getId());
            } catch (cz.dcb.support.db.jpa.controllers.exceptions.NonexistentEntityException ex) {
                Logger.getLogger(SpzstateManagerTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }

    private List<Spzstate> createSpzstate(int count) {
        List<Spzstate> values = new ArrayList<>();
        for(int i=0;i<count;i++){
            Spzstate state = DBUtils.createSpzstate();
            manager.create(state);
            values.add(state);
        }
        return values;
    }
}
