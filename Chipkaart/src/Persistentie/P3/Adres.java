package Persistentie.P3;

public class Adres {
    private int adresid;
    private String postcode;
    private String huisnummer;
    private String straat;
    private String woonplaats;
    private int reizigerid;

    public Adres(int adresid, String postcode, String huisnummer, String straat, String woonplaats, int reizigerid) {
        this.adresid = adresid;
        this.postcode = postcode;
        this.huisnummer = huisnummer;
        this.straat = straat;
        this.woonplaats = woonplaats;
        this.reizigerid = reizigerid;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getHuisnummer() {
        return huisnummer;
    }

    public void setHuisnummer(String huisnummer) {
        this.huisnummer = huisnummer;
    }

    public String getStraat() {
        return straat;
    }

    public void setStraat(String straat) {
        this.straat = straat;
    }

    public String getWoonplaats() {
        return woonplaats;
    }

    public void setWoonplaats(String woonplaats) {
        this.woonplaats = woonplaats;
    }


    public int getAdresid() {
        return adresid;
    }

    public void setAdresid(int adresid) {
        this.adresid = adresid;
    }

    public int getReizigerid() {
        return reizigerid;
    }

    public void setReizigerid(int reizigerid) {
        this.reizigerid = reizigerid;
    }

    public String toString(){
        String adres = "#" + adresid + " ";
        adres += postcode + " ";
        adres += huisnummer + " ";
        adres += straat + " ";
        adres += woonplaats + " ";
        adres += reizigerid + "\n";
        return adres;
    }
}
