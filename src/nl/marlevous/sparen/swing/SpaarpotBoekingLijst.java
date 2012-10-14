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
import nl.marlevous.sparen.SpaarpotBoeking;
import nl.marlevous.sparen.SpaarpotBoekingen;
import nl.marlevous.sparen.Spaarpotten;

/**
 *
 * @author Leo van Geest
 */
public class SpaarpotBoekingLijst {

    private List<SpaarpotBoeking> boekingenLijst = new ArrayList<>();
    private List<SpaarpotBoeking> legeBoekingenLijst = new ArrayList<>();
    private Map<Long, Integer> spaarpotId2Row = new HashMap<>();

    private SpaarpotBoekingLijst() {
    }

    public static SpaarpotBoekingLijst lees() {
        SpaarpotBoekingLijst lijst = new SpaarpotBoekingLijst();

        Spaarpotten spaarpotten = Spaarpotten.leesSpaarpotten();
        for (int i = 0; i < spaarpotten.size(); i++) {
            SpaarpotBoeking boeking = new SpaarpotBoeking(0, spaarpotten.get(i), 0.0);
            lijst.boekingenLijst.add(i, boeking);
            lijst.spaarpotId2Row.put(spaarpotten.get(i).id(), i);
            lijst.legeBoekingenLijst.add(i, boeking);
        }
        return lijst;
    }

    public SpaarpotBoeking get(int i) {
        return boekingenLijst.get(i);
    }

    public void reset(int i) {
        boekingenLijst.set(i, legeBoekingenLijst.get(i));
    }

    public void reset() {
        for (int i = 0; i < boekingenLijst.size(); i++) {
            boekingenLijst.set(i, legeBoekingenLijst.get(i));
        }
    }

    public void setVoorSpaarpot(long Id, SpaarpotBoeking boeking) {
        if (spaarpotId2Row.containsKey(Id)) {
            int i = spaarpotId2Row.get((Long) Id);
            boekingenLijst.set(i, boeking);
        }  
    }

    public int size() {
        return boekingenLijst.size();
    }

    public double totaal() {
        double t = 0;
        for (SpaarpotBoeking spaarpotBoeking : boekingenLijst) {
            t += spaarpotBoeking.bedrag();
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
    
    public SpaarpotBoekingen asSpaarpotBoekingen() {
        SpaarpotBoekingen boekingen = new SpaarpotBoekingen();
        for (int i = 0; i < boekingenLijst.size(); i++) {
            SpaarpotBoeking boeking = boekingenLijst.get(i);
            if(boeking.bedrag() != 0.0) {
                boekingen.add(boeking);
            }
            
        }
        return boekingen;
    }
}
