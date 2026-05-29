import java.util.HashMap;
import java.util.Iterator;

/**
 * Diese Klasse sammelt und liefert statistische Daten über den
 * Zustand eines Feldes. Auf sehr flexible Weise: Es wird ein
 * Zähler angelegt und gepflegt für jede Objektklasse, die im
 * Feld gefunden wird.
 * 
 * @author David J. Barnes and Michael Kolling
 * @version 2003-04-12
 */
public class FeldStatistik
{
    // Die Zähler für die jeweiligen Akteurstypen (Fuchs, Hase, etc.)
    // in der Simulation.
    private HashMap zaehler;
    // Sind die Zählerstände momentan aktuell?
    private boolean zaehlerAktuell;

    /**
     * Erzeuge ein Objekt für die Feldstatistik.
     */
    public FeldStatistik()
    {
        // Wir legen eine Sammlung für die Zähler an, die wir für
        // die gefundenen Tierarten erzeugen.
        zaehler = new HashMap();
        zaehlerAktuell = true;
    }

    /**
     * @return Eine Beschreibung, welche Tiere das
     *          Feld bevölkern.
     */
    public String gibBewohnerInfo(Feld feld)
    {
        StringBuffer buffer = new StringBuffer();
        if(!zaehlerAktuell) {
            gibZaehlerstaende(feld);
        }
        Iterator schluessel = zaehler.keySet().iterator();
        while(schluessel.hasNext()) {
            Zaehler info = (Zaehler) zaehler.get(schluessel.next());
            buffer.append(info.gibName());
            buffer.append(": ");
            buffer.append(info.gibStand());
            buffer.append(' ');
        }
        return buffer.toString();
    }
    
    /**
     * Verwerfe alle bisher gesammelten Daten; setze alle Zähler
     * auf Null zurück.
     */
    public void zuruecksetzen()
    {
        zaehlerAktuell = false;
        Iterator schluessel = zaehler.keySet().iterator();
        while(schluessel.hasNext()) {
            Zaehler z = (Zaehler) zaehler.get(schluessel.next());
            z.zuruecksetzen();
        }
    }

    /**
     * Erhöhe den Zähler für eine Tierklasse.
     */
    public void erhoeheZaehler(Class tierklasse)
    {
        Zaehler z = (Zaehler) zaehler.get(tierklasse);
        if(z == null) {
            // Wir haben noch keinen Zähler für
            // diese Spezies - also neu anlegen
            z = new Zaehler(tierklasse.getName());
            zaehler.put(tierklasse, z);
        }
        z.erhoehen();
    }

    /**
     * Signalisiere, dass eine Tierzählung beendet ist.
     */
    public void zaehlungBeendet()
    {
        zaehlerAktuell = true;
    }

    /**
     * Stelle fest, ob die Simulation noch aktiv ist, also
     * ob sie weiterhin laufen sollte.
     * @return true wenn noch mehr als eine Spezies lebt.
     */
    public boolean istAktiv(Feld feld)
    {
        // Wieviele Zähler sind nicht Null.
        int nichtNull = 0;
        if(!zaehlerAktuell) {
            gibZaehlerstaende(feld);
        }
        Iterator schluessel = zaehler.keySet().iterator();
        while(schluessel.hasNext()) {
            Zaehler info = (Zaehler) zaehler.get(schluessel.next());
            if(info.gibStand() > 0) {
                nichtNull++;
            }
        }
        return nichtNull > 1;
    }
    
    /**
     * Erzeuge Zähler für die Anzahl der Füchse und Hasen.
     * Diese werden nicht ständig aktuell gehalten, während
     * Füchse und Hasen in das Feld gesetzt werden, sondern
     * jeweils bei der Abfrage der Zählerstände berechnet.
     */
    private void gibZaehlerstaende(Feld feld)
    {
        zuruecksetzen();
        for(int zeile = 0; zeile < feld.gibTiefe(); zeile++) {
            for(int spalte = 0; spalte < feld.gibBreite(); spalte++) {
                Object tier = feld.gibObjektAn(zeile, spalte);
                if(tier != null) {
                    erhoeheZaehler(tier.getClass());
                }
            }
        }
        zaehlerAktuell = true;
    }
}
