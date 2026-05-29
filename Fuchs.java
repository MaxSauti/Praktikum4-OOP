import java.util.List;
import java.util.Iterator;
import java.util.Random;

/**
 * Ein simples Modell eines Fuchses.
 * Füchse altern, bewegen sich, fressen Hasen und sterben.
 * 
 * @author David J. Barnes and Michael Kolling
 * @version 2003-04-16
 */
public class Fuchs extends Tier
{
    // Eigenschaften aller Füchse (statische Datenfelder)
    
    // Das Alter, in dem ein Fuchs gebärfähig wird.
    private static final int GEBAER_ALTER = 10;
    // Das Höchstalter eines Fuchses.
    private static final int MAX_ALTER = 150;
    // Die Wahrscheinlichkeit, mit der ein Fuchs Nachwuchs gebärt.
    private static final double GEBAER_WAHRSCHEINLICHKEIT = 0.09;
    // Die maximale Größe eines Wurfes (Anzahl der Jungen).
    private static final int MAX_WURFGROESSE = 3;
    // Der Nährwert eines einzelnen Hasen. Letztendlich ist
    // dies die Anzahl der Schritte, die ein Fuchs bis zur
    //nächsten Mahlzeit laufen kann.
    private static final int HASEN_NAEHRWERT = 4;
	// Ein Zufallsgenerator, der die Geburten beeinflusst.
    private static final Random rand = new Random();
    
    // Individuelle Eigenschaften (Instanzfelder).

    // Der Futter-Level, der durch das Fressen von Hasen erhöht wird.
    private int futterLevel;

    /**
     * Erzeuge einen Fuchs. Ein Fuchs wird entweder neu geboren
     * (Alter 0 Jahre und nicht hungrig) oder mit einem zufälligen Alter.
     * 
     * @param zufaelligesAlter falls true, hat der neue Fuchs ein 
     *        zufälliges Alter und einen zufälligen Futter-Level.
     */
    public Fuchs(boolean zufaelligesAlter)
    {
        super();
        if(zufaelligesAlter) {
            setzeAlter(rand.nextInt(MAX_ALTER));
            futterLevel = rand.nextInt(HASEN_NAEHRWERT);
        }
        else {
            // leave age at 0
            futterLevel = HASEN_NAEHRWERT;
        }
    }
    
    /**
     * Das was ein Fuchs die meiste Zeit tut: er jagt Hasen.
     * Dabei kann er Nachwuchs gebären, vor Hunger sterben oder
     * an Altersschwäche.
     */
    public void agiere(Feld aktuellesFeld, Feld naechstesFeld, List neueTiere)
    {
        alterErhoehen();
        hungerVergroessern();
        if(istLebendig()) {
            // neue Füchse werden in Nachbarpositionen geboren.
            int geburten = gebaereNachwuchs();
            for(int b = 0; b < geburten; b++) {
                Fuchs neuerFuchs = new Fuchs(false);
                neueTiere.add(neuerFuchs);
                neuerFuchs.setzePosition(naechstesFeld.zufaelligeNachbarposition(gibPosition()));
                naechstesFeld.platziere(neuerFuchs);
            }
            // In die Richtung bewegen, in der Futter gefunden wurde.
            Position neuePosition = findeNahrung(aktuellesFeld, gibPosition());
            if(neuePosition == null) {  // kein Futter - zufällig bewegen
                neuePosition = naechstesFeld.freieNachbarposition(gibPosition());
            }
            if(neuePosition != null) {
                setzePosition(neuePosition);
                naechstesFeld.platziere(this); // setzt die Position
            }
            else {
                // weder Bleiben noch Gehen möglich - Überpopulation - kein Platz 
                setzeGestorben();
            }
        }
    }
    
    /**
     * Erhöhe das Alter dieses Fuchses. Dies kann zu seinem
     * Tod führen.
     */
    private void alterErhoehen()
    {
        setzeAlter(gibAlter()+1);
        if(gibAlter() > MAX_ALTER) {
            setzeGestorben();
        }
    }
    
    /**
     * Vergrößere den Hunger dieses Fuchses. Dies kann zu seinem
     * Tode führen.
     */
    private void hungerVergroessern()
    {
        futterLevel--;
        if(futterLevel <= 0) {
            setzeGestorben();
        }
    }
    
    /**
     * Suche nach Nahrung (Hasen) in den Nachbarpositionen.
     * @param feld das Feld, in dem gesucht werden soll.
     * @param position die Position, an der sich der Fuchs befindet.
     * @return die Position mit Nahrung, oder null, wenn keine vorhanden.
     */
    private Position findeNahrung(Feld feld, Position position)
    {
        Iterator nachbarPositionen =
                          feld.nachbarpositionen(position);
        while(nachbarPositionen.hasNext()) {
            Position pos = (Position) nachbarPositionen.next();
            Object tier = feld.gibObjektAn(pos);
            if(tier instanceof Hase) {
                Hase hase = (Hase) tier;
                if(hase.istLebendig()) { 
                    hase.setzeGestorben();
                    futterLevel = HASEN_NAEHRWERT;
                    return pos;
                }
            }
        }
        return null;
    }
        
    /**
     * Gebäre Nachwuchs, wenn dieser Fuchs gebärfähig ist.
     * @return die Anzahl der Neugeborenen (kann Null sein).
     */
    private int gebaereNachwuchs()
    {
        int geburten = 0;
        if(kannGebaeren() && rand.nextDouble() <= GEBAER_WAHRSCHEINLICHKEIT) {
            geburten = rand.nextInt(MAX_WURFGROESSE) + 1;
        }
        return geburten;
    }
    
    /**
     * Liefere eine String-Beschreibung dieses Fuchses.
     */
    public String toString()
    {
      return "Fuchs, Alter " + gibAlter();
    }

    /**
     * Ein Fuchs kann gebären, wenn er das gebärfähige
     * Alter erreicht hat.
     */
    private boolean kannGebaeren()
    {
        return gibAlter() >= GEBAER_ALTER;
    }
    
}
