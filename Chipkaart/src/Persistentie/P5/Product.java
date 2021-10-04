package Persistentie.P5;

import Persistentie.P4.OVchipkaart;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Product {
    private int productnummer;
    private String naam;
    private String beschrijving;
    private double prijs;
    private List<OVchipkaart> chipkaarten = new ArrayList<>();

    public Product(int productnummer, String naam, String beschrijving, double prijs) {
        this.productnummer = productnummer;
        this.naam = naam;
        this.beschrijving = beschrijving;
        this.prijs = prijs;
    }

    public Product(int productnummer, String naam, String beschrijving, double prijs, List chipkaarten) {
        this.productnummer = productnummer;
        this.naam = naam;
        this.beschrijving = beschrijving;
        this.prijs = prijs;
        this.chipkaarten = chipkaarten;
    }

    public int getProductnummer() {
        return productnummer;
    }

    public void setProductnummer(int productnummer) {
        this.productnummer = productnummer;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getBeschrijving() {
        return beschrijving;
    }

    public void setBeschrijving(String beschrijving) {
        this.beschrijving = beschrijving;
    }

    public double getPrijs() {
        return prijs;
    }

    public void setPrijs(double prijs) {
        this.prijs = prijs;
    }

    public List<OVchipkaart> getChipkaart() {
        return chipkaarten;
    }

    public void addChipkaart(List<OVchipkaart> chipkaart) {
        this.chipkaarten = chipkaart;
    }

    public void removeChipkaart(OVchipkaart chipkaart){
        chipkaarten.remove(chipkaart);
    }

    public void removeFromChipkaarten(Product product){
        for(OVchipkaart chipkaart : product.getChipkaart()) {
            chipkaart.removeProduct(product);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return productnummer == product.productnummer;
    }

    @Override
    public int hashCode() {
        return Objects.hash(productnummer);
    }

    public String toString() {
        String product = "#" + productnummer + " ";
        product += naam + " ";
        product += beschrijving + " ";
        product += prijs + " ";
        return product;
    }
}
