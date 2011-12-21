/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package businesstier.utils;

/**
 *
 * @author Lahvi
 */
public class ValidatorUtil {
    public static boolean isntEmpty(String value){
        if(value == null || value.isEmpty())
            return false;
        return true;
    }
}
