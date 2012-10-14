package nl.marlevous.sparen;

import nl.marlevous.sparen.database.SpaarpotDAO;

/**
 *
 * @author Note201
 */
public class Spaarpot {

    private long id;;
    private String naam;
    private double saldo;
    private double standaard;
    private boolean heeftStandaard;
    private boolean actief;

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public boolean heeftStandaard() {
        return heeftStandaard;
    }

    public void setHeeftStandaard(boolean heeftStandaard) {
        this.heeftStandaard = heeftStandaard;
    }

    public double getStandaard() {
        return standaard;
    }

    public void setStandaard(double standaard) {
        this.standaard = standaard;
    }

    public Spaarpot() {
        this(0,"",0.0,0.0,false);
    }

    public Spaarpot(
            long id,
            String naam,
            double saldo,
            double standaard) {
        this(id, naam, saldo, standaard, true);
    }

    public Spaarpot(
            long id,
            String naam,
            double saldo,
            double standaard,
            boolean actief) {
        this.id = id;
        this.naam = naam;
        this.saldo = saldo;
        this.standaard = standaard;
        if (this.standaard != 0.0) {
            this.heeftStandaard = true;
        } else {
            this.heeftStandaard = false;
        }
        this.actief = actief;
    }

    public boolean isActief() {
        return actief;
    }

    public long id() {
        return id;
    }

    public String naam() {
        return naam;
    }

    public double saldo() {
        return saldo;
    }

    public void slaop() {
        SpaarpotDAO dao = new SpaarpotDAO(this);
        dao.slaop();
    }
}
