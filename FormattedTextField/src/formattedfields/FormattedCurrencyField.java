/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package formattedfields;

import java.beans.PropertyChangeSupport;
import java.text.NumberFormat;
import java.util.Locale;
import javax.swing.JFormattedTextField;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

/**
 * Provides a JFormattedTextField that accepts only doubles. Allows for the formats as a currency
 * and min/max values allowed.
 * 
 * @author Mark Pendergast
 * Copyright Mark Pendergast
 */
public class FormattedCurrencyField extends JFormattedTextField{
    
    public static final String PROP_MINVALUE_PROPERTY = "minimum value";
    public static final String PROP_MAXVALUE_PROPERTY = "maximum value";
    private static final String PROP_COUNTRY_PROPERTY = "country";
    private static final String PROP_LANGUAGE_PROPERTY = "language";
     
    private double minValue = -Double.MAX_VALUE;
    private double maxValue = Double.MAX_VALUE;
    private String country = "NL";
    private String language = "nl";
    private Locale locale = new Locale(language, country);
    
    private formattedfields.verifiers.DoubleVerifier verifier;
    
    private PropertyChangeSupport propertySupport;
/**
 *  Default constructor
 */
    public FormattedCurrencyField() {
       this(-Double.MAX_VALUE, Double.MAX_VALUE,0);
    }
/**
 *  Constructor that accepts an initial value
 * 
 * @param value initial value
 * @throws java.lang.IllegalArgumentException
 */
 public FormattedCurrencyField(double value) throws IllegalArgumentException{
        this(-Double.MAX_VALUE, Double.MAX_VALUE,value);
    }
     
 /**
  * Constructor that accepts a min and max value
  * @param min min valid value
  * @param max max valid value
  * @throws java.lang.IllegalArgumentException
  */   
   public FormattedCurrencyField(double min, double max) throws IllegalArgumentException{
        this(min,max,0);
    }
 
   /**
    *  Constructor that accepts min, max, and initial values
    * 
    * @param min min valid value
    * @param max max valid value
    * @param value initial value
    * @throws java.lang.IllegalArgumentException
    */  
     public FormattedCurrencyField(double min, double max, double value) throws IllegalArgumentException{
        propertySupport = new PropertyChangeSupport(this);

        setValue(new Double(value));
        setText("");
        minValue = min;
        maxValue = max;
        if(min > max)
            throw new IllegalArgumentException("min value cannot be greater than max value");
        setVerifierAndFormatters();
    }
     
     private void setVerifierAndFormatters() {
                // create verifier
        verifier = new formattedfields.verifiers.DoubleVerifier(minValue, maxValue, locale);
        setInputVerifier(verifier);
        // create formatters
        locale = new Locale(language, country);
        NumberFormatter def = new NumberFormatter();
        def.setValueClass(Double.class);
        //l = new Locale("us", "US");
        
        NumberFormat lcf = NumberFormat.getCurrencyInstance(locale);
        NumberFormat ldf = NumberFormat.getNumberInstance(locale);
        NumberFormatter disp = new NumberFormatter(lcf);
        disp.setValueClass(Double.class);
        NumberFormatter ed = new NumberFormatter(ldf);
        ed.setValueClass(Double.class);
        // setup formatter factory
        DefaultFormatterFactory dff = new DefaultFormatterFactory(def,disp,ed);
        setFormatterFactory(dff);      
     }
     /**
      *  accessor for minimum valid value
      * 
      * @return min valid value
      */
      public double getMinValue() {
        return minValue;
    }
    /**
     * accessor for maximum valid value
     * 
     * @return maximum valid value
     */
    public double getMaxValue() {
        return maxValue;
    }
    /**
     *  mutator for minimum valid value
     * 
     * @param value minimum valid value
     * @throws java.lang.IllegalArgumentException
     */
    public void setMinValue(double value) throws IllegalArgumentException{
        if(value > maxValue)
               throw new IllegalArgumentException("min value cannot be greater than max value");
        double oldValue = minValue;
        minValue  = value;
        propertySupport.firePropertyChange(PROP_MINVALUE_PROPERTY, oldValue, minValue);
        verifier.setMinValue(value);
    }
    /**
     *  mutator for maximum valid value
     * 
     * @param value maximum valid value
     * @throws java.lang.IllegalArgumentException
     */
    public void setMaxValue(double value) throws IllegalArgumentException {
        if(value < minValue)
               throw new IllegalArgumentException("max value cannot be less than min value");
        double oldValue = maxValue;
        maxValue  = value;
        propertySupport.firePropertyChange(PROP_MAXVALUE_PROPERTY, oldValue, maxValue);
        verifier.setMaxValue(value);
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        String oldValue = country;
        this.country = country;
        setVerifierAndFormatters();
        propertySupport.firePropertyChange(PROP_COUNTRY_PROPERTY, oldValue, country);
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        String oldValue = language;
        this.language = language;
        setVerifierAndFormatters();
        propertySupport.firePropertyChange(PROP_LANGUAGE_PROPERTY, oldValue, language);
    }
   
  
}

