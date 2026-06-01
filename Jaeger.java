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
        int range = rand.nextInt(5);
        //Gib Schüsse ab
        Position[] dead = new Position[8];
        for (int i = 0; i < dead.length; i++){
            dead[i] = aktuellesFeld.zufaelligeNachbarposition(gibPosition());
        }

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
