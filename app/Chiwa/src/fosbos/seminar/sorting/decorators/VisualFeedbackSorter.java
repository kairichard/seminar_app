package fosbos.seminar.sorting.decorators;

import fosbos.seminar.sorting.Sorter;
import fosbos.seminar.jchart2D.painters.CustomPointPainter;
import fosbos.seminar.sorting.AbstractSortingDecorator;
import fosbos.seminar.sorting.AbstractSortingMechanics;
import fosbos.seminar.sorting.utils.Surveyor;
import info.monitorenter.gui.chart.Chart2D;
import info.monitorenter.gui.chart.IAxis.AxisTitle;
import info.monitorenter.gui.chart.ITrace2D;
import info.monitorenter.gui.chart.TracePoint2D;
import info.monitorenter.gui.chart.axis.AxisLinear;
import info.monitorenter.gui.chart.traces.Trace2DReplacing;
import info.monitorenter.gui.chart.traces.Trace2DSimple;
import info.monitorenter.gui.chart.traces.painters.TracePainterPolyline;
import info.monitorenter.gui.chart.traces.painters.TracePainterVerticalBar;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComponent;
import org.apache.commons.collections.Closure;
import org.apache.commons.collections.CollectionUtils;
import org.netbeans.lib.awtextra.AbsoluteConstraints;

/**
 * Mit diesem Dekorator ist es möglich jedem Aufruf mit einem visuellen Repräsentation
 * des selbigen zu versehen.
 * @author kai
 */
public class VisualFeedbackSorter extends AbstractSortingDecorator {

    /** Auf welcher soll {@link JComponent#add(java.awt.Component) } aufgrufen werden */
    private static JComponent paintingComponent;
    /** Abstand Oben */
    private static int marginTop = 70;
    /** Abstand Links */
    private static int marginLeft = 30;
    /** Abstand zwischen zwei benachbarten Charts */
    private static int marginInBetween = 15;
    /** Abstand zwischen zwei übereinander stehenden Charts */
    private static int marginInBetweenTop = 30;
    /** Hilfvariable zu berechnung der X-Position des Charts */
    private static int distanceX = 0;
    /** Hilfvariable zu berechnung der Y-Position des Charts */
    private static int distanceY = 0;
    /** Hilfvariable für das Ranking von Charts */
    private static int rank = 1;
    /** Größe des Charts */
    public static Dimension dimension = new Dimension(300, 150);
    /** Zählt mit wie oft schon ein {@link VisualFeedbackSorter} instanziert wurde */
    private static int numberOfInstances;
    public static Color colorCompareLeft = new Color(198, 9, 9);
    public static Color colorCompareRight = new Color(198, 9, 9);
    public static Color colorSwapRight = new Color(66, 105, 173);
    public static Color colorSwapLeft = new Color(86, 147, 252);
    public static Color colorHighlight = new Color(200, 200, 200);
    public static Color colorGet = new Color(212, 198, 50);
    public static Color colorAssign = new Color(50, 212, 106);
    private Chart2D chart;
    private ITrace2D trace;
    private ITrace2D measurementTrace;
    private List<TracePoint2D> points;
    private AbsoluteConstraints contraints;
    private ArrayList pointsOfLastSeenOperation = new ArrayList<Integer>();
    private AxisLinear x = new AxisLinear();
    private AxisLinear y = new AxisLinear();

    public VisualFeedbackSorter(Sorter algorithm) {
        super(algorithm);
        initChart();

        VisualFeedbackSorter.numberOfInstances++;
        contraints = calculateConstraints();
        VisualFeedbackSorter.paintingComponent.add(chart, contraints);
    }

    private void initChart() {
        chart = new Chart2D();

        Chart2D _hiddenMeasurementChart = new Chart2D();
        chart.setMinimumSize(VisualFeedbackSorter.dimension);
        chart.setPreferredSize(chart.getMinimumSize());

        trace = new Trace2DReplacing();
        trace.setName(((AbstractSortingMechanics) algorithm).name);
        measurementTrace = new Trace2DSimple();
        measurementTrace.setTracePainter(new TracePainterPolyline());

        measurementTrace.setName(((AbstractSortingMechanics) algorithm).name);


        // Add the trace to the chart. This has to be done before adding points (deadlock prevention): 
        initAxis();

        chart.addTrace(trace);
        _hiddenMeasurementChart.addTrace(measurementTrace);
    }

    @Override
    public void swap(int m, int n) {
        cleanUpHighlights();
        traceSortednessOverOperations();


        algorithm.swap(m, n);

        points.get(m).setLocation(n, points.get(m).getY());
        points.get(n).setLocation(m, points.get(n).getY());

        TracePoint2D tmp = points.get(m);
        points.set(m, points.get(n));
        points.set(n, tmp);

        colorizePoint(n, VisualFeedbackSorter.colorSwapLeft);
        colorizePoint(m, VisualFeedbackSorter.colorSwapRight);

        chart.revalidate();
    }

    @Override
    public void assign(int i, int value) {
        cleanUpHighlights();
        traceSortednessOverOperations();
        colorizePoint(i, VisualFeedbackSorter.colorAssign);

        algorithm.assign(i, value);
        points.get(i).setLocation((double) i, (double) value);
    }

    @Override
    public int getProblemValueAt(int i) {
        cleanUpHighlights();
        colorizePoint(i, VisualFeedbackSorter.colorGet);
        return algorithm.getProblemValueAt(i);
    }

    @Override
    public int compare(final int m, int n) {
        cleanUpHighlights();
        traceSortednessOverOperations();
        colorizePoint(n, VisualFeedbackSorter.colorCompareLeft);
        colorizePoint(m, VisualFeedbackSorter.colorCompareRight);
        chart.revalidate();
        return algorithm.compare(m, n);
    }

    public void highlightRange(int start, int end, Color color) {
        // cleanUpHighlights();
        for (int i = start; i <= end; i++) {
            colorizePoint(i, color);
        }
    }

    public void sort() {
        reset();
        numberOfOperations = 0;
        algorithm.sort();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(VisualFeedbackSorter.class.getName()).log(Level.SEVERE, null, ex);
        }
        finish();
    }

    /**
     * Wird aufgrufen so bald eine Algorithmus nicht mehr "arbeitet" diese 
     * Methode kann genutzt werden um eventuelle Statistiken anzuzeigen.
     */
    @Override
    public void finish() {
        chart.removeTrace(trace);
        chart.addTrace(measurementTrace);
        x.setVisible(true);
        y.setVisible(true);
        y.setAxisTitle(new AxisTitle("Sortedness"));
        x.setAxisTitle(new AxisTitle("      # of operations ( " + ((AbstractSortingMechanics) algorithm).getNumberOfOperations() + " in total )"));
        String suffix = "th";
        switch(rank){
            case 1: suffix = "st"; break;
            case 2: suffix = "nd"; break;
            case 3: suffix = "rd"; break;
        }
        measurementTrace.setName(+rank+suffix+" place is " + ((AbstractSortingMechanics) algorithm).name);
        VisualFeedbackSorter.paintingComponent.revalidate();
        rank++;
    }

    @Override
    public void setProblem(int[] problem) {
        reset();
        algorithm.setProblem(problem);
        trace.setTracePainter(new TracePainterVerticalBar(((VisualFeedbackSorter.dimension.width-50) / problem.length)-2, chart));
        trace.removeAllPoints();
        points = new ArrayList<TracePoint2D>(problem.length);
        for (int i = 0; i < problem.length; i++) {
            points.add(i, new TracePoint2D(i, problem[i]));
            CustomPointPainter a = new CustomPointPainter(4, chart);
            a.setColor(Color.black);
            points.get(i).addAdditionalPointPainter(a);
            trace.addPoint(points.get(i));
        }
    }

    @Override
    public void setDecoratedAlgorithm(Sorter sorter) {
        algorithm.setDecoratedAlgorithm(sorter);
    }
    
    /**
     * Setzt den Chart auf den optischen Ur-Zustand zurück sprich auf 
     * das Balken diagramm.
     */
    private void reset() {
        ((AbstractSortingMechanics)algorithm).resetNumberOfOperations();
        rank = 1;
        chart.removeAllTraces();
        chart.addTrace(trace);
        measurementTrace.removeAllPoints();
        measurementTrace.addPoint(0, 0);
        initAxis();
    }
    /**
     * Schreibt die "Sortiertheit" über die Anzahl an Operation in ein Trace {@code measurementTrace} 
     */
    public void traceSortednessOverOperations() {
        measurementTrace.addPoint(new TracePoint2D(((AbstractSortingMechanics) algorithm).getNumberOfOperations(), Surveyor.levelOfSortedness(((AbstractSortingMechanics) algorithm).getProblem())));
    }
    
    private void initAxis() {
        chart.setAxisYLeft(y, 0);
        chart.setAxisXBottom(x, 0);
        x.setVisible(true);
        y.setVisible(false);
        y.setAxisTitle(new AxisTitle(""));
        x.setAxisTitle(new AxisTitle(""));
        x.setPaintScale(false);
        y.setPaintScale(false);
    }

    /**
     * Entfernt alle Highlighter von jedem eingefärbten Punkt
     */
    private void cleanUpHighlights() {
        CollectionUtils.forAllDo(pointsOfLastSeenOperation, new Closure() {
            public void execute(Object element) {
                points.get(((Integer) element)).removeAllAdditionalPointPainters();
                CustomPointPainter a = new CustomPointPainter(4, chart);
                a.setColor(Color.black);
                points.get(((Integer) element)).addAdditionalPointPainter(a);
            }
        });
    }

    /**
     * Färbt einen Trace-Punk mit {@code color} an stelle {@code index} ein.
     * @param index
     * @param color 
     */
    private void colorizePoint(int index, Color color) {
        TracePoint2D singlePoint = (TracePoint2D) points.get(index);
        CustomPointPainter painter = new CustomPointPainter(4, chart);
        painter.setColor(color);

        singlePoint.removeAllAdditionalPointPainters();
        singlePoint.addAdditionalPointPainter(painter);
        pointsOfLastSeenOperation.add(index);
    }

    /**
     * Berechnet die Position an die ein Chart gerendert werden basierend
     * auf der Anzahl schon vorhandener visuelle Repräsentationen.
     * @return 
     */
    static AbsoluteConstraints calculateConstraints() {

        if (VisualFeedbackSorter.numberOfInstances > 1) {
            VisualFeedbackSorter.distanceX += VisualFeedbackSorter.marginInBetween + VisualFeedbackSorter.dimension.width;
        } else {
            VisualFeedbackSorter.distanceX += VisualFeedbackSorter.marginLeft;
            VisualFeedbackSorter.distanceY += VisualFeedbackSorter.marginTop;
        }

        if (VisualFeedbackSorter.distanceX + VisualFeedbackSorter.marginInBetween + VisualFeedbackSorter.dimension.width > paintingComponent.getWidth()) {
            VisualFeedbackSorter.distanceX = VisualFeedbackSorter.marginLeft;
            VisualFeedbackSorter.distanceY = VisualFeedbackSorter.marginTop + VisualFeedbackSorter.marginInBetweenTop + VisualFeedbackSorter.dimension.height;
        }

        if (VisualFeedbackSorter.distanceY > paintingComponent.getHeight()) {
            throw new Error("Chart is out of Component Bounds");
        }

        return new AbsoluteConstraints(VisualFeedbackSorter.distanceX, VisualFeedbackSorter.distanceY, -1, -1);
    }

    /**
     * Stellt ein zu welcher {@link JComponent} das Chart hinzugefügt werden soll.
     * @param panel 
     */
    public static void registerWith(JComponent panel) {
        paintingComponent = panel;
    }
}
