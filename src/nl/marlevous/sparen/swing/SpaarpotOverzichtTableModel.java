/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.marlevous.sparen.swing;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import nl.marlevous.sparen.Spaarpot;
import nl.marlevous.sparen.Spaarpotten;
import nl.marlevous.sparen.Transactie;
import nl.marlevous.sparen.database.SpaarpotDAO;

/**
 *
 * @author Note201
 */
public class SpaarpotOverzichtTableModel
        extends AbstractTableModel
        implements TableModel {

    private Transactie transactie;
    private Spaarpotten spaarpotten;
    private String[] columnNames = {"Spaarpot", "voor", "bedrag", "na"};
    private boolean[] editable = {false, false, false, false};
    private Class<?>[] columnClasses = {String.class, double.class, double.class, double.class};

    public SpaarpotOverzichtTableModel(Transactie transactie) {
        this.transactie = transactie;
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public int getRowCount() {
        return transactie.spaarpotBoekingen().size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object o;
        if (this.spaarpotten == null) {
            spaarpotten = SpaarpotDAO.getSpaarpottenTMTransactie(transactie.id());
        }
        long ID = this.transactie.spaarpotBoekingen().get(rowIndex).spaarpot().id();
        Spaarpot s = spaarpotten.getByID(ID);
        switch (columnIndex) {
            case 0:
                o = s.naam();
                break;
            case 1:
                o = s.saldo()
                        - this.transactie.spaarpotBoekingen().get(rowIndex).bedrag();
                break;
            case 2:
                o = transactie.spaarpotBoekingen().get(rowIndex).bedrag();
                break;
            case 3:
                o = s.saldo();
                break;
            default:
                throw new AssertionError();
        }
        return o;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return editable[columnIndex];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return columnClasses[columnIndex];
    }
}
