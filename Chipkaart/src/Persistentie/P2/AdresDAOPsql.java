package Persistentie.P2;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdresDAOPsql implements AdresDAO {

    private Connection conn;


    public AdresDAOPsql(Connection connection) throws SQLException {
        this.conn = connection;
    }

    @Override
    public boolean save(Adres adres) throws SQLException {
        Statement myStat = conn.createStatement();
        String huisnummer = adres.getHuisnummer();
        String postcode = adres.getPostcode();
        String straat = adres.getStraat();
        String woonplaats = adres.getWoonplaats();
        int adresid = adres.getAdresid();
        int reizigerid = adres.getReizigerid();
        int r = myStat.executeUpdate("insert into adres values(" + adresid + ",' " + postcode + "',' " + huisnummer + "',' " + straat + "','" + woonplaats + "',"+ reizigerid +");");
        return true;
    }

    @Override
    public boolean update(Adres adres) throws SQLException {
        Statement myStat = conn.createStatement();
        String huisnummer = adres.getHuisnummer();
        String postcode = adres.getPostcode();
        String straat = adres.getStraat();
        String woonplaats = adres.getWoonplaats();
        int adresid = adres.getAdresid();
        int reizigerid = adres.getReizigerid();
        int r = myStat.executeUpdate("update adres set adres_id = (" + adresid + "), postcode = ('" + postcode + "') , huisnummer = ('" + huisnummer + "') , straat = ('" + straat + "') , woonplaats = ('" + woonplaats + "') , reiziger_id = ('" + reizigerid + "') where adres_id = (" + adresid + ")");
        return true;
    }

    @Override
    public boolean delete(Adres adres) throws SQLException {
        int id = adres.getAdresid();
        Statement myStat = conn.createStatement();
        int r = myStat.executeUpdate("delete from adres where adres_id = (" + id + ") ");
        return true;
    }

    @Override
    public Adres findById(int id) throws SQLException {
        Statement myStat = conn.createStatement();
        ResultSet adres = myStat.executeQuery("select * from adres where adres_id = ("+id+")");
        ArrayList<Adres> lijst = new ArrayList<>();
        while (adres.next()) {
            String huisnummer = adres.getString("huisnummer");
            String postcode = adres.getString("postcode");
            String straat = adres.getString("straat");
            String woonplaats = adres.getString("woonplaats");
            int adresid = adres.getInt("adres_id");
            int reizigerid = adres.getInt("reiziger_id");
            Adres a1 = new Adres(adresid,postcode,huisnummer, straat, woonplaats, reizigerid);
            lijst.add(a1);
        }
        return lijst.get(0);
    }


    @Override
    public Adres findByReiziger(Reiziger reiziger) throws SQLException {
        Statement myStat = conn.createStatement();
        int id = reiziger.getId();
        ResultSet adres = myStat.executeQuery("select * from adres where reiziger_id = ("+id+")");
        ArrayList<Adres> lijst = new ArrayList<>();
        while (adres.next()) {
            String huisnummer = adres.getString("huisnummer");
            String postcode = adres.getString("postcode");
            String straat = adres.getString("straat");
            String woonplaats = adres.getString("woonplaats");
            int adresid = adres.getInt("adres_id");
            int reizigerid = adres.getInt("reiziger_id");
            Adres a1 = new Adres(adresid,postcode,huisnummer, straat, woonplaats, reizigerid);
            lijst.add(a1);
        }
        return lijst.get(0);
    }

    @Override
    public List<Adres> findAll() throws SQLException {
        Statement myStat = conn.createStatement();
        ResultSet adres = myStat.executeQuery("select * from adres");
        ArrayList<Adres> lijst = new ArrayList<>();
        while (adres.next()) {
            String huisnummer = adres.getString("huisnummer");
            String postcode = adres.getString("postcode");
            String straat = adres.getString("straat");
            String woonplaats = adres.getString("woonplaats");
            int adresid = adres.getInt("adres_id");
            int reizigerid = adres.getInt("reiziger_id");
            Adres a1 = new Adres(adresid,postcode,huisnummer, straat, woonplaats, reizigerid);
            lijst.add(a1);
        }
        return lijst;
    }

}

