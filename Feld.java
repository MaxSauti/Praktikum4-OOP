import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

/**
 * Ein rechteckiges Gitter von Feldpositionen.
 * Jede Position kann ein einzelnes Tier aufnehmen.
 * 
 * @author David J. Barnes and Michael Kolling
 * @version 2003-04-16
 */
public class Feld
{
    private static final Random rand = new Random();
    
    // Die Tiefe und die Breite des Feldes
    private int tiefe, breite;
    // Speicher f�r die Tiere
    private Akteur[][] feld;

    /**
     * Erzeuge ein Feld mit den angegebenen Dimensionen.
     * @param tiefe die Tiefe des Feldes.
     * @param breite die Breite des Feldes.
     */
    public Feld(int tiefe, int breite)
    {
        this.tiefe = tiefe;
        this.breite = breite;
        feld = new Akteur[tiefe][breite];
    }
    
    /**
     * R�ume das Feld.
     */
    public void raeumen()
    {
        for(int zeile = 0; zeile < tiefe; zeile++) {
            for(int spalte = 0; spalte < breite; spalte++) {
                feld[zeile][spalte] = null;
            }
        }
    }
        
    /**
     * Platziere das gegebene Tier an der angegebenen Position.
     * Wenn an der Position bereits ein Tier eingetragen ist,
     * geht es verloren.
     * @param akteur das Tier, das platziert werden soll.
     */
    public void platziere(Akteur akteur)
    {
        Position position = akteur.gibPosition();
        feld[position.gibZeile()][position.gibSpalte()] = akteur;
    }
    
    /**
     * Liefere das Tier an der angegebenen Position, falls vorhanden.
     * @param position die gew�nschte Position.
     * @return das Tier an der angegebenen Position oder null, wenn
     *         dort kein Tier ist.
     */
    public Akteur gibObjektAn(Position position)
    {
        return gibObjektAn(position.gibZeile(), position.gibSpalte());
    }
    
    /**
     * Liefere das Tier an der angegebenen Position, falls vorhanden.
     * @param zeile die gew�nschte Zeile.
     * @param spalte die gew�nschte Spalte.
     * @return das Tier an der angegebenen Position oder null, wenn
     *         dort kein Tier ist.
     */
    public Akteur gibObjektAn(int zeile, int spalte)
    {
        return feld[zeile][spalte];
    }
    
    /**
     * W�hle zuf�llig eine der Positionen, die an die gegebene Position
     * angrenzen, oder die gegebene Position selbst.
     * Die gelieferte Position liegt innerhalb der g�ltigen Grenzen
     * dieses Feldes.
     * @param position die Position, von der ein Nachbar zu w�hlen ist.
     * @return eine g�ltige Position innerhalb dieses Feldes. Das kann
     *         auch die gegebene Position selbst sein.
     */
    public Position zufaelligeNachbarposition(Position position)
    {
        int zeile = position.gibZeile();
        int spalte = position.gibSpalte();
        // Zuf�llig eine Abweichung von -1, 0 oder +1 f�r Zeile und Spalte w�hlen.
        int naechsteZeile = zeile + rand.nextInt(3) - 1;
        int naechsteSpalte = spalte + rand.nextInt(3) - 1;
        // Pr�fen, ob die neue Position au�erhalb der Feldgrenzen liegt.
        if(naechsteZeile < 0 || naechsteZeile >= tiefe
           || naechsteSpalte < 0 || naechsteSpalte >= breite) {
            return position;
        }
        else if(naechsteZeile != zeile || naechsteSpalte != spalte) {
            return new Position(naechsteZeile, naechsteSpalte);
        }
        else {
            return position;
        }
    }
    
    /**
     * Versuche, eine freie Nachbarposition zur gegebenen Position zu
     * finden. Wenn es keine gibt, liefere die gegebene Position, wenn
     * sie selbst frei ist. Ansonsten liefere null.
     * Die gelieferte Position liegt innerhalb der Feldgrenzen.
     * @param position die Position, f�r die eine Nachbarposition
     *                 zu liefern ist.
     * @return eine g�ltige Position innerhalb der Feldgrenzen. Das
     *         kann die gegebene Position selbst sein oder null, wenn
     *         alle Nachbarpositionen und die Position selbst belegt sind.
     */
    public Position freieNachbarposition(Position position)
    {
        Iterator nachbarn = nachbarpositionen(position);
        while(nachbarn.hasNext()) {
            Position naechste = (Position) nachbarn.next();
            if(feld[naechste.gibZeile()][naechste.gibSpalte()] == null) {
                return naechste;
            }
        }
        // Pr�fen, ob die gegebene Position selbst frei ist.
        if(feld[position.gibZeile()][position.gibSpalte()] == null) {
            return position;
        } 
        else {
            return null;
        }
    }

    /**
     * Erzeuge einen Iterator �ber eine gemischte Liste von Nachbarpositionen
     * zu der gegebenen Position. Diese Liste enth�lt nicht die gegebene 
     * Position selbst. Alle Positionen liegen innerhalb des Feldes.
     * @param position die Position, f�r die Nachbarpositionen zu liefern sind.
     * @return ein Iterator �ber Nachbarpositionen zur gegebenen Position.
     */
    public Iterator nachbarpositionen(Position position)
    {
        int zeile = position.gibZeile();
        int spalte = position.gibSpalte();
        LinkedList positionen = new LinkedList();
        for(int zDiff = -1; zDiff <= 1; zDiff++) {
            int naechsteZeile = zeile + zDiff;
            if(naechsteZeile >= 0 && naechsteZeile < tiefe) {
                for(int sDiff = -1; sDiff <= 1; sDiff++) {
                    int naechsteSpalte = spalte + sDiff;
                    // Exclude invalid locations and the original location.
                    if(naechsteSpalte >= 0 && naechsteSpalte < breite && (zDiff != 0 || sDiff != 0)) {
                        positionen.add(new Position(naechsteZeile, naechsteSpalte));
                    }
                }
            }
        }
        Collections.shuffle(positionen,rand);
        return positionen.iterator();
    }

    public Iterator nachbarnInRange(Position pos, int range) {
        LinkedList positionen = new LinkedList();
        Random rand = new Random();

        int zeile = pos.gibZeile();
        int spalte = pos.gibSpalte();

        int naechsteZeile;
        int naechsteSpalte;

        //int randRange = rand.nextInt(range);
        int shoot = rand.nextInt(2);
        if (shoot == 1) {
            for (int i = 1; i <= range; i++) {
                naechsteZeile = pos.gibZeile() + i;
                if (naechsteZeile > gibTiefe() - 1) {
                    break;
                }
                positionen.add(new Position(naechsteZeile, spalte));
            }
        }

        shoot = rand.nextInt(2);
        if (shoot == 1) {
            for (int i = 1; i <= range; i++) {
                naechsteSpalte = pos.gibSpalte() + i;
                if (naechsteSpalte > gibBreite() - 1) {
                    break;
                }
                positionen.add(new Position(zeile, naechsteSpalte));
            }
        }

        shoot = rand.nextInt(2);
        if (shoot == 1) {
            for (int i = 1; i <= range; i++) {
                naechsteZeile = pos.gibZeile() - i;
                if (naechsteZeile < 0) {
                    break;
                }
                positionen.add(new Position(naechsteZeile, spalte));
            }
        }

        shoot = rand.nextInt(2);
        if (shoot == 1) {
            for (int i = 1; i <= range; i++) {
                naechsteSpalte = pos.gibSpalte() - i;
                if (naechsteSpalte < 0) {
                    break;
                }
                positionen.add(new Position(zeile, naechsteSpalte));
            }
        }

        return positionen.iterator();
    }

    /**
     * @return die Tiefe dieses Feldes.
     */
    public int gibTiefe()
    {
        return tiefe;
    }
    
    /**
     * @return die Breite dieses Feldes.
     */
    public int gibBreite()
    {
        return breite;
    }
}
