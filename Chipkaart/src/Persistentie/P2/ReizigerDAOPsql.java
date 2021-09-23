package Persistentie.P2;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReizigerDAOPsql implements ReizigerDAO{

    private Connection conn;


    public ReizigerDAOPsql(Connection connection) throws SQLException {
        this.conn = connection;
    }

    @Override
    public boolean save(Reiziger reiziger) throws SQLException {
        try{
            PreparedStatement pstmnt = conn.prepareStatement("INSERT INTO reiziger (reiziger_id , voorletters, tussenvoegsel, achternaam, geboortedatum) VALUES (?, ?, ?, ?, ?)");
            pstmnt.setInt(1, reiziger.getId());
            pstmnt.setString(2, reiziger.getVoorletters());
            if(reiziger.getTussenvoegsel() != null) {
                pstmnt.setString(3, reiziger.getTussenvoegsel());
            }else{
                pstmnt.setString(3, null);
                }

            pstmnt.setString(4, reiziger.getAchternaam());
            pstmnt.setDate(5, (Date) reiziger.getGeboortedatum());
            pstmnt.executeUpdate();
        }catch (Exception e){
            System.out.println("something  went wrong");
        }
        return true;
    }

    @Override
    public boolean update(Reiziger reiziger) throws SQLException {
      try{
            PreparedStatement pstmnt = conn.prepareStatement("UPDATE reiziger SET reiziger_id = ?, voorletters = ?, tussenvoegsel = ?, achternaam = ?, geboortedatum = ? WHERE reiziger_id = ?");
            pstmnt.setInt(1, reiziger.getId());
            pstmnt.setString(2, reiziger.getVoorletters());
            if(reiziger.getTussenvoegsel() != null) {
                pstmnt.setString(3, reiziger.getTussenvoegsel());
            }else{
                pstmnt.setString(3, null);
            }
            pstmnt.setString(4, reiziger.getAchternaam());
            pstmnt.setDate(5, (Date) reiziger.getGeboortedatum());
        pstmnt.setInt(6, reiziger.getId());
            pstmnt.executeUpdate();
        }catch (Exception e){
            System.out.println("something  went wrong");
        }
        return true;
    }

    @Override
    public boolean delete(Reiziger reiziger) throws SQLException {
        try{
            PreparedStatement pstmnt = conn.prepareStatement("DELETE FROM reiziger WHERE reiziger_id = ?");
            pstmnt.setInt(1, reiziger.getId());
            pstmnt.executeUpdate();
        }catch (Exception e){
            System.out.println("something went wrong");
        }
        return true;
    }

    @Override
    public Reiziger findById(int id) throws SQLException {
        PreparedStatement pstmnt = conn.prepareStatement("SELECT * FROM reiziger WHERE reiziger_id = ?");
        pstmnt.setInt(1, id);
        ResultSet result = pstmnt.executeQuery();
        ArrayList<Reiziger> lijst = new ArrayList<>();
        while (result.next()) {
            int reizigerid = result.getInt("reiziger_id");
            String voorletters = result.getString("voorletters");
            String tussenvoegsel = null;
            if(result.getString("tussenvoegsel") != null) {
                tussenvoegsel = result.getString("tussenvoegsel");
            }
            String achternaam = result.getString("achternaam");
            Date geboortedatum = result.getDate("geboortedatum");
            Reiziger r1 = new Reiziger(reizigerid,voorletters,tussenvoegsel, achternaam, geboortedatum);
            lijst.add(r1);
        }
        return lijst.get(0);
    }

    @Override
    public List<Reiziger> findByGbdatum(String datum) throws SQLException {
      PreparedStatement pstmnt = conn.prepareStatement("SELECT * FROM reiziger WHERE geboortedatum = ?");
        pstmnt.setDate(1, Date.valueOf(datum));
        ResultSet result = pstmnt.executeQuery();
        ArrayList<Reiziger> lijst = new ArrayList<>();
        while (result.next()) {
            int reizigerid = result.getInt("reiziger_id");
            String voorletters = result.getString("voorletters");
            String tussenvoegsel = null;
            if(result.getString("tussenvoegsel") != null) {
                tussenvoegsel = result.getString("tussenvoegsel");
            }
            String achternaam = result.getString("achternaam");
            Date geboortedatum = result.getDate("geboortedatum");
            Reiziger r1 = new Reiziger(reizigerid,voorletters,tussenvoegsel, achternaam, geboortedatum);
            lijst.add(r1);
        }
        return lijst;
    }

    @Override
    public List<Reiziger> findAll() throws SQLException {
        PreparedStatement pstmnt = conn.prepareStatement("SELECT * FROM reiziger");
        ResultSet result = pstmnt.executeQuery();
        ArrayList<Reiziger> lijst = new ArrayList<>();
        while (result.next()) {
            int reizigerid = result.getInt("reiziger_id");
            String voorletters = result.getString("voorletters");
            String tussenvoegsel = null;
            if(result.getString("tussenvoegsel") != null) {
                tussenvoegsel = result.getString("tussenvoegsel");
            }
            String achternaam = result.getString("achternaam");
            Date geboortedatum = result.getDate("geboortedatum");
            Reiziger r1 = new Reiziger(reizigerid,voorletters,tussenvoegsel, achternaam, geboortedatum);
            lijst.add(r1);
        }
        return lijst;
    }
}

