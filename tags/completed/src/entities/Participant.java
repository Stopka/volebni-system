/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package core.data_tier.entities;

import core.SystemRegException;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

/**
 * Třída reprezentující účastníka volební akce. Má několik povinným a nepovinných
 * parametrů. Každý účastník má jméno, email, číslo OP, presenci vázanou k dané akci
 * @author Lahvi
 */
public class Participant {

    private String firstName;   //povinný
    private String lastName;    //povinný
    private String email;       //povinný
    private long cardID;        //povinný
    private long id;            //povinný
    private Additional addParams; //nepovinný
    private Map<Long, Presence> presence; //údaj nastavovaný po vytvoření.

    /**
     * Konstruktor vytvářejecí účatníka se všemi povinnými i nepovinnými parametry.
     * @param firstName Křestní jméno účastníka. Povinné.
     * @param lastName Příjmení účastníka. Povinné.
     * @param login Přihlašovací jméno účastníka. Nepovinné.
     * @param passwd Heslo účastníka. Nepovinné.
     * @param email Email účastníka. Povinné.
     * @param cardID číslo OP účastníka. Povinné.
     * @param id jednoznačné ID účastníka. Povinné.
     * @param addParams Objekt s dodatečnými údaji o účastníkovi. Nepovinné.
     */
    public Participant(String firstName, String lastName, String email, long cardID, long id, Additional addParams,
            long actionID) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.cardID = cardID;
        this.id = id;
        this.addParams = addParams;
        presence = new TreeMap<Long, Presence>();
        Presence p = new Presence(actionID);
        presence.put(actionID, p);
    }

    public Participant(String firstName, String lastName, String email, long cardID, long id, Additional addParams,
            Collection<Long> actionIDs) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.cardID = cardID;
        this.id = id;
        this.addParams = addParams;
        presence = new TreeMap<Long, Presence>();
        for (Long actionID : actionIDs) {
            Presence p = new Presence(actionID);
            presence.put(actionID, p);
        }
    }

    public Additional getAddParams() {
        return addParams;
    }

    public void setAddParams(Additional addParams) {
        this.addParams = addParams;
    }

    public long getCardID() {
        return cardID;
    }

    public void setCardID(long cardID) {
        this.cardID = cardID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isCompleteReg(long actionID) {
        
        return presence.get(actionID).isCompleteRegistration();        
    }

    public void completeRegistration(long actionID) {
        presence.get(actionID).completeRegistration();   
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLogin(long actionID) {
         return presence.get(actionID).getLogin();
    }

    public void setLogin(long actionID, String login) {
        presence.get(actionID).setLogin(login);;
    }

    public String getPassword(long actionID) {
        return presence.get(actionID).getPassword();
    }

    public void setPassword(long actionID, String passwd) {
        presence.get(actionID).setPassword(passwd);
    }

    public Presence getPresence(long id) {
        return presence.get(id);
    }

    public Collection<Long> getActionIDs() {
        return presence.keySet();
    }

    public void addAction(long id) {
        Presence pres = new Presence(id);
        presence.put(id, pres);
    }

    public void addPresence(Presence pres) {
        presence.put(pres.actionID(), pres);
    }

    public void removeAction(long id) {
        presence.remove(id);
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Jméno: " + firstName + " " + lastName + ", email: "
                + email;
    }
}
