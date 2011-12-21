/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package businesstier;

import core.SystemRegException;
import core.data_tier.entities.Role;
import core.data_tier.entities.User;
import java.util.Collection;

/**
 * Interface k fasádě pracující s operacemi nad uživatelem.
 * @author Lahvi
 */
public interface IUserFacade {
    /**
     * Vytváří nového účastníka s daným loginem, heslem, rolí a kolekcí akcí, které
     * bude mít právo ovlivňovat. Pro případ nového uživatele vrátí.
     * @param login Login nového účastníka.
     * @param password Heslo nového účastníka.
     * @param role Role nového účastníka. 
     * @param actionIDs Kolekce akcí, které bude mít uživatel právo ovlivňovat.
     * @return Vytvořený uživatel.
     */
    User createUser(String login, String password, Role role, Collection<Long> actionIDs) throws SystemRegException;
    /**
     * Vrací uživatle s daným loginem.
     * @param login Login podle, kterého se účastník vyhledá.
     * @return Vrací vyhledaného uživatele.
     * @throws SystemRegException Vygeneruje vyjímku pokud neexistuje žádný 
     * uživatel s daným loginem.
     */
    User getUser(String login) throws SystemRegException;
    /**
     * Odstraňuje uživatele s daným loginem.
     * @param login Login vyhledávaného uživatele.
     * @throws SystemRegException Vygeneruje vyjímku pokud neexistuje žádný 
     * uživatel s daným loginem.
     */
    void deleteUser(String login) throws SystemRegException;
    /**
     * Upraví parametry daného uživatele, vyhledaného podle loginu v parametru
     * {@code login}. Upraví uživateli login, heslo, roli a aktualizuje mu seznam
     * akcí, ke kterým má právo přistupovat. 
     * Při aktulazivání akcí se nejprve projdou stávají akce a pokud některá
     * chybí tak se odstraní. Poté se přidají chybějící akce z nové kolekce.
     * @param login Login, podle kterého se uživatel bude vyhledávat.
     * @param newLogin Nový login.
     * @param newPassword Nové heslo.
     * @param newRole Nová role.
     * @param newActionIDs Nový seznam akcí.
     * @throws SystemRegException Vygeneruje vyjímku pokud neexistuje žádný 
     * uživatel s daným loginem.
     */
    void editUserParametrs(String login, String newLogin, String newPassword, Role newRole, Collection<Long> newActionIDs) throws SystemRegException;
    /**
     * Asi není použito
     * @param login
     * @param newLogin
     * @param newPassword
     * @throws SystemRegException 
     */
    void editLogin(String login, String newLogin, String newPassword) throws SystemRegException;
    /**
     * Vrací všechny uživatele.
     * @return 
     */
    Collection<User> getUsers() throws SystemRegException;
    /**
     * Vrací kolekci akcí, na které má uživatel práve sahat.
     * @param login Login vyhledávané uživatele.
     * @return Kolekce s ID akcí, ke kterým má uživatel právo.
     * @throws SystemRegException SystemRegException Vygeneruje vyjímku pokud neexistuje žádný 
     * uživatel s daným loginem.
     */
    public Collection<Long> getActions(String login) throws SystemRegException;
    /**
     * Vrátí všechny uživatele, kteří mají roli nižší než je role v parametru
     * {@code role}.
     * @param role Role přihlášeného uživatele.
     * @return Kolekce nižších rolí.
     */
    Collection<User> getUsers(Role role) throws SystemRegException;
    /**
     * Odstraní uživateli s daným loginem akci danou parametrem {@code removeID}.
     * @param login Login vyhledávaného uživatele.
     * @param removeID odstraňovaná akce.
     * @throws SystemRegException Vygeneruje vyjímku pokud neexistuje žádný 
     * uživatel s daným loginem, nebo když uživatel nemá přiřezenou danou akci.
     */
    void removeAction(String login, long removeID) throws SystemRegException;
    /**
     * Přidává úživateli s daným loginem právo na danou akci.
     * @param login
     * @param a
     * @throws SystemRegException 
     */
    void addActions(String login, Collection<Long> actionIDs) throws SystemRegException;
    
    void addAction(String login, long actionID) throws SystemRegException;
}
