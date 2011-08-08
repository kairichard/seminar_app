/*
 * View.java
 */
package chiwa;

import fosbos.seminar.sorting.Sorter;
import fosbos.seminar.sorting.utils.SortingProblemCreator;
import fosbos.seminar.sorting.utils.RunnableSortingCollectionDelegator;
import fosbos.seminar.sorting.decorators.VisualFeedbackSorter;
import fosbos.seminar.sorting.decorators.SynchronizedSorter;
import chiwa.sorters.Bubblesort;
import chiwa.sorters.Heapsort;
import chiwa.sorters.Insertionsort;
import chiwa.sorters.Mergesort;
import chiwa.sorters.Quicksort;
import org.jdesktop.application.Action;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JFrame;

/**
 * The application's main frame.
 */
public class View extends FrameView {
    /* Enthält alle Dekorierten Sortiere */
    protected RunnableSortingCollectionDelegator sorters = new RunnableSortingCollectionDelegator();
    
    /* Timer der alle #busyAnimationRate das Bild des Icons ändert - dadurch entsteht die Rotation */
    private Timer busyIconTimer;
    
    /* Das der Prozess-Indikator im ruhenden Zustand */
    private Icon idleIcon;
    
    /* Array aller Prozess-Indikator zustände*/
    private Icon[] busyIcons = new Icon[15];
    
    /* Enthält den Index des letzten Icons */
    private int busyIconIndex = 0;
    
    /* View für den 'Über' Dialog  */
    private JDialog aboutBox;

    /**
     * Erstellt einen neuen MainFrame 
     * @param app 
     */
    public View(SingleFrameApplication app) {
        super(app);

        initComponents();
        initStatusBar();

        getFrame().pack();
        getFrame().setResizable(false);


        VisualFeedbackSorter.registerWith(mainPanel);

        Sorter bubblesort = new SynchronizedSorter(new VisualFeedbackSorter(new Bubblesort()));
        Sorter heapsort  = new SynchronizedSorter(new VisualFeedbackSorter(new Heapsort()));
        Sorter quicksort = new SynchronizedSorter(new VisualFeedbackSorter(new Quicksort()));
        Sorter mergesort = new SynchronizedSorter(new VisualFeedbackSorter(new Mergesort()));
        Sorter insertionsort = new SynchronizedSorter(new VisualFeedbackSorter(new Insertionsort()));

        bubblesort.accquireDecoratedSelf(bubblesort);
        heapsort.accquireDecoratedSelf(heapsort);
        quicksort.accquireDecoratedSelf(quicksort);
        mergesort.accquireDecoratedSelf(mergesort);
        insertionsort.accquireDecoratedSelf(insertionsort);

        sorters.add(bubblesort);
        sorters.add(heapsort);
        sorters.add(quicksort);
        sorters.add(mergesort);
        sorters.add(insertionsort);
        
        sorters.setProblem(SortingProblemCreator.random(problemSizeSlider.getValue()));

        RunnableSortingCollectionDelegator.stepInterval = 50;

        startStatusbarUpdateHandler();

    }
    /**
     * Startet einen neuen Timer der alle 300ms prüft ob in der #sorters
     * collection noch ein Sorter dabei ist dessen #running-flag auf #true
     * steht und ändert dem entsprechen den Prozess-Indikator und die Statusleiste
     */
    private void startStatusbarUpdateHandler() {
        new Timer(300, new ActionListener() {

            public void actionPerformed(ActionEvent ae) {
                if (sorters.isRunning()) {
                    statusMessageLabel.setText("Running");
                } else {
                    statusMessageLabel.setText("Halted");
                    busyIconTimer.stop();
                    statusAnimationLabel.setIcon(idleIcon);
                    runButton.setEnabled(true);
                    pauseButton.setEnabled(false);
                    updateProblemSetButton.setEnabled(true);
                }
            }
        }).start();
    }

    /**
     * Läde die Prozess-Indikator Icons und Instanziert einen Timer
     * für die animation des Prozess-Indikator Icons
     */
    private void initStatusBar() {
        ResourceMap resourceMap = getResourceMap();

        int busyAnimationRate = resourceMap.getInteger("StatusBar.busyAnimationRate");
        // Lade Icons
        for (int i = 0; i < busyIcons.length; i++) {
            busyIcons[i] = resourceMap.getIcon("StatusBar.busyIcons[" + i + "]");
        }

        // Timer der bei einem Button-Press#actionPerformed gestarted wird 
        busyIconTimer = new Timer(busyAnimationRate, new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                busyIconIndex = (busyIconIndex + 1) % busyIcons.length;
                statusAnimationLabel.setIcon(busyIcons[busyIconIndex]);
            }
        });
        idleIcon = resourceMap.getIcon("StatusBar.idleIcon");
        statusAnimationLabel.setIcon(idleIcon);

    }

    @Action
    /**
     * Zeigt die Über-Box an
     */
    public void showAboutBox() {
        if (aboutBox == null) {
            JFrame mainFrame = App.getApplication().getMainFrame();
            aboutBox = new AboutBox(mainFrame);
            aboutBox.setLocationRelativeTo(mainFrame);
        }
        App.getApplication().show(aboutBox);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        runButton = new javax.swing.JButton();
        stepButton = new javax.swing.JButton();
        pauseButton = new javax.swing.JToggleButton();
        problemSetType = new javax.swing.JComboBox();
        problemSizeSlider = new javax.swing.JSlider();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        problemSetSizeIndicator = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        intervalSlider = new javax.swing.JSlider();
        jLabel3 = new javax.swing.JLabel();
        intervalLabel = new javax.swing.JLabel();
        updateProblemSetButton = new javax.swing.JButton();
        menuBar = new javax.swing.JMenuBar();
        javax.swing.JMenu fileMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem exitMenuItem = new javax.swing.JMenuItem();
        javax.swing.JMenu helpMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem aboutMenuItem = new javax.swing.JMenuItem();
        statusPanel = new javax.swing.JPanel();
        javax.swing.JSeparator statusPanelSeparator = new javax.swing.JSeparator();
        statusMessageLabel = new javax.swing.JLabel();
        statusAnimationLabel = new javax.swing.JLabel();

        mainPanel.setMaximumSize(mainPanel.getPreferredSize());
        mainPanel.setMinimumSize(mainPanel.getPreferredSize());
        mainPanel.setName("mainPanel"); // NOI18N
        mainPanel.setPreferredSize(new java.awt.Dimension(990, 500));
        mainPanel.setSize(mainPanel.getPreferredSize());
        mainPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(chiwa.App.class).getContext().getResourceMap(View.class);
        runButton.setText(resourceMap.getString("runButton.text")); // NOI18N
        runButton.setName("runButton"); // NOI18N
        runButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                runButtonActionPerformed(evt);
            }
        });
        mainPanel.add(runButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 470, -1, -1));

        stepButton.setText(resourceMap.getString("stepButton.text")); // NOI18N
        stepButton.setEnabled(false);
        stepButton.setName("stepButton"); // NOI18N
        stepButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stepButtonActionPerformed(evt);
            }
        });
        mainPanel.add(stepButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 470, -1, -1));

        pauseButton.setText(resourceMap.getString("pauseButton.text")); // NOI18N
        pauseButton.setEnabled(false);
        pauseButton.setName("pauseButton"); // NOI18N
        pauseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pauseButtonActionPerformed(evt);
            }
        });
        mainPanel.add(pauseButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 470, -1, -1));

        problemSetType.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Random", "Reverse", "Flat", "Predefined" }));
        problemSetType.setName("problemSetType"); // NOI18N
        mainPanel.add(problemSetType, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 25, -1, -1));

        problemSizeSlider.setMajorTickSpacing(20);
        problemSizeSlider.setMaximum(50);
        problemSizeSlider.setMinimum(10);
        problemSizeSlider.setMinorTickSpacing(10);
        problemSizeSlider.setSnapToTicks(true);
        problemSizeSlider.setValue(10);
        problemSizeSlider.setName("problemSetSize"); // NOI18N
        problemSizeSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                problemSetSizeChangeHandler(evt);
            }
        });
        mainPanel.add(problemSizeSlider, new org.netbeans.lib.awtextra.AbsoluteConstraints(335, 25, -1, -1));

        jLabel1.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jLabel1.setName("jLabel1"); // NOI18N
        mainPanel.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, -1, -1));

        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N
        mainPanel.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 30, -1, -1));

        problemSetSizeIndicator.setText(resourceMap.getString("problemSetSizeIndicator.text")); // NOI18N
        problemSetSizeIndicator.setName("problemSetSizeIndicator"); // NOI18N
        mainPanel.add(problemSetSizeIndicator, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 30, -1, -1));
        problemSetSizeIndicator.setText("("+problemSizeSlider.getValue()+")");
        problemSetSizeIndicator.getAccessibleContext().setAccessibleName(resourceMap.getString("problemSetSizeIndicator.AccessibleContext.accessibleName")); // NOI18N

        jSeparator1.setName("jSeparator1"); // NOI18N
        mainPanel.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, 930, -1));

        intervalSlider.setMajorTickSpacing(150);
        intervalSlider.setMaximum(300);
        intervalSlider.setMinimum(20);
        intervalSlider.setMinorTickSpacing(5);
        intervalSlider.setSnapToTicks(true);
        intervalSlider.setValue(RunnableSortingCollectionDelegator.stepInterval);
        intervalSlider.setInverted(true);
        intervalSlider.setName("intervalSlider"); // NOI18N
        intervalSlider.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                intervalSliderMouseReleased(evt);
            }
        });
        intervalSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                intervalSliderStateChanged(evt);
            }
        });
        mainPanel.add(intervalSlider, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 470, -1, -1));

        jLabel3.setText("Interval:");
        jLabel3.setName("jLabel3"); // NOI18N
        mainPanel.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 475, -1, -1));

        intervalLabel.setText("(0)");
        intervalLabel.setName("intervalLabel"); // NOI18N
        mainPanel.add(intervalLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(445, 475, -1, -1));
        intervalLabel.setText("("+intervalSlider.getValue()+" ms)");

        updateProblemSetButton.setText("update");
        updateProblemSetButton.setName("updateProblemSetButton"); // NOI18N
        updateProblemSetButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateProblemSetButtonActionPerformed(evt);
            }
        });
        mainPanel.add(updateProblemSetButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 25, -1, -1));

        menuBar.setName("menuBar"); // NOI18N

        fileMenu.setText(resourceMap.getString("fileMenu.text")); // NOI18N
        fileMenu.setName("fileMenu"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(chiwa.App.class).getContext().getActionMap(View.class, this);
        exitMenuItem.setAction(actionMap.get("quit")); // NOI18N
        exitMenuItem.setName("exitMenuItem"); // NOI18N
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        helpMenu.setText(resourceMap.getString("helpMenu.text")); // NOI18N
        helpMenu.setName("helpMenu"); // NOI18N

        aboutMenuItem.setAction(actionMap.get("showAboutBox")); // NOI18N
        aboutMenuItem.setName("aboutMenuItem"); // NOI18N
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        statusPanel.setName("statusPanel"); // NOI18N

        statusPanelSeparator.setName("statusPanelSeparator"); // NOI18N

        statusMessageLabel.setName("statusMessageLabel"); // NOI18N

        statusAnimationLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        statusAnimationLabel.setName("statusAnimationLabel"); // NOI18N

        org.jdesktop.layout.GroupLayout statusPanelLayout = new org.jdesktop.layout.GroupLayout(statusPanel);
        statusPanel.setLayout(statusPanelLayout);
        statusPanelLayout.setHorizontalGroup(
            statusPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(statusPanelSeparator, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 990, Short.MAX_VALUE)
            .add(statusPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(statusMessageLabel)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 950, Short.MAX_VALUE)
                .add(statusAnimationLabel)
                .addContainerGap())
        );
        statusPanelLayout.setVerticalGroup(
            statusPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(statusPanelLayout.createSequentialGroup()
                .add(statusPanelSeparator, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 32, Short.MAX_VALUE)
                .add(statusPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(statusMessageLabel)
                    .add(statusAnimationLabel))
                .add(3, 3, 3))
        );

        setComponent(mainPanel);
        setMenuBar(menuBar);
        setStatusBar(statusPanel);
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Schreibt den Wert des oberen Sliders in ein definiertes Label
     * 
     * @param evt 
     */
    private void problemSetSizeChangeHandler(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_problemSetSizeChangeHandler
        problemSetSizeIndicator.setText("(" + problemSizeSlider.getValue() + ")");
    }//GEN-LAST:event_problemSetSizeChangeHandler

    /**
     * Benachrichtigt alle Sorter jetzt zu sortieren.
     * Außerdem werden alle Knöpfe entsprechend der Aktion
     * in ihrer benutzbarkeit gesteuert.
     * @param evt 
     */
    private void runButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_runButtonActionPerformed
        runButton.setEnabled(false);
        updateProblemSetButton.setEnabled(false);
        pauseButton.setEnabled(true);

        busyIconTimer.start();

        statusMessageLabel.setText("Running");
        sorters.run();
    }//GEN-LAST:event_runButtonActionPerformed

    /**
     * Benachrichtigt alles Sorter zu pausieren
     * Jenach Status wird der Text im Button auf
     * "Pause" oder "Resume" geändert
     * @param evt 
     */
    private void pauseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pauseButtonActionPerformed
        if (pauseButton.isSelected()) {
            pauseButton.setText("resume");
            stepButton.setEnabled(true);
            sorters.pause();
        } else {
            pauseButton.setText("pause");
            stepButton.setEnabled(false);
            sorters.resume();
        }

    }//GEN-LAST:event_pauseButtonActionPerformed
    
    /**
     * Benachrichtigt alles Sorter ein Schritt weiter zu gehen
     */
    private void stepButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stepButtonActionPerformed
        sorters.step();
    }//GEN-LAST:event_stepButtonActionPerformed

    /**
     * Stellt bei allen Sortern ein neues Problemset entsprechend
     * des eingestellten typs und der größe ein
     */
    private void updateProblemSetButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateProblemSetButtonActionPerformed
        if (problemSetType.getSelectedItem() == "Random") {
            sorters.setProblem(SortingProblemCreator.random(problemSizeSlider.getValue()));
        } else if (problemSetType.getSelectedItem() == "Predefined") {
            sorters.setProblem(SortingProblemCreator.predefined(problemSizeSlider.getValue()));
        } else if (problemSetType.getSelectedItem() == "Flat") {
            sorters.setProblem(SortingProblemCreator.flat(problemSizeSlider.getValue()));
        } else if (problemSetType.getSelectedItem() == "Reverse") {
            sorters.setProblem(SortingProblemCreator.reverse(problemSizeSlider.getValue()));
        }

    }//GEN-LAST:event_updateProblemSetButtonActionPerformed

    /**
     * Stell ein neues Interval für die Pause zwischen den Einzelen Sorter
     * operationen ein und schreibt den selben Wert in ein Label
     *
     * Die Änderung des Interval wird erst erreicht wenn die sorter pausiert und
     * wieder gestartet werden - beides übernimmt diese Methode
     * 
     * @param evt 
     */
    private void intervalSliderMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_intervalSliderMouseReleased
        RunnableSortingCollectionDelegator.stepInterval = intervalSlider.getValue();
        if(!sorters.isPaused()){
            sorters.pause();
            sorters.resume();
        }
    }//GEN-LAST:event_intervalSliderMouseReleased

    /**
     * Schreibt den Wert des Interval Sliders in ein Label
     * 
     * @param evt 
     */
    private void intervalSliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_intervalSliderStateChanged
        intervalLabel.setText("(" + intervalSlider.getValue() + " ms)");
    }//GEN-LAST:event_intervalSliderStateChanged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel intervalLabel;
    private javax.swing.JSlider intervalSlider;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JSeparator jSeparator1;
    public javax.swing.JPanel mainPanel;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JToggleButton pauseButton;
    private javax.swing.JLabel problemSetSizeIndicator;
    private javax.swing.JComboBox problemSetType;
    private javax.swing.JSlider problemSizeSlider;
    private javax.swing.JButton runButton;
    private javax.swing.JLabel statusAnimationLabel;
    javax.swing.JLabel statusMessageLabel;
    private javax.swing.JPanel statusPanel;
    private javax.swing.JButton stepButton;
    private javax.swing.JButton updateProblemSetButton;
    // End of variables declaration//GEN-END:variables
}
