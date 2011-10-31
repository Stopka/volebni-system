package entities;

import entities.utils.Generator;

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

    private Admin(boolean admin, boolean superAdmin) {
        this.admin = admin;
        this.superAdmin = superAdmin;
        login = Generator.randomString(LEN);
        psswd = Generator.randomString(LEN);
    }

    public static Admin getNewSuperAdmin() {
        return new Admin(true, true);
    }

    public static Admin getNewAdmin() {
        return new Admin(true, false);
    }

    public static Admin getNewRegistrator() {
        return new Admin(false, false);
    }
    /**
     * 
     * @return 
     */
    public boolean isAdmin() {
        return admin;
    }

    /**
     * 
     * @param admin 
     */
    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    /**
     * 
     * @return 
     */
    public boolean isSuperAdmin() {
        return superAdmin;
    }

    /**
     * 
     * @param superAdmin 
     */
    public void setSuperAdmin(boolean superAdmin) {
        this.superAdmin = superAdmin;
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
     * Metoda vygeneruje náhodný login uživatele.
     */
    public void generateLogin() {
        login = Generator.randomString(LEN);
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

    /**
     * Metoda vygeneruje náhodné heslo pro uživatele.
     */
    public void generatePassword() {
        this.psswd = Generator.randomString(LEN);
    }
}