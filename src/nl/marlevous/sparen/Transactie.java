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
package nl.marlevous.sparen;

import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;
import nl.marlevous.sparen.database.TransactieDAO;

/**
 *
 * @author Leo van Geest
 */
public class Transactie {

    private long id;
    private java.util.Date datum;
    private String reden;
    private RekeningBoekingen rekeningBoekingen;

    public void setRekeningBoekingen(RekeningBoekingen rekeningBoekingen) {
        this.rekeningBoekingen = rekeningBoekingen;
    }

    public void setSpaarpotboekingen(SpaarpotBoekingen spaarpotboekingen) {
        this.spaarpotboekingen = spaarpotboekingen;
    }
    private SpaarpotBoekingen spaarpotboekingen;

    public Transactie() {
        this.id = 0;
        this.datum = new Date();
        this.reden = null;
        this.rekeningBoekingen = null;
        this.spaarpotboekingen = null;
    }

    public Transactie(
            long id,
            Date datum,
            String reden,
            RekeningBoekingen rekeningBoekingen,
            SpaarpotBoekingen spaarpotBoekingen) {
        this.id = id;
        this.datum = datum;
        this.reden = reden;
        this.rekeningBoekingen = rekeningBoekingen;
        this.spaarpotboekingen = spaarpotBoekingen;
    }

    public Transactie(
            long id,
            Date datum,
            String reden) {
        this.id = id;
        this.datum = datum;
        this.reden = reden;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public void setReden(String reden) {
        this.reden = reden;
    }

    public java.util.Date datum() {
        return datum;
    }

    public long id() {
        return id;
    }

    public String reden() {
        return reden;
    }

    public RekeningBoekingen rekeningBoekingen() {
        if (this.rekeningBoekingen == null && this.id != 0) {
            this.rekeningBoekingen = RekeningBoekingen.voorTransactie(this.id);
        }
        return rekeningBoekingen;
    }

    public SpaarpotBoekingen spaarpotBoekingen() {
        if(this.spaarpotboekingen == null && this.id != 0) {
            this.spaarpotboekingen = SpaarpotBoekingen.voorTransactie(this.id);
        }
        return spaarpotboekingen;
    }

    public static Transactie lees(long id) {
        Transactie t;
        t = TransactieDAO.getTransactie(id);
        return t;
    }

    public String asHTML() {
        StringBuilder text = new StringBuilder();
        Locale l = new Locale("NL-nl");
        NumberFormat cf = NumberFormat.getInstance(l);
        cf.setMinimumFractionDigits(2);
        cf.setMaximumFractionDigits(2);
        text.append("<TABLE BORDER=\"1\" WIDTH=\"100%\">");
        text.append("<TR>");
        text.append("<TD WIDTH=\"10%\">");
        text.append(this.id());
        text.append("</TD>");
        text.append("<TD>");
        text.append(this.reden());
        text.append("</TD>");
        text.append("</TR>");
        if (this.rekeningBoekingen.size() + this.spaarpotboekingen.size() > 0) {
            text.append("<TR>");
            text.append("<TD colspan=\"2\">");
            text.append("<TABLE WIDTH=\"100%\">");
            if (this.rekeningBoekingen.size() > 0) {
                text.append("<TR>");
                text.append("<TD colspan=\"2\">");
                text.append("Rekeningen");
                text.append("</TD>");
                text.append("</TR>");
                for (int i = 0; i < this.rekeningBoekingen().size(); i++) {
                    RekeningBoeking r = this.rekeningBoekingen().get(i);
                    text.append("<TR>");
                    text.append("<TD>");
                    text.append(r.rekening().naam());
                    text.append("</TD>");
                    text.append("<TD ALIGN=\"RIGHT\" WIDTH=\"20%\">");
                    text.append(cf.format(r.bedrag()));
                    text.append("</TD>");
                    text.append("</TR>");
                }
            }
            if (this.spaarpotboekingen.size() > 0) {
                text.append("<TR>");
                text.append("<TD colspan=\"2\">");
                text.append("Spaarpotten");
                text.append("</TD>");
                text.append("</TR>");
                for (int i = 0; i < this.spaarpotboekingen.size(); i++) {
                    SpaarpotBoeking s = this.spaarpotboekingen.get(i);
                    text.append("<TR>");
                    text.append("<TD>");
                    text.append(s.spaarpot().naam());
                    text.append("</TD>");
                    text.append("<TD ALIGN=\"RIGHT\" WIDTH=\"20%\">");
                    text.append(cf.format(s.bedrag()));
                    text.append("</TD>");
                    text.append("</TR>");
                }
            }
            text.append("</TABLE>");
            text.append("</TD>");
        }
        text.append("</TABLE>");
        return text.toString();
    }
}
