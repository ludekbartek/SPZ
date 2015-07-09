/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.dcb.support.db.managers;

import cz.dcb.support.db.jpa.Attachment;
import cz.dcb.support.db.jpa.Spznote;
import cz.dcb.support.db.managers.exceptions.NonexistentEntityException;
import cz.dcb.support.db.managers.exceptions.PreexistingEntityException;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Cache;
import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnitUtil;
import javax.persistence.Query;
import javax.persistence.SynchronizationType;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.metamodel.Metamodel;
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
public class AttachmentManagerTest {
    private EntityManagerFactory emf = null;
    
    public AttachmentManagerTest() {
        emf = Persistence.createEntityManagerFactory("support_JPA");
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
     * Test of create method, of class AttachmentManager.
     */
    @Test
    public void testCreate() throws Exception {
        System.out.println("create");
        Attachment attachment = null;
        AttachmentManager instance = new AttachmentJpaController(emf);
        try{
            instance.create(attachment);
            fail("Created null Attachment");
        }catch(NullPointerException ex){
            Logger.getLogger(AttachmentJpaController.class.getName()).log(Level.INFO,"Creating null attachment ok");
        }catch(Exception ex1){
            fail("Wrong exception thrown: "+ ex1);
        }
        attachment = new Attachment();
        attachment.setContent("Some content.");
        attachment.setDate(new GregorianCalendar().getTime());
        attachment.setLocation("Some location");
        attachment.setSpznoteId(new Spznote());
        instance.create(attachment);
        checkExistance(instance,attachment);
        // TODO review the generated test code and remove the default call to fail.
    //    fail("The test case is a prototype.");
    }

    /**
     * Test of destroy method, of class AttachmentManager.
     */
    @Test
    public void testDestroy() throws Exception {
        System.out.println("destroy");
        Integer id = null;
        AttachmentManager instance = new AttachmentManagerImpl();
        instance.destroy(id);
        // TODO review the generated test code and remove the default call to fail.
      //  fail("The test case is a prototype.");
    }

    /**
     * Test of edit method, of class AttachmentManager.
     */
    @Test
    public void testEdit() throws Exception {
        System.out.println("edit");
        Attachment attachment = null;
        AttachmentManager instance = new AttachmentManagerImpl();
        instance.edit(attachment);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of findAttachment method, of class AttachmentManager.
     */
    @Test
    public void testFindAttachment() {
        System.out.println("findAttachment");
        Integer id = null;
        AttachmentManager instance = new AttachmentManagerImpl();
        Attachment expResult = null;
        Attachment result = instance.findAttachment(id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of findAttachmentEntities method, of class AttachmentManager.
     */
    @Test
    public void testFindAttachmentEntities_0args() {
        System.out.println("findAttachmentEntities");
        AttachmentManager instance = new AttachmentManagerImpl();
        List<Attachment> expResult = null;
        List<Attachment> result = instance.findAttachmentEntities();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
       // fail("The test case is a prototype.");
    }

    /**
     * Test of findAttachmentEntities method, of class AttachmentManager.
     */
    @Test
    public void testFindAttachmentEntities_int_int() {
        System.out.println("findAttachmentEntities");
        int maxResults = 0;
        int firstResult = 0;
        AttachmentManager instance = new AttachmentManagerImpl();
        List<Attachment> expResult = null;
        List<Attachment> result = instance.findAttachmentEntities(maxResults, firstResult);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getAttachmentCount method, of class AttachmentManager.
     */
    @Test
    public void testGetAttachmentCount() {
        System.out.println("getAttachmentCount");
        AttachmentManager instance = new AttachmentManagerImpl();
        int expResult = 0;
        int result = instance.getAttachmentCount();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    private void checkExistance(AttachmentManager instance, Attachment expected) {
        Attachment result = instance.findAttachment(expected.getId());
        assertEquals("Retrieved data differs.",result,expected);
    }

    public class AttachmentManagerImpl implements AttachmentManager {

        public void create(Attachment attachment) throws PreexistingEntityException, Exception {
        }

        public void destroy(Integer id) throws NonexistentEntityException {
        }

        public void edit(Attachment attachment) throws NonexistentEntityException, Exception {
        }

        public Attachment findAttachment(Integer id) {
            return null;
        }

        public List<Attachment> findAttachmentEntities() {
            return null;
        }

        public List<Attachment> findAttachmentEntities(int maxResults, int firstResult) {
            return null;
        }

        public int getAttachmentCount() {
            return 0;
        }
    }
    
}
