package P2;

import java.util.Date;

public class Reiziger {

    private int id;
    private String voorletters;
    private String tussenvoegsel;
    private String achternaam;
    private Date geboortedatum;

    public Reiziger(int re, String vl, String tv, String an, Date gb) {
        id = re;
        voorletters = vl;
        tussenvoegsel = tv;
        achternaam = an;
        geboortedatum = gb;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVoorletters() {
        return voorletters;
    }

    public void setVoorletters(String voorletters) {
        this.voorletters = voorletters;
    }

    public String getTussenvoegsel() {
        return tussenvoegsel;
    }

    public void setTussenvoegsel(String tussenvoegsel) {
        this.tussenvoegsel = tussenvoegsel;
    }

    public String getAchternaam() {
        return achternaam;
    }

    public void setAchternaam(String achternaam) {
        this.achternaam = achternaam;
    }

    public Date getGeboortedatum() {
        return geboortedatum;
    }

    public void setGeboortedatum(Date geboortedatum) {
        this.geboortedatum = geboortedatum;
    }

    public String toString(){
        String reiziger = "#" + id + " ";
        reiziger += voorletters + " ";
        reiziger += tussenvoegsel + " ";
        reiziger += achternaam + " ";
        reiziger += java.sql.Date.valueOf(String.valueOf(geboortedatum))+ " ";
        return reiziger;
    }
}
