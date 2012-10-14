package nl.marlevous.sparen.swing;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import nl.marlevous.sparen.Spaarpot;
import nl.marlevous.sparen.Spaarpotten;

/**
 *
 * @author Note201
 */
public class SpaarpottenTableModel
        extends AbstractTableModel
        implements TableModel {

    private Spaarpotten spaarpotten;
    private String[] columnNames = {"No", "Naam", "Saldo", "Standaard", "Actief"};
    private boolean[] editable = {false, false, false, false, false};
    private Class<?>[] columnClasses = {
        Long.class,
        String.class,
        double.class,
        double.class,
        Boolean.class
    };

    public void setSpaarpotten(Spaarpotten spaarpotten) {
        this.spaarpotten = spaarpotten;
        this.fireTableDataChanged();
    }

    public SpaarpottenTableModel(Spaarpotten s) {
        this.spaarpotten = s;
    }

    public double totaalSaldo() {
        return this.spaarpotten.totaalSaldo();
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return this.columnClasses[columnIndex];
    }

    @Override
    public int getColumnCount() {
        return this.columnNames.length;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return this.columnNames[columnIndex];
    }

    @Override
    public int getRowCount() {
        return spaarpotten.size() + 1;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object o;
        if (rowIndex < spaarpotten.size()) {
            Spaarpot pot = spaarpotten.get(rowIndex);
            switch (columnIndex) {
                case 0:
                    o = pot.id();
                    break;
                case 1:
                    o = pot.naam();
                    break;
                case 2:
                    o = pot.saldo();
                    break;
                case 3:
                    if (pot.heeftStandaard()) {
                        o = pot.getStandaard();
                    } else {
                        o = "";
                    }
                    break;
                case 4:
                    o = pot.isActief();
                    break;
                default:
                    throw new AssertionError();
            }
        } else {
            switch (columnIndex) {
                case 0:
                    o = "";
                    break;
                case 1:
                    o = "Totalen";
                    break;
                case 2:
                    o = spaarpotten.totaalSaldo();
                    break;
                case 3:
                    o = spaarpotten.totaalStandaard();
                    break;
                case 4:
                    o = false;
                    break;
                default:
                    throw new AssertionError();
            }
        }
        return o;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return editable[columnIndex];
    }
}
