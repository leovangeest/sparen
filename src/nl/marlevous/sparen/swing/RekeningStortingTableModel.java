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

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import nl.marlevous.sparen.RekeningBoeking;

/**
 *
 * @author Leo van Geest
 */
public class RekeningStortingTableModel
        extends AbstractTableModel
        implements TableModel {

    private RekeningBoekingLijst rekeningBoekingsLijst;
    private int count = 0;

    public void setRekeningBoekingsLijst(RekeningBoekingLijst lijst) {
        this.rekeningBoekingsLijst = lijst;
    }

    public double totaal() {
        double t;
        if (rekeningBoekingsLijst != null) {
            t = rekeningBoekingsLijst.totaal();
        } else {
            t = 0.0;
        }
        return t;
    }
    private String[] columnNames = {"Rekening", "Bedrag"};
    private boolean[] editable = {false, true};
    private Class<?>[] columnClasses = {String.class, Double.class};

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public int getRowCount() {
        return rekeningBoekingsLijst.size();

    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object o;
        RekeningBoeking b = rekeningBoekingsLijst.get(rowIndex);
        switch (columnIndex) {
            case 0:
                o = b.rekening().naam();
                break;
            case 1:
                if (b.bedrag() != 0.0) {
                    o = b.bedrag();
                } else {
                    o = null;
                }
                break;
            default:
                throw new AssertionError();
        }
        String res = "null";
        if (o != null) {
            res = o.toString();
        }
        return o;
    }

    @Override
    public String getColumnName(int column) {
        return this.columnNames[column];
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return editable[columnIndex];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return columnClasses[columnIndex];
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if (columnIndex == 1) {
            RekeningBoeking b = rekeningBoekingsLijst.get(rowIndex);
            b.setBedrag((Double) aValue);
            fireTableCellUpdated(rowIndex, columnIndex);
        }
    }
}