/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package businesstier.utils;

import java.util.*;

/**
 *
 * @author Lahvi
 */
public class CalendarUtil {
    
    /**
     * Metoda podle seznamu příchodů, odchodů a podle daného času pro daného účastníka
     * zjistí, zda byl na akci přítomen nebo ne.
     * 
     * @param arrivals Seznam příchodů na akci pro daného účastníka.
     * @param departures Seznam odchodů z akce pro daného účastníka.
     * @param currentTime Určitá doba pro kterou chceme zjistit přítomnost účastníka.
     * @return Vrací {@code true} pokud byl uživatel v době parametru {@param currentTime}
     * na akci přítomen, jinak vrací {@code false}.
     */
    public static boolean isPresent(List<Calendar> arrivals, List<Calendar> departures, Calendar currentTime){
        
        /*pokud je vice prichodu nez odchodu tak to znamena ze ucastnik je na akci
        pritomen, takze pokud je zjistovany cas vetsi nez posledni prichod tak
        je jasne ze je pritomen*/
        if(arrivals.size() > departures.size()){
            Calendar last = arrivals.get(arrivals.size()-1);
            if (last.compareTo(currentTime) <= 0) {
                return true;
            }
        }
        //jinak si zjistim interavaly pritomnosti podle paru prichod - odchod a
        //jestli currentTime patri do nejakeho intervalu
        for (int i = 0; i < departures.size(); i++) {
            if(currentTime.compareTo(arrivals.get(i)) >= 0 && currentTime.compareTo(departures.get(i)) < 0){
                return true;
            }
        }
        return false;
    }
}
/*
 * Calendar[] polePr = new Calendar[prichody.size()];
        Calendar[] poleOd = new Calendar[odchody.size()];
        odchody.toArray(poleOd);
        prichody.toArray(polePr);

        Calendar[][] intervaly = new Calendar[poleOd.length][2];
        for (int i = 0; i < intervaly.length; i++) {
            intervaly[i][0] = polePr[i];
            intervaly[i][1] = poleOd[i];
        }
        if (prichody.size() > odchody.size()) {
            Calendar last = polePr[prichody.size() - 1];
            if (last.compareTo(doba) == -1) {
                return true;
            }
        }
        for (int i = 0; i < odchody.size(); i++) {
            if(doba.compareTo(intervaly[i][0]) > 0 && doba.compareTo(intervaly[i][1]) < 0){
                return true;
            }
        }
 */