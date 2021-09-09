package P2;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class Main {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost/ovchip", "postgres", "Kaas");
        ReizigerDAO rdao = new ReizigerDAOPsql(conn);
        /**
         * P2. Reiziger DAO: persistentie van een klasse
         *
         * Deze methode test de CRUD-functionaliteit van de Reiziger DAO
         *
         * @throws SQLException
         */
            System.out.println("\n---------- Test ReizigerDAO -------------");

            // Haal alle reizigers op uit de database
            List<Reiziger> reizigers = rdao.findAll();
            System.out.println("[Test] ReizigerDAO.findAll() geeft de volgende reizigers:");
            for (Reiziger r : reizigers) {
                System.out.println(r);
            }
            System.out.println();

            // Maak een nieuwe reiziger aan en persisteer deze in de database
//            String gbdatum = "1981-03-14";
//            Reiziger sietske = new Reiziger(77, "S", "", "Boers", Date.valueOf(gbdatum));
//            System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.save() ");
//            rdao.save(sietske);
//            reizigers = rdao.findAll();
//            System.out.println(reizigers.size() + " reizigers\n");

            // Voeg aanvullende tests van de ontbrekende CRUD-operaties in.
            // Verander reiziger
//        String gbdatum = "1981-03-14";
//        Reiziger aap = new Reiziger(77, "A", "", "Boers", Date.valueOf(gbdatum));
//        rdao.update(aap);
//        for (Reiziger r : reizigers) {
//            System.out.println(r);
//        }

//            delete reiziger
//            String gbdatum = "1981-03-14";
//            Reiziger de = new Reiziger(2, "S", "", "Boers", Date.valueOf(gbdatum));
//            rdao.delete(de);
//            System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.save() ");

//            rdao.findById(2);

            rdao.findByGbdatum("1981-03-14");

//        conn.close;

    }
    
}