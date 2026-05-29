import java.util.List;

public abstract class Akteur {

    // Die Position dieses Tieres.
    private Position position;

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
}
