/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.dcb.support.db.managers;

import cz.dcb.support.db.jpa.controllers.AttachmentNoteJpaController;
import cz.dcb.support.db.jpa.controllers.AttachmentNoteManager;
import cz.dcb.support.db.jpa.controllers.exceptions.NonexistentEntityException;
import cz.dcb.support.db.jpa.entities.Attachmentnote;
import cz.dcb.support.db.managers.utils.DBUtils;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
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
public class AttachmentNoteManagerTest {
    
    private final AttachmentNoteManager manager;
    
    public AttachmentNoteManagerTest() {
        manager = new AttachmentNoteJpaController(DBUtils.getEntityManagerFactory());
    }
    
    @BeforeClass
    public static void setUpClass() {
       
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
         for(Attachmentnote note:manager.findAttachmentnoteEntities()){
             try {
                 manager.destroy(note.getId());
             } catch (NonexistentEntityException ex) {
                 Logger.getLogger(AttachmentNoteManagerTest.class.getName()).log(Level.SEVERE, "No such id note.", ex);
             }
        }
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of create method, of class AttachmentNoteManager.
     */
    @Test
    public void testCreate() {
        System.out.println("create");
        Attachmentnote note = DBUtils.createAttachmentNote();
        manager.create(note);
        assertNotNull("Attachment note id is null.",note.getId());
        Attachmentnote dbNote = manager.findAttachmentnote(note.getId());
        assertEquals("Object in db doesn't match.",dbNote, note);
        
    }
    
    /**
     * Test of destroy method, of class AttachmentNoteManager.
     */
    @Test
    public void testDestroy() throws Exception {
    }

    /**
     * Test of edit method, of class AttachmentNoteManager.
     */
    @Test
    public void testEdit() throws Exception {
    }

    /**
     * Test of findAttachmentnote method, of class AttachmentNoteManager.
     */
    @Test
    public void testFindAttachmentnote() {
        System.out.println("findAttachmentnote");
        Integer id = null;
       // AttachmentNoteManager instance = new AttachmentNoteManagerImpl();
        Attachmentnote expResult = null;
        Attachmentnote result = null;
        try{
            result = manager.findAttachmentnote(id);
            fail("Exception should be thrown.");
        }catch(IllegalArgumentException iae){
            assertEquals("Result doesn't match.",expResult, result);
        }catch(Exception ex){
            fail("Wrong exception has been thrown: "+ ex);
        }
        
        //assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
        expResult = DBUtils.createAttachmentNote();
        manager.create(expResult);
        result = manager.findAttachmentnote(expResult.getId());
        assertEquals("Return entity doesn't match.",expResult, result);
    }

    /**
     * Test of findAttachmentnoteEntities method, of class AttachmentNoteManager.
     */
    @Test
    public void testFindAttachmentnoteEntities_0args() {
        System.out.println("findAttachmentnoteEntities");
     //   AttachmentNoteManager instance = new AttachmentNoteManagerImpl();
        List<Attachmentnote> result = manager.findAttachmentnoteEntities();
        List<Attachmentnote> expResult = new ArrayList<>();
        assertEquals("Function should return empty list.",expResult, result);
        Random rand = new Random();
        int count = rand.nextInt(100)+1;
        expResult = new ArrayList<>(createAttachmentNotes(count));
        result = null;
        result = manager.findAttachmentnoteEntities();
        assertEquals("Size doesn't match",expResult.size(), result.size());
        testElements(expResult,result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of findAttachmentnoteEntities method, of class AttachmentNoteManager.
     */
    @Test
    public void testFindAttachmentnoteEntities_int_int() {
        System.out.println("findAttachmentnoteEntities");
        int maxResults = 0;
        int firstResult = 0;
        AttachmentNoteManager instance = new AttachmentNoteManagerImpl();
        List<Attachmentnote> expResult = null;
        List<Attachmentnote> result = instance.findAttachmentnoteEntities(maxResults, firstResult);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAttachmentnoteCount method, of class AttachmentNoteManager.
     */
    @Test
    public void testGetAttachmentnoteCount() {
        System.out.println("getAttachmentnoteCount");
        int result = manager.getAttachmentnoteCount();
        assertEquals("Database should be emtpy",0, result);
        Random rand = new Random();
        int count = rand.nextInt(100)+1;
        Set<Attachmentnote> notes=createAttachmentNotes(count);
        result = manager.getAttachmentnoteCount();
        assertEquals("Count doesn't match.",count, result);
        // TODO review the generated test code and remove the default call to fail.
    }

    private Set<Attachmentnote> createAttachmentNotes(int count) {
        Set<Attachmentnote> notes = new HashSet<>();
        for(int i=0;i<count;i++){
            Attachmentnote  note = DBUtils.createAttachmentNote();
            notes.add(note);
            manager.create(note);
        }
        return  notes;
    }

    /**
     * Test of getEntityManager method, of class AttachmentNoteManager.
     */
    @Test
    public void testGetEntityManager() {
/*        System.out.println("getEntityManager");
        AttachmentNoteManager instance = new AttachmentNoteManagerImpl();
        EntityManager expResult = null;
        EntityManager result = instance.getEntityManager();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");*/
    }

    private void testElements(List<Attachmentnote> expResult, List<Attachmentnote> result) {
        for(Attachmentnote note:expResult){
            assertTrue("Element not found: "+note,result.contains(note));
        }
    }

    public class AttachmentNoteManagerImpl implements AttachmentNoteManager {

        public void create(Attachmentnote attachmentnote) {
        }

        public void destroy(Integer id) throws NonexistentEntityException {
        }

        public void edit(Attachmentnote attachmentnote) throws NonexistentEntityException, Exception {
        }

        public Attachmentnote findAttachmentnote(Integer id) {
            return null;
        }

        public List<Attachmentnote> findAttachmentnoteEntities() {
            return null;
        }

        public List<Attachmentnote> findAttachmentnoteEntities(int maxResults, int firstResult) {
            return null;
        }

        public int getAttachmentnoteCount() {
            return 0;
        }

        public EntityManager getEntityManager() {
            return null;
        }
    }
    
}
