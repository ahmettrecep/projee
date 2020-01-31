/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

/**
 *
 * @author Recep
 */
public class satis {
    String calisanTc;
    String hastaTc;
    int ilac_id;
    int satilanAdet;
    double fiyat;

    public satis(String calisanTc, String hastaTc, int ilac_id, int satilanAdet, double fiyat) {
        this.calisanTc = calisanTc;
        this.hastaTc = hastaTc;
        this.ilac_id = ilac_id;
        this.satilanAdet = satilanAdet;
        this.fiyat = fiyat;
    }

    public String getCalisanTc() {
        return calisanTc;
    }

    public void setCalisanTc(String calisanTc) {
        this.calisanTc = calisanTc;
    }

    public String getHastaTc() {
        return hastaTc;
    }

    public void setHastaTc(String hastaTc) {
        this.hastaTc = hastaTc;
    }

    public int getIlac_id() {
        return ilac_id;
    }

    public void setIlac_id(int ilac_id) {
        this.ilac_id = ilac_id;
    }

    public int getSatilanAdet() {
        return satilanAdet;
    }

    public void setSatilanAdet(int satilanAdet) {
        this.satilanAdet = satilanAdet;
    }

    public double getFiyat() {
        return fiyat;
    }

    public void setFiyat(double fiyat) {
        this.fiyat = fiyat;
    }
}
