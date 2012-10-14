/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.marlevous.sparen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nl.marlevous.sparen.database.RekeningDAO;

/**
 *
 * @author Note201
 */
public class Rekeningen {

    private List<Rekening> rekeningen = new ArrayList<>();
    private Map<Long,Rekening> rekeningenIDMap = new HashMap<>();

    public static Rekeningen leesRekeningen() {
        return RekeningDAO.getRekeningen();
    }

    public void add(Rekening rekening) {
        this.rekeningen.add(rekening);
        this.rekeningenIDMap.put(rekening.id(),rekening);
    }
    
    public int size() {
        return this.rekeningen.size();
    }
    
    public Rekening get(int index) {
        return this.rekeningen.get(index);
    }
    
    public Rekening getByID(long id) {
        return this.rekeningenIDMap.get(id);
    }
    public double totaalSaldo() {
        double saldo = 0;
        for (Rekening rekening : rekeningen) {
            saldo += rekening.saldo();
        }
        return saldo;
    }
}