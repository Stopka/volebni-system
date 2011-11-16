/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package businesstier;

import businesstier.utils.Generator;
import core.SystemRegException;
import core.data_tier.ParticipantDAO;
import core.data_tier.entities.Participant;
import java.util.Calendar;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;


/**
 *
 * @author Lahvi
 */
public class ParticipantFacade {
    private ParticipantDAO parDataAccess;
    private Map<Integer, Participant> pars;
    
    public ParticipantFacade(){
        pars = new TreeMap<Integer, Participant>();
        String[] names = {"Petr Hlavacek", "Honza Bily", "Jakub Merta", "Vaclav Tarantik", "Vojtech Letal", "Honza Skrivanek"};
        String[] emails = {"hlavap@seznam.cz", "white@seznam.cz", "mertic@svitkov.cz", "vendy@centrum.cz", "tisek@gmail.cz", "ptak@zoo.cz"};
        String[] address = {"dolany", "polabiny", "svitkov", "smirice", "dubina", "srch"};
        int[] ids = {505050, 191919, 181818, 110011, 226226, 727272};
        for (int i = 0; i < ids.length; i++) {
            createParticipant(names[i], address[i], emails[i], ids[i]);
        }
    }
    
    public void createParticipant(String name, String email, int personalID){
        //parDataAccess.createParticipant(name, email, null, personalID);
        pars.put(personalID, new Participant(name, email, personalID));
    }
    
    public void createParticipant(String name, String address, String email, int personalID){
        //parDataAccess.createParticipant(name, email, address, personalID);
        pars.put(personalID, new Participant(name, email, personalID));
    }
    
    public Participant getParticipant(int personalID) throws SystemRegException{
        Participant res = pars.get(personalID);
        if(res == null){
            throw new SystemRegException("Účastník s ID: " + personalID + " neexistuje!");
        }
        return res; 
        
    }
    
    public Participant getParticipant(String login) throws SystemRegException{
        Collection<Participant> val = pars.values();
        for (Participant participant : val) {
            if(participant.getLogin() != null && participant.getLogin().equals(login)){
                return participant;
            }
        }
        throw new SystemRegException("Účastník s loginem: " + login + " neexistuje!");
        
    }
    
    public void completeReg(int personalID) throws SystemRegException{
        Participant p = getParticipant(personalID);
        if(p.isCompleteReg()){
            throw new SystemRegException("Registrace účastníka s ID: " + personalID + " "
                    + "nemohla být dokončena, protože už dokončena byla!");
        } else {
            p.setLogin(Generator.randomString());
            p.setPassword(Generator.randomString());
            p.setCompleteReg();
            p.getPresence().setPresence(true);
            
        }
    }
    
    public void deleteParticipant(int personalID) throws SystemRegException{
        if(pars.remove(personalID) == null){
            throw new SystemRegException("Účastník s ID: " + personalID + " nemohl "
                    + "být odstraněn, protože neexistuje!");
        }
    }
    
    public void addDeparture(String login) throws SystemRegException{
        Participant p = getParticipant(login);
        if(p != null){
            p.getPresence().addDeparture(Calendar.getInstance());
            p.getPresence().setPresence(false);
        }
    }
    
    public void addArrival(String login) throws SystemRegException{
        Participant p = getParticipant(login);
        if(p != null){
            p.getPresence().addArrival(Calendar.getInstance());
            p.getPresence().setPresence(true);
        }
    }
    
    public Collection<Participant> getAllParticipants(){
        return pars.values();
    }
    
    
}
