/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package core.data_tier.entities;

import java.util.*;

/**
 * Třída reprezentující jednotlivou akci, pro kterou se budou lidé registrovat.
 * Třída bude obsahovat základní údaje o akci, jako datum zahájení, popis akce, 
 * místo konání atd. Akce bude vytvářet administrátor, který se do aplikace při
 * zahájení registací přihlásí.
 * @author Lahvi
 */
public class Action {
    
    private String description;
    private Calendar startDate, endDate;
    private String place;
    private String name;
    private int idNum;
    private List<Participant> participants;
    
    public Action(int id, Calendar startDate, Calendar endDate, String place, String name, String description){
        idNum = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.place = place;
        this.name = name;
        this.description = description;
        participants = new ArrayList<Participant>();
    }
    
    public void addUser(Participant user){
        participants.add(user);
    }

    public String getDescription() {
        return description;
    }

    public Calendar getEndDate() {
        return endDate;
    }

    public Participant getUser(Participant u) {
        int index = participants.indexOf(u);
        if(index != -1)
            return participants.get(index);
        else
            return null;
    }

    public String getPlace() {
        return place;
    }

    public Calendar getStartDate() {
        return startDate;
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    public String getName(){
        return this.name;
    }
    
    public int getID(){
        return idNum;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setEndDate(Calendar endDate) {
        this.endDate = endDate;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public void setStartDate(Calendar startDate) {
        this.startDate = startDate;
    }
    
    
    
    @Override
    public String toString(){
        return "Akce ID: " + idNum + ". Název: " + name + ", Místo konání: " +
                place + ". Koná se od: " + getDateString(startDate)+ " do: " +
                getDateString(endDate) + ".";
    }
    
    
    private String getDateString(Calendar date){
        int d = date.get(Calendar.DATE);
        int m =date.get(Calendar.MONTH);
        int r = date.get(Calendar.YEAR);
        Formatter f = new Formatter();
        f.format("%02d.%02d.%d", d, m, r);
        return f.toString();
    }
}
