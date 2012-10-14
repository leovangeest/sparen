package nl.marlevous.sparen.swing;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFormattedTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableColumn;
import nl.marlevous.sparen.*;
import nl.marlevous.sparen.database.TransactieDAO;

/**
 *
 * @author Note201
 */
public class StortingFormulier extends javax.swing.JFrame {

    private RekeningStortingTableModel rekeningStortingTableModel;
    private RekeningBoekingLijst rekeningBoekingsLijst;
    private SpaarpotStortingTableModel spaarpotStortingTableModel;
    private SpaarpotBoekingLijst spaarpotBoekingsLijst;
    private Transactie transactie;
    Locale l = new Locale("nl", "NL");
    NumberFormat nf = NumberFormat.getCurrencyInstance(l);
    //DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT,l);
    DateFormat df = new SimpleDateFormat("d-M-yyyy");
    /**
     * Creates new form StortingsFormulier
     */
    public StortingFormulier() {
        myInit();
    }

    private void myInit() {
        initComponents();
        transactie = new Transactie();
        transactieReden.getDocument().addDocumentListener(
                new DocumentListener() {

                    @Override
                    public void insertUpdate(DocumentEvent e) {
                        transactie.setReden(transactieReden.getText());
                        checkOKButton();
                    }

                    @Override
                    public void removeUpdate(DocumentEvent e) {
                        transactie.setReden(transactieReden.getText());
                        checkOKButton();
                    }

                    @Override
                    public void changedUpdate(DocumentEvent e) {
                        transactie.setReden(transactieReden.getText());
                        checkOKButton();
                    }
                });
        transactieDatum.setValue(new Date());
        rekeningBoekingsLijst =
                RekeningBoekingLijst.lees();
        rekeningStortingTableModel =
                new RekeningStortingTableModel();
        rekeningStortingTableModel.setRekeningBoekingsLijst(rekeningBoekingsLijst);
        rekeningStortingTableModel.addTableModelListener(
                new RekeningenTabelListener());
        rekeningenTabel.setModel(rekeningStortingTableModel);
        TableColumn col = rekeningenTabel.getColumnModel().getColumn(1);
        col.setCellRenderer(new BedragCellRenderer());

        CurrencyCellEditor ce = new CurrencyCellEditor(new JFormattedTextField());
        col.setCellEditor(ce);

        spaarpotBoekingsLijst =
                SpaarpotBoekingLijst.lees();
        spaarpotStortingTableModel =
                new SpaarpotStortingTableModel();
        spaarpotStortingTableModel.setSpaarpotBoekingsLijst(spaarpotBoekingsLijst);
        spaarpotStortingTableModel.addTableModelListener(
                new SpaarpotTabelListener());
        spaarPottenTabel.setModel(spaarpotStortingTableModel);
        col = spaarPottenTabel.getColumnModel().getColumn(1);
        col.setCellRenderer(new BedragCellRenderer());
        ce = new CurrencyCellEditor(new JFormattedTextField());
        col.setCellEditor(ce);

        rekeningenTabel.changeSelection(0, 1, false, false);

    }

    private void checkOKButton() {
        boolean enable = true;
        if (transactieReden.getText().trim().equals("")) {
            enable = false;
        }
        double dif =
                rekeningStortingTableModel.totaal()
                - spaarpotStortingTableModel.totaal();
        if (dif != 0.0) {
            enable = false;
        }

        if (rekeningBoekingsLijst.isLeeg() && spaarpotBoekingsLijst.isLeeg()) {
            enable = false;
        }
        okButton.setEnabled(enable);
    }

    private void checkTotalen() {
        double t1 = rekeningBoekingsLijst.totaal();
        double t2 = spaarpotBoekingsLijst.totaal();
        double s = t1 - t2;

        lblRekeningSaldo.setVisible(false);
        lblSpaarpotSaldo.setVisible(false);
        if (s != 0.0) {
            lblRekeningSaldo.setText(String.format("%s", nf.format(-s)));
            lblRekeningSaldo.setVisible(true);

            lblSpaarpotSaldo.setText(String.format("%s", nf.format(s)));
            lblSpaarpotSaldo.setVisible(true);

        }
    }

    class RekeningenTabelListener implements TableModelListener {

        @Override
        public void tableChanged(TableModelEvent e) {
            totaalRekening.setValue(rekeningStortingTableModel.totaal());
            checkOKButton();
            checkTotalen();
        }
    }

    class SpaarpotTabelListener implements TableModelListener {

        @Override
        public void tableChanged(TableModelEvent e) {
            totaalSpaarpot.setValue(spaarpotStortingTableModel.totaal());
            checkOKButton();
            checkTotalen();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        rekeningenTabel = new javax.swing.JTable();
        totaalRekening = new javax.swing.JFormattedTextField(nf);
        transactieReden = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        spaarPottenTabel = new javax.swing.JTable();
        standaard = new javax.swing.JButton();
        totaalSpaarpot = new javax.swing.JFormattedTextField(nf);
        okButton = new javax.swing.JButton();
        lblRekeningSaldo = new javax.swing.JLabel();
        lblSpaarpotSaldo = new javax.swing.JLabel();
        transactieDatum = new javax.swing.JFormattedTextField(df);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Storting");
        setLocationByPlatform(true);

        rekeningenTabel.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        rekeningenTabel.setColumnSelectionAllowed(true);
        rekeningenTabel.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                focuslost(evt);
            }
        });
        jScrollPane1.setViewportView(rekeningenTabel);
        rekeningenTabel.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        totaalRekening.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        spaarPottenTabel.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane2.setViewportView(spaarPottenTabel);

        standaard.setText("standaard");
        standaard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                standaardActionPerformed(evt);
            }
        });

        totaalSpaarpot.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        okButton.setText("OK");
        okButton.setEnabled(false);
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });

        lblRekeningSaldo.setForeground(new java.awt.Color(255, 0, 0));
        lblRekeningSaldo.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        lblSpaarpotSaldo.setForeground(new java.awt.Color(255, 0, 0));
        lblSpaarpotSaldo.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(transactieDatum, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(transactieReden)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(standaard))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 321, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(totaalRekening, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblRekeningSaldo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 390, Short.MAX_VALUE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(okButton, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(totaalSpaarpot, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(242, 242, 242)
                                .addComponent(lblSpaarpotSaldo, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(transactieReden, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(standaard)
                    .addComponent(transactieDatum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(totaalRekening, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(totaalSpaarpot, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblSpaarpotSaldo, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 76, Short.MAX_VALUE)
                        .addComponent(okButton))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblRekeningSaldo, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void standaardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_standaardActionPerformed
        rekeningBoekingsLijst.reset();
        spaarpotBoekingsLijst.reset();
        RekeningBoekingen r = RekeningBoekingen.standaard();
        SpaarpotBoekingen s = SpaarpotBoekingen.standaard();
        transactie = new Transactie(0, new Date(), "Standaard overboeking", r, s);
        transactieReden.setText(transactie.reden());
        transactieDatum.setValue(transactie.datum());
        for (int i = 0; i < r.size(); i++) {
            RekeningBoeking boeking = transactie.rekeningBoekingen().get(i);
            rekeningBoekingsLijst.setVoorRekening(
                    boeking.rekening().id(),
                    boeking);
        }
        for (int i = 0; i < s.size(); i++) {
            SpaarpotBoeking boeking = transactie.spaarpotBoekingen().get(i);
            spaarpotBoekingsLijst.setVoorSpaarpot(
                    boeking.spaarpot().id(),
                    boeking);
        }
        rekeningStortingTableModel.fireTableDataChanged();
        spaarpotStortingTableModel.fireTableDataChanged();
    }//GEN-LAST:event_standaardActionPerformed

    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okButtonActionPerformed
        if (transactie != null) {
            transactie.setRekeningBoekingen(
                    rekeningBoekingsLijst.asRekeningBoekingen());
            transactie.setSpaarpotboekingen(
                    spaarpotBoekingsLijst.asSpaarpotBoekingen());
            transactie.setDatum((Date) transactieDatum.getValue());
            TransactieDAO tdao = new TransactieDAO(transactie);
            
            try {
                tdao.save();
            } catch (Exception ex) {
                Logger.getLogger(StortingFormulier.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        this.dispose();
        
    }//GEN-LAST:event_okButtonActionPerformed

    private void focuslost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_focuslost
        System.out.println("Focus Lost");
    }//GEN-LAST:event_focuslost

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblRekeningSaldo;
    private javax.swing.JLabel lblSpaarpotSaldo;
    private javax.swing.JButton okButton;
    private javax.swing.JTable rekeningenTabel;
    private javax.swing.JTable spaarPottenTabel;
    private javax.swing.JButton standaard;
    private javax.swing.JFormattedTextField totaalRekening;
    private javax.swing.JFormattedTextField totaalSpaarpot;
    private javax.swing.JFormattedTextField transactieDatum;
    private javax.swing.JTextField transactieReden;
    // End of variables declaration//GEN-END:variables
}
