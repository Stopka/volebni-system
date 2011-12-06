/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package businesstier;

import core.SystemRegException;
import core.data_tier.entities.Additional;
import core.data_tier.entities.Participant;
import java.util.Collection;

/**
 * Interface k fasádě pracující s operacemi nad účastníkem.
 * @author Lahvi
 */
public interface IParticipantFacade {
    /**
     * Vytvoří nového účastníka. K tomu metoda potřebuje jeho křestní jméno a příjmení,
     * email, číslo OP nebo rodné číslo nebo jiný číselný údaj ({@code personalID}), 
     * dobrovolný údaj o nepovinných parametrech {@code adInfo} a kolekci s přiřazenými
     * akcemi. Kolekce musí mít délku > 0.
     * @param fName Křestní jméno.
     * @param lName Příjmení.
     * @param email Email účastníka.
     * @param personalID Rodné číslo / číslo OP / jiný číselný identifikátor účasntíka.
     * @param actionIDs Kolekce přiřazených akcí.
     * @param adInfo Nepoviné parametry. Může být {@code null}.
     */
    void createParticipant(String fName, String lName, String email, long personalID, Collection<Long> actionIDs, Additional adInfo);
    /**
     * Vrací účastníka vyhledaného podle jeho ID získaného z parametru {@code id}.
     * @param id ID vyhledávaného účastníka.
     * @return Vyhledaný účsatník.
     * @throws SystemRegException Vyvolá vyjímku pokud neexistuje účastník s daným ID.
     */
    Participant getParticipant(long id) throws SystemRegException;
    /**
     * Zkompletuje registraci, tzn. vygeneruje heslo a login, účastníkovi s daným
     * ID. Zároveň je potřeba ID akce, ke které se login a heslo vztahují. Po 
     * dokončení registrace se automaticky zaznamená příchod účastníkovi.
     * @param id ID vyhledávaného účastníka.
     * @param actionID ID akce pro kterou se registrace kompletuje.
     * @throws SystemRegException Vyvolá vyjímku pokud neexistuje účastník s daným ID.
     */
    void completeReg(long id, long actionID) throws SystemRegException;
    /**
     * Odstraní účastníka s daným ID.
     * @param id ID daného účastníka.
     * @throws SystemRegException Vyvolá vyjímku pokud neexistuje účastník s daným ID.
     */
    void deleteParticipant(long id) throws SystemRegException;
    /**
     * Změní přítomnost účastníkovi s daným ID {@code id} na akci s ID {@code actionID}.
     * Pokud byl doposud účastník přítomen zaznamená se odchod a naopak.
     * @param id ID vyhledávaného účastníka.
     * @param actionID ID akce pro kterou se příchod/odchod vztahuje.
     * @throws SystemRegException Vyvolá vyjímku pokud neexistuje účastník s daným ID, nebo pokud
     * daný účastník nemá dokončenou registraci.
     */
    void changePresence(long id, long actionID) throws SystemRegException;
    /**
     * Vrátí všechny účastníky, kteří se účastní dané akce.
     * @param actionID ID dané akce, pro kterou chceme vyhledat účastníky.
     * @return Kolekce najitých účastníků.
     */
    Collection<Participant> getAllParticipants(long actionID);
    /**
     * Vrátí všechny účasníka, kteří jsou zrovna na dané akaci přítomni.
     * @param actionID ID akce, pro kterou chceme vyhledat účastníky.
     * @return Kolekce vyhledaných účastníků.
     */
    Collection<Participant> getPresent(long actionID);
    /**
     * Vrátí všechny účastníky, kteří na dané akci zrovna nejsou přítomni.
     * @param actionID ID akce, pro kterou chceme vyhledat účastníky.
     * @return Kolekce vyhledaných účastníků.
     */
    Collection<Participant> getAbsent(long actionID);   
    /**
     * Vrátí všechny účastníky, kteří na dané akci mají dokončenou registraci.
     * @param actionID ID akce, pro kterou chceme vyhledat účastníky.
     * @return Kolekce vyhledaných účastníků.
     */
    Collection<Participant> getCompleteRegParticipants(long actionID);
    /**
     * Vrátí všechny účastníky, kteří na dané akci nemají dokončenou registraci.* 
     * @param actionID ID akce, pro kterou chceme vyhledat účastníky.
     * @return Kolekce vyhledaných účastníků.
     */
    Collection<Participant> getIncompleteRegParticipants(long actionID);
    /**
     * Upraví přihlašovací údaje (jméno a heslo) danému účastníkovi, vyhledáného
     * podle parametru {@code id}.
     * @param id ID vyhledávaného účastníka.
     * @param newLogin Nový login účastníka.
     * @param newPassword Nové heslo účastníka.
     * @throws SystemRegException Vygeneruje vyjímku pokud neexistuje žádný účastník
     * s daným ID.
     */
    void editLogins(long id, String newLogin, String newPassword) throws SystemRegException;    
    /**
     * Upraví povinné údaje (email, jméno, číslo OP / RČ) danému účastníkovi, 
     * vyhledaného podle jeho ID.
     * @param email Nový email.
     * @param firstName Nové křestní jméno.
     * @param lastName Nové příjmění.
     * @param id ID vyhledávaného účasntíka.
     * @throws SystemRegException Vygeneruje vyjímku pokud neexistuje žádný účastník
     * s daným ID.
     */
    void editParameters(String email, String firstName, String lastName, long cardID, long id) throws SystemRegException;
    /**
     * Upraví nepovinné parametry, účastníkovi vyhledanému podle jeho ID.
     * @param id ID vyhledávaného účastníka.
     * @param adParams Třída s nepovinnými parametry.
     * @throws SystemRegException Vygeneruje vyjímku pokud neexistuje žádný účastník
     * s daným ID.
     */
    void editAdditionals(long id, Additional adParams) throws SystemRegException;
    /**
     * Danému účastníkovi, vyhledaného podle jeho ID, přidá účast na nové akci.
     * @param id ID vyhledávaného účastníka. Pravděpodobně použito jen v metodě
     * {@link IParticipantFacade#addActions(long, java.util.Collection)}.
     * @param actionID ID přidávané akce.
     * @throws SystemRegException Vygeneruje vyjímku pokud neexistuje žádný účastník
     * s daným ID.
     */
    public void addAction(long id, long actionID) throws SystemRegException;
    /**
     * Aktualizuje danému účastníkovi seznam účastí na akcích. Nový seznam, se 
     * účastníkovi přidá formou kolekce v parametru {@code actions}. Nejprve se 
     * porová
     * s dosavadním seznamem a zkontroluje se, jestli nebyli nějaké akce odstraněny. 
     * Poté se projedou přidávané akce a přidají se ty, které ještě účastník nemá.
     * @param id ID vyhledávaného účastníka.
     * @param actions Kolekce aktualizovaných akcí.
     * @throws SystemRegException Vygeneruje vyjímku pokud neexistuje žádný účastník
     * s daným ID.
     */
    public void addActions(long id, Collection<Long> actions) throws SystemRegException;
    /**
     * Danému účastníkovi odebere účast na dané akci. Účastník se vyhledá podle
     * jeho ID. Pravděpodobně použité pouze v {@link IParticipantFacade#addActions(long, java.util.Collection)}.
     * @param id ID vyhledávaného účastníka.
     * @param actionID ID odebírané akce.
     * @throws SystemRegException Vygeneruje vyjímku pokud neexistuje žádný účastník
     * s daným ID.
     */
    public void removeAction(long id, long actionID) throws SystemRegException;
}
