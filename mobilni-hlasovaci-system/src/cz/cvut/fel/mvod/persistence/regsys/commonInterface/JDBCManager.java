package cz.cvut.fel.mvod.persistence.regsys.commonInterface;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

/**
 * Třída zabaluje {@code DataAccess} třídy pro práci s databázema a pokytuje 
 * @author Lahvi
 */
public class JDBCManager {

    private static int connID = 0;
    private static PrintWriter pw;

    /*
     * Toto je statický konstruktor pro inicilaiaci logů.
     */
    static {
        try {
            FileWriter fw = new FileWriter("regSystem.log");
            pw = new PrintWriter(fw);
            DriverManager.setLogWriter(pw);
            DriverManager.getLogWriter().write("Vytvořen logger pro DriverManager...");
        } catch (IOException ex) {
            System.out.println("Chyba ve vytváření logu: " + ex.getMessage());
        }
    }

    /**
     * Vytváří nové připojení do databáze. Asi by bylo lepší použití connection
     * poolu. Třída je dostupná jen z balíčku {@code core.data_tier}.  
     * @return Vrací nový objekt {@link Connection}.
     * @throws SystemRegException V metodě se mohou vyskytnou výjimky, typu 
     * {@link SQLException} a {@link ClassNotFoundException}. SQL výjimka se může
     * vyskytnou při vytváření spojení a ClassNotFound při zíkávání třídy ovladače
     * pro MySQL databázi. Obě tyto chyby zabaluje výjimka {@link SystemRegException}, 
     * která z metody pokračuje dále.
     */
    static Connection getConnection() throws SystemRegException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb", "root", "");
            createdConnLog(con);
            return con;
        } catch (SQLException ex) {
            JDBCManager.writeLog("Něco se pokazilo při vytváření spojení! " + ex.getMessage());
            throw new SystemRegException("Něco se pokazilo při vytváření spojení!" + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            JDBCManager.writeLog("Něco se pokazilo při vytváření spojení! " + ex.getMessage());
            throw new SystemRegException("Něco se pokazilo při vytváření spojení!" + ex.getMessage());
        }
    }

    /**
     * Uzavře objekt {@link Connection}. Pokud při ukončování spojení s objektem
     * nastane chyba, zapíše se do logu. Třída je dostupná jen z balíčku {@code core.data_tier}.
     * @param conn Uzavíraný objekt. 
     */
    static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                DriverManager.getLogWriter().write("Při ukončování spojení se vyskytla chyba!");
            }
        }
    }

    /**
     * Uzavře objekt {@link ResultSet}. Pokud při ukončování spojení s objektem
     * nastane chyba, zapíše se do logu. Třída je dostupná jen z balíčku {@code core.data_tier}.
     * @param rset Uzavíraný objekt. 
     */
    static void closeResultSet(ResultSet rset) {
        if (rset != null) {
            try {
                rset.close();
            } catch (SQLException e) {
                DriverManager.getLogWriter().write("Při ukončování ResultSet se vyskytla chyba!");
            }
        }
    }

    /**
     * Uzavře objekt {@link Statement}. Pokud při ukončování spojení s objektem
     * nastane chyba, zapíše se do logu. Třída je dostupná jen z balíčku {@code core.data_tier}.
     * @param stmt Uzavíraný objekt.
     */
    static void closeStatement(Statement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                DriverManager.getLogWriter().write("Při ukončování ResultSet se vyskytla chyba!");
            }
        }
    }

    /**
     * Zapíše libovolnou informaci z parametru do logu {@link DriverManager}.
     * @param message Zpráva, která má být zapsána do logu.
     */
    public static void writeLog(String message) {
        DriverManager.getLogWriter().write(message);
    }

    /**
     * Zapíše do logu info o vytvoření nového spojení.
     * @param conn Connection objekt nového připojení.
     */
    private static void createdConnLog(Connection conn) {
        String desc = "Vytvořeno spojení ID: " + (++connID) + "\nPopis Spojeni: " + conn.toString();
        writeLog(desc);
    }

    /**
     * Vrací jedinou instanci sigletonu třídy {@link ActionDAO}. Ta poskytuje 
     * dtb. operace pro práci s akcemi.
     * @return Instance třídy {@link ActionDAO}.
     */
    public static ActionDAO getActionDAO() {
        return ActionDAO.getInstance();
    }

    /**
     * Vrací jedinou instanci sigletonu třídy {@link ParticipantDAO}. Ta posyktuje
     * dtb. operace pro práci s akcemi.
     * @return Instance třídy {@link ParticipantDAO}.
     */
    public static ParticipantDAO getParticipantDAO() {
        return ParticipantDAO.getInstance();
    }

    /**
     * Vrací jedinou instanci sigletonu třídy {@link UserDAO}. Ta poskytuje dtb.
     * operace pro práci s uživateli.
     * @return Instance třídy {@link UserDAO}.
     */
    public static UserDAO getUserDAO() {
        return UserDAO.getInstance();
    }
}
