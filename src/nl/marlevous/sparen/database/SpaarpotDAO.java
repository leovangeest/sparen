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
import nl.marlevous.sparen.Spaarpot;
import nl.marlevous.sparen.Spaarpotten;

/**
 *
 * @author Note201
 */
public class SpaarpotDAO {

    private Spaarpot spaarpot;

    public SpaarpotDAO(Spaarpot spaarpot) {
        this.spaarpot = spaarpot;
    }

    public SpaarpotDAO(long voorID) {
        spaarpot = leesSpaarpot(voorID);
    }

    public Spaarpot getSpaarpot() {
        return spaarpot;
    }

    public static Spaarpotten getSpaarpotten() {
        Connection c = new Connection();
        java.sql.Connection conn = c.connect();

        Spaarpotten spaarpotten = new Spaarpotten();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT id, naam, actief FROM spaarpot order by id");
            while (rs.next()) {
                int mysqlActief = rs.getInt("actief");
                double saldo = getSaldo(conn, rs.getLong("id"));
                double standaard = getStandaard(conn, rs.getLong("id"));
                boolean actief = (mysqlActief == 1);
                if (true) {
                    Spaarpot s = new Spaarpot(
                            rs.getLong("id"),
                            rs.getString("naam"),
                            saldo,
                            standaard,
                            actief);
                    spaarpotten.add(s);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(SpaarpotDAO.class.getName()).log(
                    Level.SEVERE,
                    "Error selecting rekeningen from database.", ex);
            System.exit(1);
        }
        c.disconnect();
        return spaarpotten;
    }

    public static Spaarpotten getSpaarpottenTMTransactie(long TransactieID) {
        Connection c = new Connection();
        java.sql.Connection conn = c.connect();

        Spaarpotten spaarpotten = new Spaarpotten();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT id, naam FROM spaarpot order by id");
            while (rs.next()) {
                double saldo = getSaldoTMTransactie(conn,
                        rs.getLong("id"),
                        TransactieID);
                double standaard = getStandaard(conn, rs.getLong("id"));
                Spaarpot s = new Spaarpot(
                        rs.getLong("id"),
                        rs.getString("naam"),
                        saldo, standaard);

                spaarpotten.add(s);
            }
        } catch (SQLException ex) {
            Logger.getLogger(RekeningDAO.class.getName()).log(
                    Level.SEVERE,
                    "Error selecting spaarpotten from database.", ex);
            System.exit(1);
        }
        c.disconnect();
        return spaarpotten;
    }

    private static double getSaldo(java.sql.Connection conn, long SpaarpotID) {

        double saldo = 0;

        StringBuilder selectStatement = new StringBuilder();

        selectStatement.append(" SELECT");
        selectStatement.append("  sum(bedrag) as saldo");
        selectStatement.append(" FROM");
        selectStatement.append("  spaarpotboeking");
        selectStatement.append(" WHERE");
        selectStatement.append("  spaarpot = ?");

        try {
            PreparedStatement stmt =
                    conn.prepareStatement(selectStatement.toString());

            stmt.setLong(1, SpaarpotID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                saldo = rs.getDouble("saldo");
            }
        } catch (SQLException ex) {
            Logger.getLogger(RekeningDAO.class.getName()).log(
                    Level.SEVERE,
                    "Error selecting spaarpotten from database.", ex);
            System.exit(1);
        }
        return saldo;
    }

    private static double getStandaard(java.sql.Connection conn, long SpaarpotID) {

        double standaard = 0;

        StringBuilder selectStatement = new StringBuilder();

        selectStatement.append(" SELECT");
        selectStatement.append("  bedrag ");
        selectStatement.append(" FROM");
        selectStatement.append("  standaardspaarpotboeking");
        selectStatement.append(" WHERE");
        selectStatement.append("  spaarpot = ?");

        try {
            PreparedStatement stmt =
                    conn.prepareStatement(selectStatement.toString());

            stmt.setLong(1, SpaarpotID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                standaard = rs.getDouble("bedrag");
            } else {
                standaard = 0.0;
            }
        } catch (SQLException ex) {
            Logger.getLogger(RekeningDAO.class.getName()).log(
                    Level.SEVERE,
                    "Error selecting standaard from database.", ex);
            System.exit(1);
        }
        return standaard;
    }

    public void saveStandaard() {
        Connection c = new Connection();
        java.sql.Connection conn = c.connect();

        StringBuilder sqlStatement = new StringBuilder();

        sqlStatement.append(" delete");
        sqlStatement.append(" FROM");
        sqlStatement.append("  standaardspaarpotboeking");
        sqlStatement.append(" WHERE");
        sqlStatement.append("  spaarpot = ?");
        try {
            PreparedStatement stmt =
                    conn.prepareStatement(sqlStatement.toString());
            stmt.setLong(1, this.spaarpot.id());
            stmt.execute();
        } catch (SQLException ex) {
            Logger.getLogger(RekeningDAO.class.getName()).log(
                    Level.SEVERE,
                    "Error deleting standaard from database.", ex);
            System.exit(1);
        }
        if (this.spaarpot.heeftStandaard()) {
            sqlStatement = new StringBuilder();

            sqlStatement.append(" insert");
            sqlStatement.append(" INTO");
            sqlStatement.append("  standaardspaarpotboeking");
            sqlStatement.append("  (spaarpot,bedrag)");
            sqlStatement.append(" VALUES");
            sqlStatement.append("  (?,?)");
            try {
                PreparedStatement stmt =
                        conn.prepareStatement(sqlStatement.toString());
                stmt.setLong(1, this.spaarpot.id());
                stmt.setDouble(2, this.spaarpot.getStandaard());
                stmt.execute();
            } catch (SQLException ex) {
                Logger.getLogger(RekeningDAO.class.getName()).log(
                        Level.SEVERE,
                        "Error deleting standaard from database.", ex);
                System.exit(1);
            }
        }
    }

    private static double getSaldoTMTransactie(
            java.sql.Connection conn,
            long spaarpotID,
            long TransactieID) {

        double saldo = 0;

        StringBuilder selectStatement = new StringBuilder();

        selectStatement.append(" SELECT");
        selectStatement.append("  sum(bedrag) as saldo");
        selectStatement.append(" FROM");
        selectStatement.append("  spaarpotboeking");
        selectStatement.append(" WHERE");
        selectStatement.append("  spaarpot = ?");
        selectStatement.append(" AND");
        selectStatement.append("  transactie <= ?");

        try {
            PreparedStatement stmt =
                    conn.prepareStatement(selectStatement.toString());

            stmt.setLong(1, spaarpotID);
            stmt.setLong(2, TransactieID);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                saldo = rs.getDouble("saldo");
            }
        } catch (SQLException ex) {
            Logger.getLogger(RekeningDAO.class.getName()).log(
                    Level.SEVERE,
                    "Error selecting spaarpotten from database.", ex);
            System.exit(1);
        }
        return saldo;
    }

    public static Spaarpot leesSpaarpot(long spaarpotId) {
        Spaarpot s;
        s = null;
        Connection c = new Connection();
        java.sql.Connection conn = c.connect();

        double standaard = getStandaard(conn, spaarpotId);
        StringBuilder selectStatement = new StringBuilder();

        selectStatement.append(" SELECT");
        selectStatement.append("  id, naam, saldo");
        selectStatement.append(" FROM");
        selectStatement.append("  spaarpot");
        selectStatement.append(" WHERE");
        selectStatement.append("  id = ?");

        try {
            PreparedStatement stmt =
                    conn.prepareStatement(selectStatement.toString());

            stmt.setLong(1, spaarpotId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                s = new Spaarpot(
                        rs.getLong("id"),
                        rs.getString("naam"),
                        rs.getDouble("saldo"),
                        standaard);
            }
        } catch (SQLException ex) {
            Logger.getLogger(RekeningDAO.class.getName()).log(
                    Level.SEVERE,
                    "Error selecting spaarpot from database.", ex);
            System.exit(1);
        }
        c.disconnect();
        return s;
    }

    public void slaop() {

        Connection c = new Connection();
        java.sql.Connection conn = c.connect();
        if (spaarpot.id() != 0) {
            try {
                StringBuilder updateStatement = new StringBuilder();

                updateStatement.append(" UPDATE spaarpot");
                updateStatement.append(" SET naam=? WHERE id=?");
                PreparedStatement stmt =
                        conn.prepareStatement(
                        updateStatement.toString(),
                        com.mysql.jdbc.Statement.RETURN_GENERATED_KEYS);
                stmt.setString(1, this.spaarpot.naam());
                stmt.setLong(2, spaarpot.id());

                stmt.execute();
            } catch (SQLException ex) {
                Logger.getLogger(SpaarpotDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            this.saveStandaard();
        } else {
            try {
                StringBuilder insertStatement = new StringBuilder();

                insertStatement.append(" INSERT INTO spaarpot");
                insertStatement.append(" (naam, saldo, actief)");
                insertStatement.append(" VALUES");
                insertStatement.append(" (?,?,?)");

                PreparedStatement stmt =
                        conn.prepareStatement(
                        insertStatement.toString(),
                        com.mysql.jdbc.Statement.RETURN_GENERATED_KEYS);
                stmt.setString(1, this.spaarpot.naam());
                stmt.setDouble(2, 0.0);
                stmt.setBoolean(3, true);
                stmt.execute();
            } catch (SQLException ex) {
                Logger.getLogger(SpaarpotDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            this.saveStandaard();
        }
    }
}
