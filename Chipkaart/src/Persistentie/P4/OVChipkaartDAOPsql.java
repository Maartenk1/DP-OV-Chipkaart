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
        Statement myStat = conn.createStatement();
        int kaart_nummer = chipkaart.getKaart_nummer();
        Date geldig_tot = chipkaart.getGeldig_tot();
        int klasse = chipkaart.getKlasse();
        int saldo = chipkaart.getSaldo();
        int reiziger_id = chipkaart.getReiziger_id();
        myStat.executeUpdate("insert into ov_chipkaart values(" + kaart_nummer + ", '" + geldig_tot + "', " + klasse + ", " + saldo + ", "+ reiziger_id +");");
        return true;
    }
    @Override
    public boolean update(OVchipkaart chipkaart) throws SQLException {
        Statement myStat = conn.createStatement();
        int kaart_nummer = chipkaart.getKaart_nummer();
        Date geldig_tot = chipkaart.getGeldig_tot();
        int klasse = chipkaart.getKlasse();
        int saldo = chipkaart.getSaldo();
        int reiziger_id = chipkaart.getReiziger_id();
        int r = myStat.executeUpdate("update ov_chipkaart set kaart_nummer = (" + kaart_nummer + "), geldig_tot = ('" + geldig_tot + "') , klasse = (" + klasse + ") , saldo = (" + saldo + ") , reiziger_id = (" + reiziger_id + ") where kaart_nummer = (" + kaart_nummer + ")");
        return true;
    }

    @Override
    public boolean delete(OVchipkaart chipkaart) throws SQLException {
        int id = chipkaart.getKaart_nummer();
        Statement myStat = conn.createStatement();
        int r = myStat.executeUpdate("delete from ov_chipkaart where kaart_nummer = (" + id + ") ");
        return true;
    }

    @Override
    public OVchipkaart findById(int id) throws SQLException {
        Statement myStat = conn.createStatement();
        ResultSet chipkaart = myStat.executeQuery("select * from ov_chipkaart where kaart_nummer = ("+id+")");
        ArrayList<OVchipkaart> lijst = new ArrayList<>();
        while (chipkaart.next()) {
            int kaart_nummer = chipkaart.getInt("kaart_nummer");
            Date geldig_tot = chipkaart.getDate("geldig_tot");
            int klasse = chipkaart.getInt("klasse");
            int saldo = chipkaart.getInt("saldo");
            int reiziger_id = chipkaart.getInt("reiziger_id");
            OVchipkaart chipkaart1 = new OVchipkaart(kaart_nummer,geldig_tot,klasse, saldo, reiziger_id);
            lijst.add(chipkaart1);
        }
        return lijst.get(0);
    }

    @Override
    public List<OVchipkaart> findAll() throws SQLException {
        Statement myStat = conn.createStatement();
        ResultSet chipkaart = myStat.executeQuery("select * from ov_chipkaart");
        ArrayList<OVchipkaart> lijst = new ArrayList<>();
        while (chipkaart.next()) {
            int kaart_nummer = chipkaart.getInt("kaart_nummer");
            Date geldig_tot = chipkaart.getDate("geldig_tot");
            int klasse = chipkaart.getInt("klasse");
            int saldo = chipkaart.getInt("saldo");
            int reiziger_id = chipkaart.getInt("reiziger_id");
            OVchipkaart chipkaart1 = new OVchipkaart(kaart_nummer,geldig_tot,klasse, saldo, reiziger_id);
            lijst.add(chipkaart1);
        }
        return lijst;
    }

    @Override
    public ArrayList<OVchipkaart> findByReiziger(Reiziger reiziger) throws SQLException {
        Statement myStat = conn.createStatement();
        int id = reiziger.getId();
        ResultSet chipkaart = myStat.executeQuery("select * from ov_chipkaart where reiziger_id = ("+id+")");
        ArrayList<OVchipkaart> lijst = new ArrayList<>();
        while (chipkaart.next()) {
            int kaart_nummer = chipkaart.getInt("kaart_nummer");
            Date geldig_tot = chipkaart.getDate("geldig_tot");
            int klasse = chipkaart.getInt("klasse");
            int saldo = chipkaart.getInt("saldo");
            int reiziger_id = chipkaart.getInt("reiziger_id");
            OVchipkaart chipkaart1 = new OVchipkaart(kaart_nummer,geldig_tot,klasse, saldo, reiziger_id);
            lijst.add(chipkaart1);
        }
        return lijst;
    }

}
