/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fel.mvod.persistence.regsys.commonInterface;

import java.util.Iterator;
import cz.cvut.fel.mvod.persistence.regsys.commonInterface.entities.Action;
import cz.cvut.fel.mvod.persistence.regsys.commonInterface.entities.Additional;
import cz.cvut.fel.mvod.persistence.regsys.commonInterface.entities.Participant;
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
public class ParticipantDAOTest {

    public ParticipantDAOTest() {
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
     * Test of getInstance method, of class ParticipantDAO.
     */
    @Test
    public void testGetInstance() {
        System.out.println("getInstance");
        assertNotNull(ParticipantDAO.getInstance());
    }

    /**
     * Test of getPresent method, of class ParticipantDAO.
     */
    @Test
    public void testGetPresent() throws Exception {
        System.out.println("getPresent");
        ActionDAO action=ActionDAO.getInstance();
        Collection<Action> actions=action.getActions();
        if(actions==null || actions.isEmpty()){
            System.err.println("Test getPresent nelze provést bez dat.");
            return;
        }
        Iterator<Action> it=actions.iterator();
        while(it.hasNext()){
            ParticipantDAO instance=ParticipantDAO.getInstance();
            Collection<Participant> result = instance.getPresent(it.next().getID());
            if(result!=null && !result.isEmpty()){
                Iterator<Participant> it2=result.iterator();
                int i=0;
                while(it2.hasNext() && i<30){
                    Participant p=it2.next();
                    assertTrue(p.isCompleteReg());
                    i++;
                }
                return;
            }
        }
        System.err.println("Test getPresent nemá v datech participanty k otestování.");
    }
}
