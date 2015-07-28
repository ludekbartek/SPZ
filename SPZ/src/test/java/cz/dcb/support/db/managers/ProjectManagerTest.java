/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.dcb.support.db.managers;

import cz.dcb.support.db.jpa.controllers.ProjectJpaController;
import cz.dcb.support.db.jpa.controllers.ProjectManager;
import cz.dcb.support.db.jpa.entities.Project;
import cz.dcb.support.db.managers.exceptions.IllegalOrphanException;
import cz.dcb.support.db.managers.exceptions.NonexistentEntityException;
import cz.dcb.support.db.managers.exceptions.PreexistingEntityException;
import cz.dcb.support.db.managers.utils.DBUtils;
import java.util.List;
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
public class ProjectManagerTest {
    
    private final ProjectManager manager = new ProjectJpaController(DBUtils.getEntityManagerFactory());
    
    public ProjectManagerTest() {
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
     * Test of create method, of class ProjectManager.
     */
    @Test
    public void testCreate() throws Exception {
        System.out.println("create");
        Project project = null;
       // ProjectManager instance = new ProjectManagerImpl();
        manager.create(project);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of destroy method, of class ProjectManager.
     */
    @Test
    public void testDestroy() throws Exception {
        System.out.println("destroy");
        String id = "";
//        ProjectManager instance = new ProjectManagerImpl();
        manager.destroy(-1);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of edit method, of class ProjectManager.
     */
    @Test
    public void testEdit() throws Exception {
        System.out.println("edit");
        Project project = null;
//        ProjectManager instance = new ProjectManagerImpl();
        manager.edit(project);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findProject method, of class ProjectManager.
     */
    @Test
    public void testFindProject() {
        System.out.println("findProject");
        Project project = DBUtils.createProject();
        Project expResult = null;
        Project result = manager.findProject(project.getId());
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findProjectEntities method, of class ProjectManager.
     */
    @Test
    public void testFindProjectEntities_0args() {
        System.out.println("findProjectEntities");
//        ProjectManager instance = new ProjectManagerImpl();
        List<Project> expResult = null;
        List<Project> result = manager.findProjectEntities();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findProjectEntities method, of class ProjectManager.
     */
    @Test
    public void testFindProjectEntities_int_int() {
        System.out.println("findProjectEntities");
        int maxResults = 0;
        int firstResult = 0;
//        ProjectManager instance = new ProjectManagerImpl();
        List<Project> expResult = null;
        List<Project> result = manager.findProjectEntities(maxResults, firstResult);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getProjectCount method, of class ProjectManager.
     */
    @Test
    public void testGetProjectCount() {
        System.out.println("getProjectCount");
//        ProjectManager instance = new ProjectManagerImpl();
        int expResult = 0;
        int result = manager.getProjectCount();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

/*    public class ProjectManagerImpl implements ProjectManager {

        public void create(Project project) throws PreexistingEntityException, Exception {
        }

        public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException {
        }

        public void edit(Project project) throws IllegalOrphanException, NonexistentEntityException, Exception {
        }

        public Project findProject(String id) {
            return null;
        }

        public List<Project> findProjectEntities() {
            return null;
        }

        public List<Project> findProjectEntities(int maxResults, int firstResult) {
            return null;
        }

        public int getProjectCount() {
            return 0;
        }
    }
  */  
}
