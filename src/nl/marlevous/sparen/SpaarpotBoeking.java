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

import java.util.Date;

/**
 *
 * @author Leo van Geest
 */
public class SpaarpotBoeking {
    private long id;
    private long transactie;

    public long id() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long transactie() {
        return transactie;
    }

    public void setTransactie(long transactie) {
        this.transactie = transactie;
    }
    private Spaarpot spaarpot;
    private double bedrag;

    public SpaarpotBoeking(long id, Spaarpot spaarpot, double bedrag) {
        this.id = id;
        this.spaarpot = spaarpot;
        this.bedrag = bedrag;
    }
    
    public double bedrag() {
        return bedrag;
    }

    public void setBedrag(double bedrag) {
        this.bedrag = bedrag;
    }

    public Spaarpot spaarpot() {
        return spaarpot;
    }

    public void setSpaarpot(Spaarpot spaarpot) {
        this.spaarpot = spaarpot;
    }            
}
