/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.marlevous.sparen;

import nl.marlevous.sparen.swing.HoofdMenu;

/**
 *
 * @author Note201
 */
public class Sparen {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                setLookandFeel();
                new HoofdMenu().setVisible(true);
            }
        });
    }

    private static void setLookandFeel() {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            System.exit(1);
        }
    }
}
