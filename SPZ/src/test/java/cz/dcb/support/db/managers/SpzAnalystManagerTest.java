/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.dcb.support.db.managers;

import cz.dcb.support.db.jpa.controllers.SpzAnalystJpaController;
import cz.dcb.support.db.jpa.controllers.SpzAnalystManager;
import cz.dcb.support.db.jpa.controllers.SpzJpaController;
import cz.dcb.support.db.jpa.controllers.SpzManager;
import cz.dcb.support.db.jpa.controllers.exceptions.NonexistentEntityException;
import cz.dcb.support.db.jpa.entities.Spz;
import cz.dcb.support.db.jpa.entities.Spzanalyst;
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
public class SpzAnalystManagerTest {
    
    private final SpzAnalystManager manager = new SpzAnalystJpaController(DBUtils.getEntityManagerFactory());
    private final Logger logger = Logger.getLogger(SpzAnalystManagerTest.class.getName());
    private final int MAX_ANALYSTS = 80;
    
    public SpzAnalystManagerTest() {
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
     * Test of create method, of class SpzAnalystManager.
     */
    @Test
    public void testCreate() {
        System.out.println("create");
        Spzanalyst spzanalyst = null;
       // SpzAnalystManager instance = new SpzAnalystManagerImpl();
        try{
            manager.create(spzanalyst);
            fail("Exception should be thrown.");
        }catch(IllegalArgumentException iae){
            logger.log(Level.INFO,"Exception ", iae);
        }
        spzanalyst = DBUtils.createSpzAnalyst();
        Random rand = new Random();
        int count = rand.nextInt(MAX_ANALYSTS)+10;
        for(int i=0;i<count;i++){
            manager.create(spzanalyst);
            Spzanalyst result = manager.findSpzanalyst(spzanalyst.getId());
            assertEquals(spzanalyst,result);
        }
        
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of edit method, of class SpzAnalystManager.
     */
    @Test
    public void testEdit() throws Exception {
        System.out.println("edit");
        Random rand = new Random();
        int count = rand.nextInt(MAX_ANALYSTS)+10;
        List<Spzanalyst> analysts = createAnalysts(count);
        int idx=rand.nextInt(count);
        Spzanalyst analyst = analysts.get(idx);
        SpzManager spzManager = new SpzJpaController(DBUtils.getEntityManagerFactory());
        Spz testVal = DBUtils.createSpz();
        spzManager.create(testVal);
        analyst.setSpzid(testVal.getId());
        manager.edit(analyst);
        Spzanalyst result = manager.findSpzanalyst(analyst.getId());
        assertEquals(analyst,result);
        List<Spzanalyst> results = manager.findSpzanalystEntities();
        assertArrayEquals(analysts.toArray(), results.toArray());
    }

    /**
     * Test of findSpzanalyst method, of class SpzAnalystManager.
     */
    @Test
    public void testFindSpzanalyst() {
        System.out.println("findSpzanalyst");
        Integer id = null;
        //SpzAnalystManager instance = new SpzAnalystManagerImpl();
        Spzanalyst expResult = null;
        Spzanalyst result = null;
        try{
            manager.findSpzanalyst(id);
            fail("Exception should be thrown.");
        }catch(IllegalArgumentException iae){
            logger.log(Level.INFO,"Exception ",iae);
        }catch(Exception ex){
            fail("Invalid exception thrown.");
        }
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
        Random rand = new Random();
        int count = rand.nextInt(MAX_ANALYSTS)+10;
        List<Spzanalyst> analysts=createAnalysts(count);
        for(Spzanalyst analyst:analysts){
            result = manager.findSpzanalyst(analyst.getId());
            assertEquals(analyst, result);
        }
        result = manager.findSpzanalyst(-1);
        assertNull(result);
        Spzanalyst maxIdAnalysts = Collections.max(analysts,new Comparator<Spzanalyst>() {

            @Override
            public int compare(Spzanalyst o1, Spzanalyst o2) {
                return o1.getId()-o2.getId();
            }
        });
        result = manager.findSpzanalyst(maxIdAnalysts.getId()+10);
        assertNull(result);
    }

    /**
     * Test of findSpzanalystEntities method, of class SpzAnalystManager.
     */
    @Test
    public void testFindSpzanalystEntities_0args() {
        System.out.println("findSpzanalystEntities");
       // SpzAnalystManager instance = new SpzAnalystManagerImpl();
        List<Spzanalyst> expResult = new ArrayList<>();
        List<Spzanalyst> result = manager.findSpzanalystEntities();
        assertEquals(expResult, result);
        Random rand = new Random();
        int count = rand.nextInt(MAX_ANALYSTS)+10;
        expResult = createAnalysts(count);
        result = manager.findSpzanalystEntities();
        assertArrayEquals(expResult.toArray(), result.toArray());
    }

    /**
     * Test of findSpzanalystEntities method, of class SpzAnalystManager.
     */
    @Test
    public void testFindSpzanalystEntities_int_int() {
        System.out.println("findSpzanalystEntities");
        int maxResults = 0;
        int firstResult = 0;
        //SpzAnalystManager instance = new SpzAnalystManagerImpl();
        List<Spzanalyst> expResult = new ArrayList<>(),values = null;
        List<Spzanalyst> result = manager.findSpzanalystEntities(maxResults, firstResult);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
        Random rand = new Random();
        int count = rand.nextInt(MAX_ANALYSTS)+10;
        values = createAnalysts(count);
        for(firstResult=0;firstResult<count - 3;firstResult++){
            for(maxResults=1;maxResults<count-firstResult;maxResults++){
                expResult = values.subList(firstResult, firstResult+maxResults);
                result = manager.findSpzanalystEntities(maxResults, firstResult);
                assertArrayEquals(expResult.toArray(), result.toArray());
            }
        }
    }
    
    @Test
    public void testFindSpzanalystEntities_int_intInvalidIndices() {
        System.out.println("findSpzanalystEntitiesInvalidIndices");
        List<Spzanalyst> result = null,
                         expResult = new ArrayList<>(),
                         values;
        Random rand = new Random();
        int count = rand.nextInt(MAX_ANALYSTS)+10;
        values = createAnalysts(count);
        try{
            result = manager.findSpzanalystEntities(-1, 1);
            assertEquals(expResult, result);
            fail("Exception should be thrown");
        }catch(IllegalArgumentException iae){
            logger.log(Level.INFO,"Exception ", iae);
        }catch(Exception ex){
            fail("Invalid exception thrown "+ex);
        }
        try{
            result = manager.findSpzanalystEntities(1,-1);
            fail("Exception should be thrown.");
        }catch(IllegalArgumentException iae){
            logger.log(Level.INFO,"Exception ",iae);
        }catch(Exception ex){
            fail("Invalid exception is thrown.");
        }
    }
    /**
     * Test of getSpzanalystCount method, of class SpzAnalystManager.
     */
    @Test
    public void testGetSpzanalystCount() {
        System.out.println("getSpzanalystCount");
        //SpzAnalystManager instance = new SpzAnalystManagerImpl();
        int expResult = 0;
        int result = manager.getSpzanalystCount();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        Random rand = new Random();
        int count = rand.nextInt(MAX_ANALYSTS)+1;
        List<Spzanalyst> analysts = createAnalysts(count);
        assertEquals(analysts.size(), manager.getSpzanalystCount());
    }

    private void clearDB() {
        EntityManagerFactory emf = DBUtils.getEntityManagerFactory();
        SpzManager spzManager = new SpzJpaController(emf);
        SpzAnalystManager analystManager = new SpzAnalystJpaController(emf);
        for(Spzanalyst analyst:analystManager.findSpzanalystEntities()){
            try {
                DBUtils.deleteSpzAnalyst(analyst);
            } catch (NonexistentEntityException ex) {
                Logger.getLogger(SpzAnalystManagerTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private List<Spzanalyst> createAnalysts(int count) {
        List<Spzanalyst> analysts = new ArrayList<>();
        for(int i=0;i<count;i++){
            Spzanalyst analyst = DBUtils.createSpzAnalyst();
            analysts.add(analyst);
            manager.create(analyst);
        }
        return analysts;
    }
}
