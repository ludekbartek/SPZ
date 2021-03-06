/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.dcb.support.db.managers;

import cz.dcb.support.db.jpa.controllers.SpzJpaController;
import cz.dcb.support.db.jpa.controllers.SpzManager;
import cz.dcb.support.db.jpa.controllers.SpzNoteJpaController;
import cz.dcb.support.db.jpa.controllers.SpzNoteManager;
import cz.dcb.support.db.jpa.controllers.SpzStateJpaController;
import cz.dcb.support.db.jpa.controllers.SpzStateManager;
import cz.dcb.support.db.jpa.controllers.SpzStateNoteJpaController;
import cz.dcb.support.db.jpa.controllers.SpzStateNoteManager;
import cz.dcb.support.db.jpa.controllers.UserJpaController;
import cz.dcb.support.db.jpa.controllers.UserManager;
import cz.dcb.support.db.jpa.controllers.exceptions.NonexistentEntityException;
import cz.dcb.support.db.jpa.entities.Spz;
import cz.dcb.support.db.jpa.entities.Spznote;
import cz.dcb.support.db.jpa.entities.Spzstate;
import cz.dcb.support.db.jpa.entities.Spzstatenote;
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
public class SpzStateNoteManagerTest {
    
    private SpzStateNoteManager manager = new SpzStateNoteJpaController(DBUtils.getEntityManagerFactory());
    private static final Logger LOGGER = Logger.getLogger(SpzStateNoteManagerTest.class.getName());
    private static final int MAX_SPZ_STATE_NOTES=90;
    
    public SpzStateNoteManagerTest() {
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
     * Test of create method, of class SpzStateNoteManager.
     */
    @Test
    public void testCreate() {
        System.out.println("create");
        Spzstatenote spzstatenote = null;
        
        try{
            manager.create(spzstatenote);
            fail("Exception should be thrown.");
        }catch(IllegalArgumentException iae){
            LOGGER.log(Level.INFO,"Exception ", iae);
        }catch(Exception ex){
            fail("Unexpected exception thrown: "+ex);
        }
        
        spzstatenote = DBUtils.createSpzStateNote();
        try{
            manager.create(spzstatenote);
        }catch(Exception ex){
            fail("Unexpected exception trhown:"+ex);
        }
        assertNotNull(spzstatenote.getId());
        Spzstatenote result = manager.findSpzstatenote(spzstatenote.getId());
        assertEquals(spzstatenote, result);
    }

    /**
     * Test of destroy method, of class SpzStateNoteManager.
     */
    
    /**
     * Test of edit method, of class SpzStateNoteManager.
     */
    @Test
    public void testEdit() throws Exception {
        System.out.println("edit");
        
        Spzstatenote spzstatenote = null;
        EntityManagerFactory emf = DBUtils.getEntityManagerFactory();
        SpzNoteManager noteManager = new SpzNoteJpaController(emf);
        SpzStateManager stateManager = new SpzStateJpaController(emf);
        try{
            manager.edit(spzstatenote);
            fail("Exception should be thrown.");
        }catch(IllegalArgumentException iae){
            LOGGER.log(Level.INFO,"Exception: ", iae);
        }catch(Exception ex){
            fail("Unexpected exception thrown.");
        }
        Random rand = new Random();
        int count = rand.nextInt(MAX_SPZ_STATE_NOTES);
        List<Spzstatenote> spzStateNotes = createSpzStateNotes(count);
        for(int i=0;i<spzStateNotes.size();i++){
            Spzstatenote item = spzStateNotes.get(i);
            Spznote noteNew = DBUtils.createSpznote(stateManager.findSpzstate(item.getStateid()));
            noteManager.create(noteNew);
            item.setNoteid(noteNew.getId());
            manager.edit(item);
            Spzstatenote newItem = manager.findSpzstatenote(item.getId());
            assertEquals(item, newItem);
            Spzstate state = DBUtils.createSpzState();
            stateManager.create(state);
            item.setStateid(state.getId());
            manager.edit(item);
            newItem = manager.findSpzstatenote(item.getId());
            assertEquals(item, newItem);
            
        }
    }

    /**
     * Test of findSpzstatenote method, of class SpzStateNoteManager.
     */
    @Test
    public void testFindSpzstatenote() {
        System.out.println("findSpzstatenote");
        Integer id = null;
        //SpzStateNoteManager instance = new SpzStateNoteManagerImpl();
        Spzstatenote expResult = null;
        Spzstatenote result = null;
        try{
            manager.findSpzstatenote(id);
            fail("Exception should be trhown.");
        }catch(IllegalArgumentException ex){
            LOGGER.log(Level.INFO,"Exception: ",ex);
        }catch(Exception ex){
            fail("Unexpected exception thrown "+ex);
        }
        assertEquals(expResult, result);
        Random rand = new Random();
        int count = rand.nextInt(MAX_SPZ_STATE_NOTES)+10;
        List<Spzstatenote> notes = createSpzStateNotes(count);
        for(Spzstatenote note:notes){
            Spzstatenote found = manager.findSpzstatenote(note.getId());
            assertEquals(note, found);
        }
        Spzstatenote note = manager.findSpzstatenote(-1);
        assertNull(note);
        int maxId = Collections.max(notes,new Comparator<Spzstatenote>() {

            @Override
            public int compare(Spzstatenote o1, Spzstatenote o2) {
                return o1.getId()-o2.getId(); //To change body of generated methods, choose Tools | Templates.
            }
        }).getId()+1;
        note = manager.findSpzstatenote(maxId);
        assertNull(note);
    }

    /**
     * Test of findSpzstatenoteEntities method, of class SpzStateNoteManager.
     */
    @Test
    public void testFindSpzstatenoteEntities_0args() {
        System.out.println("findSpzstatenoteEntities");
        //SpzStateNoteManager instance = new SpzStateNoteManagerImpl();
        List<Spzstatenote> expResult = new ArrayList<>();
        List<Spzstatenote> result = manager.findSpzstatenoteEntities();
        assertEquals(expResult, result);
        Random rand = new Random();
        int count = rand.nextInt(MAX_SPZ_STATE_NOTES)+10;
        expResult = createSpzStateNotes(count);
        result = manager.findSpzstatenoteEntities();
        assertArrayEquals(expResult.toArray(), result.toArray());
    }

    /**
     * Test of findSpzstatenoteEntities method, of class SpzStateNoteManager.
     */
    @Test
    public void testFindSpzstatenoteEntities_int_int() {
        System.out.println("findSpzstatenoteEntities");
        int maxResults = 0;
        int firstResult = 0;
        List<Spzstatenote> expResult = new ArrayList<>();
        List<Spzstatenote> result = manager.findSpzstatenoteEntities(maxResults, firstResult);
        assertEquals(expResult, result);
        
        Random rand = new Random();
        int count = rand.nextInt(MAX_SPZ_STATE_NOTES)+10;
        List<Spzstatenote> values = createSpzStateNotes(count);
        for(maxResults = 1;maxResults<count*2/3; maxResults++){
            for(firstResult=0;firstResult<count-maxResults;firstResult++){
                expResult = values.subList(firstResult, firstResult+maxResults);
                result = manager.findSpzstatenoteEntities(maxResults, firstResult);
                assertArrayEquals(expResult.toArray(), result.toArray());
            }
        }
    }

    @Test
    public void testFindSpzstatenoteEntities_int_intInvalidIndices() {
        Random rand = new Random();
        int count = rand.nextInt(MAX_SPZ_STATE_NOTES)+10;
        List<Spzstatenote> values = createSpzStateNotes(count);
        List<Spzstatenote> expResult = null;
        List<Spzstatenote> result = null;
        try{
            result = manager.findSpzstatenoteEntities(-1, 1);
            fail("Exception should be thrown.");
        }catch(IllegalArgumentException iae){
            LOGGER.log(Level.INFO,"Exception ",iae);
        }catch(Exception ex){
            fail("Unexpected exception thrown "+ex);
        }
        assertEquals(expResult, result);
        try{
            result = manager.findSpzstatenoteEntities(1, -1);
            fail("Exception should be thrown");
        }catch(IllegalArgumentException iae){
            LOGGER.log(Level.INFO,"Exception ",iae);
        }catch(Exception ex){
            fail("Unexpected exception thrown "+ex);
        }
    }
    /**
     * Test of getEntityManager method, of class SpzStateNoteManager.
     */
  /*  @Test
    public void testGetEntityManager() {
        System.out.println("getEntityManager");
        SpzStateNoteManager instance = new SpzStateNoteManagerImpl();
        EntityManager expResult = null;
        EntityManager result = instance.getEntityManager();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
*/
    /**
     * Test of getSpzstatenoteCount method, of class SpzStateNoteManager.
     */
    @Test
    public void testGetSpzstatenoteCount() {
        System.out.println("getSpzstatenoteCount");
        
        int expResult = 0;
        int result = manager.getSpzstatenoteCount();
        assertEquals(expResult, result);
        
        Random rand = new Random();
        int count = rand.nextInt(MAX_SPZ_STATE_NOTES);
        List<Spzstatenote> values=createSpzStateNotes(count);
        expResult = values.size();
        result = manager.getSpzstatenoteCount();
        assertEquals(expResult, result);
        
    }

    private List<Spzstatenote> createSpzStateNotes(int count) {
        List<Spzstatenote> values = new ArrayList<>();
        for(int i=0;i<count;i++){
            Spzstatenote spzStateNote = DBUtils.createSpzStateNote();
            manager.create(spzStateNote);
            values.add(spzStateNote);
        }
        return values;
    }

    private void clearDB() {
        EntityManagerFactory emf = DBUtils.getEntityManagerFactory();
        SpzManager spzManager = new SpzJpaController(emf);
        SpzStateManager spzStateManger = new SpzStateJpaController(emf);
        SpzNoteManager spzNoteManager = new SpzNoteJpaController(emf);
        UserManager userManager = new UserJpaController(emf);
        
        for(Spz spz:spzManager.findSpzEntities()){
            try {
                manager.destroy(spz.getId());
            } catch (NonexistentEntityException ex) {
                Logger.getLogger(SpzStateNoteManagerTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        for(User user:userManager.findUserEntities()){
            try {
                userManager.destroy(user.getId());
            } catch (NonexistentEntityException ex) {
                Logger.getLogger(SpzStateNoteManagerTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        for(Spzstate state:spzStateManger.findSpzstateEntities()){
            try {
                spzStateManger.destroy(state.getId());
            } catch (NonexistentEntityException ex) {
                Logger.getLogger(SpzStateNoteManagerTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        for(Spznote note:spzNoteManager.findSpznoteEntities()){
            try {
                spzNoteManager.destroy(note.getId());
            } catch (NonexistentEntityException ex) {
                Logger.getLogger(SpzStateNoteManagerTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        for(Spzstatenote stateNote:manager.findSpzstatenoteEntities()){
            try {
                manager.destroy(stateNote.getId());
            } catch (NonexistentEntityException ex) {
                Logger.getLogger(SpzStateNoteManagerTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public class SpzStateNoteManagerImpl implements SpzStateNoteManager {

        public void create(Spzstatenote spzstatenote) {
        }

        public void destroy(Integer id) throws NonexistentEntityException {
        }

        public void edit(Spzstatenote spzstatenote) throws NonexistentEntityException, Exception {
        }

        public Spzstatenote findSpzstatenote(Integer id) {
            return null;
        }

        public List<Spzstatenote> findSpzstatenoteEntities() {
            return null;
        }

        public List<Spzstatenote> findSpzstatenoteEntities(int maxResults, int firstResult) {
            return null;
        }

        public EntityManager getEntityManager() {
            return null;
        }

        public int getSpzstatenoteCount() {
            return 0;
        }
    }
    
}
