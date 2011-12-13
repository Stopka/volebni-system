/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fel.mvod.persistence.regsys;

import java.util.GregorianCalendar;
import cz.cvut.fel.mvod.persistence.regsys.commonInterface.entities.Action;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
public class RegSysDAOImplTest {

    public RegSysDAOImplTest() {
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
     * Test of getParticipants method, of class RegSysDAOImpl.
     */
    @Test
    public void testGetParticipants() throws Exception {
        System.out.println("getParticipants");
        RegSysDAOImpl instance = new RegSysDAOImpl();
        ArrayList result = instance.getParticipants();
        assertNotNull(result);
    }

    /**
     * Test of getAkce method, of class RegSysDAOImpl.
     */
    @Test
    public void testGetAkce() throws Exception {
        System.out.println("getAkce");
        RegSysDAOImpl instance = new RegSysDAOImpl();
        ArrayList result = instance.getAkce();
    }

    /**
     * Test of isImportEnabled and set isImportEnabled method, of class RegSysDAOImpl.
     */
    @Test
    public void testImportEnabled() {
        System.out.println("isImportEnabled");
        RegSysDAOImpl instance = new RegSysDAOImpl();
        assertFalse(instance.isImportEnabled());
        instance.setImportEnabled(true);
        assertTrue(instance.isImportEnabled());
        instance.setImportEnabled(false);
        assertTrue(instance.isImportEnabled());
    }

    /**
     * Test of setAction and setAction method, of class RegSysDAOImpl.
     */
    @Test
    public void testAction() {
        System.out.println("setAction");
        RegSysDAOImpl instance = new RegSysDAOImpl();
        assertNull(instance.getAction());
        Action action=new Action(0, new GregorianCalendar(), new GregorianCalendar(), "place", "name", "description");
        instance.setAction(action);
        assertSame(action,instance.getAction());
    }
}
