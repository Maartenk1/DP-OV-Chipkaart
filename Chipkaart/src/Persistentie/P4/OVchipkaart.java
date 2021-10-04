package Persistentie.P4;

import Persistentie.P5.Product;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class OVchipkaart {
    private int kaart_nummer;
    private Date geldig_tot;
    private int klasse;
    private float saldo;
    private int reiziger_id;
    private List<Product> producten = new ArrayList<>();

    public OVchipkaart(int kaart_nummer, Date geldig_tot, int klasse, float saldo, int reiziger_id) {
        this.kaart_nummer = kaart_nummer;
        this.geldig_tot = geldig_tot;
        this.klasse = klasse;
        this.saldo = saldo;
        this.reiziger_id = reiziger_id;
    }

    public OVchipkaart(int kaart_nummer, Date geldig_tot, int klasse, float saldo) {
        this.kaart_nummer = kaart_nummer;
        this.geldig_tot = geldig_tot;
        this.klasse = klasse;
        this.saldo = saldo;
    }

    public int getKaart_nummer() {
        return kaart_nummer;
    }

    public void setKaart_nummer(int kaart_nummer) {
        this.kaart_nummer = kaart_nummer;
    }

    public Date getGeldig_tot() {
        return geldig_tot;
    }

    public void setGeldig_tot(Date geldig_tot) {
        this.geldig_tot = geldig_tot;
    }

    public int getKlasse() {
        return klasse;
    }

    public void setKlasse(int klasse) {
        this.klasse = klasse;
    }

    public float getSaldo() {
        return saldo;
    }

    public void setSaldo(float saldo) {
        this.saldo = saldo;
    }

    public int getReiziger_id() {
        return reiziger_id;
    }

    public void setReiziger_id(int reiziger_id) {
        this.reiziger_id = reiziger_id;
    }

    public List<Product> getProduct() {
        return producten;
    }

    public void addProduct(List<Product> product) {
        this.producten = product;
    }

    public void removeProduct(Product product){
        producten.remove(product);
    }

    public String toString(){
        String chipkaart = "#" + kaart_nummer + ", ";
        chipkaart += "datum " + java.sql.Date.valueOf(String.valueOf(geldig_tot))+ ", ";
        chipkaart += "klasse " + klasse + ", ";
        chipkaart += "saldo €" + saldo + ", ";
        chipkaart += "reiziger_id " + reiziger_id;
        return chipkaart;
    }
}
