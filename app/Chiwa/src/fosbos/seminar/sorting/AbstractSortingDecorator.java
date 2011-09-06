
package fosbos.seminar.sorting;

/**
 *
 * @author kai
 */
public abstract  class AbstractSortingDecorator extends AbstractSortingMechanics {
    protected final Sorter algorithm;

    public AbstractSortingMechanics getAlgorithm() {
        return (AbstractSortingMechanics) algorithm;
    }
    public AbstractSortingDecorator(Sorter algorithm) {
        this.algorithm = algorithm;
    }
    @Override
    public abstract void swap(int m,int n);
    
    @Override
    public abstract int compare(int m,int n);
    
    @Override
    public abstract void setProblem(int[] problem);
  
}
