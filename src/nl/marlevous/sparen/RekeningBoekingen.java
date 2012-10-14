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

import java.util.ArrayList;
import java.util.List;
import nl.marlevous.sparen.database.RekeningBoekingenDAO;

/**
 *
 * @author Leo van Geest
 */
public class RekeningBoekingen {
    private List<RekeningBoeking> boekingen = new ArrayList<>();
    
    public void add(RekeningBoeking boeking) {
        this.boekingen.add(boeking);
    }
    
    public int size() {
        return boekingen.size();
    }
    
    public RekeningBoeking get(int index) {
        return boekingen.get(index);
    }
    
    public double totaal() {
        double t = 0.0;
        for (RekeningBoeking rekeningBoeking : boekingen) {
            t += rekeningBoeking.bedrag();
        }
        return t;
    }
    
    public static RekeningBoekingen voorTransactie(long transactieID) {
        RekeningBoekingen r = 
                RekeningBoekingenDAO.boekingenVoorTransactie(transactieID);
        return r;
    }
    
    public static RekeningBoekingen standaard() {
        RekeningBoekingen r = RekeningBoekingenDAO.standaardBoekingen();
        return r;
    }
}
