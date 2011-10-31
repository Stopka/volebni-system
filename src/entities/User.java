/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import entities.utils.Generator;
import java.util.Calendar;

/**
 * Třída user reprezentuje uživatele systému, při počáteční registraci
 * @author Lahvi
 */
public class User {
    
    
    
    private static final int LEN = 8;
    
    private String name;
    private String address;
    private String login;
    private String password;
    private int personalCardID;
    private Presence presence;
   
    /**
     * 
     * @param name
     * @param address
     * @param personalCardID 
     */
    public User(String name, String address, int personalCardID){
        this.name = name;
        this.address = address;
        this.personalCardID = personalCardID;
        presence = new Presence();
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
     */
    public void generateLogin(){
        this.login = Generator.randomString(User.LEN);
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

    /**
     * Metoda vygneruje náhodné heslo.
     */
    public void generatePassword() {
        password = Generator.randomString(User.LEN);
    }
    
    public void setPresence(boolean presence){
        this.presence.setPresence(presence);
    }
    
    public boolean isPresence(){
        return this.presence.isPresent();
    }
    
    public boolean isPresence(Calendar currentTime){
        return presence.isPresent(currentTime);
    }
    
}
