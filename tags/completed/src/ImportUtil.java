/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package businesstier.utils;

/**
 *
 * @author Vendik
 */
import businesstier.IParticipantFacade;
import core.SystemRegException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.StringTokenizer;
import presentation.Globals;

/**
 *
 * @author Vendik
 */
public class ImportUtil {

    /*
     * Metoda přečte názvy jednotlivých sloupců vyskytujících se v CSV souboru, vrátí list stringů,
     * který obsahuje názvy jednotlivých sloupců v jejich pořadí
     */
    static public List<String> readHeaders(String file) throws SystemRegException {
        BufferedReader bufRdr = null;
        try {
            List<String> numbers = new ArrayList<String>();
            bufRdr = new BufferedReader(new FileReader(file));
            String line = null;
            int col = 0;
            line = bufRdr.readLine();
            StringTokenizer st = new StringTokenizer(line, ";");
            while (st.hasMoreTokens()) {
                numbers.add(st.nextToken());
                col++;
            }
            col = 0;
            return numbers;
        } catch (FileNotFoundException ex) {
            throw new SystemRegException("Nastala vyjímka při načítání souboru.\n "
                    + "Soubor pravděpodobně neexistuje!\n"
                    + ex.getMessage());
        } catch (IOException ex) {
            throw new SystemRegException("Nastala vyjímka při načítání souboru.\n "
                    + "Zkontrolujte že má sobor požadovaný formát!\n"
                    + ex.getMessage());
        } finally {
            try {
                if (bufRdr != null) {
                    bufRdr.close();
                }
            } catch (IOException ex) {
                System.out.println("Tak to už je fakt error");
            }
        }
    }

    /*
     * Uživatel předá funkci pole indexů začínajících od nuly, první index určuje, 
     * ve kterém sloupci CSV souboru se vyskytují křestní jména účastníků, 
     * druhý určuje, ve kterém se nacházejí příjmení, třetí určuje, ve kterém 
     * sloupci se vyskztují emialy a 4. sloupce jsou číslo OP nebo rodné číslo účastníka.
     * 
     */
    public static void importAction(List<Integer> indexy, String file, long actionID) throws SystemRegException {
        BufferedReader bufRdr = null;
        List<String> parametry = new ArrayList<String>();
        try {
            bufRdr = new BufferedReader(new FileReader(file));
            String line = bufRdr.readLine();
            if (indexy.size() == 4) {
                while ((line = bufRdr.readLine()) != null) {
                    System.out.println(line);
                    StringTokenizer st = new StringTokenizer(line, ";");
                    while (st.hasMoreTokens()) {
                        parametry.add(st.nextToken());
                    }
                    String fName = parametry.get(indexy.get(0)).split("\"")[0].trim();
                    String lName = parametry.get(indexy.get(1)).split("\"")[0].trim();
                    String email = parametry.get(indexy.get(2)).split("\"")[0].trim();
                    String longID = parametry.get(indexy.get(3)).split("\"")[0].trim();
                    try {
                        long cardID = Long.parseLong(longID);
                        Collection<Long> actionIDs = new ArrayList<Long>();
                        actionIDs.add(actionID);
                        IParticipantFacade pfcd = Globals.getInstance().getParticipantOps();
                        pfcd.createParticipant(fName, lName, email, cardID, actionIDs, null);
                        parametry.clear();
                    } catch (NumberFormatException ex) {
                        throw new SystemRegException("nastala chyba při parsování čísla OP / rodného čísla");
                    }
                }
            } else {
                throw new SystemRegException("Nelze importovat účastníky z dúvodu: málo parametrů!");
            }
        } catch (IOException ex) {
            throw new SystemRegException("Nastala chyba při importování účastníků");
        } finally {
            try {
                bufRdr.close();
            } catch (IOException ex) {
                System.out.println("totální error");
            }
        }

    }
}