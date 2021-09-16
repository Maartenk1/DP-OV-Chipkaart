package Persistentie.P2;

import Persistentie.P4.OVchipkaart;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Reiziger {

    private int id;
    private String voorletters;
    private String tussenvoegsel;
    private String achternaam;
    private Date geboortedatum;
    private List<OVchipkaart> chipkaart = new ArrayList<>();

    public Reiziger(String voorletters, String tussenvoegsel, String achternaam, Date geboortedatum) {
        this.voorletters = voorletters;
        this.tussenvoegsel = tussenvoegsel;
        this.achternaam = achternaam;
        this.geboortedatum = geboortedatum;
    }

    public Reiziger(int id, String voorletters, String tussenvoegsel, String achternaam, Date geboortedatum) {
        this(voorletters, tussenvoegsel, achternaam, geboortedatum);
        this.id = id;
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

    public List<OVchipkaart> getChipkaart() {
        return chipkaart;
    }

    public void setChipkaart(List<OVchipkaart> chipkaart) {
        this.chipkaart = chipkaart;
    }

    public String toString(){
        String reiziger = "#" + id + " ";
        reiziger += voorletters + " ";
        reiziger += tussenvoegsel + " ";
        reiziger += achternaam + " ";
        reiziger += java.sql.Date.valueOf(String.valueOf(geboortedatum))+ "\n";
        return reiziger;
    }

}
