/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.marlevous.sparen.swing;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import nl.marlevous.sparen.Rekening;
import nl.marlevous.sparen.RekeningBoeking;
import nl.marlevous.sparen.Rekeningen;
import nl.marlevous.sparen.Transactie;
import nl.marlevous.sparen.database.RekeningDAO;

/**
 *
 * @author Note201
 */
public class RekeningenOverzichtTableModel
        extends AbstractTableModel
        implements TableModel {

    private Transactie transactie;
    private Rekeningen rekeningen;
    private double[] totalen = {0.0, 0.0, 0.0};
    private String[] columnNames = {"Rekening", "voor", "bedrag", "na"};
    private boolean[] editable = {false, false, false, false};
    private Class<?>[] columnClasses = {String.class, double.class, double.class, double.class};

    public RekeningenOverzichtTableModel(Transactie transactie) {
        this.transactie = transactie;
        rekeningen = RekeningDAO.getRekeningenTMTransactie(transactie.id());
        for (int i = 0; i < transactie.rekeningBoekingen().size(); i++) {
            RekeningBoeking b = transactie.rekeningBoekingen().get(i);
            Rekening r = rekeningen.getByID(b.rekening().id());
            totalen[0] = totalen[0] + r.saldo() - b.bedrag();
            totalen[1] = totalen[1] + b.bedrag();
            totalen[2] = totalen[2] + r.saldo();
        }
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
        return transactie.rekeningBoekingen().size() + 1;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (rowIndex >= transactie.rekeningBoekingen().size()) {
            if (columnIndex == 0) {
                return "Totaal";
            }
            if (columnIndex == 1) {
                return totalen[0];
            }
            if (columnIndex == 2) {
                return totalen[1];
            }
            if (columnIndex == 3) {
                return totalen[2];
            }
        }
        Object o;

        long ID = this.transactie.rekeningBoekingen().get(rowIndex).rekening().id();
        Rekening r = rekeningen.getByID(ID);
        switch (columnIndex) {
            case 0:
                o = r.naam();
                break;
            case 1:
                o = r.saldo()
                        - this.transactie.rekeningBoekingen().get(rowIndex).bedrag();
                break;
            case 2:
                o = transactie.rekeningBoekingen().get(rowIndex).bedrag();
                break;
            case 3:
                o = r.saldo();
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
