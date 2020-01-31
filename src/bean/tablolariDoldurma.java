/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import static bean.baglantilar.baglantiOlustur;
import graphicalInterface.anaSayfa;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Recep
 */
public class tablolariDoldurma {

    static Connection conn;

    public tablolariDoldurma() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        conn = baglantiOlustur();
    }

    public static DefaultTableModel anaSayfaStokTablosunuDoldur(DefaultTableModel dtmStok) throws SQLException {
        ResultSet resultset;
        String sorgu = "SELECT * FROM ilaclar";
        Statement stmt = conn.createStatement();
        resultset = stmt.executeQuery(sorgu);
        while (resultset.next()) {
            int ILAC_ID = resultset.getInt("ILAC_ID");
            String ADI = resultset.getString("ADI");
            int DISTRIBUTOR_ID = resultset.getInt("DISTRIBUTOR_ID");
            double FIYAT = resultset.getDouble("FIYAT");
            int ADET = resultset.getInt("ADET");
            dtmStok.addRow(new Object[]{ILAC_ID, ADI, DISTRIBUTOR_ID, FIYAT, ADET});
        }
        return dtmStok;
    }

    public static DefaultTableModel anaSayfaSatisTablosunuDoldur(DefaultTableModel dtmSatis) throws SQLException {
        ResultSet resultset;
        String sorgu = "SELECT * FROM satis";
        Connection conn = baglantiOlustur();
        Statement stmt = conn.createStatement();
        resultset = stmt.executeQuery(sorgu);
        while (resultset.next()) {
            int SATIS_ID = resultset.getInt("SATIS_ID");
            String CALISAN_TC = resultset.getString("CALISAN_TC");
            String HASTA_TCNO = resultset.getString("HASTA_TCNO");
            int ILAC_ID = resultset.getInt("ILAC_ID");
            int SATILAN_ADET = resultset.getInt("SATILAN_ADET");
            double FIYAT = resultset.getDouble("FIYAT");
            dtmSatis.addRow(new Object[]{SATIS_ID, CALISAN_TC, HASTA_TCNO, ILAC_ID, SATILAN_ADET, FIYAT});
        }
        return dtmSatis;

        /*dtmSatis.setRowCount(0);
        ResultSet rs;
        Statement stmt;
        try {
            
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM satis");
            while (rs.next()) {
                int SATIS_ID = rs.getInt("SATIS_ID");
                String CALISAN_TC = rs.getString("CALISAN_TC");
                String HASTA_TCNO = rs.getString("HASTA_TCNO");
                int ILAC_ID = rs.getInt("ILAC_ID");
                int SATILAN_ADET = rs.getInt("SATILAN_ADET");
                double FIYAT = rs.getDouble("FIYAT");
                dtmSatis.addRow(new Object[]{SATIS_ID, CALISAN_TC, HASTA_TCNO, ILAC_ID, SATILAN_ADET, FIYAT});
            }
        } catch (SQLException ex) {
            Logger.getLogger(anaSayfa.class.getName()).log(Level.SEVERE, null, ex);
        } 
         */
    }

    public static DefaultTableModel distributorEkleninTablosunuDoldur(DefaultTableModel dtmDist) throws SQLException {
        ResultSet rs;
        Connection con = baglantiOlustur();
        String sorgu = "SELECT * FROM distributor";
        Statement stmt = con.createStatement();
        rs = stmt.executeQuery(sorgu);
        while (rs.next()) {
            int dist_id = rs.getInt("DISTRIBUTOR_ID");
            String adi = rs.getString("ADI");
            String telNo = rs.getString("TELNO");
            String mailAdresi = rs.getString("MAILADRESI");
            String adres = rs.getString("ADRES");
            String ilce = rs.getString("ILCE");
            String sehir = rs.getString("SEHIR");
            dtmDist.addRow(new Object[]{dist_id, adi, telNo, mailAdresi, adres, ilce, sehir});
        }
        return dtmDist;
    }
}
