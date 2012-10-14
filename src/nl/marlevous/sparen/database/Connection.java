/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.marlevous.sparen.database;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Note201
 */
public class Connection {

    private java.sql.Connection connection;

    public java.sql.Connection connect() {
        if (connection == null) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Connection.class.getName()).log(
                        Level.SEVERE,
                        "Error loading the MySQL driver.", ex);
                System.exit(1);
            }
            try {
                connection =
                        DriverManager.getConnection(
                        "jdbc:mysql:///finance",
                        "bank",
                        "geld");
            } catch (SQLException ex) {
                Logger.getLogger(Connection.class.getName()).log(
                        Level.SEVERE,
                        "Error connecting to the MySQL database.", ex);
                System.exit(1);
            }
        }
        return connection;
    }

    public void disconnect() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(Connection.class.getName()).log(
                        Level.SEVERE,
                        "Error disconnecting from MySQL database", ex);
                System.exit(1);

            }
            connection = null;
        }
    }
}
