import java.util.List;

/**
 * Tier ist eine abstrakte Superklasse f�r Tiere. 
 * Sie verwaltet Eigenschaften, die alle Tiere gemein haben,
 * wie etwas das Alter oder eine Position.
 * 
 * @author David J. Barnes and Michael Kolling
 * @version 2003-04-16
 */
public abstract class Tier extends Akteur
{
    // Das Alter dieses Tieres.
    private int alter;
    // Ist dieses Tier noch lebendig?
    private boolean lebendig;

    /**
     * Erzeuge ein Tier mit Alter Null (ein Neugeborenes).
     */
    public Tier()
    {
        alter = 0;
        lebendig = true;
    }

    
    /**
     * Pr�fe, ob dieses Tier noch lebendig ist.
     * @return true wenn dieses Tier noch lebendig ist.
     */
    public boolean istLebendig()
    {
        return lebendig;
    }

    /**
     * Signalisiere diesem Tier, dass es gestorben ist.   :-(
     */
    public void setzeGestorben()
    {
        lebendig = false;
    }
    
    /**
     * Liefere das Alter dieses Tieres.
     * @return das Alter dieses Tieres.
     */
    public int gibAlter()
    {
        return alter;
    }

    /**
     * Setze das Alter dieses Tieres.
     * @param alter das Alter dieses Tieres.
     */
    public void setzeAlter(int alter)
    {
        this.alter = alter;
    }
}
