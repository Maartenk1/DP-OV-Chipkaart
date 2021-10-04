package Persistentie.P5;

import Persistentie.P2.Reiziger;
import Persistentie.P4.OVchipkaart;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface ProductDAO {
    public boolean save(Product product) throws SQLException;

    public boolean update(Product product) throws SQLException;

    public boolean delete(Product product) throws SQLException;

    public Product findById(int id) throws SQLException;

    public List<Product> findAll() throws SQLException;

    public ArrayList<Product> findByOVChipkaart(OVchipkaart chipkaart) throws SQLException;
}
