/*
 * Copyright (C) 2012 MarLeVous
 *
 * This program is made by MarLevous Home Grown Software.
 * There are no guarantees about the correct working of
 * this software. Use it at your own risk.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 */
package nl.marlevous.sparen.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import nl.marlevous.sparen.Rekening;
import nl.marlevous.sparen.RekeningBoeking;
import nl.marlevous.sparen.RekeningBoekingen;

/**
 *
 * @author Leo van Geest
 */
public class RekeningBoekingenDAO {

    public static RekeningBoekingen boekingenVoorTransactie(long transactieID) {
        Connection c = new Connection();
        java.sql.Connection conn = c.connect();

        RekeningBoekingen boekingen = new RekeningBoekingen();
        StringBuilder selectStatement = new StringBuilder();

        selectStatement.append(" SELECT");
        selectStatement.append("  id, transactie, rekening, bedrag");
        selectStatement.append(" FROM");
        selectStatement.append("  rekeningboeking");
        selectStatement.append(" WHERE");
        selectStatement.append("  transactie = ?");
        selectStatement.append(" ORDER BY");
        selectStatement.append("  id");

        try {
            PreparedStatement stmt =
                    conn.prepareStatement(selectStatement.toString());
            stmt.setLong(1, transactieID);
            ResultSet rs = stmt.executeQuery();
            Rekening r;
            while (rs.next()) {
                RekeningDAO rdao = new RekeningDAO(rs.getLong("rekening"));
                r = rdao.getRekening();
                RekeningBoeking b = new RekeningBoeking(
                        rs.getLong("id"),
                        transactieID,
                        r,
                        rs.getDouble("bedrag"));
                boekingen.add(b);
            }
        } catch (SQLException ex) {
            Logger.getLogger(RekeningDAO.class.getName()).log(
                    Level.SEVERE,
                    "Error selecting rekeningboekingen from database.", ex);
            System.exit(1);
        }
        c.disconnect();
        return boekingen;
    }

    public static void deleteVoorTransactie(long transactieID) {
        Connection c = new Connection();
        java.sql.Connection conn = c.connect();

        StringBuilder deleteStatement = new StringBuilder();

        deleteStatement.append(" DELETE FROM");
        deleteStatement.append("  rekeningboeking");
        deleteStatement.append(" WHERE");
        deleteStatement.append("  transactie = ?");

        try {
            PreparedStatement stmt =
                    conn.prepareStatement(deleteStatement.toString());
            stmt.setLong(1, transactieID);
            int n = stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(RekeningDAO.class.getName()).log(
                    Level.SEVERE,
                    "Error deleting rekeninboekingen from database.", ex);
            System.exit(1);
        }
        c.disconnect();
    }

    public static RekeningBoekingen standaardBoekingen() {
        Connection c = new Connection();
        java.sql.Connection conn = c.connect();

        RekeningBoekingen boekingen = new RekeningBoekingen();
        StringBuilder selectStatement = new StringBuilder();

        selectStatement.append(" SELECT");
        selectStatement.append("  id, rekening, bedrag");
        selectStatement.append(" FROM");
        selectStatement.append("  standaardrekeningboeking");
        selectStatement.append(" ORDER BY");
        selectStatement.append("  id");

        try {
            PreparedStatement stmt =
                    conn.prepareStatement(selectStatement.toString());
            ResultSet rs = stmt.executeQuery();
            Rekening r;
            while (rs.next()) {
                RekeningDAO rdao = new RekeningDAO(rs.getLong("rekening"));
                r = rdao.getRekening();
                RekeningBoeking b = new RekeningBoeking();
                b.setId(rs.getLong("id"));
                b.setRekening(r);
                b.setBedrag(rs.getDouble("bedrag"));
                boekingen.add(b);
            }
        } catch (SQLException ex) {
            Logger.getLogger(RekeningDAO.class.getName()).log(
                    Level.SEVERE,
                    "Error selecting rekeningen from database.", ex);
            System.exit(1);
        }
        c.disconnect();
        return boekingen;
    }
}
