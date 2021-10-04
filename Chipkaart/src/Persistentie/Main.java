package Persistentie;

import Persistentie.P2.Reiziger;
import Persistentie.P2.ReizigerDAO;
import Persistentie.P2.ReizigerDAOPsql;
import Persistentie.P3.Adres;
import Persistentie.P3.AdresDAO;
import Persistentie.P3.AdresDAOPsql;
import Persistentie.P4.OVChipkaartDAO;
import Persistentie.P4.OVChipkaartDAOPsql;
import Persistentie.P4.OVchipkaart;
import Persistentie.P5.Product;
import Persistentie.P5.ProductDAO;
import Persistentie.P5.ProductDAOPsql;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {
    static Connection connection;

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        getConnection();

        testReizigerDAO(new ReizigerDAOPsql(connection));
        testAdresDAO(new AdresDAOPsql(connection), new ReizigerDAOPsql(connection));
        testOVChipkaartDAO(new OVChipkaartDAOPsql(connection), new ReizigerDAOPsql(connection));
        testProductDAO(new ProductDAOPsql(connection),new OVChipkaartDAOPsql(connection), new ReizigerDAOPsql(connection));

        closeConnection();
    }

    public static void getConnection() throws SQLException {
        connection = DriverManager.getConnection("jdbc:postgresql://localhost/ovchip", "postgres", "Kaas");
    }

    public static void closeConnection() throws SQLException {
        connection.close();
    }

    public static void testReizigerDAO(ReizigerDAO rdao) throws SQLException {
        System.out.println("\n---------- Test ReizigerDAO -------------");

        // Haal alle reizigers op uit de database
        List<Reiziger> reizigers = rdao.findAll();
        System.out.println("[Test] ReizigerDAO.findAll() geeft de volgende reizigers:");
        for (Reiziger r : reizigers) {
            System.out.println(r);
        }

        String gbdatum = "1981-03-14";
        Reiziger sietske = new Reiziger( 15,"S", "van", "Boers", Date.valueOf(gbdatum));

        // Maak een nieuwe reiziger aan en persisteer deze in de database
        System.out.print("\n[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.save() ");
        rdao.save(sietske);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers");

        // Verander reiziger
        Reiziger aap = new Reiziger(15, "Z", "", "Boers", Date.valueOf("1981-03-14"));
        rdao.update(aap);

        //  delete reiziger
        rdao.delete(aap);

        //Vind reiziger met id
        System.out.println("\n[Test] Vind reiziger met ID");
        System.out.println(rdao.findById(2));


        //Vind reiziger doormiddel van geboortedatum
        System.out.println("\n[Test] Vind reiziger doormiddel van geboortedatum");
        System.out.println(rdao.findByGbdatum("1981-03-14"));
    }

    public static void testAdresDAO(AdresDAO adao, ReizigerDAO rdao) throws SQLException {
        System.out.println("\n---------- Test AdresDAO -------------");

//         Maak een nieuw adres aan en persisteer deze in de database
        Adres Utrecht = new Adres(8, "4444 AK", "22", "Havenlaan", "Utrecht", 14);
        adao.save(Utrecht);

        // Verander adres
        Adres ad1 = new Adres(8, "4444 AK", "22", "Havenstraat", "Utrecht", 14);
        adao.update(ad1);

//        delete adres
        adao.delete(Utrecht);

        //Vind adres bij id
        System.out.println("[Test] Vind adres met ID");
        System.out.println(adao.findById(1));

        //Vind adres doormiddel van reiziger
        System.out.println("\n[Test] Vind adres doormiddel van reiziger");
        Reiziger re = new Reiziger(1, "S", "", "Boers", Date.valueOf("1981-03-14"));
        System.out.println(adao.findByReiziger(re));

        // Haal alle reizigers op uit de database
        List<Adres> adressen = adao.findAll();
        System.out.println("\n[Test] AdresDAO.findAll() geeft de volgende adressen:");
        for (Adres adres : adressen) {
            System.out.println(adres);
        }

    }

    public static void testOVChipkaartDAO(OVChipkaartDAO cdao, ReizigerDAO rdao) throws SQLException {
        System.out.println("\n---------- Test ChipkaartDAO -------------");

        // Maak een nieuwe chipkaart aan en persisteer deze in de database
        Reiziger kees = new Reiziger(16, "S", "", "Boers", Date.valueOf("1981-03-14"));
        rdao.save(kees);
        OVchipkaart chipkaart = new OVchipkaart(84533, Date.valueOf("2020-03-14"), 1, 25);
        kees.addChipkaart(chipkaart);
        cdao.update(kees);

        // Verander chipkaart
        OVchipkaart chipkaart1 = new OVchipkaart(77777, Date.valueOf("2020-03-14") , 1, 60);
        kees.getChipkaart().remove(0);
        kees.addChipkaart(chipkaart1);
        cdao.update(kees);

        //delete chipkaart
        kees.getChipkaart().remove(0);
        cdao.update(kees);
        rdao.delete(kees);

        //Vind chipkaart bij id
        System.out.println("\n[Test] Vind chipkaart met ID");
        System.out.println(cdao.findById(35283));

        //Vind chipkaart doormiddel van reiziger
        System.out.println("\n[Test] Vind chipkaart doormiddel van reiziger");
        Reiziger re = new Reiziger(2, "S", "", "Boers", Date.valueOf("1981-03-14"));
        List<OVchipkaart> chipkaarten = cdao.findByReiziger(re);
        for (OVchipkaart chipkaart2 : chipkaarten) {
            System.out.println(chipkaart2);
        }

        //Vind alle chipkaarten
        List<OVchipkaart> chipkaarten2 = cdao.findAll();
        System.out.println("\n[Test] OVChipkaartDAO.findAll() geeft de volgende chipkaarten:");
        for (OVchipkaart chipkaart3 : chipkaarten2) {
            System.out.println(chipkaart3);
        }

    }

    public static void testProductDAO(ProductDAO pdao, OVChipkaartDAO cdao, ReizigerDAO rdao) throws SQLException {
        System.out.println("\n---------- Test ProductDAO -------------");

        // Maak een nieuw product aan en persisteer deze in de database
        // 2 lijsten aanmaken voor chipkaarten en producten
        List<OVchipkaart> chipkaarten = new ArrayList<>();
        List<Product> producten = new ArrayList<>();
        // Nieuwe reiziger aanmaken en toevoegen aan de database
        Reiziger kees = new Reiziger(17, "S", "", "Boers", Date.valueOf("1981-03-14"));
        rdao.save(kees);
        // Nieuwe OVchipkaart aanmaken en toevoegen aan lijst en reiziger + updaten van database
        OVchipkaart chipkaart = new OVchipkaart(84533, Date.valueOf("2020-03-14"), 1, 25);
        kees.addChipkaart(chipkaart);
        chipkaarten.add(chipkaart);
        cdao.update(kees);
        // Nieuw product aanmaken lijst chipkaarten toevoegen aan product
        Product product1 = new Product(7, "40% Korting", "Korting is 40%", 60);
        product1.addChipkaart(chipkaarten);
        // Product toevoegen aan chipkaart en opslaan in de database
        producten.add(product1);
        chipkaart.addProduct(producten);
        pdao.save(product1);

        // Verander product
        // wat er fout gaat is dat er een nieuw object wordt aangeroepen terwijl het de bestaande product1 moet zijn
        product1.setBeschrijving("Korting is 80%");
        pdao.update(product1);

        //delete product
        // Product verwijderen uit lijsten van chipkaarten
        product1.removeFromChipkaarten(product1);
        // Product verwijderen uit database samen met connectie van chipkaart
        pdao.delete(product1);
        // chipkaart verwijderen uit lijst van reiziger en beide verwijderen uit de database
        kees.getChipkaart().remove(0);
        cdao.update(kees);
        rdao.delete(kees);

        //Vind product bij id
        System.out.println("\n[Test] Vind product met ID");
        System.out.println(pdao.findById(1));

        //Vind alle producten
        List<Product> producten2 = pdao.findAll();
        System.out.println("\n[Test] ProductDAO.findAll() geeft de volgende producten:");
        for (Product product2 : producten2) {
            System.out.println(product2);
        }

        //Vind producten doormiddel van een chipkaart
        System.out.println("\n[Test] Vind producten doormiddel van een chipkaart");
        OVchipkaart chipkaart3 = new OVchipkaart(90537, Date.valueOf("2020-03-14") , 1, 60);
        List<Product> producten3 = pdao.findByOVChipkaart(chipkaart3);
        for (Product chipkaart2 : producten3) {
            System.out.println(chipkaart2);
        }
    }
}


