/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package businesstier.utils;

import java.util.Random;

/**
 *
 * @author Lahvi
 */
public class Generator {
    
    private static String alpha_num = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    /**
     * Délka generovaného řetězce. Pokud bude menšá než 0 a větší než 100 tak
     * se metoda {@link Generator#randomString()} neprovede a vrátí {@code null}.
     */
    public static final int LENGTH = 8;
    
    static Random rnd = new Random();

    /**
     * Vygeneruje náhodný řetězec znaků ({@link String}), který bude mít délku
     * zadanou parametrem.
     * 
     * @param len Délka náhodného řetězce.
     * 
     * @return Vygnerovaný řetězec.
     */
     public static String randomString(int len) {
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            sb.append(alpha_num.charAt(rnd.nextInt(alpha_num.length())));
        }
        return sb.toString();
    }
    /**
     * Vygeneruje náhodný řetězec znaků ({@link String}), který bude mít délku 
     * podle statické proměnné třídy {@link Generator} {@link Generator#LENGTH}.
     * Pokud chcete tuto délku změnit proměnná je veřejná a jde měnit.
     * 
     * @return Vygnerovaný řetězec.
     */
    public static String randomString() {
        
        StringBuilder sb = new StringBuilder(Generator.LENGTH);
    
        for (int i = 0; i < Generator.LENGTH; i++) {
            sb.append(alpha_num.charAt(rnd.nextInt(alpha_num.length())));
        }
        return sb.toString();
    }
    
    
}
