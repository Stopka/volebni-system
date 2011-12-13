/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fel.mvod.persistence.regsys.commonInterface;

import cz.cvut.fel.mvod.persistence.regsys.commonInterface.entities.Action;
import java.sql.Date;
import java.util.Calendar;
import java.util.Collection;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author stopka
 */
public class ActionDAOTest {

    public ActionDAOTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getInstance method, of class ActionDAO.
     */
    @Test
    public void testGetInstance() {
        System.out.println("getInstance");
        assertNotNull(ActionDAO.getInstance());
    }

    /**
     * Test of getActions method, of class ActionDAO.
     */
    @Test
    public void testGetActions() throws Exception {
        System.out.println("getActions");
        ActionDAO instance = null;
        assertNotNull(instance.getActions());
    }
}
