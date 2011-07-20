/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chiwa;

import info.monitorenter.gui.chart.Chart2D;
import info.monitorenter.gui.chart.IAxis.AxisTitle;
import info.monitorenter.gui.chart.ITrace2D;
import info.monitorenter.gui.chart.TracePoint2D;
import info.monitorenter.gui.chart.axis.AxisLinear;
import chiwa.TracePainterBlank;
import info.monitorenter.gui.chart.traces.Trace2DSimple;
import info.monitorenter.util.Range;
import java.awt.Color;
import java.awt.Dimension;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javax.swing.SwingUtilities;
import org.jdesktop.application.Application;

/**
 *
 * @author kai
 */
public class Chart {

    private Chart2D chart;
    private ITrace2D trace;
    private boolean running;
    
    synchronized public boolean isRunning() {
        return running;
    }
    
    synchronized public void setRunning(boolean running) {
        this.running = running;
    }
    private volatile TracePoint2D obj;
    private TracePoint2D[] points;

    public ITrace2D getTrace() {
        return trace;
    }
    
    public void setTrace(ITrace2D trace) {
        this.trace = trace;
    }

    public Chart(String string) {
        points = new TracePoint2D[100];
        chart = new Chart2D();
        chart.setMinimumSize(new Dimension(300, 150));
        chart.setPreferredSize(chart.getMinimumSize());
        // Create an ITrace: 
        trace = new Trace2DSimple();        
        trace.setName(string);


        // Add the trace to the chart. This has to be done before adding points (deadlock prevention): 
        TracePainterBlank painter = new TracePainterBlank(chart);
        trace.setTracePainter(painter);
        
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
        
        TracePoint2D first = new TracePoint2D(2, 15);
        TracePoint2D second = new TracePoint2D(1, 10);
        TracePoint2D third = new TracePoint2D(0, 0);
        
        PointPainterBlank red = new PointPainterBlank(4, chart);        
        red.setColor(Color.red);
        red.setColorFill(Color.red);
        
        PointPainterBlank green = new PointPainterBlank(4, chart);        
        green.setColor(Color.green);
        green.setColorFill(Color.green);
        
        PointPainterBlank blue = new PointPainterBlank(4, chart);        
        blue.setColor(Color.blue);
        blue.setColorFill(Color.blue);
        
        TracePoint2D as = new TracePoint2D(3, 25);        
        trace.addPoint(as);
        as = new TracePoint2D(4, 31);        
        trace.addPoint(as);
        trace.addPoint(first);
        trace.addPoint(second);
        trace.addPoint(third);
        
        as.addAdditionalPointPainter(new PointPainterBlank(4, chart));
        
        first.addAdditionalPointPainter(red);
        second.addAdditionalPointPainter(green);        
        third.addAdditionalPointPainter(blue);
        
        trace.removePoint(first);
        red.setColor(Color.CYAN);
        trace.addPoint(first);
        
    }
    
    public Chart2D getChartComponent() {
        return chart;
    }
    
    void updatePoints(int value) {
        points = new TracePoint2D[value+1];
        Random random = new Random();
        trace.removeAllPoints();
        for (int i = value; i >= 0; i--) {
            points[i] = new TracePoint2D(i, random.nextInt() * 10 + i);
            trace.addPoint(points[i]);
            points[i].addAdditionalPointPainter(new PointPainterBlank(3, chart));
        }
    }

    void exampleRunLoop() {
        Runnable r = new Runnable() {

            public void run() {
                try {
                    setRunning(true);
                    highlightAllBars();
                    setRunning(false);
                } catch (Exception x) {
                    setRunning(false);
                    System.out.println(x);
                }
            }
        };
        
        Thread internalThread = new Thread(r, "Highlighter");
        internalThread.start();
    }
    
    void highlightAllBars() throws InterruptedException, InvocationTargetException {
        Iterator<TracePoint2D> pointIt;
        List<TracePoint2D> arrAsList = Arrays.asList(points);
        pointIt = arrAsList.iterator();

        while (pointIt.hasNext()) {
            obj = (TracePoint2D) pointIt.next();            
            
            // SwingUtilities.invokeAndWait(updateColor);
            
            PointPainterBlank red = new PointPainterBlank(4, chart);                
            red.setColor(Color.red);
            red.setColorFill(Color.red);
            obj.removeAllAdditionalPointPainters();
            obj.addAdditionalPointPainter(red);
            chart.setRequestedRepaint(true);
            // SwingUtilities.invokeAndWait(updateColorBlack);
            
            Thread.currentThread().sleep(200);

            PointPainterBlank black = new PointPainterBlank(4, chart);                
            black.setColor(Color.black);
            black.setColorFill(Color.black);
            obj.addAdditionalPointPainter(black);
            chart.setRequestedRepaint(true);

        }
    }
}
