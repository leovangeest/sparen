package nl.marlevous.sparen;

/**
 *
 * @author Note201
 */
public class Rekening {

    private long id;
    private String naam;
    private double saldo;

    private Rekening() {
    }

    public Rekening(long id, String naam, double saldo) {
        this.id = id;
        this.naam = naam;
        this.saldo = saldo;
    }
    public long id() {
        return this.id;
    }
    
    public String naam() {
        return this.naam;
    }
    
    public double saldo() {
        return this.saldo;
    }
    
}
