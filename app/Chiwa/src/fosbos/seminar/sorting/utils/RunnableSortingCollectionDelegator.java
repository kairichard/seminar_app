/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fosbos.seminar.sorting.utils;

import fosbos.seminar.sorting.Sorter;
import fosbos.seminar.sorting.AbstractSortingDecorator;
import fosbos.seminar.sorting.AbstractSortingMechanics;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;
import org.apache.commons.collections.Closure;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;

/**
 * Diese Klasse könnte man als Management Klasse betrachten, denn solange mann
 * mit allen Algorithmen auf die selbe art und weise interagieren will macht es
 * Sinn einfach jeden in eine Liste zu schreiben und entsprechende Aufrufe nur einmal
 * zu machen und sie druch diese Klasse an alle Alogrithmen in der Liste delegieren
 * zu lassen.
 * @author Kai Richard König
 */
public class RunnableSortingCollectionDelegator implements SortingUtils, Runnable {

    /** Legt fest nach wieviel ms alle Thread benachrichtiget werden sollen. */
    static public int stepInterval = 150;
    /** Liste von Sortern an die delegiert werden soll. */
    private ArrayList<Sorter> algorithms = new ArrayList<Sorter>();    
    /**
     * Da jeder Sorter in einem Thread laufen soll müssen diese auch in einer
     * Liste aufbewahrt werden.
     */
    private ArrayList<Thread> threads = new ArrayList<Thread>();
    /** Timer mit dem alle {@link #stepInterval} ein Event getriggert wird */
    private Timer stepper;
    /** Zeigt an ob der {@link #stepper} angehalten wurde */
    private boolean paused = false;
    /** 
     * Zeigt an ob einer der Sorter das flag {@link AbstractSortingMechanics#running} auf true stehen hat,
     * das implitiert auch das die Liste aller Sorter noch nicht als sortiert betrachtet werden kann.
     */
    private boolean running = false;
    
    /**
     * Gibt zurück ob die Threads benachrichtige werden, sprich ein Timer läuft.
     * @return 
     */
    public boolean isPaused() {
        return paused;
    }
    
    /**
     * Prüft ob einer der Sorter noch nicht fertig ist, sprich noch am sortieren
     * ist.
     * @return 
     */
    public boolean isRunning() {
        running = CollectionUtils.countMatches(algorithms, new IsRunningPredicate() ) > 0;
        return running;
    }

    /**
     * Zerstöre den Timer der verantwortlich ist alle Sorter zu benachrichtigen.
     */
    public void pause() {
        if(!isRunning()) return;      
        safelyRemoveTimer();
        this.paused = true;
    }
    
    private void safelyRemoveTimer() {
        if(this.stepper != null){
            this.stepper.cancel();
            this.stepper.purge();
            this.stepper = null;
        }
    }
    
    /**
     * Instanziere einen neuen Timer der alle Sorter benachrichtiget, in denen 
     * die Sorter laufen, die Arbeit wieder aufzunehmen.
     */
    public void resume() {
        if(!isRunning() || !isPaused() ) return;
        safelyRemoveTimer();

        this.stepper = new Timer();
        this.stepper.scheduleAtFixedRate(new TimedStep(), 0, stepInterval);                
        this.paused = false;
    }

    /**
     * Füge einen Sorter in die Liste von Sortern ein.
     * @param sorter Der Sorter
     * @return Ob die operation funktioniert hat.
     */
    public boolean add(Sorter sorter) {
        return algorithms.add(sorter);
    }

    /**
     * Siehe {@link SortingUtils#setProblem(int[]) }
     * @param problem 
     */
    public void setProblem(int[] problem) {
        Iterator iterator = algorithms.listIterator();
        while (iterator.hasNext()) {
            Sorter element = (Sorter) iterator.next();
            element.setProblem(problem.clone());
        }

    }

    /**
     * Baut einen Threadpool welcher einen Thread für jeden Sorter enthält.
     * Außerdem wird eine Timer instanziert welcher die Sorter benachrichtigt.
     */
    public void run() {
        if(isPaused()) return;
        buildAlgorithmThreadPool();
        CollectionUtils.forAllDo(threads, new Closure() {
            public void execute(Object element) {
                ((Thread) element).start();
            }
        });
        safelyRemoveTimer();
        this.stepper = new Timer();            
        this.stepper.scheduleAtFixedRate(new TimedStep(), 0, RunnableSortingCollectionDelegator.stepInterval);                
    }

    private void buildAlgorithmThreadPool() {
        threads.clear();
        CollectionUtils.forAllDo(algorithms, new Closure() {
            public void execute(final Object algorithm) {
                threads.add(new Thread(new Runnable() {
                    public void run() {
                        ((Sorter) algorithm).sort();
                    }
                },algorithm.toString()));
            }
        });
    }
    
    /**
     * Fall manuell pausiert wurde kann mann auch manuell allen Threads 
     * eine benachrichtung schicken um eine Schritt weiter zu "arbeitern"
     */    
    public void step() {
        if(!isPaused()) return;
        new Timer().schedule(new TimedStep(), RunnableSortingCollectionDelegator.stepInterval);
    }
    
    /**
     * Hilfsklasse die dem benutzen von Listen dient, diese
     * returnd alle elemente für die die eingebaute Bedinunge wahr wird.
     */
    class IsRunningPredicate implements Predicate {
        public boolean evaluate(Object element) {
            return ((AbstractSortingDecorator)element).isRunning() == true;
        }
    }
    
    /**
     * Diese Klasse wenn instanziert und in einen Timer gesteckt 
     * iteriert über alle Sorter und benachrichtigt diese.
     */
    class TimedStep extends TimerTask {
        public void run() {
            Iterator iterator = algorithms.listIterator();
            while (iterator.hasNext()) {
                AbstractSortingDecorator element = (AbstractSortingDecorator) iterator.next();
                synchronized (element.getAlgorithm()) {
                    element.getAlgorithm().notifyAll();
                }
            }
        }
    }
}
