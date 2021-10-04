package Persistentie.P5;

import Persistentie.P2.Reiziger;
import Persistentie.P4.OVchipkaart;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ProductDAOPsql implements ProductDAO {

    private Connection conn;

    public ProductDAOPsql(Connection connection) throws SQLException {
        this.conn = connection;
    }

    @Override
    public boolean save(Product product) throws SQLException {
        int id = product.getProductnummer();
        if (findById(id) == null) {
            try {
                PreparedStatement pstmnt = conn.prepareStatement("INSERT INTO product (product_nummer , naam, beschrijving, prijs) VALUES (?, ?, ?, ?)");
                pstmnt.setInt(1, product.getProductnummer());
                pstmnt.setString(2, product.getNaam());
                pstmnt.setString(3, product.getBeschrijving());
                pstmnt.setDouble(4, product.getPrijs());
                pstmnt.executeUpdate();
            } catch (Exception e) {
                System.out.println("something  went wrong");
            }

            for (OVchipkaart chipkaart : product.getChipkaart()) {
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
            update(product);
        }

        return true;
    }

    @Override
    public boolean update(Product product) throws SQLException {
        try {
            for (OVchipkaart chipkaart : product.getChipkaart()) {
                PreparedStatement pstmnt = conn.prepareStatement("UPDATE ov_chipkaart_product SET kaart_nummer = ?, product_nummer = ?, status = ?, last_update = ? WHERE product_nummer = ?");
                pstmnt.setInt(1, chipkaart.getKaart_nummer());
                pstmnt.setInt(2, product.getProductnummer());
                pstmnt.setString(3, "actief");
                pstmnt.setDate(4, new java.sql.Date(Calendar.getInstance().getTime().getTime()));
                pstmnt.setInt(5, product.getProductnummer());
                pstmnt.executeUpdate();
            }
        } catch (Exception e) {
            System.out.println("something  went wrong");
        }

        try {
            PreparedStatement pstmnt = conn.prepareStatement("UPDATE product SET product_nummer = ?, naam = ?, beschrijving = ?, prijs = ? WHERE product_nummer = ?");
            pstmnt.setInt(1, product.getProductnummer());
            pstmnt.setString(2, product.getNaam());
            pstmnt.setString(3, product.getBeschrijving());
            pstmnt.setDouble(4, product.getPrijs());
            pstmnt.setInt(5, product.getProductnummer());
            pstmnt.executeUpdate();
        } catch (Exception e) {
            System.out.println("something  went wrong");
        }
        return true;
    }

    @Override
    public boolean delete(Product product) throws SQLException {

        try {
            PreparedStatement pstmnt = conn.prepareStatement("DELETE FROM ov_chipkaart_product WHERE product_nummer = ?");
            pstmnt.setInt(1, product.getProductnummer());
            pstmnt.executeUpdate();
        } catch (Exception e) {
            System.out.println("something  went wrong");
        }


        try {
            PreparedStatement pstmnt2 = conn.prepareStatement("DELETE FROM product WHERE product_nummer = ?");
            pstmnt2.setInt(1, product.getProductnummer());
            pstmnt2.executeUpdate();
        } catch (Exception e) {
            System.out.println("something  went wrong");
        }
        return true;

    }

    @Override
    public Product findById(int id) throws SQLException {
        Product product = null;
        PreparedStatement st = conn.prepareStatement
                ("SELECT * FROM product WHERE product_nummer = ?");
        st.setInt(1, id);
        ResultSet rs = st.executeQuery();
        if (rs.next()) {
            int product_nummer = rs.getInt(1);
            String naam = rs.getString(2);
            String beschrijving = rs.getString(3);
            double prijs = rs.getDouble(4);
            product = new Product(product_nummer, naam, beschrijving, prijs);
        }

        return product;
    }

    @Override
    public List<Product> findAll() throws SQLException {
        PreparedStatement pstmnt = conn.prepareStatement("SELECT * FROM product");
        ResultSet result = pstmnt.executeQuery();
        ArrayList<Product> lijst = new ArrayList<>();
        while (result.next()) {
            int product_nummer = result.getInt("product_nummer");
            String naam = result.getString("naam");
            String beschrijving = result.getString("beschrijving");
            double prijs = result.getDouble("prijs");
            Product product = new Product(product_nummer, naam, beschrijving, prijs);
            lijst.add(product);
        }
        return lijst;
    }

    @Override
    public ArrayList<Product> findByOVChipkaart(OVchipkaart chipkaart) throws SQLException {
        int id = chipkaart.getKaart_nummer();
        PreparedStatement pstmnt = conn.prepareStatement("SELECT product.product_nummer, naam, beschrijving, prijs from ov_chipkaart_product\n" +
                "INNER JOIN ov_chipkaart \n" +
                "\tON ov_chipkaart_product.kaart_nummer = ov_chipkaart.kaart_nummer\n" +
                "INNER JOIN product \n" +
                "\tON ov_chipkaart_product.product_nummer = product.product_nummer\n" +
                "Where ov_chipkaart.kaart_nummer = ?;");
        pstmnt.setInt(1, id);
        ResultSet result = pstmnt.executeQuery();
        ArrayList<Product> lijst = new ArrayList<>();
        while (result.next()) {
            int product_nummer = result.getInt("product_nummer");
            String naam = result.getString("naam");
            String beschrijving = result.getString("beschrijving");
            double prijs = result.getDouble("prijs");
            Product product = new Product(product_nummer, naam, beschrijving, prijs);
            lijst.add(product);
        }
        return lijst;
    }

}
