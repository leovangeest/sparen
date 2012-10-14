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

import com.mysql.jdbc.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import nl.marlevous.sparen.RekeningBoeking;
import nl.marlevous.sparen.SpaarpotBoeking;

/**
 *
 * @author Leo van Geest
 */
public class SpaarpotBoekingDAO {

    private SpaarpotBoeking spaarpotBoeking;

    public SpaarpotBoekingDAO(SpaarpotBoeking spaarpotBoeking) {
        this.spaarpotBoeking = spaarpotBoeking;
    }

    public void insert() throws Exception {
        if (spaarpotBoeking == null) {
            throw (new Exception("Spaarpotboeking kan niet null zijn."));
        }
        if (spaarpotBoeking.transactie() == 0) {
            throw (new Exception("Transactie kan niet 0 zijn."));
        }

        Connection c = new Connection();
        java.sql.Connection conn = c.connect();

        StringBuilder insertStatement = new StringBuilder();

        insertStatement.append(" INSERT INTO spaarpotBoeking");
        insertStatement.append("  (transactie, spaarpot, bedrag)");
        insertStatement.append(" values (?,?,?)");
        try {
            PreparedStatement stmt =
                    conn.prepareStatement(
                    insertStatement.toString(),
                    Statement.RETURN_GENERATED_KEYS);
            stmt.setLong(1, spaarpotBoeking.transactie());
            stmt.setLong(2, spaarpotBoeking.spaarpot().id());
            stmt.setDouble(3, spaarpotBoeking.bedrag());

            stmt.execute();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                long id = rs.getLong(1);
                spaarpotBoeking.setId(id);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        c.disconnect();
    }
}
