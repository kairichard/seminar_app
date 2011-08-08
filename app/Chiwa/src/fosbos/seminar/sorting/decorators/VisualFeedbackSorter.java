/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fosbos.seminar.sorting.decorators;

import fosbos.seminar.sorting.Sorter;
import fosbos.seminar.jchart2D.painters.PointPainterBlank;
import fosbos.seminar.jchart2D.painters.TracePainterBlank;
import fosbos.seminar.sorting.AbstractSortingDecorator;
import fosbos.seminar.sorting.AbstractSortingMechanics;
import info.monitorenter.gui.chart.Chart2D;
import info.monitorenter.gui.chart.IAxis.AxisTitle;
import info.monitorenter.gui.chart.ITrace2D;
import info.monitorenter.gui.chart.TracePoint2D;
import info.monitorenter.gui.chart.axis.AxisLinear;
import info.monitorenter.gui.chart.traces.Trace2DReplacing;
import info.monitorenter.util.Range;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComponent;
import org.netbeans.lib.awtextra.AbsoluteConstraints;

/**
 *
 * @author kai
 */
public class VisualFeedbackSorter extends AbstractSortingDecorator {

    private static JComponent paintingComponent;
    private static int marginTop = 70;
    private static int marginLeft = 30;
    private static int marginInBetween = 15;
    private static int marginInBetweenTop = 30;
    private static int distanceX = 0;
    private static int distanceY = 0;
    private static Dimension dimension = new Dimension(300, 150);
    private static int numberOfInstances;
    private Chart2D chart;
    private ITrace2D trace;
    private List<TracePoint2D> points;

    public VisualFeedbackSorter(Sorter algorithm) {
        super(algorithm);
        numberOfInstances++;

        initChart();

        VisualFeedbackSorter.paintingComponent.add(chart, calculateConstraints());
    }

    public static void registerWith(JComponent panel) {
        paintingComponent = panel;
    }

    @Override
    public void swap(int m, int n) {
        algorithm.swap(m, n);
        
        points.get(m).setLocation(n, points.get(m).getY());        
        points.get(n).setLocation(m, points.get(n).getY());
        
        TracePoint2D tmp = points.get(m);
        points.set(m, points.get(n));
        points.set(n, tmp);    
        
        chart.repaint();        
    }
    
    @Override
    public void assign(int i, int value){
        algorithm.assign(i, value);
        points.get(i).setLocation((double)i, (double)value);
    }
    
    @Override
    public int compare(final int m, int n) {

        TracePoint2D singlePoint = (TracePoint2D) points.get(m);
        
        PointPainterBlank red = new PointPainterBlank(4, chart);
        red.setColor(Color.red);
        red.setColorFill(Color.red);
        singlePoint.removeAllAdditionalPointPainters();
        singlePoint.addAdditionalPointPainter(red);
        chart.repaint();        

//
//        PointPainterBlank black = new PointPainterBlank(4, chart);
//        black.setColor(Color.black);
//        black.setColorFill(Color.black);
//        singlePoint.removeAllAdditionalPointPainters();
//        singlePoint.addAdditionalPointPainter(black);
//        chart.repaint();        

        return algorithm.compare(m, n);
    }

    @Override
    public void setProblem(int[] problem) {
        algorithm.setProblem(problem);
        trace.removeAllPoints();
        points = new ArrayList<TracePoint2D>(problem.length);
        for (int i = 0; i < problem.length; i++) {
            points.add(i,new TracePoint2D(i, problem[i]));
            points.get(i).addAdditionalPointPainter(new PointPainterBlank(3, chart));
            trace.addPoint(points.get(i));
        }
    }

    public void sort() {
        algorithm.sort();
    }

    private void initChart() {
        chart = new Chart2D();
        chart.setMinimumSize(VisualFeedbackSorter.dimension);
        chart.setPreferredSize(chart.getMinimumSize());

        trace = new Trace2DReplacing();
        trace.setName(((AbstractSortingMechanics)algorithm).name);


        // Add the trace to the chart. This has to be done before adding points (deadlock prevention): 
        trace.setTracePainter(new TracePainterBlank(4,chart));

        AxisLinear x = new AxisLinear();
        AxisLinear y = new AxisLinear();
        chart.setAxisX(x);
        chart.setAxisY(y);
        x.setVisible(true);
        y.setVisible(false);
        y.setAxisTitle(new AxisTitle(""));
        x.setAxisTitle(new AxisTitle(""));
        x.setPaintScale(false);
        y.setRange(new Range(0.0, 100.0));

        chart.addTrace(trace, x, y);

    }

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

    @Override
    public void accquireDecoratedSelf(Sorter heapsort) {
        algorithm.accquireDecoratedSelf(heapsort);
    }

}
