package nl.marlevous.sparen.swing;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;


public class DatumCellRenderer extends DefaultTableCellRenderer implements TableCellRenderer {
  
  DatumCellRenderer() { 
    setHorizontalAlignment(SwingConstants.LEFT);  
  }
    @Override
  public void setValue(Object aValue) {
    Object result = aValue;
    if (( aValue != null) && (aValue instanceof Date)) {
      Date dateValue = (Date)aValue;
      DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
      result = format.format(dateValue);
    } 
    super.setValue(result);
  }   
} 