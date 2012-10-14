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

/**
 *
 * @author Leo van Geest
 */
public class RekeningBoeking {

    private long id;
    private long transactie;
    private Rekening rekening;
    private double bedrag;

    public RekeningBoeking() {
    }

    public RekeningBoeking(
            long id,
            long transactie,
            Rekening rekening,
            double bedrag) {
        
        this.id = id;
        this.transactie = transactie;
        this.rekening = rekening;
        this.bedrag = bedrag;
    }

    public long id() {
        return this.id;
    }

    public long transactie() {
        return this.transactie;
    }

    public Rekening rekening() {
        return this.rekening;
    }

    public double bedrag() {
        return this.bedrag;
    }

    public void setBedrag(double bedrag) {
        this.bedrag = bedrag;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setTransactie(long transactie) {
        this.transactie = transactie;
    }

    public void setRekening(Rekening rekening) {
        this.rekening = rekening;
    }
}
