package nl.marlevous.sparen.swing;

import java.text.NumberFormat;
import java.util.Locale;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

/**
 * Display a <tt>Number</tt> in a table cell in the format defined by
 * {@link NumberFormat#getCurrencyInstance()}, and aligned to the right.
 */
public class BedragCellRenderer extends DefaultTableCellRenderer implements TableCellRenderer {

    BedragCellRenderer() {
        setHorizontalAlignment(SwingConstants.RIGHT);
    }

    @Override
    public void setValue(Object aValue) {
        Object result = aValue;
        if ((aValue != null) && (aValue instanceof Number)) {
            Locale l = new Locale("nl", "NL");
            Number numberValue = (Number) aValue;
            NumberFormat formatter = NumberFormat.getCurrencyInstance(l);
                result = formatter.format(numberValue.doubleValue());
        }
        super.setValue(result);
    }
}
