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
package nl.marlevous.sparen.swing;

import java.util.Date;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import nl.marlevous.sparen.Transactie;

/**
 *
 * @author Leo van Geest
 */
public class TransactieTableModel
        extends AbstractTableModel
        implements TableModel {

    List<Transactie> transactieLijst;
    private String[] columnNames = {"id", "Datum", "Reden"};
    private Class<?>[] columnClasses = {
        Long.class,
        Date.class,
        String.class};

    public void setTransactieLijst(List<Transactie> transactieLijst) {
        this.transactieLijst = transactieLijst;
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public int getRowCount() {
        return transactieLijst.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Transactie t = transactieLijst.get(rowIndex);
        Object o;
        switch (columnIndex) {
            case 0:
                o = t.id();
                break;
            case 1:
                o = t.datum();
                break;
            case 2:
                o = t.reden();
                break;
            default:
                throw new AssertionError();
        }
        return o;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }
   @Override
    public Class<?> getColumnClass(int columnIndex) {
        return this.columnClasses[columnIndex];
    }
}
