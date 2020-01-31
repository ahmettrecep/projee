/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import static bean.baglantilar.baglantiOlustur;
import static bean.tablolariDoldurma.conn;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/*
Toplam fiyat, gönderilecek yerde hesaplanacak.
Fiyat, çağrıldığı yerde hesaplanacak.
Stoktan, satılan adeti çıkar. (YAPILDI.)
 */
/**
 *
 * @author Recep
 */
public class DAO {

    public DAO() throws ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
    }

    public static boolean ilacStoktaVarMi(String ilac_adi) throws SQLException {
        boolean sonuc = false;
        Connection conn2 = baglantiOlustur();
        String aramaSorgusu = "SELECT * FROM ilaclar WHERE ADI = " + "'" + ilac_adi + "'";
        Statement stmt = conn2.createStatement();
        ResultSet aramaRs = stmt.executeQuery(aramaSorgusu);
        if (aramaRs.next()) {
            sonuc = true;
        } else {
            sonuc = false;
        }
        System.out.println(sonuc);
        return sonuc;
        
    }

    public static void ilaclarTablosunaEkle(String adi, int distributorId, double fiyat, int adet) throws SQLException {
        Connection conn = baglantiOlustur();
        String aramaSorgusu = "SELECT * FROM ilaclar WHERE ADI = " + "'" + adi + "'";
        Statement stmt = conn.createStatement();
        ResultSet aramaRs = stmt.executeQuery(aramaSorgusu);

        String sorgu = "INSERT INTO `ilaclar`(`ADI`, `DISTRIBUTOR_ID`, `FIYAT`, `ADET`) VALUES (?,?,?,?)";
        PreparedStatement pstmt = (PreparedStatement) conn.prepareStatement(sorgu);
        pstmt.setString(1, adi);
        pstmt.setInt(2, distributorId);
        pstmt.setDouble(3, fiyat);
        pstmt.setInt(4, adet);
        pstmt.executeUpdate();

    }

    public static void tedarikEkle(int distributor_id, String ilac_adi, double birim_fiyat, int birim,
            double toplam_fiyat, String alim_tarihi) throws SQLException {
        ilaclarTablosunaEkle(ilac_adi, distributor_id, birim_fiyat + 2, birim);
        Connection conn = baglantiOlustur();
        String sorgu2 = "SELECT ILAC_ID FROM ilaclar WHERE ADI = ?";
        PreparedStatement pstmt2 = (PreparedStatement) conn.prepareStatement(sorgu2);
        pstmt2.setString(1, ilac_adi);
        ResultSet rs = pstmt2.executeQuery();
        rs.first();
        int id = rs.getInt("ilac_id");
        System.out.println(id);
        
        
        
        String sorgu = "INSERT INTO `alim`(`DISTRIBUTOR_ID`, `ILAC_ID`, `ILAC_ADI`, `BIRIM_FIYAT`, `BIRIM`, `TOPLAM_FIYAT`, `ALIM_TARIHI`) VALUES (?,?,?,?,?,?,?)";
        PreparedStatement pstmt = (PreparedStatement) conn.prepareStatement(sorgu);
        pstmt.setInt(1, distributor_id);
        pstmt.setInt(2, id);
        pstmt.setString(3, ilac_adi);
        pstmt.setDouble(4, birim_fiyat);
        pstmt.setInt(5, birim);
        pstmt.setDouble(6, toplam_fiyat);
        pstmt.setString(7, alim_tarihi);
        int etkilenenler = pstmt.executeUpdate();
    }

    public static void hastaEkle(String hasta_tc, String adi, String soyadi, String dogum_tarihi) throws SQLException {
        Connection conn = baglantiOlustur();
        String sorgu = "INSERT INTO `hastalar`(`HASTA_TCNO`, `ADI`, `SOYADI`, `DOGUM_TARIHI`) VALUES (?,?,?,?)";
        PreparedStatement pstmt = (PreparedStatement) conn.prepareStatement(sorgu);
        pstmt.setString(1, hasta_tc);
        pstmt.setString(2, adi);
        pstmt.setString(3, soyadi);
        pstmt.setString(4, dogum_tarihi);
        int etkilenenler = pstmt.executeUpdate();
    }

    public static void satisaEkle(String calisan_tc, String hasta_tcno, int ilac_id, int satilan_adet, double fiyat) throws SQLException {
        Connection conn = baglantiOlustur();
        String sorgu = "INSERT INTO `satis`(`CALISAN_TC`, `HASTA_TCNO`, `ILAC_ID`, `SATILAN_ADET`, `FIYAT`) VALUES (?,?,?,?,?)";
        PreparedStatement pstmt = (PreparedStatement) conn.prepareStatement(sorgu);
        pstmt.setString(1, calisan_tc);
        pstmt.setString(2, hasta_tcno);
        pstmt.setInt(3, ilac_id);
        pstmt.setInt(4, satilan_adet);
        pstmt.setDouble(5, fiyat);
        int etkilenenler = pstmt.executeUpdate();
    }

    public static void satilanIlacinStogunuAzalt(int ilac_id, int satilan_adet) throws SQLException {
        Connection conn = baglantiOlustur();
        Statement stmt = (Statement) conn.createStatement();
        String sorgu = "SELECT adet FROM ilaclar WHERE ILAC_ID=" + ilac_id;
        ResultSet rs = stmt.executeQuery(sorgu);
        rs.first();
        int adet = rs.getInt("adet");
        adet = adet - satilan_adet;
        String sorgu2 = "UPDATE ilaclar SET adet=" + adet + "WHERE ilac_id=" + ilac_id;
        stmt.executeUpdate(sorgu2);
    }

    public static boolean kimlikKontrolu(String kullaniciAdi, String parola) throws SQLException {
        boolean sonuc = false; // 
        String ka, sifre;
        Connection conn = baglantiOlustur();
        Statement stmt = (Statement) conn.createStatement(); 
        ResultSet rs;
        String sorgu = "SELECT KULLANICIADI,PAROLA FROM CALISAN WHERE KULLANICIADI=" + "'" + kullaniciAdi + "'";
        rs = stmt.executeQuery(sorgu); 

        if (rs.next()) { 
            ka = rs.getString(1);
            sifre = rs.getString(2); 
            if (ka.equals(kullaniciAdi) && sifre.equals(parola)) { 
                sonuc = true;
            } else {
                sonuc = false;
            }
        }

        return sonuc;
    }

    public static String girisBilgilerineAitTCgetir(String kullaniciAdi, String parola) throws SQLException {
        Connection conn = baglantiOlustur(); 
        Statement stmt = (Statement) conn.createStatement(); 
        ResultSet rs;
        String sorgu = "SELECT calisan_tcno FROM calisan where kullaniciadi=" + "'" + kullaniciAdi + "'" + "and parola=" + "'" + parola + "'"; 
        rs = stmt.executeQuery(sorgu);
        rs.first(); 
        String tcno = rs.getString(1); 
        return tcno;
    }

    public static void distributorEkle(String adi, String telno, String mailAdresi, String adres, String ilce, String sehir) throws SQLException {
        Connection conn = baglantiOlustur();
        String sorgu = "INSERT INTO `distributor`( `ADI`, `TELNO`, `MAILADRESI`, `ADRES`, `ILCE`, `SEHIR`) VALUES (?,?,?,?,?,?)";
        PreparedStatement pstmt = (PreparedStatement) conn.prepareStatement(sorgu);
        pstmt.setString(1, adi);
        pstmt.setString(2, telno);
        pstmt.setString(3, mailAdresi);
        pstmt.setString(4, adres);
        pstmt.setString(5, ilce);
        pstmt.setString(6, sehir);
        int b = pstmt.executeUpdate();
        //System.out.println("sonuc int değeri : " + b);
    }

    public static void satisTablosunuTetikle(String TCNO, String HASTA_TCNO, int ILAC_ID, int SATILAN_ADET, int FIYAT) throws SQLException {
        Connection conn = baglantiOlustur();
        String sorgu = "INSERT INTO `satis`(`CALISAN_TC`, `HASTA_TCNO`, `ILAC_ID`, `SATILAN_ADET`, `FIYAT`) VALUES (?,?,?,?,?)";
        PreparedStatement pstmt = (PreparedStatement) conn.prepareStatement(sorgu);
        pstmt.setString(1, TCNO);
        pstmt.setString(2, HASTA_TCNO);
        pstmt.setInt(3, ILAC_ID);
        pstmt.setInt(4, SATILAN_ADET);
        pstmt.setInt(5, FIYAT);
    }

    /*public static void tedarikEklee(int dist_id,int ilac_id,String ilac_adi,double birimFiyat,int birim,double toplamFiyat,String alim_tarihi) throws SQLException{
        Connection conn = baglantiOlustur();
        String sorgu = "INSERT INTO `alim`(`DISTRIBUTOR_ID`, `ILAC_ID`, `ILAC_ADI`, `BIRIM_FIYAT`, `BIRIM`, `TOPLAM_FIYAT`, `ALIM_TARIHI`) VALUES (?,?,?,?,?,?,?)";
        
    }*/
    public static boolean distributorAdinaGoreAra(String dist_adi) throws SQLException {
        boolean sonuc = false;
        Connection conn = baglantiOlustur();
        String sorgu = "SELECT ADI FROM distributor WHERE ADI = " + "'" + dist_adi + "'";
        Statement stmt = conn.createStatement(); // 
        ResultSet rs = stmt.executeQuery(sorgu);
        if (rs.next()) {
            sonuc = false;
        } else {
            sonuc = true;
        }
        return sonuc;
    }

    public static void distributorSil(String dist_adi) throws SQLException {
        Connection conn = baglantiOlustur();
        String sorgu = "DELETE FROM DISTRIBUTOR WHERE ADI = " + "'" + dist_adi + "'";
        Statement stmt = conn.createStatement();
        stmt.executeUpdate(sorgu);
    }

    public static int ilacAra(String ilacAdi) throws SQLException {
        int id = 0;
        boolean sonuc = false;
        Connection conn = baglantiOlustur();
        String sorgu = "SELECT ILAC_ID FROM ilaclar WHERE ADI = " + "'" + ilacAdi + "'";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sorgu);
        if (rs.next()) {
            rs.first();
            id = rs.getInt(1);
        } else {
            id = 0;
        }
        return id;
    }

    public static ilac idTabanliIlacAra(int id) throws SQLException {
        Connection conn = baglantiOlustur();
        String sorgu = "SELECT * FROM ILACLAR WHERE ILAC_ID = " + id;
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sorgu);
        if (!rs.next()) {
            return null;
        }
        rs.first();
        int ilac_id = rs.getInt(1);
        String ilac_adi = rs.getString(2);
        int distributorId = rs.getInt(3);
        double fiyat = rs.getDouble(4);
        int adet = rs.getInt(5);
        ilac i = new ilac(ilac_id, ilac_adi, distributorId, fiyat, adet);
        return i;
    }

    public static void girisDogrula() {

    }

}
