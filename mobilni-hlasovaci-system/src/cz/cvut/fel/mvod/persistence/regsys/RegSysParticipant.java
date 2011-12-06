/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fel.mvod.persistence.regsys;

/**
 * Entita ucastnika z registracniho systemu
 * @author Ondra
 */
import java.util.Calendar;

/**
 * Třída user reprezentuje uživatele systému, při počáteční registraci
 * @author Lahvi
 */
public class RegSysParticipant {
    
    
    
    private static final int LEN = 8;
    
    private String name;
    private String address;
    private String login;
    private String password;
    private int personalCardID;
    private RegSysPresence presence;
    private boolean completeReg;
   
    /**
     * 
     * @param name
     * @param address
     * @param personalCardID 
     */
    public RegSysParticipant(String name, String address, int personalCardID){
        this.name = name;
        this.address = address;
        this.personalCardID = personalCardID;
        completeReg = false;
        presence = new RegSysPresence();
    }

    /**
     * 
     * @return 
     */
    public String getAddress() {
        return address;
    }

    /**
     * 
     * @param address 
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 
     * @return 
     */
    public int getpersonalCardID() {
        return personalCardID;
    }
    
    /**
     * 
     * @param personalCardID 
     */
    public void setpersonalCardID(int personalCardID) {
        this.personalCardID = personalCardID;
    }

    /**
     * 
     * @return 
     */
    public String getLogin() {
        return login;
    }

    /**
     * 
     * @param login 
     */
    public void setLogin(String login) {
        this.login = login;
    }
    /**
     * 
     * @return 
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name 
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     * @return 
     */
    public String getPassword() {
        return password;
    }
    
    /**
     * 
     * @param passwd 
     */
    public void setPassword(String passwd){
        this.password = passwd;
    }
    
    public RegSysPresence getPresence(){
        return presence;
    }

    /**
     * @return the completeReg
     */
    public boolean isCompleteReg() {
        return completeReg;
    }

    /**
     * @param completeReg the completeReg to set
     */
    public void setCompleteReg() {
        this.completeReg = true;
    }
}
