package nl.marlevous.sparen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nl.marlevous.sparen.database.RekeningDAO;
import nl.marlevous.sparen.database.SpaarpotDAO;

/**
 *
 * @author Note201
 */
public class Spaarpotten {

    private List<Spaarpot> spaarpotten = new ArrayList<>();
    private Map<Long, Spaarpot> spaarpottenIDMap = new HashMap<>();
    
    public static Spaarpotten leesSpaarpotten() {
        return SpaarpotDAO.getSpaarpotten();
    }

    public void add(Spaarpot spaarpot) {
        this.spaarpotten.add(spaarpot);
        this.spaarpottenIDMap.put(spaarpot.id(),spaarpot);
    }

    public int size() {
        return this.spaarpotten.size();
    }

    public Spaarpot get(int index) {
        return this.spaarpotten.get(index);
    }

    public Spaarpot getByID(long id) {
        return spaarpottenIDMap.get(id);
    }
    public double totaalSaldo() {
        double saldo = 0;
        for (Spaarpot spaarpot : spaarpotten) {
            saldo += spaarpot.saldo();
        }
        return saldo;
    }
    
    public double totaalStandaard() {
        double totaal = 0;
        for (Spaarpot spaarpot : spaarpotten) {
            totaal += spaarpot.getStandaard();
        }
        return totaal;
    }
    
    public double standaardBalans() {
        double standaardRekening = RekeningDAO.getStandaardTotaal();
        double standaardSpaarpot = this.totaalStandaard();
        
        return standaardSpaarpot - standaardRekening;
    }
}
