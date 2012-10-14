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

package nl.marlevous.sparen.swing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nl.marlevous.sparen.RekeningBoeking;
import nl.marlevous.sparen.RekeningBoekingen;
import nl.marlevous.sparen.Rekeningen;

/**
 *
 * @author Leo van Geest
 */
public class RekeningBoekingLijst {
    private List<RekeningBoeking> boekingenLijst = new ArrayList<>();
    private List<RekeningBoeking> legeBoekingenLijst = new ArrayList<>();
    private Map<Long, Integer> rekeningId2Row = new HashMap<>();  
    
    private RekeningBoekingLijst() {
        
    }
    public static RekeningBoekingLijst lees() {
        RekeningBoekingLijst lijst = new RekeningBoekingLijst();
        
        Rekeningen rekeningen = Rekeningen.leesRekeningen();
        for (int i = 0; i < rekeningen.size(); i++) {
            RekeningBoeking boeking = new RekeningBoeking();
            RekeningBoeking legeBoeking = new RekeningBoeking();
            boeking.setRekening(rekeningen.get(i));
            legeBoeking.setRekening(rekeningen.get(i));
            boeking.setBedrag(0.0);
            legeBoeking.setBedrag(0.0);
            lijst.boekingenLijst.add(i, boeking);
            lijst.rekeningId2Row.put(rekeningen.get(i).id(), i);
            lijst.legeBoekingenLijst.add(i, legeBoeking);
        }
        return lijst;
    }
    
    public RekeningBoeking get(int i) {
        return boekingenLijst.get(i);
    }
    
    public RekeningBoekingen asRekeningBoekingen () {
        RekeningBoekingen boekingen = new RekeningBoekingen();
        
        for (int i = 0; i < boekingenLijst.size(); i++) {
            RekeningBoeking boeking = boekingenLijst.get(i);
            if(boeking.bedrag() != 0.0) {
                boekingen.add(boeking);
            }
        }
        return boekingen;
    }
    
    public void reset(int i) {
        boekingenLijst.set(i, legeBoekingenLijst.get(i));
    }
    
    public void reset() {
        for(int i=0; i< boekingenLijst.size(); i++) {
            boekingenLijst.set(i,legeBoekingenLijst.get(i));
        }
    }
    public void setVoorRekening(long Id, RekeningBoeking boeking) {
        int i = rekeningId2Row.get((Long) Id);
        boekingenLijst.set(i, boeking);
    }
    
    public int size() {
        return boekingenLijst.size();
    }
    
    public double totaal() {
        double t = 0;
        for (RekeningBoeking rekeningBoeking : boekingenLijst) {
            t += rekeningBoeking.bedrag();
        }
        return t;
    }
    
    public boolean isLeeg() {
        boolean leeg = true;
        for(int i=0; i < boekingenLijst.size(); i++) {
            if(boekingenLijst.get(i).bedrag() != 0.0) {
                leeg = false;
            }
        }
        return leeg;
    }
    
    public void setTransactieID(long transactieID) {
        for (RekeningBoeking rekeningBoeking : boekingenLijst) {
            rekeningBoeking.setTransactie(transactieID);
        }
    }
}
