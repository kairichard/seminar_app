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

/**
 *
 * @author kai
 */
public class RunnableSortingCollectionDelegator implements SortingUtils, Runnable {

    static public int stepInterval = 150;
    private ArrayList<Sorter> algorithms = new ArrayList<Sorter>();
    private ArrayList<Thread> threads = new ArrayList<Thread>();
    private Timer stepper;
    private boolean paused = false;
    private boolean running = false;
    
    public boolean isPaused() {
        return paused;
    }
    
    public boolean isRunning() {
        CollectionUtils.forAllDo(algorithms, new IsRunningClosure() );
        return running;
    }

    
    public void pause() {
        if(!isRunning()) return;      
        
        if(this.stepper != null){
            this.stepper.cancel();
            this.stepper.purge();
            this.stepper = null;
        }        
        this.paused = true;
    }
    
    public void resume() {
        if(!isRunning() || !isPaused() ) return;
        
        if(this.stepper != null){
            this.stepper.cancel();
            this.stepper.purge();
            this.stepper = null;
        }

        this.stepper = new Timer();
        this.stepper.scheduleAtFixedRate(new TimedStep(), 0, stepInterval);                
        this.paused = false;
    }

    public boolean add(Sorter e) {
        return algorithms.add(e);
    }

    public void setProblem(int[] problem) {
        Iterator iterator = algorithms.listIterator();
        while (iterator.hasNext()) {
            Sorter element = (Sorter) iterator.next();
            element.setProblem(problem.clone());
        }

    }

    public void run() {
        if(isPaused()) return;
        buildAlgorithmThreadPool();
        CollectionUtils.forAllDo(threads, new Closure() {
            public void execute(Object element) {
                ((Thread) element).start();
            }
        });
        if( this.stepper != null ){ 
            this.stepper.cancel();
            this.stepper.purge();
            this.stepper = null;      
        }
        this.stepper = new Timer();            
        this.stepper.scheduleAtFixedRate(new TimedStep(), 0, RunnableSortingCollectionDelegator.stepInterval);                
    }

    private void buildAlgorithmThreadPool() {
        threads.clear();
        CollectionUtils.forAllDo(algorithms, new Closure() {
            public void execute(final Object closureAlgorithm) {
                threads.add(new Thread(new Runnable() {
                    public void run() {
                        ((Sorter) closureAlgorithm).sort();
                    }
                },closureAlgorithm.toString()));
            }
        });
    }


    public void step() {
        if(!isPaused()) return;
        new Timer().schedule(new TimedStep(), RunnableSortingCollectionDelegator.stepInterval);
    }
    
    class IsRunningClosure implements Closure {
        public void execute(Object element) {
            running = ((AbstractSortingMechanics)element).isRunning();
        }
    }
    
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
