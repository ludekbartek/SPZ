/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.dcb.support.db.managers;

import cz.dcb.support.db.jpa.controllers.NoteIssuerJpaController;
import cz.dcb.support.db.jpa.controllers.NoteIssuerManager;
import cz.dcb.support.db.jpa.controllers.SpzNoteJpaController;
import cz.dcb.support.db.jpa.controllers.SpzNoteManager;
import cz.dcb.support.db.jpa.controllers.SpzStateJpaController;
import cz.dcb.support.db.jpa.controllers.SpzStateManager;
import cz.dcb.support.db.jpa.controllers.UserJpaController;
import cz.dcb.support.db.jpa.controllers.UserManager;
import cz.dcb.support.db.jpa.controllers.exceptions.NonexistentEntityException;
import cz.dcb.support.db.jpa.entities.Noteissuer;
import cz.dcb.support.db.jpa.entities.Spznote;
import cz.dcb.support.db.jpa.entities.Spzstate;
import cz.dcb.support.db.jpa.entities.User;
import cz.dcb.support.db.managers.utils.DBUtils;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;
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
public class NoteIssuerManagerTest {
    private NoteIssuerManager manager = new NoteIssuerJpaController(DBUtils.getEntityManagerFactory());
    private final Logger logger = Logger.getLogger(NoteIssuerManager.class.getName());
    public NoteIssuerManagerTest() {
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
    }

    /**
     * Test of create method, of class NoteIssuerManager.
     */
    @Test
    public void testCreate() {
        System.out.println("create");
        Noteissuer noteIssuer = null;
        try{
            manager.create(noteIssuer);
            fail("Exception should be thrown");
        }catch(IllegalArgumentException pe){
            logger.log(Level.INFO,"Correct exception.",pe);
        }catch(Exception ex){
            fail("Wrong exception thrown:"+ex);
        }
        noteIssuer = DBUtils.createNoteIssuer(DBUtils.getEntityManagerFactory());
        manager.create(noteIssuer);
        assertNotNull(noteIssuer.getId());
        Noteissuer result = manager.findNoteissuer(noteIssuer.getId());
        assertEquals(noteIssuer, result);
    }

    /**
     * Test of destroy method, of class NoteIssuerManager.
     */
    @Test
    public void testDestroy() throws Exception {
    }

    /**
     * Test of edit method, of class NoteIssuerManager.
     */
    @Test
    public void testEdit() throws Exception {
        System.out.println("edit");
        Noteissuer noteissuer = null;
        NoteIssuerManager instance = new NoteIssuerManagerImpl();
        instance.edit(noteissuer);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findNoteissuer method, of class NoteIssuerManager.
     */
    @Test
    public void testFindNoteissuer() {
        System.out.println("findNoteissuer");
        Integer id = null;
        NoteIssuerManager instance = new NoteIssuerManagerImpl();
        Noteissuer expResult = null;
        Noteissuer result = instance.findNoteissuer(id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findNoteissuerEntities method, of class NoteIssuerManager.
     */
    @Test
    public void testFindNoteissuerEntities_0args() {
        System.out.println("findNoteissuerEntities");
        NoteIssuerManager instance = new NoteIssuerManagerImpl();
        List<Noteissuer> expResult = null;
        List<Noteissuer> result = instance.findNoteissuerEntities();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findNoteissuerEntities method, of class NoteIssuerManager.
     */
    @Test
    public void testFindNoteissuerEntities_int_int() {
        System.out.println("findNoteissuerEntities");
        int maxResults = 0;
        int firstResult = 0;
        NoteIssuerManager instance = new NoteIssuerManagerImpl();
        List<Noteissuer> expResult = null;
        List<Noteissuer> result = instance.findNoteissuerEntities(maxResults, firstResult);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getEntityManager method, of class NoteIssuerManager.
     */
    @Test
    public void testGetEntityManager() {
        System.out.println("getEntityManager");
        NoteIssuerManager instance = new NoteIssuerManagerImpl();
        EntityManager expResult = null;
        EntityManager result = instance.getEntityManager();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getNoteissuerCount method, of class NoteIssuerManager.
     */
    @Test
    public void testGetNoteissuerCount() {
        System.out.println("getNoteissuerCount");
        NoteIssuerManager instance = new NoteIssuerManagerImpl();
        int expResult = 0;
        int result = instance.getNoteissuerCount();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    private void clearDB() {
        EntityManagerFactory emf = DBUtils.getEntityManagerFactory();
        UserManager userMan = new UserJpaController(emf);
        SpzStateManager spzStateManager = new SpzStateJpaController(emf);
        NoteIssuerManager niMananager = new NoteIssuerJpaController(emf);
        SpzNoteManager spzNoteManager = new SpzNoteJpaController(emf);
        for(User user:userMan.findUserEntities()){
            try {
                userMan.destroy(user.getId());
            } catch (NonexistentEntityException ex) {
                logger.log(Level.SEVERE, null, ex);
            }
        }
        for(Spzstate state:spzStateManager.findSpzstateEntities()){
            try {
                spzStateManager.destroy(state.getId());
            } catch (NonexistentEntityException ex) {
                logger.log(Level.SEVERE, null, ex);
            }
        }
        for(Noteissuer noteIssuer:niMananager.findNoteissuerEntities()){
            try {
                niMananager.destroy(noteIssuer.getId());
            } catch (NonexistentEntityException ex) {
                Logger.getLogger(NoteIssuerManagerTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        for(Spznote note:spzNoteManager.findSpznoteEntities()){
            try {
                spzNoteManager.destroy(note.getId());
            } catch (NonexistentEntityException ex) {
                Logger.getLogger(NoteIssuerManagerTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }

    public class NoteIssuerManagerImpl implements NoteIssuerManager {

        public void create(Noteissuer noteissuer) {
        }

        public void destroy(Integer id) throws NonexistentEntityException {
        }

        public void edit(Noteissuer noteissuer) throws NonexistentEntityException, Exception {
        }

        public Noteissuer findNoteissuer(Integer id) {
            return null;
        }

        public List<Noteissuer> findNoteissuerEntities() {
            return null;
        }

        public List<Noteissuer> findNoteissuerEntities(int maxResults, int firstResult) {
            return null;
        }

        public EntityManager getEntityManager() {
            return null;
        }

        public int getNoteissuerCount() {
            return 0;
        }
    }
    
}
