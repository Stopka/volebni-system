/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package businesstier;

import core.SystemRegException;
import core.data_tier.ActionDAO;

import core.data_tier.JDBCManager;
import core.data_tier.entities.Action;
import java.util.Date;
import java.util.Calendar;
import java.util.*;

/**
 *
 * @author Lahvi
 */
public class ActionFacade {

    public Map<Integer, Action> actions;

    public ActionFacade() {
        actions = new TreeMap<Integer, Action>();
        String[] names = {"Volby spolku Praha 1", "Zasedání předsednictva", "Prodej perníku", "Hlasování o postupu", "Sjezd BRNO"};
        String[] places = {"Praha 1, Malostranské Nám.", "Houslice, Hospoda U Matějky",
            "Pardubice, Pernšt. nám, radnice", "Praha 6, Dejvice", "Brno, Brněnská, hospoda U Brňáka"};
        String[] desc = {"Bude se volit na zasedání nedobrovolník pro oběť bohům. "
            + "Hlasování bude tajné!", "Chceme nového tjenéja že jo pjecedo?!",
            "Potřebujem zvýšit prodej perníku a tak budeme hlasovat jestli nezačnem "
            + "vařit perník.", "Hlasování o postupu jak udělat z kulaťáku hranaťák.",
            "Brňáci se se sjedó a budó hlasováát."};
        Calendar[] startDates = new Calendar[5];
        Calendar[] endDates = new Calendar[5];
        
        for (int i = 0; i < endDates.length; i++) {
            startDates[i] = Calendar.getInstance(); 
            endDates[i] = Calendar.getInstance();
        }
        
        startDates[0].set(2011, 11, 11); endDates[0].set(2011, 11, 14);
        startDates[1].set(2011, 11, 18); endDates[1].set(2011, 11, 25);
        startDates[2].set(2011, 12, 2); endDates[2].set(2011, 12, 8);
        startDates[3].set(2011, 12, 27); endDates[3].set(2012, 1, 3);
        startDates[4].set(2011, 3, 10); endDates[4].set(2012, 3, 11);
        
        for (int i = 0; i < endDates.length; i++) {
            Action a = new Action(i+1, startDates[i], endDates[i], places[i], names[i], desc[i]);
            actions.put(a.getID(), a);
            
        }
    }
    public Collection<Action> getActions(){
        return actions.values();
    }
    public void changeStartDate(int ID, Calendar startDate) throws SystemRegException{
        Action a = getAction(ID);
        a.setStartDate(startDate);
    }
    
    public void changeEndDate(int ID, Calendar endDate) throws SystemRegException{
        Action a = getAction(ID);
        a.setEndDate(endDate);
    }
    
    public void changeDates(int ID, Calendar stDate, Calendar endDate) throws SystemRegException{
        Action a = getAction(ID);
        a.setStartDate(stDate);
        a.setEndDate(endDate);
    }
    
    public void changeParams(int ID, String name, String place, String desc) throws SystemRegException{
        Action a = getAction(ID);
        if(name != null)
            a.setName(name);
        if(place != null)
            a.setPlace(place);
        if(desc != null)
            a.setDescription(desc);
    }
    
    
    public void changeName(int ID, String name) throws SystemRegException{
        Action a = getAction(ID);
        a.setName(name);
    }
    
    public void changeDescription(int ID, String desc) throws SystemRegException{
        Action a = getAction(ID);
        a.setDescription(desc);
    }
    
    public void changePlace(int ID, String place) throws SystemRegException{
        Action a = getAction(ID);
        a.setPlace(place);
    }
    
    public void deleteAction(int ID) throws SystemRegException{
        if(actions.remove(ID) == null)
            throw new SystemRegException("Akce s ID: " + ID + " nemohla být odstraňena,"
                    + "protože neexistuje!");
    }
    
    public Action getAction(String name) throws SystemRegException{
        Collection<Action> val = actions.values();
        for (Action action : val) {
            if(action.getName().equals(name))
                return action;
        }
        throw new SystemRegException("Akce se jménem: " + name + " nebyla nalezena!");
    }
    
    public Action getAction(int ID) throws SystemRegException{
        Action a = actions.get(ID);
        if(a == null)
            throw new SystemRegException("Akce s ID: " + ID + " nebyla nalezena!");
        return a;
    }
    
    
    /*public void createAction(String name, String place, String desc, Calendar startDate, Calendar endDate) throws SystemRegException{
    Date startUtilDate = startDate.getTime();
    Date endUtilDate = endDate.getTime();
    java.sql.Date startDateDtb = new java.sql.Date(startUtilDate.getTime());
    java.sql.Date endDateDtb = new java.sql.Date(endUtilDate.getTime());
    
    actionDataAccess.createAction(name, place, startDateDtb, endDateDtb, desc);
    }
    
    public void deleteAction(int ID) throws SystemRegException{
    actionDataAccess.deleteAction(ID);
    }*/
}
