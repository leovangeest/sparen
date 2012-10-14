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

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import javax.swing.DefaultCellEditor;
import javax.swing.JFormattedTextField;
import javax.swing.SwingConstants;

class CurrencyCellEditor extends DefaultCellEditor {

    private NumberFormat doubleFormat;

    public CurrencyCellEditor(final JFormattedTextField tf) {
        super(tf);
        Locale l = new Locale("nl", "NL");
        doubleFormat = NumberFormat.getNumberInstance(l);

        tf.setFocusLostBehavior(JFormattedTextField.COMMIT);
        tf.setHorizontalAlignment(SwingConstants.RIGHT);
        tf.setBorder(null);

        delegate = new EditorDelegate() {

            @Override
            public void setValue(Object param) {
                Double _value = (Double) param;
                if (_value == null) {
                    tf.setValue("");
                } else {
                    double _d = _value.doubleValue();
                    String _format = doubleFormat.format(_d);
                    tf.setValue(_format);
                }
            }

            @Override
            public Object getCellEditorValue() {
                try {
                    String _field = tf.getText();
                    Number _number = doubleFormat.parse(_field);
                    double _parsed = _number.doubleValue();
                    Double d = new Double(_parsed);
                    return d;
                } catch (ParseException e) {
                    return new Double(0.0);
                }
            }
        };
    }
}