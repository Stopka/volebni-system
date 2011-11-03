package core.data_tier.entities;

import businesstier.utils.Generator;

/**
 * 
 * @author Lahvi
 * @version 1.0
 * @created 31-10-2011 0:11:30
 */
public class Admin {

    private static int LEN = 8;
    private boolean admin;
    private boolean superAdmin;
    private String login;
    private String psswd;
    private Role role;

    public Admin(Role role, String login, String password) {
        this.role = role;
        this.login = login;
        this.psswd = password;
    }
    public void setRole(Role role){
        
    }
    
    public Role getRole(){
        return this.role;
    }

    /**
     * Metoda nastavuje nové přihlašovací jméno uživatele.
     * @param login Nové přihlašovací jméno.
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * Metoda vrací přihlašovací jméno uživatele.
     * @return Přihlašovací jméno uživatele.
     */
    public String getLogin() {
        return this.login;
    }


    /**
     * Metoda nastavuje nové přihlašovací heslo uživatele.
     * @param password Nové heslo.
     */
    public void setPassword(String password) {
        this.psswd = password;
    }

    /**
     * Metoda vrací heslo uživatele.
     * @return Helso uživatele.
     */
    public String getPassword() {
        return this.psswd;
    }
}