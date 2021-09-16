package Persistentie.P4;

import Persistentie.P2.Reiziger;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface OVChipkaartDAO {
    public boolean save(OVchipkaart chipkaart) throws SQLException;

    public boolean update(OVchipkaart chipkaart) throws SQLException;

    public boolean delete(OVchipkaart chipkaart) throws SQLException;

    public OVchipkaart findById(int id) throws SQLException;

    public List<OVchipkaart> findAll() throws SQLException;

    public ArrayList<OVchipkaart> findByReiziger(Reiziger reiziger) throws SQLException;
}
