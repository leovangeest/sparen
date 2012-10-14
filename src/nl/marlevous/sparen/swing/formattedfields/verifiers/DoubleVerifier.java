package nl.marlevous.sparen.swing.formattedfields.verifiers;

import java.awt.Color;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.Locale;
import javax.swing.InputVerifier;
import javax.swing.text.JTextComponent;

/**
 *
 * @author Mark Pendergast Copyright Mark Pendergast
 */
public class DoubleVerifier extends InputVerifier {

    protected double minValue = -Double.MAX_VALUE;
    protected double maxValue = Double.MAX_VALUE;
    protected static final Color INVALID_COLOR = Color.red;
    protected static final Color VALID_COLOR = Color.black;
    private Locale locale;

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }
    /**
     * Creates an Verifier object that makes sure the text can be parsed into a
     * double between MIN_VALUE and MAX_VALUE
     */
    public DoubleVerifier() {
    }

    /**
     * Creates an Verifier object that makes sure the text can be parsed into a
     * double between min and max
     *
     * @param min lowest valid value
     * @param max highest valid value
     * @throws java.lang.IllegalArgumentException
     */
    public DoubleVerifier(double min, double max) throws IllegalArgumentException {
        if (min > max) {
            throw new IllegalArgumentException("min value must be less than max value");
        }
        minValue = min;
        maxValue = max;
    }

    /**
     * verifies the value in the component can be parsed to a double between
     * minValue and maxValue
     *
     * @param jc a JTextComponent
     * @return returns false if the value is not valid
     */
    public boolean verify(javax.swing.JComponent jc) {
        String text = ((JTextComponent) jc).getText();
        if(locale == null) {
            locale = new Locale("nl","NL");
        }
        NumberFormat ldf = NumberFormat.getNumberInstance(locale);
        ParsePosition pp = new ParsePosition(0);
        double val = 0.0;
        Number nval = ldf.parse(text, pp);
        if(nval != null) {
            val = nval.doubleValue();
        }
        if (text.length() != pp.getIndex()) {
            jc.setForeground(INVALID_COLOR);
            return false;
        }
        if (val < minValue || val > maxValue) {
            jc.setForeground(INVALID_COLOR);
            return false;
        }

        jc.setForeground(VALID_COLOR);
        return true;
    }

    /**
     * Mutator method for minValue, minValue is used to set the lower range of
     * valid numbers.
     *
     * @param value
     * @throws java.lang.IllegalArgumentException
     */
    public void setMinValue(double value) throws IllegalArgumentException {
        if (value > maxValue) {
            throw new IllegalArgumentException("value must be less than maxvalue");
        }
        minValue = value;
    }

    /**
     * Mutator method for maxValue, maxValue is used to set the upper range of
     * valid numbers.
     *
     * @param value new maximum value
     * @throws java.lang.IllegalArgumentException
     */
    public void setMaxValue(double value) throws IllegalArgumentException {
        if (value < minValue) {
            throw new IllegalArgumentException("value must be greater than minvalue");
        }
        maxValue = value;
    }
}
