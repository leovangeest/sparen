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

import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import nl.marlevous.sparen.Transactie;

/**
 *
 * @author Leo van Geest
 */
public class TransactieTabelSelectionListener implements ListSelectionListener {

    JTable aTable;
    VertoonTransactie toner;
    long VorigeTransactie = 0;

    public TransactieTabelSelectionListener(
            JTable aTable,
            VertoonTransactie toner) {

        this.aTable = aTable;
        this.toner = toner;
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            int selectedRow = aTable.getSelectedRow();
            if (selectedRow >= 0) {
                int modelRow = aTable.convertRowIndexToModel(selectedRow);
                long transactieID = (long) aTable.getModel().getValueAt(modelRow, 0);
                if (transactieID != this.VorigeTransactie) {
                    this.VorigeTransactie = transactieID;
                    toner.Vertoon(Transactie.lees(transactieID));
                }
            }
        }
    }
}
