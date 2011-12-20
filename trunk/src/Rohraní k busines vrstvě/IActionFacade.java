/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package businesstier;

import core.SystemRegException;
import core.data_tier.entities.Action;
import java.util.Calendar;
import java.util.Collection;

/**
 * Interface k fasádě pracující s operacemi nad akcemi.
 * @author Lahvi
 */
public interface IActionFacade {
    
    /**
     * Metoda, která vytváří novu akci. Potřebuje vyžaduje název, místo konání
     * a data záhajení a ukončení. Popis akce může být null. Pro případ potřeby 
     * vrací vytvořenou akci.
     * @param name Název akce.
     * @param place Místo konání akce.
     * @param desc Popis akce. Nepovinný.
     * @param startDate Datum zahájení akce.
     * @param endDate Datum ukončení akce.
     * @return Vytvořená akce.
     */
    Action createAction(String name, String place, String desc, Calendar startDate, Calendar endDate) throws SystemRegException;
    /**
     * Vrací všechny vytvořené akce. Nevím jestli je eště někde potřeba. Pravděpodobně 
     * nahrazeno {@link IActionFacade#getActions(java.util.Collection)}, kdy se vrací
     * pouze ty akce, na které má daný uživatel/účastník právo.
     * @return Kolekce všech akcí.
     */
    Collection<Action> getActions() throws SystemRegException;
    /**
     * Vrací všechny akce, na které má daný uživatel/účastník právo. Metoda dané
     * akce vyhledá podle jejich IDček získaných z parametru {@code actionIDs}.
     * @param actionIDs Kolekce IDček vyhledávaných akcí.
     * @return Vrací kolekci s vyhledánými akcemi.
     */
    Collection<Action> getActions(Collection<Long> actionIDs) throws SystemRegException;
    /**
     * Změní k akci s daným ID dta zahájení a ukončení.
     * @param ID ID vyhledáváné akce.
     * @param stDate Parametr s novým datem zahájení.
     * @param endDate Parametr se starým datem zahájení.
     * @throws SystemRegException Vyvolá vyjímku pokud neexistuje akce s daným ID.
     */
    void changeDates(long ID, Calendar stDate, Calendar endDate) throws SystemRegException;
    /**
     * Změní parametry akce s daným ID. Mění název, místo a popis. Popis může být
     * null.
     * @param ID ID vyhledávané akce.
     * @param name Nový název akce.
     * @param place Nové místo konání dané akce.
     * @param desc Nový popis akce. Nepoviný.
     * @throws SystemRegException Vyvolá vyjímku pokud neexistuje akce s daným ID.
     */
    void changeParams(long ID, String name, String place, String desc) throws SystemRegException;
    /**
     * Odstraňuje akci s daným ID. Zároveň je nutné projít všechny uživatele a 
     * účastníky a odebrat jim práva na tuto akci!
     * @param ID ID odstraňované akce.
     * @throws SystemRegException Vyvolá vyjímku pokud neexistuje akce s daným ID.
     */ 
    void deleteAction(long ID) throws SystemRegException;
    /**
     * Vrací akci vyhledanou podle jména. Pravděpodobně není použito nikde.
     * @param name Jméno vyhledávané akce.
     * @return Vyhledaná akce.
     * @throws SystemRegException Vyvolá vyjímku pokud neexistuje akce s daným jménem.
     */
    Action getAction(String name) throws SystemRegException;
    /**
     * Vrací akci vyhledanou podle ID.
     * @param ID ID vyhledávané akce.
     * @return Vyhledaná akce.
     * @throws SystemRegException Vrací akci vyhledanou podle jména.
     */
    Action getAction(long ID) throws SystemRegException;
}
