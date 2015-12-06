/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.dcb.support.db.managers;

import cz.dcb.support.db.jpa.controllers.SpzNoteJpaController;
import cz.dcb.support.db.jpa.controllers.SpzNoteManager;
import cz.dcb.support.db.jpa.controllers.SpzStateJpaController;
import cz.dcb.support.db.jpa.controllers.SpzStateManager;
import cz.dcb.support.db.jpa.entities.Spznote;
import cz.dcb.support.db.jpa.entities.Spzstate;
import cz.dcb.support.db.managers.exceptions.IllegalOrphanException;
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
public class SpznoteManagerTest {
    private final SpzNoteManager manager = new SpzNoteJpaController(DBUtils.getEntityManagerFactory());
    private static final Logger LOGGER = Logger.getLogger(SpznoteManagerTest.class.getName());
    private static final int MAX_SPZ_NOTES = 90;
    public SpznoteManagerTest() {
        
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

    private void clearDB() {
        for(Spznote note:manager.findSpznoteEntities()){
            try {
                manager.destroy(note.getId());
            } catch (cz.dcb.support.db.jpa.controllers.exceptions.NonexistentEntityException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        }
    }
    
    @After
    public void tearDown() {
        clearDB();
    }

    /**
     * Test of create method, of class SpznoteManager.
     */
    @Test
    public void testCreate() throws Exception {
        System.out.println("create");
        Spznote spznote = null;
        try{
            manager.create(spznote);
            fail("Exception should be thrown.");
        }catch(IllegalArgumentException iae){
            LOGGER.log(Level.INFO,"Exception ", iae);
        }catch(Exception ex){
            fail("Unexpected exception "+ex);
        }

        Spzstate spzstate = DBUtils.createSpzState();
        spznote = DBUtils.createSpznote(spzstate);
        manager.create(spznote);
        Spznote result = manager.findSpznote(spznote.getId());
        assertEquals(spznote, result);
    }

   
    /**
     * Test of edit method, of class SpznoteManager.
     */
    @Test
    public void testEdit() throws Exception {
        Random rand = new Random();
        int count = rand.nextInt(MAX_SPZ_NOTES)+10;
        List<Spznote> notes = createSpzNotes(count);
        int number = 1;
        for(Spznote note:notes){
            note.setNotetext("Note text "+number);
            number++;
            manager.edit(note);
            Spznote result = manager.findSpznote(note.getId());
            assertEquals(note, result);
        }
    }

    @Test
    public void testEditInvalidID() throws Exception{
        int minId = -1;
        Spzstate state = DBUtils.createSpzState();
        Spznote note = DBUtils.createSpznote(state);
        note.setId(minId);
        manager.edit(note);
        Spznote result = manager.findSpznote(minId);
        assertNull(result);
        int maxId = Collections.max(manager.findSpznoteEntities(), new Comparator<Spznote>() {

            @Override
            public int compare(Spznote o1, Spznote o2) {
                return o1.getId()-o2.getId();
            }
        }).getId();
        note.setId(maxId+1);
        manager.edit(note);
        result = manager.findSpznote(maxId+1);
        LOGGER.log(Level.INFO,"result = ",result);
        assertEquals(note, result);
        
    }
    @Test
    public void testSpznoteEditInvalidNotetext(){
        Spzstate state = DBUtils.createSpzState();
        Spznote note = DBUtils.createSpznote(state);
        String text = createRandomText(8000);
        note.setNotetext(text);
        manager.create(note);
        Spznote result = manager.findSpznote(note.getId());
        assertEquals(note.getNotetext(), result.getNotetext());
        note.setNotetext(createRandomText(8001));
        try {
            manager.edit(note);
        } catch (Exception ex) {
            Logger.getLogger(SpznoteManagerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * Test of findSpznote method, of class SpznoteManager.
     */
    @Test
    public void testFindSpznote() {
        System.out.println("findSpznote");
        Integer id = null;
        Spznote expResult = null, result = null;
        try{
            result = manager.findSpznote(id);
            fail("Exception should be thrown.");
        }catch(IllegalArgumentException ex){
            LOGGER.log(Level.INFO,"Exception ",ex);
        }catch(Exception ex){
            fail("Unexpected exception thrown: "+ex);
        }
        assertEquals(expResult, result);
        Random rand = new Random();
        int count = rand.nextInt(MAX_SPZ_NOTES)+10;
        List<Spznote> notes = createSpzNotes(count);
        for(Spznote note:notes){
            result = manager.findSpznote(note.getId());
            assertEquals(note, result);
        }
    }
    
    @Test
    public void testFindNonExistingSpznote(){
        Comparator<Spznote> noteComparator = new Comparator<Spznote>() {

            @Override
            public int compare(Spznote o1, Spznote o2) {
                return o1.getId()-o2.getId();
            }
        };
        System.out.println("findnonExistingSpznote");
        Random rand = new Random();
        int count = rand.nextInt(MAX_SPZ_NOTES)+10;
        List<Spznote> values = createSpzNotes(count);
        int min = Collections.min(values,noteComparator).getId(),
            max = Collections.max(values,noteComparator).getId();
        Spznote result = manager.findSpznote(min-1);
        assertNull(result);
        result = manager.findSpznote(max+1);
        assertNull(result);
    }

    /**
     * Test of findSpznoteEntities method, of class SpznoteManager.
     */
    @Test
    public void testFindSpznoteEntities_0args() {
        System.out.println("findSpznoteEntities");
        Random rand = new Random();
        int count = rand.nextInt(MAX_SPZ_NOTES)+10;
        List<Spznote> expResult = new ArrayList<>();
        List<Spznote> result = manager.findSpznoteEntities();
        assertEquals(expResult, result);
        expResult = createSpzNotes(count);
        result = manager.findSpznoteEntities();
        assertArrayEquals(expResult.toArray(), result.toArray());
    }

    /**
     * Test of findSpznoteEntities method, of class SpznoteManager.
     */
    @Test
    public void testFindSpznoteEntities_int_int() {
        System.out.println("findSpznoteEntities");
        int maxResults = 0;
        int firstResult = 0;
        List<Spznote> expResult = new ArrayList<>();
        List<Spznote> result = manager.findSpznoteEntities(maxResults, firstResult);
        assertEquals(expResult, result);
        Random rand = new Random();
        int count = rand.nextInt(MAX_SPZ_NOTES)+10;
        List<Spznote> values = createSpzNotes(count);
        for(firstResult=1;firstResult<count;firstResult++){
            for(maxResults=1;maxResults<=count - firstResult;maxResults++){
                expResult = values.subList(firstResult, firstResult+maxResults);
                result = manager.findSpznoteEntities(maxResults, firstResult);
                assertArrayEquals(expResult.toArray(), result.toArray());
            }
        }
    }

    /**
     * Test of getSpznoteCount method, of class SpznoteManager.
     */
    @Test
    public void testGetSpznoteCount() {
        System.out.println("getSpznoteCount");
        int expResult = 0;
        int result = manager.getSpznoteCount();
        assertEquals(expResult, result);
        int count;
        Random rand = new Random();
        count = rand.nextInt(MAX_SPZ_NOTES)+10;
        List<Spznote> notes = createSpzNotes(count);
        expResult = notes.size();
        result = manager.getSpznoteCount();
        assertEquals(expResult, result);
    }

    /*public class SpznoteManagerImpl implements SpznoteManager {

        public void create(Spznote spznote) throws PreexistingEntityException, Exception {
        }

        public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        }

        public void edit(Spznote spznote) throws IllegalOrphanException, NonexistentEntityException, Exception {
        }

        public Spznote findSpznote(Integer id) {
            return null;
        }

        public List<Spznote> findSpznoteEntities() {
            return null;
        }

        public List<Spznote> findSpznoteEntities(int maxResults, int firstResult) {
            return null;
        }

        public int getSpznoteCount() {
            return 0;
        }
    }*/

    private List<Spznote> createSpzNotes(int count) {
        List<Spznote> notes = new ArrayList<>();
        EntityManagerFactory emf = DBUtils.getEntityManagerFactory();
        SpzStateManager stateManager = new SpzStateJpaController(emf);
        
        for(int i=0;i<count;i++){
            Spzstate state = DBUtils.createSpzState();
            stateManager.create(state);
            Spznote note = DBUtils.createSpznote(state);
            manager.create(note);
            notes.add(note);
        }
        return notes;
    }

    private String createRandomText(int len) {
        Random rand = new Random();
        StringBuilder builder = new StringBuilder();
        for(int i=0;i<len;i++){
            char c ;
            if(rand.nextBoolean()){
                c = (char)(rand.nextBoolean()?rand.nextInt('Z'-'A')+'A':rand.nextInt('z'-'a')+'a');
            }else{
                c=' ';
            }
            builder.append(c);
            
        }
        return builder.toString();
    }
    
}
