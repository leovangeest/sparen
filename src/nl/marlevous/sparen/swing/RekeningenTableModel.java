/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.marlevous.sparen.swing;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import nl.marlevous.sparen.Rekeningen;

/**
 *
 * @author Note201
 */
public class RekeningenTableModel
        extends AbstractTableModel
        implements TableModel {

    private Rekeningen rekeningen;

    public RekeningenTableModel(Rekeningen r) {
        this.rekeningen = r;
    }
    private String[] columnNames = {"No", "Naam", "Saldo"};
    private boolean[] editable = {false, true, false};
    private Class<?>[] columnClasses = {Long.class, String.class, double.class};
    
    public double totaalSaldo() {
        return this.rekeningen.totaalSaldo();
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
        return rekeningen.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object o;

        switch (columnIndex) {
            case 0:
                o = rekeningen.get(rowIndex).id();
                break;
            case 1:
                o = rekeningen.get(rowIndex).naam();
                break;
            case 2:
                o = rekeningen.get(rowIndex).saldo();
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
