/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.dcb.support.db.managers;

import cz.dcb.support.db.jpa.controllers.ProjectConfigurationManager;
import cz.dcb.support.db.jpa.controllers.exceptions.NonexistentEntityException;
import cz.dcb.support.db.jpa.entities.Projectconfiguration;
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
public class ProjectConfigurationManagerTest {
    
    public ProjectConfigurationManagerTest() {
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
     * Test of create method, of class ProjectConfigurationManager.
     */
    @Test
    public void testCreate() {
        System.out.println("create");
        Projectconfiguration projectconfiguration = null;
        ProjectConfigurationManager instance = new ProjectConfigurationManagerImpl();
        instance.create(projectconfiguration);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of destroy method, of class ProjectConfigurationManager.
     */
    @Test
    public void testDestroy() throws Exception {
        System.out.println("destroy");
        Integer id = null;
        ProjectConfigurationManager instance = new ProjectConfigurationManagerImpl();
        instance.destroy(id);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of edit method, of class ProjectConfigurationManager.
     */
    @Test
    public void testEdit() throws Exception {
        System.out.println("edit");
        Projectconfiguration projectconfiguration = null;
        ProjectConfigurationManager instance = new ProjectConfigurationManagerImpl();
        instance.edit(projectconfiguration);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findProjectconfiguration method, of class ProjectConfigurationManager.
     */
    @Test
    public void testFindProjectconfiguration() {
        System.out.println("findProjectconfiguration");
        Integer id = null;
        ProjectConfigurationManager instance = new ProjectConfigurationManagerImpl();
        Projectconfiguration expResult = null;
        Projectconfiguration result = instance.findProjectconfiguration(id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findProjectconfigurationEntities method, of class ProjectConfigurationManager.
     */
    @Test
    public void testFindProjectconfigurationEntities_0args() {
        System.out.println("findProjectconfigurationEntities");
        ProjectConfigurationManager instance = new ProjectConfigurationManagerImpl();
        List<Projectconfiguration> expResult = null;
        List<Projectconfiguration> result = instance.findProjectconfigurationEntities();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findProjectconfigurationEntities method, of class ProjectConfigurationManager.
     */
    @Test
    public void testFindProjectconfigurationEntities_int_int() {
        System.out.println("findProjectconfigurationEntities");
        int maxResults = 0;
        int firstResult = 0;
        ProjectConfigurationManager instance = new ProjectConfigurationManagerImpl();
        List<Projectconfiguration> expResult = null;
        List<Projectconfiguration> result = instance.findProjectconfigurationEntities(maxResults, firstResult);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getEntityManager method, of class ProjectConfigurationManager.
     */
    @Test
    public void testGetEntityManager() {
        System.out.println("getEntityManager");
        ProjectConfigurationManager instance = new ProjectConfigurationManagerImpl();
        EntityManager expResult = null;
        EntityManager result = instance.getEntityManager();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getProjectconfigurationCount method, of class ProjectConfigurationManager.
     */
    @Test
    public void testGetProjectconfigurationCount() {
        System.out.println("getProjectconfigurationCount");
        ProjectConfigurationManager instance = new ProjectConfigurationManagerImpl();
        int expResult = 0;
        int result = instance.getProjectconfigurationCount();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    public class ProjectConfigurationManagerImpl implements ProjectConfigurationManager {

        public void create(Projectconfiguration projectconfiguration) {
        }

        public void destroy(Integer id) throws NonexistentEntityException {
        }

        public void edit(Projectconfiguration projectconfiguration) throws NonexistentEntityException, Exception {
        }

        public Projectconfiguration findProjectconfiguration(Integer id) {
            return null;
        }

        public List<Projectconfiguration> findProjectconfigurationEntities() {
            return null;
        }

        public List<Projectconfiguration> findProjectconfigurationEntities(int maxResults, int firstResult) {
            return null;
        }

        public EntityManager getEntityManager() {
            return null;
        }

        public int getProjectconfigurationCount() {
            return 0;
        }
    }
    
}
