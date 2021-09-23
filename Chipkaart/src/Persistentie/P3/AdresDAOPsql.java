package Persistentie.P3;

import Persistentie.P2.Reiziger;

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
        try{
            PreparedStatement pstmnt = conn.prepareStatement("INSERT INTO adres (adres_id , postcode, huisnummer, straat, woonplaats, reiziger_id) VALUES (?, ?, ?, ?, ?, ?)");
            pstmnt.setInt(1, adres.getAdresid());
            pstmnt.setString(2, adres.getHuisnummer());
            pstmnt.setString(3, adres.getPostcode());
            pstmnt.setString(4, adres.getStraat());
            pstmnt.setString(5, adres.getWoonplaats());
            pstmnt.setInt(6, adres.getReizigerid());
            pstmnt.executeUpdate();
        }catch (Exception e){
            System.out.println("something  went wrong");
        }
        return true;
    }

    @Override
    public boolean update(Adres adres) throws SQLException {
        try{
            PreparedStatement pstmnt = conn.prepareStatement("UPDATE adres SET adres_id = ?, postcode = ?, huisnummer = ?, straat = ?, woonplaats = ?, reiziger_id = ? WHERE adres_id = ?");
            pstmnt.setInt(1, adres.getAdresid());
            pstmnt.setString(2, adres.getHuisnummer());
            pstmnt.setString(3, adres.getPostcode());
            pstmnt.setString(4, adres.getStraat());
            pstmnt.setString(5, adres.getWoonplaats());
            pstmnt.setInt(6, adres.getReizigerid());
            pstmnt.setInt(7, adres.getAdresid());
            pstmnt.executeUpdate();
        }catch (Exception e){
            System.out.println("something  went wrong");
        }
        return true;
    }

    @Override
    public boolean delete(Adres adres) throws SQLException {
        try{
            PreparedStatement pstmnt = conn.prepareStatement("DELETE FROM adres WHERE adres_id = ?");
            pstmnt.setInt(1, adres.getAdresid());
            pstmnt.executeUpdate();
        }catch (Exception e){
            System.out.println("something went wrong");
        }
        return true;
    }

    @Override
    public Adres findById(int id) throws SQLException {
        PreparedStatement pstmnt = conn.prepareStatement("SELECT * FROM adres WHERE adres_id = ?");
        pstmnt.setInt(1, id);
        ResultSet result = pstmnt.executeQuery();
        ArrayList<Adres> lijst = new ArrayList<>();
        while (result.next()) {
            String huisnummer = result.getString("huisnummer");
            String postcode = result.getString("postcode");
            String straat = result.getString("straat");
            String woonplaats = result.getString("woonplaats");
            int adresid = result.getInt("adres_id");
            int reizigerid = result.getInt("reiziger_id");
            Adres a1 = new Adres(adresid,postcode,huisnummer, straat, woonplaats, reizigerid);
            lijst.add(a1);
        }
        return lijst.get(0);
    }


    @Override
    public Adres findByReiziger(Reiziger reiziger) throws SQLException {
        int id = reiziger.getId();
        PreparedStatement pstmnt = conn.prepareStatement("SELECT * FROM adres WHERE reiziger_id = ?");
        pstmnt.setInt(1, id);
        ResultSet result = pstmnt.executeQuery();
        ArrayList<Adres> lijst = new ArrayList<>();
        while (result.next()) {
            String huisnummer = result.getString("huisnummer");
            String postcode = result.getString("postcode");
            String straat = result.getString("straat");
            String woonplaats = result.getString("woonplaats");
            int adresid = result.getInt("adres_id");
            int reizigerid = result.getInt("reiziger_id");
            Adres a1 = new Adres(adresid,postcode,huisnummer, straat, woonplaats, reizigerid);
            lijst.add(a1);
        }
        return lijst.get(0);
    }

    @Override
    public List<Adres> findAll() throws SQLException {
        PreparedStatement pstmnt = conn.prepareStatement("SELECT * FROM adres");
        ResultSet result = pstmnt.executeQuery();
        ArrayList<Adres> lijst = new ArrayList<>();
        while (result.next()) {
            String huisnummer = result.getString("huisnummer");
            String postcode = result.getString("postcode");
            String straat = result.getString("straat");
            String woonplaats = result.getString("woonplaats");
            int adresid = result.getInt("adres_id");
            int reizigerid = result.getInt("reiziger_id");
            Adres a1 = new Adres(adresid,postcode,huisnummer, straat, woonplaats, reizigerid);
            lijst.add(a1);
        }
        return lijst;
    }

}

