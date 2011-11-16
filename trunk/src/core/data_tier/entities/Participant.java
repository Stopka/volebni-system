/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package core.data_tier.entities;

/**
 * Třída user reprezentuje uživatele systému, při počáteční registraci
 * @author Lahvi
 */
public class Participant {

    private String name;
    private String address;
    private String login;
    private String password;
    private int personalCardID;
    private String email;
    private Presence presence;
    private boolean completeReg;

    /**
     * 
     * @param name
     * @param address
     * @param personalCardID 
     */
    public Participant(String name, String email, int personalCardID) {
        this.name = name;
        this.email = email;
        this.personalCardID = personalCardID;
        completeReg = false;
        presence = new Presence();
    }

    public Participant(String name, String email, String address, int personalCardID) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.personalCardID = personalCardID;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
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
    public void setPassword(String passwd) {
        this.password = passwd;
    }

    public Presence getPresence() {
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
    
    @Override
    public String toString(){
        
        return "Účastník " + name + ", č. OP: " +  personalCardID + ", Email: " 
                + email + (login != null ? ", Login: " + login : "");    
    }
}
