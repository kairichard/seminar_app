
package fosbos.seminar.sorting;

import java.awt.Color;

/**
 * Abstrakte Dekorierer Klasse welche von {@link AbstractSortingMechanics} erbt.
 * - {@see http://www.theserverside.de/decorator-pattern-in-java/ } -
 * Damit ist es möglich das sich Klassen wie ein {@link Sorter} verhalten aber etwas
 * anderes tuen wie z.b. alle Operationen zu synchronisieren.
 * @author Kai Richard König
 */
public abstract class AbstractSortingDecorator extends AbstractSortingMechanics {
    /**
     * Beinhaltet eine Instanz an die alle Methodenaufrufe,
     * nach dem die eigne Logik ausgeführt wurde, weiter propagiert werden.
     * Quasi das Basisprinzip.
     */
    protected final Sorter algorithm;

    /**
     * Nimmt als Parameter alles was sich wie ein {@link Sorter} verhält z.b.  
     * auch eine dekorierte Klasse. Der übergebene {@link Sorter} wird dann 
     * wiederrum mit funktionalität dekoriert.
     * @param algorithm Eine Klasse die {@link Sorter} implementiert also z.b. auch {@link AbstractSortingDecorator }
     */
    public AbstractSortingDecorator(Sorter algorithm) {
        this.algorithm = algorithm;
    }
    
    /**
     * Liefert den {@link Sorter} zurück welche dem Konstruktor übergeben wurde.
     * @return Dekorierter Sorter
     */
    public AbstractSortingMechanics getAlgorithm() {
        return (AbstractSortingMechanics) algorithm;
    }    
    /**
     * Ist rein zu Visalisierungs zwecken, kann eine alles zwischen {@code n} und {@code m} mit der 
     * Farbe {@code color} einfärben.
     * @param n
     * @param m
     * @param color 
     */
    public abstract void highlightRange(int n, int m, Color color);
}
