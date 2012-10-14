/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.marlevous.sparen.database;

import com.mysql.jdbc.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import nl.marlevous.sparen.*;

/**
 *
 * @author Note201
 */
public class TransactieDAO {

    private Transactie transactie;

    public TransactieDAO(Transactie transactie) {
        this.transactie = transactie;
    }

    public void save() throws Exception {
        Connection c = new Connection();
        java.sql.Connection conn = c.connect();

        if (transactie.id() == 0) {
            StringBuilder insertStatement = new StringBuilder();

            insertStatement.append(" INSERT INTO transactie");
            insertStatement.append("  (datum, reden)");
            insertStatement.append(" values (?,?)");
            PreparedStatement stmt =
                    conn.prepareStatement(
                    insertStatement.toString(),
                    Statement.RETURN_GENERATED_KEYS);
            java.sql.Date d = new java.sql.Date(transactie.datum().getTime());
            stmt.setDate(1, d);
            stmt.setString(2, transactie.reden());
            stmt.execute();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                long id = rs.getLong(1);
                transactie.setId(id);
            }
        } else {
            RekeningBoekingenDAO.deleteVoorTransactie(transactie.id());

            StringBuilder insertStatement = new StringBuilder();
            insertStatement.append(" UPDATE transactie");
            insertStatement.append(" SET ");
            insertStatement.append("   datum=?, reden=?");
            insertStatement.append(" WHERE ");
            insertStatement.append("   id=?");
            PreparedStatement stmt =
                    conn.prepareStatement(
                    insertStatement.toString());
            java.sql.Date d = new java.sql.Date(transactie.datum().getTime());
            stmt.setDate(1, d);
            stmt.setString(2, transactie.reden());
            stmt.setLong(3, transactie.id());
            stmt.execute();
        }


        for (int i = 0; i < transactie.rekeningBoekingen().size(); i++) {
            RekeningBoeking b = transactie.rekeningBoekingen().get(i);
            b.setTransactie(transactie.id());
            RekeningBoekingDAO rdao = new RekeningBoekingDAO(b);
            rdao.insert();
        }
        for (int i = 0; i < transactie.spaarpotBoekingen().size(); i++) {
            SpaarpotBoeking b = transactie.spaarpotBoekingen().get(i);
            b.setTransactie(transactie.id());
            SpaarpotBoekingDAO sdao = new SpaarpotBoekingDAO(b);
            sdao.insert();
        }

        c.disconnect();
    }

    /**
     * save alleen in de transactie tabel, geen boekingen
     *
     * @throws Exception
     */
    public void saveSimple() throws Exception {
        Connection c = new Connection();
        java.sql.Connection conn = c.connect();

        if (transactie.id() == 0) {
            StringBuilder insertStatement = new StringBuilder();

            insertStatement.append(" INSERT INTO transactie");
            insertStatement.append("  (datum, reden)");
            insertStatement.append(" values (?,?)");
            PreparedStatement stmt =
                    conn.prepareStatement(
                    insertStatement.toString(),
                    Statement.RETURN_GENERATED_KEYS);
            java.sql.Date d = new java.sql.Date(transactie.datum().getTime());
            stmt.setDate(1, d);
            stmt.setString(2, transactie.reden());
            stmt.execute();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                long id = rs.getLong(1);
                transactie.setId(id);
            }
        } else {

            StringBuilder insertStatement = new StringBuilder();
            insertStatement.append(" UPDATE transactie");
            insertStatement.append(" SET ");
            insertStatement.append("   datum=?, reden=?");
            insertStatement.append(" WHERE ");
            insertStatement.append("   id=?");
            PreparedStatement stmt =
                    conn.prepareStatement(
                    insertStatement.toString());
            java.sql.Date d = new java.sql.Date(transactie.datum().getTime());
            stmt.setDate(1, d);
            stmt.setString(2, transactie.reden());
            stmt.setLong(3, transactie.id());
            stmt.execute();
        }

        c.disconnect();
    }

    public static Transactie getTransactie(long TransactieID) {
        Transactie t = null;

        Connection c = new Connection();
        java.sql.Connection conn = c.connect();

        StringBuilder selectStatement = new StringBuilder();

        selectStatement.append(" SELECT");
        selectStatement.append("  id, datum, reden");
        selectStatement.append(" FROM");
        selectStatement.append("  transactie");
        selectStatement.append(" WHERE");
        selectStatement.append("  id = ?");

        try {
            PreparedStatement stmt =
                    conn.prepareStatement(selectStatement.toString());

            stmt.setLong(1, TransactieID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                RekeningBoekingen r =
                        RekeningBoekingen.voorTransactie(TransactieID);
                SpaarpotBoekingen s =
                        SpaarpotBoekingen.voorTransactie(TransactieID);
                t = new Transactie(
                        rs.getLong("id"),
                        rs.getDate("datum"),
                        rs.getString("reden"),
                        r,
                        s);
            }
        } catch (SQLException ex) {
            Logger.getLogger(TransactieDAO.class.getName()).log(
                    Level.SEVERE,
                    "Error selecting rekeningen from database.", ex);
            System.exit(
                    1);
        }
        c.disconnect();
        return t;
    }

    public static List<Transactie> getAlleTransacties() {
        ArrayList<Transactie> lijst = new ArrayList<>();
        Transactie t;
        Connection c = new Connection();
        java.sql.Connection conn = c.connect();

        StringBuilder selectStatement = new StringBuilder();

        selectStatement.append(" SELECT");
        selectStatement.append("  id, datum, reden");
        selectStatement.append(" FROM");
        selectStatement.append("  transactie");
        selectStatement.append(" ORDER BY");
        selectStatement.append("  id DESC");

        try {
            PreparedStatement stmt =
                    conn.prepareStatement(selectStatement.toString());

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                t = new Transactie(
                        rs.getLong("id"),
                        rs.getDate("datum"),
                        rs.getString("reden"));
                lijst.add(t);

            }
        } catch (SQLException ex) {
            Logger.getLogger(TransactieDAO.class.getName()).log(
                    Level.SEVERE,
                    "Error selecting rekeningen from database.", ex);
            System.exit(
                    1);
        }
        c.disconnect();
        return lijst;
    }
}
