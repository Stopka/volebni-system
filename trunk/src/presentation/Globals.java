/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation;

import businesstier.*;
import core.SystemRegException;
import core.data_tier.ActionDAO;
import core.data_tier.ParticipantDAO;
import core.data_tier.UserDAO;
import java.awt.Component;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.Calendar;
import javax.naming.NamingException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Lahvi
 */
public class Globals {

    private static Globals globals;
    private ActionFacade actionOps;
    private ParticipantFacade participantOps;
    private UserFacade userOps;

    private Globals() {
        actionOps = new ActionFacade();
        participantOps = new ParticipantFacade();
        userOps = new UserFacade();
    }

    public static Globals getInstance() {
        if (globals == null) {
            globals = new Globals();
        }
        return globals;
    }

    public ActionFacade getActionOps() {
        return actionOps;
    }

    public ParticipantFacade getParticipantOps() {
        return participantOps;
    }

    public UserFacade getUserOps() {
        return userOps;
    }
    
    

    public static void showErr(Component owner, Exception ex) {
        JOptionPane.showMessageDialog(owner, ex.getMessage(), "Nastala chyba!", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) throws SystemRegException, UnsupportedEncodingException, IOException, NamingException, SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        /*try {
            Globals gl = Globals.getInstance();
            ActionFacade aDao = new ActionFacade();
            Calendar cal = Calendar.getInstance();
            cal.set(2051, 11, 9);
            Calendar cal2 = Calendar.getInstance();
            cal2.set(2081, 11, 15);

            //aDao.createAction("Lahviho akce2", "Dolany 3asddfd9, 533 45", "Hovnfsgdfsghgdhfgjhfjhfo na nic prostě jen tak ty kokot.", cal, cal2);
            aDao.deleteAction(2);
        } catch (SystemRegException ex) {
            System.out.println(ex.getMessage());
        }*/
        //java.sql.Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/da");
        /*ParticipantDAO parDao = gl.getParticipantDAO();
        UserDAO aDao = gl.getAdminDAO();
        String[] names = {"Petr Hlavacek", "Honza Bily", "Jakub Merta", "Vaclav Tarantik", "Vojtech Letal", "Honza Skrivanek"};
        String[] logins = {"Lahvi", "White", "Mertic", "Vena", "Tisek", "Ptak"};
        String[] pswds = {"lahvi", "white", "metic", "vena", "tisek", "ptak"};
        String[] address = {"dolany", "polabiny", "svitkov", "smirice", "dubina", "srch"};
        int[] ids = {050505, 191919, 181818, 110011, 226226, 727272};
        
        for (int i = 0; i < ids.length; i++) {
        //parDao.createParticipant(names[i], address[i], ids[i], logins[i], pswds[i]);
        }
        
        String[] alog = {"SuperAdmin", "Admin", "Reg"};
        String[] apswds = {"121", "122", "123"};
        for (int i = 0; i < apswds.length; i++) {
        aDao.createAdmin(alog[i], apswds[i], i);
        }*/
        Globals gl = Globals.getInstance();
        LoginScreen s = new LoginScreen();
    }
}
