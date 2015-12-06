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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    private final Logger logger = Logger.getLogger(ProjectManagerTest.class.getName());
    private final int MAX_COUNT=90;
    
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
        clearDB();
    }
    
    @After
    public void tearDown() {
        clearDB();
    }

    /**
     * Test of create method, of class ProjectManager.
     */
    @Test
    public void testCreate() throws Exception {
        System.out.println("create");
        Project project = null;
       // ProjectManager instance = new ProjectManagerImpl();
        try{
            manager.create(project);
            fail("Exception should be thrown.");
        }catch(IllegalArgumentException iae){
            logger.log(Level.INFO,"Exception ", iae);
        }catch(Exception ex){
            fail("Wrong exception thrown: "+ex);
        }
        
        project = DBUtils.createProject();
        manager.create(project);
        assertNotNull(project.getId());
        Project result = manager.findProject(project.getId());
        assertEquals(project,result);
    }

    /**
     * Test of destroy method, of class ProjectManager.
     */
    /*@Test
    public void testDestroy() throws Exception {
        System.out.println("destroy");
        String id = "";
//        ProjectManager instance = new ProjectManagerImpl();
        manager.destroy(-1);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }*/

    /**
     * Test of edit method, of class ProjectManager.
     */
    @Test
    public void testEdit() throws Exception {
        System.out.println("edit");
        Project project = null;
//        ProjectManager instance = new ProjectManagerImpl();
        try{
            manager.edit(project);
            fail("Exception should be thrown.");
        }catch(IllegalArgumentException iae){
            logger.log(Level.INFO,"Exception ",iae);
        }catch(Exception ex){
            fail("Wrong exception thrown "+ex);
        }
        Random rand = new Random();
        int count = rand.nextInt(MAX_COUNT)+10;
        List<Project> projects = createProjects(count);
        
        for(int idx=0;idx<projects.size();idx++){
            if(rand.nextBoolean()){
                project = projects.get(idx);
                project.setName("Jmeno "+idx);
                manager.edit(project);
                Project result = manager.findProject(project.getId());
                assertEquals(project,result);
            }
         }
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of findProject method, of class ProjectManager.
     */
    @Test
    public void testFindProject() {
        System.out.println("findProject");
        Random rand = new Random();
        int count = rand.nextInt(MAX_COUNT)+10;
        Project expResult  = null;
        Project result = null;
        try{
            manager.findProject(null);
            fail("Exception should be thrown.");
        }catch(IllegalArgumentException ex){
            logger.log(Level.INFO,ex.toString());
        }catch(Exception ex){
            fail("Invalid exception thrown "+ex);
        }
        assertEquals(expResult, result);
        
        List<Project> projects = createProjects(count);
        for(Project project:projects){
            result = manager.findProject(project.getId());
            assertEquals(result, project);
        }
        Comparator<Project> projectComparator = new Comparator<Project>() {

            @Override
            public int compare(Project o1, Project o2) {
                
                return o1.getId() - o2.getId();
            }
        };
        int minId = Collections.min(projects,projectComparator).getId()-1;
        int maxId = Collections.max(projects, projectComparator).getId()+1;
        result = manager.findProject(minId);
        assertNull(result);
        result = manager.findProject(maxId);
        assertNull(result);
    }

    /**
     * Test of findProjectEntities method, of class ProjectManager.
     */
    @Test
    public void testFindProjectEntities_0args() {
        System.out.println("findProjectEntities");
//        ProjectManager instance = new ProjectManagerImpl();
        List<Project> expResult = new ArrayList<>();
        List<Project> result = manager.findProjectEntities();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        Random rand = new Random();
        int count = rand.nextInt(MAX_COUNT)+10;
        expResult = createProjects(count);
        result = manager.findProjectEntities();
        assertArrayEquals(expResult.toArray(), result.toArray());
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
        List<Project> expResult = new ArrayList<>();
        List<Project> result = manager.findProjectEntities(maxResults, firstResult);
        assertEquals(expResult, result);
        Random rand = new Random();
        int count = rand.nextInt(MAX_COUNT);
        List<Project> values = createProjects(count);
        for(firstResult=1;firstResult<count - 2;firstResult++){
            for(maxResults=1;maxResults< count - firstResult;maxResults++){
                expResult = values.subList(firstResult, firstResult+maxResults);
                result = manager.findProjectEntities(maxResults, firstResult);
                assertArrayEquals(expResult.toArray(), result.toArray());
            }
        }
    }
    
    @Test
    public void testFindProjectEntities_int_intInvalidIndeces() {
        System.out.println("findProjectEntities");
        List<Project> result = null;
        Random rand = new Random();
        int count = rand.nextInt(MAX_COUNT)+10;
        List<Project> values = createProjects(count);
        try{
            result = manager.findProjectEntities(-1, 1);
            fail("Exception should be thrown.");
        }catch(IllegalArgumentException ex){
            logger.log(Level.INFO,ex.toString());
        }catch(Exception ex){
            fail(ex.toString());
        }
        assertNull(result);
        
        try{
            result = manager.findProjectEntities(1, -1);
            fail("Exception should be thrown.");
        }catch(IllegalArgumentException ex){
            logger.log(Level.INFO,ex.toString());
        }catch(Exception ex){
            fail(ex.toString());
        }
        assertNull(result);
        
        
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
        Random rand = new Random();
        int count = rand.nextInt(MAX_COUNT);
        List<Project> projects = createProjects(result);
        assertEquals(projects.size(), manager.getProjectCount());
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

    private void clearDB() {
        for(Project project:manager.findProjectEntities()){
            try {
                manager.destroy(project.getId());
            } catch (cz.dcb.support.db.jpa.controllers.exceptions.NonexistentEntityException ex) {
                Logger.getLogger(ProjectManagerTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private List<Project> createProjects(int count) {
        List<Project> projects = new ArrayList<>();
        for(int i=0;i<count;i++){
            Project proj =  DBUtils.createProject();
            proj.setName(proj.getName()+i);
            proj.setDescription(proj.getDescription()+i);
            manager.create(proj);
            projects.add(proj);
        }
        return projects;
    }
}
