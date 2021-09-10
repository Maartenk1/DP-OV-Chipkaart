package P2;

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
        Statement myStat = conn.createStatement();
        int id = reiziger.getId();
        String voorletters = reiziger.getVoorletters();
        String tussenvoegsel = null;
        if(reiziger.getTussenvoegsel() != null) {
            tussenvoegsel = reiziger.getTussenvoegsel();
        }
        String achternaam = reiziger.getAchternaam();
        java.util.Date geboortedatum = reiziger.getGeboortedatum();
        int r = myStat.executeUpdate("insert into reiziger values("+id +",' "+voorletters+"',' "+tussenvoegsel+"',' "+achternaam+"','"+geboortedatum+"');");
        return true;
    }

    @Override
    public boolean update(Reiziger reiziger) throws SQLException {
        Statement myStat = conn.createStatement();
        int id = reiziger.getId();
        String voorletters = reiziger.getVoorletters();
        String tussenvoegsel = " ";
        if(reiziger.getTussenvoegsel() != null) {
            tussenvoegsel = reiziger.getTussenvoegsel();
        }
        String achternaam = reiziger.getAchternaam();
        java.util.Date geboortedatum = reiziger.getGeboortedatum();
        int r = myStat.executeUpdate("update reiziger set reiziger_id = ("+id +"), voorletters = ('"+voorletters+"') , tussenvoegsel = ('"+tussenvoegsel+"') , achternaam = ('"+achternaam+"') , geboortedatum = ('"+geboortedatum+"') where reiziger_id = ("+id+")");
        return true;
    }

    @Override
    public boolean delete(Reiziger reiziger) throws SQLException {
        int id = reiziger.getId();
        Statement myStat = conn.createStatement();
        int r = myStat.executeUpdate("delete from reiziger where reiziger_id = ("+id+") ");
        return true;
    }

    @Override
    public Reiziger findById(int id) throws SQLException {
        Statement myStat = conn.createStatement();
        ResultSet reiziger = myStat.executeQuery("select * from reiziger where reiziger_id = ("+id+")");
        ArrayList<Reiziger> lijst = new ArrayList<>();
        while (reiziger.next()) {
            int reizigerid = reiziger.getInt("reiziger_id");
            String voorletters = reiziger.getString("voorletters");
            String tussenvoegsel = null;
            if(reiziger.getString("tussenvoegsel") != null) {
                tussenvoegsel = reiziger.getString("tussenvoegsel");
            }
            String achternaam = reiziger.getString("achternaam");
            Date geboortedatum = reiziger.getDate("geboortedatum");
            Reiziger r1 = new Reiziger(reizigerid,voorletters,tussenvoegsel, achternaam, geboortedatum);
            lijst.add(r1);
        }
        return lijst.get(0);
    }

    @Override
    public List<Reiziger> findByGbdatum(String datum) throws SQLException {
        Statement myStat = conn.createStatement();
        ResultSet reiziger = myStat.executeQuery("select * from reiziger where geboortedatum = ('"+datum+"')");
        ArrayList<Reiziger> lijst = new ArrayList<>();
        while (reiziger.next()) {
            int reizigerid = reiziger.getInt("reiziger_id");
            String voorletters = reiziger.getString("voorletters");
            String tussenvoegsel = null;
            if(reiziger.getString("tussenvoegsel") != null) {
                tussenvoegsel = reiziger.getString("tussenvoegsel");
            }
            String achternaam = reiziger.getString("achternaam");
            Date geboortedatum = reiziger.getDate("geboortedatum");
            Reiziger r1 = new Reiziger(reizigerid,voorletters,tussenvoegsel, achternaam, geboortedatum);
            lijst.add(r1);
        }
        return lijst;
    }

    @Override
    public List<Reiziger> findAll() throws SQLException {
        Statement myStat = conn.createStatement();
        ResultSet reiziger = myStat.executeQuery("select * from reiziger");
        ArrayList<Reiziger> lijst = new ArrayList<>();
        while (reiziger.next()) {
            int reizigerid = reiziger.getInt("reiziger_id");
            String voorletters = reiziger.getString("voorletters");
            String tussenvoegsel = null;
            if(reiziger.getString("tussenvoegsel") != null) {
                tussenvoegsel = reiziger.getString("tussenvoegsel");
            }
            String achternaam = reiziger.getString("achternaam");
            Date geboortedatum = reiziger.getDate("geboortedatum");
            Reiziger r1 = new Reiziger(reizigerid,voorletters,tussenvoegsel, achternaam, geboortedatum);
            lijst.add(r1);
        }
        return lijst;
    }
}

