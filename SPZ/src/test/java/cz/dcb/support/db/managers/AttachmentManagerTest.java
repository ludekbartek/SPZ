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
import cz.dcb.support.db.managers.utils.DBUtils;
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
public class AttachmentManagerTest {
    private EntityManagerFactory emf = null;
    
    public AttachmentManagerTest() {
        emf = DBUtils.getEntityManagerFactory();
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
        testCreateNullAttachment();
        testValidAttachment();
        testNullDateAttachment();
    }

    private void testValidAttachment() throws Exception {
        AttachmentManager instance = new AttachmentJpaController(emf);
        Attachment attachment = newAttachment();
        instance.create(attachment);
        checkExistance(instance,attachment);
    }

    private Attachment newAttachment() {
        Attachment attachment = new Attachment();
        attachment.setContent("Some content.");
        attachment.setDate(new GregorianCalendar().getTime());
        attachment.setLocation("Some location");
        attachment.setSpznoteId(new Spznote());
        return attachment;
    }

    private void testCreateNullAttachment() {
        Attachment attachment = newAttachment();
        try{
            attachment.setDate(null);
            fail("Created null date Attachment");
        }catch(NullPointerException ex){
            Logger.getLogger(AttachmentJpaController.class.getName()).log(Level.INFO,"Creating null attachment ok");
        }catch(Exception ex1){
            fail("Wrong exception thrown: "+ ex1);
        }
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
        testIncorrectEdits();
        testCorrectEdits();
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
        fail("Dodelat nalezeni (ne)existujicich priloh");
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getAttachmentCount method, of class AttachmentManager.
     */
    @Test
    public void testGetAttachmentCount() throws NonexistentEntityException {
        System.out.println("getAttachmentCount");
        AttachmentManager man = new AttachmentJpaController(emf);
        int expResult = 0;
        int result = man.getAttachmentCount();
        assertEquals(expResult, result);
        //AttachmentManager man = new AttachmentJpaController(emf);
        Attachment attach = addCorrectAttachemnt(man);
        result = man.getAttachmentCount();
        assertEquals("Incorrect attachments count. ",1,result);
        man.destroy(attach.getId());
        result = man.getAttachmentCount();
        assertEquals("Incorrect attachments count. ",0,result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    private void checkExistance(AttachmentManager instance, Attachment expected) {
        Attachment result = instance.findAttachment(expected.getId());
        assertEquals("Retrieved data differs.",result,expected);
    }

    private void testNullDateAttachment() {
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
   
    }

    private void testIncorrectEdits() throws Exception {
        AttachmentManager man = new AttachmentJpaController(emf);
        Attachment attachment = addCorrectAttachemnt(man);
        Attachment newValue = null;
        try {
            man.edit(newValue);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(AttachmentManagerTest.class.getName()).log(Level.INFO, "Correct exception while editing null attachment.", ex);
        } catch (Exception ex1){
            fail("Incorrect exception thrown while editing null attachment.");
        }
        Attachment attachment1 = new Attachment();
        attachment1.setContent(attachment.getContent());
        attachment1.setId(-1);
        attachment1.setDate(attachment.getDate());
        attachment1.setLocation(attachment.getLocation());
        attachment1.setSpznoteId(attachment.getSpznoteId());
        attachment1.setTs(attachment.getTs());
        attachment1.setType(attachment.getType());
        
        try {
            man.edit(attachment1);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(AttachmentManagerTest.class.getName()).log(Level.SEVERE, "Correct exception has been thrown.", ex);
        }
        checkNonExistance(man, newValue);
        
    }

    private void testCorrectEdits() throws Exception {
        AttachmentManager man = new AttachmentJpaController(emf);
        Attachment attach = addCorrectAttachemnt(man);
        attach.setContent("Changed content.");
        man.edit(attach);
        checkExistance(man, attach);
        attach.setDate(new GregorianCalendar().getTime());
        man.edit(attach);
        checkExistance(man, attach);
        attach.setLocation("/new/path");
        man.edit(attach);
        checkExistance(man, attach);
        attach.setTs(attach.getTs()+1);
        man.edit(attach);
        checkExistance(man, attach);
        attach.setType("application/x+msword");
        man.edit(attach);
        checkExistance(man, attach);
    }

    private Attachment addCorrectAttachemnt(AttachmentManager man) {
        Attachment attach = new Attachment();
        Random rand = new Random();
        int val = rand.nextInt(100);
        attach.setContent("Some content " + val );
        attach.setLocation("/Some/path"+val);
        attach.setDate(new GregorianCalendar().getTime());
        attach.setSpznoteId(new Spznote());
        attach.setTs(val);
        attach.setType("radna"+val);
        try {
            man.create(attach);
        } catch (Exception ex) {
            Logger.getLogger(AttachmentManagerTest.class.getName()).log(Level.SEVERE, null, ex);
            fail("Unable to create correct attachment: "+ex);
        }
        return attach;
    }

    private void checkNonExistance(AttachmentManager man, Attachment newValue) {
        Attachment res = man.findAttachment(newValue.getId());
        assertNotSame(newValue, res);
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
