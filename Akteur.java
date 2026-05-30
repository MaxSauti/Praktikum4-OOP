import java.util.List;

public abstract class Akteur {

    // Die Position dieses Tieres.
    private Position position;
    private boolean lebendig;

    public Akteur(){
        lebendig = true;
    }

    /**
     * Lasse dieses Tier agieren - es soll das tun, was
     * es tun muss oder m�chte.
     */
    abstract public void agiere(Feld aktuellesFeld,
                                Feld naechstesFeld, List neueTiere);

    /**
     * Liefere die Position dieses Tieres.
     * @return die Position dieses Tieres.
     */
    public Position gibPosition()
    {
        return position;
    }

    /**
     * Setze die Position dieses Tieres.
     * @param zeile die vertikale Koordinate der Position.
     * @param spalte die horizontale Koordinate der Position.
     */
    public void setzePosition(int zeile, int spalte)
    {
        this.position = new Position(zeile, spalte);
    }

    /**
     * Setze die Position dieses Tieres.
     * @param position die Position dieses Tieres.
     */
    public void setzePosition(Position position)
    {
        this.position = position;
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
}
