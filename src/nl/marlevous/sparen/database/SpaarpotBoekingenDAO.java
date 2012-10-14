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
import nl.marlevous.sparen.Spaarpot;
import nl.marlevous.sparen.SpaarpotBoeking;
import nl.marlevous.sparen.SpaarpotBoekingen;

/**
 *
 * @author Leo van Geest
 */
public class SpaarpotBoekingenDAO {

    public static SpaarpotBoekingen boekingenVoorTransactie(long transactieID) {
        Connection c = new Connection();
        java.sql.Connection conn = c.connect();

        SpaarpotBoekingen boekingen = new SpaarpotBoekingen();
        StringBuilder selectStatement = new StringBuilder();

        selectStatement.append(" SELECT");
        selectStatement.append("  id, transactie, spaarpot, bedrag");
        selectStatement.append(" FROM");
        selectStatement.append("  spaarpotboeking");
        selectStatement.append(" WHERE");
        selectStatement.append("  transactie = ?");
        selectStatement.append(" ORDER BY");
        selectStatement.append("  id");

        try {
            PreparedStatement stmt =
                    conn.prepareStatement(selectStatement.toString());
            stmt.setLong(1, transactieID);
            ResultSet rs = stmt.executeQuery();
            Spaarpot s;
            while (rs.next()) {
                SpaarpotDAO sdao = new SpaarpotDAO(rs.getLong("spaarpot"));
                s = sdao.getSpaarpot();
                SpaarpotBoeking b = new SpaarpotBoeking(
                        rs.getLong("id"),
                        s,
                        rs.getDouble("bedrag"));
                boekingen.add(b);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SpaarpotBoekingenDAO.class.getName()).log(
                    Level.SEVERE,
                    "Error selecting spaarpotten from database.", ex);
            System.exit(1);
        }
        c.disconnect();
        return boekingen;
    }
    
        public static SpaarpotBoekingen standaardBoekingen() {
        Connection c = new Connection();
        java.sql.Connection conn = c.connect();

        SpaarpotBoekingen boekingen = new SpaarpotBoekingen();
        StringBuilder selectStatement = new StringBuilder();

        selectStatement.append(" SELECT");
        selectStatement.append("  id, spaarpot, bedrag");
        selectStatement.append(" FROM");
        selectStatement.append("  standaardspaarpotboeking");
        selectStatement.append(" ORDER BY");
        selectStatement.append("  id");

        try {
            PreparedStatement stmt =
                    conn.prepareStatement(selectStatement.toString());
            ResultSet rs = stmt.executeQuery();
            Spaarpot s;
            while (rs.next()) {
                SpaarpotDAO sdao = new SpaarpotDAO(rs.getLong("spaarpot"));
                s = sdao.getSpaarpot();
                SpaarpotBoeking b = new SpaarpotBoeking(
                        rs.getLong("id"),
                        s,
                        rs.getDouble("bedrag"));
                boekingen.add(b);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SpaarpotBoekingenDAO.class.getName()).log(
                    Level.SEVERE,
                    "Error selecting spaarpotten from database.", ex);
            System.exit(1);
        }
        c.disconnect();
        return boekingen;
    }
}
