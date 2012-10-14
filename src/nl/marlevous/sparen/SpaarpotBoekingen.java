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
import nl.marlevous.sparen.database.SpaarpotBoekingenDAO;

/**
 *
 * @author Leo van Geest
 */
public class SpaarpotBoekingen {
    private List<SpaarpotBoeking> boekingen = new ArrayList<>();
    
    public void add(SpaarpotBoeking boeking) {
        this.boekingen.add(boeking);
    }
    
    public int size() {
        return boekingen.size();
    }
    
    public SpaarpotBoeking get(int index) {
        return boekingen.get(index);
    }
    
    public double totaal() {
        double t = 0.0;
        for (SpaarpotBoeking spaarpotBoeking : boekingen) {
            t += spaarpotBoeking.bedrag();
        }
        return t;
    }
    
    public static SpaarpotBoekingen voorTransactie(long transactieID) {
        SpaarpotBoekingen s = 
                SpaarpotBoekingenDAO.boekingenVoorTransactie(transactieID);
        return s;
    }
    
        public static SpaarpotBoekingen standaard() {
        SpaarpotBoekingen s = 
                SpaarpotBoekingenDAO.standaardBoekingen();
        return s;
    }
}
