package nl.marlevous.sparen.swing;

import javax.swing.JComboBox;
import nl.marlevous.sparen.Rekeningen;

/**
 *
 * @author Note201
 */
public class RekeningenComboBox {
    JComboBox<String> comboBox;
    RekeningenComboBox() {
        comboBox = new JComboBox<>();
        Rekeningen r = Rekeningen.leesRekeningen();
        for (int i = 0; i < r.size(); i++) {
            comboBox.addItem(r.get(i).naam());
            
        }
    }

    public JComboBox<String> comboBox() {
        return this.comboBox;
    }
}
