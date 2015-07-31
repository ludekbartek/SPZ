/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.dcb.support.db.managers;

import cz.dcb.support.db.jpa.controllers.AttachmentNoteManager;
import cz.dcb.support.db.jpa.controllers.exceptions.NonexistentEntityException;
import cz.dcb.support.db.jpa.entities.Attachmentnote;
import java.util.List;
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
    
    public AttachmentNoteManagerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
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
        Attachmentnote attachmentnote = null;
        AttachmentNoteManager instance = new AttachmentNoteManagerImpl();
        instance.create(attachmentnote);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of destroy method, of class AttachmentNoteManager.
     */
    @Test
    public void testDestroy() throws Exception {
        System.out.println("destroy");
        Integer id = null;
        AttachmentNoteManager instance = new AttachmentNoteManagerImpl();
        instance.destroy(id);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of edit method, of class AttachmentNoteManager.
     */
    @Test
    public void testEdit() throws Exception {
        System.out.println("edit");
        Attachmentnote attachmentnote = null;
        AttachmentNoteManager instance = new AttachmentNoteManagerImpl();
        instance.edit(attachmentnote);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findAttachmentnote method, of class AttachmentNoteManager.
     */
    @Test
    public void testFindAttachmentnote() {
        System.out.println("findAttachmentnote");
        Integer id = null;
        AttachmentNoteManager instance = new AttachmentNoteManagerImpl();
        Attachmentnote expResult = null;
        Attachmentnote result = instance.findAttachmentnote(id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findAttachmentnoteEntities method, of class AttachmentNoteManager.
     */
    @Test
    public void testFindAttachmentnoteEntities_0args() {
        System.out.println("findAttachmentnoteEntities");
        AttachmentNoteManager instance = new AttachmentNoteManagerImpl();
        List<Attachmentnote> expResult = null;
        List<Attachmentnote> result = instance.findAttachmentnoteEntities();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
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
        AttachmentNoteManager instance = new AttachmentNoteManagerImpl();
        int expResult = 0;
        int result = instance.getAttachmentnoteCount();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getEntityManager method, of class AttachmentNoteManager.
     */
    @Test
    public void testGetEntityManager() {
        System.out.println("getEntityManager");
        AttachmentNoteManager instance = new AttachmentNoteManagerImpl();
        EntityManager expResult = null;
        EntityManager result = instance.getEntityManager();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
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
