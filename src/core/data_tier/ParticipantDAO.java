/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package core.data_tier;

import core.SystemRegException;
import core.data_tier.entities.Participant;
import java.util.*;

/**
 *
 * @author Lahvi
 */
public class ParticipantDAO {
    private Map<Integer, Participant> persons;
    
    public ParticipantDAO(){
        //V parametru asi bude ziskani pripojeni z nejakyho connection poolu
        persons = new HashMap<Integer, Participant>();
        //ted se do HashMapy nactou tabulky z databaze
    }
    
    /**
     * Vytvoří nového účatníka.
     * @param name Jméno nového účatníka.
     * @param address Adresa nového účastníka.
     * @param id Číslo občanky a zároveň identifikátor účatníka.
     */
    public void createParticipant(String name, String address, int id){
        Participant newPerson = new Participant(name, address, id);
        persons.put(id, newPerson);
        //zapis do databaze
        //'insert into PARTICIPANTS values (name, address, id, null, null..'
    }
    
    /**
     * Vymaže účastníka vyhledaného podle jeho loginu, předaném parametrem 
     * @param login.
     * @param login Login odstraňovaného účastníka.
     */
    public void deleteParticipant(String login){
        //vymaže uživatele s daným loginem z databáze
    }
    
    /**
     * Odstraní účastníka vyhledaného podle čísla občanky, předaném v parametru
     * @param idnum . 
     * @param idnum Číslo OP odstraňovaného účastníka.
     */
    public void deleteParticipant(int idnum){
        //vymaže uživatele s daným ID z databáze
    }
    
    /**
     * Vyhledá účastníka podle jeho čísla OP.
     * @param idnum
     * @return
     * @throws Exception 
     */
    public Participant getParticipant(int idnum) throws SystemRegException{
        Participant p = persons.get(idnum);
        if(p != null){
            return p;
        } else {
            System.out.println("Nenalezeno!");
            throw new SystemRegException("Nenalezen uživatel s id: " +idnum);
        }
    }
    
    /**
     * Vyhledá a vrátí účastníka podle jeho loginu z parametru {@code login}.
     * @param login Login hledaného účastníka.
     * @return Vrací vyhledaného účastníka.
     * @throws Exception Pokud neexistuje žádný účastník s předaným loginem vyhodí
     * se vyjímka {@link core.SystemRegException}.
     */
    public Participant getParticipant(String login) throws SystemRegException{
        Collection<Participant> values = persons.values();
        Iterator<Participant> it = values.iterator();        
        while(it.hasNext()){
            Participant next = it.next();
            if(next.getLogin().equals(login)){
                return next;
            }
        }
        System.out.println("Nenalzen uživatel s loginem: " + login);
        throw new SystemRegException("Nenalzen uživatel s loginem: " + login);
    }
    
    /**
     * Metoda přidává odchod.
     * @param login
     * @param depTime
     * @throws Exception 
     */
    public void addDeparture(String login, Calendar depTime) throws SystemRegException{
        depTime = Calendar.getInstance();
        Participant p = getParticipant(login);
        p.getPresence().addDeparture(depTime);
    }
    /**
     * Metoda přidává příchod
     * @param login
     * @param arrTime
     * @throws Exception 
     */
    public void addArrival(String login, Calendar arrTime) throws SystemRegException{
        arrTime = Calendar.getInstance();
        Participant p = getParticipant(login);
        p.getPresence().addArrival(arrTime);
    }
    
}
