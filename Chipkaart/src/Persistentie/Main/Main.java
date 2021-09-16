package Persistentie.Main;

import Persistentie.P2.Reiziger;
import Persistentie.P2.ReizigerDAO;
import Persistentie.P2.ReizigerDAOPsql;
import Persistentie.P3.Adres;
import Persistentie.P3.AdresDAO;
import Persistentie.P3.AdresDAOPsql;
import Persistentie.P4.OVChipkaartDAO;
import Persistentie.P4.OVChipkaartDAOPsql;
import Persistentie.P4.OVchipkaart;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class Main {
    static Connection connection;

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        getConnection();

        testReizigerDAO(new ReizigerDAOPsql(connection));
        testAdresDAO(new AdresDAOPsql(connection), new ReizigerDAOPsql(connection));
        testOVChipkaartDAO(new OVChipkaartDAOPsql(connection), new ReizigerDAOPsql(connection));

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
        System.out.println();

        String gbdatum = "1981-03-14";
        Reiziger sietske = new Reiziger(81, "S", "", "Boers", Date.valueOf(gbdatum));

        //  delete reiziger
        Reiziger de = new Reiziger(14, "S", "", "Boers", Date.valueOf("1981-03-14"));
        rdao.delete(de);
        rdao.delete(sietske);

        // Maak een nieuwe reiziger aan en persisteer deze in de database
        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.save() ");
        rdao.save(sietske);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers\n");

        // Verander reiziger
        Reiziger aap = new Reiziger(81, "Z", "", "Boers", Date.valueOf("1981-03-14"));
        rdao.update(aap);


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
        Reiziger kees = new Reiziger(14, "S", "", "Boers", Date.valueOf("1981-03-14"));
        rdao.save(kees);
        Adres Utrecht = new Adres(8, "4444 AK", "22", "Havenlaan", "Utrecht", 14);
        adao.save(Utrecht);

        // Verander adres
        Adres ad1 = new Adres(8, "4444 AK", "22", "Havenstraat", "Utrecht", 14);
        adao.update(ad1);

//        delete adres
        adao.delete(Utrecht);

        //Vind adres bij id
        System.out.println("\n[Test] Vind adres met ID");
        System.out.println(adao.findById(1));

        //Vind adres doormiddel van reiziger
        System.out.println("\n[Test] Vind adres doormiddel van reiziger");
        Reiziger re = new Reiziger(1, "S", "", "Boers", Date.valueOf("1981-03-14"));
        System.out.println(adao.findByReiziger(re));

        //Vind alle adressen
        System.out.println("\n[Test] Vind alle adressen");
        System.out.println(adao.findAll());

    }

    public static void testOVChipkaartDAO(OVChipkaartDAO cdao, ReizigerDAO rdao) throws SQLException {
        System.out.println("\n---------- Test ChipkaartDAO -------------");

        // Maak een nieuwe chipkaart aan en persisteer deze in de database
        Reiziger kees = new Reiziger(15, "S", "", "Boers", Date.valueOf("1981-03-14"));
        rdao.save(kees);
        OVchipkaart chipkaart = new OVchipkaart(84533, Date.valueOf("2020-03-14"), 1, 25, 15);
        cdao.save(chipkaart);

        // Verander chipkaart
        OVchipkaart chipkaart1 = new OVchipkaart(84533, Date.valueOf("2020-03-14") , 1, 60, 15);
        cdao.update(chipkaart1);

        //delete chipkaart
        cdao.delete(chipkaart);
        rdao.delete(kees);

        //Vind chipkaart bij id
        System.out.println("\n[Test] Vind chipkaart met ID");
        System.out.println(cdao.findById(35283));

        //Vind adres doormiddel van reiziger
        System.out.println("\n[Test] Vind chipkaart doormiddel van reiziger");
        Reiziger re = new Reiziger(2, "S", "", "Boers", Date.valueOf("1981-03-14"));
        System.out.println(cdao.findByReiziger(re));

        //Vind alle adressen
        System.out.println("\n[Test] Vind alle chipkaarten");
        System.out.println(cdao.findAll());

    }
}


