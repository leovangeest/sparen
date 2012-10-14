/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.marlevous.sparen.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import nl.marlevous.sparen.Rekening;
import nl.marlevous.sparen.Rekeningen;

/**
 *
 * @author Note201
 */
public class RekeningDAO {

    private Rekening rekening;

    public RekeningDAO(long rekeningID) {
        this.rekening = leesRekening(rekeningID);
    }

    public Rekening getRekening() {
        return rekening;
    }

    public static Rekeningen getRekeningen() {
        Connection c = new Connection();
        java.sql.Connection conn = c.connect();

        Rekeningen rekeningen = new Rekeningen();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT id, naam FROM rekening order by id");
            while (rs.next()) {
                double saldo = getSaldo(conn, rs.getLong("id"));
                Rekening r = new Rekening(
                        rs.getLong("id"),
                        rs.getString("naam"),
                        saldo);

                rekeningen.add(r);
            }
        } catch (SQLException ex) {
            Logger.getLogger(RekeningDAO.class.getName()).log(
                    Level.SEVERE,
                    "Error selecting rekeningen from database.", ex);
            System.exit(1);
        }
        c.disconnect();
        return rekeningen;
    }

    public static Rekeningen getRekeningenTMTransactie(long TransactieID) {
        Connection c = new Connection();
        java.sql.Connection conn = c.connect();

        Rekeningen rekeningen = new Rekeningen();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT id, naam FROM rekening order by id");
            while (rs.next()) {
                double saldo = getSaldoTMTransactie(conn,
                        rs.getLong("id"),
                        TransactieID);
                Rekening r = new Rekening(
                        rs.getLong("id"),
                        rs.getString("naam"),
                        saldo);

                rekeningen.add(r);
            }
        } catch (SQLException ex) {
            Logger.getLogger(RekeningDAO.class.getName()).log(
                    Level.SEVERE,
                    "Error selecting rekeningen from database.", ex);
            System.exit(1);
        }
        c.disconnect();
        return rekeningen;
    }

    private static double getSaldo(java.sql.Connection conn, long rekeningID) {

        double saldo = 0;

        StringBuilder selectStatement = new StringBuilder();

        selectStatement.append(" SELECT");
        selectStatement.append("  sum(bedrag) as saldo");
        selectStatement.append(" FROM");
        selectStatement.append("  rekeningboeking");
        selectStatement.append(" WHERE");
        selectStatement.append("  rekening = ?");

        try {
            PreparedStatement stmt =
                    conn.prepareStatement(selectStatement.toString());

            stmt.setLong(1, rekeningID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                saldo = rs.getDouble("saldo");
            }
        } catch (SQLException ex) {
            Logger.getLogger(RekeningDAO.class.getName()).log(
                    Level.SEVERE,
                    "Error selecting rekeningen from database.", ex);
            System.exit(1);
        }
        return saldo;
    }

    private static double getSaldoTMTransactie(java.sql.Connection conn, long rekeningID, long TransactieID) {

        double saldo = 0;

        StringBuilder selectStatement = new StringBuilder();

        selectStatement.append(" SELECT");
        selectStatement.append("  sum(bedrag) as saldo");
        selectStatement.append(" FROM");
        selectStatement.append("  rekeningboeking");
        selectStatement.append(" WHERE");
        selectStatement.append("  rekening = ?");
        selectStatement.append(" AND");
        selectStatement.append("  transactie <= ?");

        try {
            PreparedStatement stmt =
                    conn.prepareStatement(selectStatement.toString());

            stmt.setLong(1, rekeningID);
            stmt.setLong(2, TransactieID);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                saldo = rs.getDouble("saldo");
            }
        } catch (SQLException ex) {
            Logger.getLogger(RekeningDAO.class.getName()).log(
                    Level.SEVERE,
                    "Error selecting rekeningen from database.", ex);
            System.exit(1);
        }
        return saldo;
    }

    private static Rekening leesRekening(long rekeningID) {
        Rekening r;
        r = null;
        Connection c = new Connection();
        java.sql.Connection conn = c.connect();

        StringBuilder selectStatement = new StringBuilder();

        selectStatement.append(" SELECT");
        selectStatement.append("  id, naam, saldo");
        selectStatement.append(" FROM");
        selectStatement.append("  rekening");
        selectStatement.append(" WHERE");
        selectStatement.append("  id = ?");

        try {
            PreparedStatement stmt =
                    conn.prepareStatement(selectStatement.toString());

            stmt.setLong(1, rekeningID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                r = new Rekening(
                        rs.getLong("id"),
                        rs.getString("naam"),
                        rs.getDouble("saldo"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(RekeningDAO.class.getName()).log(
                    Level.SEVERE,
                    "Error selecting rekeningen from database.", ex);
            System.exit(1);
        }
        c.disconnect();
        return r;
    }

    public static double getStandaardTotaal() {
        Connection c = new Connection();
        java.sql.Connection conn = c.connect();
        double totaal = 0;
        
        StringBuilder selectStatement = new StringBuilder();

        selectStatement.append(" SELECT");
        selectStatement.append("  sum(bedrag)");
        selectStatement.append(" FROM");
        selectStatement.append("  standaardrekeningboeking");

        try {
            PreparedStatement stmt =
                    conn.prepareStatement(selectStatement.toString());

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                totaal = rs.getDouble(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(RekeningDAO.class.getName()).log(
                    Level.SEVERE,
                    "Error selecting rekeningen from database.", ex);
            System.exit(1);
        }
        c.disconnect();
        return totaal;
    }
}
