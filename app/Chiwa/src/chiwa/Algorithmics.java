package chiwa;

/*
 * Interface für alle Algorythmen
 * Vornehmlich all Marker-Interface verwenden
 * da alle Algorythmen auch von Algorithm erben.
 * Das heißt mann sollte:
 *      public void method(Algorithmics algo) {}
 * verwenden.
 * cheers
 * @author Kai Richard König
 */
public interface Algorithmics {
    /**
     * Hier wird die eigentliche Sorttierlogik implementiert
     */
    public void sort();
}
