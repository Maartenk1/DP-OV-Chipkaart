package Persistentie.P4;

import Persistentie.P2.Reiziger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OVChipkaartDAOPsql implements OVChipkaartDAO {

    private Connection conn;


    public OVChipkaartDAOPsql(Connection connection) throws SQLException {
        this.conn = connection;
    }


    @Override
    public boolean save(OVchipkaart chipkaart) throws SQLException {
        try{
            PreparedStatement pstmnt = conn.prepareStatement("INSERT INTO ov_chipkaart (kaart_nummer , geldig_tot, klasse, saldo, reiziger_id) VALUES (?, ?, ?, ?, ?)");
            pstmnt.setInt(1, chipkaart.getKaart_nummer());
            pstmnt.setDate(2, chipkaart.getGeldig_tot());
            pstmnt.setInt(3, chipkaart.getKlasse());
            pstmnt.setInt(4, chipkaart.getSaldo());
            pstmnt.setInt(5, chipkaart.getReiziger_id());
            pstmnt.executeUpdate();
        }catch (Exception e){
            System.out.println("something  went wrong");
        }
        return true;
    }

    @Override
    public boolean update(OVchipkaart chipkaart) throws SQLException {
        try{
            PreparedStatement pstmnt = conn.prepareStatement("UPDATE ov_chipkaart SET kaart_nummer = ?, geldig_tot = ?, klasse = ?, saldo = ?, reiziger_id = ? WHERE kaart_nummer = ?");
            pstmnt.setInt(1, chipkaart.getKaart_nummer());
            pstmnt.setDate(2, chipkaart.getGeldig_tot());
            pstmnt.setInt(3, chipkaart.getKlasse());
            pstmnt.setInt(4, chipkaart.getSaldo());
            pstmnt.setInt(5, chipkaart.getReiziger_id());
            pstmnt.setInt(6, chipkaart.getKaart_nummer());
            pstmnt.executeUpdate();
        }catch (Exception e){
            System.out.println("something  went wrong");
        }
        return true;
    }

    @Override
    public boolean delete(OVchipkaart chipkaart) throws SQLException {
        try{
            PreparedStatement pstmnt = conn.prepareStatement("DELETE FROM ov_chipkaart WHERE kaart_nummer = ?");
            pstmnt.setInt(1, chipkaart.getKaart_nummer());
            pstmnt.executeUpdate();
        }catch (Exception e){
            System.out.println("something  went wrong");
        }
        return true;
    }

    @Override
    public OVchipkaart findById(int id) throws SQLException {
        PreparedStatement pstmnt = conn.prepareStatement("SELECT * FROM ov_chipkaart WHERE kaart_nummer = ?");
        pstmnt.setInt(1, id);
        ResultSet result = pstmnt.executeQuery();
        ArrayList<OVchipkaart> lijst = new ArrayList<>();
        while (result.next()) {
            int kaart_nummer = result.getInt("kaart_nummer");
            Date geldig_tot = result.getDate("geldig_tot");
            int klasse = result.getInt("klasse");
            int saldo = result.getInt("saldo");
            int reiziger_id = result.getInt("reiziger_id");
            OVchipkaart chipkaart1 = new OVchipkaart(kaart_nummer,geldig_tot,klasse, saldo, reiziger_id);
            lijst.add(chipkaart1);
        }
        return lijst.get(0);
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
            int saldo = result.getInt("saldo");
            int reiziger_id = result.getInt("reiziger_id");
            OVchipkaart chipkaart1 = new OVchipkaart(kaart_nummer,geldig_tot,klasse, saldo, reiziger_id);
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
            int saldo = result.getInt("saldo");
            int reiziger_id = result.getInt("reiziger_id");
            OVchipkaart chipkaart1 = new OVchipkaart(kaart_nummer,geldig_tot,klasse, saldo, reiziger_id);
            lijst.add(chipkaart1);
        }
        return lijst;
    }

}
