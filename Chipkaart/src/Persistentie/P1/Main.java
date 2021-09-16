package Persistentie.P1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Connection mycon = DriverManager.getConnection("jdbc:postgresql://localhost/ovchip", "postgres", "Kaas");
        Statement myStat = mycon.createStatement();
        ResultSet reiziger = myStat.executeQuery("select * from reiziger");

        System.out.println("Alle reizigers:");
        while (reiziger.next()) {
            System.out.print("#" + reiziger.getString("reiziger_id") + ": ");
            System.out.print(reiziger.getString("voorletters") + "." + " ");
            if (reiziger.getString("tussenvoegsel") != null) {
                System.out.print(reiziger.getString("tussenvoegsel") + " ");
            }
            System.out.print(reiziger.getString("achternaam") + " ");
            System.out.print("(" + reiziger.getString("geboortedatum") + ")\n");
        }
    }
}