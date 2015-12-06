/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.dcb.support.db.managers;

import java.util.logging.Logger;
import cz.dcb.support.db.jpa.controllers.SpzJpaController;
import cz.dcb.support.db.jpa.controllers.SpzManager;
import cz.dcb.support.db.jpa.controllers.SpzStateJpaController;
import cz.dcb.support.db.jpa.controllers.SpzStateManager;
import cz.dcb.support.db.jpa.controllers.SpzStatesJpaController;
import cz.dcb.support.db.jpa.controllers.SpzStatesManager;
import cz.dcb.support.db.jpa.controllers.exceptions.NonexistentEntityException;
import cz.dcb.support.db.jpa.entities.Spz;
import cz.dcb.support.db.jpa.entities.Spzstate;
import cz.dcb.support.db.jpa.entities.Spzstates;
import cz.dcb.support.db.managers.utils.DBUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
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
public class SpzStatesManagerTest {
    
    private final SpzStatesManager manager = new SpzStatesJpaController(DBUtils.getEntityManagerFactory());
    private static final Logger LOGGER = Logger.getLogger(SpzStatesManagerTest.class.getName());
    private static final int MAX_VALUES = 90;
    
    public SpzStatesManagerTest() {
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
     * Test of create method, of class SpzStatesManager.
     */
    @Test
    public void testCreate() {
        System.out.println("create");
        Spzstates spzStates = null;
        try{
            manager.create(spzStates);
            fail("Exception should be thrown.");
        }catch(IllegalArgumentException ex){
            LOGGER.log(Level.INFO, ex.toString());
        }catch(Exception ex){
            fail(ex.toString());
        }
        
        spzStates = DBUtils.createSpzStates();
        manager.create(spzStates);
        assertNotNull(spzStates.getId());
        Spzstates result = manager.findSpzstates(spzStates.getId());
        
        assertEquals(spzStates, result);
        
    }

    /**
     * Test of edit method, of class SpzStatesManager.
     */
    @Test
    public void testEdit() throws Exception {
        System.out.println("edit");
        Spzstates spzstates = null;
        try{
            manager.edit(spzstates);
            fail("Exception should be thrown");
        }catch(IllegalArgumentException ex){
            LOGGER.log(Level.INFO, ex.toString());
        }catch(Exception ex){
            fail(ex.toString());
        }
        
        spzstates = DBUtils.createSpzStates();
        manager.create(spzstates);
        
        Spz newSpz = DBUtils.createSpz();
        SpzManager spzManager = new SpzJpaController(DBUtils.getEntityManagerFactory());
        spzManager.create(newSpz);
        spzstates.setSpzid(newSpz.getId());
        
        manager.edit(spzstates);
        Spzstates result = manager.findSpzstates(spzstates.getId());
        
        assertEquals(spzstates, result);
        
        Spzstate state = DBUtils.createSpzState();
        SpzStateManager stateManager = new SpzStateJpaController(DBUtils.getEntityManagerFactory());
        stateManager.create(state);
        spzstates.setStateid(state.getId());
        
        result = manager.findSpzstates(spzstates.getId());
        assertEquals(result, spzstates);
    }

    /**
     * Test of findSpzstates method, of class SpzStatesManager.
     */
    @Test
    public void testFindSpzstates() {
        System.out.println("findSpzstates");
        Integer id = null;
        Spzstates expResult = null;
        Spzstates result = null;
        try{
            manager.findSpzstates(id);
            fail("Exception should be thrown.");
        }catch(IllegalArgumentException ex){
            LOGGER.log(Level.INFO, ex.toString());
        }catch(Exception ex){
            fail(ex.toString());
        }
        
        assertEquals(expResult, result);
            
        
        Random rand = new Random();
        int count = rand.nextInt(MAX_VALUES)+10;
        List<Spzstates> states = createSpzStates(count);
        for(Spzstates statesIt:manager.findSpzstatesEntities()){
            result = manager.findSpzstates(statesIt.getId());
            assertEquals(statesIt, result);
        }
        
        Comparator<Spzstates> comp = new Comparator<Spzstates>() {

            @Override
            public int compare(Spzstates o1, Spzstates o2) {
                return o1.getId() - o2.getId();
            }
        };
        int minId = Collections.min(states,comp).getId()-1;
        int maxId = Collections.max(states,comp).getId()+1;
        
        result = manager.findSpzstates(minId);
        assertNull(result);
        result = manager.findSpzstates(maxId);
        assertNull(result);
    }

    /**
     * Test of findSpzstatesEntities method, of class SpzStatesManager.
     */
    @Test
    public void testFindSpzstatesEntities_0args() {
        System.out.println("findSpzstatesEntities");
        List<Spzstates> expResult = new ArrayList<>();
        List<Spzstates> result = manager.findSpzstatesEntities();
        assertEquals(expResult, result);
        Random rand = new Random();
        int count = rand.nextInt(MAX_VALUES)+10;
        expResult = createSpzStates(count);
        result = manager.findSpzstatesEntities();
        assertArrayEquals(expResult.toArray(), result.toArray());
    }

    /**
     * Test of findSpzstatesEntities method, of class SpzStatesManager.
     */
    @Test
    public void testFindSpzstatesEntities_int_int() {
        System.out.println("findSpzstatesEntities");
        int maxResults = 0;
        int firstResult = 0;
        //SpzStatesManager instance = new SpzStatesManagerImpl();
        List<Spzstates> expResult = new ArrayList<>();
        List<Spzstates> result = manager.findSpzstatesEntities(maxResults, firstResult);
        assertEquals(expResult, result);
        Random rand = new Random();
        int count = rand.nextInt(MAX_VALUES);
        List<Spzstates> values = createSpzStates(count);
        for(firstResult=1;firstResult<count-2;firstResult++){
            for(maxResults=1;maxResults<count - firstResult;maxResults++){
                expResult = values.subList(firstResult, firstResult+maxResults);
                result = manager.findSpzstatesEntities(maxResults, firstResult);
                assertArrayEquals(expResult.toArray(), result.toArray());
            }
                
        }
    }
    
    @Test
    public void testFindSpzstatesEntities_int_intInvalidIndices() {
        System.out.println("findSpzstatesEntitiesInvalidIndices");
        Random rand = new Random();
        int count = rand.nextInt(MAX_VALUES)+10;
        createSpzStates(count);
        try{
            manager.findSpzstatesEntities(-1, 0);
            fail("Exception should be thrown.");
        }catch(IllegalArgumentException ex){
            LOGGER.log(Level.INFO, ex.toString());
        }catch(Exception ex){
            fail(ex.toString());
        }
        
        try{
            manager.findSpzstatesEntities(0, -1);
            fail("Exception should be thrown.");
        }catch(IllegalArgumentException ex){
            LOGGER.log(Level.INFO, ex.toString());
        }catch(Exception ex){
            fail(ex.toString());
        }
        List<Spzstates> result = manager.findSpzstatesEntities(1, count+1);
        assertEquals(result.size(),0);
    }
    /**
     * Test of getSpzstatesCount method, of class SpzStatesManager.
     */
    @Test
    public void testGetSpzstatesCount() {
        System.out.println("getSpzstatesCount");
        int expResult = 0;
        int result = manager.getSpzstatesCount();
        assertEquals(expResult, result);
        
        Random rand = new Random();
        int count = rand.nextInt(MAX_VALUES)+10;
        createSpzStates(count);
        assertEquals(count, manager.getSpzstatesCount());
    }

    @Test
    public void testFindSpzStates(){
        Spz spz = DBUtils.createSpz();
        Random rand = new Random();
        int count = rand.nextInt(15)+5;
        List<Spzstates> allStates = new ArrayList<>();
        List<Spzstates> states = createSpzStates(count,spz);
        allStates.addAll(states);
        allStates.addAll(createSpzStates(count));
        List<Spzstate> result = manager.findSpzstates(spz);
        assertArrayEquals(states.toArray(), result.toArray());
        allStates.removeAll(result);
        for(Spzstates remain:states){
            assertFalse(allStates.contains(remain));
        }
    }
    
    private void clearDB() {
        EntityManagerFactory emf = DBUtils.getEntityManagerFactory();
        SpzManager spzManager = new SpzJpaController(emf);
        SpzStateManager stateManager = new SpzStateJpaController(emf);
        
        for(Spzstates spzStates:manager.findSpzstatesEntities()){
            try {
                manager.destroy(spzStates.getId());
            } catch (NonexistentEntityException ex) {
                java.util.logging.Logger.getLogger(SpzStatesManagerTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        for(Spz spz:spzManager.findSpzEntities()){
            try {
                spzManager.destroy(spz.getId());
            } catch (NonexistentEntityException ex) {
                java.util.logging.Logger.getLogger(SpzStatesManagerTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        for(Spzstate state:stateManager.findSpzstateEntities()){
            try {
                stateManager.destroy(state.getId());
            } catch (NonexistentEntityException ex) {
                java.util.logging.Logger.getLogger(SpzStatesManagerTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

   
    private List<Spzstates> createSpzStates(int count) {
        List<Spzstates> values = new ArrayList<>();
        for(int i=0;i<count;i++){
            Spzstates value = DBUtils.createSpzStates();
            manager.create(value);
            values.add(value);
        }
        return values;
    }
    
    private List<Spzstates> createSpzStates(int count, Spz spz){
        List<Spzstates> values = new ArrayList<>();
        for(int i=0;i<count;i++){
            Spzstates value = DBUtils.createSpzStates(spz);
            manager.create(value);
            values.add(value);
        }
        return values;
    }

//    public class SpzStatesManagerImpl implements SpzStatesManager {
//
//        public void create(Spzstates spzstates) {
//        }
//
//        public void destroy(Integer id) throws NonexistentEntityException {
//        }
//
//        public void edit(Spzstates spzstates) throws NonexistentEntityException, Exception {
//        }
//
//        public Spzstates findSpzstates(Integer id) {
//            return null;
//        }
//
//        public List<Spzstates> findSpzstatesEntities() {
//            return null;
//        }
//
//        public List<Spzstates> findSpzstatesEntities(int maxResults, int firstResult) {
//            return null;
//        }
//
//        public EntityManager getEntityManager() {
//            return null;
//        }
//
//        public int getSpzstatesCount() {
//            return 0;
//        }
//    }
    
}
