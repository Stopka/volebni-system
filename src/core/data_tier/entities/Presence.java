/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package core.data_tier.entities;

import java.util.*;

/**
 * Entita {@code Presence} reprezetuje přítomnost daného uživatele na volbní akci. 
 * Tzn. že entita je asociována s entitou {@link User} v násobnosti {@code OneToOne}.
 * Entita nám poskytuje informace o tom jestli se v dané době uživatel na akci
 * nacházel nebo nenacházel a také zdali se na akci nachází v přítomnosti. Samozřejmě
 * se dá přítomnost také nastavit.
 * 
 * @see Presence#isPritomen()
 * @see Presence#isPritomen(java.util.Calendar)
 * @see Presence#setPritomen(boolean) 
 * @author Lahvi
 */
public class Presence {
    private boolean presence;
    private List<Calendar> departures;
    private List<Calendar> arrivals;

    /**
     * Entita {@link Presence} má bezparametrický konstruktor. V konstruktoru
     * se aktuální přítomnost nastaví na {@code false} a inicializují se seznamy
     * příchodů a odchodů.
     */
    public Presence() {
        departures = new ArrayList<Calendar>();
        arrivals = new ArrayList<Calendar>();
        presence = false;
    }

    /**
     * Nastavuje přítomnost. Používá se při odchodech a příchodech.
     * 
     * @param pritomen pokud je {@code true} tak nastavuje že účastník právě 
     * přišel a je přítomen. Pokud {@code false} tak nastavuje, že účastník
     * odchází a tudíž je nepřítomen.
     */
    public void setPresence(boolean pritomen) {
        presence = pritomen;
    }

    /**
     * Vrací aktualní přítomnost. Tedy zda se účastník v současné době na akci
     * nachází nebo nenachází.
     * @return Pokud je uživatel přítomen vrací {@code true}, jinak vrací 
     * {@code false}.
     */
    public boolean isPresent() {
        return presence;
    }

    /**
     * Metoda zjišťuje zda-li se účastník v době dané parametrem na dané akci
     * nacházel nebo ne. Vrací {@code boolean}.
     * 
     * @param doba parametr určující dobu ve které chceme zjistit přítomnost či 
     * nepřítomnost účastníka.
     * 
     * @return pokud se v době dané parametrem účastník na akci nacházel tak vrací
     * {@code true}, pokud ne tak vrací {@code false}.
     */
    public boolean isPresent(Calendar doba) {       
        return false;
    }
    
    public void addDeparture(Calendar depTime){
        departures.add(depTime);
    }
    
    public void addArrival(Calendar arrTime){
        arrivals.add(arrTime);
    }
    
   
    
    @Override
    public String toString(){
        return "Účastník " + (presence ? "je " : "není ") + "přítomen"; 
    }
}
