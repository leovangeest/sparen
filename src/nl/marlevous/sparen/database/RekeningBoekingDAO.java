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

/**
 *
 * @author Leo van Geest
 */
public class RekeningBoekingDAO {

    private RekeningBoeking rekeningBoeking;

    public RekeningBoekingDAO(RekeningBoeking rekeningBoeking) {
        this.rekeningBoeking = rekeningBoeking;
    }

    public void insert() throws Exception {
        if (rekeningBoeking == null) {
            throw (new Exception("Rekeningboeking kan niet null zijn."));
        }
        if (rekeningBoeking.transactie() == 0) {
            throw (new Exception("Transactie kan niet 0 zijn."));
        }

        Connection c = new Connection();
        java.sql.Connection conn = c.connect();

        StringBuilder insertStatement = new StringBuilder();

        insertStatement.append(" INSERT INTO rekeningBoeking");
        insertStatement.append("  (transactie, rekening, bedrag)");
        insertStatement.append(" values (?,?,?)");
        try {
            PreparedStatement stmt =
                    conn.prepareStatement(
                    insertStatement.toString(),
                    Statement.RETURN_GENERATED_KEYS);
            stmt.setLong(1, rekeningBoeking.transactie());
            stmt.setLong(2, rekeningBoeking.rekening().id());
            stmt.setDouble(3, rekeningBoeking.bedrag());

            stmt.execute();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                long id = rs.getLong(1);
                rekeningBoeking.setId(id);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        c.disconnect();
    }
}
