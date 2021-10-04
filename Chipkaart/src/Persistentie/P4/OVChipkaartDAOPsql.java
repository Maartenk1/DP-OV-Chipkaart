package Persistentie.P4;

import Persistentie.P2.Reiziger;
import Persistentie.P5.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class OVChipkaartDAOPsql implements OVChipkaartDAO {

    private Connection conn;


    public OVChipkaartDAOPsql(Connection connection) throws SQLException {
        this.conn = connection;
    }


    @Override
    public boolean save(OVchipkaart chipkaart) throws SQLException {

        int id = chipkaart.getKaart_nummer();
        if (findById(id) == null) {
            try {
                PreparedStatement pstmnt = conn.prepareStatement("INSERT INTO ov_chipkaart (kaart_nummer , geldig_tot, klasse, saldo, reiziger_id) VALUES (?, ?, ?, ?, ?)");
                pstmnt.setInt(1, chipkaart.getKaart_nummer());
                pstmnt.setDate(2, chipkaart.getGeldig_tot());
                pstmnt.setInt(3, chipkaart.getKlasse());
                pstmnt.setFloat(4, chipkaart.getSaldo());
                pstmnt.setInt(5, chipkaart.getReiziger_id());
                pstmnt.executeUpdate();
            } catch (Exception e) {
                System.out.println("something  went wrong");
            }

            for (Product product : chipkaart.getProduct()) {
                try {
                    PreparedStatement pstmnt2 = conn.prepareStatement("INSERT INTO ov_chipkaart_product (kaart_nummer , product_nummer, status, last_update) VALUES (?, ?, ?, ?)");
                    pstmnt2.setInt(1, chipkaart.getKaart_nummer());
                    pstmnt2.setInt(2, product.getProductnummer());
                    pstmnt2.setString(3, "actief");
                    pstmnt2.setDate(4, new java.sql.Date(Calendar.getInstance().getTime().getTime()));
                    pstmnt2.executeUpdate();
                } catch (Exception e) {
                    System.out.println("something  went wrong");
                }
            }
        } else {
            chipkaart.getReiziger_id();
            PreparedStatement pstmnt = conn.prepareStatement("SELECT * FROM reiziger WHERE reiziger_id = ?");
            pstmnt.setInt(1, id);
            ResultSet result = pstmnt.executeQuery();
            ArrayList<Reiziger> lijst = new ArrayList<>();
            while (result.next()) {
                int reizigerid = result.getInt("reiziger_id");
                String voorletters = result.getString("voorletters");
                String tussenvoegsel = null;
                if (result.getString("tussenvoegsel") != null) {
                    tussenvoegsel = result.getString("tussenvoegsel");
                }
                String achternaam = result.getString("achternaam");
                Date geboortedatum = result.getDate("geboortedatum");
                Reiziger r1 = new Reiziger(reizigerid, voorletters, tussenvoegsel, achternaam, geboortedatum);
                lijst.add(r1);
            }
            update(lijst.get(0));
        }
        return true;
    }

    @Override
    public List<OVchipkaart> update(Reiziger reiziger) throws SQLException {
        List<OVchipkaart> ovChipkaarten = reiziger.getChipkaart();
        for (OVchipkaart chipkaart : ovChipkaarten) {
            PreparedStatement pstmnt = conn.prepareStatement
                    ("DELETE FROM ov_chipkaart_product WHERE kaart_nummer = ?");
            pstmnt.setInt(1, chipkaart.getKaart_nummer());
            pstmnt.executeUpdate();
        }
        PreparedStatement pstmnt2 = conn.prepareStatement
                ("DELETE FROM ov_chipkaart WHERE reiziger_id = ?");
        pstmnt2.setInt(1, reiziger.getId());
        pstmnt2.executeUpdate();
        // ov-chipkaarten in db plaatsen
        for (OVchipkaart ovChipkaart : ovChipkaarten) {
            save(ovChipkaart);
        }

        return ovChipkaarten;
    }

    @Override
    public boolean delete(OVchipkaart chipkaart) throws SQLException {
        try {
            PreparedStatement pstmnt = conn.prepareStatement("DELETE FROM ov_chipkaart_product WHERE kaart_nummer = ?");
            pstmnt.setInt(1, chipkaart.getKaart_nummer());
            pstmnt.executeUpdate();
        } catch (Exception e) {
            System.out.println("something  went wrong");
        }

        try {
            PreparedStatement pstmnt2 = conn.prepareStatement("DELETE FROM ov_chipkaart WHERE kaart_nummer = ?");
            pstmnt2.setInt(1, chipkaart.getKaart_nummer());
            pstmnt2.executeUpdate();
        } catch (Exception e) {
            System.out.println("something  went wrong");
        }
        return true;
    }

    @Override
    public OVchipkaart findById(int id) throws SQLException {
        OVchipkaart chipkaart = null;
        PreparedStatement pstmnt = conn.prepareStatement("SELECT * FROM ov_chipkaart WHERE kaart_nummer = ?");
        pstmnt.setInt(1, id);
        ResultSet result = pstmnt.executeQuery();
        ArrayList<OVchipkaart> lijst = new ArrayList<>();
        if (result.next()) {
            int kaart_nummer = result.getInt("kaart_nummer");
            Date geldig_tot = result.getDate("geldig_tot");
            int klasse = result.getInt("klasse");
            float saldo = result.getFloat("saldo");
            int reiziger_id = result.getInt("reiziger_id");
            chipkaart = new OVchipkaart(kaart_nummer, geldig_tot, klasse, saldo, reiziger_id);
        }
        return chipkaart;
    }

    @Override
    public List<OVchipkaart> findAll() throws SQLException {
        PreparedStatement pstmnt = conn.prepareStatement("SELECT * FROM ov_chipkaart");
        ResultSet result = pstmnt.executeQuery();
        ArrayList<OVchipkaart> lijst = new ArrayList<>();
        while (result.next()) {
            int kaart_nummer = result.getInt("kaart_nummer");
            Date geldig_tot = result.getDate("geldig_tot");
            int klasse = result.getInt("klasse");
            float saldo = result.getFloat("saldo");
            int reiziger_id = result.getInt("reiziger_id");
            OVchipkaart chipkaart1 = new OVchipkaart(kaart_nummer, geldig_tot, klasse, saldo, reiziger_id);
            lijst.add(chipkaart1);
        }
        return lijst;
    }

    @Override
    public ArrayList<OVchipkaart> findByReiziger(Reiziger reiziger) throws SQLException {
        int id = reiziger.getId();
        PreparedStatement pstmnt = conn.prepareStatement("SELECT * FROM ov_chipkaart WHERE reiziger_id = ?");
        pstmnt.setInt(1, id);
        ResultSet result = pstmnt.executeQuery();
        ArrayList<OVchipkaart> lijst = new ArrayList<>();
        while (result.next()) {
            int kaart_nummer = result.getInt("kaart_nummer");
            Date geldig_tot = result.getDate("geldig_tot");
            int klasse = result.getInt("klasse");
            float saldo = result.getFloat("saldo");
            int reiziger_id = result.getInt("reiziger_id");
            OVchipkaart chipkaart1 = new OVchipkaart(kaart_nummer, geldig_tot, klasse, saldo, reiziger_id);
            lijst.add(chipkaart1);
        }
        return lijst;
    }

}
