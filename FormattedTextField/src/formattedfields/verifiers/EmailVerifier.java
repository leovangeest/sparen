package formattedfields.verifiers;

import java.awt.Color;
import javax.swing.InputVerifier;
import javax.swing.JFormattedTextField;


/**
 *
 * @author Mark Pendergast
 * Copyright Mark Pendergast * 
 */
public class EmailVerifier extends InputVerifier{
  private static final Color INVALID_COLOR = Color.red;
  private static final Color VALID_COLOR = Color.black; 
  static private formattedfields.formatters.EmailFormatter ef = new formattedfields.formatters.EmailFormatter();
 /**
  *  Default constructor
  */
  public EmailVerifier()
  {
    
  }
 /**
  * Check the contents to see if it is a valid email address
  * @param jc the component to be checked
  * @return true if valid, false if not
  */
 public boolean verify(javax.swing.JComponent jc)
 {

   try{
      JFormattedTextField ftf = (JFormattedTextField)jc;
      String email = (String)ef.stringToValue(ftf.getText());
      jc.setForeground(VALID_COLOR); 
      return true;
   }
   catch(Exception e)
   {
    jc.setForeground(INVALID_COLOR);
    return false;
   }

 }
 
}