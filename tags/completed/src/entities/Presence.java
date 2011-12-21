package core.data_tier.entities;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

/**
 * Třída Presence reprezentuje účast na dané akci. Účast na akci zahrnuje přiznak
 * dokončení registrace, přihlašovací jméno a heslo v rámci dané akce, listy příchodů
 * a odchodů a konečně také příznak o tom zda je účastník právě teď přítomen.
 * @author Lahvi
 */
public class Presence {
    
    private long actionID; //akce, ke které se presence vztahuje
    private List<Calendar> checkins; //list příchodů
    private List<Calendar> checkouts; //list odchodů
    private boolean present; //přihlášen právě teď?
    private boolean completeReg;
    private String login, password;
    
    /**
     * Konstruktor se pro vytvoření účasti na dané akci. Načítá se z tabulky 
     * participatoion. Poté se z tabulky Presence k dané presenci načtou příchody a
     * odchody pomocí metod {@link Presence#addCheckIn(java.util.Calendar) } a 
     * {@link Presence#addCheckOut(java.util.Calendar) }, se vloží do seznamu.
     * @param actionID Identifikátor akce ke které se přítomnost vztahuje
     * @param present Příznak zda je účastník právě teď přítomen nebo ne.
     * @param isCompleteReg Příznak zda má účastník dokončenou registraci
     * @param login Přihlašovací jméno účastníka.
     * @param password Heslo účastníka.
     */
    public Presence(long actionID, boolean present, boolean isCompleteReg, String login, String password){
        
        this.actionID = actionID;
        this.present = present;
        this.completeReg = isCompleteReg;
        checkins = new ArrayList<Calendar>();
        checkouts = new ArrayList<Calendar>();
        this.login = null; this.password = null;
    }
    
    /**
     * Konstrukor, který se obvykle používá při vytváření nové přítomnosti.
     * Potřebuje ID akce, ke které se bude vztahovat a jinak jsou všechny ostatní
     * parametry nastaveny na defaultní hodnoty. Tzn. že pokud je vytvářena nová 
     * účast na akci, tak login a heslo budou null, uživatel nebude mít registraci
     * kompletní a na akci nebude přítomen.
     * @param actionID 
     */
    public Presence(long actionID){
        this(actionID, false, false, null, null);
    }
    
    public boolean isPresent(){
        return present;
    }
    
    public void completeRegistration(){
        completeReg = true;
    }
    
    public boolean isCompleteRegistration(){
        return completeReg;
    }
    
    public void setLogin(String login){
        this.login = login;
    }
    
    public void setPassword(String password){
        this.password = password;
    }
    
    public String getLogin(){
        return login;
    }
    
    public String getPassword(){
        return password;
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
