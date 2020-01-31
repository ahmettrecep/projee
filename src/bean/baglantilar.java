/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Recep
 */
public class baglantilar {
    public static Connection baglantiOlustur() throws SQLException{
        Connection conn;
        conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/proje_eczane", "root", "");
        return conn;
    }
    public static void baglantiyiKapat(Connection conn) throws SQLException{
        conn.close();
    }
}
