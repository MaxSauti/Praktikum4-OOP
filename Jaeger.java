import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Jaeger extends Akteur{

    public Jaeger () {
        super();
    }

    public void agiere(Feld aktuellesFeld, Feld naechstesFeld, List neueTiere) {
        Position neuePosition = naechstesFeld.freieNachbarposition(gibPosition());
        if(neuePosition != null) {
            setzePosition(neuePosition);
        }
        else {
            setzePosition(gibPosition());
        }
        naechstesFeld.platziere(this);

        Random rand = new Random();
        int range = rand.nextInt(7) + 1;

        //Gib Schüsse ab

        Iterator tmp = aktuellesFeld.nachbarnInRange(gibPosition(), range);
        while (tmp.hasNext()){
            Position pos = (Position) tmp.next();
            if (pos != null) {
                Object opfer = aktuellesFeld.gibObjektAn(pos);
                if (opfer instanceof Jaeger || opfer == null) {
                    continue;
                }
                ((Akteur) opfer).setzeGestorben();
            }
        }
    }
}
