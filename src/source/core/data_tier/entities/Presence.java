/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package core.data_tier.entities;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author Lahvi
 */
public class Presence {
    
    private long actionID; //akce, ke které se presence vztahuje
    private List<Calendar> checkins; //list příchodů
    private List<Calendar> checkouts; //list odchodů
    private boolean present; //přihlášen právě teď?
    
    /**
     * Konstruktor se pro vytvoření účasti na dané akci. Načítá se z tabulky 
     * participatoion. Poté se z tabulky Presence k dané presenci načtou příchody a
     * odchody pomocí metod {@link Presence#addCheckIn(java.util.Calendar) } a 
     * {@link Presence#addCheckOut(java.util.Calendar) }, se vloží do seznamu.
     * @param actionID Identifikátor akce ke které se přítomnost vztahuje
     * @param present Příznak zda je účastník právě teď přítomen nebo ne.
     */
    public Presence(long actionID, boolean present){
        
        this.actionID = actionID;
        this.present = present;
        checkins = new ArrayList<Calendar>();
        checkouts = new ArrayList<Calendar>();
    }
    
    /**
     * Konstrukor, který se obvykle používá při vytváření nové přítomnosti.
     * Potřebuje ID akce, ke které se bude vztahovat
     * @param actionID 
     */
    public Presence(long actionID){
        this(actionID, false);
    }
    
    public boolean isPresent(){
        return present;
    }
    
    public void addCheckIn(Calendar checkInTime){
        checkins.add(checkInTime);
        present = true;
    }
    
    public void addCheckOut(Calendar checkOutTime){
        checkouts.add(checkOutTime);
        present = false;
    }
    
    public Collection<Calendar> getCheckIns(){
        return this.checkins;
    }
    
    public Collection<Calendar> getCheckOuts(){
        return this.checkouts;
    }
    
   
    
    public long actionID(){
        return actionID;
    }
}
