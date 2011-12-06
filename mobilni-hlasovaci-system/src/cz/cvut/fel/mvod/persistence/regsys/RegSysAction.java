package cz.cvut.fel.mvod.persistence.regsys;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class RegSysAction {
    
    private int id;
    private String description;
    private Calendar startDate, endDate;
    private String place;
    private String name;
    
    private List<RegSysParticipant> participants;
    
    public RegSysAction(int id, Calendar startDate, Calendar endDate, String place, String name){
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.place = place;
        this.name = name;
        participants = new ArrayList<RegSysParticipant>();
    }
    
    public RegSysAction(Calendar startDate, Calendar endDate, String place, String name, String description){
        this.startDate = startDate;
        this.endDate = endDate;
        this.place = place;
        this.name = name;
        this.description = description;
        participants = new ArrayList<RegSysParticipant>();
    }
    
    public RegSysAction(int id, String name){
        this.id = id;
        this.name = name;
    }
    
    public void addUser(RegSysParticipant user){
        participants.add(user);
    }

    public String getDescription() {
        return description;
    }

    public Calendar getEndDate() {
        return endDate;
    }

    public RegSysParticipant getUser(RegSysParticipant u) {
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
    
    public void setEndDCalendar(Calendar startDate){
        this.startDate = startDate; 
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    public String getName(){
        return this.name;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }
    @Override
    public String toString() {
        return name;
    }
    
}