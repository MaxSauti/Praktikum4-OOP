import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Jaeger extends Akteur{

    private static int kills;
    private final int range = 5;

    public Jaeger () {
        super();
    }


    /**
     * Agiere Methode des Jägers. Der Jäger sucht sich alle Nachbarfelder die in der Range liegen und
     * tötet die Tiere auf diesen Feldern
     * @param aktuellesFeld Das aktuelle Spielfeld
     * @param naechstesFeld Das Feld des nächsten Schrittes
     * @param neueTiere     Liste der neugeborenen Tiere, bei Jäger unnötig
     */
    public void agiere(Feld aktuellesFeld, Feld naechstesFeld, List neueTiere) {
        /*
        Random rand = new Random();
        int range = rand.nextInt(7) + 1;
        */
        //Gib Schüsse ab

        Iterator tmp = aktuellesFeld.nachbarnInRange(gibPosition(), range);
        while (tmp.hasNext()){
            Position pos = (Position) tmp.next();
            if (pos != null) {
                Akteur opfer = aktuellesFeld.gibObjektAn(pos);
                if (opfer instanceof Jaeger || opfer == null) {
                    continue;
                }
                opfer.setzeGestorben();
                kills++;
            }
        }

        Position neuePosition = naechstesFeld.freieNachbarposition(gibPosition());
        if(neuePosition != null) {
            setzePosition(neuePosition);
        }
        else {
            setzePosition(gibPosition());
        }
        naechstesFeld.platziere(this);
    }

    public static int getKills() {
        return kills;
    }
}
